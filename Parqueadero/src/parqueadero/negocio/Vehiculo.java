
package parqueadero.negocio;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class Vehiculo {
    /**
     * Atributos.
     */
    String placa;
    String identificacion;
    String marca;
    String tipo;
    /**
     * Constructor por Defecto.
     */
    Vehiculo() {}
    /**
     * Constructor por Argumentos.
     * @param p_placa
     * @param p_id
     * @param p_marca
     * @param p_tipo 
     */
    public Vehiculo(String p_placa, String p_id, String p_marca, String p_tipo) {
        placa = p_placa;
        identificacion = p_id;
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

    public void setIdentificacion(String modelo) {
        this.identificacion = modelo;
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

    @Override
    public String toString() {
        return "Vehiculo{" + "placa=" + placa + ", identificacion=" + identificacion + ", marca=" + marca + ", tipo=" + tipo + '}';
    }
}
