package com.sgp.producto;

public class ProductoDigital extends Producto {
  private double tamannoDescarga;
  private String licencia;
  private double iva;
  private double descuento;

  // * Constructor:

  public ProductoDigital(String nombre, double precio, double tamannoDescarga, String licencia) {

    super(nombre, precio);

    // Validación interna de 'tamannoDescarga':

    if (tamannoDescarga <= 0) {
      throw new IllegalArgumentException("El tamaño de descarga debe ser positivo y mayor que 0.");
    }

    // Validación interna de 'licencia':

    if (licencia == null || licencia.isEmpty()) {
      throw new IllegalArgumentException("La licencia no puede estar vacía.");
    }

    // Asignación de variables:

    this.tamannoDescarga = tamannoDescarga;
    this.licencia = licencia;
    this.iva = 0; // De forma predeterminada.
    this.descuento = 0; // De forma predeterminada.
  }

  // * Getters:

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

  // * Setters:

  /**
   * Para asignarle un nuevo tamaño de descarga al producto
   * 
   * @param nuevoTamannoDescarga Tamaño de descarga en MB
   */
  public void setTamannoDescarga(double nuevoTamannoDescarga) {
    if (nuevoTamannoDescarga <= 0) {
      throw new IllegalArgumentException("El tamaño de descarga debe ser positivo y mayor que 0.");
    }
    this.tamannoDescarga = nuevoTamannoDescarga;
  }

  public void setLicencia(String nuevaLicencia) { // Para asignarle una nueva licencia al producto.
    if (nuevaLicencia == null || nuevaLicencia.isEmpty()) {
      throw new IllegalArgumentException("La licencia no puede estar vacía.");
    }
    this.licencia = nuevaLicencia;
  }

  // * Otros métodos:

  /**
   * Para asignarle un (nuevo) IVA a un producto de forma controlada
   * 
   * @param iva IVA a asignar, en decimales
   */
  public void asignarIva(double iva) {
    if (iva <= 0) {
      throw new IllegalArgumentException("El IVA debe ser positivo y mayor que 0.");
    }

    if (this.iva == iva) {
      throw new IllegalArgumentException("Ese IVA ya estaba asignado al producto.");
    }

    this.iva = iva;
  }

  /**
   * Para asignarle un (nuevo) descuento a un producto de forma controlada
   * 
   * @param descuento Descuento a asignar, en decimales
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

  @Override // Sobreescribe de Producto para hacer cálculos con los atributos específicos.
  public double calcularPrecioFinal() {
    return Math.round(getPrecio() * (1 + iva) * (1 - descuento) * 100.0) / 100.0;
  }
}
