package com.sgp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.sgp.producto.ProductoDigital;
import com.sgp.producto.ProductoFisico;

class PedidoTest {

  @ParameterizedTest
  @CsvSource({
      "60, 12, 35, 0.13, 0.25, 29.67",
      "60, 12, 35, 0.13, 0.25, 0",
      "60, 12, 35, 0.13, 0.25, -29.67"
  })

  void testCalcularTotal(double precioProductoFisico, double costeEnvio,
      double precioProductoDigital, double iva, double descuento, double totalInvalido) { // * CP-09

    // Productos de prueba:

    ProductoFisico productoFisico = new ProductoFisico("Producto 1", precioProductoFisico, costeEnvio);
    ProductoDigital productoDigital = new ProductoDigital("Producto 2", precioProductoDigital, 1.0,
        "Todos los derechos reservados");

    productoDigital.asignarIva(iva);
    productoDigital.asignarDescuento(descuento);

    // Pedido en el que meterlos, junto a un cliente de prueba asociado:

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");
    Pedido pedido = new Pedido(cliente);

    pedido.agregarProducto(productoFisico);
    pedido.agregarProducto(productoDigital);

    // Verificamos que el total no salga 29'67€ o 0€ o -29'67€:

    double totalFinal = pedido.calcularTotal();

    assertFalse(totalFinal == totalInvalido,
        "El total no tendría que haber salido " + totalInvalido);
  }

  @Test
  void testExcepcionPedidoSinCliente() { // * CP-11

    // Verificamos que salta una excepción al crear un pedido sin cliente asociado:

    assertThrows(IllegalArgumentException.class,
        () -> new Pedido(null),
        "Tendría que haber saltado una IllegalArgumentException al crear un pedido sin cliente asociado.");
  }

  @Test
  void testPedidoBufanda() { // * CP-01

    // Datos de prueba:

    ProductoFisico bufanda = new ProductoFisico("Bufanda", 10.0, 2.5);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(bufanda);

    // Verificamos que la bufanda esté agregada en la lista del pedido:

    assertTrue(pedido.getProductos().contains(bufanda), "Tendría que estar el producto 'bufanda' en el pedido.");
  }

  @Test
  void testPedidoTresEuros() { // * CP-02

    // Datos de prueba:

    ProductoDigital producto = new ProductoDigital("Monedas", 3.0, 0.1, "N/A");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto);

    // Verificamos que el producto de tres euros esta agregado en el pedido:

    assertEquals(3.0, pedido.getProductos().get(0).getPrecio(), "El precio del producto debería haber salido 3€");

    // Probamos que el precio final salga 3€:

    assertEquals(3.0, pedido.calcularTotal(), "El precio total final del pedido debería haber salido 3€");
  }

  @Test
  void testCaracterEspecial() { // * CP-05

    // Datos de prueba:

    ProductoFisico producto = new ProductoFisico("Móvil", 1.0, 1.0);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto);

    // Verificamos que el nombre del producto no salga "M?vil":

    assertNotEquals("M?vil", pedido.getProductos().get(0).getNombre(),
        "El nombre del pedido no debería haber salido 'M?vil'");
  }

  @Test
  void testTotalDosProductos() { // * CP-06

    // Datos de prueba:

    ProductoDigital producto1 = new ProductoDigital("Producto 1", 5.0, 0.1, "Todos los derechos reservados");
    producto1.asignarIva(0.1);

    ProductoDigital producto2 = new ProductoDigital("Producto 2", 6.0, 1.0, "Todos los derechos reservados");
    producto2.asignarIva(0.1);
    producto2.asignarDescuento(0.15);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto1);
    pedido.agregarProducto(producto2);

    // Verificamos que el total final no salga 11'10€ ni 0€ ni -11'10€:

    assertNotEquals(11.10, pedido.calcularTotal(), "El total del pedido no debería haber salido 11'10€");

    assertNotEquals(0, pedido.calcularTotal(), "El total del pedido no debería haber salido 0€");

    assertNotEquals(-11.10, pedido.calcularTotal(), "El total del pedido no debería haber salido -11'10€");
  }

  @Test
  void testProductoDuplicado() {

    // Datos de prueba:

    ProductoFisico producto = new ProductoFisico("Producto", 10.0, 3.5);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto);

    // Verificamos que no salga duplicado el producto de antes:

    assertEquals(1, pedido.getProductos().stream()
        .filter(p -> p.getNombre().equals("Producto"))
        .count(),
        "No debería salir el producto duplicado en la lista del pedido");
  }
}
