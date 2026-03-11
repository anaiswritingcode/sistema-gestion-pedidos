package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ProductoFisicoTest {

  @Test
  public void testCosteEnvio() {

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
}
