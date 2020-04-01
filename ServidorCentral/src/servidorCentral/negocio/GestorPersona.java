/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

import java.sql.SQLException;

/**
 *
 * @author Guido
 */
public class GestorPersona {

    private ConectorJdbcSingleton conector;

    public GestorPersona() {
        conector = conector.getConexion();
    }

    public boolean adicionarPersona(String id, String nombre, String apellido, String genero, String fecha_nacimiento) {
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO persona VALUES ( " + id + ", '" + nombre + "', '" + apellido + "', '" + genero + "', '" + fecha_nacimiento + "')");
            conector.desconectarse();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error desde adicionar persona: " + ex.getMessage());
            return false;
        }
    }

}
