/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcf.AModel;
import parqueadero.acceso.IServidorCentral;
import parqueadero.acceso.ServicioServidorCentralSocket;

/**
 *
 * @author Guido
 */
public class GestorZona extends AModel {
    private ConectorJdbcSingleton con;
    private final IServidorCentral servidorCentral;

    public GestorZona() {
        servidorCentral = new ServicioServidorCentralSocket();
        con = con.getConexion();
    }

    /**
     * Recupera de servidor central todas las zonas registradas
     *
     * @return Un array de tipo Zona, null si no existen registros
     */
    public Zona[] zonasDeServidorCentral() {
        String json = servidorCentral.obtenerZonasDesdeServidorCentral();
        if (!json.equals("NO_ENCONTRADO")) {
            return deserializarZonasDeServidorCentral(json);
        } else {
            return null;
        }
    }

    public ZonaA[] zonasDeServidorCentralA() {
        String json = servidorCentral.obtenerZonasDesdeServidorCentral();
        if (!json.equals("NO_ENCONTRADO")) {
            return deserializarZonasDeServidorCentralA(json);
        } else {
            return null;
        }
    }
    /**
     * Deserializa el array de objetos json y lo convierte en un array de
     * objetos Zona
     *
     * @param Array_serializado array de zonas en formato json
     * @return array de zonas de tipo zona
     */
    private Zona[] deserializarZonasDeServidorCentral(String arrayJsonSerializado) {
        return new Gson().fromJson(arrayJsonSerializado, Zona[].class);
    }
    
    private ZonaA[] deserializarZonasDeServidorCentralA(String arrayJsonSerializado) {
        return new Gson().fromJson(arrayJsonSerializado, ZonaA[].class);
    }
    
    public boolean registrarZona(String codigo, String nombre, String cant){
        try {
            con.conectarse();
            con.actualizar("INSERT into Zona (zonId,zonNombre,zonCantPuestos) values("+codigo+", '"+nombre+"', "+cant+")");
            con.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorZona.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}
