/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.projetopoo.interfacegrafica;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Delivery;
import com.mycompany.projetopoo.interfacegrafica.Codigo.ItemCarrinho;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Cliente;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Pedido;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Restaurante;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Item;
import com.mycompany.projetopoo.interfacegrafica.Codigo.*;
import static com.mycompany.projetopoo.interfacegrafica.JFrameLogin.jarFilePath;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Janela principal do cliente para fazer pedidos
 * @author jaoti
 */
public final class JFrameCliente extends javax.swing.JFrame {

    /**
     * Creates new form JFrameCliente
     */
    
    //Variaveis de sistema
    private final Cliente cliente;
    private final Delivery delivery;
    private Restaurante restaurantePedido;
    private boolean restauranteAberto;
    private enum Ordenacao{
        ALFABETICA,
        NUMERO_PEDIDOS
    }
    
    /**
     * Faz um pedido, coloca o timeStamp de agora
     */
    private void fazerPedido(){
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        delivery.realizaPedido(cliente, restaurantePedido,  date);
    }
    
    /**
     * Metodo para receber mensagem do cardapio
     * @param qtdAdd
     */
    public void processarMensagemCardapio(int qtdAdd){
        //Deixa o botão visivel
        if(!btnCarrinho.isVisible())
            if(qtdAdd > 0)  btnCarrinho.setVisible(true);
        
        carregarDadosCarrinho();
    }
    
    /**
     * Metodo para carregas os pedidos
     */
    private void carregarDadosPedidos(){
        panelPedidos.removeAll();
        //Adiciona um panel para cada pedido
        Collections.reverse(cliente.getPedidos());
        for(Pedido pedido : cliente.getPedidos()){
            JPanelPedido panelPedido = new JPanelPedido(pedido,JPanelPedido.Tela.CLIENTE);
            panelPedidos.add(panelPedido);
        }
        pack();
    }
    
    /**
     * Metodo para carregar os items do carrinho
     */
    private void carregarDadosCarrinho(){
        //Zerar o carrinho
        panelCarrinho.removeAll();
        int quantidadeItems = cliente.getCarrinho().size();//Quantidade de items
        int quantidadeItemsTotal = 0;
        lblTotal.setText("Total: R$" + String.valueOf(cliente.getValorTotalCarrinho()));
        //Loop para cada item do cardapio
        for(int i = 0;i<quantidadeItems;i++){
            ItemCarrinho itemCarrinho = cliente.getCarrinho().get(i);
            Item item = itemCarrinho.getItem();
            int quantidade = itemCarrinho.getQuantidade();
            JPanelItem panelItem = new JPanelItem(item,JPanelItem.Tela.CARRINHO);
            panelItem.setQuantidade(quantidade);
            quantidadeItemsTotal += quantidade; //Vai contando a quantidade total de itens
            //Adiciona o panel a tela de carrinho
            panelCarrinho.add(panelItem);
            
            //Adicionar o evento de mudar a quantidade de items
            JSpinner nmbQuantidade =  panelItem.getNmbQuantidade();
            nmbQuantidade.addChangeListener((ChangeEvent e) -> {
                itemCarrinho.setQuantidade((int)nmbQuantidade.getValue());
                lblQuantidadeCarrinho.setText(String.valueOf(cliente.getQuantidadeItemsCarrinho()));
                lblTotal.setText("Total: R$" + String.valueOf(cliente.getValorTotalCarrinho()));
            });
        }
        lblQuantidadeCarrinho.setText(String.valueOf(quantidadeItemsTotal));
        this.pack();//Metodo para meio que atualizar a tela  
    }
    
