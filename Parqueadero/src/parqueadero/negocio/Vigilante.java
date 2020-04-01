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
public class Vigilante {
  String perIdentificacion;
    String codLogin;
    String perNombre;
    String perApellido;
    String Genero;
    String perFechaNac;
    String vigCodigo;
    String vigEmpresa;
    String vigPorteria;

    public Vigilante() {
    }
    

    public Vigilante(String perIdentificacion, String codLogin, String perNombre, String perApellido, String Genero, String perFechaNac, String vigCodigo, String vigEmpresa, String vigPorteria) {
        this.perIdentificacion = perIdentificacion;
        this.codLogin = codLogin;
        this.perNombre = perNombre;
        this.perApellido = perApellido;
        this.Genero = Genero;
        this.perFechaNac = perFechaNac;
        this.vigCodigo = vigCodigo;
        this.vigEmpresa = vigEmpresa;
        this.vigPorteria = vigPorteria;
    }
    
    public String getPerIdentificacion() {
        return perIdentificacion;
    }

    public void setPerIdentificacion(String perIdentificacion) {
        this.perIdentificacion = perIdentificacion;
    }

    public String getCodLogin() {
        return codLogin;
    }

    public void setCodLogin(String codLogin) {
        this.codLogin = codLogin;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public String getPerApellido() {
        return perApellido;
    }

    public void setPerApellido(String perApellido) {
        this.perApellido = perApellido;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getPerFechaNac() {
        return perFechaNac;
    }

    public void setPerFechaNac(String perFechaNac) {
        this.perFechaNac = perFechaNac;
    }

    public String getVigCodigo() {
        return vigCodigo;
    }

    public void setVigCodigo(String vigCodigo) {
        this.vigCodigo = vigCodigo;
    }

    public String getVigEmpresa() {
        return vigEmpresa;
    }

    public void setVigEmpresa(String vigEmpresa) {
        this.vigEmpresa = vigEmpresa;
    }

    public String getVigPorteria() {
        return vigPorteria;
    }

    public void setVigPorteria(String vigPorteria) {
        this.vigPorteria = vigPorteria;
    }
    
}
