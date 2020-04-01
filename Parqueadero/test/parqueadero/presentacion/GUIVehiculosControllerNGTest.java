/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.presentacion;

import java.awt.event.ActionEvent;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import parqueadero.negocio.Vehiculo;

/**
 *
 * @author Guido
 */
public class GUIVehiculosControllerNGTest {
    
    public GUIVehiculosControllerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of actualizar method, of class GUIVehiculosController.
     */
    @Test
    public void testActualizar() {
        System.out.println("actualizar");
        ActionEvent e = null;
        GUIVehiculosController instance = null;
        instance.actualizar(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarVehiculosXPersona method, of class GUIVehiculosController.
     */
    Vehiculo[] ovj;
    @Test
    public void testBuscarVehiculosXPersona() {
        System.out.println("buscarVehiculosXPersona");
        String id = "1";
        GUIVehiculosController instance = null;
        instance.buscarVehiculosXPersona(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
