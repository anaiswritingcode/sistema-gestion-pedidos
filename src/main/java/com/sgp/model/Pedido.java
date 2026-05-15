package com.sgp.model;

import java.util.ArrayList;
import java.util.List;

import com.sgp.producto.Producto;

public class Pedido {
    private static int contadorId = 0; // 'static' para que se comparta entre instancias.
    private final int idPedido;
    private final Cliente cliente;
    private final List<Producto> productos;
    private int cantidades;

    // * CONSTRUCTOR:

    /**
     * Construcción base para los pedidos.
     * 
     * @param cliente Cliente al que le pertenece el pedido.
     */
    public Pedido(Cliente cliente) {

        // Validación interna de 'cliente':
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        // Asignación de variables:
        this.idPedido = contadorId++;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.cantidades = 0; // Ninguna unidad hasta que se introduzcan.
    }

    // * GETTERS:

    public int getIdPedido() {
        return idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public int getCantidades() {
        return cantidades;
    }

    // * OTROS MÉTODOS:

    private void validarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null.");
        }

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo.");
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
        for (int i = 0; i < unidades; i++) {
            productos.add(producto);
            this.cantidades++;
        }
    }

    /**
     * Para eliminar productos de la lista de forma controlada.
     * 
     * @param producto Producto a eliminar.
     */
    public void eliminarProducto(Producto producto) {
        if (producto == null) {
            throw new NullPointerException("El producto no puede ser null.");
        }

        if (!productos.remove(producto)) {
            throw new IllegalArgumentException("El producto no existe en el pedido.");
        }
    }

    /**
     * Para calcular el precio total de la lista del pedido.
     * 
     * @return Precio total.
     * @throws Exception
     */
    public double calcularTotal() throws Exception {

        double total;
        int annosAntiguedad = this.getCliente().getAnnosAntiguedad();
        boolean esVip = this.getCliente().getEsVip();

        if (productos.isEmpty()) {
            throw new Exception("La lista de productos no puede estar vacía.");
        }

        total = productos.stream()
                // Cogemos el precio final de cada producto de la lista:
                .mapToDouble(Producto::calcularPrecioFinal)
                .sum();

        if (annosAntiguedad > 5) {
            total *= 0.8;
        } else if (annosAntiguedad > 2) {
            total *= 0.9;
        }

        if (esVip) {
            total *= 0.75;
        }

        return total;
    }

    public String mostrarResumen() throws Exception {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Productos:\n");
        productos.forEach(
                p -> resumen
                        .append("- ")
                        .append(p.getNombre())
                        .append(", precio final: ")
                        .append(p.calcularPrecioFinal())
                        .append(" euros.\n"));
        resumen
                .append("Total: ")
                .append(calcularTotal())
                .append(" euros.");

        // Devolvemos la String construida:
        return resumen.toString();
    }
}
