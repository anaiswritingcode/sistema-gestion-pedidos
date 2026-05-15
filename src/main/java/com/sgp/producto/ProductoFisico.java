package com.sgp.producto;

public class ProductoFisico extends Producto {
  private double costeEnvio;
  private double peso;
  private String zonaDestino;

  // * CONSTRUCTOR:

  public ProductoFisico(String nombre, double precioBase, double peso, String zonaDestino) {

    super(nombre, precioBase);

    // Validación interna de 'peso':
    if (peso <= 0) {
      throw new IllegalArgumentException("El peso debe ser positivo.");
    } else {
      this.costeEnvio += peso;
    }

    // Validación interna de 'zonaDestino':
    if (zonaDestino == null || zonaDestino.isEmpty()) {
      throw new IllegalArgumentException("La zona de destino no puede estar vacía.");
    } else if (zonaDestino.equalsIgnoreCase("ESPAÑA") || zonaDestino.equalsIgnoreCase("ESPA?A")) {
      zonaDestino = "ESPANA";
    }

    switch (zonaDestino.toUpperCase()) {
      case "ESPANA" -> this.costeEnvio += 0.0;
      case "FRANCIA" -> this.costeEnvio += 5.0;
      case "ITALIA" -> this.costeEnvio += 5.0;
      case "PORTUGAL" -> this.costeEnvio += 5.0;
      default -> this.costeEnvio += 10.0;
    }

    // Asignación de variables:
    this.peso = peso;
  }

  // * GETTERS:

  /*
   ** Recalcula el coste de envío con cada llamada, por si se han cambiado
   ** los valores de peso y/o zona de envío:
   */
  public double getCosteEnvio() {
    double costeEnvioActual;

    switch (this.zonaDestino) {
      case "ESPANA" -> costeEnvioActual = 0.0;
      case "FRANCIA" -> costeEnvioActual = 5.0;
      case "ITALIA" -> costeEnvioActual = 5.0;
      case "PORTUGAL" -> costeEnvioActual = 5.0;
      default -> costeEnvioActual = 10.0;
    }

    return this.peso + costeEnvioActual;
  }

  public double getPeso() {
    return peso;
  }

  public String getZonaDestino() {
    if (this.zonaDestino.equalsIgnoreCase("ESPANA")) {
      return "ESPAÑA";
    } else {
      return zonaDestino.toUpperCase();
    }
  }

  // * SETTERS:

  /**
   * Asigna un nuevo peso al producto.
   * 
   * @param nuevoPeso
   */
  public void setPeso(double nuevoPeso) {
    if (nuevoPeso <= 0) {
      throw new IllegalArgumentException("El peso debe ser positivo.");
    }
    this.peso = nuevoPeso;
  }

  /**
   * Asigna una nueva zona de destino al producto.
   * 
   * @param nuevaZonaDestino
   */
  public void setZonaDestino(String nuevaZonaDestino) {
    if (nuevaZonaDestino == null || nuevaZonaDestino.isEmpty()) {
      throw new IllegalArgumentException("La zona de destino no puede estar vacía.");
    } else if (nuevaZonaDestino.equalsIgnoreCase("ESPAÑA") || nuevaZonaDestino.equalsIgnoreCase("ESPA?A")) {
      this.zonaDestino = "ESPANA";
    } else {
      this.zonaDestino = nuevaZonaDestino.toUpperCase();
    }
  }

  // * OTROS MÉTODOS:

  // Sobreescribe el de Producto para calcular con los atributos específicos:
  @Override
  public double calcularPrecioFinal() {
    double precioTotal = getPrecio() + getCosteEnvio();
    return Math.round(precioTotal * 100.0) / 100.0;
  }
}
