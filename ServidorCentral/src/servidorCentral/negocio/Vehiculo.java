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
public class Vehiculo {
    String placa;
    String identificacion;
    String marca;
    String tipo;
    
    Vehiculo(){}
    
    public Vehiculo(String p_placa, String p_identificacion, String p_marca, String p_tipo){
        placa = p_placa;
        identificacion = p_identificacion;
        marca = p_marca;
        tipo = p_tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
