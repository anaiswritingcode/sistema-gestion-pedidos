package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProductoDigitalTest {

  @Test
  void testCalculoIva() { // * CP-03

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

  @Test
  void testCalculoTotal() { // * CP-04

    double precio = 45;
    double iva = 0.21;
    double descuento = 0.1;

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", precio, 0.5, "Todos los derechos reservados");

    producto.asignarIva(iva);
    producto.asignarDescuento(descuento);

    // Verificamos que el precio final salga 49€:

    assertEquals(49.01, producto.calcularPrecioFinal(), "El precio final tendría que haber salido 49.01€");
  }
}
