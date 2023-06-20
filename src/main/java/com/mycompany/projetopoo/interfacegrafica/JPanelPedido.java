/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.projetopoo.interfacegrafica;

import com.mycompany.projetopoo.interfacegrafica.Codigo.ItemCarrinho;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Pedido;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Card de Pedido incluindo os items
 * @author jaoti
 */
public class JPanelPedido extends javax.swing.JPanel {

    /**
     * Creates new form JPanelPedido
     */
    
    private final Pedido pedido;
    public enum Tela{
        RESTAURANTE,
        CLIENTE
    }

    /**
     * Metodo que cria cada "linha" de item retorna um panel com todas as linhas
     * @param itemCarrinho
     * @return Panel com todos os items do pedido
     */
    private JPanel createItemPanel(ItemCarrinho itemCarrinho){
        //Cria o panel
        JPanel jPanelItem = new javax.swing.JPanel();
        jPanelItem.setBackground(Color.getColor("e9e5e2"));
        jPanelItem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItem.setLayout(new java.awt.GridLayout(1, 3));
        //Cria a label de nome
        JLabel lblNomeProduto = new JLabel();
        lblNomeProduto.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProduto.setText(itemCarrinho.getItem().getNome()); //Pega o nome do item
        lblNomeProduto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItem.add(lblNomeProduto);
        //Cria a label de quantidade
        JLabel lblQuantidade = new JLabel();
        lblQuantidade.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidade.setText("x" + String.valueOf(itemCarrinho.getQuantidade()));
        lblQuantidade.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItem.add(lblQuantidade);
        //Cria a label de valor
        JLabel lblValor = new JLabel();
        lblValor.setForeground(new java.awt.Color(51, 51, 51));
        lblValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValor.setText("R$" + String.valueOf(itemCarrinho.getItem().getPreco()*itemCarrinho.getQuantidade()));
        lblValor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItem.add(lblValor);
        
        jPanelItem.setVisible(true);
        return jPanelItem;
        
    }
    
    /**
     * Retorna a lista de items do carrinho
     * @param tela
     * @return Lista de items do carrinho
     */
    private void carregarImagem(Tela tela){
        String path;
        if(tela == Tela.CLIENTE)
            path = pedido.getRestaurante().getResolvedProfilePath();
        else
            path = pedido.getCliente().getResolvedProfilePath();
        
        this.lblImg.setIcon(JFrameLogin.getImageIconFromPath(path,50));
    }

