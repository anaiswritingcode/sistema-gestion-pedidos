package com.sgp.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sgp.producto.Producto;
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
     * Calcula el precio total del pedido sumando el precio final de cada producto
     * (con IVA para productos digitales y con coste de envío del país del cliente
     * para productos físicos) multiplicado por su cantidad.
     *
     * @return Precio total sin descuentos de cliente.
     */
    public double calcularTotal() {
        if (cantidades.isEmpty()) {
            throw new IllegalStateException("La lista de productos no puede estar vacía.");
        }

        double total = 0.0;

        /*
         ** Sumar el precio por unidad de cada producto (con envío o IVA según tipo)
         ** multiplicado por su cantidad:
         */
        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            if (p == null)
                continue;

            double precioPorUnidad;
            if (p instanceof ProductoFisico pf) {
                precioPorUnidad = pf.getPrecio() + pf.calcularCosteEnvio(cliente.getPais());
            } else {
                precioPorUnidad = p.calcularPrecioFinal();
            }

            total += precioPorUnidad * cantidad;
        }

        return total;
    }

    public String mostrarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Productos:\n");

        /*
         ** Para cada producto calcular su precio por unidad y subtotal
         ** y añadirlos al resumen:
         */
        for (Map.Entry<Producto, Integer> entry : cantidades.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            if (p == null)
                continue;

            /*
             ** Precio base + envío según país del cliente para productos físicos,
             ** precio con IVA y descuento para productos digitales:
             */
            double precioPorUnidad;
            if (p instanceof ProductoFisico pf) { // Si el producto es (es una instancia de) un ProductoFísico.
                precioPorUnidad = pf.getPrecio() + pf.calcularCosteEnvio(cliente.getPais());
            } else {
                precioPorUnidad = p.calcularPrecioFinal();
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
