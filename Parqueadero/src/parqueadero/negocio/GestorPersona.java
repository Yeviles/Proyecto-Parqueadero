/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guido
 */
public class GestorPersona {
    private ConectorJdbcSingleton conector;
    public GestorPersona(){
        conector = conector.getConexion();
    }
    
    public boolean adicionarPersona(String id, String nombre, String apellido, String genero, String fecha_nacimiento){
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO persona VALUES ( "+id+", '"+nombre+"', '"+apellido+"', '"+genero+"', '"+fecha_nacimiento+"')");
            conector.desconectarse();
            return true;
        } catch (SQLException ex ) {
            System.out.println("Error desde adicionar persona: "+ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestorPersona.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Persona existePersona(String identificacion) {

        Persona per = null;
        try {
            conector.conectarse();
            conector.crearConsulta("Select * from persona where peridentificacion = " + identificacion + " ");
            while (conector.getResultado().next()) {
                per = new Persona(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3), conector.getResultado().getString(4), conector.getResultado().getString(5));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return per;
    }
    
}