    /**
     * Carrega os dados do card
     * @param tela diz o que fazer baseado no tipo de tela
     */
    private void carregarDados(Tela tela){
        //Carrega os dados do pedido nas labels
        String date = "";
        if(pedido.getDataPedido() != null)
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pedido.getDataPedido()); //Converter a Data em String
        lblData.setText(date);
        lblStatus.setText(pedido.getStatus()); 
        cbStatus.setSelectedItem(pedido.getStatus());
        if(tela == Tela.CLIENTE){
            lblNomeRestaurante.setText(pedido.getRestaurante().getNomeReal());
            cbStatus.setVisible(false);
            lblStatus.setVisible(true);
        }
        else{//Restaurante
            lblNomeRestaurante.setText(pedido.getCliente().getNomeReal());
            cbStatus.setVisible(true);
            lblStatus.setVisible(false);
        }
        lblNumero.setText("Nº do Pedido: " + String.valueOf(pedido.getCodigo())); 
        lblValorTotal.setText("Total: R$" + String.valueOf(pedido.getValorTotal())); 
        carregarImagem(tela);
        //Carrega a lista de items do pedido
        for(ItemCarrinho itemCarrinho : pedido.getListaItemCarrinho()){
            //Cria a linha do item
            jPanelItems.add(createItemPanel(itemCarrinho));
        }     
        jPanelItems.validate();
        jPanelItems.repaint();
        this.validate();
        this.repaint();
    }
    
    /**
     * Construtor do Panel de pedido (Card)
     * @param pedido
     * @param tela
     */
    public JPanelPedido(Pedido pedido, Tela tela) {
        initComponents();
        this.pedido = pedido;
        carregarDados(tela);
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

        jPanelItemTeste2 = new javax.swing.JPanel();
        lblNomeProdutoTeste2 = new javax.swing.JLabel();
        lblQuantidadeTeste2 = new javax.swing.JLabel();
        lblValorTeste2 = new javax.swing.JLabel();
        jPanelItemTeste1 = new javax.swing.JPanel();
        lblNomeProdutoTeste1 = new javax.swing.JLabel();
        lblQuantidadeTeste1 = new javax.swing.JLabel();
        lblValorTeste1 = new javax.swing.JLabel();
        jPanelItemTeste = new javax.swing.JPanel();
        lblNomeProdutoTeste = new javax.swing.JLabel();
        lblQuantidadeTeste = new javax.swing.JLabel();
        lblValorTeste = new javax.swing.JLabel();
        jPanelItemTeste3 = new javax.swing.JPanel();
        lblNomeProdutoTeste3 = new javax.swing.JLabel();
        lblQuantidadeTeste3 = new javax.swing.JLabel();
        lblValorTeste3 = new javax.swing.JLabel();
        jPanelItemTeste4 = new javax.swing.JPanel();
        lblNomeProdutoTeste4 = new javax.swing.JLabel();
        lblQuantidadeTeste4 = new javax.swing.JLabel();
        lblValorTeste4 = new javax.swing.JLabel();
        jPanelItemTeste5 = new javax.swing.JPanel();
        lblNomeProdutoTeste5 = new javax.swing.JLabel();
        lblQuantidadeTeste5 = new javax.swing.JLabel();
        lblValorTeste5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblData = new javax.swing.JLabel();
        jPanelCabecalho = new javax.swing.JPanel();
        lblNomeRestaurante = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanelItems = new javax.swing.JPanel();

        jPanelItemTeste2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste2.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste2.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste2.setText("Nome do Item");
        lblNomeProdutoTeste2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste2.add(lblNomeProdutoTeste2);

        lblQuantidadeTeste2.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste2.setText("x99");
        lblQuantidadeTeste2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste2.add(lblQuantidadeTeste2);

        lblValorTeste2.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste2.setText("R$999,99");
        lblValorTeste2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste2.add(lblValorTeste2);

        jPanelItemTeste1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste1.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste1.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste1.setText("Nome do Item");
        lblNomeProdutoTeste1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste1.add(lblNomeProdutoTeste1);

        lblQuantidadeTeste1.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste1.setText("x99");
        lblQuantidadeTeste1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste1.add(lblQuantidadeTeste1);

        lblValorTeste1.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste1.setText("R$999,99");
        lblValorTeste1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste1.add(lblValorTeste1);

        jPanelItemTeste.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste.setText("Nome do Item");
        lblNomeProdutoTeste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste.add(lblNomeProdutoTeste);

        lblQuantidadeTeste.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste.setText("x99");
        lblQuantidadeTeste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste.add(lblQuantidadeTeste);

        lblValorTeste.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste.setText("R$999,99");
        lblValorTeste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste.add(lblValorTeste);

        jPanelItemTeste3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste3.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste3.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste3.setText("Nome do Item");
        lblNomeProdutoTeste3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste3.add(lblNomeProdutoTeste3);

        lblQuantidadeTeste3.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste3.setText("x99");
        lblQuantidadeTeste3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste3.add(lblQuantidadeTeste3);

        lblValorTeste3.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste3.setText("R$999,99");
        lblValorTeste3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste3.add(lblValorTeste3);

        jPanelItemTeste4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste4.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste4.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste4.setText("Nome do Item");
        lblNomeProdutoTeste4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste4.add(lblNomeProdutoTeste4);

        lblQuantidadeTeste4.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste4.setText("x99");
        lblQuantidadeTeste4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste4.add(lblQuantidadeTeste4);

        lblValorTeste4.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste4.setText("R$999,99");
        lblValorTeste4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste4.add(lblValorTeste4);

        jPanelItemTeste5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 1, 0));
        jPanelItemTeste5.setLayout(new java.awt.GridLayout(1, 3));

        lblNomeProdutoTeste5.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeProdutoTeste5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeProdutoTeste5.setText("Nome do Item");
        lblNomeProdutoTeste5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste5.add(lblNomeProdutoTeste5);

        lblQuantidadeTeste5.setForeground(new java.awt.Color(51, 51, 51));
        lblQuantidadeTeste5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantidadeTeste5.setText("x99");
        lblQuantidadeTeste5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste5.add(lblQuantidadeTeste5);

        lblValorTeste5.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTeste5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTeste5.setText("R$999,99");
        lblValorTeste5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelItemTeste5.add(lblValorTeste5);

        setBackground(new java.awt.Color(255, 102, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        setMaximumSize(new java.awt.Dimension(600, 2000));
        setMinimumSize(new java.awt.Dimension(500, 70));
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        lblData.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblData.setForeground(new java.awt.Color(51, 51, 51));
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblData.setText("29/03/2000 19:30:21");
        lblData.setAlignmentX(0.5F);
        lblData.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(lblData);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(jPanel1, gridBagConstraints);

        jPanelCabecalho.setBackground(new java.awt.Color(184, 170, 160));
        jPanelCabecalho.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 0));
        jPanelCabecalho.setLayout(new java.awt.GridBagLayout());

        lblNomeRestaurante.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblNomeRestaurante.setForeground(new java.awt.Color(51, 51, 51));
        lblNomeRestaurante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeRestaurante.setText("Nome Res");
        lblNomeRestaurante.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanelCabecalho.add(lblNomeRestaurante, gridBagConstraints);

        lblNumero.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(51, 51, 51));
        lblNumero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumero.setText("Numero Pedido");
        lblNumero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanelCabecalho.add(lblNumero, gridBagConstraints);

        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(51, 51, 51));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Status Pedido");
        lblStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanelCabecalho.add(lblStatus, gridBagConstraints);

        lblValorTotal.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        lblValorTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTotal.setText("Valor total pedido");
        lblValorTotal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanelCabecalho.add(lblValorTotal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanelCabecalho.add(lblImg, gridBagConstraints);

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguardando Confirmação", "Preparo", "Em Rota", "Finalizado" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelCabecalho.add(cbStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        add(jPanelCabecalho, gridBagConstraints);

        jPanelItems.setBackground(new java.awt.Color(233, 229, 226));
        jPanelItems.setOpaque(false);
        jPanelItems.setLayout(new java.awt.GridLayout(0, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        add(jPanelItems, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Ativado quando o valor da comboBox é trocado
     * @param evt
     */
    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        
        if(cbStatus.getSelectedItem() != null)
            pedido.setStatus((String)cbStatus.getSelectedItem());
    }//GEN-LAST:event_cbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCabecalho;
    private javax.swing.JPanel jPanelItemTeste;
    private javax.swing.JPanel jPanelItemTeste1;
    private javax.swing.JPanel jPanelItemTeste2;
    private javax.swing.JPanel jPanelItemTeste3;
    private javax.swing.JPanel jPanelItemTeste4;
    private javax.swing.JPanel jPanelItemTeste5;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblNomeProdutoTeste;
    private javax.swing.JLabel lblNomeProdutoTeste1;
    private javax.swing.JLabel lblNomeProdutoTeste2;
    private javax.swing.JLabel lblNomeProdutoTeste3;
    private javax.swing.JLabel lblNomeProdutoTeste4;
    private javax.swing.JLabel lblNomeProdutoTeste5;
    private javax.swing.JLabel lblNomeRestaurante;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblQuantidadeTeste;
    private javax.swing.JLabel lblQuantidadeTeste1;
    private javax.swing.JLabel lblQuantidadeTeste2;
    private javax.swing.JLabel lblQuantidadeTeste3;
    private javax.swing.JLabel lblQuantidadeTeste4;
    private javax.swing.JLabel lblQuantidadeTeste5;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblValorTeste;
    private javax.swing.JLabel lblValorTeste1;
    private javax.swing.JLabel lblValorTeste2;
    private javax.swing.JLabel lblValorTeste3;
    private javax.swing.JLabel lblValorTeste4;
    private javax.swing.JLabel lblValorTeste5;
    private javax.swing.JLabel lblValorTotal;
    // End of variables declaration//GEN-END:variables
}
