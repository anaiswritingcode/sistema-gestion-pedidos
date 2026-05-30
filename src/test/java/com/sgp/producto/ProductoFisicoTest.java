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

    // España → 0€, Francia → 5€, resto → 10€:

    assertEquals(0.0, producto.calcularCosteEnvio("ESPAÑA"));
    assertEquals(5.0, producto.calcularCosteEnvio("FRANCIA"));
    assertEquals(10.0, producto.calcularCosteEnvio("CHINA"));
  }
}
