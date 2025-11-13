public class Producto {
  private int idProducto;
  private static int contadorId = 0;
  private String nombre;
  private double precio;

  // * Constructor:

  public Producto(String nombre, double precio) {
    contadorId++;
    idProducto = contadorId;
    this.nombre = nombre;
    this.precio = precio;
  }

  // * Getters:

  public int getIdProducto() {
    return idProducto;
  }

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
  }

  public double calcularPrecioFinal() {
    return precio; // Aquí simplemente devuelve 'precio', se sobreescribe en las subclases 'ProductoFisico' y 'ProductoDigital' según sus atributos específicos.
  }
}
