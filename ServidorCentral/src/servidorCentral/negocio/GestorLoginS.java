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
public class GestorLoginS{
    ConectorJdbcSingleton  conector;
    public GestorLoginS(){
        conector = conector.getConexion();
    }
    //buscar los datos en la bd
    public LoginS consultarDatosLoginS(String p_id_usuario, String p_clave) {
        LoginS login = null;
        try {
            conector.conectarse();
           
            conector.crearConsulta("SELECT * FROM login WHERE codlogin = " + p_id_usuario + " AND logcontrasena = '" + p_clave+"' ");
           
            if (conector.getResultado().next()) {
                login = new LoginS(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3));
            }
            conector.desconectarse();
        } catch (SQLException ex) {
            Logger.getLogger(GestorLoginS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }
  
    public String consultarIdentificacionVigilante(String id_){
        String identificacion = null;
        try{
            conector.conectarse();
            conector.crearConsulta("select v.peridentificacion  from login l inner join vigilante v on(l.codlogin = v.codlogin) where l.codlogin = "+id_);
            while(conector.getResultado().next()){
                identificacion = conector.getResultado().getString(1);
            }
            conector.desconectarse();
        }catch(SQLException ex){
            System.out.println("Error desde consultarIdentificacionVigilante(Serv)"+ex.getMessage());
        }
        return identificacion;
    }
    // 
     public String consultarPorteriaVigilante(String id_){
        String porteria = null;
        try{
            conector.conectarse();
            conector.crearConsulta("select v.vigporteria from login l inner join vigilante v on(l.codlogin = v.codlogin) where l.codlogin = "+id_);
            while(conector.getResultado().next()){
                porteria = conector.getResultado().getString(1);
            }
            conector.desconectarse();
        }catch(SQLException ex){
            System.out.println("Error desde consultarIdentificacionVigilante(Serv)"+ex.getMessage());
        }
        return porteria;
    }
    // Convertir el registro de la BD a json
   /* private void parseToLogin(LoginS login, String json){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        login.setId(properties.getProperty("logid"));
        login.setUsuario(properties.getProperty("lognombre"));
        login.setContrasenia(properties.getProperty("logcontrasena"));
    }*/
    
}
