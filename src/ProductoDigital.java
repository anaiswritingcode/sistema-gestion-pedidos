public class ProductoDigital extends Producto {
  private double tamannoDescarga;
  private String licencia;
  private double iva;
  private double descuento;
  // En proceso.

  // * Constructor:

  public ProductoDigital(String nombre, double precio, double tamannoDescarga, String licencia) {
    super(nombre, precio);
    this.tamannoDescarga = tamannoDescarga;
    this.licencia = licencia;
    this.iva = 0; // Inicializado a 0 por defecto para evitar errores.
    this.descuento = 0; // Inicializado a 0 por la misma razón.
    // En proceso.
  }

  // * Getters:

  // getNombre() y getPrecio() ya los implementa la superclase Producto y son accesibles desde la subclase ProductoDigital.

  public double getTamannoDescarga() {
    return tamannoDescarga;
  }

  public String getLicencia() {
    return licencia;
  }

  // * Setters:

  public void setIva(double iva) {
    this.iva = iva;
  }

  public void setDescuento(double descuento) {
    this.descuento = descuento;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método equivalente de Producto para hacer cálculos con atributos específicos de ProductoDigital.
  public double calcularPrecioFinal() {
    return getPrecio() * (1 + iva) * (1 - descuento);
  }
}
