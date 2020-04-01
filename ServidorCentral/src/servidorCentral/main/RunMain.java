package servidorCentral.main;

import servidorCentral.servicio.servidorCentralServer;

/**
 *
 * @author libardo
 */
public class RunMain {
    public static void main(String args[]){
        servidorCentralServer regSer = new servidorCentralServer();
        regSer.iniciar();
    }
}
