package com.sgp.service;

import com.sgp.model.Cliente;
import com.sgp.model.Pedido;

public class Tienda {

    private static final double DESCUENTO_VIP = 0.20;
    private static final double DESCUENTO_FIDELIDAD_CORTA = 0.05;
    private static final double DESCUENTO_FIDELIDAD_EXTENDIDA = 0.10;
    private static final int ANNOS_FIDELIDAD_CORTA = 2;
    private static final int ANNOS_FIDELIDAD_PREMIUM = 5;

    /**
     * Realiza la venta de un pedido para el cliente dado.
     * Calcula el desglose de importes (neto, IVA, envío...),
     * aplica el descuento de fidelidad o estado VIP del cliente
     * y devuelve una Factura con el resultado final.
     *
     * @param cliente Cliente que realiza la compra.
     * @param pedido  Pedido con los productos a adquirir.
     * @return Factura generada con el desglose completo de importes.
     */
    public Factura realizarVenta(Cliente cliente, Pedido pedido) {

        // Validación interna de 'cliente':
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        // Validación interna de 'pedido':
        if (pedido == null || pedido.getCantidades().isEmpty()) {
            throw new IllegalArgumentException("El pedido no puede estar vacío.");
        }

        double totalNeto = pedido.calcularTotalNeto();
        double totalIva = pedido.calcularTotalIva();
        double totalEnvio = pedido.calcularTotalEnvio();
        double descuento = calcularDescuentoCliente(cliente);
        return new Factura(totalNeto, totalIva, totalEnvio, descuento);
    }

    /**
     * Calcula el porcentaje de descuento aplicable a un cliente según su estado
     * VIP y años de antigüedad. Los descuentos se acumulan.
     * 
     * @param cliente Cliente del que calcular el descuento.
     * @return Descuento acumulado, en decimales.
     */
    public double calcularDescuentoCliente(Cliente cliente) {

        // Validación interna de 'cliente':
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        double descuento = 0.0;

        if (cliente.getEsVip()) {
            descuento += DESCUENTO_VIP;
        }

        if (cliente.getAnnosAntiguedad() >= ANNOS_FIDELIDAD_PREMIUM) {
            descuento += DESCUENTO_FIDELIDAD_EXTENDIDA;
        } else if (cliente.getAnnosAntiguedad() >= ANNOS_FIDELIDAD_CORTA) {
            descuento += DESCUENTO_FIDELIDAD_CORTA;
        }

        return descuento;
    }
}
