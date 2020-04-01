
package parqueadero.negocio;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class Persona {
    /**
     * Atributos.
     */
    String peridentificacion, pernombre, perapellido, pergenero, perfechaNacimiento;
    /**
     * Constructor por Defecto.
     */
    public Persona(){}

    public Persona(String peridentificacion, String pernombre, String perapellido, String pergenero, String perfechaNacimiento) {
        this.peridentificacion = peridentificacion;
        this.pernombre = pernombre;
        this.perapellido = perapellido;
        this.pergenero = pergenero;
        this.perfechaNacimiento = perfechaNacimiento;
    }

    public String getPeridentificacion() {
        return peridentificacion;
    }

    public void setPeridentificacion(String peridentificacion) {
        this.peridentificacion = peridentificacion;
    }

    public String getPernombre() {
        return pernombre;
    }

    public void setPernombre(String pernombre) {
        this.pernombre = pernombre;
    }

    public String getPerapellido() {
        return perapellido;
    }

    public void setPerapellido(String perapellido) {
        this.perapellido = perapellido;
    }

    public String getPergenero() {
        return pergenero;
    }

    public void setPergenero(String pergenero) {
        this.pergenero = pergenero;
    }

    public String getPerfechaNacimiento() {
        return perfechaNacimiento;
    }

    public void setPerfechaNacimiento(String perfechaNacimiento) {
        this.perfechaNacimiento = perfechaNacimiento;
    }

    @Override
    public String toString() {
        return "Persona{" + "peridentificacion=" + peridentificacion + ", pernombre=" + pernombre + ", perapellido=" + perapellido + ", pergenero=" + pergenero + ", perfechaNacimiento=" + perfechaNacimiento + '}';
    }

    

    
}
