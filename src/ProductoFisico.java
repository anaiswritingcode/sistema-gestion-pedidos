public class ProductoFisico extends Producto {
  private double costeEnvio;
  // En proceso.

  // * Constructor:

  public ProductoFisico(String nombre, double precio, double costeEnvio) {
    super(nombre, precio);
    this.costeEnvio = costeEnvio;
    // En proceso.
  }

  // * Getters:

  // getNombre() y getPrecio() ya los implementa la superclase Producto y son accesibles desde la subclase ProductoFisico.

  public double getCosteEnvio() {
    return costeEnvio;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método equivalente de Producto para hacer cálculos con atributos específicos de ProductoFisico.
  public double calcularPrecioFinal() {
    return getPrecio() + costeEnvio;
  }
}
