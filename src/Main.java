import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    int respuestaMenu = 0;
    String nombreCliente = "";
    String correoCliente = "";
    String tipoProducto = "";
    String nombreProducto = "";
    double precioProducto = 0;
    double tamannoDescargaProducto = 0;
    String licenciaProducto = "";
    double costeEnvioProducto = 0;
    List<Cliente> listaClientes = new ArrayList<>();
    List<Producto> listaProductos = new ArrayList<>();

    Scanner scan = new Scanner(System.in);

    // * Menú de preguntas:

    System.out.println("\n-- Sistema de gestión de productos --");

    System.out.println("\nElija una de las siguientes opciones:");
    System.out.println(" 1) Añadir cliente.");
    System.out.println(" 2) Añadir producto.");

    do {
      System.out.print("\nSu respuesta (1, 2): ");
      respuestaMenu = scan.nextInt();
      scan.nextLine(); // Limpiar buffer.

      if (respuestaMenu != 1 && respuestaMenu != 2) {
        System.out.println("\nOpción no válida, introduzca '1' o '2'.");
      }
    } while (respuestaMenu != 1 && respuestaMenu != 2);

    if (respuestaMenu == 1) { // Añadir cliente.
      System.out.print("\n¿Cuál es el nombre completo del cliente? ");
      nombreCliente = scan.nextLine();

      System.out.print("¿Cuál es el correo del cliente? ");
      correoCliente = scan.nextLine();

      listaClientes.add(new Cliente(nombreCliente, correoCliente));
    } else { // Añadir producto.
      do { // Elegir tipo de producto.
        System.out.print("\n¿Qué tipo de producto desea añadir? (digital, fisico) ");
        tipoProducto = scan.nextLine();

        if (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico")) {
          System.out.println("\nOpción no valida. Escriba 'digital' o 'fisico' (sin tilde).");
        }
      } while (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico"));

      System.out.print("- Nombre del producto: ");
      nombreProducto = scan.nextLine();

      System.out.print("- Precio del producto: ");
      precioProducto = scan.nextDouble(); // TODO: Verificar que no sea negativo.
      scan.nextLine(); // Limpiar buffer.

      if (tipoProducto.equalsIgnoreCase("digital")) { // Pedir atributos específicos de los productos digitales.
        System.out.print("- Tamaño de descarga (en MB): ");
        tamannoDescargaProducto = scan.nextDouble(); // TODO: Verificar que no sea negativo.
        scan.nextLine(); // Limpiar buffer.

        System.out.print("- Licencia: ");
        licenciaProducto = scan.nextLine();

        listaProductos.add(new ProductoDigital(nombreProducto, precioProducto, tamannoDescargaProducto, licenciaProducto));
      }
      
      if (tipoProducto.equalsIgnoreCase("fisico")) { // Pedir atributos específicos de los productos físicos.
        System.out.print("- Coste de envío: ");
        costeEnvioProducto = scan.nextDouble(); // TODO: Verificar que no sea negativo.

        listaProductos.add(new ProductoFisico(nombreProducto, precioProducto, costeEnvioProducto));
      }
    }

    scan.close();
  }
}
