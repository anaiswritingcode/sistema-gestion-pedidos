package com.sgp.producto;

public class ProductoFisico extends Producto {
  private double costeEnvio;

  // * Constructor:

  public ProductoFisico(String nombre, double precio, double costeEnvio) {
    super(nombre, precio);

    // Validación interna de 'costeEnvio':

    if (costeEnvio <= 0) {
      throw new IllegalArgumentException("El coste de envío debe ser positivo y mayor que 0.");
    }

    // Asignación de variables:

    this.costeEnvio = costeEnvio;
  }

  // * Getters:

  public double getCosteEnvio() {
    return costeEnvio;
  }

  // * Setters:

  public void setCosteEnvio(double nuevoCosteEnvio) {
    if (nuevoCosteEnvio <= 0) {
      throw new IllegalArgumentException("El coste de envío debe ser positivo y mayor que 0.");
    }
    this.costeEnvio = nuevoCosteEnvio;
  }

  // * Otros métodos:

  @Override // Sobreescribe de Producto para hacer cálculos con los atributos específicos.
  public double calcularPrecioFinal() {
    return Math.round((getPrecio() + costeEnvio) * 100.0) / 100.0;
  }
}
