
package parqueadero.negocio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GestorRolTest {
    
    public GestorRolTest() {
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
     * Test of obtenerRolPerDeServidorCentral method, of class GestorRol.
     */
    @Test
    public void testObtenerRolPerDeServidorCentral() {
        System.out.println("obtenerRolPerDeServidorCentral");
        String id = "33333";
        GestorRol instance = new GestorRol();
        Rol result = instance.obtenerRolPerDeServidorCentral(id);
        assertEquals("1003", result.getRol_id());
        assertEquals("33333", result.getRol_identificacion());
        assertEquals("Estudiante", result.getRol_nom());
    }
    
}
