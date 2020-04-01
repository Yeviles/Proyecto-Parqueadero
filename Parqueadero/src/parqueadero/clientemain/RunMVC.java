package parqueadero.clientemain;

import parqueadero.negocio.GestorLogin;
import parqueadero.negocio.GestorVehiculos;
import parqueadero.presentacion.GUILOGIN;
import parqueadero.presentacion.GUILoginController;
import parqueadero.presentacion.GUIVehiculosController;
import parqueadero.presentacion.MenuAdministrador;
import parqueadero.presentacion.MenuVigilante;

/**
 * Es el pegamento de la aplición
 *
 * @author Guido,Maria, Yennyfer
 */
public class RunMVC {

    public RunMVC() {
        GestorLogin gestor_log = new GestorLogin();
        GestorVehiculos gestor_veh = new GestorVehiculos();
        
        // Primera VISTA iniciar sesion
        GUILOGIN view0 = new GUILOGIN();
        GUILoginController ctrl_login = new GUILoginController(gestor_log, view0);
        gestor_log.addView(view0);
        gestor_log.notificar();
        view0.setVisible(true);

        // Segunda VISTA buscar vehiculos para un usuario
        MenuVigilante view_1 = new MenuVigilante();
        gestor_veh.addView(view_1);
        GUIVehiculosController contro_vehs = new GUIVehiculosController(gestor_veh, view_1);
        gestor_veh.notificar(); // Para que se cargue los vehiculos al cargar la ventana
        //view_1.setVisible(true);
        
        // Enlaza el action controller de los botones al controlador y fija el comando de acción
        view0.getBtnIniciar().addActionListener(ctrl_login);
        view0.getBtnIniciar().setActionCommand("INGRESAR");

        view_1.getRBtnIdentificacion().addActionListener(contro_vehs);
        view_1.getRBtnIdentificacion().setActionCommand("BUSCAR");
       
//       MAPA m = new MAPA();
//       m.setVisible(true);

    }
}
