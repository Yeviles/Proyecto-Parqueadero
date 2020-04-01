
package parqueadero.negocio;

/**
 *
 * @author Guido,Maria, Yennyfer.
 */
public class Rol {
    /**
     * Atributos.
     */
    String rol_id;
    String rol_nom;
    String rol_identificacion;
    /**
     * Constructor por Defecto.
     */
    Rol() {}
    /**
     * Constructor por Argumentos.
     * @param rol_id
     * @param rol_identificacion
     * @param rol_nom 
     */
    public Rol(String rol_id,String rol_identificacion, String rol_nom ) {
        this.rol_id = rol_id;
        this.rol_nom = rol_nom;
        this.rol_identificacion = rol_identificacion;
    }

    public String getRol_identificacion() {
        return rol_identificacion;
    }

    public void setRol_identificacion(String rol_identificacion) {
        this.rol_identificacion = rol_identificacion;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getRol_nom() {
        return rol_nom;
    }

    public void setRol_nom(String rol_nom) {
        this.rol_nom = rol_nom;
    }

    @Override
    public String toString() {
        return "Rol{" + "rol_id=" + rol_id + ", rol_nom=" + rol_nom + ", rol_identificacion=" + rol_identificacion + '}';
    }

           
}
