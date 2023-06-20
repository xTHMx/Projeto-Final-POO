
package com.mycompany.projetopoo.interfacegrafica.Codigo;

import java.util.List;

/**
 *
 * @author jaoti Responsável pela interface
 */
public class ItemCarrinho implements Compravel{
    
    private final Item item;
    private int quantidade;
    
    /**
     * Constructor do Item dos Carrinhos
     * @param item Item 
     * @param quantidade Quantidade do Item
     */
    public ItemCarrinho(Item item,int quantidade){
        this.item = item;
        this.quantidade = quantidade;
    }

    /**
     * Define a quantidade 
     * @param quantidade nova quantidade de itens
     */
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    /**
     * Adiciona a o valor na quantidade do Carrinho
     * @param quantidade Quantidade a ser adicionada
     */
    public void addQuantidade(int quantidade){
        this.quantidade += quantidade;
    }

    @Override
    public void addToCarrinho(Cliente cli,int quantidade){
        List<ItemCarrinho> carrinho = cli.getCarrinho();
        //Verifica se já existe o item no carrinho para adicionar somente a quantidade
        for(ItemCarrinho itemCarrinho : carrinho){
            if(itemCarrinho.getItem().equals(this.getItem())){
                itemCarrinho.addQuantidade(quantidade); //Adiciona a quantidade
                return;
            }
        }
        //Se não encontrou o item, adiciona ele normalmente
        carrinho.add(this);
    }
    
    /**
     * Pega a quantidade de itens
     * @return Quantidade de itens
     */
    public int getQuantidade(){
        return quantidade;
    }

    /**
     * Pega o item do carrinho
     * @return Item
     */
    public Item getItem(){
        return item;
    }
}
