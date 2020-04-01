package parqueadero.negocio;

import parqueadero.acceso.ServicioServidorCentralSocket;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcf.AModel;
import parqueadero.acceso.IServidorCentral;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GestorLogin extends AModel{
    
    private final IServidorCentral servidor_central;
    ConectorJdbcSingleton conector;
    
    /**
     * Contructor por Defecto.
     */
    public GestorLogin(){
        servidor_central = new ServicioServidorCentralSocket();
        conector = conector.getConexion();
    }
    
    /**
     * Compara los datos obtenidos del servidor central y capturados por pantalla.
     * @param id Dato a comprar con el registrado el Bd, se obtiene del servidor Central.
     * @param pass Dato a comprar con el registrado el Bd, se obtiene del servidor Central.
     * @return 
     */
    public Login buscarLogin(int id, String pass){
        String json = servidor_central.obtenerDatosLoginDelServidorCentral(id, pass);
        if(!json.equals("NO_ENCONTRADO")){
            Login login = new Login();
            parseToLogin(login, json);
            return login;
        }else{
            return null;
        }
    }
    
    /**
     * Convierte el registro de la BD a json.
     **/
    private void parseToLogin(Login login, String json){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        login.setId(Integer.parseInt(properties.getProperty("codlogin")));
        login.setUsuario(properties.getProperty("lognombre"));
        login.setContrasenia(properties.getProperty("logcontrasena"));
    }
    
    public String consultarIdentificacionVigilante(String codigo){
        String json = servidor_central.obtenerIdentificacionDelVigilante(codigo);
        if(!json.equals("NO_ENCONTRADO")){
            return json;
        }else{
            return null;
        }
    }
    
    public String consultarPorteriaVigilante(String codigo){
        String json = servidor_central.obtenerPorteriaVigilante(codigo);
        if(!json.equals("NO_ENCONTRADO")){
            return json;
        }else{
            return null;
        }
    }
    
    public boolean esAdministrador(String codigo) {
        boolean band = false; 
        try {
            conector.conectarse();
            conector.crearConsulta("select * from administrador where codlogin = " + codigo);
            if(conector.getResultado().next())
                band = true;
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorLogin.class.getName()).log(Level.SEVERE, null, ex);
            band = false;
        }
        return band;
    }
    
    public boolean existeLogin(String codigo) {
        boolean band = false;
        try {
            conector.conectarse();
            String sql = "select * from login where codlogin = " + codigo;
            System.out.println("My sql "+sql);
            conector.crearConsulta(sql);
            if(conector.getResultado().next())
                band = true;
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorLogin.class.getName()).log(Level.SEVERE, null, ex);
            band = false;
        }
        return band;
    }
    
    public boolean registrarLogin(String codigo, String usuario, String pass){
        try {
            conector.conectarse();
            String sql = "INSERT INTO public.login(codlogin, lognombre, logcontrasena) VALUES ("+codigo+", '"+usuario+"', '"+pass+"' )" ;
            System.out.println("My sql2 "+sql);
            conector.actualizar(sql);
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorLogin.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}
