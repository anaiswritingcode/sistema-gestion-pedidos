package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductoTest {

  @Test
  void testCreacionProductoBase() {

    String nombre1 = "";
    String nombre2 = null;
    double precio1 = 0;
    double precio2 = -1;

    /*
     * Verificamos que saltan excepciones al crear
     * productos con datos base inválidos:
     */

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoDigital(nombre1, precio1, 1.0, "Todos los derechos reservados"),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto con nombre y precio inválidos.");

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoFisico(nombre2, precio2, 2.0),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto con nombre y precio inválidos.");
  }
}
