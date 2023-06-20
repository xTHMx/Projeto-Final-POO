package com.mycompany.projetopoo.interfacegrafica.Codigo;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Classe principal do software, é nele que ocorre o processamento de tudo
 */
public class Delivery {
    private final List<Restaurante> restaurantes = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Item> itens = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private final String itemPath;
    private final String usuarioPath;
    private final String pedidosPath;

    /**
     * Inicializa os dados do Delivery
     * @param itemPath
     * @param usuarioPath
     * @param pedidosPath
     */
    public Delivery(String itemPath, String usuarioPath, String pedidosPath){ //inicializa dados
        //Define o caminho dos arquivos
        this.itemPath = itemPath;
        this.usuarioPath = usuarioPath;
        this.pedidosPath = pedidosPath;
        carregarDados();  
    }
    
    /**
     * Carrega os Dados
     */
    private void carregarDados(){
        carregarUsuariosDB();
        carregarItemsDB();
        carregarPedidosDB();
    }

    /**
     * Carrega os Items do Banco de Dados
     */
    private void carregarItemsDB(){
        BufferedReader bfread;
        String line;
        //carrega items cadastrados
        try{
            bfread = new BufferedReader(new FileReader(itemPath));

            while((line = bfread.readLine()) != null ){
                String[] splitted = line.split("#");
                if(splitted.length != 0) {
                    //Vamos adicionar um item
                    Item item;
                    Restaurante res;
                    switch (splitted[0]) {
                        case "Comida" -> {
                            //qual o tipo de item
                            res = busca(Integer.parseInt(splitted[11]),restaurantes);
                            item = new Comida(splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5], //valores separados por #
                                    Integer.parseInt(splitted[6]),splitted[7],Boolean.parseBoolean(splitted[8]),Boolean.parseBoolean(splitted[9]),Boolean.parseBoolean(splitted[10]),res);
                        }
                        case "Produto" -> {
                            res = busca(Integer.parseInt(splitted[7]),restaurantes);
                            item= new Produto(splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5],splitted[6],res);
                        }
                        default -> {
                            System.out.println("Erro ao importar arquivo de item");
                            continue;
                        }
                    }
                    cadastraItem(res,item);     
                } 
            }
            //Fechar o arquivo
            bfread.close();
            
        }catch(FileNotFoundException e){ //se nao houver arquivo
            File out = new File(itemPath);
            try{
               out.createNewFile();
            }catch(IOException f){
                System.out.println("Não foi possivel criar arquivo " + f.getMessage());
            }    
        }catch(IOException e){
            System.out.println("Erro ao abrir arquivo " + e.getMessage());
        }
    }

    /**
     * Carrega os usuarios do Banco de Dados
     */
    private void carregarUsuariosDB(){
        BufferedReader bfread;
        String line;
        //Carrega usuarios
        try{
            bfread = new BufferedReader(new FileReader(usuarioPath)); 
            
            while((line = bfread.readLine()) != null){ 
                String[] splitted = line.split("#");  
                
                if(splitted.length != 0){
                    //Decidir o que fazer com cada tipo de usuario
                    Usuario user;
                    switch (splitted[0]) {
                        case "Cliente" -> //qual o tipo de usuario
                            user = new Cliente(splitted[1], splitted[2], Integer.parseInt(splitted[3]), splitted[4], splitted[5], splitted[6]);
                        case "Restaurante" -> user = new Restaurante(splitted[1], splitted[2], Integer.parseInt(splitted[3]), splitted[4], splitted[5],splitted[6]);
                        default -> {
                            System.out.println("Erro ao importar arquivo de usuario");
                            continue;
                        }
                    }
                    cadastraUsuario(user);     
                }  
            }
            bfread.close();

        }catch(FileNotFoundException e){  //arquivo não existe
            File out = new File(usuarioPath);
            try{
               out.createNewFile();
            }catch(IOException f){
                System.out.println("Não foi possivel criar arquivo " + f.getMessage());
            }
            
        }catch(IOException e){
            System.out.println("Erro ao abrir arquivo " + e.getMessage());
        }
        
    }

    /**
     * Carrega os pedidos no banco de dados
     */
    private void carregarPedidosDB(){
        BufferedReader bfread;
        String line;
        Scanner scan;
        //carrega pedidos
        try{
            bfread = new BufferedReader(new FileReader(pedidosPath));
            while((line = bfread.readLine()) != null){
                //Dividir a linha para pegar os atributos
                String[] splitted = line.split("#"); 
                if(splitted.length != 0) {
                    //Adiciona os dados do pedido
                    int codigo = Integer.parseInt(splitted[0]); //Codigo do pedido
                    Restaurante res = busca(Integer.parseInt(splitted[1]),restaurantes);//Encontra o objeto de restaurante pelo codigo
                    Cliente cli = busca(Integer.parseInt(splitted[2]),clientes);
                    String data = splitted[3];


                    //Pedido sendo adicionado
                    Pedido pedidoTemp = new Pedido(codigo,res,cli,data);
                    pedidoTemp.setStatus(splitted[4]);
                    //Divide a lista de pedidos
                    scan = new Scanner(splitted[5]);
                    String subLine = scan.nextLine();
                    String[] codes = subLine.split(",");
                    
                    if(codes.length == 0) continue; //Não tem nada!
                    else{
                        for(String code : codes){
                            //A estrurua é a seguinte: ...,999;999,100;1,... a separação de ; é codigo;quantidade
                            String[] codigoQuantidade = code.split(";");
                            if(codigoQuantidade.length != 0){
                                Item item = busca(Integer.parseInt(codigoQuantidade[0]), itens); //Pega o item pelo código
                                int quantidade = Integer.parseInt(codigoQuantidade[1]); //Pega a quantidade
                                //Vamos adicionar o itemCarrinho = item + quantidade
                                pedidoTemp.addItem(item,quantidade); //Adiciona na lista
                            } 
                        }
                    }
                    pedidos.add(pedidoTemp);//Adiciona o pedido a lista de pedidos
                    //Adiciona o pedido ao restaurante e cliente correspondente
                    res.getPedidos().add(pedidoTemp);
                    cli.getPedidos().add(pedidoTemp);     
                }    
            }
            bfread.close();//Fecha o arquivo
        }catch(FileNotFoundException e){ //se nao houver arquivo
            File out = new File(pedidosPath);
            try{
               out.createNewFile();
            }catch(IOException f){
                System.out.println("Não foi possivel criar arquivo " + f.getMessage());
            } 
        }catch(IOException e){
            System.out.println("Erro ao abrir arquivo " + e.getMessage());
        }
    }
    
    /**
     * Metodo para pegar o ultimo codigo dos Pedidos
     *  @return ultimo codigo Pedido
     */
    public int getLastCodigoPedido(){
       return this.pedidos.size();
    }

    /**
     * Metodo para pegar o ultimo codigo dos Itens
     * @return Ultimo codigo dos Itens
     */
    public int getLastCodigoItem(){
       return this.itens.size();
    }

    /**
     * Metodo para pegar o ultimo codigo dos Restaurantes
     * @return Ultimo codigo dos Restaurantes
     */
    public int getLastCodigoRestaurante(){
       return this.restaurantes.size();
    }
    
    /**
     * Metodo para pegar o ultimo codigo dos Clientes
     * @return Ultimo codigo dos clientes
     */
    public int getLastCodigoCliente(){
        return this.clientes.size();
    }
    
    /**
     * Retorna a lista de Restaurantes
     * @return Lista de Restaurantes
     */
    public List<Restaurante> getRestaurantes(){
        return restaurantes;
    }
    
    /**
     * Metodo para buscar um usuario com o nome de usuario
     * @param nomeUsuario Nome do usuario
     * @return Objeto do Usuario
     */
    public Usuario getUsuario(String nomeUsuario){
        try{
            Usuario user;
            //Usuario é cliente?
            user = busca(nomeUsuario, clientes);
            if(user == null)
                //Usuário é restaurante?
                user = busca(nomeUsuario, restaurantes);
            //Encontrado?
            if(user != null){
                return user;
            }
        }catch(Exception ex){
            return null;
        }
        return null;
    }

    /**
     * Função de busca precisa de um item atraves de seu nome
     * @param <T> Qualquer Instancia de usuario ou item
     * @param chave Nome da Instancia
     * @param lista Lista correspondente a Instancia
     * @return Retorna a instancia procurada
     */
    public <T extends Pesquisavel> T busca(String chave, List<T> lista){
        for(int i =0;i < lista.size();i++)
            if(lista.get(i).getNome().equals(chave))
                    return lista.get(i);
        
        return null;

    }

    /**
     * Função de busca precisa de um item atraves de um codigo
     * @param <T> Qualquer Instancia de usuario ou item
     * @param chave Chave da Instancia
     * @param lista Lista correspondente a Instancia
     * @return Retorna a instancia procurada
     */
    public <T extends Pesquisavel> T busca(int chave, List<T> lista){
       
        for(int i =0;i < lista.size();i++)
            if(chave == lista.get(i).getCodigo())
                    return lista.get(i);
      
        return null;

    }


    /**
     * Função que pesquisa parte a parte por palavras numa lista
     * O filtro não é cumulativo na função, mas como retorna uma lista nova pode ser usada com outro filtro
     * @param <T> Classe da lista a ser pesquisada
     * @param classe Classe do Item para identificar, ex: Item i, Cliente c , não necessita estar instanciado
     * @param nome caracteres de busca
     * @param filtro Filtro de pesquisa -> tipo de item
     * @param lista Lista onde sera procurado a Classe
     * @return Retorna um lista filtrada
     */
    public <T extends Pesquisavel> List<T> pesquisa(T classe, String nome, String filtro, List<T> lista){ //temp
        //Normalizar o nome
        nome = nome.toLowerCase();
        if(classe instanceof Item)
            lista = lista.stream().filter(s -> s.getTipo().contains(filtro)).collect(Collectors.toList());

        //filtro
        List<T> newLista = new ArrayList<>();
        for(T item : lista){
            //Pegamos o nome real em lower case
            String nomeItem = item.getNomeReal().toLowerCase();
            if(nomeItem.toLowerCase().contains(nome)) newLista.add(item);
        }
        return newLista;
    }


    /**
     * Cadastra uma comida, somente usada quando logada como um restaurante
     * @param res
     * @param nome Nome da comida
     * @param preco Preço da comida
     * @param codigo Codigo da comida
     * @param tipo Tipo da comida
     * @param imgPath Local da imagem
     * @param serveQ Quantas pessoas servem
     * @param tamanho Tamanho do alimento
     * @param isVegetariano É algo vegetariano?
     * @param isGelado É algo gelado?
     * @param isSemAcucar É algo sem açucar?
     */
    public void cadastraComida(Restaurante res,String nome, float preco, int codigo, String tipo, String imgPath, int serveQ, String tamanho,
     boolean isVegetariano,boolean isGelado,boolean isSemAcucar){
        Comida comida = new Comida(nome, preco, codigo, tipo, imgPath, serveQ, tamanho, isVegetariano, isGelado, isSemAcucar, res);

        itens.add(comida);
        res.getListaItem().add(comida);

    }

    /**
     * Cadastra um Produto, somente usada quando logada como um restaurante
     * @param res
     * @param nome Nome da Produto
     * @param preco Preço da Produto
     * @param codigo Codigo da Produto
     * @param tipo Tipo da Produto
     * @param imgPath Local da imagem
     * @param marca Marca do Produto
     */
    public void cadastraProduto(Restaurante res, String nome, float preco, int codigo, String tipo, String imgPath, String marca){
        Produto produto = new Produto(nome, preco, codigo, tipo, imgPath, marca, res);

        itens.add(produto);
        res.getListaItem().add(produto);
        
    }
       
    
    /**
     * Cadastra um Produto, somente usada quando logada como um restaurante
     * @param res
     * @param item
     */
    public void cadastraItem(Restaurante res, Item item){
        itens.add(item);
        res.getListaItem().add(item);  
    }

    /**
     * Cadastra os dados de um Restaurante e armazena em um arquivo
     * @param nomeUsuario Nome do Restaurante
     * @param senha Senha do Restaurante
     * @param codigo Codigo do Restaurante
     * @param profileImg Local Imagem do Restaurante
     * @param cnpj Cnpj do Restaurante
     * @param nome
     */
    public void cadastraRestaurante(String nomeUsuario, String senha, int codigo, String profileImg, String cnpj,String nome){
        restaurantes.add(new Restaurante(nomeUsuario, senha, codigo, cnpj,  profileImg, nome));

    }

    /**
     * Cadastra usuario atravas do objeto Usuario
     * @param user Objeto do Usuario
     */
    public void cadastraUsuario(Usuario user){
        if(user instanceof Cliente cliente)
            clientes.add(cliente);
        else
            restaurantes.add((Restaurante)user);

    }

    /**
     * Cadastra os dados de um Cliente e armazena em um arquivo
     * @param nomeUsuario Usuario do cliente
     * @param senha Senha do cliente
     * @param codigo Codigo do Cliente
     * @param cpf
     * @param profileImg Local da Imagem do cliente
     * @param nome
     */
    public void cadastraCliente(String nomeUsuario, String senha, int codigo, String cpf, String profileImg,String nome){    
        clientes.add(new Cliente(nomeUsuario, senha, codigo, cpf, profileImg, nome));

    }

    /**
     * Funçao que reazliza o pedido e salva ele depois
     * @param cli
     * @param restaurante Restaurante de onde vem o pedido
     * @param dataPedido Data do pedido
     */
    public void realizaPedido(Cliente cli, Restaurante restaurante, String dataPedido){
        Pedido novoPedido = cli.fazerPedido(this.getLastCodigoPedido()+1, restaurante, dataPedido);
        if(novoPedido != null)
            pedidos.add(novoPedido);

    }

    /**
     * Função que adiciona item ao carrinho quando usada pelocliente
     * @param cli
     * @param item Item a ser inserido
     * @param quantidade Quantos item a ser inserido
     */
    public void addItemNoCarrinho(Cliente cli, Item item, int quantidade){
        ItemCarrinho itemCarrinho = new ItemCarrinho(item,quantidade);
        itemCarrinho.addToCarrinho(cli, quantidade);
    }


    /**
     * Funçao que faz login na conta de um cliente
     * @param nomeUsuario Nome de usuario do cliente
     * @param senha Senah do Cliente
     * @return Sucesso no login
     */
    public Usuario loginUsuario(String nomeUsuario, String senha){
        Usuario user = getUsuario(nomeUsuario);
        if(user == null) return null;
        //Comparar senhas
        if(user.getSenha().equals(senha)){
            //retorna o usuario, deu certo o login
            return user;
        }
        return null; //nao funcionou

    }

    /**
     * Salva os items no banco de dados
     */
    private void salvarItemsDB(){
        BufferedWriter bfWriter = null;
        //Selecion o arquivo de items
        try{
             bfWriter = new BufferedWriter(new FileWriter(itemPath));
        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
        //salva items
        for(Item item : itens){
            try{
                if(item instanceof Comida comida){
                    bfWriter.write("Comida#"+ comida.getNome() + "#" + comida.getPreco() + "#" + comida.getCodigo() + "#" + comida.getTipo() + "#" + comida.getImgPath() + "#" +
                                    comida.getServeQuantos() + "#" + comida.getTamanho() + "#" + comida.getVegetariano() + "#" + comida.getGelado() + "#" + 
                                    comida.getSemAcucar() + "#" + comida.getRestaurante().getCodigo());
                    bfWriter.newLine(); //pula a linha atual
                }else{
                    Produto prod = (Produto) item;

                    bfWriter.write("Produto#"+ prod.getNome() + "#" + prod.getPreco() + "#" + prod.getCodigo() + "#" + prod.getTipo() + "#" + prod.getImgPath() + "#" + prod.getMarca() + "#" + prod.getRestaurante().getCodigo());
                    bfWriter.newLine(); //pula a linha atual
                }

            }catch(IOException e){
                System.out.println("Erro ao escrever arquivo" + e.getMessage());
            }
        }
        //Tenta fechar o arquivo
        try{
            bfWriter.close();
        }catch(IOException e){
            System.out.println("Erro ao fechar arquivo" + e.getMessage());
        }    
    }

    /**
     * Salva os Usuarios no banco de dados
     */
    private void salvarUsuariosDB(){
        BufferedWriter bfWriter = null;
        //Selecion o arquivo de usuarios
        try{
            bfWriter = new BufferedWriter(new FileWriter(usuarioPath)); 
        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
        //salva clientes
        for(Cliente cli : clientes){
            try{
                bfWriter.write("Cliente#" +cli.getNome() + "#" + cli.getSenha() + "#" + cli.getCodigo() + "#" + cli.getDocumento() + "#" + cli.getProfilePath() + "#" + cli.getNomeReal() + "#");
                bfWriter.newLine(); //pula a linha atual
            }catch(IOException e){
                System.out.println("Erro ao escrever arquivo" + e.getMessage());
            }   
        }
        //salva restaurantes
        for(Restaurante res : restaurantes){
            try{
                bfWriter.write("Restaurante#" + res.getNome()+ "#" + res.getSenha() + "#" + res.getCodigo() + "#" + res.getDocumento() + "#" + res.getProfilePath() + "#" + res.getNomeReal() + "#");
                bfWriter.newLine(); //pula a linha atual
            }catch(IOException e){
                System.out.println("Erro ao escrever arquivo" + e.getMessage());
            }
        }
        
        //Tenta fechar o arquivo
        try{
            bfWriter.close();
        }catch(IOException e){
            System.out.println("Erro ao fechar arquivo" + e.getMessage());
        } 
    }

    /**
     * Salva os pedidos no banco de dados
     */
    private void salvarPedidosDB(){
        BufferedWriter bfWriter = null;
        //Selecion o arquivo de pedidos
        try{
            bfWriter = new BufferedWriter(new FileWriter(pedidosPath));
        }
        catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
        //salva pedidos
        for(Pedido pedido : pedidos){
            try{     
                bfWriter.write(pedido.getCodigo()+ "#");
                if(pedido.getRestaurante() == null) 
                    bfWriter.write("#");
                else
                    bfWriter.write(pedido.getRestaurante().getCodigo() + "#");
                if(pedido.getCliente() == null) 
                    bfWriter.write("#");
                else
                    bfWriter.write(pedido.getCliente().getCodigo() + "#");
                if(pedido.getDataPedido() == null) 
                    bfWriter.write("#");
                else{
                    String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pedido.getDataPedido());
                    bfWriter.write(data + "#");
                }
                    
                
                bfWriter.write(pedido.getStatus() + "#");

                //Escreve cada item separado por comma (,) e a quantidade separada por ;
                for(ItemCarrinho itemCarrinho : pedido.getListaItemCarrinho())
                    bfWriter.write(itemCarrinho.getItem().getCodigo() + ";" +itemCarrinho.getQuantidade() + ","); //escreve os codigos e a quantidade dos itens  
   
                bfWriter.newLine(); //pula a linha atual
            }catch(IOException e){
                System.out.println("Erro ao escrever arquivo" + e.getMessage());
            }
        } 
        //Tenta fechar o arquivo
        try{
            bfWriter.close();
        }catch(IOException e){
            System.out.println("Erro ao fechar arquivo" + e.getMessage());
        }
    }

    /**
     * Fecha o programa e chama as funçoes de salvamento
     */
    public void fechaPrograma(){
        salvarItemsDB();
        salvarUsuariosDB();
        salvarPedidosDB();
    }
      
}
