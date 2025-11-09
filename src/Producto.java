public class Producto {
  private String nombre;
  private double precio;
  // En proceso.

  // * Constructor:

  public Producto(String nombre, double precio) {
    this.nombre = nombre;
    this.precio = precio;
    // En proceso.
  }

  // * Getters:

  public String getNombre() {
    return nombre;
  }

  public double getPrecio() {
    return precio;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método toString() propio de objetos en Java.
  public String toString() {
    return "\nProducto: " + nombre + ", precio: " + precio + ".";
    // En proceso.
  }

  public double calcularPrecioFinal() {
    return precio; // Aquí simplemente devuelve 'precio', se sobreescribe en las subclases 'ProductoFisico' y 'ProductoDigital' según sus atributos específicos.
  }
}
