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
public class LoginS {

    String id;
    String usuario;
    String contrasenia;
    
    public LoginS(){};
    
    public LoginS(String p_identificacion, String p_usuario, String p_contrasenia) {
        id = p_identificacion;
        usuario = p_usuario;
        contrasenia = p_contrasenia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "LoginS{" + "id=" + id + ", usuario=" + usuario + ", contrasenia=" + contrasenia + '}';
    }
}
