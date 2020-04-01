/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

/**
 *
 * @author Guido
 */
public class Parqueo {
    String idparqueo;
    String vehplaca;
    String bahid;
    String fechaingreso;
    String fechasalida;
    String horasalida;
    String horaentrada;

    public Parqueo(String vehplaca, String bahid, String fechaingreso) {
        this.vehplaca = vehplaca;
        this.bahid = bahid;
        this.fechaingreso = fechaingreso;
    }

    public Parqueo(String idparqueo, String vehplaca, String bahid, String fechaingreso, String fechasalida, String horasalida, String horaentrada) {
        this.idparqueo = idparqueo;
        this.vehplaca = vehplaca;
        this.bahid = bahid;
        this.fechaingreso = fechaingreso;
        this.fechasalida = fechasalida;
        this.horasalida = horasalida;
        this.horaentrada = horaentrada;
    }

    public String getIdparqueo() {
        return idparqueo;
    }

    public void setIdparqueo(String idparqueo) {
        this.idparqueo = idparqueo;
    }

    public String getVehplaca() {
        return vehplaca;
    }

    public void setVehplaca(String vehplaca) {
        this.vehplaca = vehplaca;
    }

    public String getBahid() {
        return bahid;
    }

    public void setBahid(String bahid) {
        this.bahid = bahid;
    }

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(String fechasalida) {
        this.fechasalida = fechasalida;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public String getHoraentrada() {
        return horaentrada;
    }

    public void setHoraentrada(String horaentrada) {
        this.horaentrada = horaentrada;
    }

    @Override
    public String toString() {
        return "Parqueo{" + "idparqueo=" + idparqueo + ", vehplaca=" + vehplaca + ", bahid=" + bahid + ", fechaingreso=" + fechaingreso + ", fechasalida=" + fechasalida + ", horasalida=" + horasalida + ", horaentrada=" + horaentrada + '}';
    }
   
}
