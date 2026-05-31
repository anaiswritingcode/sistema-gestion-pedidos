package com.sgp.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FacturaTest {

    @Test
    void testCreacionFacturaValida() {

        Factura factura = new Factura(100.0, 21.0, 5.0, 0.0);

        /*
         ** Verificamos que los getters devuelven los valores con los que
         ** se creó la factura:
         */

        assertEquals(100.0, factura.getTotalNeto(),
                "El total neto debería ser 100.0");
        assertEquals(21.0, factura.getTotalIva(),
                "El total IVA debería ser 21.0");
        assertEquals(5.0, factura.getTotalEnvio(),
                "El total envío debería ser 5.0");
        assertEquals(0.0, factura.getDescuentoAplicado(),
                "El descuento aplicado debería ser 0.0");
    }

    @Test
    void testTotalFinalSinDescuento() {

        Factura factura = new Factura(100.0, 21.0, 5.0, 0.0);

        /*
         ** Verificamos que el total final sea la suma de neto + IVA + envío
         ** cuando no hay descuento:
         */

        assertEquals(126.0, factura.getTotalFinal(),
                "El total final sin descuento debería ser 126.0");
    }

    @Test
    void testTotalFinalConDescuento() {

        Factura factura = new Factura(100.0, 21.0, 5.0, 0.10);

        /*
         ** Verificamos que el descuento del 10% se aplica correctamente
         ** sobre el subtotal:
         */

        assertEquals(113.40, factura.getTotalFinal(),
                "El total final con un 10% de descuento sobre 126.0 debería ser 113.40");
    }

    @Test
    void testCodigoFacturaFormato() {

        Factura factura = new Factura(50.0, 10.5, 0.0, 0.0);

        // Verificamos que el código de factura sigue el formato "FAC-XXXX":

        assertTrue(factura.getCodigoFactura().matches("FAC-\\d{4}"),
                "El código de factura debería tener el formato 'FAC-XXXX'");
    }

    @Test
    void testFechaEmision() {

        LocalDateTime antes = LocalDateTime.now();
        Factura factura = new Factura(200.0, 42.0, 10.0, 0.05);
        LocalDateTime despues = LocalDateTime.now();

        /*
         ** Verificamos que la fecha de emisión no sea nula
         ** ni esté fuera del intervalo de creación:
         */

        assertNotNull(factura.getFechaEmision(),
                "La fecha de emisión no debería ser nula.");
        assertFalse(factura.getFechaEmision().isBefore(antes),
                "La fecha de emisión no debería ser anterior al momento de creación.");
        assertFalse(factura.getFechaEmision().isAfter(despues),
                "La fecha de emisión no debería ser posterior al momento de creación.");
    }

    @Test
    void testExcepcionesParametrosInvalidos() {

        /*
         ** Verificamos que se lanzan excepciones con valores negativos
         ** o descuento fuera de rango:
         */

        assertThrows(IllegalArgumentException.class,
                () -> new Factura(-1.0, 21.0, 5.0, 0.0),
                "Tendría que haber saltado una IllegalArgumentException por total neto negativo.");

        assertThrows(IllegalArgumentException.class,
                () -> new Factura(100.0, -1.0, 5.0, 0.0),
                "Tendría que haber saltado una IllegalArgumentException por total IVA negativo.");

        assertThrows(IllegalArgumentException.class,
                () -> new Factura(100.0, 21.0, -1.0, 0.0),
                "Tendría que haber saltado una IllegalArgumentException por total envío negativo.");

        assertThrows(IllegalArgumentException.class,
                () -> new Factura(100.0, 21.0, 5.0, 1.5),
                "Tendría que haber saltado una IllegalArgumentException por descuento mayor que 1.");

        assertThrows(IllegalArgumentException.class,
                () -> new Factura(100.0, 21.0, 5.0, -0.1),
                "Tendría que haber saltado una IllegalArgumentException por descuento negativo.");
    }
}
