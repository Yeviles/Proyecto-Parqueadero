package parqueadero.acceso;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahurtado, wpantoja, rzambran
 */
public class ServicioServidorCentralSocket implements IServidorCentral {

    private Socket socket = null;
    private Scanner entradaDecorada;
    private PrintStream salidaDecorada;
    private final String IP_SERVIDOR = "localhost";
    private final int PUERTO = 5000;

    /**
     * Obtiene el registro de un cliente en formato Json
     *
     * @param id identificador del cliente
     * @return json con el registro del cliente
     */
    @Override
    public String obtenerVehiculoDelServidorCentral(String id) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalida(id);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String obtenerDatosLoginDelServidorCentral(int id, String pass) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaLogin(id, pass);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String obtenerListaVehiculosXPersonaDelServidorCentral(String id) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaVehiculosXPersona(id);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String obtenerListaVehiculosXPersonaCodigoDelServidorCentral(String id) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaVehiculosXPersonaCodigo(id);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaVehiculosXPersonaCodigo(String id) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarVehiculosXPersonaCodigo," + id);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalida(String id) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarCiudadano," + id);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaLogin(int id, String pass) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarDatosLogin," + id + "," + pass);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaVehiculosXPersona(String id) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarVehiculosXPersona," + id);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    @Override
    public String obtenerRolPersona(String id) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaRolPersona(id);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaRolPersona(String id) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarRol," + id);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    private void cerrarFlujos() {
        salidaDecorada.close();
        entradaDecorada.close();
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void conectar(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            //System.out.println("Conectado");
        } catch (IOException e) {
            System.out.println("Error: No se ha ejecutado el servidor.... " + e.getMessage() + "\n");
            System.exit(0);
        }
    }

    @Override
    public String agregarVehiculoEnServidorCentral(String placa_veh, int id_propietario, String marca_veh, String tipo_veh) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaAgregarVehiculo(placa_veh, id_propietario, marca_veh, tipo_veh);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaAgregarVehiculo(String placa_veh, int id_propietario, String marca_veh, String tipo_veh) {
        String respuesta = "";
        try {
            entradaDecorada = new Scanner(socket.getInputStream());
            salidaDecorada = new PrintStream(socket.getOutputStream());
            salidaDecorada.flush();
            // Usando el protocolo de comunicación
            String datos = "agregarVehiculo," + placa_veh + "," + id_propietario + "," + marca_veh + "," + tipo_veh;
            salidaDecorada.println(datos);
            if (entradaDecorada.hasNextLine()) {
                respuesta = entradaDecorada.nextLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String obtenerZonasDesdeServidorCentral() {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaZonasS();
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaZonasS() throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarZonas");
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    @Override
    public String agregarBahiaEnServidorCentra(String id_bahia_ab, String id_zona_ab, String id_persona_ab, String estado_ab) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaAgregarBahia(id_bahia_ab, id_zona_ab, id_persona_ab, estado_ab);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaAgregarBahia(String id_bahia_ab, String id_zona_ab, String id_persona_ab, String estado_ab) {
         String respuesta = "";
        try {
            entradaDecorada = new Scanner(socket.getInputStream());
            salidaDecorada = new PrintStream(socket.getOutputStream());
            salidaDecorada.flush();
            // Usando el protocolo de comunicación
            salidaDecorada.println("agregarBahia," + id_bahia_ab + "," + id_zona_ab + "," + id_persona_ab + "," +estado_ab);
            if (entradaDecorada.hasNextLine()) {
                respuesta = entradaDecorada.nextLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String editarBahiaEnServidorCentra(String id_bahia_eb, String id_zona_eb, String id_persona_eb, String estado_eb) {
         String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaEditarBahia(id_bahia_eb, id_zona_eb, id_persona_eb, estado_eb);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    private String leerFlujoEntradaSalidaEditarBahia(String id_bahia_eb, String id_zona_eb, String id_persona_eb, String estado_eb){
         String respuesta = "";
        try {
            entradaDecorada = new Scanner(socket.getInputStream());
            salidaDecorada = new PrintStream(socket.getOutputStream());
            salidaDecorada.flush();
            // Usando el protocolo de comunicación
            salidaDecorada.println("editarBahia," + id_bahia_eb + "," + id_zona_eb + "," + id_persona_eb + "," +estado_eb);
            if (entradaDecorada.hasNextLine()) {
                respuesta = entradaDecorada.nextLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    @Override
    public String obtenerBahiasPorZonasDeServCentral(int id_zona) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaBahiaXZona(id_zona);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaBahiaXZona(int id_zona) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("consultarBahiasXzona," + id_zona);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    @Override
    public String obtenerEstadoBahiaDesdeServidor(String id_bahia, String id_zona) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaEstadoBahia(id_bahia, id_zona);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    private String leerFlujoEntradaSalidaEstadoBahia(String id_bahia, String id_zona) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("estadoBahia," + id_bahia+", "+id_zona);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    @Override
    public String obtenerIdentificacionDelVigilante(String codigo) {
        String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaIdentificacionVigilante(codigo);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaIdentificacionVigilante(String codigo) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("identificacionVigilante," + codigo);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    @Override
    public String obtenerPorteriaVigilante(String codigo) {
       String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalidaPorteriaVigilante(codigo);
            cerrarFlujos();
            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String leerFlujoEntradaSalidaPorteriaVigilante(String codigo) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println("porteriaVigilante," + codigo);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }
}
