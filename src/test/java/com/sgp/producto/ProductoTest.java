package com.sgp.producto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProductoTest {

    @Test
    void testCreacionProductoBase() { // * CP-10 se incluye dentro pero sin AssertFalse.

        String nombre1 = "";
        String nombre2 = null;
        double precio1 = 0;
        double precio2 = -1;

        /*
         * Verificamos que saltan excepciones al crear
         * productos con datos base inválidos:
         */

        assertThrows(IllegalArgumentException.class,
                () -> new ProductoDigital(nombre1, precio1, 1.0, "Todos los derechos reservados"),
                "Tendría que haber saltado una IllegalArgumentException al crear un producto con nombre y precio inválidos.");

        assertThrows(IllegalArgumentException.class,
                () -> new ProductoFisico(nombre2, precio2, 2.0, "ESPAÑA"),
                "Tendría que haber saltado una IllegalArgumentException al crear un producto con nombre y precio inválidos.");
    }

    @Test
    void testSettersProductoBase() {

        String nombre = "Monitor 4k";
        double precio = 350.0;

        // Verificamos que funcionen los setters de Producto:

        ProductoFisico producto = new ProductoFisico(nombre, precio, 12.0, "FRANCIA");

        assertThrows(IllegalArgumentException.class,
                () -> producto.setNombre(""),
                "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo nombre vacío.");

        assertThrows(IllegalArgumentException.class,
                () -> producto.setNombre(null),
                "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo nombre de valor nulo.");

        assertThrows(IllegalArgumentException.class,
                () -> producto.setPrecioBase(-1),
                "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo precio inválido.");
    }

    @Test
    void testGettersProductoBase() {
        ProductoDigital producto = new ProductoDigital("Curso Java", 49.99, 1.0, "Todos los derechos reservados");

        // Verificamos el funcionamiento de los getters:

        assertEquals("Curso Java", producto.getNombre());
        assertEquals(49.99, producto.getPrecio());
    }

    @Test
    void testSettersValidos() {
        ProductoFisico producto = new ProductoFisico("Teclado", 80.0, 0.5, "ESPAÑA");

        producto.setNombre("Teclado Mecánico");
        producto.setPrecioBase(95.0);

        // Verificamos el funcionamiento de setters válidos:

        assertEquals("Teclado Mecánico", producto.getNombre());
        assertEquals(95.0, producto.getPrecio());
    }

    @Test
    void testToStringProductoBase() {
        ProductoFisico producto = new ProductoFisico("Ratón", 25.0, 0.2, "ESPAÑA");
        String resultado = producto.toString();

        // Verificamos el funcionamiento del toString():

        assertTrue(resultado.contains("Ratón"), "toString() debería contener el nombre del producto.");
        assertTrue(resultado.contains("25"), "toString() debería contener el precio del producto.");
    }
}
