import java.util.*;

public class Pedido {
  /* Notas: 
  - Para establecer la relación de asociación con
  Cliente, úsalo como atributo en vez de un ArrayList; 
  un pedido solo le pertenece a un cliente en este caso.
  - Para establecer la relación de agregación con Producto,
  usa un ArrayList; los productos existen por sí mismos y
  un pedido puede contener varios. 
  */

  private Cliente cliente;
  private List<Producto> productos = new ArrayList<>();
  // En proceso.

  // * Constructor:

  public Pedido(Cliente cliente) {
    this.cliente = cliente;
    this.productos = new ArrayList<>();
    // En proceso.
  }

  // * Otros métodos:

  public void agregarProducto(Producto producto) {
    productos.add(producto);
  }

  public double calcularTotal() {
    double total = 0;
    for (Producto producto : productos) {
      total += producto.calcularPrecioFinal();
    }
    return total;
  }

  public void mostrarResumen() {
    System.out.println("Productos:");
    for (Producto producto : productos) {
      System.out.println("- " + producto.getNombre() + ", precio final: " + producto.calcularPrecioFinal() + "€."); // Imprime una lista con los productos.
    }
    System.out.println("Total: " + calcularTotal() + "€.");
  }
}
