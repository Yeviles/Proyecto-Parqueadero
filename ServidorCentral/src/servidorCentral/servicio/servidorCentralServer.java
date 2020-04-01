package servidorCentral.servicio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidorCentral.negocio.BahiaS;
import servidorCentral.negocio.Ciudadano;
import servidorCentral.negocio.GestorBahiaS;
import servidorCentral.negocio.GestorCiudadano;
import servidorCentral.negocio.GestorLoginS;
import servidorCentral.negocio.GestorRolS;
import servidorCentral.negocio.GestorVehiculo;
import servidorCentral.negocio.GestorZonaS;
import servidorCentral.negocio.LoginS;
import servidorCentral.negocio.RolS;
import servidorCentral.negocio.Vehiculo;
import servidorCentral.negocio.ZonaS;

public class servidorCentralServer implements Runnable {

    private final GestorCiudadano gestor;
    private final GestorLoginS gestor_login;
    private final GestorVehiculo gestor_vehiculo;
    private final GestorRolS gestor_rol_s;
    private final GestorBahiaS gestor_bahia_s;
    private final GestorZonaS gestor_zona_s;

    private static ServerSocket ssock;
    private static Socket socket;

    private Scanner entradaDecorada;
    private PrintStream salidaDecorada;
    private static final int PUERTO = 5000;

    /**
     * Constructor
     */
    public servidorCentralServer() {
        gestor = new GestorCiudadano();
        gestor_login = new GestorLoginS();
        gestor_vehiculo = new GestorVehiculo();
        gestor_rol_s = new GestorRolS();
        gestor_bahia_s = new GestorBahiaS();
        gestor_zona_s = new GestorZonaS();
    }

    /**
     * Logica completa del servidor
     */
    public void iniciar() {
        if (abrirPuerto()) {
            while (true) {
                esperarAlCliente();
                lanzarHilo();
            }
        }
    }

    public static long tiempoMaximo() {
        return System.currentTimeMillis();
    }

    /**
     * Lanza el hilo
     */
    private static void lanzarHilo() {
        new Thread(new servidorCentralServer()).start();
    }

