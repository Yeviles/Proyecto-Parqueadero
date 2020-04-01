/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guido
 */
public class GestorPersonaTest {
    
    public GestorPersonaTest() {
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
     * Test of adicionarPersona method, of class GestorPersona.
     */
    @Test
    public void testAdicionarPersona() {
        System.out.println("adicionarPersona");
        String id = "2628";
        String nombre = "RAMON";
        String apellido = "DAZA";
        String genero = "MASCULINO";
        String fecha_nacimiento = "12/03/1994";
        GestorPersona instance = new GestorPersona();
        boolean expResult = true;
        boolean result = instance.adicionarPersona(id, nombre, apellido, genero, fecha_nacimiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of existePersona method, of class GestorPersona.
     */
    @Test
    public void testExistePersona() {
        System.out.println("existePersona");
        String identificacion = "2628";
        GestorPersona instance = new GestorPersona();
        Persona expResult = new Persona("2628", "RAMON", "DAZA", "MASCULINO", "12/03/1994");
        Persona result = instance.existePersona(identificacion);
        //assertEquals(expResult, result);
        
        assertEquals("2628", result.getPeridentificacion());
        assertEquals("RAMON", result.getPernombre());
        assertEquals("DAZA", result.getPerapellido());
        assertEquals("MASCULINO", result.getPergenero());
        assertEquals("12/03/1994", result.getPerfechaNacimiento());
        
    }
    
}
