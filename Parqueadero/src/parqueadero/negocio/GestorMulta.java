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
public class GestorMulta {
    private ConectorJdbcSingleton conector;

    public GestorMulta() {
        conector = conector.getConexion();
    }

    //En teoria esto seria en la parte local. Solo le interesa al vigilante.
    public boolean agregarMulta(String id_vig, String desc_mul, String fecha_mul, String foto_mul, String placa) {
        try {
            conector.conectarse();
            String sql = "INSERT INTO MULTA(perIdentificacion, mulDescripcion, mulFecha, mulFoto, vehplaca) VALUES("+ id_vig + ", '" + desc_mul + "', '" + fecha_mul+ "', '" + foto_mul + "', '"+placa+"' )";
            conector.actualizar(sql);
            conector.desconectarse();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error desde agregarMulta(cli): " + ex.getMessage());
            return false;
        }
    }
    public ArrayList<Multa> consultarMultasPorPlaca(int id_multa, String placa) {
        ArrayList<Multa> array_multas = new ArrayList<>();
        try {
            //String sql = "select * from multa m where m.mulId = " + id_multa + "and (m.mulId in select perIdentificacion from vehiculo vh where vehPlaca = '" + placa + "')";
            String sql = "select * from multa where vehplaca = '"+placa+"'";
            System.out.println("Id recuperado: " + sql);
            conector.conectarse();
            conector.crearConsulta(sql);
            while (conector.getResultado().next()) {
                Multa multa = new Multa(Integer.parseInt(conector.getResultado().getString(1)), Integer.parseInt(conector.getResultado().getString(2)), conector.getResultado().getString(3), conector.getResultado().getString(4), conector.getResultado().getString(5));
                array_multas.add(multa);
            }
            conector.desconectarse();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error desde consultarMultaPorPlaca " + ex.getMessage());
        }
        return array_multas;
    }
    
    public int cantidadMultasXplaca(String placa) {
        String sql = "select count(vehplaca) from multa where vehplaca = '"+placa+"' ";
        int cantidad = 0;
        try {
            conector.conectarse();
            conector.crearConsulta(sql);
            while(conector.getResultado().next()){
                cantidad = Integer.parseInt(conector.getResultado().getString(1));
            }
            conector.desconectarse();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestorMulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }
}
