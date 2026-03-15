package com.sgp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ClienteTest {

  @Test
  void testCreacionCliente() {

    String nombre1 = null;
    String nombre2 = "";
    String nombre3 = "Alfonso";
    String correo1 = null;
    String correo2 = "";
    String correo3 = "alfonso.com";

    // Verificamos que salta una excepción al crear clientes con datos inválidos:

    assertThrows(IllegalArgumentException.class,
        () -> new Cliente(nombre1, correo3),
        "Tendría que haber saltado una IllegalArgumentException al crear un cliente con nombre y correo inválidos.");

    assertThrows(NullPointerException.class,
        () -> new Cliente(nombre3, correo1),
        "Tendría que haber saltado una NullPointerException al crear un cliente con un correo de valor nulo.");

    assertThrows(IllegalArgumentException.class,
        () -> new Cliente(nombre2, correo2),
        "Tendría que haber saltado una IllegalArgumentException al crear un cliente con nombre y correo vacíos.");
  }

  @Test
  void testGettersCliente() {

    String nombre = "Alfonso";
    String correo = "alfonso@mail.com";

    // Verificamos que funcionen los getters de Cliente:

    Cliente cliente = new Cliente(nombre, correo);
    cliente.agregarDireccion("Calle Abeto S/N");

    assertTrue(cliente.getIdCliente() >= 0);
    assertEquals(nombre, cliente.getNombreCompleto());
    assertEquals(correo, cliente.getCorreo());
    assertFalse(cliente.getDirecciones() == null || cliente.getDirecciones().isEmpty());
  }
}
