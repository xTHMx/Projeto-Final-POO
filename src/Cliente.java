import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o cliente que ira usufruir do delivery
 */
public class Cliente extends Usuario {
    private String cpf;
    private List<Item> carrinho = new ArrayList<>();
    
    /**
     * Constructor da classe Cliente
     * @param nomeUsuario Usuario do cliente
     * @param senha Senha do cliente
     * @param codigo Codigo do Cliente
     * @param documento Documento do cliente
     * @param profileImg Local da Imagem do cliente
     */
    public Cliente(String nomeUsuario, String senha, int codigo, String cpf, String profileImg){
        super(nomeUsuario, senha, codigo, profileImg);
        this.cpf = cpf;
    }

    /**
     * Funçao que remove um certo item da lista do carrinho
     * @param codigo Codigo do item a ser removido
     * @return Se foi removido/encontrado ou não
     */
    public boolean removeItem(int codigo){
        int i = 0;

        while((codigo != carrinho.get(i).getCodigo()) && (i < carrinho.size()))
            i++;
        
        if(codigo == carrinho.get(i).getCodigo()){
            carrinho.remove(i);
            return true;
        }else{
            System.out.println("Item não encontrado!");
            return false;
        }
          
    }

    @Override
    public String getDocumento(){
        return cpf;
    }

    /**
     * Função que realiza o pedido dos items no carrinho
     * @param numeroPedido Numero do pedido
     * @param restaurante Nome do Restaurante
     * @param dataPedido Data do pedido
     * @return Instancia do Pedido
     */
    public Pedido fazerPedido(int numeroPedido, String restaurante, String dataPedido){
        Pedido novoPedido = new Pedido(numeroPedido, restaurante, getNome(), dataPedido);
        int i;

        for(i = 0; i < carrinho.size()-1; i++){
            novoPedido.addItem(carrinho.get(i));
        }
        carrinho.clear();
        getPedidos().add(novoPedido); //nao visivel

        return novoPedido;
    }

    /**
     * Retorna a lista de items do carrinho
     * @return Lista de items do carrinho
     */
    public List<Item> getCarrinho(){
        return carrinho;
    }

}
