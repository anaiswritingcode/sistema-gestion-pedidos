package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductoFisicoTest {

  @Test
  void testCosteEnvio() { // * CP-07

    double precio = 14.0;
    double peso = 6.0;
    String zonaDestino = "ESPAÑA";

    // Productos físicos de prueba:

    ProductoFisico producto1 = new ProductoFisico("Producto 1", precio, peso, zonaDestino);
    ProductoFisico producto2 = new ProductoFisico("Producto 2", precio, peso, zonaDestino);
    ProductoFisico producto3 = new ProductoFisico("Producto 3", precio, peso, zonaDestino);

    double precioFinalTotal = (producto1.calcularPrecioFinal()
        + producto2.calcularPrecioFinal()
        + producto3.calcularPrecioFinal());

    // Verificamos que el total con ese coste de envío no salga ni 61€ ni 59€:

    assertNotEquals(61.0, precioFinalTotal, "El precio final total no debería salir 61€");
    assertNotEquals(59.0, precioFinalTotal, "El precio final total no debería salir 59€");
  }

  @Test
  void testCreacionProductoFisico() {

    // Verificamos que salta una excepción con costes de envío inválidos:

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoFisico("Producto", 1.0, 0, "ESPAÑA"),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto físico con un coste de envío menor o igual que 0.");
  }

  @Test
  void testCalcularCosteEnvio() {

    ProductoFisico producto = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");

    // Verificar que España = 0€, Francia = 5€ y el resto = 10€:

    assertEquals(0.0, producto.calcularCosteEnvio("ESPAÑA"));
    assertEquals(5.0, producto.calcularCosteEnvio("FRANCIA"));
    assertEquals(10.0, producto.calcularCosteEnvio("CHINA"));
  }

  @Test
  void testCalcularCosteEnvioItaliayPortugal() { // * (REGRESIÓN)

    ProductoFisico producto = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");

    // Verificar que Italia y Portugal también tienen coste de envío de 5€:

    assertEquals(5.0, producto.calcularCosteEnvio("ITALIA"), "El coste de envío a Italia debería ser 5€");
    assertEquals(5.0, producto.calcularCosteEnvio("PORTUGAL"), "El coste de envío a Portugal debería ser 5€");
    assertNotEquals(0.0, producto.calcularCosteEnvio("ITALIA"), "Italia no debería tener un coste de envío gratuito.");
  }

  @Test
  void testCalcularCosteEnvioPaisNuloOVacio() {

    ProductoFisico producto = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");

    // Verificar que un país nulo o vacío lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> producto.calcularCosteEnvio(null),
        "Debería lanzarse una IllegalArgumentException por país nulo.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.calcularCosteEnvio(""),
        "Debería lanzarse una IllegalArgumentException por país vacío.");
  }

  @Test
  void testSetPesoInvalido() {

    ProductoFisico producto = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");

    // Verificar que un peso negativo o cero lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> producto.setPeso(-1.0),
        "Debería lanzarse una IllegalArgumentException por peso negativo.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setPeso(0.0),
        "Debería lanzarse una IllegalArgumentException por peso cero.");
  }

  @Test
  void testConstructorZonaDestinoInvalida() {

    // Verificamos que el constructor lanza excepción con zona de destino
    // nula/vacía:

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoFisico("Producto", 5.0, 5.0, null),
        "Debería lanzarse una IllegalArgumentException por zona de destino nula.");

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoFisico("Producto", 5.0, 5.0, ""),
        "Debería lanzarse una IllegalArgumentException por zona de destino vacía.");
  }

  @Test
  void testGetZonaDestino() { // * (REGRESIÓN)

    ProductoFisico productoEspana = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");
    ProductoFisico productoFrancia = new ProductoFisico("Producto", 5.0, 5.0, "FRANCIA");
    ProductoFisico productoPortugal = new ProductoFisico("Producto", 5.0, 5.0, "PORTUGAL");

    /*
     ** Verificamos que España se devuelva con Ñ aunque se almacene internamente
     ** sin ella:
     */

    assertEquals("ESPAÑA", productoEspana.getZonaDestino());
    assertNotEquals("ESPANA", productoEspana.getZonaDestino(),
        "La zona de España debería devolverse con Ñ.");

    // Verificamos que otros países se devuelven en mayúsculas sin modificar:

    assertEquals("FRANCIA", productoFrancia.getZonaDestino());
    assertEquals("PORTUGAL", productoPortugal.getZonaDestino());
  }

  @Test
  void testSetZonaDestino() {

    ProductoFisico producto = new ProductoFisico("Producto", 5.0, 5.0, "ESPAÑA");

    // Verificamos el setter de zona de destino:

    producto.setZonaDestino("ALEMANIA");
    assertEquals("ALEMANIA", producto.getZonaDestino());

    // Verificamos que al ponerlo null y vacío se lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> producto.setZonaDestino(null),
        "Debería lanzarse una IllegalArgumentException por zona de destino nula.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setZonaDestino(""),
        "Debería lanzarse una IllegalArgumentException por zona de destino vacía.");
  }
}
