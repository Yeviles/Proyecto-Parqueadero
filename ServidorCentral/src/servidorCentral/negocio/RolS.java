/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorCentral.negocio;

/**
 *
 * @author Guido
 */
public class RolS {
     String rol_id;
    String rol_nom;
    String rol_identificacion;

    public RolS(String rol_id, String rol_identificacion, String rol_nom ) {
        this.rol_id = rol_id;
        this.rol_nom = rol_nom;
        this.rol_identificacion = rol_identificacion;
    }

    RolS() {
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

    public String getRol_identificacion() {
        return rol_identificacion;
    }

    public void setRol_identificacion(String rol_identificacion) {
        this.rol_identificacion = rol_identificacion;
    }

    @Override
    public String toString() {
        return "RolS{" + "rol_id=" + rol_id + ", rol_nom=" + rol_nom + ", rol_identificacion=" + rol_identificacion + '}';
    }
}