    private static boolean abrirPuerto() {
        try {
            ssock = new ServerSocket(PUERTO);
            System.out.println("Escuchando por el puerto " + PUERTO);
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage()); //+ " ¡Ya estoy ejecutandome!!!!! Supongo que debería ejecutar el cliente :/");
            return false;
        }
    }

    /**
     * Espera que el cliente se conecta y le devuelve un socket
     */
    private static void esperarAlCliente() {
        try {
            socket = ssock.accept();
            System.out.println("Cliente conectado");
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    /**
     * Cuerpo del hilo
     */
    @Override
    public void run() {
        try {
            crearFlujos();
            leerFlujos();
            cerrarFlujos();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(servidorCentralServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea los flujos con el socket
     *
     * @throws IOException
     */
    private void crearFlujos() throws IOException {
        salidaDecorada = new PrintStream(socket.getOutputStream());
        entradaDecorada = new Scanner(socket.getInputStream());
    }

    /**
     * Lee los flujos del socket
     */
    private void leerFlujos() throws ClassNotFoundException, SQLException {
        if (entradaDecorada.hasNextLine()) {
            // Extrae el flujo que envía el cliente
            String peticion = entradaDecorada.nextLine();
            decodificarPeticion(peticion);
        } else {
            salidaDecorada.flush();
            salidaDecorada.println("NO_ENCONTRADO");
        }
    }

    /**
     * Decodifica la petición, extrayeno la acción y los parámetros
     *
     * @param peticion petición completa al estilo
     * "consultarCiudadano,983932814"
     */
    private void decodificarPeticion(String peticion) throws ClassNotFoundException, SQLException {
        StringTokenizer tokens = new StringTokenizer(peticion, ",");
        String parametros[] = new String[15];

        int i = 0;
        while (tokens.hasMoreTokens()) {
            parametros[i++] = tokens.nextToken();
        }
        String accion = parametros[0];
        procesarAccion(accion, parametros);
    }

    /**
     * Segun el protocolo decide qué accion invocar
     *
     * @param accion acción a procesar
     * @param parametros parámetros de la acción
     */
    private void procesarAccion(String accion, String parametros[]) throws SQLException, ClassNotFoundException {
        switch (accion) {
            case "consultarCiudadano":
                String id = parametros[1];
                Ciudadano ciu = gestor.buscarCiudadano(id);
                if (ciu == null) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    //salidaDecorada.println(parseToJSON(ciu));
                }
                break;
            //Obtiene del servidor central los datos de un Login especifico cumpliendo con las condiciones de los parametros
            case "consultarDatosLogin":
                String identificacion = parametros[1];
                String pass = parametros[2];
                LoginS log_s = gestor_login.consultarDatosLoginS(identificacion, pass);
                if (log_s == null) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(parseToJSONLogin(log_s));
                }
                break;
            //Obtiene del servidor central los vehiculos asociados a una persona dada la identificacion de la persona
            case "consultarVehiculosXPersona":
                String identificacion_persona = parametros[1];
                ArrayList<Vehiculo> vehiculos_list = gestor_vehiculo.consultarVehiculosXPersona(identificacion_persona);
                if (vehiculos_list.isEmpty()) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(serializarVehiculos(vehiculos_list));
                }
                break;
            //Obtiene del servidor central la lista de carros que estan asociados a un persona dado el codigo
            case "consultarVehiculosXPersonaCodigo":
                String identificacion_persona_cod = parametros[1];
                ArrayList<Vehiculo> vehiculos_list_x_cod = gestor_vehiculo.consultarVehiculosXPersonaCodigo(identificacion_persona_cod);
                if (vehiculos_list_x_cod.isEmpty()) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(serializarVehiculos(vehiculos_list_x_cod));
                }
                break;
            //Permite agregar un nuevo vehiculo al servidor central
            case "agregarVehiculo":
                String placa_vehiculo = parametros[1];
                String id_propietario = parametros[2];
                String marca_vehiculo = parametros[3];
                String tipo_vehiculo = parametros[4];

                Vehiculo veh = new Vehiculo(placa_vehiculo, id_propietario, marca_vehiculo, tipo_vehiculo);
                if (!gestor_vehiculo.insertarVehiculo(veh)) {
                    salidaDecorada.println("FRACASO");
                }
                break;
            //Consulta el rol para para el registro que cumpla las condiciones 
            case "consultarRol":
                String identificacion_persona_rol = parametros[1];
                RolS obj_rol_s = gestor_rol_s.consultarNombreRol(identificacion_persona_rol);
                if (obj_rol_s == null) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(parseToJsonRol(obj_rol_s));
                }
                break;
            //Obtiene de servidor central todas las zonas registradas.
            case "consultarZonas":
                ArrayList<ZonaS> lista_zonas = gestor_zona_s.zonasDesdeServidorCentral();
                if (lista_zonas.isEmpty()) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(parseToJsonZonasS(lista_zonas));
                }
                break;
            //Agregar una bahia en servidor central
            case "agregarBahia":
                int id_bahia_ab = Integer.parseInt(parametros[1]);
                int id_zona_ab = Integer.parseInt(parametros[2]);
                int id_persona_ab = Integer.parseInt(parametros[3]);
                String estado_zona_ab = parametros[4];
                if (!gestor_bahia_s.agregarBahia(id_bahia_ab, id_zona_ab, id_persona_ab, estado_zona_ab)) {
                    salidaDecorada.println("FRACASO");
                }
                break;
            //Edita en servidor central una bahia
            case "editarBahia":
                int id_bahia_eb = Integer.parseInt(parametros[1]);
                int id_zona_eb = Integer.parseInt(parametros[2]);
                int id_persona_eb = Integer.parseInt(parametros[3]);
                String estado_zona_eb = parametros[4];
                if (!gestor_bahia_s.editarBahia(id_bahia_eb, id_zona_eb, id_persona_eb, estado_zona_eb)) {
                    salidaDecorada.println("FRACASO");
                }
                break;
            //Consulta de servidor central las bahias asociadas a una zona
            case "consultarBahiasXzona":
                int id_zona = Integer.parseInt(parametros[1]);
                ArrayList<BahiaS> lista_bahias = gestor_bahia_s.consultarBahiasPorZonaS(id_zona);
                if (lista_bahias == null) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(parseToJsonBahias(lista_bahias));
                }
                break;
            //Consulta de servidor central una bahia que tenga cumpla con los parametros
            case "estadoBahia":
                String id_bahia_e = parametros[1];
                String id_zona_e = parametros[2];
                BahiaS bahia = gestor_bahia_s.estadoZona(id_bahia_e, id_zona_e);
                if (bahia == null) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(parseToJsonElemetoBahia(bahia));
                }
                break;
            //Consulta la identificacion de un vigilante dado su codigo
            case "identificacionVigilante":
                String id_vigilante = parametros[1];
                String id_recuperado = gestor_login.consultarIdentificacionVigilante(id_vigilante);
                if(id_recuperado == null){
                    salidaDecorada.println("NO_ENCONTRADO");
                }else{
                    salidaDecorada.println(id_recuperado);
                }
                break;
             //Consulta la porteria de un vigilante dado su codigo
            case "porteriaVigilante":
                String porteria_vigilante = parametros[1];
                String porteria_recuperado = gestor_login.consultarPorteriaVigilante(porteria_vigilante);
                if(porteria_recuperado == null){
                    salidaDecorada.println("NO_ENCONTRADO");
                }else{
                    salidaDecorada.println(porteria_recuperado);
                }
                break;
        }
    }

    /**
     * Convertir de objeto RolS a formato Json
     *
     * @param obj_rol_s objeto de tipo RolS
     * @return informacion en formato json
     */
    private String parseToJsonRol(RolS obj_rol_s) {
        JsonObject json_object = new JsonObject();
        json_object.addProperty("rolid", obj_rol_s.getRol_id());
        json_object.addProperty("peridentificacion", obj_rol_s.getRol_identificacion());
        json_object.addProperty("rolnombre", obj_rol_s.getRol_nom());

        return json_object.toString();
    }

    /**
     * Cierra los flujos de entrada y salida
     *
     * @throws IOException
     */
    private void cerrarFlujos() throws IOException {
        salidaDecorada.close();
        entradaDecorada.close();
        socket.close();
    }

    /**
     * Convierte el objeto Ciudadano a json
     *
     * @param ciu Objeto ciudadano
     * @return cadena json
     */
    /* private String parseToJSON(Ciudadano ciu) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("id", ciu.getCedula());
        jsonobj.addProperty("nombres", ciu.getNombres());
        jsonobj.addProperty("apellidos", ciu.getApellidos());
        jsonobj.addProperty("direccion", ciu.getDireccion());
        jsonobj.addProperty("celular", ciu.getMovil());
        jsonobj.addProperty("email", ciu.getEmail());
        jsonobj.addProperty("sexo", ciu.getSexo());
        return jsonobj.toString();
    }*/
    private String parseToJSONLogin(LoginS logs) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("codlogin", logs.getId());
        jsonobj.addProperty("lognombre", logs.getUsuario());
        jsonobj.addProperty("logcontrasena", logs.getContrasenia());
        return jsonobj.toString();
    }

    private String serializarVehiculos(ArrayList<Vehiculo> listado) {//Se envia con los nombres a como esten en la clase
        JsonArray array = new JsonArray();
        JsonObject gsonObj;
        for (Vehiculo veh : listado) {
            gsonObj = new JsonObject();
            gsonObj.addProperty("placa", veh.getPlaca());
            gsonObj.addProperty("identificacion", veh.getIdentificacion());
            gsonObj.addProperty("marca", veh.getMarca());
            gsonObj.addProperty("tipo", veh.getTipo());
            array.add(gsonObj);
        }
        return array.toString();
    }

    /**
     * Sirve para un elemento bahia. Recibe un objeto tipo bahia
     *
     * @param obj_bahia
     * @return retorna un objeto de tipo bahia en formato json
     */
    private String parseToJsonElemetoBahia(BahiaS obj_bahia) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("bahid", obj_bahia.getBahid());
        jsonobj.addProperty("zona_id", obj_bahia.getZona_id());
        jsonobj.addProperty("per_identificacion", obj_bahia.getPer_identificacion());
        jsonobj.addProperty("bahia_estado", obj_bahia.getBahia_estado());
        return jsonobj.toString();
    }

    /**
     * Sirve para un conjunto de elemento bahia. Recibe un array tipo bahia
     *
     * @param obj_bahia
     * @return retorna un objeto de tipo bahia en formato json
     */
    private String parseToJsonBahias(ArrayList<BahiaS> lista_bahias) {
        JsonArray Array = new JsonArray();
        JsonObject myObjJson;
        for (BahiaS elem_bahia : lista_bahias) {
            myObjJson = parseToJsonBahiaAux(elem_bahia);
            Array.add(myObjJson);
        }
        return Array.toString();
    }

    /**
     * Recibe un objeto de tipo bahia y lo convierte a formato Json
     *
     * @param obj_bahia
     * @return un objeto en formato Json
     */
    private JsonObject parseToJsonBahiaAux(BahiaS obj_bahia) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("bahid", obj_bahia.getBahid());
        jsonobj.addProperty("zonid", obj_bahia.getZona_id());
        jsonobj.addProperty("peridentificacion", obj_bahia.getPer_identificacion());
        jsonobj.addProperty("bahestado", obj_bahia.getBahia_estado());
        return jsonobj;
    }

    /**
     * Recibe un array tipo zona
     *
     * @param obj_bahia
     * @return retorna un objeto de tipo bahia en formato json
     */
    private String parseToJsonZonasS(ArrayList<ZonaS> lista_zonas) {
        JsonArray Array = new JsonArray();
        JsonObject json_obj;
        for (ZonaS ele_zona : lista_zonas) {
            json_obj = new JsonObject();
            json_obj.addProperty("zonid", ele_zona.getZonid());
            json_obj.addProperty("zonnombre", ele_zona.getZonnombre());
            json_obj.addProperty("zoncantpuestos", ele_zona.getZoncantpuestos());
            Array.add(json_obj);
        }
        return Array.toString();
    }

    /**
     * Recibe un objeto de tipo Zon y lo convierte a formato Json
     *
     * @param obj_bahia
     * @return un objeto en formato Json
     */
//    private JsonObject parseToJsonZonaSaAux(Zon ele_zona) {
//        JsonObject json_obj = new JsonObject();
//        json_obj.addProperty("zonid", ele_zona.getZona_id());
//        json_obj.addProperty("zonnombre", ele_zona.getZona_nombre());
//        json_obj.addProperty("zoncantpuestos", ele_zona.getZona_cantidad_bahias());
//        return json_obj;
//    }
}
