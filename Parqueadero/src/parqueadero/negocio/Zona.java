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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import parqueadero.presentacion.MenuAdministrador;
import parqueadero.presentacion.MenuVigilante;
import parqueadero.utils.Utilidades;

/**
 *
 * @author Guido
 */
public class Zona extends JPanel {

    List<Bahia> lista_bahias;
    int zonid;
    String zonnombre;
    int zoncantpuestos;

    public Zona() {
    }

    public Zona(ArrayList<Bahia> lista_bahias, int identificador, String nombre, int cant_bahias) {
        super();
        lista_bahias = new ArrayList<Bahia>();
        this.lista_bahias = lista_bahias;
        this.zonid = identificador;
        this.zonnombre = nombre;
        this.zoncantpuestos = cant_bahias;
    }

    public Zona(int identificador, String nombre, int cant_bahias) {
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
        int cont_aux = 0;
        //GestorBahia gb = new GestorBahia();
        GestorBahiaLocal gb = new GestorBahiaLocal();

        //Bahia array_bahias_x_zona[] = gb.bahiasPorZona(getIdentificador());
        ArrayList<Bahia> array_bahias_x_zona = gb.consultarBahiasPorZonaS(getIdentificador());

        if (getIdentificador() != 1) {//si la zona es diferente de id = 1; 
            //cont = (array_bahias_x_zona[0].bahid) - 1; //Recupere la primera posicion que encuentre para esa zona
            cont = array_bahias_x_zona.get(0).bahid - 1;  //Recupere la primera posicion que encuentre para esa zona
            //System.out.println("Contador: " + cont);
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                //Bahia aux = gb.estadoBahiaDesdeServidorCentral("" + ++cont, "" + this.getIdentificador());
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
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYY");
            Calendar calendario = new GregorianCalendar();
            int hora, minutos, segs;
            String hora_actual = "";
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segs = calendario.get(Calendar.SECOND);
            hora_actual = "" + hora + ":" + minutos + ":" + segs;

            for (Bahia bahia : lista_bahias) {
                if (e.getSource().equals(bahia)) {
                    if (bahia.isSelected()) {
                        Bahia bahia_aux = gest_bahia.estadoBahiaDesdeServidorCentral("" + bahia.getBahid(), "" + bahia.getZona_id());
                        bahia.setBahia_estado(bahia_aux.getBahia_estado());
                        bahia.setSelected(false);
                        if (e_ocupado.equals(bahia.getBahia_estado())) {
                            if (MenuVigilante.porteria_vig.equals(p_norte)) {
                                ImageIcon icon = new ImageIcon("src/recursos/out.png");

                                if ((JOptionPane.showConfirmDialog(null, "Desea desocupar la bahia ?", "ADVERTENCIA",
                                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon)) == 0) {
                                    // 0=yes, 1=no, 2=cancel
                                    Parqueo obj_parqueo = gest_parqueo.recuperarParqueo(bahia.getBahid());
                                    
                                    if (obj_parqueo != null) {
                                        String placa_rec = obj_parqueo.getVehplaca();
                                        if (gest_parqueo.registrarSalida(placa_rec, "" + bahia.getBahid(), obj_parqueo.getFechaingreso(), sdf.format(fecha), hora_actual, obj_parqueo.horaentrada)) {
                                            if (gest_bahia.editarBahiaEnServidorCentral("" + bahia.getBahid(), "" + getIdentificador(), MenuVigilante.identificacion_vigilante_in, e_libre)) {
                                                bahia.setBackground(color_disponible);
                                            } else {
                                                Utilidades.mensajeError("No fue posible hacer el cambio", "ERROR");
                                            }
                                        }
                                    } else {
                                        Utilidades.mensajeError("No fue posible realizar la operación ", "Error");
                                    }
                                }
                            } else {
                                Utilidades.mensajeError("NO TIENE PERMISOS PARA REALIZAR ESTA OPERACION", "ERROR");
                            }

                        } else {
                            if (MenuVigilante.porteria_vig.equals(p_sur)) {
                                if (!gest_parqueo.estaParqueado("" + MenuVigilante.datosSeleccionado().get(1))) {
                                    //objeto parqueo
                                    if (gest_parqueo.registrarIngreso("" + MenuVigilante.datosSeleccionado().get(1), "" + bahia.getBahid(), fecha.toString(), hora_actual)) {
                                        if (gest_bahia.editarBahiaEnServidorCentral("" + bahia.getBahid(), "" + getIdentificador(), MenuVigilante.identificacion_vigilante_in, e_ocupado)) {
                                            bahia.setBackground(color_ocupado);
                                        } else {
                                            Utilidades.mensajeError("No fue posible hacer el cambio", "ERROR");
                                        }
                                    } else {
                                        Utilidades.mensajeError("No fue posible registrar ingreso", "Error");
                                    }
                                } else {
                                    Utilidades.mensajeError("Vehiculo ya se encuentra parqueado", "Error");
                                }
                            } else {
                                Utilidades.mensajeError("NO TIENE PERMISOS PARA REALIZAR ESTA OPERACION", "ERROR");
                            }
                        }
                    }
                }
            }
        }
    }
}
