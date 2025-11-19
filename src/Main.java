import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {

    int respuestaMenu = 0;

    int numClientes = 0;
    String nombreCliente = "";
    String correoCliente = "";

    int numProductos = 0;
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
    System.out.println(" 1) Añadir clientes.");
    System.out.println(" 2) Añadir productos.");

    System.out.println("\nNota: si lo que quiere es registrar un pedido, primero debe añadir al menos un cliente y un producto.");

    do {
      System.out.print("\nSu respuesta (1, 2): ");
      respuestaMenu = scan.nextInt();

      if (respuestaMenu != 1 && respuestaMenu != 2) {
        System.out.println("\nOpción no válida, introduzca '1' o '2'.");
      }
    } while (respuestaMenu != 1 && respuestaMenu != 2);

    if (respuestaMenu == 1) {

      // * Añadir cliente:

      System.out.print("\n¿Cuántos clientes va a añadir? ");
      numClientes = scan.nextInt();
      scan.nextLine(); // Limpiar buffer.

      for (int i = 0; i < numClientes; i++) { // Por cada cliente.
        System.out.print("\n¿Cuál es el nombre completo del cliente " + (i+1) + "? ");
        nombreCliente = scan.nextLine();
  
        System.out.print("¿Cuál es el correo del cliente " + (i+1) + "? ");
        correoCliente = scan.nextLine();
  
        listaClientes.add(new Cliente(nombreCliente, correoCliente));
      }

    } else {

      // * Añadir producto:

      System.out.print("\n¿Cuántos productos va a añadir? ");
      numProductos = scan.nextInt();
      scan.nextLine(); // Limpiar buffer.

      for (int i = 0; i < numProductos; i++) { // Por cada producto.
        do { // Elegir tipo de producto.
          System.out.print("\n¿Qué tipo de producto desea añadir? (digital, fisico) ");
          tipoProducto = scan.nextLine();
  
          if (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico")) {
            System.out.println("\nOpción no valida. Escriba 'digital' o 'fisico' (sin tilde).");
          }
        } while (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico"));
  
        System.out.print("- Nombre del producto " + (i+1) + ": ");
        nombreProducto = scan.nextLine();
  
        do { // Poner precio y revisar el input.
          System.out.print("- Precio del producto " + (i+1) + ": ");
          precioProducto = scan.nextDouble();
          scan.nextLine(); // Limpiar buffer.
  
          if (precioProducto < 0) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
          }
        } while (precioProducto < 0);
  
        if (tipoProducto.equalsIgnoreCase("digital")) { // Pedir atributos específicos de los productos digitales.
          do { // Poner tamaño de descarga y revisar el input.
            System.out.print("- Tamaño de descarga del producto " + (i+1) + " (en MB): ");
            tamannoDescargaProducto = scan.nextDouble();
            scan.nextLine(); // Limpiar buffer.
          
            if (tamannoDescargaProducto < 0) {
              System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
            }
          } while (tamannoDescargaProducto < 0);
  
          System.out.print("- Licencia del producto " + (i+1) + ": ");
          licenciaProducto = scan.nextLine();
  
          listaProductos.add(new ProductoDigital(nombreProducto, precioProducto, tamannoDescargaProducto, licenciaProducto));
        }
        
        if (tipoProducto.equalsIgnoreCase("fisico")) { // Pedir atributos específicos de los productos físicos.
          do { // Poner el coste de envío y revisar el input.
            System.out.print("- Coste de envío del producto " + (i+1) + ": ");
            costeEnvioProducto = scan.nextDouble();
          
            if (costeEnvioProducto < 0) {
              System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
            }
          } while (costeEnvioProducto < 0);
  
          listaProductos.add(new ProductoFisico(nombreProducto, precioProducto, costeEnvioProducto));
        }
      }
    }

    scan.close();
  }
}
