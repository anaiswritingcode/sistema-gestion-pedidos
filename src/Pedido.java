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

  private int idPedido;
  private static int contadorId = 0;
  private Cliente cliente;
  private List<Producto> productos = new ArrayList<>();
  // En proceso.

  // * Constructor:

  public Pedido(Cliente cliente) {
    contadorId++;
    idPedido = contadorId;
    this.cliente = cliente;
    this.productos = new ArrayList<>();
    // En proceso.
  }

  // * Getters:

  public int getIdPedido() {
    return idPedido;
  }

  public String getCliente() {
    return cliente.getNombreCompleto();
  }

  public String getProductos() {
    for (Producto producto : productos) {
      return producto.getNombre();
    }
    
    return null;
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
    System.out.println("\nProductos:");
    for (Producto producto : productos) {
      System.out.println("- " + producto.getNombre() + ", precio final: " + producto.calcularPrecioFinal() + " euros."); // Imprime una lista con los productos.
    }
    System.out.println("\nTotal: " + calcularTotal() + " euros.");
  }
}