    /**
     * Metodo para carregar os Restaurantes no panel
     * @param restaurantes
     */
    private void carregarDadosRestaurante(List<Restaurante> restaurantes){
        //Zerar o panel
        panelBusca.removeAll();
        
        int quantidadeItems = restaurantes.size();//Quantidade de items

        //Loop para cada restaurante na lista
        for(int i = 0;i<quantidadeItems;i++){
            Restaurante res = restaurantes.get(i);
            //Adiciona um panel para funcionar como margem
            JPanelRestaurante panelRes = new JPanelRestaurante(res);
            panelRes.setCursor(new Cursor(Cursor.HAND_CURSOR));
            //Adiciona o panel (card) a tela principal
            panelBusca.add(panelRes);
            
            
            JFrameCliente jFrameCliente = this;
            //Adicionar o evento de mouse click para mostrar os detalhes do item
            panelRes.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //O que fazer quando o usuario clica no card?
                    //Ve se algum card foi aberto, se nao abre um novo
                    if(!restauranteAberto){
                        JFrameFazerPedido jFramePedido = new JFrameFazerPedido(panelRes.getRestaurante(),cliente,jFrameCliente);
                        jFramePedido.setLocation(jFrameCliente.getX() + jFrameCliente.getWidth(), jFrameCliente.getY());
                        jFramePedido.addWindowListener(new WindowAdapter(){
                            @Override
                            public void windowClosing(WindowEvent e){
                                restauranteAberto = false;
                            }
                        });
                        restaurantePedido = panelRes.getRestaurante();
                        jFramePedido.setVisible(true);
                        restauranteAberto = true;
                    }  
                }    
            });
        }
        this.revalidate();
        this.pack();//Metodo para meio que atualizar a tela  
    }
    
    /**
     * Método para carregar os dados da conta na tab de conta
     */
    private void carregarDadosConta(){
        //Carrega as labels com os dados do cliente
        this.lblOla.setText("Olá, " + cliente.getNomeReal());
        this.lblNome.setText("Nome: " + cliente.getNome());
        this.lblCpf.setText("CPF: " + cliente.getDocumento());
        this.lblCodigo.setText("Codigo: "+ String.valueOf(cliente.getCodigo()));
        this.lblNumeroPedidos.setText("Número de pedidos: "+String.valueOf(cliente.getPedidos().size()));
        this.carregarImagemPerfil(cliente.getResolvedProfilePath());
    }
    
    /**
     * Abre um fileChooser para o usuario selecionar a nova imagem
     */
    private void mudarImagemPerfil(){
        String path;
        //Inicializa o fileChooser no caminho antigo se n estiver vazio
        if(cliente.getResolvedProfilePath().isBlank())
            path = JFrameLogin.jarFilePath;
        else
            path = cliente.getResolvedProfilePath();
        
        JFileChooser fileChooser = new JFileChooser(path);
        int option = fileChooser.showOpenDialog(this);
        //A pessoa selecionou a imagem
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //Coloca na label a imagem do path selecionado
            carregarImagemPerfil(selectedFile.getAbsolutePath());
            //Salva o caminho na conta
            cliente.setRelativeProfilePath(selectedFile.getAbsolutePath());
        }
    }
    /**
     * Método para colocar a imagem na label
     * @param path
     */
    private void carregarImagemPerfil(String path){
        this.lblImg.setIcon(JFrameLogin.getImageIconFromPath(path,200));
    }
    
    /**
     * Inicia a tela no inicio (Busca de restaurantes)
     */
    public void iniciarTelaNoInicio(){
        //Mostra o panel de início
        btnBusca.doClick();
        //Esconde o btn do carrinho
        btnCarrinhoFicaVisivel();
        //Evita um bug quando não tem restaurante para busca
        if(delivery.getRestaurantes().isEmpty())
            panelBusca.setVisible(false);
    }
    
    /**
     * Mostra ou esconde o botão de carrinho baseado em algumas condições
     */
    private void btnCarrinhoFicaVisivel(){
        
        if(cliente.getCarrinho().isEmpty() || panelCarrinho.isVisible()){
            btnCarrinho.setVisible(false); //Não aparece
        }else{
            btnCarrinho.setVisible(true);
        }  
    }
 
    /**
     * Adiciona o botão do carrinho, mas não o mostra por enquanto
     */
    private void initBtnCarrinho(){
        btnCarrinho.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnCarrinho.add(lblQuantidadeCarrinho);
        //Quando o botao for pressionado mostra o panel do carrinho
        btnCarrinho.addActionListener(e -> {
            this.panelBusca.setVisible(false);
            this.panelPedidos.setVisible(false);
            this.panelConta.setVisible(false);
            this.panelCarrinho.setVisible(true);
            jScrollPaneMaster.setColumnHeaderView(panelCardBtnPedido);
            this.panelFiltros.setVisible(false);
            btnCarrinho.setVisible(false); //Esconde o botão do carrinho
            carregarDadosCarrinho();
            pack();
        });
    }
    
    /**
     * Adiicona um design customizado para alguns componentes
     */
    private void customDesign(){
        jScrollPaneMaster.getVerticalScrollBar().setUnitIncrement(20);
        initBtnCarrinho();
    }
    /**
     * Construtor da classe
     * @param cliente
     * @param delivery
     */
    public JFrameCliente(Cliente cliente, Delivery delivery) {
        initComponents();
        this.setIconImage(new ImageIcon(jarFilePath + "\\imagens" + "\\" + "DefaultWindowIcon.png").getImage());
        this.cliente = cliente;
        this.delivery = delivery;
        this.panelBusca.setLayout(new BoxLayout(panelBusca,BoxLayout.Y_AXIS)); //Deixa o panel de forma a receber uma lista de Panelitems
        //Vamos colocar para inicar do começo
        iniciarTelaNoInicio();
        customDesign();
        
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

        panelFiltros = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        btnOrdem = new javax.swing.JButton();
        cbOrdem = new javax.swing.JComboBox<>();
        lblQuantidadeCarrinho = new javax.swing.JLabel();
        panelCardBtnPedido = new javax.swing.JPanel();
        btnFazerPedido = new javax.swing.JButton();
        btnLimparCarrinho = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jLayeredPaneMaster = new javax.swing.JLayeredPane();
        jPanelBtnCarrinho = new javax.swing.JPanel();
        btnCarrinho = new javax.swing.JButton();
        jScrollPaneMaster = new javax.swing.JScrollPane();
        jPanelMaster = new javax.swing.JPanel();
        panelCarrinho = new javax.swing.JPanel();
        panelBusca = new javax.swing.JPanel();
        panelPedidos = new javax.swing.JPanel();
        panelConta = new javax.swing.JPanel();
        lblOla = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblCpf = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNumeroPedidos = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnImagemPerfil = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        panelButtons = new javax.swing.JPanel();
        btnBusca = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnConta = new javax.swing.JButton();

        panelFiltros.setBackground(new java.awt.Color(255, 255, 255));
        panelFiltros.setForeground(new java.awt.Color(255, 255, 255));
        panelFiltros.setLayout(new java.awt.GridBagLayout());

        txtPesquisa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 5);
        panelFiltros.add(txtPesquisa, gridBagConstraints);

        btnPesquisar.setBackground(new java.awt.Color(238, 71, 68));
        btnPesquisar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        panelFiltros.add(btnPesquisar, gridBagConstraints);

        btnOrdem.setBackground(new java.awt.Color(238, 71, 68));
        btnOrdem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOrdem.setForeground(new java.awt.Color(255, 255, 255));
        btnOrdem.setText("Ordenar");
        btnOrdem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 10);
        panelFiltros.add(btnOrdem, gridBagConstraints);

        cbOrdem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbOrdem.setForeground(new java.awt.Color(255, 255, 255));
        cbOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alfabética", "Nº de Pedidos" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 10, 5);
        panelFiltros.add(cbOrdem, gridBagConstraints);

        lblQuantidadeCarrinho.setText("jLabel1");

        panelCardBtnPedido.setBackground(new java.awt.Color(255, 204, 204));
        panelCardBtnPedido.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        panelCardBtnPedido.setForeground(new java.awt.Color(255, 204, 204));
        panelCardBtnPedido.setLayout(new java.awt.GridBagLayout());

        btnFazerPedido.setBackground(new java.awt.Color(230, 179, 101));
        btnFazerPedido.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnFazerPedido.setForeground(new java.awt.Color(255, 255, 255));
        btnFazerPedido.setText("Fazer Pedido");
        btnFazerPedido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFazerPedido.setPreferredSize(new java.awt.Dimension(97, 50));
        btnFazerPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFazerPedidoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        panelCardBtnPedido.add(btnFazerPedido, gridBagConstraints);

        btnLimparCarrinho.setBackground(new java.awt.Color(242, 126, 110));
        btnLimparCarrinho.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnLimparCarrinho.setForeground(new java.awt.Color(255, 255, 255));
        btnLimparCarrinho.setText("Limpar Carrinho");
        btnLimparCarrinho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimparCarrinho.setPreferredSize(new java.awt.Dimension(97, 50));
        btnLimparCarrinho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCarrinhoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panelCardBtnPedido.add(btnLimparCarrinho, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 0, 0));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelCardBtnPedido.add(lblTotal, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLayeredPaneMaster.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPaneMaster.setForeground(new java.awt.Color(255, 255, 255));
        jLayeredPaneMaster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLayeredPaneMaster.setEnabled(false);
        jLayeredPaneMaster.setLayout(new java.awt.GridBagLayout());

        jPanelBtnCarrinho.setBackground(new java.awt.Color(204, 255, 51));
        jPanelBtnCarrinho.setOpaque(false);
        jPanelBtnCarrinho.setLayout(new java.awt.GridBagLayout());

        btnCarrinho.setBackground(new java.awt.Color(184, 214, 160));
        btnCarrinho.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCarrinho.setForeground(new java.awt.Color(0, 0, 0));
        btnCarrinho.setText("Carrinho");
        btnCarrinho.setAlignmentX(0.5F);
        btnCarrinho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarrinho.setMaximumSize(new java.awt.Dimension(500, 60));
        btnCarrinho.setPreferredSize(new java.awt.Dimension(200, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanelBtnCarrinho.add(btnCarrinho, gridBagConstraints);

        jLayeredPaneMaster.setLayer(jPanelBtnCarrinho, javax.swing.JLayeredPane.POPUP_LAYER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jLayeredPaneMaster.add(jPanelBtnCarrinho, gridBagConstraints);

        jScrollPaneMaster.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneMaster.setBorder(null);
        jScrollPaneMaster.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPaneMaster.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneMaster.setOpaque(false);
        jScrollPaneMaster.setViewportView(jPanelMaster);

        jPanelMaster.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMaster.setForeground(new java.awt.Color(255, 255, 255));
        jPanelMaster.setLayout(new javax.swing.BoxLayout(jPanelMaster, javax.swing.BoxLayout.PAGE_AXIS));

        panelCarrinho.setBackground(new java.awt.Color(255, 204, 204));
        panelCarrinho.setLayout(new javax.swing.BoxLayout(panelCarrinho, javax.swing.BoxLayout.Y_AXIS));
        jPanelMaster.add(panelCarrinho);

        panelBusca.setBackground(new java.awt.Color(253, 232, 232));
        panelBusca.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 0, 5));
        panelBusca.setForeground(new java.awt.Color(255, 255, 255));
        panelBusca.setLayout(new javax.swing.BoxLayout(panelBusca, javax.swing.BoxLayout.Y_AXIS));
        jPanelMaster.add(panelBusca);

        panelPedidos.setBackground(new java.awt.Color(251, 244, 233));
        panelPedidos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 0, 5));
        panelPedidos.setForeground(new java.awt.Color(255, 255, 255));
        panelPedidos.setLayout(new javax.swing.BoxLayout(panelPedidos, javax.swing.BoxLayout.Y_AXIS));
        jPanelMaster.add(panelPedidos);

        panelConta.setBackground(new java.awt.Color(187, 232, 246));
        panelConta.setLayout(new java.awt.GridBagLayout());

        lblOla.setBackground(new java.awt.Color(0, 0, 0));
        lblOla.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblOla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOla.setText("Olá, fullano de tal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblOla, gridBagConstraints);

        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblImg, gridBagConstraints);

        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNome.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblNome, gridBagConstraints);

        lblCpf.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCpf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCpf.setText("Documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblCpf, gridBagConstraints);

        lblCodigo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("Codigo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblCodigo, gridBagConstraints);

        lblNumeroPedidos.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lblNumeroPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumeroPedidos.setText("Numero Pedidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(lblNumeroPedidos, gridBagConstraints);

        btnSair.setBackground(new java.awt.Color(239, 83, 80));
        btnSair.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSair.setForeground(new java.awt.Color(102, 102, 102));
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        panelConta.add(btnSair, gridBagConstraints);

        btnImagemPerfil.setText("Mudar Imagem de Perfil");
        btnImagemPerfil.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnImagemPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagemPerfilActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelConta.add(btnImagemPerfil, gridBagConstraints);

        btnEditar.setText("Editar Conta");
        btnEditar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelConta.add(btnEditar, gridBagConstraints);

        jPanelMaster.add(panelConta);

        jScrollPaneMaster.setViewportView(jPanelMaster);

        jLayeredPaneMaster.setLayer(jScrollPaneMaster, javax.swing.JLayeredPane.PALETTE_LAYER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jLayeredPaneMaster.add(jScrollPaneMaster, gridBagConstraints);

        panelButtons.setBackground(new java.awt.Color(255, 255, 255));
        panelButtons.setForeground(new java.awt.Color(255, 255, 255));
        panelButtons.setLayout(new java.awt.GridBagLayout());

        btnBusca.setBackground(new java.awt.Color(239, 83, 80));
        btnBusca.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBusca.setForeground(new java.awt.Color(255, 255, 255));
        btnBusca.setText("Busca");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelButtons.add(btnBusca, gridBagConstraints);

        btnPedidos.setBackground(new java.awt.Color(232, 185, 116));
        btnPedidos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPedidos.setForeground(new java.awt.Color(255, 255, 255));
        btnPedidos.setText("Pedidos");
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelButtons.add(btnPedidos, gridBagConstraints);

        btnConta.setBackground(new java.awt.Color(55, 188, 229));
        btnConta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConta.setForeground(new java.awt.Color(255, 255, 255));
        btnConta.setText("Conta");
        btnConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelButtons.add(btnConta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jLayeredPaneMaster.add(panelButtons, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneMaster, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneMaster, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Vai para a tela de busca (Restaurantes)
     */
    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        //Mostra o panel de Busca
        carregarDadosRestaurante(delivery.getRestaurantes());
        this.panelBusca.setVisible(true);
        this.panelPedidos.setVisible(false);
        this.panelConta.setVisible(false);
        this.panelCarrinho.setVisible(false);
        jScrollPaneMaster.setColumnHeaderView(panelFiltros);
        panelFiltros.setVisible(true);
        btnCarrinhoFicaVisivel();
    }//GEN-LAST:event_btnBuscaActionPerformed

    /**
     * Vai para a tela de pedidos
     */
    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        //Mostra o panel de PEdidos
        this.panelBusca.setVisible(false);
        this.panelPedidos.setVisible(true);
        this.panelConta.setVisible(false);
        this.panelCarrinho.setVisible(false);
        jScrollPaneMaster.setColumnHeaderView(null);
        carregarDadosPedidos();
        btnCarrinhoFicaVisivel();
    }//GEN-LAST:event_btnPedidosActionPerformed

    /**
     * Vai para a tela de conta
     */
    private void btnContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaActionPerformed
        //Mostra o panel de Conta
        this.panelBusca.setVisible(false);
        this.panelPedidos.setVisible(false);
        this.panelConta.setVisible(true);
        this.panelCarrinho.setVisible(false);
        jScrollPaneMaster.setColumnHeaderView(null);
        this.carregarDadosConta();
        btnCarrinhoFicaVisivel();
    }//GEN-LAST:event_btnContaActionPerformed

    /**
     * Pesquisa os restaurantes que contem o termo digitado
     * @param nome
     */
    private void pesquisarRestaurante(String nome){
        List<Restaurante> list = delivery.pesquisa(restaurantePedido, nome, "", delivery.getRestaurantes());
        Ordenacao ordenacao;
        if(cbOrdem.getSelectedItem().equals("Nº de Pedidos")) ordenacao = Ordenacao.NUMERO_PEDIDOS;
        else ordenacao = Ordenacao.ALFABETICA;
        //Ordena a lista
        switch (ordenacao){
            case ALFABETICA -> Collections.sort(list, (item1,item2) -> item1.getNomeReal().compareTo(item2.getNomeReal()));
            case NUMERO_PEDIDOS -> Collections.sort(list, (item1,item2) -> item2.getPedidos().size() - item1.getPedidos().size());
        }
        
        carregarDadosRestaurante(list);
    }
    
    /**
     * Pesquisa em todos os restaurantes o termo digitado
     */
    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        //Faz a pesquisa
        pesquisarRestaurante(txtPesquisa.getText());
    }//GEN-LAST:event_btnPesquisarActionPerformed

    /**
     * Ordena a lista de restaurantes baseado no que foi selecionado
     */
    private void btnOrdemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemActionPerformed
        //Faz a pesquisa e ordenação
        pesquisarRestaurante(txtPesquisa.getText());
    }//GEN-LAST:event_btnOrdemActionPerformed

    /**
     * Deslogar da conta, volta para tela de login
     */
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    /**
     * Mudar a imagem de perfil ao clicar no botao correspondente
     */
    private void btnImagemPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagemPerfilActionPerformed
        // TODO add your handling code here:
        this.mudarImagemPerfil();
    }//GEN-LAST:event_btnImagemPerfilActionPerformed

    /**
     * Abre o dialog para mudar a imagem de perfil quando clicar nela
     */
    private void lblImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMouseClicked
        // TODO add your handling code here:
        this.mudarImagemPerfil();
    }//GEN-LAST:event_lblImgMouseClicked

     /**
     * Para pesquisar com o botão enter
     */
    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            pesquisarRestaurante(txtPesquisa.getText());
        
    }//GEN-LAST:event_txtPesquisaKeyPressed

     /**
     * Abre a janela de edição do usuário
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        //Editar a conta
        JFrameUsuario jFrameUsuario = new JFrameUsuario(delivery,cliente);
        jFrameUsuario.setVisible(true);
        jFrameUsuario.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                btnConta.doClick();
            }
        });
    }//GEN-LAST:event_btnEditarActionPerformed

     /**
     * Faz o pedido com os itens que estão no carrinho
     */
    private void btnFazerPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFazerPedidoActionPerformed
        // TODO add your handling code here:
        //Fazer o pedido
        fazerPedido();
        JOptionPane.showMessageDialog(this, "Pedido Realizado com sucesso!");
        btnPedidos.doClick();
    }//GEN-LAST:event_btnFazerPedidoActionPerformed

    /**
     * Limpa o carrinho
     */
    private void btnLimparCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCarrinhoActionPerformed
        cliente.getCarrinho().clear();
        btnBusca.doClick();
    }//GEN-LAST:event_btnLimparCarrinhoActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameCliente(null,null).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCarrinho;
    private javax.swing.JButton btnConta;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFazerPedido;
    private javax.swing.JButton btnImagemPerfil;
    private javax.swing.JButton btnLimparCarrinho;
    private javax.swing.JButton btnOrdem;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cbOrdem;
    private javax.swing.JLayeredPane jLayeredPaneMaster;
    private javax.swing.JPanel jPanelBtnCarrinho;
    private javax.swing.JPanel jPanelMaster;
    private javax.swing.JScrollPane jScrollPaneMaster;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNumeroPedidos;
    private javax.swing.JLabel lblOla;
    private javax.swing.JLabel lblQuantidadeCarrinho;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelBusca;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelCardBtnPedido;
    private javax.swing.JPanel panelCarrinho;
    private javax.swing.JPanel panelConta;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelPedidos;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
