package com.sgp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void testCreacionCliente() {

        String nombre1 = null;
        String nombre2 = "";
        String nombre3 = "Alfonso";
        String correo1 = null;
        String correo2 = "";
        String correo3 = "alfonso.com";

        // Verificamos que salta una excepción al crear clientes con datos inválidos:

        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(nombre1, correo3, 1, false, "ESPAÑA"),
                "Tendría que haber saltado una IllegalArgumentException al crear un cliente con nombre y correo inválidos.");

        assertThrows(NullPointerException.class,
                () -> new Cliente(nombre3, correo1, 1, false, "ESPAÑA"),
                "Tendría que haber saltado una NullPointerException al crear un cliente con un correo de valor nulo.");

        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(nombre2, correo2, 1, false, "ESPAÑA"),
                "Tendría que haber saltado una IllegalArgumentException al crear un cliente con nombre y correo vacíos.");
    }

    @Test
    void testGettersCliente() {

        String nombre = "Alfonso";
        String correo = "alfonso@mail.com";

        // Verificamos que funcionen los getters de Cliente:

        Cliente cliente = new Cliente(nombre, correo, 1, false, "ESPAÑA");
        cliente.agregarDireccion("Calle Abeto S/N");

        assertTrue(cliente.getIdCliente() >= 0);
        assertEquals(nombre, cliente.getNombreCompleto());
        assertEquals(correo, cliente.getCorreo());
        assertFalse(cliente.getDirecciones() == null || cliente.getDirecciones().isEmpty());
    }

    @Test
    void testSetterCliente() {

        String nombre = "Alfonso";
        String correo = "alfonso@mail.com";

        // Verificamos que funcione el setter de Cliente:

        Cliente cliente = new Cliente(nombre, correo, 1, false, "ESPAÑA");

        assertThrows(IllegalArgumentException.class,
                () -> cliente.setCorreo("alfonso2.com"),
                "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo correo inválido.");

        assertThrows(IllegalArgumentException.class,
                () -> cliente.setCorreo(""),
                "Tendría que haber saltado una IllegalArgumentException al establecer un nuevo correo vacío.");

        assertThrows(NullPointerException.class,
                () -> cliente.setCorreo(null),
                "Tendría que haber saltado una NullPointerException al establecer un nuevo correo de valor nulo.");
    }

    @Test
    void testCreacionClienteAnnosYPaisInvalidos() {

        /*
         ** Verificamos que salta una excepción con años de antigüedad negativos
         ** o país inválido:
         */

        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", "ana@mail.com", -1, false, "ESPAÑA"),
                "Tendría que haber saltado una IllegalArgumentException por años de antigüedad negativos.");

        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", "ana@mail.com", 1, false, null),
                "Tendría que haber saltado una IllegalArgumentException por país nulo.");

        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", "ana@mail.com", 1, false, ""),
                "Tendría que haber saltado una IllegalArgumentException por país vacío.");
    }

    @Test
    void testGettersMissingYSetterAnnosAntiguedad() {

        Cliente cliente = new Cliente("Ana", "ana@mail.com", 3, true, "PORTUGAL");

        // Verificamos el funcionamiento de los getters:

        assertEquals(3, cliente.getAnnosAntiguedad());
        assertTrue(cliente.getEsVip());
        assertEquals("PORTUGAL", cliente.getPais());

        // Verificamos el funcionamiento del setter:

        assertThrows(IllegalArgumentException.class,
                () -> cliente.setAnnosAntiguedad(-1),
                "Tendría que haber saltado una IllegalArgumentException por años de antigüedad negativos.");
    }

    @Test
    void testGestionDirecciones() {

        Cliente cliente = new Cliente("Ana", "ana@mail.com", 1, false, "ESPAÑA");
        cliente.agregarDireccion("Calle Mayor 1");
        cliente.agregarDireccion("Calle Menor 2");

        // Verificamos el modificar dirección válida:

        cliente.modificarDireccion(0, "Calle Mayor 10");
        assertEquals("Calle Mayor 10", cliente.getDirecciones().get(0));

        // Verificamos el eliminar dirección existente:

        cliente.eliminarDireccion("Calle Menor 2");
        assertFalse(cliente.getDirecciones().contains("Calle Menor 2"),
                "La dirección eliminada no debería estar en la lista.");

        // Verificamos un índice fuera de rango:

        assertThrows(IndexOutOfBoundsException.class,
                () -> cliente.modificarDireccion(-1, "Calle X"),
                "Tendría que haber saltado una IndexOutOfBoundsException con índice inválido.");
    }

    @Test
    void testSetterEsVipYPais() {

        Cliente cliente = new Cliente("Ana", "ana@mail.com", 1, false, "ESPAÑA");

        // Verificamos el cambio de estado VIP:

        assertFalse(cliente.getEsVip());
        cliente.setEsVip(true);
        assertTrue(cliente.getEsVip());
        cliente.setEsVip(false);
        assertFalse(cliente.getEsVip());

        // Verificamos el cambio de país:

        assertEquals("ESPAÑA", cliente.getPais());
        cliente.setPais("FRANCIA");
        assertEquals("FRANCIA", cliente.getPais());
    }

    @Test
    void testAgregarYEliminarDireccionesInvalidas() {

        Cliente cliente = new Cliente("Ana", "ana@mail.com", 1, false, "ESPAÑA");

        // Verificamos que agregar una dirección null o vacía lanza excepción:

        assertThrows(IllegalArgumentException.class,
                () -> cliente.agregarDireccion(null),
                "Tendría que haber saltado una IllegalArgumentException al agregar una dirección nula.");

        assertThrows(IllegalArgumentException.class,
                () -> cliente.agregarDireccion(""),
                "Tendría que haber saltado una IllegalArgumentException al agregar una dirección vacía.");

        // Verificamos que eliminar una dirección inexistente lanza excepción:

        assertThrows(IllegalArgumentException.class,
                () -> cliente.eliminarDireccion("Dirección Inexistente"),
                "Tendría que haber saltado una IllegalArgumentException al eliminar una dirección que no existe.");
    }

    @Test
    void testSettersCorreoYAnnos() {

        Cliente cliente = new Cliente("Ana", "ana@mail.com", 3, false, "ESPAÑA");

        // Verificamos que setCorreo actualiza el valor correctamente:

        cliente.setCorreo("nuevo@mail.com");
        assertEquals("nuevo@mail.com", cliente.getCorreo());

        // Verificamos que setAnnosAntiguedad actualiza el valor correctamente:

        cliente.setAnnosAntiguedad(10);
        assertEquals(10, cliente.getAnnosAntiguedad());

        // Verificamos el formato del toString():

        String resultado = cliente.toString();
        assertTrue(resultado.contains("Ana"), "El toString() debe incluir el nombre del cliente.");
        assertTrue(resultado.contains("nuevo@mail.com"), "El toString() debe incluir el correo actualizado.");
    }
}
