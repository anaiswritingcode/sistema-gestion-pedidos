import java.util.*;

public class Cliente {
  private int idCliente;
  private String nombreCompleto;
  private List<String> direccion = new ArrayList<>();
  private String correo;
  // En proceso.

  // * Constructor:

  public Cliente(int idCliente, String nombreCompleto, String correo) {
    this.idCliente = idCliente;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    // En proceso.
  }
}
