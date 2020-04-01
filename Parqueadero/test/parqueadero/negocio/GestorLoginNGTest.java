
package parqueadero.negocio;

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
public class GestorLoginNGTest {
    
    public GestorLoginNGTest() {
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
     * Test of buscarLogin method, of class GestorLogin.
     */
    @Test
    public void testBuscarLogin() {
        System.out.println("buscarLogin");
        int id = 1010;
        String pass = "123";
        GestorLogin instance = new GestorLogin();
        
//        Login expResult = null;
//        Login result = instance.buscarLogin(id, pass);
//        assertEquals(result, expResult);
        
        Login result = instance.buscarLogin(id, pass);
        
        assertEquals(id, result.getId());
        assertEquals("Juan", result.getUsuario());
        assertEquals("123", result.getContrasenia());
    }
}
