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
public class ZonaS {
    String zonid;
    String zonnombre;
    String zoncantpuestos;

    public ZonaS() {}

    public ZonaS(String zonid, String zonnombre, String zoncantpuestos) {
        this.zonid = zonid;
        this.zonnombre = zonnombre;
        this.zoncantpuestos = zoncantpuestos;
    }

    public String getZonid() {
        return zonid;
    }

    public void setZonid(String zonid) {
        this.zonid = zonid;
    }

    public String getZonnombre() {
        return zonnombre;
    }

    public void setZonnombre(String zonnombre) {
        this.zonnombre = zonnombre;
    }

    public String getZoncantpuestos() {
        return zoncantpuestos;
    }

    public void setZoncantpuestos(String zoncantpuestos) {
        this.zoncantpuestos = zoncantpuestos;
    }
}
