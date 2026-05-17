package com.sgp.producto;

public class ProductoFisico extends Producto {
  private double peso;
  private String zonaDestino;

  // * CONSTRUCTOR:

  public ProductoFisico(String nombre, double precioBase, double peso, String zonaDestino) {

    super(nombre, precioBase);

    // Validación interna de 'peso':
    if (peso <= 0) {
      throw new IllegalArgumentException("El peso debe ser positivo.");
    }

    // Validación interna de 'zonaDestino':
    if (zonaDestino == null || zonaDestino.isEmpty()) {
      throw new IllegalArgumentException("La zona de destino no puede estar vacía.");
    } else if (zonaDestino.equalsIgnoreCase("ESPAÑA") || zonaDestino.equalsIgnoreCase("ESPA?A")) {
      zonaDestino = "ESPANA";
    }

    // Asignación de variables:
    this.peso = peso;
    this.zonaDestino = zonaDestino.toUpperCase();
  }

  // * GETTERS:

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

  /**
   * Calcula el coste de envío según el país de destino.
   *
   * @param pais País de destino (e.g, "ESPAÑA", "FRANCIA").
   * @return Coste de envío en euros: 0 para España, 5 para
   *         Francia/Italia/Portugal, 10 para el resto.
   */
  public double calcularCosteEnvio(String pais) {
    if (pais == null || pais.isEmpty()) {
      throw new IllegalArgumentException("El país de destino no puede estar vacío.");
    }

    String paisNormalizado = pais.trim().toUpperCase()
        .replace("ESPAÑA", "ESPANA")
        .replace("ESPA?A", "ESPANA");

    return switch (paisNormalizado) {
      case "ESPANA" -> 0.0;
      case "FRANCIA", "ITALIA", "PORTUGAL" -> 5.0;
      default -> 10.0;
    };
  }

  // Sobreescribe el de Producto para calcular con los atributos específicos:
  @Override
  public double calcularPrecioFinal() {
    double precioTotal = getPrecio() + calcularCosteEnvio(this.zonaDestino);
    return Math.round(precioTotal * 100.0) / 100.0;
  }
}
