/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baleadashermanas;

import baleadashermanas.BD.ConexionBD;
import baleadashermanas.utilidades.Queries;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Carlos
 */
public class Orden extends javax.swing.JFrame {
    ArrayList<String> clientes = new ArrayList<String>();
    boolean hayDecimal = false;
    Statement stmt = null;
    Connection con = null;

    /**
     * Creates new form Orden
     */
    public Orden(String nombreUsuario) throws SQLException {
        initComponents();
        informacionGeneral();
        txt_subtotal.requestFocus();
        lbl_nombreUsuario.setText(nombreUsuario);
        this.con = ConexionBD.obtenerConexion();
        lbl_nombreProducto.setVisible(false);
        //lbl_idFactura.setVisible(false);
        buscarClientes();
        clientesArray();
    }

    public Orden() {

    }

    public void informacionGeneral() {
        this.setTitle("Ordenes");
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("../Img/Titulo.png")).getImage());
        PlaceHolder holder;
    }

    public void buscarClientes() {
        ArrayList<String> lista = new ArrayList<String>();
        try {
            lista = new Queries().llenarClientes();
            for (int i = 0; i < lista.size(); i++) {
                cmb_cliente.addItem(lista.get(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clientesArray(){
        ArrayList<String> lista = new ArrayList<String>();
        clientes.add("0|Consumidor Final");
        try {
            lista = new Queries().llenarID();
            for (int i = 0; i < lista.size(); i++) {
                clientes.add(lista.get(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarDatos() {
        try {
            String nombreProducto = lbl_nombreProducto.getText();
            String sql = "SELECT * FROM inventario where nombreproducto = '" + nombreProducto + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            double subtotal = 0;
            while (rs.next()) {
                Locale locale = new Locale("es", "HN");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                DefaultTableModel model = (DefaultTableModel) tbl_orden.getModel();
                String[] datos = new String[3];
                datos[0] = rs.getString("nombreproducto");
                datos[1] = "1";
                String precio = currencyFormatter.format(rs.getDouble("precio")).substring(1);
                datos[2] = precio;
                model.addRow(datos);
                subtotal = rs.getDouble("precio");
            }
            double totalAnterior = Double.parseDouble(txt_total.getText());
            double totalNuevo = totalAnterior + subtotal;

            double isv = totalNuevo * 0.15;
            double subtotalConImpuesto = totalNuevo - isv;

            double total = subtotalConImpuesto + isv;

            String subtotalFinal = String.valueOf(subtotalConImpuesto);
            String isvFinal = String.valueOf(isv);
            String totalFinal = String.valueOf(total);
            txt_subtotal.setText(subtotalFinal);
            txt_isv.setText(isvFinal);
            txt_total.setText(totalFinal);
            habilitarNumeros();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void cambio() {
        Locale locale = new Locale("es", "HN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String pagoCliente = txt_pago.getText();
        double pago = Double.parseDouble(pagoCliente);
        double total = Double.parseDouble(txt_total.getText());
        double cambio = pago - total;
        String cambioTotal = currencyFormatter.format(cambio);
        txt_cambio.setText(cambioTotal);
    }

    public void habilitarNumeros() {
        btn_retroceso.setEnabled(true);
        btn_punto.setEnabled(true);
        btn_0.setEnabled(true);
        btn_1.setEnabled(true);
        btn_2.setEnabled(true);
        btn_3.setEnabled(true);
        btn_4.setEnabled(true);
        btn_5.setEnabled(true);
        btn_6.setEnabled(true);
        btn_7.setEnabled(true);
        btn_8.setEnabled(true);
        btn_9.setEnabled(true);
    }

    public String capturarIdEmpleado() {
        String idEmpleado = "";
        try {
            Statement st = con.createStatement();
            String sql = "Select idempleado from empleado where usuario_empleado = '" + lbl_nombreUsuario.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                idEmpleado = rs.getString("idempleado");
                return idEmpleado;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idEmpleado;
    }

    public String capturarIdCliente() {
        String idCliente = "";
        try {
            Statement st = con.createStatement();
            
            String cliente = cmb_cliente.getSelectedItem().toString();
            String[] textoSeparado = cliente.split(".");
            for (int i = 0; i < textoSeparado.length; i++) {
                String string = textoSeparado[i];
                JOptionPane.showMessageDialog(this,string);
            }
            
            
            
            //String sql = "Select idcliente from cliente where id_cliente = '" w+ lbl_nombreUsuario.getText() + "'";
            /*ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                idCliente = rs.getString("idcliente");
                return idCliente;
            }*/ 
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idCliente;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jpn_principal = new javax.swing.JPanel();
        lbl_nombreUsuario = new javax.swing.JLabel();
        lbl_home = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        lbl_tituloClientes = new javax.swing.JLabel();
        jpn_numeros = new javax.swing.JPanel();
        btn_9 = new javax.swing.JButton();
        btn_7 = new javax.swing.JButton();
        btn_8 = new javax.swing.JButton();
        btn_4 = new javax.swing.JButton();
        btn_5 = new javax.swing.JButton();
        btn_6 = new javax.swing.JButton();
        btn_1 = new javax.swing.JButton();
        btn_2 = new javax.swing.JButton();
        btn_3 = new javax.swing.JButton();
        btn_0 = new javax.swing.JButton();
        btn_punto = new javax.swing.JButton();
        btn_retroceso = new javax.swing.JButton();
        jpn_productos = new javax.swing.JPanel();
        btn_baleadaSencilla = new javax.swing.JButton();
        btn_refrescoNatural = new javax.swing.JButton();
        btn_carneAsada = new javax.swing.JButton();
        btn_baleadaCarne = new javax.swing.JButton();
        btn_polloChuco = new javax.swing.JButton();
        btn_gaseosa = new javax.swing.JButton();
        btn_cenaTipica = new javax.swing.JButton();
        btn_desayunoTipico = new javax.swing.JButton();
        btn_baleadaTodo = new javax.swing.JButton();
        jsp_tabla = new javax.swing.JScrollPane();
        tbl_orden = new javax.swing.JTable();
        jpn_pago = new javax.swing.JPanel();
        jpn_total = new javax.swing.JPanel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_isv = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        txt_isv = new javax.swing.JTextField();
        jpn_metodoPago = new javax.swing.JPanel();
        lbl_metodoPago = new javax.swing.JLabel();
        lbl_pago = new javax.swing.JLabel();
        lbl_cambio = new javax.swing.JLabel();
        txt_cambio = new javax.swing.JTextField();
        txt_pago = new javax.swing.JTextField();
        cmb_metodoPago = new javax.swing.JComboBox<>();
        btn_imprimir = new javax.swing.JButton();
        btn_reiniciarFactura = new javax.swing.JButton();
        btn_iniciarFactura = new javax.swing.JButton();
        btn_pagar = new javax.swing.JButton();
        btn_borrar = new javax.swing.JButton();
        lbl_cliente = new javax.swing.JLabel();
        lbl_nombreProducto = new javax.swing.JLabel();
        cmb_cliente = new javax.swing.JComboBox<>();
        lbl_idCliente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(40, 74, 172));
        kGradientPanel1.setkStartColor(new java.awt.Color(205, 63, 145));
        kGradientPanel1.setkTransparentControls(false);
        kGradientPanel1.setPreferredSize(new java.awt.Dimension(1319, 821));

        jpn_principal.setBackground(new java.awt.Color(255, 255, 255));

        lbl_nombreUsuario.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        lbl_nombreUsuario.setText("Nombre de Usuario");

        lbl_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/home-icon-silhouette.png"))); // NOI18N
        lbl_home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_homeMousePressed(evt);
            }
        });

        lbl_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/profile.png"))); // NOI18N
        lbl_usuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lbl_tituloClientes.setFont(new java.awt.Font("Roboto Black", 0, 48)); // NOI18N
        lbl_tituloClientes.setText("Orden");

        jpn_numeros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_9.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_9.setText("9");
        btn_9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_9.setEnabled(false);
        btn_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_9ActionPerformed(evt);
            }
        });

        btn_7.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_7.setText("7");
        btn_7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_7.setEnabled(false);
        btn_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_7ActionPerformed(evt);
            }
        });

        btn_8.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_8.setText("8");
        btn_8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_8.setEnabled(false);
        btn_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_8ActionPerformed(evt);
            }
        });

        btn_4.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_4.setText("4");
        btn_4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_4.setEnabled(false);
        btn_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_4ActionPerformed(evt);
            }
        });

        btn_5.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_5.setText("5");
        btn_5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_5.setEnabled(false);
        btn_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_5ActionPerformed(evt);
            }
        });

        btn_6.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_6.setText("6");
        btn_6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_6.setEnabled(false);
        btn_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_6ActionPerformed(evt);
            }
        });

        btn_1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_1.setText("1");
        btn_1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_1.setEnabled(false);
        btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_1ActionPerformed(evt);
            }
        });

        btn_2.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_2.setText("2");
        btn_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_2.setEnabled(false);
        btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_2ActionPerformed(evt);
            }
        });

        btn_3.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_3.setText("3");
        btn_3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_3.setEnabled(false);
        btn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_3ActionPerformed(evt);
            }
        });

        btn_0.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_0.setText("0");
        btn_0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_0.setEnabled(false);
        btn_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_0ActionPerformed(evt);
            }
        });

        btn_punto.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_punto.setText(".");
        btn_punto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_punto.setEnabled(false);
        btn_punto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_puntoActionPerformed(evt);
            }
        });

        btn_retroceso.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        btn_retroceso.setText("C");
        btn_retroceso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_retroceso.setEnabled(false);
        btn_retroceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retrocesoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_numerosLayout = new javax.swing.GroupLayout(jpn_numeros);
        jpn_numeros.setLayout(jpn_numerosLayout);
        jpn_numerosLayout.setHorizontalGroup(
            jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_numerosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_numerosLayout.createSequentialGroup()
                        .addComponent(btn_7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_numerosLayout.createSequentialGroup()
                        .addComponent(btn_4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_numerosLayout.createSequentialGroup()
                        .addComponent(btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_numerosLayout.createSequentialGroup()
                        .addComponent(btn_0, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_punto, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_retroceso, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jpn_numerosLayout.setVerticalGroup(
            jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_numerosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpn_numerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_retroceso, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_punto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_0, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpn_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_baleadaSencilla.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_baleadaSencilla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/baleadasencilla.jpg"))); // NOI18N
        btn_baleadaSencilla.setText("Baleada Sencilla");
        btn_baleadaSencilla.setToolTipText("");
        btn_baleadaSencilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_baleadaSencilla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_baleadaSencilla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_baleadaSencilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baleadaSencillaActionPerformed(evt);
            }
        });

        btn_refrescoNatural.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_refrescoNatural.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/refresconatural.jpeg"))); // NOI18N
        btn_refrescoNatural.setText("Refresco Natural");
        btn_refrescoNatural.setToolTipText("");
        btn_refrescoNatural.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refrescoNatural.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_refrescoNatural.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_refrescoNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrescoNaturalActionPerformed(evt);
            }
        });

        btn_carneAsada.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_carneAsada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/carneasada.jpg"))); // NOI18N
        btn_carneAsada.setText("Carne Asada");
        btn_carneAsada.setToolTipText("");
        btn_carneAsada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_carneAsada.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_carneAsada.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_carneAsada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carneAsadaActionPerformed(evt);
            }
        });

        btn_baleadaCarne.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_baleadaCarne.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/baleadacarne.jpg"))); // NOI18N
        btn_baleadaCarne.setText("Baleada Carne");
        btn_baleadaCarne.setToolTipText("");
        btn_baleadaCarne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_baleadaCarne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_baleadaCarne.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_baleadaCarne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baleadaCarneActionPerformed(evt);
            }
        });

        btn_polloChuco.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_polloChuco.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\pollo.png")); // NOI18N
        btn_polloChuco.setText("Pollo Chuco");
        btn_polloChuco.setToolTipText("");
        btn_polloChuco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_polloChuco.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_polloChuco.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_polloChuco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_polloChucoActionPerformed(evt);
            }
        });

        btn_gaseosa.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_gaseosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logococa.jpg"))); // NOI18N
        btn_gaseosa.setText("Gaseosa");
        btn_gaseosa.setToolTipText("");
        btn_gaseosa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_gaseosa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_gaseosa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_gaseosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gaseosaActionPerformed(evt);
            }
        });

        btn_cenaTipica.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_cenaTipica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cena.jpg"))); // NOI18N
        btn_cenaTipica.setText("Cena Típica");
        btn_cenaTipica.setToolTipText("");
        btn_cenaTipica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cenaTipica.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cenaTipica.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cenaTipica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cenaTipicaActionPerformed(evt);
            }
        });

        btn_desayunoTipico.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_desayunoTipico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/desayunojpg.jpg"))); // NOI18N
        btn_desayunoTipico.setText("Desayuno Típico");
        btn_desayunoTipico.setToolTipText("");
        btn_desayunoTipico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_desayunoTipico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_desayunoTipico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_desayunoTipico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_desayunoTipicoActionPerformed(evt);
            }
        });

        btn_baleadaTodo.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_baleadaTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/baleada-con-Todo.png"))); // NOI18N
        btn_baleadaTodo.setText("Baleada con Todo");
        btn_baleadaTodo.setToolTipText("");
        btn_baleadaTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_baleadaTodo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_baleadaTodo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_baleadaTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baleadaTodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_productosLayout = new javax.swing.GroupLayout(jpn_productos);
        jpn_productos.setLayout(jpn_productosLayout);
        jpn_productosLayout.setHorizontalGroup(
            jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_productosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_refrescoNatural)
                    .addComponent(btn_carneAsada)
                    .addComponent(btn_baleadaSencilla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_gaseosa)
                    .addComponent(btn_polloChuco)
                    .addComponent(btn_baleadaCarne))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_desayunoTipico)
                    .addComponent(btn_cenaTipica)
                    .addComponent(btn_baleadaTodo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpn_productosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_baleadaCarne, btn_baleadaSencilla, btn_baleadaTodo, btn_carneAsada, btn_cenaTipica, btn_desayunoTipico, btn_gaseosa, btn_polloChuco, btn_refrescoNatural});

        jpn_productosLayout.setVerticalGroup(
            jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpn_productosLayout.createSequentialGroup()
                        .addComponent(btn_baleadaTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cenaTipica, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_desayunoTipico, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpn_productosLayout.createSequentialGroup()
                            .addComponent(btn_baleadaCarne, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_polloChuco, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_gaseosa, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpn_productosLayout.createSequentialGroup()
                            .addComponent(btn_baleadaSencilla, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_carneAsada, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_refrescoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpn_productosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_baleadaCarne, btn_baleadaSencilla, btn_baleadaTodo, btn_carneAsada, btn_cenaTipica, btn_desayunoTipico, btn_gaseosa, btn_polloChuco, btn_refrescoNatural});

        tbl_orden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre del Producto", "Cantidad", "Precio"
            }
        ));
        tbl_orden.setRowHeight(18);
        jsp_tabla.setViewportView(tbl_orden);

        jpn_pago.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jpn_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_subtotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_subtotal.setText("Subtotal");

        lbl_isv.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_isv.setText("ISV");

        lbl_total.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_total.setText("Total");

        txt_subtotal.setEditable(false);
        txt_subtotal.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        txt_subtotal.setText("0");

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        txt_total.setText("0");

        txt_isv.setEditable(false);
        txt_isv.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        txt_isv.setText("0");

        javax.swing.GroupLayout jpn_totalLayout = new javax.swing.GroupLayout(jpn_total);
        jpn_total.setLayout(jpn_totalLayout);
        jpn_totalLayout.setHorizontalGroup(
            jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_totalLayout.createSequentialGroup()
                .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_totalLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_isv, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_total, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jpn_totalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_subtotal)))
                .addGap(18, 18, 18)
                .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_subtotal)
                    .addComponent(txt_isv)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jpn_totalLayout.setVerticalGroup(
            jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_totalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_subtotal))
                .addGap(15, 15, 15)
                .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_isv, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_isv))
                .addGap(18, 18, 18)
                .addGroup(jpn_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_total))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpn_metodoPago.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_metodoPago.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_metodoPago.setText("Método de pago");

        lbl_pago.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_pago.setText("Pago");

        lbl_cambio.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lbl_cambio.setText("Cambio");

        txt_cambio.setEditable(false);
        txt_cambio.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N

        txt_pago.setEditable(false);
        txt_pago.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N

        cmb_metodoPago.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        cmb_metodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el método", "Efectivo", "Tarjeta" }));
        cmb_metodoPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cmb_metodoPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jpn_metodoPagoLayout = new javax.swing.GroupLayout(jpn_metodoPago);
        jpn_metodoPago.setLayout(jpn_metodoPagoLayout);
        jpn_metodoPagoLayout.setHorizontalGroup(
            jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_metodoPagoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_metodoPago)
                    .addComponent(lbl_pago, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_cambio, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_pago)
                    .addComponent(txt_cambio)
                    .addComponent(cmb_metodoPago, 0, 263, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jpn_metodoPagoLayout.setVerticalGroup(
            jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_metodoPagoLayout.createSequentialGroup()
                .addGroup(jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_metodoPagoLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbl_metodoPago))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_metodoPagoLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(cmb_metodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_pago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpn_metodoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cambio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_imprimir.setBackground(new java.awt.Color(205, 63, 145));
        btn_imprimir.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btn_imprimir.setForeground(new java.awt.Color(255, 255, 255));
        btn_imprimir.setText("Imprimir factura");
        btn_imprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_imprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_imprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_imprimirMouseExited(evt);
            }
        });
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });

        btn_reiniciarFactura.setBackground(new java.awt.Color(205, 63, 145));
        btn_reiniciarFactura.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btn_reiniciarFactura.setForeground(new java.awt.Color(255, 255, 255));
        btn_reiniciarFactura.setText("Reiniciar");
        btn_reiniciarFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reiniciarFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_reiniciarFacturaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_reiniciarFacturaMouseExited(evt);
            }
        });
        btn_reiniciarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reiniciarFacturaActionPerformed(evt);
            }
        });

        btn_iniciarFactura.setBackground(new java.awt.Color(205, 63, 145));
        btn_iniciarFactura.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btn_iniciarFactura.setForeground(new java.awt.Color(255, 255, 255));
        btn_iniciarFactura.setText("Iniciar");
        btn_iniciarFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_iniciarFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_iniciarFacturaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_iniciarFacturaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_iniciarFacturaMousePressed(evt);
            }
        });
        btn_iniciarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarFacturaActionPerformed(evt);
            }
        });

        btn_pagar.setBackground(new java.awt.Color(205, 63, 145));
        btn_pagar.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btn_pagar.setForeground(new java.awt.Color(255, 255, 255));
        btn_pagar.setText("Pagar");
        btn_pagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_pagarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_pagarMouseExited(evt);
            }
        });
        btn_pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagarActionPerformed(evt);
            }
        });

        btn_borrar.setBackground(new java.awt.Color(205, 63, 145));
        btn_borrar.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btn_borrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_borrar.setText("Eliminar");
        btn_borrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_borrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_borrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_borrarMouseExited(evt);
            }
        });
        btn_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_pagoLayout = new javax.swing.GroupLayout(jpn_pago);
        jpn_pago.setLayout(jpn_pagoLayout);
        jpn_pagoLayout.setHorizontalGroup(
            jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_pagoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpn_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpn_metodoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpn_pagoLayout.createSequentialGroup()
                        .addComponent(btn_iniciarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_reiniciarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_pagoLayout.createSequentialGroup()
                        .addComponent(btn_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        jpn_pagoLayout.setVerticalGroup(
            jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_pagoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpn_pagoLayout.createSequentialGroup()
                        .addGroup(jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_iniciarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reiniciarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpn_pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpn_metodoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpn_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cliente.png"))); // NOI18N
        lbl_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_clienteMousePressed(evt);
            }
        });

        lbl_nombreProducto.setText("jLabel1");

        cmb_cliente.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        cmb_cliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cmb_cliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmb_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_clienteActionPerformed(evt);
            }
        });

        lbl_idCliente.setText("jLabel1");

        javax.swing.GroupLayout jpn_principalLayout = new javax.swing.GroupLayout(jpn_principal);
        jpn_principal.setLayout(jpn_principalLayout);
        jpn_principalLayout.setHorizontalGroup(
            jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_principalLayout.createSequentialGroup()
                .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpn_principalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_usuario)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_nombreUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_principalLayout.createSequentialGroup()
                                .addComponent(lbl_tituloClientes)
                                .addGap(164, 164, 164)
                                .addComponent(lbl_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmb_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(lbl_home))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_principalLayout.createSequentialGroup()
                                .addComponent(lbl_idCliente)
                                .addGap(149, 149, 149)
                                .addComponent(lbl_nombreProducto)
                                .addGap(221, 221, 221))))
                    .addGroup(jpn_principalLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpn_pago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jpn_principalLayout.createSequentialGroup()
                                .addComponent(jpn_numeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jsp_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jpn_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(21, 21, 21))
        );
        jpn_principalLayout.setVerticalGroup(
            jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_principalLayout.createSequentialGroup()
                .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_principalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_nombreProducto)
                            .addComponent(lbl_idCliente))
                        .addGap(18, 18, 18)
                        .addComponent(cmb_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_principalLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lbl_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_principalLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpn_principalLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_tituloClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpn_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpn_numeros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsp_tabla))
                .addGap(18, 18, 18)
                .addComponent(jpn_pago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jpn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jpn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1426, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_homeMousePressed
        Principal principal = new Principal(lbl_nombreUsuario.getText());
        this.dispose();
        principal.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_homeMousePressed

    private void btn_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_4ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "4";
        txt_pago.setText(pago);
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_4ActionPerformed

    private void btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_1ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "1";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_1ActionPerformed

    private void btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_2ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "2";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_2ActionPerformed

    private void btn_retrocesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retrocesoActionPerformed
        int tamaño = txt_pago.getText().length();
        if (tamaño == 0) {
            return;
        }
        txt_pago.setText(txt_pago.getText().substring(0, (tamaño - 1)));

        if (tamaño >= 2) {
            cambio();
        }

        if (tamaño == 1) {
            txt_cambio.setText("-L" + txt_total.getText());
        }

        if (!txt_pago.getText().contains(".")) {
            hayDecimal = false;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_retrocesoActionPerformed

    private void btn_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_0ActionPerformed
        int tamaño = txt_pago.getText().length();
        if (tamaño == 0) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String pago = "";
        pago = txt_pago.getText();
        pago += "0";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_0ActionPerformed

    private void btn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_3ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "3";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_3ActionPerformed

    private void btn_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_5ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "5";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_5ActionPerformed

    private void btn_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_6ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "6";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_6ActionPerformed

    private void btn_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_7ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "7";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_7ActionPerformed

    private void btn_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_8ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "8";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_8ActionPerformed

    private void btn_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_9ActionPerformed
        String pago = "";
        pago = txt_pago.getText();
        pago += "9";
        txt_pago.setText(pago);
        cambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_9ActionPerformed

    private void btn_puntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_puntoActionPerformed
        int tamaño = txt_pago.getText().length();

        if (hayDecimal) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        if (tamaño == 0) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String caracterAnterior = "";
        caracterAnterior = txt_pago.getText().substring((tamaño - 1));
        if (caracterAnterior.equals(".")) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String pago = "";
        pago = txt_pago.getText();
        pago += ".";
        txt_pago.setText(pago);
        hayDecimal = true;
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_puntoActionPerformed

    private void btn_iniciarFacturaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_iniciarFacturaMouseEntered
        btn_iniciarFactura.setBackground(new Color(156, 2, 91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iniciarFacturaMouseEntered

    private void btn_iniciarFacturaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_iniciarFacturaMouseExited
        btn_iniciarFactura.setBackground(new Color(205, 63, 145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iniciarFacturaMouseExited

    private void btn_iniciarFacturaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_iniciarFacturaMousePressed

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iniciarFacturaMousePressed

    private void lbl_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_clienteMousePressed
        cmb_cliente.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_clienteMousePressed

    private void btn_iniciarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarFacturaActionPerformed
        btn_iniciarFactura.setBackground(new Color(40, 74, 172));
        try {
            Calendar f;
            f = Calendar.getInstance();
            int d = f.get(Calendar.DATE), mes = 1 + (f.get(Calendar.MONTH)), año = f.get(Calendar.YEAR);
            String fecha = (año + "-" + mes + "-" + d);
            PreparedStatement ps;
            ResultSet rs;
            String cai = "35BD6A-0195F4-B34BAA-8B7D13-37791A-2D";
            int totalInicial = 0;
            ps = con.prepareStatement("INSERT INTO factura_encabezado (cai, idempleado, totalfactura, idcliente,"
                    + "fecha_factura)"
                    + "VALUES(?,?,?,?,?)");
            ps.setString(1, cai);
            String idEmpleado = capturarIdEmpleado();
            String idCliente = lbl_idCliente.getText();
            ps.setString(2, idEmpleado);
            ps.setString(3, String.valueOf(totalInicial));
            ps.setString(4, idCliente);
            ps.setString(5, fecha);
            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Factura iniciada");
             
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iniciarFacturaActionPerformed

    private void btn_pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagarActionPerformed
        btn_pagar.setBackground(new Color(40, 74, 172));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pagarActionPerformed

    private void btn_reiniciarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reiniciarFacturaActionPerformed
        btn_reiniciarFactura.setBackground(new Color(40, 74, 172));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_reiniciarFacturaActionPerformed

    private void btn_pagarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pagarMouseEntered
        btn_pagar.setBackground(new Color(156, 2, 91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pagarMouseEntered

    private void btn_pagarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pagarMouseExited
        btn_pagar.setBackground(new Color(205, 63, 145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pagarMouseExited

    private void btn_reiniciarFacturaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reiniciarFacturaMouseEntered
        btn_reiniciarFactura.setBackground(new Color(156, 2, 91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_reiniciarFacturaMouseEntered

    private void btn_reiniciarFacturaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reiniciarFacturaMouseExited
        btn_reiniciarFactura.setBackground(new Color(205, 63, 145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_reiniciarFacturaMouseExited

    private void btn_borrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_borrarMouseEntered
        btn_borrar.setBackground(new Color(156, 2, 91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_borrarMouseEntered

    private void btn_borrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_borrarMouseExited
        btn_borrar.setBackground(new Color(205, 63, 145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_borrarMouseExited

    private void btn_imprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_imprimirMouseEntered
        btn_imprimir.setBackground(new Color(156, 2, 91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_imprimirMouseEntered

    private void btn_imprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_imprimirMouseExited
        btn_imprimir.setBackground(new Color(205, 63, 145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_imprimirMouseExited

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        btn_imprimir.setBackground(new Color(40, 74, 172));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrarActionPerformed
        btn_borrar.setBackground(new Color(40, 74, 172));

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_borrarActionPerformed

    private void btn_baleadaSencillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baleadaSencillaActionPerformed
        lbl_nombreProducto.setText(btn_baleadaSencilla.getText());
        actualizarDatos();
        // TODO add y;our handling code here:
    }//GEN-LAST:event_btn_baleadaSencillaActionPerformed

    private void btn_baleadaCarneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baleadaCarneActionPerformed
        lbl_nombreProducto.setText(btn_baleadaCarne.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_baleadaCarneActionPerformed

    private void btn_baleadaTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baleadaTodoActionPerformed
        lbl_nombreProducto.setText(btn_baleadaTodo.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_baleadaTodoActionPerformed

    private void btn_carneAsadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carneAsadaActionPerformed
        lbl_nombreProducto.setText(btn_carneAsada.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_carneAsadaActionPerformed

    private void btn_polloChucoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_polloChucoActionPerformed
        lbl_nombreProducto.setText(btn_polloChuco.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_polloChucoActionPerformed

    private void btn_cenaTipicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cenaTipicaActionPerformed
        lbl_nombreProducto.setText(btn_cenaTipica.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cenaTipicaActionPerformed

    private void btn_refrescoNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrescoNaturalActionPerformed
        lbl_nombreProducto.setText(btn_refrescoNatural.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_refrescoNaturalActionPerformed

    private void btn_gaseosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gaseosaActionPerformed
        lbl_nombreProducto.setText(btn_gaseosa.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_gaseosaActionPerformed

    private void btn_desayunoTipicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desayunoTipicoActionPerformed
        lbl_nombreProducto.setText(btn_desayunoTipico.getText());
        actualizarDatos();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_desayunoTipicoActionPerformed

    private void cmb_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_clienteActionPerformed
        String string = cmb_cliente.getSelectedItem().toString();
        String [] array =  string.split("\\.");
        lbl_idCliente.setText(array[0]);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_clienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("Button.arc", 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Orden().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_0;
    private javax.swing.JButton btn_1;
    private javax.swing.JButton btn_2;
    private javax.swing.JButton btn_3;
    private javax.swing.JButton btn_4;
    private javax.swing.JButton btn_5;
    private javax.swing.JButton btn_6;
    private javax.swing.JButton btn_7;
    private javax.swing.JButton btn_8;
    private javax.swing.JButton btn_9;
    private javax.swing.JButton btn_baleadaCarne;
    private javax.swing.JButton btn_baleadaSencilla;
    private javax.swing.JButton btn_baleadaTodo;
    private javax.swing.JButton btn_borrar;
    private javax.swing.JButton btn_carneAsada;
    private javax.swing.JButton btn_cenaTipica;
    private javax.swing.JButton btn_desayunoTipico;
    private javax.swing.JButton btn_gaseosa;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_iniciarFactura;
    private javax.swing.JButton btn_pagar;
    private javax.swing.JButton btn_polloChuco;
    private javax.swing.JButton btn_punto;
    private javax.swing.JButton btn_refrescoNatural;
    private javax.swing.JButton btn_reiniciarFactura;
    private javax.swing.JButton btn_retroceso;
    private javax.swing.JComboBox<String> cmb_cliente;
    private javax.swing.JComboBox<String> cmb_metodoPago;
    private javax.swing.JPanel jpn_metodoPago;
    private javax.swing.JPanel jpn_numeros;
    private javax.swing.JPanel jpn_pago;
    private javax.swing.JPanel jpn_principal;
    private javax.swing.JPanel jpn_productos;
    private javax.swing.JPanel jpn_total;
    private javax.swing.JScrollPane jsp_tabla;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_cambio;
    private javax.swing.JLabel lbl_cliente;
    private javax.swing.JLabel lbl_home;
    private javax.swing.JLabel lbl_idCliente;
    private javax.swing.JLabel lbl_isv;
    private javax.swing.JLabel lbl_metodoPago;
    private javax.swing.JLabel lbl_nombreProducto;
    private javax.swing.JLabel lbl_nombreUsuario;
    private javax.swing.JLabel lbl_pago;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_tituloClientes;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_usuario;
    private javax.swing.JTable tbl_orden;
    private javax.swing.JTextField txt_cambio;
    private javax.swing.JTextField txt_isv;
    private javax.swing.JTextField txt_pago;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
