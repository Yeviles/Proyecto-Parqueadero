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
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import mvcf.AModel;
import mvcf.AView;
import parqueadero.negocio.ConectorJdbc;
import parqueadero.negocio.GestorMulta;
import parqueadero.negocio.GestorPersona;
import parqueadero.negocio.GestorRol;
import parqueadero.negocio.GestorVehiculos;
import parqueadero.negocio.GestorZona;
import parqueadero.negocio.Persona;
import parqueadero.negocio.Rol;
import parqueadero.negocio.Vehiculo;
import parqueadero.negocio.Zona;
import parqueadero.utils.Utilidades;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import parqueadero.negocio.GestorParqueo;
import parqueadero.negocio.Parqueo;
/**
 *
 * @author Guido
 */
public class MenuVigilante extends javax.swing.JFrame implements AView {

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
    public MenuVigilante() {
        initComponents();
        this.setLocationRelativeTo(null);
        bosquejoParqueadero();
        vec_aux = new Vector();
        txtId.setText("11111");
        jtbPnMenuVigilante.setEnabledAt(1, false);
        pnlContenedorBosquejo.setVisible(false);
        jbtn_asignar.setVisible(false);
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
        inicializarTablaVhsXPersona();
        inicializarTablaMulta();
    }

    /**
     * Metodo que recibe los parametros para dibujar una zona
     *
     * @param fil filas que tendra esa zona
     * @param col columnas que tendra esa zona
     * @param f Objeto de tipo Zona
     * @return Un objeto de tipo Zona
     */
    public Zona dibujarZona(int fil, int col, Zona f) {
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
    private Zona crearZona(String nombre, int id, int cant) {
        return new Zona(id, nombre, cant);
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
     * Recupera desde servidor central todas las zonas registradas y las
     * configura para ser agregadas al mapa paqueadero
     */
    private void zonasParqueadero() {
        GestorZona gest_zona = new GestorZona();
        Zona array_zonas[] = gest_zona.zonasDeServidorCentral();
        int cantidad_bahias = 0;
        String rec = "";
        int filas = 0;
        int cols = 0;

        for (Zona z : array_zonas) {
            cantidad_bahias = z.getCantBahias();
            if (cantidad_bahias % 2 != 0) {
                cantidad_bahias++;
            }
            rec = balancearFilasCols(z.getCantBahias());
            filas = Integer.parseInt(rec.split(":")[1]);
            cols = Integer.parseInt(rec.split(":")[0]);

            if (cols > 0 && filas > 0) {
                //System.out.println("Cols["+cols+"] fil["+filas+"] nom ["+z.getNombre()+"] id ["+z.getIdentificador()+"]");
                agregarZonaEnPanel(cols, filas, z.getNombre(), z.getIdentificador());
            } else {
                System.out.println("No hay bahias ");
            }
        }
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
        jtbPnMenuVigilante = new javax.swing.JTabbedPane();
        jtabPnlPersona = new javax.swing.JTabbedPane();
        ContenedorConsultarPersona = new javax.swing.JPanel();
        pnlSurError = new javax.swing.JPanel();
        lb_error = new javax.swing.JLabel();
        Busqueda = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        contenedorRadios = new javax.swing.JPanel();
        jrbtn_identificacion = new javax.swing.JRadioButton();
        jrbtn_codigo = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jtxt_rol = new javax.swing.JTextField();
        ContenedorTblVehiculos = new javax.swing.JPanel();
        jscrlPnlTblVehiculos1 = new javax.swing.JScrollPane();
        tblVehiculosXPersona = new javax.swing.JTable();
        ContenedorRegistrarPersona = new javax.swing.JPanel();
        jPanelNorth = new javax.swing.JPanel();
        jPanelWest = new javax.swing.JPanel();
        jPanelEast = new javax.swing.JPanel();
        jPanelCenter = new javax.swing.JPanel();
        jPanelRPCenter = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtx_identificacion_rp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtx_nombre_rp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxt_apellido_rp = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jcbx_genero_rp = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jdc_fecha_rp = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jcbx_rol_pr = new javax.swing.JComboBox<>();
        jPanel_relleno = new javax.swing.JPanel();
        jPanelAux = new javax.swing.JPanel();
        jbtn_add_persona_rp = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanelSouth = new javax.swing.JPanel();
        pnlAsignarBahia = new javax.swing.JPanel();
        pnlContenedorNorte = new javax.swing.JPanel();
        contenedorIndicacionesEstado = new javax.swing.JPanel();
        auxInfNort = new javax.swing.JPanel();
        pnlContenedorInfo = new javax.swing.JPanel();
        pnlOcupado1 = new javax.swing.JPanel();
        jlbLibre1 = new javax.swing.JLabel();
        pnlOcupado2 = new javax.swing.JPanel();
        jlbLibre2 = new javax.swing.JLabel();
        auxInfSur = new javax.swing.JPanel();
        pnlResidencias = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        auxSouth = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlContenedorBosquejo = new javax.swing.JPanel();
        sur = new javax.swing.JPanel();
        jlbl_error = new javax.swing.JLabel();
        jTabbedPaneVehiculo = new javax.swing.JTabbedPane();
        pnlConsultarVehiculo = new javax.swing.JPanel();
        pnlBusquedaMulta = new javax.swing.JPanel();
        pnlContenedorBusqueda = new javax.swing.JPanel();
        auxBusquedaNort = new javax.swing.JPanel();
        auxbusquedaSur = new javax.swing.JPanel();
        jlbl_placa = new javax.swing.JLabel();
        jtxt_placa = new javax.swing.JTextField();
        jbtn_buscar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jbtn_asignar = new javax.swing.JButton();
        pnlCentro = new javax.swing.JPanel();
        ContenedorTblVehiculosMultas = new javax.swing.JPanel();
        jscrlPnlTblVehiculosMulta = new javax.swing.JScrollPane();
        tblVehiculosMulta = new javax.swing.JTable();
        pnlSurMulta = new javax.swing.JPanel();
        jPanelRegistrarVh1 = new javax.swing.JPanel();
        jPanelNorth2 = new javax.swing.JPanel();
        jPanelWest2 = new javax.swing.JPanel();
        jPanelCenter2 = new javax.swing.JPanel();
        contenedorCamposMultar = new javax.swing.JPanel();
        Placa = new javax.swing.JLabel();
        jtxt_placa_rec = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxt_area_descripcion = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jtxt_foto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanelRVSuouth1 = new javax.swing.JPanel();
        jbtn_add_multa = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        pnlContenedorBusqueda1 = new javax.swing.JPanel();
        auxBusquedaNort1 = new javax.swing.JPanel();
        auxbusquedaSur1 = new javax.swing.JPanel();
        jlbl_placa1 = new javax.swing.JLabel();
        jtxt_placa_multar = new javax.swing.JTextField();
        jbtn_buscar_multar = new javax.swing.JButton();
        jPanelEast2 = new javax.swing.JPanel();
        jPanelSouth2 = new javax.swing.JPanel();
        jPanelRegistrarVh = new javax.swing.JPanel();
        jPanelNorth1 = new javax.swing.JPanel();
        jPanelWest1 = new javax.swing.JPanel();
        jPanelCenter1 = new javax.swing.JPanel();
        contenedorCamposRegistro1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtxt_placa_rv = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtxt_id_rv = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtxt_marca_rv = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jcbx_tipo_rv = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanelRVSuouth = new javax.swing.JPanel();
        jbtn_add_vehiculo_rv = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanelEast1 = new javax.swing.JPanel();
        jPanelSouth1 = new javax.swing.JPanel();
        pnlReportes = new javax.swing.JPanel();
        pnlBotones = new javax.swing.JPanel();
        jbtn_repoter = new javax.swing.JButton();
        jbtn_reporte_ingreso = new javax.swing.JButton();
        pnlContenedorTabla = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbl_reporte_vehiculo = new javax.swing.JTable();

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
        jbl_menu_vigilante.setText("Menu Vigilante");
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

        jtbPnMenuVigilante.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jtabPnlPersona.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jtabPnlPersona.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        ContenedorConsultarPersona.setBackground(new java.awt.Color(207, 226, 243));
        ContenedorConsultarPersona.setLayout(new java.awt.BorderLayout());

        pnlSurError.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlSurError.add(lb_error);

        ContenedorConsultarPersona.add(pnlSurError, java.awt.BorderLayout.SOUTH);

        Busqueda.setBackground(new java.awt.Color(207, 226, 243));
        Busqueda.setMaximumSize(new java.awt.Dimension(436, 436));
        Busqueda.setMinimumSize(new java.awt.Dimension(100, 69));
        Busqueda.setLayout(new java.awt.GridLayout(3, 0));

        jLabel5.setBackground(new java.awt.Color(207, 226, 243));
        Busqueda.add(jLabel5);

        txtId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdKeyPressed(evt);
            }
        });
        Busqueda.add(txtId);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Busqueda por:");
        Busqueda.add(jLabel2);

