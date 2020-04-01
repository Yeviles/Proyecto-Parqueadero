package parqueadero.negocio;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcf.AModel;
import parqueadero.acceso.IServidorCentral;
import parqueadero.acceso.ServicioServidorCentralSocket;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GestorRol extends AModel {

    private ConectorJdbcSingleton conector;
    private final IServidorCentral servidorCentral;

    /**
     * Constructor por defecto.
     */
    public GestorRol() {
        servidorCentral = new ServicioServidorCentralSocket();
        conector = conector.getConexion();
    }

    public boolean adicionarRol(String id_rol, String identificacion, String rol_nombre) {
        try {
            conector.conectarse();
            conector.actualizar("Insert into rol values(" + id_rol + ", " + identificacion + ", '" + rol_nombre + "') ");
            conector.desconectarse();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error desde adicionar rol: " + ex.getMessage());
            return false;
        }
    }

    public int ultimoId() {

        int id = 0;
        try {
            conector.conectarse();
            conector.crearConsulta("Select rolid from rol");
            while (conector.getResultado().next()) {
                id = Integer.parseInt(conector.getResultado().getString(1));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Obtiene la informacion de una persona y su rol a partir de su
     * identificaion o código
     *
     * @param id identificador de la persona
     * @return devuelve un objeto rol
     */
    public Rol obtenerRolPerDeServidorCentral(String id) {
        String json = servidorCentral.obtenerRolPersona(id);
        if (!json.equals("NO_ENCONTRADO")) {
            Rol obj_rol = new Rol();
            parseToRol(obj_rol, json);
            return obj_rol;
        }
        return null;
    }

    /**
     * Convierte de json a objeto Rol.(todo en cadenas)
     *
     * @param obj_rol objeto de tipo Rol
     * @param json parametro con la informacion en formato json
     */
    private void parseToRol(Rol obj_rol, String json) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        obj_rol.setRol_id(properties.getProperty("rolid"));
        obj_rol.setRol_identificacion(properties.getProperty("peridentificacion"));
        obj_rol.setRol_nom(properties.getProperty("rolnombre"));
    }

}
