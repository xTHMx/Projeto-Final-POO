import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que mantem os dados de todos os pedidos, armazena em ambos os lados
 */
public class Pedidos {
    private List<Item> itens = new ArrayList<>();
    private int numeroPedido;
    private float valorTotal;
    private String restaurante;
    private String cliente;
    private Date dataPedido;

    public Pedidos(int numeroPedido, String restaurante, String cliente, String dataPedido){
        this.numeroPedido = numeroPedido;
        this.restaurante = restaurante;
        this.cliente = cliente;
        valorTotal = 0f;
        
        String[] data = dataPedido.split("/");
        this.dataPedido = new Date(Integer.parseInt(data[2]),Integer.parseInt(data[1]),Integer.parseInt(data[0]));
    }

    /**
     * Funçao que adiciona um item e sua quantidade, e ja altera o valor total
     * @param item Item do pedido
     * @param quantidade Quantidade desse item no pedido
     */
    public void addItem(Item item){
        itens.add(item);
        valorTotal += item.getPreco();
    }

    /**
     * Retorna o numero do pedido
     * @return Numero do Pedido
     */
    public int getNumeroPedido(){
        return numeroPedido;
    }

    /**
     * Retorna o valor total do pedido
     * @return Valor Total
     */
    public float getValorTotal(){
        return valorTotal;
    }

    /**
     * Retorna o nome do restaurante que realizou o pedido
     * @return Nome do Restaurante
     */
    public String getRestaurante(){
        return restaurante;
    }

    /**
     * Retorna o nome do cliente que fez o pedido
     * @return Nome do Cliente
     */
    public String getCliente(){
        return cliente;
    }

    /**
     * Retorna a instacia Date da data do pedido
     * @return Instacia Date da data
     */
    public Date getDataPedido(){
        return dataPedido;
    }


}