        contenedorRadios.setLayout(new java.awt.GridLayout(1, 0));

        jrbtn_identificacion.setBackground(new java.awt.Color(207, 226, 243));
        btnGroup.add(jrbtn_identificacion);
        jrbtn_identificacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jrbtn_identificacion.setText("Identificacion");
        jrbtn_identificacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbtn_identificacionMouseClicked(evt);
            }
        });
        jrbtn_identificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbtn_identificacionActionPerformed(evt);
            }
        });
        contenedorRadios.add(jrbtn_identificacion);

        jrbtn_codigo.setBackground(new java.awt.Color(207, 226, 243));
        btnGroup.add(jrbtn_codigo);
        jrbtn_codigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jrbtn_codigo.setText("Codigo");
        jrbtn_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbtn_codigoActionPerformed(evt);
            }
        });
        contenedorRadios.add(jrbtn_codigo);

        Busqueda.add(contenedorRadios);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Rol:");
        Busqueda.add(jLabel7);

        jtxt_rol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Busqueda.add(jtxt_rol);

        ContenedorConsultarPersona.add(Busqueda, java.awt.BorderLayout.NORTH);

        ContenedorTblVehiculos.setLayout(new java.awt.GridLayout(1, 0));

        jscrlPnlTblVehiculos1.setBackground(new java.awt.Color(0, 102, 153));

        tblVehiculosXPersona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVehiculosXPersona.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVehiculosXPersona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVehiculosXPersonaMouseClicked(evt);
            }
        });
        jscrlPnlTblVehiculos1.setViewportView(tblVehiculosXPersona);

        ContenedorTblVehiculos.add(jscrlPnlTblVehiculos1);

        ContenedorConsultarPersona.add(ContenedorTblVehiculos, java.awt.BorderLayout.CENTER);

        jtabPnlPersona.addTab("Consultar ", ContenedorConsultarPersona);

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

        jtx_identificacion_rp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtx_identificacion_rpKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtx_identificacion_rp);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("  Nombre");
        jPanelRPCenter.add(jLabel10);

        jtx_nombre_rp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtx_nombre_rpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtx_nombre_rpKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtx_nombre_rp);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("  Apellido");
        jPanelRPCenter.add(jLabel11);

        jtxt_apellido_rp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_apellido_rpKeyTyped(evt);
            }
        });
        jPanelRPCenter.add(jtxt_apellido_rp);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("  Genero");
        jPanelRPCenter.add(jLabel12);

        jcbx_genero_rp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione genero", "MASCULINO", "FEMENINO" }));
        jPanelRPCenter.add(jcbx_genero_rp);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("  Fecha nacimiento");
        jPanelRPCenter.add(jLabel13);
        jPanelRPCenter.add(jdc_fecha_rp);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("  Rol");
        jPanelRPCenter.add(jLabel8);

        jcbx_rol_pr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Rol", "DOCENTE", "VISITANTE", "ESTUDIANTE", "ADMINISTRATIVO" }));
        jPanelRPCenter.add(jcbx_rol_pr);

        jPanel_relleno.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRPCenter.add(jPanel_relleno);

        jPanelAux.setLayout(new java.awt.BorderLayout());

        jbtn_add_persona_rp.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_add_persona_rp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_add_persona_rp.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_add_persona_rp.setText("Guardar");
        jbtn_add_persona_rp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_persona_rpActionPerformed(evt);
            }
        });
        jPanelAux.add(jbtn_add_persona_rp, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(207, 226, 243));
        jPanelAux.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanelRPCenter.add(jPanelAux);

        jPanelCenter.add(jPanelRPCenter, java.awt.BorderLayout.NORTH);

        ContenedorRegistrarPersona.add(jPanelCenter, java.awt.BorderLayout.CENTER);
        ContenedorRegistrarPersona.add(jPanelSouth, java.awt.BorderLayout.PAGE_END);

        jtabPnlPersona.addTab("Registrar", ContenedorRegistrarPersona);

        jtbPnMenuVigilante.addTab("Persona", jtabPnlPersona);

        pnlAsignarBahia.setLayout(new java.awt.BorderLayout());

        pnlContenedorNorte.setLayout(new java.awt.BorderLayout());

        contenedorIndicacionesEstado.setBackground(new java.awt.Color(207, 226, 243));
        contenedorIndicacionesEstado.setLayout(new java.awt.BorderLayout());

        auxInfNort.setBackground(new java.awt.Color(207, 226, 243));
        contenedorIndicacionesEstado.add(auxInfNort, java.awt.BorderLayout.NORTH);

        pnlContenedorInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pnlOcupado1.setBackground(new java.awt.Color(51, 255, 0));
        pnlOcupado1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlOcupado1.setForeground(new java.awt.Color(255, 255, 255));
        pnlOcupado1.setLayout(new java.awt.GridLayout(1, 0));

        jlbLibre1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jlbLibre1.setText("Ocupado");
        pnlOcupado1.add(jlbLibre1);

        pnlContenedorInfo.add(pnlOcupado1);

        pnlOcupado2.setBackground(new java.awt.Color(255, 255, 255));
        pnlOcupado2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlOcupado2.setForeground(new java.awt.Color(255, 255, 255));
        pnlOcupado2.setLayout(new java.awt.GridLayout(1, 0));

        jlbLibre2.setBackground(new java.awt.Color(216, 219, 226));
        jlbLibre2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jlbLibre2.setText("Libre");
        pnlOcupado2.add(jlbLibre2);

        pnlContenedorInfo.add(pnlOcupado2);

        contenedorIndicacionesEstado.add(pnlContenedorInfo, java.awt.BorderLayout.EAST);

        auxInfSur.setBackground(new java.awt.Color(207, 226, 243));
        contenedorIndicacionesEstado.add(auxInfSur, java.awt.BorderLayout.PAGE_END);

        pnlContenedorNorte.add(contenedorIndicacionesEstado, java.awt.BorderLayout.NORTH);

        pnlResidencias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Residencias");
        pnlResidencias.add(jLabel6);

        pnlContenedorNorte.add(pnlResidencias, java.awt.BorderLayout.CENTER);

        auxSouth.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorNorte.add(auxSouth, java.awt.BorderLayout.SOUTH);

        pnlAsignarBahia.add(pnlContenedorNorte, java.awt.BorderLayout.PAGE_START);

        pnlContenedorBosquejo.setLayout(new java.awt.GridLayout(2, 0, 30, 20));
        jScrollPane1.setViewportView(pnlContenedorBosquejo);

        pnlAsignarBahia.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        sur.add(jlbl_error);

        pnlAsignarBahia.add(sur, java.awt.BorderLayout.PAGE_END);

        jtbPnMenuVigilante.addTab("Asignar Bahia", pnlAsignarBahia);

        jTabbedPaneVehiculo.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPaneVehiculo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        pnlConsultarVehiculo.setLayout(new java.awt.BorderLayout());

        pnlBusquedaMulta.setLayout(new java.awt.BorderLayout());

        pnlContenedorBusqueda.setLayout(new java.awt.BorderLayout(3, 0));

        auxBusquedaNort.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda.add(auxBusquedaNort, java.awt.BorderLayout.PAGE_START);

        auxbusquedaSur.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda.add(auxbusquedaSur, java.awt.BorderLayout.PAGE_END);

        jlbl_placa.setBackground(new java.awt.Color(207, 226, 243));
        jlbl_placa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_placa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/multa.png"))); // NOI18N
        jlbl_placa.setText("Placa Vehiculo");
        pnlContenedorBusqueda.add(jlbl_placa, java.awt.BorderLayout.LINE_START);

        jtxt_placa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_placaKeyTyped(evt);
            }
        });
        pnlContenedorBusqueda.add(jtxt_placa, java.awt.BorderLayout.CENTER);

        jbtn_buscar.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_buscar.setText("Buscar");
        jbtn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_buscarActionPerformed(evt);
            }
        });
        pnlContenedorBusqueda.add(jbtn_buscar, java.awt.BorderLayout.LINE_END);

        pnlBusquedaMulta.add(pnlContenedorBusqueda, java.awt.BorderLayout.PAGE_START);

        pnlConsultarVehiculo.add(pnlBusquedaMulta, java.awt.BorderLayout.NORTH);

        jbtn_asignar.setText("Asignar");
        jbtn_asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_asignarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_asignar);

        pnlConsultarVehiculo.add(jPanel1, java.awt.BorderLayout.LINE_END);

        pnlCentro.setLayout(new java.awt.BorderLayout());

        ContenedorTblVehiculosMultas.setLayout(new java.awt.GridLayout(1, 0));

        jscrlPnlTblVehiculosMulta.setBackground(new java.awt.Color(0, 102, 153));

        tblVehiculosMulta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVehiculosMulta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVehiculosMulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVehiculosMultaMouseClicked(evt);
            }
        });
        jscrlPnlTblVehiculosMulta.setViewportView(tblVehiculosMulta);

        ContenedorTblVehiculosMultas.add(jscrlPnlTblVehiculosMulta);

        pnlCentro.add(ContenedorTblVehiculosMultas, java.awt.BorderLayout.CENTER);

        pnlConsultarVehiculo.add(pnlCentro, java.awt.BorderLayout.CENTER);
        pnlConsultarVehiculo.add(pnlSurMulta, java.awt.BorderLayout.PAGE_END);

        jTabbedPaneVehiculo.addTab("Consultar", pnlConsultarVehiculo);

        jPanelRegistrarVh1.setLayout(new java.awt.BorderLayout());
        jPanelRegistrarVh1.add(jPanelNorth2, java.awt.BorderLayout.NORTH);
        jPanelRegistrarVh1.add(jPanelWest2, java.awt.BorderLayout.WEST);

        jPanelCenter2.setBackground(new java.awt.Color(207, 226, 243));
        jPanelCenter2.setLayout(new java.awt.BorderLayout());

        contenedorCamposMultar.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposMultar.setLayout(new java.awt.GridLayout(0, 2));

        Placa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Placa.setText("  Placa");
        contenedorCamposMultar.add(Placa);

        jtxt_placa_rec.setEditable(false);
        contenedorCamposMultar.add(jtxt_placa_rec);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("  Descripción");
        contenedorCamposMultar.add(jLabel18);

        jtxt_area_descripcion.setColumns(20);
        jtxt_area_descripcion.setRows(5);
        jtxt_area_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_area_descripcionKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtxt_area_descripcion);

        contenedorCamposMultar.add(jScrollPane2);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("  Foto");
        contenedorCamposMultar.add(jLabel20);

        jtxt_foto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_fotoKeyTyped(evt);
            }
        });
        contenedorCamposMultar.add(jtxt_foto);

        jPanel3.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposMultar.add(jPanel3);

        jPanelRVSuouth1.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth1.setLayout(new java.awt.BorderLayout());

        jbtn_add_multa.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_add_multa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_add_multa.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_add_multa.setText("Guardar");
        jbtn_add_multa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_multaActionPerformed(evt);
            }
        });
        jPanelRVSuouth1.add(jbtn_add_multa, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth1.add(jPanel6, java.awt.BorderLayout.NORTH);

        contenedorCamposMultar.add(jPanelRVSuouth1);

        jPanelCenter2.add(contenedorCamposMultar, java.awt.BorderLayout.CENTER);

        pnlContenedorBusqueda1.setLayout(new java.awt.BorderLayout(3, 0));

        auxBusquedaNort1.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda1.add(auxBusquedaNort1, java.awt.BorderLayout.PAGE_START);

        auxbusquedaSur1.setBackground(new java.awt.Color(207, 226, 243));
        pnlContenedorBusqueda1.add(auxbusquedaSur1, java.awt.BorderLayout.PAGE_END);

        jlbl_placa1.setBackground(new java.awt.Color(207, 226, 243));
        jlbl_placa1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_placa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/multa.png"))); // NOI18N
        jlbl_placa1.setText("Placa Vehiculo");
        pnlContenedorBusqueda1.add(jlbl_placa1, java.awt.BorderLayout.LINE_START);

        jtxt_placa_multar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_placa_multar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_placa_multarKeyTyped(evt);
            }
        });
        pnlContenedorBusqueda1.add(jtxt_placa_multar, java.awt.BorderLayout.CENTER);

        jbtn_buscar_multar.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_buscar_multar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_buscar_multar.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_buscar_multar.setText("Buscar");
        jbtn_buscar_multar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_buscar_multarActionPerformed(evt);
            }
        });
        pnlContenedorBusqueda1.add(jbtn_buscar_multar, java.awt.BorderLayout.LINE_END);

        jPanelCenter2.add(pnlContenedorBusqueda1, java.awt.BorderLayout.PAGE_START);

        jPanelRegistrarVh1.add(jPanelCenter2, java.awt.BorderLayout.CENTER);
        jPanelRegistrarVh1.add(jPanelEast2, java.awt.BorderLayout.EAST);
        jPanelRegistrarVh1.add(jPanelSouth2, java.awt.BorderLayout.PAGE_END);

        jTabbedPaneVehiculo.addTab("Multar", jPanelRegistrarVh1);

        jPanelRegistrarVh.setLayout(new java.awt.BorderLayout());
        jPanelRegistrarVh.add(jPanelNorth1, java.awt.BorderLayout.NORTH);
        jPanelRegistrarVh.add(jPanelWest1, java.awt.BorderLayout.WEST);

        jPanelCenter1.setBackground(new java.awt.Color(207, 226, 243));
        jPanelCenter1.setLayout(new java.awt.BorderLayout());

        contenedorCamposRegistro1.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposRegistro1.setLayout(new java.awt.GridLayout(0, 2));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("  Placa");
        contenedorCamposRegistro1.add(jLabel14);

        jtxt_placa_rv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_placa_rvKeyTyped(evt);
            }
        });
        contenedorCamposRegistro1.add(jtxt_placa_rv);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("  Identificacion propietario");
        contenedorCamposRegistro1.add(jLabel15);

        jtxt_id_rv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_id_rvKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_id_rvKeyTyped(evt);
            }
        });
        contenedorCamposRegistro1.add(jtxt_id_rv);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("  Marca Vehiculo");
        contenedorCamposRegistro1.add(jLabel16);

        jtxt_marca_rv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_marca_rvKeyTyped(evt);
            }
        });
        contenedorCamposRegistro1.add(jtxt_marca_rv);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("  Tipo Vehiculo");
        contenedorCamposRegistro1.add(jLabel17);

        jcbx_tipo_rv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione tipo", "MOTO", "CARRO" }));
        contenedorCamposRegistro1.add(jcbx_tipo_rv);

        jPanel2.setBackground(new java.awt.Color(207, 226, 243));
        contenedorCamposRegistro1.add(jPanel2);

        jPanelRVSuouth.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth.setLayout(new java.awt.BorderLayout());

        jbtn_add_vehiculo_rv.setBackground(new java.awt.Color(224, 102, 102));
        jbtn_add_vehiculo_rv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtn_add_vehiculo_rv.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_add_vehiculo_rv.setText("Guardar");
        jbtn_add_vehiculo_rv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_vehiculo_rvActionPerformed(evt);
            }
        });
        jPanelRVSuouth.add(jbtn_add_vehiculo_rv, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(207, 226, 243));
        jPanelRVSuouth.add(jPanel5, java.awt.BorderLayout.NORTH);

        contenedorCamposRegistro1.add(jPanelRVSuouth);

        jPanelCenter1.add(contenedorCamposRegistro1, java.awt.BorderLayout.NORTH);

        jPanelRegistrarVh.add(jPanelCenter1, java.awt.BorderLayout.CENTER);
        jPanelRegistrarVh.add(jPanelEast1, java.awt.BorderLayout.EAST);
        jPanelRegistrarVh.add(jPanelSouth1, java.awt.BorderLayout.PAGE_END);

        jTabbedPaneVehiculo.addTab("Registrar", jPanelRegistrarVh);

        jtbPnMenuVigilante.addTab("Vehiculo", jTabbedPaneVehiculo);

        pnlReportes.setLayout(new java.awt.BorderLayout());

        jbtn_repoter.setText("Reporte horas de congestion");
        jbtn_repoter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_repoterActionPerformed(evt);
            }
        });
        pnlBotones.add(jbtn_repoter);

        jbtn_reporte_ingreso.setText("Reporte ingreso");
        jbtn_reporte_ingreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_reporte_ingresoActionPerformed(evt);
            }
        });
        pnlBotones.add(jbtn_reporte_ingreso);

        pnlReportes.add(pnlBotones, java.awt.BorderLayout.NORTH);

        pnlContenedorTabla.setLayout(new java.awt.BorderLayout());

        jtbl_reporte_vehiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jtbl_reporte_vehiculo);

        pnlContenedorTabla.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        pnlReportes.add(pnlContenedorTabla, java.awt.BorderLayout.CENTER);

        jtbPnMenuVigilante.addTab("Reportes", pnlReportes);

        getContentPane().add(jtbPnMenuVigilante, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_add_vehiculo_rvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_vehiculo_rvActionPerformed
        // TODO add your handling code here:
        String placa = jtxt_placa_rv.getText();
        String identificacio_prop = jtxt_id_rv.getText();
        String marca = jtxt_marca_rv.getText();
        String tipo = jcbx_tipo_rv.getSelectedItem().toString();

        GestorVehiculos obj_gv = new GestorVehiculos();
        if(!obj_gv.adicionarVehiculo(placa, identificacio_prop, marca, tipo)){
            Utilidades.mensajeAdvertencia("Problemas al registrar vehiculo", "Aviso");
        }else{
            Utilidades.mensajeExito("Vehiculo registrado exitosamente!", "Exito");
        }
        limpiarFormularioAddVehiculo();
    }//GEN-LAST:event_jbtn_add_vehiculo_rvActionPerformed

    private void limpiarFormularioAddVehiculo(){
        jtxt_placa_rv.setText("");
        jtxt_id_rv.setText("");
        jtxt_marca_rv.setText("");
        jcbx_tipo_rv.setSelectedIndex(0);
    }
    private void jtxt_id_rvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_id_rvKeyReleased
        String placa = jtxt_placa_rv.getText().trim();
        if(placa.equals("")){
            GestorVehiculos obj_gv = new GestorVehiculos();
            Vehiculo vh = obj_gv.existeVehiculo(placa);
            if(vh!=null){
                Utilidades.mensajeAdvertencia("Vehiculo ya esta registrado.. en proceso", "Aviso");
                jtxt_placa_rv.setText(vh.getPlaca());
                jtxt_id_rv.setText(vh.getIdentificacion());
                jtxt_marca_rv.setText(vh.getMarca());
                jcbx_tipo_rv.setSelectedItem(vh.getTipo());
            }
        }
    }//GEN-LAST:event_jtxt_id_rvKeyReleased

    private void jbtn_add_persona_rpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_persona_rpActionPerformed
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        GestorPersona obj_gs_persona = new GestorPersona();
        GestorRol obj_gs_rol = new GestorRol();

        if(validarFormularioPerson()){
            String id = jtx_identificacion_rp.getText().trim();
            String nombre = jtx_nombre_rp.getText().trim();
            String apellido = jtxt_apellido_rp.getText().trim();
            String fecha_nacimiento = formato.format(jdc_fecha_rp.getDate());
            String genero = jcbx_genero_rp.getSelectedItem().toString();
            String rol_nombre = jcbx_rol_pr.getSelectedItem().toString();

            if(!obj_gs_persona.adicionarPersona(id, nombre, apellido, genero, fecha_nacimiento)){
                Utilidades.mensajeError("Problemas al ingresar registro", "Atencion");
            }else{
                if(!obj_gs_rol.adicionarRol(""+(obj_gs_rol.ultimoId()+1), id, rol_nombre)){
                    Utilidades.mensajeError("Problemas al ingresar registro con rol", "Atencion");
                }else{
                    Utilidades.mensajeExito("Registrado exitosamente!", "Exito");
                    limpiarFomularioRegistrarPersona();
                }
            }
        }
    }//GEN-LAST:event_jbtn_add_persona_rpActionPerformed

    private void limpiarFomularioRegistrarPersona(){
        jtx_identificacion_rp.setText("");
        jtx_nombre_rp.setText("");
        jtxt_apellido_rp.setText("");
        jcbx_genero_rp.setSelectedIndex(0);
        jcbx_rol_pr.setSelectedIndex(0);
    }
    private void tblVehiculosXPersonaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVehiculosXPersonaMouseClicked
        int seleccionada = tblVehiculosXPersona.rowAtPoint(evt.getPoint());
        //capturar los datos de una fila seleccionada
        DefaultTableModel dfm = (DefaultTableModel) tblVehiculosXPersona.getModel();
        v = (Vector) dfm.getDataVector().get(seleccionada);
        //clicktabla
        jtbPnMenuVigilante.setSelectedIndex(1);
        //System.out.println("My " + v.toString());
        jtbPnMenuVigilante.setEnabledAt(1, true); //desbloquear un pestania
        pnlContenedorBosquejo.setVisible(true);
        
    }//GEN-LAST:event_tblVehiculosXPersonaMouseClicked

    private void jrbtn_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbtn_codigoActionPerformed

        String id = txtId.getText().trim();

        if (jrbtn_codigo.isSelected()) {
            lb_error.setText("");
            if (id.equals("")) {
                validarFormulario();
                return;
            }
            setId(id);
            GestorVehiculos gestor_veh = new GestorVehiculos();
            GestorRol gestor_rol = new GestorRol();
            try {
                Rol obj_rol = gestor_rol.obtenerRolPerDeServidorCentral(id);
                if (obj_rol == null) {
                    jtxt_rol.setText("");
                } else {
                    jtxt_rol.setText(obj_rol.getRol_nom());
                }
            } catch (Exception e) {
                System.out.println("Error desde consultar nombre rol: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                Vehiculo[] listado = gestor_veh.buscarVehiculosXPersonaCodigo(id); //GARRAFAL_VACIO
                if (listado != null) {
                    llenarTabla(listado, id);
                } else {
                    inicializarTablaVhsXPersona();
                    pnlSurError.setBackground(Color.red);
                    lb_error.setForeground(Color.white);
                    lb_error.setText("No tiene vehiculos registrados");
                }
            } catch (Exception e) {
                System.out.println("ERROR al cargar la tabla " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jrbtn_codigoActionPerformed

    private void jrbtn_identificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbtn_identificacionActionPerformed
        String id = txtId.getText().trim();
        if (jrbtn_identificacion.isSelected()) {
            lb_error.setText("");
            if (id.equals("")) {
                validarFormulario();
                return;
            }
            setId(id);
            GestorVehiculos gestor_veh = new GestorVehiculos();
            GestorRol gestor_rol = new GestorRol();
            try {
                Rol obj_rol = gestor_rol.obtenerRolPerDeServidorCentral(id);
                if (obj_rol == null) {
                    jtxt_rol.setText("");
                } else {
                    jtxt_rol.setText(obj_rol.getRol_nom());
                }
            } catch (Exception e) {
                System.out.println("Error desde consultar nombre rol: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                Vehiculo[] listado = gestor_veh.buscarVehiculosXPersona(id); //GARRAFAL_VACIO
                if (listado != null) {
                    pnlSurError.setBackground(Color.LIGHT_GRAY);
                    llenarTabla(listado, id);
                } else {
                    inicializarTablaVhsXPersona();
                    pnlSurError.setBackground(Color.red);
                    lb_error.setForeground(Color.white);
                    lb_error.setText("No tiene vehiculos registrados");
                }
            } catch (Exception e) {
                System.out.println("ERROR al cargar la Tabla " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jrbtn_identificacionActionPerformed

    private void jrbtn_identificacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbtn_identificacionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbtn_identificacionMouseClicked

    private void txtIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKeyPressed

    private void jbtn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_buscarActionPerformed
        String placa_a_buscar = jtxt_placa.getText().trim();
        int cant = 0;
            if (placa_a_buscar.equals("")) {
               Utilidades.mensajeError("Ingrese una placa", "Error");
            }{
            GestorVehiculos gestor_veh = new GestorVehiculos();
            GestorMulta gest_multa = new GestorMulta();
            cant = gest_multa.cantidadMultasXplaca(placa_a_buscar);
            try {
                Vehiculo veh = gestor_veh.buscarVehiculoXPlaca(placa_a_buscar); //GARRAFAL_VACIO
                
                if (veh != null) {
                    llenarTablaAux(veh, cant);
                    jbtn_asignar.setVisible(true);
                } else {
                    inicializarTablaMulta();
                    Utilidades.mensajeError("Vehiculo no existe. Por favor registrarlo", "Sin registrar");
                    jTabbedPaneVehiculo.setSelectedIndex(2);
                    jbtn_asignar.setVisible(false);
                }
            } catch (Exception e) {
                System.out.println("ERROR al cargar la Tabla " + e.getMessage());
                e.printStackTrace();
            }
    }
        
    }//GEN-LAST:event_jbtn_buscarActionPerformed

    private void jbtn_add_multaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_multaActionPerformed
        GestorMulta gest_mult = new GestorMulta();
        String placa = jtxt_placa.getText().trim();
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         if(validarCamposMulta()){
            if(!gest_mult.agregarMulta(identificacion_vigilante_in, jtxt_area_descripcion.getText(), sdf.format(fecha),  "No hay foto", jtxt_placa_rec.getText())){
                Utilidades.mensajeError("Problemas al registrar multa", "Error");
            }else{
                Utilidades.mensajeExito("Vehiculo multado exitosamente!", "Exito");
                limpiarFormularioMulta();
            }  
         }
    }//GEN-LAST:event_jbtn_add_multaActionPerformed


    private void limpiarFormularioMulta(){
       jtxt_placa.setText("");
       jtxt_area_descripcion.setText("");
       jtxt_foto.setText("");
    }
    private void jbtn_buscar_multarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_buscar_multarActionPerformed
        GestorVehiculos gest_vehiculo = new GestorVehiculos();
        GestorMulta gest_multa = new GestorMulta();
        
        String placa = jtxt_placa_multar.getText().trim();
        if(placa.equals("")){
            Utilidades.mensajeError("Ingrese una placa", "Error");
            jtxt_placa_multar.requestFocus();
        }else{
            Vehiculo veh = gest_vehiculo.existeVehiculo(placa);
            if(veh!=null){
                jtxt_placa_rec.setText(veh.getPlaca());
                jtxt_area_descripcion.requestFocus();
            }else{
                Utilidades.mensajeAdvertencia("Vehiculo no esta registrado", "Error");
            }
        }
    }//GEN-LAST:event_jbtn_buscar_multarActionPerformed

    private void jbtn_asignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_asignarActionPerformed
        if (tblVehiculosMulta.getSelectedRow() != -1) {//Veririca si hay una fila seleccionada
            String id = "" + JOptionPane.showInputDialog("Ingrese una identificacion");
            GestorPersona gest_per = new GestorPersona();
            Persona per = gest_per.existePersona(id);
            if (per != null) {
                GestorVehiculos gest_veh = new GestorVehiculos();
                if (!gest_veh.asociarVehiculo(id, "" + getVec_aux().get(1))) {
                    Utilidades.mensajeError("Problemas al asociar el vehiculo", "Error");
                } else {
                    Utilidades.mensajeExito("Vehiculo asociado exitosamente", "Exito");
                }
            }
        } else {
            Utilidades.mensajeAdvertencia("Seleccione un registro de la tabla", "Advertencia");
        }
    }//GEN-LAST:event_jbtn_asignarActionPerformed

    private void tblVehiculosMultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVehiculosMultaMouseClicked
        int seleccionada = tblVehiculosMulta.rowAtPoint(evt.getPoint());

        //capturar los datos de una fila seleccionada
        DefaultTableModel dfm = (DefaultTableModel) tblVehiculosMulta.getModel();
        setVec_aux((Vector) dfm.getDataVector().get(seleccionada));
    }//GEN-LAST:event_tblVehiculosMultaMouseClicked

    private void jtx_nombre_rpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_nombre_rpKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtx_nombre_rpKeyTyped

    private void jtx_nombre_rpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_nombre_rpKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtx_nombre_rpKeyPressed

    private void jtx_identificacion_rpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtx_identificacion_rpKeyTyped
         if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jtx_identificacion_rpKeyTyped

    private void jtxt_apellido_rpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_apellido_rpKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_apellido_rpKeyTyped

    private void jtxt_placaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_placaKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_placaKeyTyped

    private void jtxt_placa_multarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_placa_multarKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_placa_multarKeyTyped

    private void jtxt_area_descripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_area_descripcionKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_area_descripcionKeyTyped

    private void jtxt_fotoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_fotoKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_fotoKeyTyped

    private void jtxt_placa_rvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_placa_rvKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_placa_rvKeyTyped

    private void jtxt_id_rvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_id_rvKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_id_rvKeyTyped

    private void jtxt_marca_rvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_marca_rvKeyTyped
        char c = evt.getKeyChar();
        //Colocar mayusculas lo que se ingrese
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_jtxt_marca_rvKeyTyped

    private void jbtn_nom_vigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_nom_vigActionPerformed
        this.dispose();
        GUILOGIN obj_login = new GUILOGIN();
        obj_login.setVisible(true);
    }//GEN-LAST:event_jbtn_nom_vigActionPerformed

    private void jbtn_repoterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_repoterActionPerformed
        // TODO add your handling code here:
        try {
            ConectorJdbc con = new ConectorJdbc();
            Connection conn = con.getConecion();
            JasperReport reporte;
            String path = "C:\\Users\\Guido\\Documents\\NetBeansProjects\\Taller4-cliente-servidor\\Parqueadero\\src\\Reporte\\myReport.jasper";
//            String path = "C:\\Users\\Guido\\Documents\\NetBeansProjects\\Taller4-cliente-servidor\\Parqueadero\\src\\Reporte\\ReporteHorasCongestion.jasper";
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path); //Se carga el reporte de su localizacion
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conn); //Agregamos los parametros para llenar el reporte
            JasperViewer viewer = new JasperViewer(jprint, false); //Se crea la vista del reportes
            viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Se declara con dispose_on_close para que no se cierre el programa cuando se cierre el reporte
            viewer.setVisible(true); //Se vizualiza el reporte
        } catch (JRException | ClassNotFoundException ex) {
            Logger.getLogger(MenuVigilante.class.getName()).log(Level.SEVERE, null, ex);
            //Utilidades.Utilidades.mensajeError(ex.getMessage(), "Error");
        }
    }//GEN-LAST:event_jbtn_repoterActionPerformed

    private void jbtn_reporte_ingresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_reporte_ingresoActionPerformed
          String placa = ""+JOptionPane.showInputDialog(rootPane, "Ingrese una placa: ");
          GestorParqueo g_p = new GestorParqueo();
          if(!placa.isEmpty()){
            llenarTablaReporteEntradaVeh(g_p.historialParqueo(placa));
          }else{
              Utilidades.mensajeError("No ingreso una placa", "Error");
          }
          
    }//GEN-LAST:event_jbtn_reporte_ingresoActionPerformed
   
    private boolean validarCamposMulta() {
        String placa = jtxt_placa_rec.getText().trim();
        String descripcion = jtxt_area_descripcion.getText().trim();
        if (placa.equals("")) {
            Utilidades.mensajeError("Ingrese una placa", "Error");
            jtxt_placa_rec.requestFocus();
            return false;
        }
        if (descripcion.equals("")) {
            Utilidades.mensajeError("Ingrese una descripcion ", "Error");
            jtxt_area_descripcion.requestFocus();
            return false;
        }
        return true;
    }
    static Vector v;
    public static Vector datosSeleccionado(){
        return v;
    }
    /**
     * Valida que los datos estén correctamente diligenciados en el formulario
     *
     * @return true si están bien diligenciados, false en caso contrario
     */
    public boolean validarFormulario() {
        if (this.getId().equals("")) {
            Utilidades.mensajeAdvertencia("Debe agregar la identificación del involucrado", "Atención");
            txtId.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validarFormularioPerson() {
        if (jtx_identificacion_rp.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese identificacion", "Atencion");
            jtx_identificacion_rp.requestFocus();
            return false;
        }
        if (jtx_nombre_rp.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese nombre", "Atencion");
            jtx_nombre_rp.requestFocus();
            return false;
        }
        if (jtxt_apellido_rp.getText().equals("")) {
            Utilidades.mensajeAdvertencia("Ingrese apellido", "Atencion");
            jtxt_apellido_rp.requestFocus();
            return false;
        }
        if (jcbx_genero_rp.getSelectedIndex() == 0) {
            Utilidades.mensajeAdvertencia("Seleccione un genero", "Atencion");
            return false;
        }
        if(jdc_fecha_rp.getDate()==null){
            Utilidades.mensajeAdvertencia("Seleccione una fecha", "Atencion");
            return false;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            Date fecha_actual = new Date();
            if(Utilidades.fechaEsPosterior(jdc_fecha_rp.getDate(), fecha_actual)){
                Utilidades.mensajeAdvertencia("Seleccione una fecha valida", "Atencion");
                return false;
            }
        
        }
        if (jcbx_rol_pr.getSelectedIndex() == 0) {
            Utilidades.mensajeAdvertencia("Seleccione un rol", "Atencion");
            return false;
        }
        return true;
    }

    public void setId(String dato) {
        txtId.setText(dato);
    }

    public String getId() {
        return txtId.getText().trim();
    }

    public String getRol() {
        return jtxt_rol.getText();
    }

    public void limpiarCajas() {
        //if (todas) {
        txtId.setText("");
        jtxt_rol.setText("");
        //}
    }

    private void inicializarTablaVhsXPersona() {
        tblVehiculosXPersona.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Id/cod", "Placa", "Marca", "Tipo"
                }
        ));
    }

    private void inicializarTablaMulta() {
        tblVehiculosMulta.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Identificacion ", "Placa", "Marca", "Tipo", "Multas"
                }
        ));
    }
    
     private void inicializarTablaReporteEntradasVehiculo() {
        jtbl_reporte_vehiculo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Placa", "fechaIngreso", "bahia parqueada"
                }
        ));
    }
     
    private void llenarTablaReporteEntradaVeh(ArrayList<Parqueo> p) {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        dt.addColumn("Placa");
        dt.addColumn("Fecha ingreso");
        dt.addColumn("Bahia");

        Object rowData[] = new Object[7];

        for (Parqueo objPregun : p) {
            rowData[0] = objPregun.getVehplaca();
            rowData[1] = objPregun.getFechaingreso();
            rowData[2] = objPregun.getBahid();
            dt.addRow(rowData);
        }
        jtbl_reporte_vehiculo.setModel(dt);
        jtbl_reporte_vehiculo.setRowHeight(32);
    }

    private void llenarTabla(Vehiculo[] vehiculos, String id) {
        this.inicializarTablaVhsXPersona();
        DefaultTableModel model = (DefaultTableModel) tblVehiculosXPersona.getModel();

        Object rowData[] = new Object[7];

        for (Vehiculo veh : vehiculos) {
            if(veh.getIdentificacion().equals(id)){
                rowData[0] = veh.getIdentificacion()+" [Propiedad]";
            }else{
                rowData[0] = veh.getIdentificacion()+" [Asociado]";
            }
            rowData[1] = veh.getPlaca();
            rowData[2] = veh.getMarca();
            rowData[3] = veh.getTipo();
            model.addRow(rowData);
        }
        tblVehiculosXPersona.setRowHeight(32);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Busqueda;
    private javax.swing.JPanel ContenedorConsultarPersona;
    private javax.swing.JPanel ContenedorRegistrarPersona;
    private javax.swing.JPanel ContenedorTblVehiculos;
    private javax.swing.JPanel ContenedorTblVehiculosMultas;
    private javax.swing.JPanel Jpnl_norte_m_vigilante;
    private javax.swing.JPanel Norte;
    private javax.swing.JLabel Placa;
    private javax.swing.JPanel auxBusquedaNort;
    private javax.swing.JPanel auxBusquedaNort1;
    private javax.swing.JPanel auxInfNort;
    private javax.swing.JPanel auxInfSur;
    private javax.swing.JPanel auxSouth;
    private javax.swing.JPanel auxbusquedaSur;
    private javax.swing.JPanel auxbusquedaSur1;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JPanel contenedorCamposMultar;
    private javax.swing.JPanel contenedorCamposRegistro1;
    private javax.swing.JPanel contenedorIndicacionesEstado;
    private javax.swing.JPanel contenedorRadios;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelAux;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelCenter1;
    private javax.swing.JPanel jPanelCenter2;
    private javax.swing.JPanel jPanelEast;
    private javax.swing.JPanel jPanelEast1;
    private javax.swing.JPanel jPanelEast2;
    private javax.swing.JPanel jPanelNorth;
    private javax.swing.JPanel jPanelNorth1;
    private javax.swing.JPanel jPanelNorth2;
    private javax.swing.JPanel jPanelRPCenter;
    private javax.swing.JPanel jPanelRVSuouth;
    private javax.swing.JPanel jPanelRVSuouth1;
    private javax.swing.JPanel jPanelRegistrarVh;
    private javax.swing.JPanel jPanelRegistrarVh1;
    private javax.swing.JPanel jPanelSouth;
    private javax.swing.JPanel jPanelSouth1;
    private javax.swing.JPanel jPanelSouth2;
    private javax.swing.JPanel jPanelWest;
    private javax.swing.JPanel jPanelWest1;
    private javax.swing.JPanel jPanelWest2;
    private javax.swing.JPanel jPanel_relleno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPaneVehiculo;
    private javax.swing.JLabel jbl_menu_vigilante;
    private javax.swing.JButton jbtn_add_multa;
    private javax.swing.JButton jbtn_add_persona_rp;
    private javax.swing.JButton jbtn_add_vehiculo_rv;
    private javax.swing.JButton jbtn_asignar;
    private javax.swing.JButton jbtn_buscar;
    private javax.swing.JButton jbtn_buscar_multar;
    public static javax.swing.JButton jbtn_nom_vig;
    private javax.swing.JButton jbtn_reporte_ingreso;
    private javax.swing.JButton jbtn_repoter;
    private javax.swing.JComboBox<String> jcbx_genero_rp;
    private javax.swing.JComboBox<String> jcbx_rol_pr;
    private javax.swing.JComboBox<String> jcbx_tipo_rv;
    private com.toedter.calendar.JDateChooser jdc_fecha_rp;
    private javax.swing.JLabel jlbLibre1;
    private javax.swing.JLabel jlbLibre2;
    public static javax.swing.JLabel jlbl_error;
    private javax.swing.JLabel jlbl_placa;
    private javax.swing.JLabel jlbl_placa1;
    private javax.swing.JPanel jpnl_Center_m_vigilante;
    private javax.swing.JRadioButton jrbtn_codigo;
    private javax.swing.JRadioButton jrbtn_identificacion;
    private javax.swing.JScrollPane jscrlPnlTblVehiculos1;
    private javax.swing.JScrollPane jscrlPnlTblVehiculosMulta;
    private javax.swing.JTabbedPane jtabPnlPersona;
    private javax.swing.JTabbedPane jtbPnMenuVigilante;
    private javax.swing.JTable jtbl_reporte_vehiculo;
    private javax.swing.JTextField jtx_identificacion_rp;
    private javax.swing.JTextField jtx_nombre_rp;
    private javax.swing.JTextField jtxt_apellido_rp;
    private javax.swing.JTextArea jtxt_area_descripcion;
    private javax.swing.JTextField jtxt_foto;
    private javax.swing.JTextField jtxt_id_rv;
    private javax.swing.JTextField jtxt_marca_rv;
    private javax.swing.JTextField jtxt_placa;
    private javax.swing.JTextField jtxt_placa_multar;
    private javax.swing.JTextField jtxt_placa_rec;
    private javax.swing.JTextField jtxt_placa_rv;
    private javax.swing.JTextField jtxt_rol;
    private javax.swing.JLabel lb_error;
    private javax.swing.JPanel pnlAsignarBahia;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBusquedaMulta;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlConsultarVehiculo;
    private javax.swing.JPanel pnlContenedorBosquejo;
    private javax.swing.JPanel pnlContenedorBusqueda;
    private javax.swing.JPanel pnlContenedorBusqueda1;
    private javax.swing.JPanel pnlContenedorInfo;
    private javax.swing.JPanel pnlContenedorNorte;
    private javax.swing.JPanel pnlContenedorTabla;
    private javax.swing.JPanel pnlOcupado1;
    private javax.swing.JPanel pnlOcupado2;
    private javax.swing.JPanel pnlReportes;
    private javax.swing.JPanel pnlResidencias;
    private javax.swing.JPanel pnlSurError;
    private javax.swing.JPanel pnlSurMulta;
    private javax.swing.JPanel sur;
    private javax.swing.JTable tblVehiculosMulta;
    private javax.swing.JTable tblVehiculosXPersona;
    public static javax.swing.JTextField txtId;
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

    public JRadioButton getRBtnIdentificacion() {
        return jrbtn_identificacion;
    }

    public JRadioButton getRBtnCodigo() {
        return jrbtn_codigo;
    }

    private void llenarTablaAux(Vehiculo veh, int cant) {
        
        this.inicializarTablaMulta();
        DefaultTableModel model = (DefaultTableModel) tblVehiculosMulta.getModel();

        Object rowData[] = new Object[7];

        rowData[0] = veh.getIdentificacion();
        rowData[1] = veh.getPlaca();
        rowData[2] = veh.getMarca();
        rowData[3] = veh.getTipo();
        rowData[4] = cant;
        model.addRow(rowData);

        tblVehiculosMulta.setRowHeight(32);
    }
}
