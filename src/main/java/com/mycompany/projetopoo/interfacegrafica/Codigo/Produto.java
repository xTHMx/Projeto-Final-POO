package com.mycompany.projetopoo.interfacegrafica.Codigo;

public class Produto extends Item {
    private String marca;

    /**
     * Contructor da classe Produto
     * @param nome Nome da Produto
     * @param preco Preço da Produto
     * @param codigo Codigo da Produto
     * @param tipo Tipo da Produto
     * @param imgPath Local da imagem
     * @param marca Marca do Produto
     * @param restaurante
     */
    public Produto(String nome, float preco, int codigo, String tipo, String imgPath, String marca, Restaurante restaurante){
        super(nome, preco, codigo, tipo, imgPath, restaurante);
        this.marca = marca;
    }

    /**
     * Constructor da classe produto usando o restaurante
     * @param res Restaurante
     */
    public Produto(Restaurante res){
        super(res);
    }
    
    /**
     * Define a marca
     * @param marca Nova marca
     */
    public void setMarca(String marca){
        this.marca = marca;
    }

    /**
     * Retorna a marca do produto
     * @return Marca do produto
     */
    public String getMarca(){
        return marca;
    }

}
