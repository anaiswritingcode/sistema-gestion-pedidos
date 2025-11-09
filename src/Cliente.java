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

  public List<String> getDirecciones() {
    return direcciones;
  }

  // * Setters:

  public void setDireccion(String direccion) {
    direcciones.add(direccion);
  }

  // * Otros métodos:

  @Override // Sobreescribe el método toString() propio de objetos en Java.
  public String toString() {
    return "\nCliente: " + nombreCompleto + ", correo: " + correo + ", ID: " + idCliente + ".";
    // En proceso.
  }
}
