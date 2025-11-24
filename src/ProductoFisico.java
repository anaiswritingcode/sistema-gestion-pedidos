public class ProductoFisico extends Producto {
  private double costeEnvio;

  // * Constructor:

  public ProductoFisico(String nombre, double precio, double costeEnvio) {
    super(nombre, precio);
    this.costeEnvio = costeEnvio;
  }

  // * Getters:

  // getNombre() y getPrecio() ya los implementa la superclase Producto y son accesibles desde la subclase ProductoFisico.

  public double getCosteEnvio() {
    return costeEnvio;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método equivalente de Producto para hacer cálculos con atributos específicos de ProductoFisico.
  public double calcularPrecioFinal() {
    double precio = getPrecio() + costeEnvio; // Para calcular el precio final del producto teniendo en cuenta el coste de envío.
    return Math.round(precio * 100.0) / 100.0; // Para redondear a dos decimales.
  }
}
