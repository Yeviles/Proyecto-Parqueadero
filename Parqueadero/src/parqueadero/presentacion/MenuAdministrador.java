/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero.presentacion;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import mvcf.AModel;
import mvcf.AView;
import parqueadero.negocio.GestorBahiaLocal;
import parqueadero.negocio.GestorLogin;
import parqueadero.negocio.GestorPersona;
import parqueadero.negocio.GestorVigilante;
import parqueadero.negocio.GestorZona;
import parqueadero.negocio.Persona;
import parqueadero.negocio.Vigilante;
import parqueadero.negocio.ZonaA;
import parqueadero.utils.Utilidades;

/**
 *
 * @author Guido
 */
public class MenuAdministrador extends javax.swing.JFrame implements AView {

    private String accion;
    public static String identificacion_vigilante_in;
    public static String porteria_vig;
    int alto_boton = 25;
    int ancho_boton = 30;
    
    private Vector vec_aux; 

    int espacio_col = 50;
    int espacio_filas = 30;

    int pos_x = 10;
    int pos_y = 10;
    int aux = pos_x;

   

    /**
     * Creates new form Menu
     */
    public MenuAdministrador() {
        initComponents();
        this.setLocationRelativeTo(null);
        bosquejoParqueadero();
        vec_aux = new Vector();
        jtbPnMenuAdmin.setEnabledAt(1, true);
        pnlContenedorBosquejo.setVisible(false);
        jbtn_nom_vig.setFocusable(false);
    }

    public Vector getVec_aux() {
        return vec_aux;
    }

    public void setVec_aux(Vector vec_aux) {
        this.vec_aux = vec_aux;
    }
    /**
     * Llama a todos los metodos necesario para simular el bosquejo del
     * parqueadero
     */

    private void bosquejoParqueadero() {
        zonasParqueadero();
        inicializarTablaVigilante();
    }

    /**
     * Recupera desde servidor central todas las zonas registradas y las
     * configura para ser agregadas al mapa paqueadero
     */
    private void zonasParqueadero() {
        pnlContenedorBosquejo.removeAll();
        
        GestorZona gest_zona = new GestorZona();
        ZonaA array_zonas[] = gest_zona.zonasDeServidorCentralA();
        int cantidad_bahias = 0;
        String rec = "";
        int filas = 0;
        int cols = 0;

        for (ZonaA zona_tmp : array_zonas) {
            cantidad_bahias = zona_tmp.getCantBahias();
            if (cantidad_bahias % 2 != 0) {
                cantidad_bahias++;
            }
            rec = balancearFilasCols(zona_tmp.getCantBahias());
            filas = Integer.parseInt(rec.split(":")[1]);
            cols = Integer.parseInt(rec.split(":")[0]);

            if (cols > 0 && filas > 0) {
                //System.out.println("Cols["+cols+"] fil["+filas+"] nom ["+z.getNombre()+"] id ["+z.getIdentificador()+"]");
                agregarZonaEnPanel(cols, filas, zona_tmp.getNombre(), zona_tmp.getIdentificador());
            } else {
                System.out.println("No hay bahias ");
            }
        }
    }

      /**
     * Distribuye la cantidad de bahias en filas y columnas para maquetar una
     * zona en el mapa
     *
     * @param limite cantidad de bahias
     * @return dos numeros que opcionalmente se pueden tomar como cols o filas
     */
    private String balancearFilasCols(int limite) {
        if (!tieneMultiplos(limite)) {
            limite += 1;
        }
        for (int i = 1; i <= limite; i++) {
            for (int j = 1; j <= i; j++) {
                if (i * j == limite) {
                    return i + ":" + j;
                }
            }
        }
        return 0 + ":" + 0;
    }

    private boolean tieneMultiplos(int limite) {
        for (int i = 2; i < limite; i++) {
            if (limite % i == 0) {
                return true;
            }
        }
        return false;
    }
    
    
        /**
     * Configurada la zona permite adicionarla en el mapa del parqueadero
     *
     * @param col cantidad de columnas
     * @param fil cantidad de filas
     * @param nombre nombre que va a tener la zona
     */
    private void agregarZonaEnPanel(int col, int fil, String nombre, int id_zona) {
        pnlContenedorBosquejo.add(dibujarZona(fil, col, crearZona(nombre, id_zona, fil * col)));
    }
    
