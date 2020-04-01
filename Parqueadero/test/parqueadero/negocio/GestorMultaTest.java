/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 *
 * @author Guido
 */
public class GestorMultaTest {
    
    public GestorMultaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of agregarMulta method, of class GestorMulta.
     */
    @Test
    public void testAgregarMulta() {
        System.out.println("agregarMulta");
        String id_vig = "11111";
        String desc_mul = "Multa 2";
        String fecha_mul = "03/03/2020";
        String foto_mul = "No tiene";
        String placa = "GVK12D";
        GestorMulta instance = new GestorMulta();
        boolean expResult = true;
        boolean result = instance.agregarMulta(id_vig, desc_mul, fecha_mul, foto_mul, placa);
        assertEquals(expResult, result);
    }

    /**
     * Test of consultarMultasPorPlaca method, of class GestorMulta.
     */

    /**
     * Test of cantidadMultasXplaca method, of class GestorMulta.
     */
    @Test
    public void testCantidadMultasXplaca() {
        System.out.println("cantidadMultasXplaca");
        String placa = "GVK12D";
        GestorMulta instance = new GestorMulta();
        int expResult = instance.cantidadMultasXplaca(placa);
        int result = instance.cantidadMultasXplaca(placa);
        assertEquals(expResult, result);
    }
    
}
