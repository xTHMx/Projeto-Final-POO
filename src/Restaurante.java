
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os restaurantes que prestarao delivery
 */
public class Restaurante extends Usuario{
    private String cnpj;
    private List<Item> itens = new ArrayList<>();
    private int[] codigoItemsCadastrados;

    public Restaurante(String nomeUsuario, String senha, int codigo, String profileImg, String cnpj){
        super(nomeUsuario, senha, codigo, profileImg);
        this.cnpj = cnpj;
        
    }

    @Override
    public String getDocumento(){
        return cnpj;
    }

    /**
     * Altera o estado de um pedido
     * @param pedido
     * @param novoStatus
     */
    public void alteraStatusPedido(Pedido pedido, String novoStatus){
        pedido.setStatus(novoStatus);
    }

    /**
     * Retorna a lista de items cadastrados
     * @return Lista de Itens
     */
    public List<Item> getListaItem(){
        return itens;
    }
}
