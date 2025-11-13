import java.util.*;

public class Pedido {
  private int idPedido;
  private static int contadorId = 0;
  private Cliente cliente;
  private List<Producto> productos = new ArrayList<>();

  // * Constructor:

  public Pedido(Cliente cliente) {
    contadorId++;
    idPedido = contadorId;
    this.cliente = cliente;
    this.productos = new ArrayList<>();
  }

  // * Getters:

  public int getIdPedido() {
    return idPedido;
  }

  public String getCliente() {
    return cliente.getNombreCompleto();
  }

  public String[] getProductos() {
    String[] nombreProductos = new String[productos.size()];
    int contadorProducto = 0;
    for (Producto producto : productos) { // Para ir metiendo los nombres de cada producto introducido en la lista dentro de un array. 
      nombreProductos[contadorProducto++] = producto.getNombre();
    }
    return nombreProductos;
  }

  // * Otros métodos:

  public void agregarProducto(Producto producto) {
    productos.add(producto);
  }

  public double calcularTotal() {
    double total = 0;
    for (Producto producto : productos) { // Para añadir el precio final de cada producto introducido en la lista.
      total += producto.calcularPrecioFinal();
    }
    return total;
  }

  public void mostrarResumen() {
    System.out.println("\nProductos:");
    for (Producto producto : productos) { // Para imprimir cada producto introducido en la lista.
      System.out.println("- " + producto.getNombre() + ", precio final: " + producto.calcularPrecioFinal() + " euros.");
    }
    System.out.println("\nTotal: " + calcularTotal() + " euros.");
  }
}
