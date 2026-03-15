package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductoTest {

  @Test
  void testCreacionProductoBase() { // * CP-10 se incluye dentro pero sin AssertFalse.

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

  @Test
  void testSettersProductoBase() {

    String nombre = "Monitor 4k";
    double precio = 350.0;

    // Verificamos que funcionen los setters de Producto:

    ProductoFisico producto = new ProductoFisico(nombre, precio, 12.0);

    assertThrows(IllegalArgumentException.class,
        () -> producto.setNombre(""),
        "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo nombre vacío.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setNombre(null),
        "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo nombre de valor nulo.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setPrecio(-1),
        "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo precio inválido.");
  }
}
