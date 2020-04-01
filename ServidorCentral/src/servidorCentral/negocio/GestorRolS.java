/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

import java.sql.SQLException;

/**
 *
 * @author Guido
 */
public class GestorRolS {
    private ConectorJdbcSingleton conector;
    public GestorRolS(){
        conector = conector.getConexion();
    }
    
    public RolS consultarNombreRol(String id) throws SQLException{
        RolS obj_rol_s = new RolS();
        
        obj_rol_s = null;
        conector.conectarse();
        conector.crearConsulta("Select r.rolid,r.peridentificacion,r.rolnombre from persona p inner join rol r on (p.peridentificacion = r.peridentificacion) \n" +
                               "where p.peridentificacion = "+id+" or p.peridentificacion = (select e.peridentificacion from estudiante e where e.estcodigo = "+id+")" );
        
        while(conector.getResultado().next()){
            obj_rol_s = new RolS(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3));
        }
        return obj_rol_s;
    }
}
