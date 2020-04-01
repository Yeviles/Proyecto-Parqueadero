package parqueadero.negocio;

/**
 *
 * @author Guido,Maria,Yennyfer
 */
public class Login {
    /**
     * Atributos.
     */
    int id;
    String usuario;
    String contrasenia;
    
    public Login(){};
    
    public Login(int p_identificacion, String p_usuario, String p_contrasenia) {
        id = p_identificacion;
        usuario = p_usuario;
        contrasenia = p_contrasenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Login{" + "id=" + id + ", usuario=" + usuario + ", contrasenia=" + contrasenia + '}';
    }
    
    
}
