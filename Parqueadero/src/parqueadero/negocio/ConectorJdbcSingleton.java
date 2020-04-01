/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

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
    private Connection cn;
    private ResultSet rs;
    private Statement st;
    
    private static ConectorJdbcSingleton conexion;
    private final String URL = "jdbc:postgresql://localhost:5432/v5_park";
    private final String USER = "postgres";
    private final String PASSWORD = "123";
    
    /**
     * Constructor por Defecto.
     */
    private ConectorJdbcSingleton() {}
    
    public static ConectorJdbcSingleton getConexion(){
        if(conexion == null)
            conexion = new ConectorJdbcSingleton();
        return conexion;
    }
    /**
     * Verifica si la conexión a la base de datos es exitosa
    **/
    public void conectarse() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        cn = DriverManager.getConnection(URL, USER, PASSWORD);
        if (cn != null) {
            //System.out.println("CONECTADO!!!!!");
        }
    }

    /**
     * Ejecuta una consulta de tipo select
     * @param sql
     * @throws SQLException
     */
    public void crearConsulta(String sql) throws SQLException {
        st = cn.createStatement();
        rs = st.executeQuery(sql);
    }

    /**
     * Ejecuta una consulta de tipo insert, update o delete
     * @param sql
     * @throws SQLException
     */
    public void actualizar(String sql) throws SQLException {
        st = cn.createStatement();
        st.executeUpdate(sql);
    }

    /**
     * Cierra las variables de rs, st y cn que estén abiertas.
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
     * @return
     */
    public ResultSet getResultado() {
        return rs;
    }
}
