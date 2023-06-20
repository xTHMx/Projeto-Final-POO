/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.projetopoo.interfacegrafica;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Delivery;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Usuario;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Cliente;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Restaurante;
import com.mycompany.projetopoo.interfacegrafica.Codigo.*;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
/**
 * Primeira janela, usada para fazer login ou cadastrar uma nova conta
 * @author jaoti
 */
public class JFrameLogin extends javax.swing.JFrame {

    /**
     * Creates new form JFrameLogin
     */
    
    //Variável usada para pegar a instância do JFrameLogin
    public Delivery delivery;
    public JFrameCliente jFrameCliente;
    public static String jarFilePath;
    
    /**
     * Metodo que retorna um ImageIcon a partir de um caminho e uma altura dada, usado para mostrar as imagens do programa
     * @param path
     * @param height
     * @return 
     **/
    public static ImageIcon getImageIconFromPath(String path, int height){
        ImageIcon imageIcon;
        if(path.isEmpty()){
            imageIcon = new ImageIcon(JFrameLogin.jarFilePath + "\\imagens" + "\\" + "Default.png");
        }else{
            imageIcon = new ImageIcon(path); 
        }
        //Normaliza a imagem para 200x200
        Image image = imageIcon.getImage().getScaledInstance(-1, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        //Coloca a imagem na label
        return imageIcon;
    }
    
    /**
     * Construtor da classe
     **/
    public JFrameLogin() {
        initComponents();
        //Esconder a mensagem de erro
        this.lblErro.setVisible(false);
        //Codigo para pegar o caminho a partir do arquivo .jar
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String caminhoJava = jarFile.getParent() + "\\";
        jarFilePath = caminhoJava;
        //variáveis para auxiliar nos caminhos
        String caminhoItems = caminhoJava + "items.dat";
        String caminhoUsuarios = caminhoJava + "usuarios.dat";
        String caminhoPedidos = caminhoJava + "pedidos.dat";
        //Instanciar nova classe delivery
        delivery = new Delivery(caminhoItems,caminhoUsuarios,caminhoPedidos);
        //Colocar icone
        this.setIconImage(new ImageIcon(jarFilePath + "\\imagens" + "\\" + "DefaultWindowIcon.png").getImage());
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
              delivery.fechaPrograma();
              e.getWindow().dispose();
            }
        });
    }
    
    /**
     * Cria um dialog para redefinição de senha do usuário
     * @param user
    **/
    private void esqueciSenha(Usuario user){
        //Cria os campos de senha
        JPasswordField pwdSenhaLocal = new JPasswordField(20);
        JPasswordField pwdRepetirSenha = new JPasswordField(20);
        //Coloca os campos num panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nova senha: "));
        panel.add(pwdSenhaLocal);
        panel.add(new JLabel("Repita a senha: "));
        panel.add(pwdRepetirSenha);
        //Abre um dialog com o panel
        int result = JOptionPane.showConfirmDialog(this, panel, "Criar nova senha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //Usuario apertou em OK?
        if (result == JOptionPane.OK_OPTION) {
            //Variaveis para facilitar a comparação
            String senha = new String(pwdSenhaLocal.getPassword());
            String repetirSenha = new String(pwdRepetirSenha.getPassword());
            //Senhas iguais?
            if (senha.equals(repetirSenha)) {
                //Atualiza a conta  
                if(user != null){
                    user.setSenha(senha); 
                    JOptionPane.showMessageDialog(this, "Senha trocada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }else{//Usuario nao encontrado
                    JOptionPane.showMessageDialog(this, "Usuário não existe!!!");
                }
                
            } else {
                //Mensagem de erro que as senhas são diferentes
                JOptionPane.showMessageDialog(this, "Senhas diferentes!", "Erro", JOptionPane.ERROR_MESSAGE);
                esqueciSenha(user); //Tenta denovo
            }
        }
    }
    
    /**
     * Metodo para logar uma conta e abrir o JFrame correspondente
     **/
    private void logarConta(){
        //Esconde a mensagem de erro
        this.lblErro.setVisible(false);
        //Pegar as strings dos componentes
        String nomeUsuario = this.txtUsuario.getText();
        String senha = String.valueOf(this.pwdSenha.getPassword());
        //Tenta fazer o login
        Usuario user = delivery.loginUsuario(nomeUsuario, senha);
        //Conseguiu fazer o login?
        if(user != null){
            //É cliente?
            if(user.getClass() == Cliente.class){
                jFrameCliente = new JFrameCliente((Cliente)user,delivery);
                jFrameCliente.setLocation(this.getX() + this.getWidth(), this.getY());
                jFrameCliente.setVisible(true);
        
            }else{//É restaurante?
               JFrameRestaurante jFrameRestaurante = new JFrameRestaurante(delivery,(Restaurante)user);
               jFrameRestaurante.setVisible(true);
            }
        }  
        else{
            this.lblErro.setVisible(true);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLayeredPane1 = new javax.swing.JLayeredPane();
        lblLogo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        pwdSenha = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        jPanelMaster = new javax.swing.JPanel();
        btnCadastrar = new javax.swing.JButton();
        btnEsqueciSenha = new javax.swing.JButton();
        lblErro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(200, 200));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane1.setForeground(new java.awt.Color(255, 255, 255));
        jLayeredPane1.setOpaque(true);
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(320, 500));
        jLayeredPane1.setLayout(new java.awt.GridBagLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon("C:\\Users\\jaoti\\OneDrive\\Documents\\NetBeansProjects\\ProjetoPOO-InterfaceGrafica\\target\\Imagens\\main-logo-cropped.png")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jLayeredPane1.add(lblLogo, gridBagConstraints);

        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUsuario.setLabelFor(txtUsuario);
        lblUsuario.setText("Usuário");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(lblUsuario, gridBagConstraints);

        lblSenha.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSenha.setLabelFor(pwdSenha);
        lblSenha.setText("Senha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel1.add(lblSenha, gridBagConstraints);

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(239, 83, 80));
        btnLogin.setAutoscrolls(true);
        btnLogin.setLabel("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel1.add(btnLogin, gridBagConstraints);

        pwdSenha.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 110;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(pwdSenha, gridBagConstraints);

        txtUsuario.setToolTipText("Nome de usuário");
        txtUsuario.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 110;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(txtUsuario, gridBagConstraints);

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.PALETTE_LAYER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        jLayeredPane1.add(jPanel1, gridBagConstraints);

        jPanelMaster.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMaster.setOpaque(false);
        jPanelMaster.setLayout(new java.awt.GridBagLayout());

        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(239, 83, 80));
        btnCadastrar.setLabel("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanelMaster.add(btnCadastrar, gridBagConstraints);

        btnEsqueciSenha.setBackground(new java.awt.Color(239, 83, 80));
        btnEsqueciSenha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEsqueciSenha.setForeground(new java.awt.Color(255, 255, 255));
        btnEsqueciSenha.setLabel("Esqueci a Senha");
        btnEsqueciSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsqueciSenhaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanelMaster.add(btnEsqueciSenha, gridBagConstraints);

        lblErro.setForeground(new java.awt.Color(255, 0, 0));
        lblErro.setText("Não foi possível fazer o login!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelMaster.add(lblErro, gridBagConstraints);

        jLayeredPane1.setLayer(jPanelMaster, javax.swing.JLayeredPane.PALETTE_LAYER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        jLayeredPane1.add(jPanelMaster, gridBagConstraints);

        getContentPane().add(jLayeredPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Faz o Login
     **/
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        //Metodo chamado no clique do botão Login
        logarConta();
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * Abre o Dialog de Esqueci Senha
     **/
    private void btnEsqueciSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsqueciSenhaActionPerformed
        Usuario user = delivery.getUsuario(txtUsuario.getText());
        //Verifica se o usuario existe
        if(user == null){
            JOptionPane.showMessageDialog(this, "Usuário não existe!!!");
        }else{
            esqueciSenha(user);
        }
        
    }//GEN-LAST:event_btnEsqueciSenhaActionPerformed

    /**
     * Abre o frame de cadastro de usuários
     **/
    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
            //Chama a janela de cadastro e passa o objeto de delivery para adicionar a conta
            java.awt.EventQueue.invokeLater(() -> {
            new JFrameUsuario( delivery, null).setVisible(true);
        });
    }//GEN-LAST:event_btnCadastrarActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameLogin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEsqueciSenha;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMaster;
    private javax.swing.JLabel lblErro;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField pwdSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
