/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.negocio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JPanel;
import parqueadero.presentacion.MenuAdministrador;

import parqueadero.utils.Utilidades;

/**
 *
 * @author Guido
 */
public class ZonaA extends JPanel {

    List<Bahia> lista_bahias;
    int zonid;
    String zonnombre;
    int zoncantpuestos;

    public ZonaA() {
    }

    public ZonaA(ArrayList<Bahia> lista_bahias, int identificador, String nombre, int cant_bahias) {
        super();
        lista_bahias = new ArrayList<Bahia>();
        this.lista_bahias = lista_bahias;
        this.zonid = identificador;
        this.zonnombre = nombre;
        this.zoncantpuestos = cant_bahias;
    }

    public ZonaA(int identificador, String nombre, int cant_bahias) {
        super();
        lista_bahias = new ArrayList<Bahia>();
        this.zonid = identificador;
        this.zonnombre = nombre;
        this.zoncantpuestos = cant_bahias;
    }

    public List<Bahia> getListaBahias() {
        return lista_bahias;
    }

    public void setListaBahias(ArrayList<Bahia> lista_bahias) {
        this.lista_bahias = lista_bahias;
    }

    public int getIdentificador() {
        return zonid;
    }

    public void setIdentificador(int identificador) {
        this.zonid = identificador;
    }

    public String getNombre() {
        return zonnombre;
    }

    public void setNombre(String nombre) {
        this.zonnombre = nombre;
    }

    public int getCantBahias() {
        return zoncantpuestos;
    }

    public void setCantBahias(int cant_bahias) {
        this.zoncantpuestos = cant_bahias;
    }

    @Override
    public String toString() {
        return "Zona{ identificador=" + zonid + ", nombre=" + zonnombre + ", cant_bahias=" + zoncantpuestos + '}';
    }

    Color color_disponible = new Color(216, 219, 226); //color gris 
    //Color color_disponible = new Color(240,240,240);//color blanco
    Color color_ocupado = new Color(23, 255, 21); //color verde
    Font fuente = new Font("Consolas", Font.ITALIC, 15);
    String estado = "", e_libre = "LIBRE", e_ocupado = "OCUPADO", p_sur = "SUR", p_norte = "NORTE";

    public void graficarBahias(int filas, int columnas) {
        lista_bahias = new ArrayList<Bahia>();
        int cont = 0;
        
        GestorBahiaLocal gb = new GestorBahiaLocal();
        ArrayList<Bahia> array_bahias_x_zona = gb.consultarBahiasPorZonaS(getIdentificador());

        if (getIdentificador() != 1) {//si la zona es diferente de id = 1; 
            cont = array_bahias_x_zona.get(0).bahid - 1;  //Recupere la primera posicion que encuentre para esa zona
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Bahia aux = gb.estadoZona("" + ++cont, "" + this.getIdentificador());
                if (aux != null) {
                    Bahia bahia = new Bahia(cont, getIdentificador(), 11111, aux.getBahia_estado());
                    bahia.setText("Ficha #" + aux.bahid);
                    bahia.setFont(fuente);
                    if (e_libre.equals(bahia.getBahia_estado())) {
                        bahia.setBackground(color_disponible);
                    }
                    if (e_ocupado.equals(bahia.getBahia_estado())) {
                        bahia.setBackground(color_ocupado);
                    }
                    lista_bahias.add(bahia);

                    actionBoton accion = new actionBoton();
                    bahia.addActionListener(accion);
                    this.add(bahia);
                } else {
                    System.out.println("No tenemos datos para su bahia");
                }
            }
        }
    }

    private String nombre() {
        return "";
    }

    public class actionBoton implements ActionListener {

        GestorBahia gest_bahia = new GestorBahia();
        GestorParqueo gest_parqueo = new GestorParqueo();

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Bahia bahia : lista_bahias) {
                if (e.getSource().equals(bahia)) {
                    if (bahia.isSelected()) {
                        Bahia bahia_aux = gest_bahia.estadoBahiaDesdeServidorCentral("" + bahia.getBahid(), "" + bahia.getZona_id());
                        bahia.setBahia_estado(bahia_aux.getBahia_estado());
                        bahia.setSelected(false);
                    }
                }
            }
        }
    }
}
