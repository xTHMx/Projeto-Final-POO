/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.projetopoo.interfacegrafica;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Delivery;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Produto;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Pedido;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Restaurante;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Item;
import com.mycompany.projetopoo.interfacegrafica.Codigo.Comida;
import com.mycompany.projetopoo.interfacegrafica.Codigo.*;
import static com.mycompany.projetopoo.interfacegrafica.JFrameLogin.jarFilePath;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
/**
 * Tela principal do restaurante
 * @author jaoti
 */
public final class JFrameRestaurante extends javax.swing.JFrame {

    /**
     * Creates new form JFrameCliente
     */
    
    private final Restaurante restaurante;
    private final Delivery delivery;
    private String imgPathDialog;
    
    /**
     * Metodo para carregas os pedidos
     **/
    private void carregarDadosPedidos(){
        panelPedidos.removeAll();
        Collections.reverse(restaurante.getPedidos());
        //Adiciona um panel para cada pedido
        for(Pedido pedido : restaurante.getPedidos()){
            JPanelPedido panelPedido = new JPanelPedido(pedido,JPanelPedido.Tela.RESTAURANTE);
            panelPedidos.add(panelPedido);
        }
        pack();
    }
   
    /**
     * Metodo para carregar os Restaurantes no panel
     **/
    private void carregarDadosCardapio(){
        //Zerar o panel
        panelCardapio.removeAll();
     
        //Variaveis para facilitar a logica
        List<Item> items =  restaurante.getListaItem(); //Pega o cardapio
        int quantidadeItems = items.size();//Quantidade de items
        
        //Loop para cada item do cardapio
        for(int i = 0;i<quantidadeItems;i++){
            Item item = items.get(i);
            JPanelItem panelItem = new JPanelItem(item,JPanelItem.Tela.LISTA);
            //Adicionar o evento de mouse click para mostrar os detalhes do item
            panelItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    itemCardapioDialog(panelItem.getItem());
                }    
            }); 
            panelCardapio.add(panelItem);     
        }
        this.pack();
    }
    
    /**
     * Método para carregar os dados da conta na tab de conta
     **/
    private void carregarDadosConta(){
        //Carrega as labels com os dados do cliente
        this.lblOla.setText("Olá, " + restaurante.getNomeReal());
        this.lblNome.setText("Nome de Usuário: " + restaurante.getNome());
        this.lblCpf.setText("CNPJ: " + restaurante.getDocumento());
        this.lblCodigo.setText("Codigo: "+ String.valueOf(restaurante.getCodigo()));
        this.lblNumeroPedidos.setText("Número de pedidos: "+String.valueOf(restaurante.getPedidos().size()));
        this.carregarImagemPerfil(restaurante.getResolvedProfilePath());
    }
    
    /**
     * Abre um fileChooser para o usuario selecionar a nova imagem
     **/
    private void mudarImagemPerfil(){
        String path;
        //Inicializa o fileChooser no caminho antigo se n estiver vazio
        if(restaurante.getResolvedProfilePath().isBlank())
            path = JFrameLogin.jarFilePath;
        else
            path = restaurante.getResolvedProfilePath();
        
        JFileChooser fileChooser = new JFileChooser(path);
        int option = fileChooser.showOpenDialog(this);
        //A pessoa selecionou a imagem
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //Coloca na label a imagem do path selecionado
            carregarImagemPerfil(selectedFile.getAbsolutePath());
            //Salva o caminho na conta
            restaurante.setRelativeProfilePath(selectedFile.getAbsolutePath());
        }
    }
    /**
     * Método para colocar a imagem na label
     * @param path
     **/
    private void carregarImagemPerfil(String path){
        this.lblImg.setIcon(JFrameLogin.getImageIconFromPath(path,200));
    }
    
    /**
     * Metodo para iniciar a tela no inicio (Cardapio)
     **/
    public void iniciarTelaNoInicio(){
        //Mostra o panel de início
        this.panelCardapio.setVisible(true);
        this.panelPedidos.setVisible(false);
        this.panelConta.setVisible(false);
        this.jScrollPaneMaster.setColumnHeaderView(this.btnAddItemCardapio);
        carregarDadosCardapio();
    }
    
    
    /**
     * Abre o Dialog de edição e criação de items
     * @param item
     **/
    private void itemCardapioDialog(Item item){
        //Verifica se esta criando ou modificando um item
        
        //Cria os campos
        JComboBox cbTipoItem = new JComboBox();
        JLabel lblImagem = new JLabel("Imagem do produto",UIManager.getIcon("FileView.fileIcon"),JLabel.CENTER);
        lblImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblImagem.setPreferredSize(new Dimension(100,120));
        cbTipoItem.addItem("Comida");
        cbTipoItem.addItem("Produto");
        JTextField txtNome = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtTipo = new JTextField();
        //Coloca os campos num panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(cbTipoItem);
        panel.add(lblImagem);
        panel.add(new JLabel("Nome do item: "));
        panel.add(txtNome);
        panel.add(new JLabel("Preço: "));
        panel.add(txtPreco);
        panel.add(new JLabel("Tipo: "));
        panel.add(txtTipo);
        

        //Item nulo? (Novo item?)
        if(item != null){
            //Colocar os dados gerais nos campos
            txtNome.setText(item.getNome());
            txtPreco.setText(String.valueOf(item.getPreco()));
            txtTipo.setText(item.getTipo());
            cbTipoItem.setSelectedItem(item.getClass().getSimpleName());  //Coloca a informação correta no comboBox  
            //Colocar a imagem pela primeira vez
            if(item.getImgPath() != null  && !item.getImgPath().isBlank()){//Verificar se tem imagem
                ImageIcon imageIcon = new ImageIcon(item.getResolvedImgPath());
                //Normaliza a imagem para 200x200
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
                //Coloca a imagem na label
                lblImagem.setIcon(imageIcon);
                lblImagem.setText("");
                imgPathDialog = item.getResolvedImgPath();

            }else{
                imgPathDialog = JFrameLogin.jarFilePath;
            }
        }else{
            imgPathDialog = JFrameLogin.jarFilePath;
        }
        //Adiciona um listener para abrir um fileChooser e mudar a imagem da label  
        lblImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                JFileChooser fileChooser = new JFileChooser(imgPathDialog);
                int option = fileChooser.showOpenDialog(panel);
                //A pessoa selecionou a imagem
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //Coloca na label a imagem do path selecionado
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    //Normaliza a imagem para 100x100
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(image);
                    //Coloca a imagem na label
                    lblImagem.setIcon(imageIcon);
                    lblImagem.setText("");
                    imgPathDialog = selectedFile.getAbsolutePath();
                }
            }
            
        });
        
        //Cria os campos da comida
        JPanel panelComida = new JPanel(); //Cria novo panel
        panelComida.setLayout(new BoxLayout(panelComida, BoxLayout.Y_AXIS));
        JSpinner nmbServeQuantos = new JSpinner();
        nmbServeQuantos.setModel(new SpinnerNumberModel(0,0,100,1));
        JTextField txtTamanho = new JTextField();
        JCheckBox chkVegetariano = new JCheckBox("É Vegetariano?");
        JCheckBox chkGelado = new JCheckBox("É Gelado?");
        JCheckBox chkSemAcucar = new JCheckBox("É Sem Açucar?");
        //Adiciona no panel da comida
        panelComida.add(new JLabel("Serve quantos?: "));
        panelComida.add(nmbServeQuantos);
        panelComida.add(chkVegetariano);
        panelComida.add(chkGelado);
        panelComida.add(chkSemAcucar);
        
        //Cria o campos eo panel do produto
        JPanel panelProduto = new JPanel(); //Cria novo panel
        panelProduto.setLayout(new BoxLayout(panelProduto, BoxLayout.Y_AXIS));
        JTextField txtMarca = new JTextField();
        //Adiciona no panel
        panelProduto.add(new JLabel("Marca: "));
        panelProduto.add(txtMarca);   
        
        //Adiciona os dois panels ao panel principal
        panel.add(panelComida);
        panel.add(panelProduto);
        
        //Item nulo? (Novo item?)
        if(item != null){
            //Comida ou Produto?? Para adicionar os dados
            if(item instanceof Comida comida){                //Adiciona os dados
                nmbServeQuantos.setValue(comida.getServeQuantos());
                chkVegetariano.setSelected(comida.getVegetariano());
                chkGelado.setSelected(comida.getGelado());
                chkSemAcucar.setSelected(comida.getSemAcucar());

            }
            else{//Produto
                Produto produto = (Produto)item;
                txtMarca.setText(produto.getMarca());
            }
        }
        //Listener para quando o tipo selecionado muda
        cbTipoItem.addActionListener((ActionEvent e) -> {
            String strSelecionado = String.valueOf(cbTipoItem.getSelectedItem());
            //Comida ou Produto?
            if(strSelecionado.equals("Comida")){ //Mostra os campos de comida
                panelComida.setVisible(true);
                panelProduto.setVisible(false);
            }else{ //Mostra os campos de produto
                panelComida.setVisible(false);
                panelProduto.setVisible(true);
            }
        });
       
        //Abre um dialog com o panel principal
        int result = JOptionPane.showConfirmDialog(this, panel, "Salvar item no cardápio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        //Usuario apertou em OK?
        if (result == JOptionPane.OK_OPTION) {
            //Item novo? = null
            if(item == null){
                //Verificar o comboBox para instanciar comida ou produto
                if(cbTipoItem.getSelectedItem().equals("Comida")){
                    item = new Comida(restaurante);
                }
                else if(cbTipoItem.getSelectedItem().equals("Produto")){
                    item = new Produto(restaurante);
                }
            }
            //Colocar os novos dados
            if(txtNome.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Nome inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            item.setNome(txtNome.getText());
            try{
                item.setPreco(Float.parseFloat(txtPreco.getText()));
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            item.setTipo(txtTipo.getText());
            item.setRelativeImgPath(imgPathDialog);
            //Comida ou Produto?
            if(item instanceof Comida comida){ //Comida
                //Finalizamos o objeto comida
                comida.setServeQuantos((int)nmbServeQuantos.getValue());
                comida.setTamanho(txtTamanho.getText());
                comida.setVegetariano(chkVegetariano.isSelected());
                comida.setGelado(chkGelado.isSelected());
                comida.setSemAcucar(chkSemAcucar.isSelected());
            }else{//Produto
                //Finalizamos o objeto produto
                Produto produto = (Produto)item;
                produto.setMarca(txtMarca.getText());
            }            
            
            //Temos um item com todos os dados necessários, agora resta adicionar ou atualizar esse item
            //Verificar o código para ver se é um produto novo ou antigo
            if(item.getCodigo() == 0 ){
                item.setCodigo(delivery.getLastCodigoItem()+1);//Coloca o novo código do produto
                delivery.cadastraItem(restaurante, item); //Cadastra um novo item
                JOptionPane.showMessageDialog(this, "Item cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else{
                //Não faz nada com o item, pois o mesmo ja foi atualizado
                JOptionPane.showMessageDialog(this, "Item atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            btnCardapio.doClick();
        }   
    }
    
    /**
     * Construtor da tela de navegação de Restaurante
     * @param delivery
     * @param restaurante
     **/
    public JFrameRestaurante(Delivery delivery,Restaurante restaurante) {
        initComponents();
        this.panelCardapio.setLayout(new BoxLayout(panelCardapio,BoxLayout.Y_AXIS)); //Deixa o panel de forma a receber uma lista de Panelitems
        this.delivery = delivery;
        this.restaurante = restaurante;
        jScrollPaneMaster.getVerticalScrollBar().setUnitIncrement(20);
        this.setIconImage(new ImageIcon(jarFilePath + "\\imagens" + "\\" + "DefaultWindowIcon.png").getImage());
        //Vamos colocar para inicar do começo
        iniciarTelaNoInicio();
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

        jCheckBox1 = new javax.swing.JCheckBox();
        btnAddItemCardapio = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPaneMaster = new javax.swing.JScrollPane();
        jPanelMaster = new javax.swing.JPanel();
        panelCardapio = new javax.swing.JPanel();
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
        btnCardapio = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnConta = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        btnAddItemCardapio.setBackground(new java.awt.Color(238, 71, 68));
        btnAddItemCardapio.setForeground(new java.awt.Color(255, 255, 255));
        btnAddItemCardapio.setText("+");
        btnAddItemCardapio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddItemCardapio.setMaximumSize(new java.awt.Dimension(200, 3000));
        btnAddItemCardapio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemCardapioActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 0));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPaneMaster.setBackground(new java.awt.Color(255, 204, 204));
        jScrollPaneMaster.setBorder(null);
        jScrollPaneMaster.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneMaster.setColumnHeaderView(null);
        jScrollPaneMaster.setViewportView(jPanelMaster);

        jPanelMaster.setBackground(new java.awt.Color(255, 204, 204));
        jPanelMaster.setLayout(new javax.swing.BoxLayout(jPanelMaster, javax.swing.BoxLayout.Y_AXIS));

        panelCardapio.setBackground(new java.awt.Color(255, 204, 204));
        panelCardapio.setLayout(new javax.swing.BoxLayout(panelCardapio, javax.swing.BoxLayout.Y_AXIS));
        jPanelMaster.add(panelCardapio);

        panelPedidos.setBackground(new java.awt.Color(255, 204, 153));
        panelPedidos.setLayout(new javax.swing.BoxLayout(panelPedidos, javax.swing.BoxLayout.Y_AXIS));
        jPanelMaster.add(panelPedidos);

        panelConta.setBackground(new java.awt.Color(187, 232, 246));
        panelConta.setMaximumSize(new java.awt.Dimension(600, 2147483647));
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
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelConta.add(btnEditar, gridBagConstraints);

        jPanelMaster.add(panelConta);

        jScrollPaneMaster.setViewportView(jPanelMaster);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jScrollPaneMaster, gridBagConstraints);

        panelButtons.setLayout(new java.awt.GridBagLayout());

        btnCardapio.setBackground(new java.awt.Color(239, 83, 80));
        btnCardapio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCardapio.setForeground(new java.awt.Color(255, 255, 255));
        btnCardapio.setText("Cardápio");
        btnCardapio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCardapioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelButtons.add(btnCardapio, gridBagConstraints);

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        panelButtons.add(btnConta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(panelButtons, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Muda a tela para o cardapio
     **/
    private void btnCardapioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCardapioActionPerformed
        //Mostra o panel de início
        this.panelCardapio.setVisible(true);
        this.btnAddItemCardapio.setVisible(true);
        this.panelPedidos.setVisible(false);
        this.panelConta.setVisible(false);
        this.jScrollPaneMaster.setColumnHeaderView(this.btnAddItemCardapio);
        carregarDadosCardapio();
    }//GEN-LAST:event_btnCardapioActionPerformed

    /**
     * Muda a tela para Pedidos
     **/
    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        //Mostra o panel de início
        this.panelCardapio.setVisible(false);
        this.btnAddItemCardapio.setVisible(false);
        this.panelPedidos.setVisible(true);
        this.panelConta.setVisible(false);
        this.jScrollPaneMaster.setColumnHeaderView(null);
        carregarDadosPedidos();
    }//GEN-LAST:event_btnPedidosActionPerformed

    /**
     * Muda a tela para a conta
     **/
    private void btnContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaActionPerformed
        //Mostra o panel de início
        this.panelCardapio.setVisible(false);
        this.btnAddItemCardapio.setVisible(false);
        this.panelPedidos.setVisible(false);
        this.panelConta.setVisible(true);
        this.jScrollPaneMaster.setColumnHeaderView(null);
        this.carregarDadosConta();
    }//GEN-LAST:event_btnContaActionPerformed

    /**
     * Abre o JFrameFazerPedido para que seja selecionado os items do restaurante
     **/
    private void btnAddItemCardapioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemCardapioActionPerformed
        // TODO add your handling code here:
        itemCardapioDialog(null);
    }//GEN-LAST:event_btnAddItemCardapioActionPerformed
  
    /**
     * Abre o dialog para troca de imagem
     **/
    private void lblImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMouseClicked
        // TODO add your handling code here:
        this.mudarImagemPerfil();
    }//GEN-LAST:event_lblImgMouseClicked

    /**
     * Desloga e volta para tela de login
     **/
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    /**
     * Abre o dialog de trocar imagem
     **/
    private void btnImagemPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagemPerfilActionPerformed
        // TODO add your handling code here:
        this.mudarImagemPerfil();
    }//GEN-LAST:event_btnImagemPerfilActionPerformed

    /**
     * Abre o frame de editar o usuário
     **/
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        JFrameUsuario jFrameUsuario = new JFrameUsuario(delivery,restaurante);
        jFrameUsuario.setVisible(true);
        jFrameUsuario.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                for(ActionListener al : btnCardapio.getActionListeners())
                    al.actionPerformed(evt);
            }
        });
    }//GEN-LAST:event_btnEditarActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameRestaurante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameRestaurante(null,null).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItemCardapio;
    private javax.swing.JButton btnCardapio;
    private javax.swing.JButton btnConta;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnImagemPerfil;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnSair;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMaster;
    private javax.swing.JScrollPane jScrollPaneMaster;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNumeroPedidos;
    private javax.swing.JLabel lblOla;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelCardapio;
    private javax.swing.JPanel panelConta;
    private javax.swing.JPanel panelPedidos;
    // End of variables declaration//GEN-END:variables
}
