package com.sgp.model;

import java.util.ArrayList;
import java.util.List;

import com.sgp.producto.Producto;

public class Pedido {
  private static int contadorId = 0; // 'static' para que se comparta entre instancias.
  private final int idPedido;
  private final Cliente cliente;
  private final List<Producto> productos;

  // * Constructor:

  /**
   * Construcción base para los pedidos
   * 
   * @param cliente Cliente al que le pertenece el pedido
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
  }

  // * Getters:

  public int getIdPedido() {
    return idPedido;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public List<Producto> getProductos() {
    return new ArrayList<>(productos);
  }

  // * Otros métodos:

  private void validarProducto(Producto producto) {

    if (producto == null) {
      throw new IllegalArgumentException("El producto no puede ser null.");
    }

    if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
    }

    if (producto.getPrecio() <= 0) {
      throw new IllegalArgumentException("El precio debe ser mayor a 0.");
    }
  }

  /**
   * Para añadir productos a la lista de forma controlada
   * 
   * @param producto Producto a agregar
   */
  public void agregarProducto(Producto producto) {
    validarProducto(producto);
    if (productos.contains(producto)) {
      throw new IllegalArgumentException("El producto ya está en la lista del pedido.");
    }
    productos.add(producto);
  }

  /**
   * Para eliminar productos de la lista de forma controlada
   * 
   * @param producto Producto a eliminar
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
   * Para calcular el precio total de la lista del pedido
   * 
   * @return Precio total
   */
  public double calcularTotal() {
    return productos.stream()
        .mapToDouble(Producto::calcularPrecioFinal) // Cogemos el precio final de cada producto de la lista.
        .sum();
  }

  public String mostrarResumen() {
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
    return resumen.toString(); // Devolvemos la String construida
  }
}
