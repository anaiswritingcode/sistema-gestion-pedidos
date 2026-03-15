package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProductoDigitalTest {

  @Test
  void testCalculoIva() {

    double precio = 5.0;
    double ivaEsperado = 0.21;

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Calendario 2026", precio, 12.00, "Todos los derechos reservados");

    producto.asignarIva(ivaEsperado);

    // Verificamos que el IVA se haya asignado correctamente:

    assertTrue(producto.getIva() == ivaEsperado, "El IVA tendría que haber salido '0.21'");

    // Vemos si también funciona metido en el precio final:

    double precioFinal = producto.calcularPrecioFinal();
    double precioFinalEsperado = Math.round(precio * (1 + ivaEsperado) * 100.0) / 100.0;

    assertTrue(precioFinal == precioFinalEsperado, "El precio final tendría que haber salido '6.05'");
  }
}
