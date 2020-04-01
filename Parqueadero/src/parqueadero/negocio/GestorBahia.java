/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import com.google.gson.Gson;
import java.util.Properties;
import mvcf.AModel;
import parqueadero.acceso.IServidorCentral;
import parqueadero.acceso.ServicioServidorCentralSocket;

/**
 *
 * @author Guido
 */
public class GestorBahia extends AModel {

    private final IServidorCentral servidor_central;
    //ConectorJdbc conector;

    public GestorBahia() {
        servidor_central = new ServicioServidorCentralSocket();
        //conector = new ConectorJdbc();
    }

    /**
     * Permite adicionar una zona en servidor central
     *
     * @param id_bahia_ab
     * @param id_zona_ab
     * @param id_persona
     * @param estado_ab
     * @return true si agrego la bahia, false si no fue agregado
     */
    public boolean adicionarBahiaEnServidorCentral(String id_bahia_ab, String id_zona_ab, String id_persona, String estado_ab) {
        return !servidor_central.agregarBahiaEnServidorCentra(id_bahia_ab, id_zona_ab, id_persona, estado_ab).equals("FRACASO");
    }

    /**
     * Actualiza los datos asociados a una bahia
     *
     * @param id_bahia_eb
     * @param id_zona_eb
     * @param id_persona_eb
     * @param estado_eb
     * @return true si actualizo la bahia, false si no fue actualizada la bahia
     */
    public boolean editarBahiaEnServidorCentral(String id_bahia_eb, String id_zona_eb, String id_persona_eb, String estado_eb) {
        return !servidor_central.editarBahiaEnServidorCentra(id_bahia_eb, id_zona_eb, id_persona_eb, estado_eb).equals("FRACASO");
    }

    /**
     * Busca las bahias en el servidor central de una zona
     *
     * @param id_zona
     * @return
     */
    public Bahia[] bahiasPorZona(int id_zona) {
        String arrayJsonSerializado = servidor_central.obtenerBahiasPorZonasDeServCentral(id_zona);
        if (!arrayJsonSerializado.equals("NO_ENCONTRADO")) {
            Bahia[] bahias = parseToBahiaXZona(arrayJsonSerializado);
            return bahias;
        } else {
            return null;
        }
    }

    /**
     * Convierte de formato json a objeto Bahia
     *
     * @param bahia objeto de tipo Bahia
     * @param arrayJsonSerializado objeto bahia en formato json
     */
    private Bahia[] parseToBahiaXZona(String arrayJsonSerializado) {
        Bahia[] bahias = new Gson().fromJson(arrayJsonSerializado, Bahia[].class);
        return bahias;
    }
    /**
     * Busca el estado de una zona en el servidor central
     * @param id_bahia identificado de la bahia
     * @param id_zona identificador de la zona a la que esta asociada la bahia
     * @return Objeto tipo Bahia, null si no lo encuentra
     */
    public Bahia estadoBahiaDesdeServidorCentral(String id_bahia, String id_zona){
        String json = servidor_central.obtenerEstadoBahiaDesdeServidor(id_bahia, id_zona);
        if(!json.equals("NO_ENCONTRADO")){
            Bahia bahia = new Bahia();
            parseToBahiaEstado(bahia, json);
            return bahia;
        }
        return null;
    }
    /**
     * Deserializa el objeto json y lo convierte en un objeto Bahia
     * @param bahia Objeto tipo Bahia 
     * @param json objeto Bahia en formato json
     */
    private void parseToBahiaEstado(Bahia bahia, String json) {
        Gson gson = new Gson();
        Properties prop = gson.fromJson(json, Properties.class);
        bahia.setBahid(Integer.parseInt(prop.getProperty("bahid")));
        bahia.setZona_id(Integer.parseInt(prop.getProperty("zona_id")));
        bahia.setPer_identificacion(Integer.parseInt(prop.getProperty("per_identificacion")));
        bahia.setBahia_estado(prop.getProperty("bahia_estado"));
    }
}