    /**
     * Metodo que recibe los parametros para dibujar una zona
     *
     * @param fil filas que tendra esa zona
     * @param col columnas que tendra esa zona
     * @param f Objeto de tipo Zona
     * @return Un objeto de tipo Zona
     */
    public ZonaA dibujarZona(int fil, int col, ZonaA f) {
        TitledBorder border = new TitledBorder(f.getNombre());
        f.setBackground(Color.WHITE);
        f.setBorder(border);
        f.setLayout(new GridLayout(fil, col, 6, 20));//numero de filas y columnas de la zona, seguido de espacio entre col y espacio entre fils
        f.graficarBahias(fil, col);
        return f;
    }

    /**
     * Permite crear un objeto Zona con su nombre, identificador y cantidad de
     * bahias respectivas
     *
     * @param nombre nombre de la zona
     * @param id identificador de la zona
     * @param cant cantidad de bahias para esa zona
     * @return un objeto de tipo Zona
     */
    private ZonaA crearZona(String nombre, int id, int cant) {
        return new ZonaA(id, nombre, cant);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup = new javax.swing.ButtonGroup();
        Norte = new javax.swing.JPanel();
        Jpnl_norte_m_vigilante = new javax.swing.JPanel();
        jbl_menu_vigilante = new javax.swing.JLabel();
        jpnl_Center_m_vigilante = new javax.swing.JPanel();
        jbtn_nom_vig = new javax.swing.JButton();
        jtbPnMenuAdmin = new javax.swing.JTabbedPane();
        jtabPnlVigilante = new javax.swing.JTabbedPane();
        ContenedorConsultarPersona = new javax.swing.JPanel();
        pnlSurError = new javax.swing.JPanel();
        lb_error = new javax.swing.JLabel();
        ContenedorTblVehiculos = new javax.swing.JPanel();
        jscrlPnlTblVehiculos1 = new javax.swing.JScrollPane();
        tblVigilantes = new javax.swing.JTable();
        pnlContenedorBusqueda1 = new javax.swing.JPanel();
        auxBusquedaNort1 = new javax.swing.JPanel();
        auxbusquedaSur1 = new javax.swing.JPanel();
        jlbl_placa1 = new javax.swing.JLabel();
        jtxt_id_vig = new javax.swing.JTextField();
        jbtn_buscar_vig = new javax.swing.JButton();
        ContenedorRegistrarPersona = new javax.swing.JPanel();
        jPanelNorth = new javax.swing.JPanel();
        jPanelWest = new javax.swing.JPanel();
        jPanelEast = new javax.swing.JPanel();
        jPanelCenter = new javax.swing.JPanel();
        jPanelRPCenter = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtx_identificacion_vig = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtx_nombre_vig = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxt_apellido_vig = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jcbx_genero_vig = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jdc_fecha_vig = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jtxt_empresa_vig = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtxt_cod_login = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jcbx_porteria = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jtxt_codigo_vig = new javax.swing.JTextField();
        jPanel_relleno = new javax.swing.JPanel();
        jPanelAux = new javax.swing.JPanel();
        jbtn_add_vigilante = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanelSouth = new javax.swing.JPanel();
        jpnlZona = new javax.swing.JPanel();
        contenedorCamposZona = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_id_zona = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtxt_nom_zona = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtxt_cant_bahias = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanelRVSuouth = new javax.swing.JPanel();
        btn_add_zona = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        pnlContenedorBosquejo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        Norte.setBackground(new java.awt.Color(0, 102, 156));
        Norte.setForeground(new java.awt.Color(255, 255, 255));
        Norte.setLayout(new java.awt.BorderLayout());

        Jpnl_norte_m_vigilante.setBackground(new java.awt.Color(0, 102, 156));

        jbl_menu_vigilante.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbl_menu_vigilante.setForeground(new java.awt.Color(255, 255, 255));
        jbl_menu_vigilante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jbl_menu_vigilante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ojo.png"))); // NOI18N
        jbl_menu_vigilante.setText("Menu Administrador");
        Jpnl_norte_m_vigilante.add(jbl_menu_vigilante);

        Norte.add(Jpnl_norte_m_vigilante, java.awt.BorderLayout.CENTER);

        jpnl_Center_m_vigilante.setBackground(new java.awt.Color(0, 102, 156));
        jpnl_Center_m_vigilante.setLayout(new java.awt.BorderLayout());

        jbtn_nom_vig.setBackground(new java.awt.Color(0, 102, 156));
        jbtn_nom_vig.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_nom_vig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Usuario.png"))); // NOI18N
        jbtn_nom_vig.setBorder(null);
        jbtn_nom_vig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_nom_vigActionPerformed(evt);
            }
        });
        jpnl_Center_m_vigilante.add(jbtn_nom_vig, java.awt.BorderLayout.CENTER);

        Norte.add(jpnl_Center_m_vigilante, java.awt.BorderLayout.LINE_END);

        getContentPane().add(Norte, java.awt.BorderLayout.NORTH);
        Norte.getAccessibleContext().setAccessibleParent(Norte);

        jtbPnMenuAdmin.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jtabPnlVigilante.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jtabPnlVigilante.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        ContenedorConsultarPersona.setBackground(new java.awt.Color(207, 226, 243));
        ContenedorConsultarPersona.setLayout(new java.awt.BorderLayout());

        pnlSurError.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlSurError.add(lb_error);

        ContenedorConsultarPersona.add(pnlSurError, java.awt.BorderLayout.SOUTH);

        ContenedorTblVehiculos.setLayout(new java.awt.GridLayout(1, 0));

        jscrlPnlTblVehiculos1.setBackground(new java.awt.Color(0, 102, 153));

        tblVigilantes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVigilantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jscrlPnlTblVehiculos1.setViewportView(tblVigilantes);

        ContenedorTblVehiculos.add(jscrlPnlTblVehiculos1);

        ContenedorConsultarPersona.add(ContenedorTblVehiculos, java.awt.BorderLayout.CENTER);

        pnlContenedorBusqueda1.setLayout(new java.awt.BorderLayout(3, 0));

        auxBusquedaNort1.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda1.add(auxBusquedaNort1, java.awt.BorderLayout.PAGE_START);

        auxbusquedaSur1.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda1.add(auxbusquedaSur1, java.awt.BorderLayout.PAGE_END);

        jlbl_placa1.setBackground(new java.awt.Color(207, 226, 243));
        jlbl_placa1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_placa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/multa.png"))); // NOI18N
        jlbl_placa1.setText("Identificacion Vigilante");
        pnlContenedorBusqueda1.add(jlbl_placa1, java.awt.BorderLayout.LINE_START);

        jtxt_id_vig.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_id_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_id_vigKeyTyped(evt);
            }
        });
        pnlContenedorBusqueda1.add(jtxt_id_vig, java.awt.BorderLayout.CENTER);

        jbtn_buscar_vig.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_buscar_vig.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_buscar_vig.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_buscar_vig.setText("Buscar");
        jbtn_buscar_vig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_buscar_vigActionPerformed(evt);
            }
        });
        pnlContenedorBusqueda1.add(jbtn_buscar_vig, java.awt.BorderLayout.LINE_END);

        ContenedorConsultarPersona.add(pnlContenedorBusqueda1, java.awt.BorderLayout.PAGE_START);

        jtabPnlVigilante.addTab("Consultar ", ContenedorConsultarPersona);

        ContenedorRegistrarPersona.setLayout(new java.awt.BorderLayout());
        ContenedorRegistrarPersona.add(jPanelNorth, java.awt.BorderLayout.NORTH);
        ContenedorRegistrarPersona.add(jPanelWest, java.awt.BorderLayout.WEST);
        ContenedorRegistrarPersona.add(jPanelEast, java.awt.BorderLayout.EAST);

        jPanelCenter.setBackground(new java.awt.Color(207, 226, 243));
        jPanelCenter.setLayout(new java.awt.BorderLayout());

        jPanelRPCenter.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRPCenter.setLayout(new java.awt.GridLayout(0, 2));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("  Identificacion");
        jPanelRPCenter.add(jLabel9);

        jtx_identificacion_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtx_identificacion_vigKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtx_identificacion_vig);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("  Nombre");
        jPanelRPCenter.add(jLabel10);

        jtx_nombre_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtx_nombre_vigKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtx_nombre_vigKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtx_nombre_vig);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("  Apellido");
        jPanelRPCenter.add(jLabel11);

        jtxt_apellido_vig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_apellido_vigActionPerformed(evt);
            }
        });
        jtxt_apellido_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_apellido_vigKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtxt_apellido_vig);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("  Genero");
        jPanelRPCenter.add(jLabel12);

        jcbx_genero_vig.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione genero", "MASCULINO", "FEMENINO" }));
        jPanelRPCenter.add(jcbx_genero_vig);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("  Fecha nacimiento");
        jPanelRPCenter.add(jLabel13);
        jPanelRPCenter.add(jdc_fecha_vig);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("  Empresa");
        jPanelRPCenter.add(jLabel8);

        jtxt_empresa_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_empresa_vigKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtxt_empresa_vig);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("  Codigo Login");
        jPanelRPCenter.add(jLabel1);

        jtxt_cod_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_cod_loginKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtxt_cod_login);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("  Porteria");
        jPanelRPCenter.add(jLabel3);

        jcbx_porteria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione porteria", "SUR", "NORTE" }));
        jPanelRPCenter.add(jcbx_porteria);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("  Codigo");
        jPanelRPCenter.add(jLabel4);

        jtxt_codigo_vig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_codigo_vigKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtxt_codigo_vig);

        jPanel_relleno.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRPCenter.add(jPanel_relleno);

        jPanelAux.setLayout(new java.awt.BorderLayout());

        jbtn_add_vigilante.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_add_vigilante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_add_vigilante.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_add_vigilante.setText("Guardar");
        jbtn_add_vigilante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_vigilanteActionPerformed(evt);
            }
        });
        jPanelAux.add(jbtn_add_vigilante, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(207, 226, 243));
        jPanelAux.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanelRPCenter.add(jPanelAux);

        jPanelCenter.add(jPanelRPCenter, java.awt.BorderLayout.NORTH);

        ContenedorRegistrarPersona.add(jPanelCenter, java.awt.BorderLayout.CENTER);
        ContenedorRegistrarPersona.add(jPanelSouth, java.awt.BorderLayout.PAGE_END);

        jtabPnlVigilante.addTab("Registrar", ContenedorRegistrarPersona);

        jtbPnMenuAdmin.addTab("Vigilante", jtabPnlVigilante);

        jpnlZona.setLayout(new java.awt.BorderLayout());

        contenedorCamposZona.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposZona.setLayout(new java.awt.GridLayout(0, 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("  Id Zona");
        contenedorCamposZona.add(jLabel2);
        contenedorCamposZona.add(txt_id_zona);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("  Nombre Zona");
        contenedorCamposZona.add(jLabel14);

        jtxt_nom_zona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_nom_zonaKeyTyped(evt);
            }
        });
        contenedorCamposZona.add(jtxt_nom_zona);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("  Cantidad de bahias");
        contenedorCamposZona.add(jLabel15);

        jtxt_cant_bahias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_cant_bahiasKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_cant_bahiasKeyTyped(evt);
            }
        });
        contenedorCamposZona.add(jtxt_cant_bahias);

        jPanel2.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposZona.add(jPanel2);

        jPanelRVSuouth.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth.setLayout(new java.awt.BorderLayout());

        btn_add_zona.setBackground(new java.awt.Color(224, 102, 102));
        btn_add_zona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_add_zona.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_zona.setText("Guardar");
        btn_add_zona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_zonaActionPerformed(evt);
            }
        });
        jPanelRVSuouth.add(btn_add_zona, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth.add(jPanel5, java.awt.BorderLayout.NORTH);

        contenedorCamposZona.add(jPanelRVSuouth);

        jpnlZona.add(contenedorCamposZona, java.awt.BorderLayout.PAGE_START);

        jtbPnMenuAdmin.addTab("Zona", jpnlZona);

        pnlContenedorBosquejo.setLayout(new java.awt.GridLayout(2, 0, 30, 20));
        jtbPnMenuAdmin.addTab("Mapa", pnlContenedorBosquejo);

        getContentPane().add(jtbPnMenuAdmin, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_nom_vigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_nom_vigActionPerformed
        this.dispose();
        GUILOGIN obj_login = new GUILOGIN();
        obj_login.setVisible(true);
    }//GEN-LAST:event_jbtn_nom_vigActionPerformed

    private void jbtn_add_vigilanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_vigilanteActionPerformed
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        GestorVigilante obj_vigilante = new GestorVigilante();
        GestorLogin gest_log = new GestorLogin();
        GestorPersona gest_per = new GestorPersona();
        if (validarFormularioPerson()) {
            String id = jtx_identificacion_vig.getText().trim();
            String nombre = jtx_nombre_vig.getText().trim();
            String apellido = jtxt_apellido_vig.getText().trim();
            String genero = jcbx_genero_vig.getSelectedItem().toString();
            String fecha_nacimiento = formato.format(jdc_fecha_vig.getDate());
            String empresa = jtxt_empresa_vig.getText().trim();
            String cod_login = jtxt_cod_login.getText().trim();
            String porteria = jcbx_porteria.getSelectedItem().toString();
            String cod_vigilante = jtxt_codigo_vig.getText().trim();
            Persona obj_per = new Persona();
            obj_per = gest_per.existePersona(id);
            if (obj_per == null) {
                gest_per.adicionarPersona(id, nombre, apellido, genero, fecha_nacimiento);
                if (!gest_log.existeLogin(cod_login)) {
                    if (gest_log.registrarLogin(cod_login, nombre, "123")) {
                        if (!obj_vigilante.registrarVigilante(id, cod_login, nombre, apellido, genero, fecha_nacimiento, cod_vigilante, empresa, porteria)) {
                            Utilidades.mensajeError("Problemas al ingresar registro", "Atencion");
                        } else {
                            Utilidades.mensajeExito("Registrado exitosamente!", "Exito");
                            limpiarFomulario();
                        }
                    } else {
                        Utilidades.mensajeError("Problemas al registrar Login", "Error");
                    }
                }
            } else {
                Utilidades.mensajeError("Problemas al registrar persona", "Error");
            }
        }
    }//GEN-LAST:event_jbtn_add_vigilanteActionPerformed

    private void jtxt_apellido_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_apellido_vigKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_apellido_vigKeyTyped

    private void jtx_nombre_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_nombre_vigKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtx_nombre_vigKeyTyped

    private void jtx_nombre_vigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_nombre_vigKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtx_nombre_vigKeyPressed

    private void jtx_identificacion_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_identificacion_vigKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jtx_identificacion_vigKeyTyped

    private void jtxt_apellido_vigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_apellido_vigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_apellido_vigActionPerformed

    private void jtxt_id_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_id_vigKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_id_vigKeyTyped

    private void jbtn_buscar_vigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_buscar_vigActionPerformed
        String id_vigilante = jtxt_id_vig.getText().trim();
        if (id_vigilante.equals("")) {
            Utilidades.mensajeError("Ingrese una placa", "Error");
        }{
            GestorVigilante gestor_vig = new GestorVigilante();
            try {
                Vigilante vig = gestor_vig.buscarVigilanteXIdentificacion(id_vigilante); //GARRAFAL_VACIO

                if (vig != null) {
                    llenarTabla(vig);
                } else {
                    inicializarTablaVigilante();
                    Utilidades.mensajeError("Vigilante no exite. Por favor registrarlo", "Sin registrar");
                }
            } catch (Exception e) {
                System.out.println("ERROR al cargar la Tabla " + e.getMessage());
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jbtn_buscar_vigActionPerformed

    private void jtxt_nom_zonaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_nom_zonaKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_nom_zonaKeyTyped

    private void jtxt_cant_bahiasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_cant_bahiasKeyReleased
        String placa = jtxt_nom_zona.getText().trim();
        if(placa.equals("")){
        }
    }//GEN-LAST:event_jtxt_cant_bahiasKeyReleased

    private void jtxt_cant_bahiasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_cant_bahiasKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_cant_bahiasKeyTyped

    private void btn_add_zonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_zonaActionPerformed
        // TODO add your handling code here:
        GestorZona ges_zona = new GestorZona();
        int zona_parcial = ges_zona.zonasDeServidorCentral().length;
        zona_parcial = zona_parcial+1;
        txt_id_zona.setText(""+zona_parcial);
        String id_zona = txt_id_zona.getText() ;
        String nombre_zona = jtxt_nom_zona.getText().trim();
        String cantidad = jtxt_cant_bahias.getText().trim();

        GestorZona obj_zona = new GestorZona();
        if(!obj_zona.registrarZona(id_zona, nombre_zona, cantidad)){
            Utilidades.mensajeAdvertencia("Problemas al registrar zona", "Aviso");
        }else{
            Utilidades.mensajeExito("Zona registrado exitosamente!", "Exito");
            agregarBahiasDeZona(id_zona, cantidad);
            jtbPnMenuAdmin.setSelectedIndex(2);
            limpiarFormularioZona();
            bosquejoParqueadero();
        }
    }//GEN-LAST:event_btn_add_zonaActionPerformed

    private void jtxt_codigo_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_codigo_vigKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jtxt_codigo_vigKeyTyped

    private void jtxt_cod_loginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_cod_loginKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jtxt_cod_loginKeyTyped

    private void jtxt_empresa_vigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_empresa_vigKeyTyped
         char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_empresa_vigKeyTyped
   
    private void limpiarFormularioZona(){
        txt_id_zona.setText("");
        jtxt_nom_zona.setText("");
        jtxt_cant_bahias.setText("");
    }
    
     private void agregarBahiasDeZona(String id_zona, String cantidad) {
         GestorBahiaLocal gest_bah = new GestorBahiaLocal();
         for (int i = 0; i < Integer.parseInt(cantidad); i++) {
            gest_bah.agregarBahia(id_zona, "11111");
         }
    }


    public boolean validarFormularioPerson() {
        if (jtx_identificacion_vig.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese identificacion", "Atencion");
            jtx_identificacion_vig.requestFocus();
            return false;
        }
        if (jtxt_cod_login.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese codigo de login para el vigilante", "Atencion");
            jtxt_cod_login.requestFocus();
            return false;
        }
        if (jtx_nombre_vig.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese nombre", "Atencion");
            jtx_nombre_vig.requestFocus();
            return false;
        }
        if (jtxt_apellido_vig.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese apellido", "Atencion");
            jtxt_apellido_vig.requestFocus();
            return false;
        }
        if (jcbx_genero_vig.getSelectedIndex() == 0) {
            Utilidades.mensajeAdvertencia("Seleccione un genero", "Atencion");
            return false;
        }
        if(jdc_fecha_vig.getDate()==null){
            Utilidades.mensajeAdvertencia("Seleccione una fecha", "Atencion");
            return false;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            Date fecha_actual = new Date();
            if(Utilidades.fechaEsPosterior(jdc_fecha_vig.getDate(), fecha_actual)){
                Utilidades.mensajeAdvertencia("Seleccione una fecha valida", "Atencion");
                return false;
            }
        }
        if (jcbx_porteria.getSelectedIndex() == 0) {
            Utilidades.mensajeAdvertencia("Seleccione una porteria", "Atencion");
            return false;
        }
        if (jtxt_codigo_vig.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese codigo del vigilante", "Atencion");
            jtxt_codigo_vig.requestFocus();
            return false;
        }
        return true;
    }
     static Vector v;
    public static Vector datosSeleccionado(){
        return v;
    }


    private void inicializarTablaVigilante() {
        tblVigilantes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Identificacion ", "CodLogin", "Nombre", "Apellido", "Genero", "FecNac", "Codigo", "Empresa", "Porteria"
                }
        ));
    }

    private void llenarTabla(Vigilante vigilante) {
        this.inicializarTablaVigilante();
        DefaultTableModel model = (DefaultTableModel) tblVigilantes.getModel();

        Object rowData[] = new Object[10];

        rowData[0] = vigilante.getPerIdentificacion();
        rowData[1] = vigilante.getCodLogin();
        rowData[2] = vigilante.getPerNombre();
        rowData[3] = vigilante.getPerApellido();
        rowData[4] = vigilante.getGenero();
        rowData[5] = vigilante.getPerFechaNac();
        rowData[6] = vigilante.getVigCodigo();
        rowData[7] = vigilante.getVigEmpresa();
        rowData[8] = vigilante.getVigPorteria();
        
        model.addRow(rowData);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContenedorConsultarPersona;
    private javax.swing.JPanel ContenedorRegistrarPersona;
    private javax.swing.JPanel ContenedorTblVehiculos;
    private javax.swing.JPanel Jpnl_norte_m_vigilante;
    private javax.swing.JPanel Norte;
    private javax.swing.JPanel auxBusquedaNort1;
    private javax.swing.JPanel auxbusquedaSur1;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton btn_add_zona;
    private javax.swing.JPanel contenedorCamposZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAux;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelEast;
    private javax.swing.JPanel jPanelNorth;
    private javax.swing.JPanel jPanelRPCenter;
    private javax.swing.JPanel jPanelRVSuouth;
    private javax.swing.JPanel jPanelSouth;
    private javax.swing.JPanel jPanelWest;
    private javax.swing.JPanel jPanel_relleno;
    private javax.swing.JLabel jbl_menu_vigilante;
    private javax.swing.JButton jbtn_add_vigilante;
    private javax.swing.JButton jbtn_buscar_vig;
    public static javax.swing.JButton jbtn_nom_vig;
    private javax.swing.JComboBox<String> jcbx_genero_vig;
    private javax.swing.JComboBox<String> jcbx_porteria;
    private com.toedter.calendar.JDateChooser jdc_fecha_vig;
    private javax.swing.JLabel jlbl_placa1;
    private javax.swing.JPanel jpnlZona;
    private javax.swing.JPanel jpnl_Center_m_vigilante;
    private javax.swing.JScrollPane jscrlPnlTblVehiculos1;
    private javax.swing.JTabbedPane jtabPnlVigilante;
    private javax.swing.JTabbedPane jtbPnMenuAdmin;
    private javax.swing.JTextField jtx_identificacion_vig;
    private javax.swing.JTextField jtx_nombre_vig;
    private javax.swing.JTextField jtxt_apellido_vig;
    private javax.swing.JTextField jtxt_cant_bahias;
    private javax.swing.JTextField jtxt_cod_login;
    private javax.swing.JTextField jtxt_codigo_vig;
    private javax.swing.JTextField jtxt_empresa_vig;
    private javax.swing.JTextField jtxt_id_vig;
    private javax.swing.JTextField jtxt_nom_zona;
    private javax.swing.JLabel lb_error;
    private javax.swing.JPanel pnlContenedorBosquejo;
    private javax.swing.JPanel pnlContenedorBusqueda1;
    private javax.swing.JPanel pnlSurError;
    private javax.swing.JTable tblVigilantes;
    private javax.swing.JTextField txt_id_zona;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actualizar(AModel amodel) {

    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("recursos/logoI.png"));

        return retValue;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    private void limpiarFomulario() {
        jtx_identificacion_vig.setText("");
        jtx_nombre_vig.setText("");
        jtxt_apellido_vig.setText("");
        jcbx_genero_vig.setSelectedIndex(0);

        jtxt_empresa_vig.setText("");
        jtxt_cod_login.setText("");
        jcbx_porteria.setSelectedIndex(0);
        jtxt_codigo_vig.setText("");
    }
}
