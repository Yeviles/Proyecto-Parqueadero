/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Guido
 */
public class GestorBahiaS {

    private ConectorJdbcSingleton conector;

    public GestorBahiaS() {
        //Se hace usa la instancia que se creo en el patron.
        conector = conector.getConexion();
    }

    public boolean agregarBahia(int id, int zona_id, int persona_id, String estado) {
        boolean inserto = false;
        try {
            conector.conectarse();
            String sql = "INSERT INTO BAHIA(zonid, peridentificacion, bahestado) VALUES(" + id + ", " + zona_id + ", " + persona_id + ", '" + estado + "')";
            conector.actualizar(sql);
            conector.desconectarse();
            inserto = true;
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
            System.out.println("Error desde editarBahia(Serv): " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<BahiaS> consultarBahiasPorZonaS(int id_zona) {
        ArrayList<BahiaS> array_bahias = new ArrayList<>();
        try {
            String sql = "SELECT bahid, zonid, peridentificacion, bahestado FROM bahia where zonid = " + id_zona;
            
            conector.conectarse();
            conector.crearConsulta(sql);
            while (conector.getResultado().next()) {
                BahiaS bahia = new BahiaS(Integer.parseInt(conector.getResultado().getString(1)), Integer.parseInt(conector.getResultado().getString(2)), Integer.parseInt(conector.getResultado().getString(3)), conector.getResultado().getString(4));
                array_bahias.add(bahia);
            }
            conector.desconectarse();
        } catch (SQLException ex) {
            System.out.println("Error desde consultarBahiasPorZonaS " + ex.getMessage());
        }
        return array_bahias;
    }
    
    public BahiaS estadoZona(String id_bahia_e, String id_zona_e){
        BahiaS bahia = null;
        try {
            String sql = "SELECT * FROM bahia where bahid = " + id_bahia_e+" AND zonid = "+id_zona_e;
            conector.conectarse();
            conector.crearConsulta(sql);
            while (conector.getResultado().next()) {
                bahia = new BahiaS(Integer.parseInt(conector.getResultado().getString(1)), Integer.parseInt(conector.getResultado().getString(2)), Integer.parseInt(conector.getResultado().getString(3)), conector.getResultado().getString(4));
            }
            conector.desconectarse();
        } catch (SQLException ex) {
            System.out.println("Error desde estadoZona(serv): " + ex.getMessage());
        }
        return bahia;
    }
}
