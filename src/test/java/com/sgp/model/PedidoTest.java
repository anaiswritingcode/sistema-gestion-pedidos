package com.sgp.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
