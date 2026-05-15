package com.sgp.producto;

public abstract class Producto {
  private static int contadorId = 0; // 'static' para que se comparta entre instancias.
  private final int id;
  private String nombre;
  private double precioBase;

  // * CONSTRUCTOR:

  protected Producto(String nombre, double precioBase) {

    // Validación interna de 'nombre':
    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }

    // Validación interna de 'precio':
    if (precioBase <= 0) {
      throw new IllegalArgumentException("El precio debe ser positivo.");
    }

    // Asignación de variables:
    this.id = contadorId++;
    this.nombre = nombre;
    this.precioBase = precioBase;
  }

  // * GETTERS:

  public int getIdProducto() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public double getPrecio() {
    return precioBase;
  }

  // * SETTERS:

  /**
   * Asigna un nuevo nombre al producto.
   * 
   * @param nuevoNombre
   */
  public void setNombre(String nuevoNombre) {
    if (nuevoNombre == null || nuevoNombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }
    this.nombre = nuevoNombre;
  }

  /**
   * Asigna un nuevo precio al producto.
   * 
   * @param nuevoPrecio
   */
  public void setPrecioBase(double nuevoPrecio) {
    if (nuevoPrecio <= 0) {
      throw new IllegalArgumentException("El precio debe ser positivo.");
    }
    this.precioBase = nuevoPrecio;
  }

  // * OTROS MÉTODOS:

  // Sobreescribe el método toString() propio de objetos en Java:
  @Override
  public String toString() {
    return String.format("Producto: %s, precio %.2f", nombre, precioBase);
  }

  /**
   * Método para ser sobreescrito por las distintas subclases de producto
   * con los datos adicionales específicos de cada una a la hora de
   * calcular el precio final.
   * 
   * @return
   */
  public abstract double calcularPrecioFinal();
}
