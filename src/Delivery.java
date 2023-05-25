import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe principal do software, é nele que ocorre o processamento de tudo
 */
public class Delivery {
    private List<Restaurante> restaurantes = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Item> itens = new ArrayList<>();
    private Usuario contaLogada;

    public Delivery(){ //inicializa dados
        BufferedReader bfread = null;
        String line;
        String[] splitted;
        contaLogada = null; //necessita de procedimento de login atraves da iterface

        //carrega items cadastrados
        try{
            bfread = new BufferedReader(new FileReader("../items.dat"));

            while((line = bfread.readLine()) != null){
                splitted = line.split("#");

                if(splitted[0] == "Comida"){
                    itens.add(new Comida(
                        splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5],
                        Integer.parseInt(splitted[6]),splitted[7],Boolean.parseBoolean(splitted[8]),Boolean.parseBoolean(splitted[9]),Boolean.parseBoolean(splitted[10])
                    ));

                }else{
                    itens.add(new Produto(
                        splitted[1],Float.parseFloat(splitted[2]),Integer.parseInt(splitted[3]),splitted[4],splitted[5],splitted[6]
                    ));

                }
            }

            bfread.close();

        }catch(FileNotFoundException e){
            File out = new File("../items.dat");
        }catch(IOException e){
            System.out.println("Erro ao abrir arquivo " + e.getMessage());
        }

        //Carrega usuarios
        try{

            bfread = new BufferedReader(new FileReader("../usuarios.dat"));

            while((line = bfread.readLine()) != null){
                splitted = line.split("#");

                if(splitted[0] == "Cliente"){
                    clientes.add();

                }else{
                    restaurantes.add();

                }
            }

            bfread.close();

        }catch(FileNotFoundException e){
            File out = new File("../items.dat");
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

    public <T extends Pesquisavel> List<T> pesquisa(String nome, List<T> lista){ //temp
        return null;
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
        Comida comida = new Comida(nome, preco, codigo, tipo, imgPath, serveQ, tamanho, isVegetariano, isGelado, isSemAcucar);

        itens.add(comida);
        res.getListaItem().add(comida);

        //salvar no arquivo
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

        itens.add(produto);
        res.getListaItem().add(produto);

        //salvar no arquivo
    }

    public void cadastraRestaurante(){

    }

    public void cadastraCliente(){

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
