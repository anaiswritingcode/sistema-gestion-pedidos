package com.sgp.producto;

public abstract class Producto {
  private static int contadorId = 0; // 'static' para que se comparta entre instancias.
  private final int idProducto;
  private String nombre;
  private double precio;

  // * Constructor:

  public Producto(String nombre, double precio) {

    // Validación interna de 'nombre':

    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }

    // Validación interna de 'precio':

    if (precio <= 0) {
      throw new IllegalArgumentException("El precio debe ser positivo y mayor que 0.");
    }

    this.idProducto = contadorId++;
    this.nombre = nombre;
    this.precio = precio;
  }

  // * Getters:

  public int getIdProducto() {
    return idProducto;
  }

  public String getNombre() {
    return nombre;
  }

  public double getPrecio() {
    return precio;
  }

  // * Setters:

  public void setNombre(String nuevoNombre) { // Para asignarle un nuevo nombre al producto.
    if (nuevoNombre == null || nuevoNombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }
    this.nombre = nuevoNombre;
  }

  public void setPrecio(double nuevoPrecio) { // Para asignarle un nuevo precio al producto.
    if (nuevoPrecio <= 0) {
      throw new IllegalArgumentException("El precio debe ser positivo y mayor que 0.");
    }
    this.precio = nuevoPrecio;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método toString() propio de objetos en Java.
  public String toString() {
    return String.format("Producto: %s, precio %.2f", nombre, precio);
  }

  public abstract double calcularPrecioFinal();
}
