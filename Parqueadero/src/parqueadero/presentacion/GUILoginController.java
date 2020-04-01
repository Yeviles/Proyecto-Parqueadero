
package parqueadero.presentacion;

import parqueadero.negocio.GestorLogin;
import java.awt.event.ActionEvent;
import mvcf.AActionController;
import mvcf.AModel;
import mvcf.AView;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GUILoginController extends AActionController {

    private final GUILOGIN vista_log;
    private final GestorLogin gestor_log;

    public GUILoginController(AModel myModel, AView myView) {
        super(myModel, myView);
        this.vista_log = (GUILOGIN) myView;
        this.gestor_log = (GestorLogin) myModel;
    }

    @Override
    public void actualizar(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "INGRESAR":
                if (vista_log.validarFormulario()) {
                    //if (vista_log.getAccion().equals("INGRESAR")) {
                    iniciarSesion();
                    //}
                }
        }
    }

    private void iniciarSesion() {
        gestor_log.buscarLogin(Integer.parseInt(vista_log.getUser()), vista_log.getPass());
    }

}
