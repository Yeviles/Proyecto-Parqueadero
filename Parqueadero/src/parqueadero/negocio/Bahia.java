/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import javax.swing.JToggleButton;

/**
 *
 * @author Guido
 */
public class Bahia extends JToggleButton {
    int bahid;
    int zona_id;
    int per_identificacion;
    String bahia_estado;

    public Bahia() {
        this(0, 0, 0, "");
    }
    
    public Bahia(int id_bahia, int zona_id, int per_identificacion, String estado) {
        super(); //Llama al constructor de la clase padre
        this.bahid = id_bahia;
        this.zona_id = zona_id;
        this.per_identificacion = per_identificacion;
        this.bahia_estado = estado;
    }

    public int getBahid() {
        return bahid;
    }

    public void setBahid(int bahid) {
        this.bahid = bahid;
    }

    public int getZona_id() {
        return zona_id;
    }

    public void setZona_id(int zona_id) {
        this.zona_id = zona_id;
    }

    public int getPer_identificacion() {
        return per_identificacion;
    }

    public void setPer_identificacion(int per_identificacion) {
        this.per_identificacion = per_identificacion;
    }

    public String getBahia_estado() {
        return bahia_estado;
    }

    public void setBahia_estado(String bahia_estado) {
        this.bahia_estado = bahia_estado;
    }

    @Override
    public String toString() {
        return "Bahia{" + "bahid=" + bahid + ", zona_id=" + zona_id + ", per_identificacion=" + per_identificacion + ", bahia_estado=" + bahia_estado + '}';
    }
        
}
