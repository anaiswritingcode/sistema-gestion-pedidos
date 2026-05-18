package com.sgp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Factura {

    // Prefijo para los códigos de todas las facturas:
    private static final String PREFIJO_CODIGO = "FAC-";
    // Fecha tipo día/mes/año hora:minuto:segundo con DateTimeFormatter:
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static int contadorFactura = 0;

    private final String codigoFactura;
    private final LocalDateTime fechaEmision;
    private final double totalNeto;
    private final double totalIva;
    private final double totalEnvio;
    private final double descuentoAplicado;
    private final double totalFinal;

    /**
     * Crea una factura con desglose completo de importes.
     *
     * @param totalNeto         Suma de precios base tras descuentos de producto,
     *                          antes de IVA y envío.
     * @param totalIva          Importe total de IVA de todos los productos.
     * @param totalEnvio        Importe total de costes de envío.
     * @param descuentoAplicado Descuento de fidelidad o estado VIP del cliente,
     *                          en decimales
     */
    public Factura(double totalNeto, double totalIva, double totalEnvio, double descuentoAplicado) {

        // Validación interna de 'totalNeto':
        if (totalNeto < 0) {
            throw new IllegalArgumentException("El total neto no puede ser negativo.");
        }

        // Validación interna de 'totalIva':
        if (totalIva < 0) {
            throw new IllegalArgumentException("El total de IVA no puede ser negativo.");
        }

        // Validación interna de 'totalEnvio':
        if (totalEnvio < 0) {
            throw new IllegalArgumentException("El total de envío no puede ser negativo.");
        }

        // Validación interna de 'descuentoAplicado':
        if (descuentoAplicado < 0 || descuentoAplicado > 1) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 1.");
        }

        // Asignación de variables:
        this.codigoFactura = PREFIJO_CODIGO + String.format("%04d", ++contadorFactura); // Formato "FAC-0001"
        this.fechaEmision = LocalDateTime.now();
        this.totalNeto = Math.round(totalNeto * 100.0) / 100.0;
        this.totalIva = Math.round(totalIva * 100.0) / 100.0;
        this.totalEnvio = Math.round(totalEnvio * 100.0) / 100.0;
        this.descuentoAplicado = descuentoAplicado;
        double subtotal = totalNeto + totalIva + totalEnvio;
        this.totalFinal = Math.round(subtotal * (1.0 - descuentoAplicado) * 100.0) / 100.0;
    }

    // * GETTERS:

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Devuelve la base imponible (suma de precios base tras descuentos de producto,
     * antes de IVA y envío).
     *
     * @return Total neto en euros.
     */
    public double getTotalNeto() {
        return totalNeto;
    }

    /**
     * Devuelve el importe total de IVA de todos los productos digitales del pedido.
     *
     * @return Total IVA en euros.
     */
    public double getTotalIva() {
        return totalIva;
    }

    /**
     * Devuelve el importe total de costes de envío de todos los productos físicos.
     *
     * @return Total envío en euros.
     */
    public double getTotalEnvio() {
        return totalEnvio;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    /**
     * Devuelve el importe total final después de aplicar todos los descuentos.
     *
     * @return Total final en euros.
     */
    public double getTotalFinal() {
        return totalFinal;
    }

    // * OTROS MÉTODOS:

    /**
     * Genera un resumen detallado de la factura.
     *
     * @return String con el desglose completo de la factura.
     */
    @Override
    public String toString() {
        double subtotal = totalNeto + totalIva + totalEnvio;
        double importeDescuento = Math.round(subtotal * descuentoAplicado * 100.0) / 100.0;

        return String.format(
                "=== FACTURA %s ===%n" +
                        "Fecha emisión  : %s%n" +
                        "────────────────────────────────────%n" +
                        "Total neto     : %.2f €%n" +
                        "Total IVA      : %.2f €%n" +
                        "Total envío    : %.2f €%n" +
                        "Subtotal       : %.2f €%n" +
                        "Descuento      : -%.2f € (%.0f %%)%n" +
                        "────────────────────────────────────%n" +
                        "TOTAL FINAL    : %.2f €%n",
                codigoFactura,
                fechaEmision.format(FORMATO_FECHA),
                totalNeto, totalIva, totalEnvio, subtotal,
                importeDescuento, descuentoAplicado * 100,
                totalFinal);
    }
}
