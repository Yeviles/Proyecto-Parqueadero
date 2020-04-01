/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Guido
 */
public class GestorBahiaLocal {
    private ConectorJdbcSingleton conector;

    public GestorBahiaLocal() {
        this.conector = conector.getConexion();
    }
    
        public boolean agregarBahia(String zona_id, String persona_id) {
        boolean inserto = false;
        try {
            conector.conectarse();
            String sql = "INSERT INTO BAHIA(zonid, peridentificacion, bahestado) VALUES("+ zona_id + ", " + persona_id + ", 'LIBRE')";
            conector.actualizar(sql);
            conector.desconectarse();
            inserto = true;
        } catch (SQLException | ClassNotFoundException ex ) {
            System.out.println("Error desde agregarBahia(serv): " + ex.getMessage());
        }
        return inserto;
    }

    public boolean editarBahia(int id_bahia_eb, int id_zona_eb, int id_persona_eb, String estado_zona_eb) {
        try {
            conector.conectarse();
            String sql = "UPDATE BAHIA SET PERIDENTIFICACION = " + id_persona_eb + ", BAHESTADO = '" + estado_zona_eb + "' WHERE BAHID = " + id_bahia_eb + " AND  ZONID = " + id_zona_eb;
            conector.actualizar(sql);
            conector.desconectarse();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error desde editarBahia(Serv): " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Bahia> consultarBahiasPorZonaS(int id_zona) {
        ArrayList<Bahia> array_bahias = new ArrayList<>();
        try {
            String sql = "SELECT bahid, zonid, peridentificacion, bahestado FROM bahia where zonid = " + id_zona;
           
            conector.conectarse();
            conector.crearConsulta(sql);
            while (conector.getResultado().next()) {
                Bahia bahia = new Bahia(Integer.parseInt(conector.getResultado().getString(1)), Integer.parseInt(conector.getResultado().getString(2)), Integer.parseInt(conector.getResultado().getString(3)), conector.getResultado().getString(4));
                array_bahias.add(bahia);
            }
            conector.desconectarse();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error desde consultarBahiasPorZona(parqueadero) " + ex.getMessage());
        }
        return array_bahias;
    }
    
    public Bahia estadoZona(String id_bahia_e, String id_zona_e){
        Bahia bahia = null;
        try {
            String sql = "SELECT * FROM bahia where bahid = " + id_bahia_e+" AND zonid = "+id_zona_e;
            conector.conectarse();
            conector.crearConsulta(sql);
            while (conector.getResultado().next()) {
                bahia = new Bahia(Integer.parseInt(conector.getResultado().getString(1)), Integer.parseInt(conector.getResultado().getString(2)), Integer.parseInt(conector.getResultado().getString(3)), conector.getResultado().getString(4));
            }
            conector.desconectarse();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error desde estadoZona(Cli): " + ex.getMessage());
        }
        return bahia;
    }
}
