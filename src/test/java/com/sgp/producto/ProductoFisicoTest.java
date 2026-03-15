package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductoFisicoTest {

  @Test
  void testCosteEnvio() { // * CP-07

    double precio = 14.0;
    double costeEnvio = 6.0;

    // Productos físicos de prueba:

    ProductoFisico producto1 = new ProductoFisico("Producto 1", precio, costeEnvio);
    ProductoFisico producto2 = new ProductoFisico("Producto 2", precio, costeEnvio);
    ProductoFisico producto3 = new ProductoFisico("Producto 3", precio, costeEnvio);

    double precioFinalTotal = (producto1.calcularPrecioFinal()
        + producto2.calcularPrecioFinal()
        + producto3.calcularPrecioFinal());

    // Verificamos que el total con ese coste de envío no salga ni 61€ ni 59€:

    assertFalse(precioFinalTotal == 61.0, "El precio final total no debería salir 61€");
    assertFalse(precioFinalTotal == 59.0, "El precio final total no debería salir 59€");
  }

  @Test
  void testCreacionProductoFisico() {

    // Verificamos que salta una excepción con costes de envío inválidos:

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoFisico("Producto", 1.0, 0),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto físico con un coste de envío menor o igual que 0.");
  }
}
