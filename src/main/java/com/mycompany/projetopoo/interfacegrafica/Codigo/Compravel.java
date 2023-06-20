package com.mycompany.projetopoo.interfacegrafica.Codigo;



/**
 * Inteface de todos os itens compraveis
 */
public interface Compravel {

    /**
     * Função que adiciona o proprio item na lista de um cliente
     * @param cli Cliente que vai comprar o item
     * @param quantidade Quantidade daquele item
     */
    public void addToCarrinho(Cliente cli,int quantidade);

}
