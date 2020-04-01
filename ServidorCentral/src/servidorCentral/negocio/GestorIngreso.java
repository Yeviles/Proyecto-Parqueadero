/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guido
 */
public class GestorIngreso {

    ConectorJdbcSingleton conector;

    public GestorIngreso() {
        conector = conector.getConexion();
    }

    public boolean registrarIngreso(String veh_placa, String bahia_id, String fecha_ingreso) {
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO ingreso VALUES ('" + veh_placa + "', " + bahia_id + ", '" + fecha_ingreso + "', null )");
            conector.desconectarse();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GestorIngreso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean registrarSalida(String veh_placa, String fecha_salida) {
        try {
            conector.conectarse();
            conector.actualizar("UPDATE ingreso SET ingfechasalida = '" + fecha_salida+ "') WHERE vehplaca = '"+veh_placa+"'");
            conector.desconectarse();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GestorIngreso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
