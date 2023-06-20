package com.mycompany.projetopoo.interfacegrafica.Codigo;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que mantem os dados de todos os pedidos, armazena em ambos os lados
 */
public class Pedido extends Pesquisavel{
    private List<ItemCarrinho> itens = new ArrayList<>();
    private int numeroPedido;
    private float valorTotal;
    private String status; //aguardando confirm, preparo, em rota, entregue, cancelado;
    private Restaurante restaurante;
    private Cliente cliente;
    private Date dataPedido;

    /**
     * Constructor do Carrinho
     * @param numeroPedido Numero do Pedido
     * @param restaurante Restaurante do Pedido
     * @param cliente Cliente do Pedido
     * @param dataPedido Data do Pedido
     */
    public Pedido(int numeroPedido, Restaurante restaurante, Cliente cliente, String dataPedido){
        this.numeroPedido = numeroPedido;
        this.restaurante = restaurante;
        this.cliente = cliente;
        status = "Aguardando Confirmação";
        valorTotal = 0f;
        if(dataPedido != null){
            try{
                this.dataPedido = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataPedido);
            }catch(Exception e){
                System.out.println("Erro no parse da data!");
            }
        }
    }
    
    

    /**
     * Funçao que adiciona um item e sua quantidade, e ja altera o valor total
     * @param item Item do pedido
     * @param quantidade Quantidade desse item no pedido
     */
    public void addItem(Item item, int quantidade){
        if(item == null) return;
        ItemCarrinho itemCarrinho = new ItemCarrinho(item,quantidade);
        itens.add(itemCarrinho);
        valorTotal += item.getPreco();
    }
    
    /**
     * Adiciona o item no carrinho usando o ItemCarrinho
     * @param itemCarrinho Instancia do ItemCarrinho
     */
    public void addItemCarrinho(ItemCarrinho itemCarrinho){
        if(itemCarrinho == null) return;
        itens.add(itemCarrinho);
        valorTotal += itemCarrinho.getItem().getPreco();
    }

    /**
     * Retorna o numero do pedido
     * @return Numero do Pedido
     */
    @Override
    public int getCodigo(){
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
    public Restaurante getRestaurante(){
        return restaurante;
    }

    /**
     * Retorna o nome do cliente que fez o pedido
     * @return Nome do Cliente
     */
    public Cliente getCliente(){
        return cliente;
    }

    /**
     * Retorna a instacia Date da data do pedido
     * @return Instacia Date da data
     */
    public Date getDataPedido(){
        return dataPedido;
    }

    /**
     * Retorna o status do Pedido
     * @return Status do Pedido
     */
    public String getStatus(){
        return status;
    }

    /**
     * Altera o status do Pedido
     * @param novoStatus Novo status do Pedido
     */
    public void setStatus(String novoStatus){
        this.status = novoStatus;
    }

    /**
     * Função que retorna a lista de itens do pedido
     * @return Lista de itens
     */
    public List<ItemCarrinho> getListaItemCarrinho(){
        return itens;
    }

    /**
     * Substitui uma lista na variavel da lista de itens
     * @param listaItem Lista de itens
     */
    public void setListaItemCarrinho(List<ItemCarrinho> listaItemCarrinho){
        if(listaItemCarrinho == null) return;
        this.itens = listaItemCarrinho;
    }


}
