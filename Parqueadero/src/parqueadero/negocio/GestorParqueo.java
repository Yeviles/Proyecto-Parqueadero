/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guido
 */
public class GestorParqueo {

    ConectorJdbcSingleton conector;

    public GestorParqueo() {
        conector = conector.getConexion();
    }

    public boolean registrarIngreso(String veh_placa, String bahia_id, String fecha_ingreso, String hora_entrada) {
        try {
            conector.conectarse();
            conector.actualizar("INSERT INTO parqueo(vehplaca, bahid, fechaingreso, fechasalida, horasalida, horaentrada) VALUES ('" + veh_placa + "', " + bahia_id + ", '" + fecha_ingreso + "', null, null, '"+hora_entrada+"' )");
            conector.desconectarse();
            return true;
        } catch (SQLException | ClassNotFoundException ex ) {
            Logger.getLogger(GestorParqueo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean registrarSalida(String veh_placa, String id_bahia, String fecha_entrada, String fecha_salida, String hora_salida, String hora_entrada ) {
        try {
            conector.conectarse();
            String sql = "UPDATE parqueo SET fechasalida = '" + fecha_salida+ "', horasalida ='"+hora_salida+"' WHERE vehplaca = '"+veh_placa+"' AND bahid = "+id_bahia+" AND fechaingreso = '"+fecha_entrada+"' AND horaentrada = '"+hora_entrada+"' ";
            conector.actualizar(sql);
            conector.desconectarse();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GestorParqueo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Parqueo recuperarParqueo(int id_bahia) {
        Parqueo parqueo = null;
        try {
            conector.conectarse();
            conector.crearConsulta("Select * from parqueo where bahid = " + id_bahia);
            while (conector.getResultado().next()) {
                parqueo = new Parqueo(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3), conector.getResultado().getString(4), conector.getResultado().getString(5), conector.getResultado().getString(6), conector.getResultado().getString(7));
            }
            conector.desconectarse();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorParqueo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parqueo;
    }
    
    public boolean estaParqueado(String placa){
        try {
            conector.conectarse();
            conector.crearConsulta("select * from parqueo where vehplaca = '"+placa+"' and fechasalida is null");
            while(conector.getResultado().next()){
                return true;
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorParqueo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Parqueo> historialParqueo(String placa){
        ArrayList<Parqueo> list_p = new ArrayList<>();
        try {
            conector.conectarse();
            conector.crearConsulta("select idparqueo, vehplaca, bahid, TO_CHAR(fechaingreso, 'IYYY-IW-ID') fecha, fechasalida, horasalida, horaentrada FROM parqueo where fechaingreso > (select (select current_date) - integer '16') and vehplaca = '"+placa+"'");
            while(conector.getResultado().next()){
                list_p.add(new Parqueo(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3), conector.getResultado().getString(4), conector.getResultado().getString(5), conector.getResultado().getString(6), conector.getResultado().getString(7)));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorParqueo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list_p;
    }
}
