package com.sgp.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sgp.producto.Producto;
import com.sgp.producto.ProductoDigital;
import com.sgp.producto.ProductoFisico;

public class Pedido {
    private static int contadorId = 0;
    private final int idPedido;
    private final Cliente cliente;
    private final Map<Producto, Integer> cantidades;

    // * CONSTRUCTOR:

    /**
     * Construcción base para los pedidos.
     *
     * @param cliente Cliente al que le pertenece el pedido.
     */
    public Pedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        this.idPedido = contadorId++;
        this.cliente = cliente;
        this.cantidades = new LinkedHashMap<>();
    }

    // * GETTERS:

    public int getIdPedido() {
        return idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Lista de productos únicos del pedido (sin duplicados por cantidad):
    public List<Producto> getProductos() {
        return new ArrayList<>(cantidades.keySet());
    }

    // Mapa de producto (cantidad de unidades):
    public Map<Producto, Integer> getCantidades() {
        return new LinkedHashMap<>(cantidades);
    }

    // * OTROS MÉTODOS:

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null.");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    private void validarNoVacio() {
        if (cantidades.isEmpty()) {
            throw new IllegalStateException("La lista de productos no puede estar vacía.");
        }
    }

    /**
     * Para añadir productos a la lista de forma controlada.
     *
     * @param producto Producto a agregar.
     * @param unidades Unidades de ese producto.
     */
    public void agregarProducto(Producto producto, int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("El número de unidades debe ser positivo.");
        }

        validarProducto(producto);

        if (cantidades.containsKey(producto)) {
            cantidades.put(producto, cantidades.get(producto) + unidades);
        } else {
            cantidades.put(producto, unidades);
        }
    }

    /**
     * Para eliminar una unidad de un producto de forma controlada.
     *
     * @param producto Producto a eliminar.
     */
    public void eliminarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null.");
        }
        if (!cantidades.containsKey(producto)) {
            throw new IllegalArgumentException("El producto no existe en el pedido.");
        }

        // Eliminar la entrada del mapa si solo queda 1 unidad, si no restar 1:
        int cantidadProductoActual = cantidades.get(producto);
        if (cantidadProductoActual <= 1) {
            cantidades.remove(producto);
        } else {
            cantidades.put(producto, cantidadProductoActual - 1);
        }
    }

    /**
     * Suma de precios base tras descuentos de producto, sin IVA ni envío.
     *
     * @return Total neto en euros.
     */
    public double calcularTotalNeto() {

        validarNoVacio();

        double total = 0.0;

        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {

            Producto p = entry.getKey();

            if (p == null) {
                continue;
            }

            int cantidad = entry.getValue();
            if (p instanceof ProductoDigital pd) { // Si es un ProductoDigital.
                total += pd.getPrecio() * (1.0 - pd.getDescuento()) * cantidad;
            } else {
                total += p.getPrecio() * cantidad;
            }
        }

        return total;
    }

    /**
     * Importe total de IVA de todos los productos digitales del pedido.
     *
     * @return Total IVA en euros.
     */
    public double calcularTotalIva() {

        validarNoVacio();

        double total = 0.0;

        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {

            Producto p = entry.getKey();

            if (p == null) {
                continue;
            }

            int cantidad = entry.getValue();
            if (p instanceof ProductoDigital pd) { // Si es un ProductoDigital.
                total += pd.getPrecio() * (1.0 - pd.getDescuento()) * pd.getIva() * cantidad;
            }
        }

        return total;
    }

    /**
     * Importe total de costes de envío de todos los productos físicos,
     * calculado según el país del cliente asociado al pedido.
     *
     * @return Total envío en euros.
     */
    public double calcularTotalEnvio() {

        validarNoVacio();

        double total = 0.0;

        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {

            Producto p = entry.getKey();

            if (p == null) {
                continue;
            }

            int cantidad = entry.getValue();
            if (p instanceof ProductoFisico pf) { // Si es un ProductoFisico.
                total += pf.calcularCosteEnvio(cliente.getPais()) * cantidad;
            }
        }

        return total;
    }

    /**
     * Calcula el precio total del pedido: suma de neto, IVA y envío de todos
     * los productos multiplicados por su cantidad. Sin descuentos de cliente.
     *
     * @return Precio total sin descuentos de cliente.
     */
    public double calcularTotal() {
        return calcularTotalNeto() + calcularTotalIva() + calcularTotalEnvio();
    }

    public String mostrarResumen() {

        StringBuilder resumen = new StringBuilder();
        resumen.append("Productos:\n");

        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {

            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            if (p == null)
                continue;

            /*
             ** Precio base + envío según país del cliente para productos físicos.
             */
            double precioPorUnidad = p.calcularPrecioFinal();
            if (p instanceof ProductoFisico pf) { // Si es un producto físico.
                precioPorUnidad += pf.calcularCosteEnvio(cliente.getPais());
            }

            double subtotal = precioPorUnidad * cantidad;
            resumen.append("- ").append(p.getNombre())
                    .append(" x").append(cantidad)
                    .append(", precio/unidad: ").append(precioPorUnidad)
                    .append(" euros, subtotal: ").append(subtotal)
                    .append(" euros.\n");
        }
        resumen.append("Total: ").append(calcularTotal()).append(" euros.");

        return resumen.toString();
    }
}
