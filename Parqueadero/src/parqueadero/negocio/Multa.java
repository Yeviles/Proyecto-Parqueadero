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
public class Multa {
    int mulId, perIdentificacion;
    String vehplaca, mulDescripcion , mulFecha, mulFoto;
    
    public Multa(){}
    public Multa(int id_mul, int id_vig, String desc_mul, String fecha_mul, String foto_mul){
        this.mulId = id_mul;
        this.perIdentificacion = id_vig;
        this.mulDescripcion = desc_mul;
        this.mulFecha = fecha_mul;
        this.mulFoto = foto_mul;
    }

    public Multa(int id_mul, int id_vig, String desc_mul, String fecha_mul, String foto_mul, String veh_placa){
        this.mulId = id_mul;
        this.perIdentificacion = id_vig;
        this.mulDescripcion = desc_mul;
        this.mulFecha = fecha_mul;
        this.mulFoto = foto_mul;
        this.vehplaca = veh_placa;
    }

    public String getVehplaca() {
        return vehplaca;
    }

    public void setVehplaca(String vehplaca) {
        this.vehplaca = vehplaca;
    }
    public int getMulId() {
        return mulId;
    }

    public void setMulId(int mulId) {
        this.mulId = mulId;
    }

    public int getPerIdentificacion() {
        return perIdentificacion;
    }

    public void setPerIdentificacion(int perIdentificacion) {
        this.perIdentificacion = perIdentificacion;
    }

    public String getMulDescripcion() {
        return mulDescripcion;
    }

    public void setMulDescripcion(String mulDescripcion) {
        this.mulDescripcion = mulDescripcion;
    }

    public String getMulFecha() {
        return mulFecha;
    }

    public void setMulFecha(String mulFecha) {
        this.mulFecha = mulFecha;
    }

    public String getMulFoto() {
        return mulFoto;
    }

    public void setMulFoto(String mulFoto) {
        this.mulFoto = mulFoto;
    }

    @Override
    public String toString() {
        return "Multa{" + "mulId=" + mulId + ", perIdentificacion=" + perIdentificacion + ", vehplaca=" + vehplaca + ", mulDescripcion=" + mulDescripcion + ", mulFecha=" + mulFecha + ", mulFoto=" + mulFoto + '}';
    }

    
}
