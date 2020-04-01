/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guido
 */
public class GestorVigilante {
    private ConectorJdbcSingleton conector;
    public GestorVigilante(){
        conector = conector.getConexion();
    }
    
    public boolean registrarVigilante(String identificacion, String cod_log, String per_nombre, String per_apellido, String per_genero, String per_fec_nac, String vig_cod, String vid_empresa, String vid_porteria) {
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO public.vigilante(\n"
                    + "	peridentificacion, codlogin, pernombre, perapellido, pergenero, perfechanac, vigcodigo, vigempresa, vigporteria)\n"
                    + "	VALUES (" + identificacion + ", " + cod_log + ", '" + per_nombre + "', '" + per_apellido + "', '" + per_genero + "', '" + per_fec_nac + "', " + vig_cod + ", '" + vid_empresa + "', '" + vid_porteria + "' )");
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorVigilante.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public Vigilante buscarVigilanteXIdentificacion(String id){
        Vigilante vi  = null;
        try {
            conector.conectarse();
            conector.crearConsulta("Select * from vigilante where peridentificacion = '"+id+"' ");
            while(conector.getResultado().next()){
                vi = new Vigilante(conector.getResultado().getString(1), conector.getResultado().getString(2) , conector.getResultado().getString(3), conector.getResultado().getString(4) , conector.getResultado().getString(5),  conector.getResultado().getString(6), conector.getResultado().getString(7), conector.getResultado().getString(8), conector.getResultado().getString(9));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vi;
    }
}
