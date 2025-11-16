import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    int respuestaMenu = 0;
    String nombreCliente = "";
    String correoCliente = "";
    List<Cliente> listaClientes = new ArrayList<>();

    Scanner scan = new Scanner(System.in);

    // * Menú de preguntas:

    System.out.println("\n-- Sistema de gestión de productos --");

    System.out.println("\nEliga una de las siguientes opciones:");
    System.out.println(" 1) Añadir cliente.");
    System.out.println(" 2) Añadir producto.");

    do {
      System.out.print("\nSu respuesta (1, 2): ");
      respuestaMenu = scan.nextInt();
      scan.nextLine();

      if (respuestaMenu != 1 && respuestaMenu != 2) {
        System.out.println("Opción no válida, introduzca '1' o '2'.");
      }
    } while (respuestaMenu != 1 && respuestaMenu != 2);

    if (respuestaMenu == 1) {
      System.out.print("\n¿Cuál es el nombre completo del cliente? ");
      nombreCliente = scan.nextLine();

      System.out.print("¿Cuál es el correo del cliente? ");
      correoCliente = scan.nextLine();

      listaClientes.add(new Cliente(nombreCliente, correoCliente));
    }

    scan.close();
  }
}
