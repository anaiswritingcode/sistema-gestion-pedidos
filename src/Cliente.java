import java.util.*;

public class Cliente {
  private int idCliente;
  private String nombreCompleto;
  private List<String> direcciones = new ArrayList<>();
  private String correo;
  // En proceso.

  // * Constructor:

  public Cliente(int idCliente, String nombreCompleto, String correo) {
    this.idCliente = idCliente;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    // En proceso.
  }

  // * Getters:

  public int getIdCliente() {
    return idCliente;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public List<String> getDirecciones() {
    return direcciones;
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
