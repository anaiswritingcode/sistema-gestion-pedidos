package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductoDigitalTest {

  @Test
  void testCalculoIva() { // * CP-03

    double precio = 5.0;
    String ivaEsperado = "GENERAL";

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Calendario 2026", precio, 12.00, "Todos los derechos reservados");

    producto.aplicarIVA(ivaEsperado);

    // Verificamos que el IVA se haya asignado correctamente:

    assertEquals(0.21, producto.getIva(), "El IVA tendría que haber salido '0.21'");

    // Vemos si también funciona metido en el precio final:

    double precioFinal = producto.calcularPrecioFinal();
    double precioFinalEsperado = Math.round(precio * (1 + 0.21) * 100.0) / 100.0;

    assertEquals(precioFinalEsperado, precioFinal, "El precio final tendría que haber salido '6.05'");
  }

  @Test
  void testCalculoTotal() { // * CP-04

    double precio = 45;
    String iva = "GENERAL";
    double descuento = 0.1;

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", precio, 0.5, "Todos los derechos reservados");

    producto.aplicarIVA(iva);
    producto.asignarDescuento(descuento);

    // Verificamos que el precio final salga 49€:

    assertEquals(49.01, producto.calcularPrecioFinal(), "El precio final tendría que haber salido 49.01€");
  }

  @Test
  void testCalculoIvaSuper() { // * CP-12

    String iva = "SUPER";
    double ivaEsperado = 0.04;

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", 10.0, 1.0, "Todos los derechos reservados");

    // Verificamos que el IVA salga 0.04:

    producto.aplicarIVA(iva);
    assertEquals(ivaEsperado, producto.getIva(), "El IVA de tipo SUPER tendría que haber salido '0.04'");
  }

  @Test
  void testCalculoIvaipoInvalido() { // * CP-13

    String iva = "INVENTADO";

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", 5.0, 2.5, "Todos los derechos reservados");

    // Verificamos que se devuelva una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> producto.aplicarIVA(iva),
        "Tendría que haber saltado una IllegalArgumentException por un tipo de IVA inválido.");
  }

  @Test
  void testCreacionProductoDigital() {

    double tamannoDescarga = 0.0;
    String licencia1 = null;
    String licencia2 = "";

    /*
     ** Verificamos que saltan excepciones al crear
     ** productos digitales con datos inválidos:
     */

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoDigital("Producto", 8.5, tamannoDescarga, licencia1),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto digital con tamaño de descarga y licencia inválidos.");

    assertThrows(IllegalArgumentException.class,
        () -> new ProductoDigital("Producto", 8.5, 1.0, licencia2),
        "Tendría que haber saltado una IllegalArgumentException al crear un producto digital con licencia inválida.");
  }

  @Test
  void testCalculoIvaReducido() {

    String iva = "REDUCIDO";
    double ivaEsperado = 0.10;
    double precio = 20.0;

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", precio, 1.0, "Todos los derechos reservados");
    producto.aplicarIVA(iva);

    // Verificamos los IVAs reducidos:

    assertEquals(ivaEsperado, producto.getIva(), "El IVA de tipo REDUCIDO tendría que haber salido '0.10'");

    double precioFinalEsperado = Math.round(precio * (1 + ivaEsperado) * 100.0) / 100.0;
    assertEquals(precioFinalEsperado, producto.calcularPrecioFinal(),
        "El precio final con IVA reducido no es el esperado.");
  }

  @ParameterizedTest
  @ValueSource(doubles = { -0.5, 1.1 })
  void testDescuentoFueraDeRango(double descuentoInvalido) {

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", 10.0, 1.0, "Todos los derechos reservados");

    // Probamos descuentos inválidos:

    assertThrows(IllegalArgumentException.class,
        () -> producto.asignarDescuento(descuentoInvalido),
        "Tendría que haber saltado una IllegalArgumentException por un descuento fuera del rango [0, 1]");
  }

  @Test
  void testSettersInvalidos() {

    // Producto digital de prueba:

    ProductoDigital producto = new ProductoDigital("Producto 1", 10.0, 1.0, "Todos los derechos reservados");

    // Probamos a meter datos inválidos en los setters:

    assertThrows(IllegalArgumentException.class,
        () -> producto.setTamannoDescarga(0),
        "Tendría que haber saltado una IllegalArgumentException por tamaño de descarga <= 0");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setLicencia(null),
        "Tendría que haber saltado una IllegalArgumentException por licencia nula.");

    assertThrows(IllegalArgumentException.class,
        () -> producto.setLicencia(""),
        "Tendría que haber saltado una IllegalArgumentException por licencia vacía.");
  }
}
