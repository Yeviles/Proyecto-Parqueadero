package parqueadero.negocio;

import parqueadero.acceso.ServicioServidorCentralSocket;
import mvcf.AModel;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Properties;
import parqueadero.acceso.IServidorCentral;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa el modelo (Observable) de datos Cuando hay cambios en el estado,
 * notifica a todas sus vistas (observadores)
 *
 * @author Guido,Maria, Yennyfer
 */
public class GestorVehiculos extends AModel {

    private final IServidorCentral servidorCentral;
    private ConectorJdbcSingleton conector;

    /**
     * Constructor por defecto.
     */
    public GestorVehiculos() {
        servidorCentral = new ServicioServidorCentralSocket();//aquimeconectoconservidorcentral
        conector = conector.getConexion();
    }
    
    public Vehiculo existeVehiculo(String placa){
        Vehiculo vh = null;
        try {
            conector.conectarse();
            conector.crearConsulta("Select * from vehiculo where vehplaca = '"+placa+"' ");
            while(conector.getResultado().next()){
                vh = new Vehiculo(conector.getResultado().getString(1), conector.getResultado().getString(2) , conector.getResultado().getString(3), conector.getResultado().getString(4));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vh;
    }
    
    public boolean adicionarVehiculo(String placa, String identificacion, String marca, String tipo){
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO vehiculo VALUES ('"+placa+"', "+identificacion+", '"+marca+"', '"+tipo+"' )");
            conector.desconectarse();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(GestorVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problemas al registrar el nuevo vehiculo "+ex.getMessage());
            return false;
        }
        
    }
    
    public boolean asociarVehiculo(String identificacion, String placa){
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO vehiculosasociados(peridentificacion, vehplaca) VALUES ("+identificacion+", '"+placa+"' )");
            conector.desconectarse();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(GestorVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problemas al asociar vehiculo "+ex.getMessage());
            return false;
        }
        
    }
    
    /**
     * Obtiene de la base de datos todos los Vehiculos.
     *
     * @return lista de tipo Vehiculo.
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public ArrayList<Vehiculo> consultarVehiculos() throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("SELECT * FROM vehiculo");
        ArrayList<Vehiculo> vehiculos = new ArrayList();
        /*
        while (conector.getResultado().next()) {
            Vehiculo veh = new Vehiculo(conector.getResultado().getString("vehplaca"), conector.getResultado().getString("vehmodelo"), conector.getResultado().getString("vehmarca"), conector.getResultado().getString("vehtipo"));
            vehiculos.add(veh);
        }
        conector.desconectarse();
         */
        return vehiculos;

    }

    /**
     * Busca un Vehiculo en el servidor remoto de la registraduría
     *
     * @param id identificacion del Usuario.
     * @return Objeto tipo Vehiculo, null si no lo encuentra
     */
    public Vehiculo buscarVehiculoServidorCentral(String id) {
        //Obtiene el objeto json serializado al servidor de la registraduria
        String json = servidorCentral.obtenerVehiculoDelServidorCentral(id);
        if (!json.equals("NO_ENCONTRADO")) {
            //Lo encontró
            Vehiculo cliente = new Vehiculo();
            parseToVehiculo(cliente, json);
            return cliente;
        }
        return null;
    }

    /**
     * Deserializa el objeto json y lo convierte en un objeto Vehiculo.
     *
     * @param vehiculo Objeto tipo Vehiculo
     * @param json objeto cliente en formato json
     */
    private void parseToVehiculo(Vehiculo vehiculo, String json) {//Sirve solo para un cliente
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        vehiculo.setPlaca(properties.getProperty("vehplaca"));
        vehiculo.setIdentificacion(properties.getProperty("id"));
        vehiculo.setMarca(properties.getProperty("vehmarca"));
        vehiculo.setTipo(properties.getProperty("vehtipo"));
    }

    public boolean agregarVehiculo(String placa_veh, int id_propietario, String marca_veh, String tipo_veh) {
        String json = servidorCentral.agregarVehiculoEnServidorCentral(placa_veh, id_propietario, marca_veh, tipo_veh);
        return !json.equals("FRACASO");
    }

    /**
     * Busca los vehiculos de una persona cuya identificacion llega por
     * parametro.
     *
     * @param Id identificacion de la persona(Usurio).
     * @return lista de vehiculos de la persona.
     */
    public Vehiculo[] buscarVehiculosXPersona(String Id) {
        String json = servidorCentral.obtenerListaVehiculosXPersonaDelServidorCentral(Id);
        //OK System.out.println("LLega a buscar: "+Id);
        if (!json.equals("NO_ENCONTRADO")) {
            Vehiculo[] vehiculos = deserializarVehiculosXPersona(json);
            return vehiculos;
        } else {
            return null;
        }
    }

    /**
     * Dada una información en formato json lo convierte a arreglo.
     *
     * @param arrayJsonSerializado donde viene la informacion
     * @return un arreglo
     */
    private Vehiculo[] deserializarVehiculosXPersona(String arrayJsonSerializado) {
        Vehiculo[] vehiculos = new Gson().fromJson(arrayJsonSerializado, Vehiculo[].class);
        return vehiculos;
    }

    /**
     * Recupera de la bd los vehiculos asociados a un código.
     *
     * @param Id identificador de la persona(Usuario).
     * @return un arreglo con los vehiculos asociados a la identificacion.
     */
    public Vehiculo[] buscarVehiculosXPersonaCodigo(String Id) {
        String json = servidorCentral.obtenerListaVehiculosXPersonaCodigoDelServidorCentral(Id);
        if (!json.equals("NO_ENCONTRADO")) {
            Vehiculo[] vehiculos = deserializarVehiculosXPersonaCodigo(json);
            return vehiculos;
        } else {
            return null;
        }
    }

    /**
     * Convierte de formato json a cadena.
     *
     * @param arrayJsonSerializado variable con información en formato json.
     * @return una cadena con la informacion.
     */
    private Vehiculo[] deserializarVehiculosXPersonaCodigo(String arrayJsonSerializado) {
        Vehiculo[] vehiculos = new Gson().fromJson(arrayJsonSerializado, Vehiculo[].class);
        return vehiculos;
    }

    /*
    private ArrayList<Vehiculo> deserializarVehiculosXPersona(String arrayJsonSerializado){
        ArrayList<Vehiculo> vehiculos = new ArrayList();
        JsonArray array = new Gson().fromJson(arrayJsonSerializado, JsonArray.class);
        for(JsonElement elem : array){
            Vehiculo veh = new Vehiculo();
            veh.setPlaca(elem.getAsJsonObject().get("vehplaca").getAsString());
            veh.setIdentificacion(elem.getAsJsonObject().get("peridentificacion").getAsString());
            veh.setMarca(elem.getAsJsonObject().get("vehmarca").getAsString());
            veh.setTipo(elem.getAsJsonObject().get("vehtipo").getAsString());
            vehiculos.add(veh);
        }
        return vehiculos;
    } 
     */
    /**
     * Dada una placa recupera un objeto de tipo vehiculo
     * @param placa placa del vehiculo a buscar
     * @return objeto tipo vehiculo
     */
    public Vehiculo buscarVehiculoXPlaca(String placa) {

        Vehiculo veh = null;
        try {
            conector.conectarse();
            conector.crearConsulta("Select * from vehiculo where vehplaca = '"+placa+"' ");
            while(conector.getResultado().next()){
                veh = new Vehiculo(conector.getResultado().getString(1), conector.getResultado().getString(2) , conector.getResultado().getString(3), conector.getResultado().getString(4));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return veh;
    }
}
