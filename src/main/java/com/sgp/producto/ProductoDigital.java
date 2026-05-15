package com.sgp.producto;

public class ProductoDigital extends Producto {
  private double tamannoDescarga;
  private String licencia;
  private double iva;
  private double descuento;

  // * CONSTRUCTOR:

  public ProductoDigital(String nombre, double precioBase, double tamannoDescarga, String licencia) {

    super(nombre, precioBase);

    // Validación interna de 'tamannoDescarga':
    if (tamannoDescarga <= 0) {
      throw new IllegalArgumentException("El tamaño de descarga debe ser positivo.");
    }

    // Validación interna de 'licencia':
    if (licencia == null || licencia.isEmpty()) {
      throw new IllegalArgumentException("La licencia no puede estar vacía.");
    }

    // Asignación de variables:
    this.tamannoDescarga = tamannoDescarga;
    this.licencia = licencia;
    this.iva = 0; // De forma predeterminada hasta que se cambie.
    this.descuento = 0; // De forma predeterminada hasta que se cambie.
  }

  // * GETTERS:

  public double getTamannoDescarga() {
    return tamannoDescarga;
  }

  public String getLicencia() {
    return licencia;
  }

  public double getIva() {
    return iva;
  }

  public double getDescuento() {
    return descuento;
  }

  // * SETTERS:

  /**
   * Asigna un nuevo tamaño de descarga al producto.
   * 
   * @param nuevoTamannoDescarga Tamaño de descarga en MB.
   */
  public void setTamannoDescarga(double nuevoTamannoDescarga) {
    if (nuevoTamannoDescarga <= 0) {
      throw new IllegalArgumentException("El tamaño de descarga debe ser positivo.");
    }
    this.tamannoDescarga = nuevoTamannoDescarga;
  }

  /**
   * Asigna una nueva licencia al producto.
   * 
   * @param nuevaLicencia (e.g, "Todos lo derechos reservados")
   */
  public void setLicencia(String nuevaLicencia) {
    if (nuevaLicencia == null || nuevaLicencia.isEmpty()) {
      throw new IllegalArgumentException("La licencia no puede estar vacía.");
    }
    this.licencia = nuevaLicencia;
  }

  // * OTROS MÉTODOS:

  /**
   * Asigna un IVA nuevo/diferente al producto.
   * 
   * @param tipoIva IVA a asignar: general (21%), reducido (10%) o súper (4%).
   */
  public void aplicarIva(String tipoIva) {
    if (tipoIva.equalsIgnoreCase("SÚPER"))
      tipoIva = "SUPER";

    switch (tipoIva.toUpperCase()) {
      case "GENERAL" -> this.iva = 0.21;
      case "REDUCIDO" -> this.iva = 0.10;
      case "SUPER" -> this.iva = 0.04;
      default ->
        throw new IllegalArgumentException("El tipo de IVA debe ser 'general' (21%), 'reducido' (10%) o 'súper' (4%)");
    }
  }

  /**
   * Asigna un descuento nuevo/diferente al producto de forma controlada.
   * 
   * @param descuento Descuento a asignar, en decimales.
   */
  public void asignarDescuento(double descuento) {
    if (descuento < 0 || descuento > 1) {
      throw new IllegalArgumentException("El descuento debe ser positivo y menor que 1.0.");
    }

    if (this.descuento == descuento) {
      throw new IllegalArgumentException("Ese descuento ya estaba asignado al producto.");
    }

    this.descuento = descuento;
  }

  // Sobreescribe el de Producto para calcular con los atributos específicos:
  @Override
  public double calcularPrecioFinal() {
    double resultado = (getPrecio() * (1 - descuento)) * (1 + iva);
    return Math.round(resultado * 100.0) / 100.0;
  }
}
