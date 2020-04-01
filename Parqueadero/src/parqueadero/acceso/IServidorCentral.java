package parqueadero.acceso;

/**
 *
 * @author ahurtado
 */
public interface IServidorCentral {

    public String obtenerVehiculoDelServidorCentral(String id);

    public String obtenerDatosLoginDelServidorCentral(int id, String pass);

    public String obtenerListaVehiculosXPersonaDelServidorCentral(String id);

    public String obtenerListaVehiculosXPersonaCodigoDelServidorCentral(String id);

    public String obtenerRolPersona(String id);

    public String agregarVehiculoEnServidorCentral(String placa_veh, int id_propietario, String marca_veh, String tipo_veh);

    public String obtenerZonasDesdeServidorCentral();

    public String agregarBahiaEnServidorCentra(String id_bahia_ab, String id_zona_ab, String id_persona_ab, String estado_ab);

    public String editarBahiaEnServidorCentra(String id_bahia_eb, String id_zona_eb, String id_persona_eb, String estado_eb);

    public String obtenerBahiasPorZonasDeServCentral(int id_zona);
    
    public String obtenerEstadoBahiaDesdeServidor(String id_bahia, String id_zona);
    
    public String obtenerIdentificacionDelVigilante(String codigo);
    
    public String obtenerPorteriaVigilante(String codigo);
    
}
