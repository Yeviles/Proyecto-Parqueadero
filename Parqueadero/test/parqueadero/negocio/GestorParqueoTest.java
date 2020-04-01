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
public class GestorParqueoTest {
    
    public GestorParqueoTest() {
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
     * Test of registrarIngreso method, of class GestorParqueo.
     */
    @Test
    public void testRegistrarIngreso() {
        System.out.println("registrarIngreso");
        String veh_placa = "GVK12D";
        String bahia_id = "1";
        String fecha_ingreso = "20/01/2020";
        String hora_entrada = "08:02:55";
        GestorParqueo instance = new GestorParqueo();
        boolean expResult = true;
        boolean result = instance.registrarIngreso(veh_placa, bahia_id, fecha_ingreso, hora_entrada);
        assertEquals(expResult, result);
    }

    /**
     * Test of registrarSalida method, of class GestorParqueo.
     */
    @Test
    public void testRegistrarSalida() {
        System.out.println("registrarSalida");
        String veh_placa = "GVK12D";
        String id_bahia = "1";
        String fecha_entrada = "20/01/2020";
        String fecha_salida = "20/01/2021";
        String hora_salida = "09:02:55";
        String hora_entrada = "08:02:55";
        GestorParqueo instance = new GestorParqueo();
        boolean expResult = true;
        boolean result = instance.registrarSalida(veh_placa, id_bahia, fecha_entrada, fecha_salida, hora_salida, hora_entrada);
        assertEquals(expResult, result);
    }

    /**
     * Test of recuperarParqueo method, of class GestorParqueo.
     */
    @Test
    public void testRecuperarParqueo() {
        System.out.println("recuperarParqueo");
        int id_bahia = 1;
        GestorParqueo instance = new GestorParqueo();
        //Parqueo expResult = new Parqueo("1","GVK12D", "1", "20/01/2020", "20/01/2021", "09:02:55", "08:02:55");
        Parqueo result = instance.recuperarParqueo(id_bahia);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        if(result!=null){
            assertEquals("1", result.getIdparqueo());
            assertEquals("GVK12D", result.getVehplaca());
            assertEquals("1", result.getBahid());
            assertEquals("20/01/2020", result.getFechaingreso());
            assertEquals("20/01/2021", result.getFechasalida());
            assertEquals("09:02:55", result.getHorasalida());
            assertEquals("08:02:55", result.getHoraentrada());
        }else{
            System.out.println("No retorno");
        }
        
    }

    /**
     * Test of estaParqueado method, of class GestorParqueo.
     */
    @Test
    public void testEstaParqueado() {
        System.out.println("estaParqueado");
        String placa = "";
        GestorParqueo instance = new GestorParqueo();
        boolean expResult = false;
        boolean result = instance.estaParqueado(placa);
        assertEquals(expResult, result);
    }
    
}
