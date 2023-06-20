package com.mycompany.projetopoo.interfacegrafica.Codigo;


import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os restaurantes que prestarao delivery
 */
public class Restaurante extends Usuario{
    private String cnpj;
    private final List<Item> itens = new ArrayList<>();

    /**
     * Constructor da classe Restaurante
     * @param nomeUsuario Nome do Usuario do Restaurante
     * @param senha Senha do Restaurante
     * @param codigo Codigo do Restaurante
     * @param cnpj Cnpj do Restaurante
     * @param profileImg Local da Imagem do Restaurante
     * @param nome Nome do Retaurante
     */
    public Restaurante(String nomeUsuario, String senha, int codigo, String cnpj, String profileImg,String nome){
        super(nomeUsuario, senha, codigo, profileImg,nome);
        this.cnpj = cnpj;
        
    }

    @Override
    public void setDocumento(String documento){
        this.cnpj = documento;
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
