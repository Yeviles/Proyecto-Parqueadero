
package parqueadero.negocio;

import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GestorVehiculosNGTest {
    
    public GestorVehiculosNGTest() {
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
     * Test of consultarVehiculos method, of class GestorVehiculos.
     */
//    @Test
//    public void testConsultarVehiculos() throws Exception {
//        System.out.println("consultarVehiculos");
//        GestorVehiculos instance = new GestorVehiculos();
//        ArrayList expResult = null;
//        ArrayList result = instance.consultarVehiculos();
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of buscarVehiculoServidorCentral method, of class GestorVehiculos.
     */
//    @Test
//    public void testBuscarVehiculoServidorCentral() {
//        System.out.println("buscarVehiculoServidorCentral");
//        String id = "";
//        GestorVehiculos instance = new GestorVehiculos();
//        Vehiculo expResult = null;
//        Vehiculo result = instance.buscarVehiculoServidorCentral(id);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of buscarVehiculosXPersona method, of class GestorVehiculos.
     */
    @Test
    public void testBuscarVehiculosXPersona() {
        System.out.println("buscarVehiculosXPersona");
        String Id = "22222";
        GestorVehiculos instance = new GestorVehiculos();
        
        Vehiculo[] result = instance.buscarVehiculosXPersona(Id);
        //assertEquals(result, expResult);
        assertEquals("22222", result[0].getIdentificacion());
        assertEquals("VYT21X", result[0].getPlaca());
        assertEquals("CHEVROLET", result[0].getMarca());
        assertEquals("Automovil", result[0].getTipo());
    }
    
}
