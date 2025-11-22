import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {

    int numClientes = 0;
    String nombreCliente = "";
    String correoCliente = "";
    int codigoCliente = 0;
    List<Cliente> listaClientes = new ArrayList<>();

    int numProductos = 0;
    String tipoProducto = "";
    String nombreProducto = "";
    double precioProducto = 0;
    double tamannoDescargaProducto = 0;
    String licenciaProducto = "";
    double costeEnvioProducto = 0;
    double ivaProducto = 0;
    double descuentoProducto = 0;
    boolean esPrimerProducto = true;
    int codigoProducto = 0;
    List<Producto> listaProductos = new ArrayList<>();

    int codigoPedido = 0;
    String respuestaResumen = "";
    boolean verResumen = true;
    List<Pedido> listaPedidos = new ArrayList<>();

    Scanner scan = new Scanner(System.in);

    // * Menú de preguntas:

    System.out.println("\n-- Sistema de gestión de productos --");

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

    // * Añadir producto:

    System.out.print("\n¿Cuántos productos va a añadir? ");
    numProductos = scan.nextInt();
    scan.nextLine(); // Limpiar buffer.

    for (int i = 0; i < numProductos; i++) { // Por cada producto.
      do { // Elegir tipo de producto y revisar el input.
        if (esPrimerProducto) {
          System.out.print("\n¿Qué tipo de producto desea añadir? (digital, fisico) ");
        tipoProducto = scan.nextLine();
        } else {
          System.out.print("\n¿Qué otro tipo de producto desea añadir? (digital, fisico) ");
          tipoProducto = scan.nextLine();
        }
        
        if (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico")) {
          System.out.println("\nOpción no valida. Escriba 'digital' o 'fisico' (sin tilde).");
        } else {
          esPrimerProducto = false;
        }
      } while (!tipoProducto.equalsIgnoreCase("digital") && !tipoProducto.equalsIgnoreCase("fisico"));

      System.out.print(" - Nombre del producto " + (i+1) + ": ");
      nombreProducto = scan.nextLine();

      do { // Poner precio y revisar el input.
        System.out.print(" - Precio del producto " + (i+1) + ": ");
        precioProducto = scan.nextDouble();
        scan.nextLine(); // Limpiar buffer.

        if (precioProducto < 0) {
          System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
        }
      } while (precioProducto < 0);

      if (tipoProducto.equalsIgnoreCase("digital")) { // Pedir atributos específicos de los productos digitales.
        do { // Poner tamaño de descarga y revisar el input.
          System.out.print(" - Tamaño de descarga del producto " + (i+1) + " (en MB): ");
          tamannoDescargaProducto = scan.nextDouble();
          scan.nextLine(); // Limpiar buffer.
        
          if (tamannoDescargaProducto < 0) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
          }
        } while (tamannoDescargaProducto < 0);

        System.out.print(" - Licencia del producto " + (i+1) + ": ");
        licenciaProducto = scan.nextLine();

        listaProductos.add(new ProductoDigital(nombreProducto, precioProducto, tamannoDescargaProducto, licenciaProducto));

        do { // Poner IVA y revisar el input.
          System.out.print(" - IVA del producto " + (i+1) + " (en decimales): ");
          ivaProducto = scan.nextDouble();
          
          if (ivaProducto < 0) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
          } else {
            ((ProductoDigital) listaProductos.get(i)).setIva(ivaProducto);
          }
        } while (ivaProducto < 0);
        
        do { // Poner descuento y revisar el input.
          System.out.print(" - Descuento al producto " + (i+1) + " (en decimales): ");
          descuentoProducto = scan.nextDouble();
          scan.nextLine(); // Limpiar buffer.
          
          if (descuentoProducto < 0) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
          } else {
            ((ProductoDigital) listaProductos.get(i)).setDescuento(descuentoProducto);
          }
        } while (descuentoProducto < 0);
      }
      
      if (tipoProducto.equalsIgnoreCase("fisico")) { // Pedir atributos específicos de los productos físicos.
        do { // Poner el coste de envío y revisar el input.
          System.out.print(" - Coste de envío del producto " + (i+1) + ": ");
          costeEnvioProducto = scan.nextDouble();
          scan.nextLine(); // Limpiar buffer.
        
          if (costeEnvioProducto < 0) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo.\n");
          }
        } while (costeEnvioProducto < 0);

        listaProductos.add(new ProductoFisico(nombreProducto, precioProducto, costeEnvioProducto));
      }
    }

    // * Hacer pedido:

    for (int i = 0; i < numClientes; i++) { // Por cada cliente.
      do { // Añadir cliente y revisar el input.
        System.out.print("\n¿De qué cliente va a ser el pedido " + (i+1) + "? Introduce su número: ");
        codigoCliente = scan.nextInt();
      
        if (codigoCliente < 1 || codigoCliente > listaClientes.size()) {
          System.out.println("\nEntrada de datos no válida. Introduce un número positivo y dentro del rango total de clientes.");
        }
      } while (codigoCliente < 1 || codigoCliente > listaClientes.size());

      listaPedidos.add(new Pedido(listaClientes.get(codigoCliente - 1)));

      System.out.print("\n¿Cuántos productos quiere meter en el pedido " + (i+1) + "? ");
      numProductos = scan.nextInt();

      esPrimerProducto = true; // Reseteamos la variable para la próxima comprobación.

      for (int j = 0; j < numProductos; j++) { // Por cada producto.
        do { // Añadir producto y revisar el input.
          if (esPrimerProducto) {
            System.out.print(" " + (j + 1) + ") ¿Qué producto desea agregar al pedido " + (i+1) + "? Introduce su número: ");
            codigoProducto = scan.nextInt();
          } else {
            System.out.print(" " + (j + 1) + ") ¿Qué otro producto desea agregar al pedido " + (i+1) + "? Introduce su número: ");
            codigoProducto = scan.nextInt();
          }
        
          if (codigoProducto < 1 || codigoProducto > listaProductos.size()) {
            System.out.println("\nEntrada de datos no válida. Introduce un número positivo y dentro del rango total de productos.");
          } else {
            esPrimerProducto = false;
          }
        } while (codigoProducto < 1 || codigoProducto > listaProductos.size());

        listaPedidos.get(i).agregarProducto(listaProductos.get(codigoProducto - 1));
      }
    }

    // * Mostrar resumen del pedido:

    do {
      do { // Elegir resumen y revisar el input.
        System.out.print("\n¿De qué pedido desea ver el resumen? Introduce su número: ");
        codigoPedido = scan.nextInt();
        
        if (codigoPedido < 1 || codigoPedido > listaPedidos.size()) {
          System.out.println("\nEntrada de datos no válida. Introduce un número positivo y dentro del rango total de pedidos.");
        } else {
          listaPedidos.get(codigoPedido - 1).mostrarResumen();
        }
      } while (codigoPedido < 1 || codigoPedido > listaPedidos.size());

      do { // Elegir si ver más resúmenes y revisar el input.
        System.out.print("\n¿Quiere ver el resumen de otro pedido? (si, no) ");
        respuestaResumen = scan.next();
        
        if (!respuestaResumen.equalsIgnoreCase("si") && !respuestaResumen.equalsIgnoreCase("no")) {
          System.out.println("\nOpción no valida. Escriba 'si' o 'no' (sin tilde).");
        } else {
          if (respuestaResumen.equalsIgnoreCase("no")) {
            verResumen = false;
          }
        }
      } while (!respuestaResumen.equalsIgnoreCase("si") && !respuestaResumen.equalsIgnoreCase("no"));
    } while (verResumen);

    scan.close();
  }
}
