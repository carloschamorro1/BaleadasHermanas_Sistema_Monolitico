/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baleadashermanas;

import com.formdev.flatlaf.*;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Carlos
 */
public class Login extends javax.swing.JFrame {
    int click = 0;
    
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();   
        holders();
        info();
    }
    
   
    public void info(){
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("../Img/Titulo.png")).getImage());
    }
    

    public void holders(){
        PlaceHolder holder;
        PlaceHolder holder2;
        holder = new PlaceHolder(txt_usuario,Color.gray,Color.black,"Usuario",false,"Roboto",25);
        holder = new PlaceHolder(txt_contraseña,Color.gray,Color.black,"Contraseña",false,"Roboto",25);
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
        jPanel1 = new javax.swing.JPanel();
        lbl_titulo = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        btn_ingresar = new javax.swing.JButton();
        lbl_contraseñaOlvidada = new javax.swing.JLabel();
        lbl_contraseña = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        txt_contraseña = new javax.swing.JPasswordField();
        lbl_vercontraseña = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(40, 74, 172));
        kGradientPanel1.setkStartColor(new java.awt.Color(205, 63, 145));
        kGradientPanel1.setkTransparentControls(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lbl_titulo.setFont(new java.awt.Font("Roboto Black", 0, 48)); // NOI18N
        lbl_titulo.setText("Acceso");

        txt_usuario.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        txt_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usuarioActionPerformed(evt);
            }
        });

        btn_ingresar.setBackground(new java.awt.Color(204, 0, 204));
        btn_ingresar.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        btn_ingresar.setForeground(new java.awt.Color(255, 255, 255));
        btn_ingresar.setText("Ingresar");
        btn_ingresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ingresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ingresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ingresarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_ingresarMousePressed(evt);
            }
        });

        lbl_contraseñaOlvidada.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lbl_contraseñaOlvidada.setText("¿Has olvidado tu contraseña?");
        lbl_contraseñaOlvidada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lbl_contraseñaOlvidadaFocusGained(evt);
            }
        });

        lbl_contraseña.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\candado-cerrado.png")); // NOI18N

        lbl_usuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\man-user.png")); // NOI18N

        txt_contraseña.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        txt_contraseña.setEchoChar('*');

        lbl_vercontraseña.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\ojo-cerrado.png")); // NOI18N
        lbl_vercontraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_vercontraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_vercontraseñaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_contraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_usuario)
                                    .addComponent(txt_contraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))))
                        .addGap(8, 8, 8)
                        .addComponent(lbl_vercontraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_contraseñaOlvidada)
                        .addGap(109, 109, 109))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_titulo)
                .addGap(176, 176, 176))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_vercontraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_contraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45)
                .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lbl_contraseñaOlvidada)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(393, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(396, 396, 396))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_contraseñaOlvidadaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lbl_contraseñaOlvidadaFocusGained
        lbl_contraseñaOlvidada.setForeground(Color.red);
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_contraseñaOlvidadaFocusGained

    private void btn_ingresarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ingresarMousePressed
        btn_ingresar.setBackground(new Color(40,74,172));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ingresarMousePressed

    private void btn_ingresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ingresarMouseExited
        btn_ingresar.setBackground(new Color(205,63,145));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ingresarMouseExited

    private void btn_ingresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ingresarMouseEntered
        btn_ingresar.setBackground(new Color(156,2,91));
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ingresarMouseEntered

    private void txt_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usuarioActionPerformed

    private void lbl_vercontraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_vercontraseñaMouseClicked
        click++;
        if(click%2 != 0){
            
            lbl_vercontraseña.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\ojo.png"));
            txt_contraseña.setEchoChar((char)0);
        }else{
            lbl_vercontraseña.setIcon(new javax.swing.ImageIcon("C:\\Users\\cmcha\\Documents\\NetBeansProjects\\BaleadasHermanas\\BaleadasHermanas\\src\\Img\\ojo-cerrado.png"));
            txt_contraseña.setEchoChar('*');
        } 
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_vercontraseñaMouseClicked

    private void btn_ingresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ingresarMouseClicked
        JOptionPane.showMessageDialog(this, "Bienvenido al sistema");
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ingresarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try{
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put( "Button.arc", 650 );
        }catch(Exception e){
            e.printStackTrace();
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JPanel jPanel1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_contraseña;
    private javax.swing.JLabel lbl_contraseñaOlvidada;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JLabel lbl_usuario;
    private javax.swing.JLabel lbl_vercontraseña;
    private javax.swing.JPasswordField txt_contraseña;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
