package com.sgp.producto;

public class ProductoFisico extends Producto {

  private double peso;
  private String zonaDestino;
  private final String espanaInterrogacion = "ESPA?A";
  private final String espanaPlano = "ESPANA";
  private final String espanaConSimboloEspecial = "ESPAÑA";

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
    } else if (zonaDestino.equalsIgnoreCase(espanaConSimboloEspecial)
        || zonaDestino.equalsIgnoreCase(espanaInterrogacion)) {
      zonaDestino = espanaPlano;
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
    if (this.zonaDestino.equalsIgnoreCase(espanaPlano)) {
      return espanaConSimboloEspecial;
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
    } else if (nuevaZonaDestino.equalsIgnoreCase(espanaConSimboloEspecial)
        || nuevaZonaDestino.equalsIgnoreCase(espanaInterrogacion)) {
      this.zonaDestino = espanaPlano;
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
        .replace(espanaConSimboloEspecial, espanaPlano)
        .replace(espanaInterrogacion, espanaPlano);

    return switch (paisNormalizado) {
      case espanaPlano -> 0.0;
      case "FRANCIA", "ITALIA", "PORTUGAL" -> 5.0;
      default -> 10.0;
    };
  }

  /*
   ** Sobreescribe el de Producto, pero aquí el envío es siempre contextual
   ** (por el país del cliente), así que simplemente se devuelve el precio.
   */
  @Override
  public double calcularPrecioFinal() {
    return getPrecio();
  }
}
