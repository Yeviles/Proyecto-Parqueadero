
package parqueadero.presentacion;

import java.awt.event.ActionEvent;
import mvcf.AActionController;
import mvcf.AModel;
import mvcf.AView;
import parqueadero.negocio.GestorVehiculos;
import parqueadero.negocio.Vehiculo;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class GUIVehiculosController extends AActionController {

    private final GestorVehiculos gestor_veh;
    private final MenuVigilante vista_veh;

    public GUIVehiculosController(AModel myModel, AView myView) {
        super(myModel, myView);
        this.gestor_veh = (GestorVehiculos) myModel;
        this.vista_veh = ( MenuVigilante) myView;
    }

    @Override
    public void actualizar(ActionEvent e) {
        try {
            Vehiculo[] vehiculos = gestor_veh.buscarVehiculosXPersona(vista_veh.getId());
        } catch (Exception a) {
            System.out.println(a.getStackTrace());
        }

        switch(e.getActionCommand()){
            case "BUSCAR":
                if(vista_veh.validarFormulario()){
                    // OK System.out.println("LLega a buscar: "+vista_veh.getId());
                    buscarVehiculosXPersona(vista_veh.getId());
                }
                break;
        }
    }

    public void buscarVehiculosXPersona(String id) {
        try {
            gestor_veh.buscarVehiculosXPersona(id);
        } catch (Exception e) {
            System.out.println("ERROR al consultar vehiculos para persona"+e.getMessage());
            e.printStackTrace();
        }
        
    }
}
