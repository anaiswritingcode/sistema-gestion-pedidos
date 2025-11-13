import java.util.*;

public class Cliente {
  private int idCliente;
  private static int contadorId = 0;
  private String nombreCompleto;
  private List<String> direcciones = new ArrayList<>();
  private String correo;

  // * Constructor:

  public Cliente(String nombreCompleto, String correo) {
    contadorId++;
    idCliente = contadorId;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
  }

  // * Getters:

  public int getIdCliente() {
    return idCliente;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public String[] getDirecciones() {
    String[] nombreDirecciones = new String[direcciones.size()];
    int contadorDirecciones = 0;
    for (String direccion : direcciones) {
      nombreDirecciones[contadorDirecciones++] = direccion;
    }
    return nombreDirecciones;
  }

  public String getCorreo() {
    return correo;
  }

  // * Setters:

  public void setDireccion(String direccion) { // Para añadir direcciones de las que elegir.
    direcciones.add(direccion);
  }

  public void setCorreo(String correo) { // Para asignar un nuevo correo.
    this.correo = correo;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método toString() propio de objetos en Java.
  public String toString() {
    return "\nCliente: " + nombreCompleto + ", correo: " + correo + ", ID: " + idCliente + ".";
    // En proceso.
  }
}
