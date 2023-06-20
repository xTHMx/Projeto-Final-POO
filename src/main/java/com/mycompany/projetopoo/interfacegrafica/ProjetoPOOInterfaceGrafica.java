/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projetopoo.interfacegrafica;

/**
 * Classe principal do programa, já chama a tela de login
 * @author jaoti
 */
public class ProjetoPOOInterfaceGrafica {

    //Variável que representa a tela de login
    private static JFrameLogin jFrameLogin;
    
    /**
     * Main Principal do programa
     * @param args
     */
    public static void main(String[] args) {
        iniciarTela(); 
    }
    
    /**
     * Inicia as telas, carrega o LookAndFeel
     */
    private static void iniciarTela(){
        //Código que coloca o estilo dos componentes
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //Instancia e coloca o login visivel
        jFrameLogin = new JFrameLogin();
        jFrameLogin.setVisible(true);
    }
}
