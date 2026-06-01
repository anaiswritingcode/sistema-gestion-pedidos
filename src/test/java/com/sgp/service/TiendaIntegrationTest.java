package com.sgp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sgp.model.Cliente;
import com.sgp.model.Pedido;
import com.sgp.producto.ProductoDigital;
import com.sgp.producto.ProductoFisico;

class TiendaIntegrationTest {

    private Tienda tienda;
    private ProductoDigital producto;

    @BeforeEach
    void setUp() {

        // Nueva instancia de Tienda:
        tienda = new Tienda();

        // ProductoDigital de prueba:
        producto = new ProductoDigital("Producto 1", 100.0, 500.0, "Todos los derechos reservados");
        producto.aplicarIVA("GENERAL");
    }

    @Test
    void testVentaClienteSinDescuento() { // * CP-15

        Cliente cliente = new Cliente("Sam GM", "sam_gm@mail.com", 0, false, "ESPAÑA");

        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(producto, 1);

        Factura factura = tienda.realizarVenta(cliente, pedido);

        /*
         ** La factura debe tener un código válido y un precio total
         ** de 121€ (sin descuento):
         */

        assertTrue(factura.getCodigoFactura().startsWith("FAC-"),
                "El código de factura debería empezar por 'FAC-'");
        assertEquals(121.0, factura.getTotalFinal(), 0.01,
                "El total final sin descuento debería ser 121€ (100€ neto + 21€ IVA)");
    }

    @Test
    void testVentaClienteVip() { // * CP-16

        Cliente clienteVip = new Cliente("Fran MF", "fran_mf@mail.com", 0, true, "ESPAÑA");

        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(producto, 1);

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        // Verificamos que el cliente VIP recibe un 20% de descuento:

        assertEquals(96.8, factura.getTotalFinal(), 0.01,
                "El total final con descuento VIP (20%) debería ser 96.80€");
        assertEquals(0.20, factura.getDescuentoAplicado(), 0.001,
                "El descuento aplicado debería ser 0.20 (20%)");
    }

    @Test
    void testVentaClienteFidelidadExtendida() { // * CP-17

        Cliente clienteFiel = new Cliente("María G", "mariag@mail.com", 5, false, "ESPAÑA");

        Pedido pedido = new Pedido(clienteFiel);
        pedido.agregarProducto(producto, 1);

        Factura factura = tienda.realizarVenta(clienteFiel, pedido);

        // Verificamos que los 5 años de antigüedad dan un 10% de descuento:

        assertEquals(108.9, factura.getTotalFinal(), 0.01,
                "El total final con fidelidad extendida (10%) debería ser 108.90€");
    }

    @Test
    void testVentaClienteVipYFidelidadAcumulados() { // * CP-18 (REGRESIÓN)

        Cliente clienteVipFiel = new Cliente("Carla CS", "carla_cs@mail.com", 5, true, "ESPAÑA");

        Pedido pedido = new Pedido(clienteVipFiel);
        pedido.agregarProducto(producto, 1);

        Factura factura = tienda.realizarVenta(clienteVipFiel, pedido);

        // Verificamos que VIP (20%) + fidelidad ≥5 años (10%) dan 30% acumulado:

        assertEquals(0.30, factura.getDescuentoAplicado(), 0.001,
                "El descuento acumulado debería ser 0.30 (30%)");
        assertEquals(84.7, factura.getTotalFinal(), 0.01,
                "El total final con descuento acumulado (30%) debería ser 84.70€");
    }

    @Test
    void testVentaConProductoFisicoEnvioInternacional() { // * CP-19

        ProductoFisico libro = new ProductoFisico("Libro", 50.0, 1.5, "ESPAÑA");

        Cliente clienteFrancia = new Cliente("Luna DP", "luna_dp@mail.com", 0, false, "FRANCIA");

        Pedido pedido = new Pedido(clienteFrancia);
        pedido.agregarProducto(libro, 2);

        Factura factura = tienda.realizarVenta(clienteFrancia, pedido);

        /*
         ** Verificamos que 2 unidades de un ProductoFisico de 50€ para un cliente
         ** en Francia da un coste de envío de 10€ y un total de 110€:
         */

        assertEquals(10.0, factura.getTotalEnvio(), 0.01,
                "El coste de envío total a Francia (2 unidades) debería ser 10€");
        assertEquals(110.0, factura.getTotalFinal(), 0.01,
                "El total final debería ser 110€ (100€ neto + 0€ IVA + 10€ envío)");
    }

    @Test
    void testVentaClienteNullLanzaExcepcion() { // * CP-20 (REGRESIÓN)

        Cliente cliente = new Cliente("Test", "test@mail.com", 0, false, "ESPAÑA");

        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(producto, 1);

        // Verificamos que salta una excepción con un cliente null:

        assertThrows(IllegalArgumentException.class,
                () -> tienda.realizarVenta(null, pedido),
                "Debería lanzarse una IllegalArgumentException al pasar un cliente null.");
    }

    @Test
    void testVentaPedidoNullLanzaExcepcion() { // * CP-21 (REGRESIÓN)

        Cliente cliente = new Cliente("Test", "test@mail.com", 0, false, "ESPAÑA");

        // Verificamos que salta una excepción con un pedido null:

        assertThrows(IllegalArgumentException.class,
                () -> tienda.realizarVenta(cliente, null),
                "Debería lanzarse una IllegalArgumentException al pasar un pedido null.");
    }

    @Test
    void testVentaPedidoVacioLanzaExcepcion() { // * CP-22 (REGRESIÓN)

        Cliente cliente = new Cliente("Test", "test@mail.com", 0, false, "ESPAÑA");
        Pedido pedidoVacio = new Pedido(cliente);

        /*
         ** Tienda debe rechazar pedidos vacíos
         ** antes de llegar a Pedido.calcularTotalNeto():
         */

        assertThrows(IllegalArgumentException.class,
                () -> tienda.realizarVenta(cliente, pedidoVacio),
                "Debería lanzarse una IllegalArgumentException al intentar vender un pedido vacío.");
    }

    @Test
    void testCalcularDescuentoClienteNullLanzaExcepcion() { // * CP-23 (REGRESIÓN)

        /*
         ** Verificamos que salta una excepción al calcular
         ** el descuento de un cliente null:
         */

        assertThrows(IllegalArgumentException.class,
                () -> tienda.calcularDescuentoCliente(null),
                "Debería lanzarse una IllegalArgumentException al calcular el descuento de un cliente null.");
    }

    @Test
    void testTotalFinalClienteVipEsDiferenteAlSubtotalBruto() { // * CP-24 (REGRESIÓN)

        Cliente clienteVip = new Cliente("Vip Test", "vip_test@mail.com", 0, true, "ESPAÑA");
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(producto, 1);

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        double subtotalBruto = factura.getTotalNeto() + factura.getTotalIva() + factura.getTotalEnvio();

        // Verificamos que un cliente VIP debe pagar menos que el subtotal bruto:

        assertNotEquals(subtotalBruto, factura.getTotalFinal(),
                "El total final de un cliente VIP no debería ser igual al subtotal bruto (el descuento debe aplicarse)");
    }
}
