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
public class GestorVehiculo {

    private ConectorJdbcSingleton conector;

    public GestorVehiculo() {
        conector = conector.getConexion();
    }

    public ArrayList<Vehiculo> consultarVehiculosXPersona(String id) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("(select * from vehiculo where peridentificacion = "+ id+") union\n" +
"(select * from vehiculo where vehplaca in (select va.vehplaca from vehiculo v inner join vehiculosasociados va on v.peridentificacion=va.peridentificacion\n" +
"where v.peridentificacion = "+ id+"))" );
        //select * from vehiculo where peridentificacion = (select peridentificacion from estudiante where estcodigo = '33333')
        ArrayList<Vehiculo> lista_vehiculos = new ArrayList();
        while (conector.getResultado().next()) {
            Vehiculo veh = new Vehiculo(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3), conector.getResultado().getString(4));
            lista_vehiculos.add(veh);
        }
        return lista_vehiculos;
    }

    //Buscar vehiculos para una persona mediante el codigo
    /**
     *
     * @param cod recibe el codigo de una persona para listar vehiculos
     * asociados
     * @return una lista con los vehiculos asociados al codigo
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Vehiculo> consultarVehiculosXPersonaCodigo(String cod) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("select * from vehiculo where peridentificacion = (select peridentificacion from estudiante where estcodigo = " + cod + ") ");
        ArrayList<Vehiculo> lista_vehiculos = new ArrayList();
        while (conector.getResultado().next()) {
            Vehiculo veh = new Vehiculo(conector.getResultado().getString(1), conector.getResultado().getString(2), conector.getResultado().getString(3), conector.getResultado().getString(4));
            lista_vehiculos.add(veh);
        }
        return lista_vehiculos;
    }
    /**
     * 
     * @param veh
     * @return 
     */
    public boolean insertarVehiculo(Vehiculo veh){
        boolean inserto = true;
        try {
            conector.conectarse();
            String sql = "INSERT INTO vehiculo(\n" +
                    "	vehplaca, peridentificacion, vehmarca, vehtipo)\n" +
                    "	VALUES ('"+veh.getPlaca()+"',"+ veh.getIdentificacion()+", '"+ veh.getMarca()+"', '"+veh.getTipo()+"')";
            conector.actualizar(sql);
            conector.desconectarse();
        } catch (SQLException ex) {
            inserto = false;
            //Logger.getLogger(GestorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al insertar vehiculo(serv): "+ex.getMessage());
        }
        return inserto;
    }
}
