import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe principal do software, é nele que ocorre o processamento de tudo
 */
public class Delivery {
    private List<Restaurante> restaurantes = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Item> itens = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private Usuario contaLogada;
    private String itemPath;
    private String usuarioPath;
    private String pedidosPath;

    /**
     * Inicializa os dados do Delivery
     */
    public Delivery(String itemPath, String usuarioPath, String pedidosPath){ //inicializa dados
        BufferedReader bfread = null;
        Scanner scan = null;
        int i;
        String line;
        String[] splitted;

        contaLogada = null; //necessita de procedimento de login atraves da iterface
        this.itemPath = itemPath;
        this.usuarioPath = usuarioPath;

        //carrega items cadastrados
        try{
            bfread = new BufferedReader(new FileReader(itemPath));

            while((line = bfread.readLine()) != null){
                splitted = line.split("#");

                if(splitted[0] == "Comida"){ //qual o tipo de item
                    itens.add(new Comida(
                        splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5], //valores separados por #
                        Integer.parseInt(splitted[6]),splitted[7],Boolean.parseBoolean(splitted[8]),Boolean.parseBoolean(splitted[9]),Boolean.parseBoolean(splitted[10])
                    ));

                }else{
                    itens.add(new Produto(
                        splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5],splitted[6]
                    ));

                }
            }

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

