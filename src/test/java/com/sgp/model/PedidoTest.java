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
      "60, ESPAÑA, 35, REDUCIDO, 0.25, 29.67, 3.4",
      "60, ITALIA, 35, REDUCIDO, 0.25, 0, 7.2",
      "60, CHINA, 35, REDUCIDO, 0.25, -29.67, 2.5"
  })

  // * CP-09:
  void testCalcularTotal(double precioProductoFisico, String zonaDestino,
      double precioProductoDigital, String iva, double descuento, double totalInvalido, double peso) {

    // Productos de prueba:

    ProductoFisico productoFisico = new ProductoFisico("Producto 1", precioProductoFisico, peso, zonaDestino);
    ProductoDigital productoDigital = new ProductoDigital("Producto 2", precioProductoDigital, 1.0,
        "Todos los derechos reservados");

    productoDigital.aplicarIVA(iva);
    productoDigital.asignarDescuento(descuento);

    // Pedido en el que meterlos, junto a un cliente de prueba asociado:

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");
    Pedido pedido = new Pedido(cliente);

    pedido.agregarProducto(productoFisico, 1);
    pedido.agregarProducto(productoDigital, 1);

    // Verificamos que el total no salga 29'67€ o 0€ o -29'67€:

    double totalFinal = pedido.calcularTotal();

    assertNotEquals(totalInvalido, totalFinal,
        "El total no tendría que haber salido " + totalInvalido);
  }

  @Test
  void testExcepcionPedidoSinProductos() {

    // Cliente y pedido de prueba:

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");
    Pedido pedido = new Pedido(cliente);

    /*
     ** Verificamos que salta una excepción al calcular el total
     ** de un pedido sin productos:
     */

    assertThrows(IllegalStateException.class, pedido::calcularTotal,
        "Tendría que haber saltado una IllegalStateException al calcular el total de un pedido vacío.");
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

    ProductoFisico bufanda = new ProductoFisico("Bufanda", 10.0, 2.5, "PORTUGAL");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(bufanda, 1);

    // Verificamos que la bufanda esté agregada en la lista del pedido:

    assertTrue(pedido.getProductos().contains(bufanda), "Tendría que estar el producto 'bufanda' en el pedido.");
  }

  @Test
  void testPedidoTresEuros() { // * CP-02

    // Datos de prueba:

    ProductoDigital producto = new ProductoDigital("Monedas", 3.0, 0.1, "N/A");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 1);

    // Verificamos que el producto de tres euros esta agregado en el pedido:

    assertEquals(3.0, pedido.getProductos().get(0).getPrecio(), "El precio del producto debería haber salido 3€");

    // Probamos que el precio final salga 3€:

    assertEquals(3.0, pedido.calcularTotal(), "El precio total final del pedido debería haber salido 3€");
  }

  @Test
  void testCaracterEspecial() { // * CP-05

    // Datos de prueba:

    ProductoFisico producto = new ProductoFisico("Móvil", 1.0, 1.0, "FRANCIA");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 1);

    // Verificamos que el nombre del producto no salga "M?vil":

    assertNotEquals("M?vil", pedido.getProductos().get(0).getNombre(),
        "El nombre del pedido no debería haber salido 'M?vil'");
  }

  @Test
  void testTotalDosProductos() { // * CP-06

    // Datos de prueba:

    ProductoDigital producto1 = new ProductoDigital("Producto 1", 5.0, 0.1, "Todos los derechos reservados");
    producto1.aplicarIVA("REDUCIDO");

    ProductoDigital producto2 = new ProductoDigital("Producto 2", 6.0, 1.0, "Todos los derechos reservados");
    producto2.aplicarIVA("REDUCIDO");
    producto2.asignarDescuento(0.15);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto1, 1);
    pedido.agregarProducto(producto2, 1);

    // Verificamos que el total final no salga 11'10€ ni 0€ ni -11'10€:

    assertNotEquals(11.10, pedido.calcularTotal(), "El total del pedido no debería haber salido 11'10€");

    assertNotEquals(0, pedido.calcularTotal(), "El total del pedido no debería haber salido 0€");

    assertNotEquals(-11.10, pedido.calcularTotal(), "El total del pedido no debería haber salido -11'10€");
  }

  @Test
  void testProductoDuplicado() { // * CP-08

    // Datos de prueba:

    ProductoFisico producto = new ProductoFisico("Producto", 10.0, 3.5, "ESPAÑA");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 1);

    // Verificamos que no salga duplicado el producto de antes:

    assertEquals(1, pedido.getProductos().stream()
        .filter(p -> p.getNombre().equals("Producto"))
        .count(),
        "No debería salir el producto duplicado en la lista del pedido");
  }

  @Test
  void testEliminarProductoReduceYElimina() {

    // Datos de prueba:

    ProductoFisico producto = new ProductoFisico("Camiseta", 15.0, 0.3, "ESPAÑA");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 2);

    // Probamos a eliminar una unidad:

    pedido.eliminarProducto(producto);
    assertEquals(1, pedido.getCantidades().get(producto),
        "Tras eliminar una unidad debería quedar una más en el pedido.");

    // Probamos a eliminar la última unidad:

    pedido.eliminarProducto(producto);
    assertFalse(pedido.getProductos().contains(producto),
        "Tras eliminar la última unidad el producto no debería estar en el pedido.");
  }

  @Test
  void testExcepcionEliminarProductoInvalido() {

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");
    Pedido pedido = new Pedido(cliente);

    ProductoFisico productoExterno = new ProductoFisico("Producto", 5.0, 1.0, "ESPAÑA");
    pedido.agregarProducto(productoExterno, 1);

    // Verificamos que eliminar null lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> pedido.eliminarProducto(null),
        "Tendría que haber saltado una IllegalArgumentException al eliminar un producto null.");

    // Verificamos que eliminar un producto que no está lanza una excepción:

    ProductoFisico productoAusente = new ProductoFisico("Producto", 8.0, 0.5, "ESPAÑA");
    assertThrows(IllegalArgumentException.class,
        () -> pedido.eliminarProducto(productoAusente),
        "Tendría que haber saltado una IllegalArgumentException al eliminar un producto no presente.");
  }

  @Test
  void testAgregarMismoProductoAcumulaUnidades() {

    // Datos de prueba:

    ProductoDigital producto = new ProductoDigital("Ebook", 10.0, 1.0, "N/A");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 2);
    pedido.agregarProducto(producto, 3);

    // Verificamos que las unidades se acumulan en la misma entrada del mapa:

    assertEquals(5, pedido.getCantidades().get(producto),
        "Agregar el mismo producto dos veces debería acumular las unidades");

    // Probamos que al agregar con unidades <= 0 se lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> pedido.agregarProducto(producto, 0),
        "Tendría que haber saltado una IllegalArgumentException al agregar con 0 unidades");
  }

  @Test
  void testGetClienteAsociado() {

    // Datos de prueba:

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");
    Pedido pedido = new Pedido(cliente);

    // Verificamos que getCliente() devuelve el cliente correcto y el ID es válido:

    assertEquals(cliente, pedido.getCliente(),
        "El cliente asociado al pedido debería coincidir con el cliente creado.");
    assertTrue(pedido.getIdPedido() >= 0,
        "El ID del pedido debería ser un número no negativo.");
  }

  @Test
  void testAgregarProductoNullLanzaExcepcion() {

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");
    Pedido pedido = new Pedido(cliente);

    // Verificamos que agregar un producto null lanza una excepción:

    assertThrows(IllegalArgumentException.class,
        () -> pedido.agregarProducto(null, 1),
        "Tendría que haber saltado una IllegalArgumentException al agregar un producto null.");
  }

  @Test
  void testCalcularTotalNeto() {

    ProductoDigital producto = new ProductoDigital("Libro", 20.0, 1.0, "N/A");
    producto.asignarDescuento(0.25);

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 1);

    // Verificamos que el total neto es 15€ (sin IVA):

    assertEquals(15.0, pedido.calcularTotalNeto(),
        "El total neto de un producto de 20€ con un 25% de descuento debería ser 15€");
  }

  @Test
  void testCalcularTotalIvaConIvaGeneral() {

    ProductoDigital producto = new ProductoDigital("Software", 10.0, 1.0, "MIT");
    producto.aplicarIVA("GENERAL");

    Cliente cliente = new Cliente("Alfonso G.D.", "alfonso_gd@mail.com", 1, false, "ESPAÑA");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 1);

    // Verificamos que el total de IVA es 2.10€:

    assertEquals(2.1, pedido.calcularTotalIva(), 0.001,
        "El total de IVA de un producto de 10€ con IVA general (21%) debería ser 2.10€");
  }

  @Test
  void testCalcularTotalEnvioClienteExtranjero() {

    ProductoFisico producto = new ProductoFisico("Silla", 50.0, 5.0, "ESPAÑA");

    Cliente cliente = new Cliente("Jean Dupont", "jean@mail.com", 1, false, "PORTUGAL");

    Pedido pedido = new Pedido(cliente);
    pedido.agregarProducto(producto, 2);

    // Verificamos que el coste de envío total es 10€:

    assertEquals(10.0, pedido.calcularTotalEnvio(),
        "El total de envío para 2 unidades con cliente de PORTUGAL debería ser 10€");
  }
}
