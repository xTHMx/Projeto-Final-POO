package com.mycompany.projetopoo.interfacegrafica.Codigo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o cliente que ira usufruir do delivery
 */
public class Cliente extends Usuario {
    private String cpf;
    private final List<ItemCarrinho> carrinho = new ArrayList<>();
    
    /**
     * Constructor da classe Cliente
     * @param nomeUsuario Usuario do cliente
     * @param senha Senha do cliente
     * @param codigo Codigo do Cliente
     * @param cpf
     * @param profileImg Local da Imagem do cliente
     * @param nome
     */
    public Cliente(String nomeUsuario, String senha, int codigo, String cpf, String profileImg, String nome){
        super(nomeUsuario, senha, codigo, profileImg, nome);
        this.cpf = cpf;
    }

    /**
     * Funçao que remove um certo item da lista do carrinho
     * @param codigo Codigo do item a ser removido
     * @return Se foi removido/encontrado ou não
     */
    public boolean removeItem(int codigo){
        int i = 0;

        while((codigo != carrinho.get(i).getItem().getCodigo()) && (i < carrinho.size()))
            i++;
        
        if(codigo == carrinho.get(i).getItem().getCodigo()){
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

    @Override
    public void setDocumento(String documento){
        this.cpf = documento;
    }

    /**
     * Função que realiza o pedido dos items no carrinho
     * @param numeroPedido Numero do pedido
     * @param restaurante Nome do Restaurante
     * @param dataPedido Data do pedido
     * @return Instancia do Pedido
     */
    public Pedido fazerPedido(int numeroPedido, Restaurante restaurante, String dataPedido){
        Pedido novoPedido = new Pedido(numeroPedido, restaurante, this, dataPedido);
        if(novoPedido != null){
            //Adiciona a lista de ItemsCarrinho no pedido
            for(int i = 0; i < carrinho.size(); i++){
                novoPedido.addItemCarrinho(carrinho.get(i));
            }
            
            getPedidos().add(novoPedido); //nao visivel
            restaurante.getPedidos().add(novoPedido);
        }
        carrinho.clear();
        return novoPedido;
    }

    /**
     * Retorna a lista de items do carrinho
     * @return Lista de items do carrinho
     */
    public List<ItemCarrinho> getCarrinho(){
        return carrinho;
    }
    
    /**
     * Retorna o valor total do carrinho
     */
    public float getValorTotalCarrinho(){
        float valorTotal = 0f;
        for(var item: carrinho){
            valorTotal += item.getItem().getPreco() * item.getQuantidade(); 
        }
        return valorTotal;
    }
    
    /**
     * Retorna a quantidade de itens do carrinho
     * @return Quantidade
     */
    public int getQuantidadeItemsCarrinho(){
        int quantidade = 0;
        for(ItemCarrinho itemCarrinho : carrinho){
            quantidade += itemCarrinho.getQuantidade();
        }
        return quantidade;
    }

}
