package com.mycompany.projetopoo.interfacegrafica.Codigo;

import com.mycompany.projetopoo.interfacegrafica.JFrameLogin;
import java.io.File;

/**
 * Classe abstrata que representa os items
 */
public abstract class Item extends Pesquisavel{
    private String nome;
    private float preco;
    private int codigo;
    private String tipo; //subclasse das deviraçoes -> bebida: vinho, cerveja; comida: japonesa, pizza
    private String imgPath;
    private final Restaurante restaurante;
    
    /**
     * Construtor da classe Item
     * @param nome Nome do item
     * @param preco Preco do item
     * @param codigo Codigo do item
     * @param tipo
     * @param imgPath local da imagem
     * @param restaurante
     */
    public Item(String nome, float preco, int codigo, String tipo, String imgPath, Restaurante restaurante){
        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.tipo = tipo;
        this.imgPath = imgPath;
        this.restaurante = restaurante;
    }
    
    /**
     * Constructor do item usando o Restaurante
     * @param restaurante Restaurante
     */
    public Item(Restaurante restaurante){
        this.restaurante = restaurante;
    }

    /**
     * Retorna o objeto do Restaurante
     * @return Restaurante
     */
    public Restaurante getRestaurante(){
        return this.restaurante;
    }

    /**
     * Define o codigo do Item
     * @param codigo Novo Codigo
     */
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    /**
     * Define o local da imagem do item
     * @param imgPath Local da Imagem
     */
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    /**
     * Retorna o Local da imagem do item
     * @return Local da Imagem
     */
    public String getImgPath(){
        return imgPath;
    }

    /**
     * Retorna o local encontrado da Imagem
     * @return Local da imagem
     */
    public String getResolvedImgPath(){
        if(getImgPath().isBlank()) return "";
        else//Resolver o Path relativo
            return new File(JFrameLogin.jarFilePath).toPath().resolve(new File(getImgPath()).toPath()).toString();
    }

    /**
     * Define o local da image do usuario
     * @param absolutePath
     */
    public void setRelativeImgPath(String absolutePath){
        this.imgPath = new File(JFrameLogin.jarFilePath).toPath().relativize(new File(absolutePath).toPath()).toString();
    }
    
    @Override
    public int getCodigo(){
        return codigo;
    }

    @Override
    public String getNome(){
        return nome;
    }

    /**
     * Altera o preco do item
     * @param preco Novo preço do item
     */
    public void setPreco(float preco){
        this.preco = preco;
    }

    /**
     * Funçao que retorna o preco do item
     * @return Preco do item
     */
    public float getPreco(){
        return preco;
    }
     /**
     * Funçao que altera o nome do item
     * @param nome Novo nome do item
     */
    public void setNome(String nome){
        this.nome = nome;
    }
    /**
     * Funçao que altera o tipo do item
     * @param tipo Novo tipo do item
     */
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    @Override
    public String getTipo(){
        return tipo;
    }

   
    //Adicionar o item ao carrinho de um cliente
    public void addToCarrinho(Cliente cli,int quantidade){
        ItemCarrinho itemCarrinho = new ItemCarrinho(this,quantidade);
        itemCarrinho.addToCarrinho(cli, quantidade);
    }

}
