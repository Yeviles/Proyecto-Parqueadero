/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Guido
 */
public class ConectorJdbcSingleton {
    //inicializar variable para usar en patron singleton
    private static ConectorJdbcSingleton conexion;
    private Connection cn = null;
    private ResultSet rs = null;
    private Statement st = null;
    private final String URL = "jdbc:postgresql://localhost:5432/v5_park";
    //Cambie la URL de su bd local, por ejemplo, si tiene Windows,sería algo como:
    //private final String URL = "jdbc:hsqldb:file:C:\\clientes\\bd\\clientes;hsqldb.lock_file=false";

    private final String USER = "postgres";
    private final String PASSWORD = "123";

    /**
     * Constructor por Defecto.
     */
    private ConectorJdbcSingleton() {}
    
    /**Accesor que cre una instancia de la conexion.
     * 
     * @return la instancia de una conexion
     */
    public static ConectorJdbcSingleton getConexion(){
        if(conexion == null)
            conexion = new ConectorJdbcSingleton();
        return conexion;
    }

    public void conectarse() {
        try {
            Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (cn != null) {
                System.out.println("CONECTADO!!!!!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexion");
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta una consulta de tipo select
     *
     * @param sql
     * @throws SQLException
     */
    public void crearConsulta(String sql) throws SQLException {
        //System.out.println("Consulta a servidor : "+sql);
        st = cn.createStatement();
        rs = st.executeQuery(sql);
    }

    /**
     * Ejecuta una consulta de tipo insert, update o delete
     *
     * @param sql
     * @return 
     * @throws SQLException
     */
    public void actualizar(String sql) throws SQLException{
        st = cn.createStatement();
        st.executeUpdate(sql);
    }

    /**
     * Cierra las variables de rs, st y cn que estén abiertas
     *
     * @throws SQLException
     */
    public void desconectarse() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        st.close();
        cn.close();
    }

    /**
     * Devuelve todo el conjunto de resultados
     *
     * @return
     */
    public ResultSet getResultado() {
        return rs;
    }
    
}