        //carrega pedidos
        try{
            bfread = new BufferedReader(new FileReader(pedidosPath));

            while((line = bfread.readLine()) != null){
                splitted = line.split("#"); 

                String[] codes;
                String subLine;
                List<Item> pedidosItens = new ArrayList<>();
                Pedido pedidoTemp;

                scan = new Scanner(splitted[0]);
                subLine = scan.nextLine();
                codes = subLine.split(",");

                for(i = 0; i < codes.length -1; i++){
                    pedidosItens.add(busca(codes[0], itens));
                }

                pedidoTemp = new Pedido(Integer.parseInt(splitted[1]),splitted[2],splitted[3],splitted[4]);
                pedidoTemp.setListaItem(pedidosItens);
                
                pedidos.add(pedidoTemp);


            }

            bfread.close();

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

        //Carrega usuarios
        try{

            bfread = new BufferedReader(new FileReader(usuarioPath)); 

            while((line = bfread.readLine()) != null){ 
                splitted = line.split("#");  
 
                if(splitted[0] == "Cliente"){ //qual o tipo de usuario
                    Cliente cli = new Cliente(splitted[1], splitted[2], Integer.parseInt(splitted[3]), splitted[4], splitted[5]);
                    clientes.add(cli);
                    
                    //readiciona pedidos

                }else{
                    Restaurante res = new Restaurante(splitted[1], splitted[2], Integer.parseInt(splitted[3]), splitted[4], splitted[5]);
                    restaurantes.add(res);

                    //readiciona pedidos

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
     * Função de busca precisa de um item atraves de seu nome
     * @param <T> Qualquer Instancia de usuario ou item
     * @param chave Nome da Instancia
     * @param lista Lista correspondente a Instancia
     * @return Retorna a instancia procurada
     */
    public <T extends Pesquisavel> T busca(String chave, List<T> lista){
        int i = 0;

        while(!chave.equals(lista.get(i).getNome()) && i < lista.size())
            i++;

        if(chave.equals(lista.get(i).getNome())){
            return lista.get(i);
        }
        
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
        int i = 0;

        while(chave != lista.get(i).getCodigo() && i < lista.size())
            i++;

        if(chave == lista.get(i).getCodigo()){
            return lista.get(i);
        }
        
        return null;

    }


    /**
     * Função que pesquisa parte a parte por palavras numa lista
     * O filtro não é cumulativo na função, mas como retorna uma lista nova pode ser usada com outro filtro
     * @param <T> Classe da lista a ser pesquisada
     * @param classe Classe do Item para identificar, ex: Item i, Cliente c , não necessita estar instanciado
     * @param chars caracteres de busca
     * @param filtro Filtro de pesquisa -> tipo de item
     * @param lista Lista onde sera procurado a Classe
     * @return Retorna um lista filtrada
     */
    public <T extends Pesquisavel> List<T> pesquisa(T classe, String chars, String filtro, List<T> lista){ //temp

        if(classe instanceof Item)
            lista = lista.stream().filter(s -> s.getTipo().contains(filtro)).collect(Collectors.toList());

                                //filtro
        lista = lista.stream().filter(s -> s.getNome().contains(chars)).collect(Collectors.toList());

        return lista;
    }


    /**
     * Cadastra uma comida, somente usada quando logada como um restaurante
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
    public void cadastraComida(String nome, float preco, int codigo, String tipo, String imgPath, int serveQ, String tamanho,
     boolean isVegetariano,boolean isGelado,boolean isSemAcucar){
        Restaurante res = (Restaurante) contaLogada;
        BufferedWriter bfWriter = null;
        Comida comida = new Comida(nome, preco, codigo, tipo, imgPath, serveQ, tamanho, isVegetariano, isGelado, isSemAcucar);

        itens.add(comida);
        res.getListaItem().add(comida);
        res.addCodigoItem(codigo);

        //salvar no arquivo
        try{
            bfWriter = new BufferedWriter(new FileWriter(itemPath,true)); //true é para append
            
            bfWriter.write("Produto#"+ nome + "#" + preco + "#" + codigo + "#" + tipo + "#" + imgPath + "#" +
                                             serveQ + "#" + tamanho + "#" + isVegetariano + "#" + isGelado + "#" + isSemAcucar);
            bfWriter.newLine(); //pula a linha atual
            bfWriter.close();

        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
    }

    /**
     * Cadastra um Produto, somente usada quando logada como um restaurante
     * @param nome Nome da Produto
     * @param preco Preço da Produto
     * @param codigo Codigo da Produto
     * @param tipo Tipo da Produto
     * @param imgPath Local da imagem
     * @param marca Marca do Produto
     */
    public void cadastraProduto(String nome, float preco, int codigo, String tipo, String imgPath, String marca){
        Restaurante res = (Restaurante) contaLogada;
        Produto produto = new Produto(nome, preco, codigo, tipo, imgPath, marca);
        BufferedWriter bfWriter = null;

        itens.add(produto);
        res.getListaItem().add(produto);
        res.addCodigoItem(codigo);

        //salvar no arquivo
        try{
            bfWriter = new BufferedWriter(new FileWriter(itemPath,true)); //true é para append
            
            bfWriter.write("Produto#"+ nome + "#" + preco + "#" + codigo + "#" + tipo + "#" + imgPath + "#" + marca);
            bfWriter.newLine(); //pula a linha atual
            bfWriter.close();

        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
        
        
    }

    /**
     * Cadastra os dados de um Restaurante e armazena em um arquivo
     * @param nomeUsuario
     * @param senha
     * @param codigo
     * @param profileImg
     * @param cnpj
     */
    public void cadastraRestaurante(String nomeUsuario, String senha, int codigo, String profileImg, String cnpj){
        BufferedWriter bfWriter = null;

        restaurantes.add(new Restaurante(nomeUsuario, senha, codigo, profileImg, cnpj));

        //adiciona no arquivo
        try{
            bfWriter = new BufferedWriter(new FileWriter(usuarioPath,true)); //true é para append
            
            bfWriter.write("Produto#"+ nomeUsuario + "#" + senha + "#" + codigo + "#" + cnpj + "#" + profileImg);
            bfWriter.newLine(); //pula a linha atual
            bfWriter.close();

        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }
    }

    /**
     * Cadastra os dados de um Cliente e armazena em um arquivo
     * @param nomeUsuario Usuario do cliente
     * @param senha Senha do cliente
     * @param codigo Codigo do Cliente
     * @param documento Documento do cliente
     * @param profileImg Local da Imagem do cliente
     */
    public void cadastraCliente(String nomeUsuario, String senha, int codigo, String cpf, String profileImg){
        BufferedWriter bfWriter = null;

        try{
            bfWriter = new BufferedWriter(new FileWriter(usuarioPath,true)); //true é para append
            
            bfWriter.write("Produto#"+ nomeUsuario + "#" + senha + "#" + codigo + "#" + cpf + "#" + profileImg);
            bfWriter.newLine(); //pula a linha atual
            bfWriter.close();

        }catch(IOException e){
            System.out.println("Erro ao escrever arquivo" + e.getMessage());
        }     
    }

    /**
     * 
     * @param numeroPedido
     * @param restaurante
     * @param dataPedido
     */
    public void realizaPedido(int numeroPedido, String restaurante, String dataPedido){
        Cliente cli = (Cliente) contaLogada; //retorna á especialização
        cli.fazerPedido(numeroPedido, restaurante, dataPedido);

    }

    /**
     * 
     * @param item
     * @param quantidade
     */
    public void addItemNoCarrinho(Item item, int quantidade){
        Cliente cli = (Cliente) contaLogada;
        item.addToCarrinho(cli, quantidade);
    }


    /**
     * Funçao que faz login na conta de um cliente
     * @param nomeUsuario Nome de usuario do cliente
     * @param senha Senah do Cliente
     * @return Sucesso no login
     */
    public boolean loginCliente(String nomeUsuario, String senha){
        Cliente cli;
        cli = busca(nomeUsuario, clientes);

        if(cli != null){
            if(cli.getSenha() == senha){
                contaLogada = cli;
                //aqui ----- abrir opçoes de cliente
                return true;
            }
        }

        return false; //nao funcionou

    }

    /**
     * Funçao que faz login na conta de um restaurante
     * @param nomeUsuario Nome de usuario do restaurante
     * @param senha Senah do restaurante
     * @return Sucesso no login
     */
    public boolean loginRestaurante(String nomeUsuario, String senha){
        Restaurante res;
        res = busca(nomeUsuario, restaurantes);

        if(res != null){
            if(res.getSenha() == senha){
                contaLogada = res;
                //aqui ----- abrir opçoes de restaurante
                return true;
            }
        }

        return false; //nao funcionou
    }

}
