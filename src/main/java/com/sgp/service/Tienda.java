package com.sgp.service;

import java.util.Map;

import com.sgp.model.Cliente;
import com.sgp.model.Pedido;
import com.sgp.producto.Producto;
import com.sgp.producto.ProductoDigital;
import com.sgp.producto.ProductoFisico;

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

        double totalNeto = 0.0;
        double totalIva = 0.0;
        double totalEnvio = 0.0;

        /*
         ** Acumula el precio neto, el IVA y coste de envío por cada producto del pedido
         ** según su tipo y cantidad:
         */
        for (Map.Entry<Producto, Integer> entry : pedido.getCantidades().entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            switch (p) {
                case null -> {
                    // No se hace nada.
                }
                case ProductoDigital pd -> {
                    double baseImponible = pd.getPrecio() * (1.0 - pd.getDescuento());
                    totalNeto += baseImponible * cantidad;
                    totalIva += baseImponible * pd.getIva() * cantidad;
                }
                case ProductoFisico pf -> {
                    totalNeto += pf.getPrecio() * cantidad;
                    totalEnvio += pf.calcularCosteEnvio(cliente.getPais()) * cantidad;
                }
                default -> totalNeto += p.getPrecio() * cantidad;
            }
        }

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
