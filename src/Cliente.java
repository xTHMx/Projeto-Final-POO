import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o cliente que ira usufruir do delivery
 */
public class Cliente extends Usuario {
    private List<Item> carrinho = new ArrayList<>();
    
    /**
     * Constructor da classe Cliente
     * @param usuario Usuario do cliente
     * @param senha Senha do cliente
     * @param documento Documento do cliente
     * @param profileImg Local da Imagem do cliente
     */
    public Cliente(String usuario, String senha, String documento, String profileImg){
        super(usuario, senha, documento, profileImg);
    }

    public Pedidos fazerPedido(int numeroPedido, String restaurante, String dataPedido){
        Pedidos novoPedido = new Pedidos(numeroPedido, restaurante, getUsuario(), dataPedido);
        int i;

        for(i = 0; i < carrinho.size()-1; i++){
            novoPedido.addItem(carrinho.get(i));
        }
        carrinho.clear();

        return novoPedido;
    }

}
