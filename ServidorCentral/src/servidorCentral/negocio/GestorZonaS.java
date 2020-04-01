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
public class GestorZonaS {
    private ConectorJdbcSingleton conector;

    public GestorZonaS() {
        conector = conector.getConexion();
    }
    
    public ArrayList<ZonaS> zonasDesdeServidorCentral() {
        ArrayList<ZonaS> zonas = new ArrayList();
        try {
            conector.conectarse();
            conector.crearConsulta("SELECT * FROM ZONA");
            while (conector.getResultado().next()) {
                ZonaS zona = new ZonaS((conector.getResultado().getString(1)), conector.getResultado().getString(2),(conector.getResultado().getString(3)));
                zonas.add(zona);
            }
            conector.desconectarse();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getErrorCode()+" "+e.getMessage());
        }
        return zonas;
    }
    
}
