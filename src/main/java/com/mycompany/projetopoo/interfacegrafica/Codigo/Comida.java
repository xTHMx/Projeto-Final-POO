package com.mycompany.projetopoo.interfacegrafica.Codigo;


/**
 * Classe que representa as diversas comidas
 */
public class Comida extends Item {
    private int serveQuantos;
    private String tamanho;
    private boolean isVegetariano;
    private boolean isGelado;
    private boolean isSemAcucar;

    /**
     * Constructor da classe comida
     * @param nome Nome da comida
     * @param preco Preço da comida
     * @param codigo Codigo da comida
     * @param tipo Tipo da comida
     * @param imgPath Local da imagem
     * @param serveQ Quantas pessoas servem
     * @param tamanho Tamanho do alimento
     * @param isVegetariano É algo vegetariano?
     * @param isGelado É algo gelado?
     * @param isSemAcucar É algo sem açucar?
     * @param restaurante
     */
    public Comida(String nome, float preco, int codigo, String tipo, String imgPath, int serveQ, String tamanho, boolean isVegetariano,boolean isGelado,boolean isSemAcucar, Restaurante restaurante){
        super(nome, preco, codigo, tipo, imgPath, restaurante);
        this.serveQuantos = serveQ;
        this.tamanho = tamanho;
        this.isVegetariano = isVegetariano;
        this.isSemAcucar = isSemAcucar;
        this.isGelado = isGelado;
    }

    /**
     * Constructor da classe Comida usando o Restaurante
     * @param res Restaurante
     */
    public Comida(Restaurante res){
        super(res);
    }
    
    /**
     * Define o numero de pessoas que a comida serve
     * @param serveQuantos Numero de pessoas
     */
    public void setServeQuantos(int serveQuantos){
        this.serveQuantos = serveQuantos;
    }

    /**
     * Define o tamanho da comida
     * @param tamanho Tamanho da Comida de pessoas
     */
    public void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }

    /**
     * Define se a comida é para vegetarianos
     * @param isVegetariano É vegetariano?
     */
    public void setVegetariano(boolean isVegetariano){
        this.isVegetariano = isVegetariano;
    }
    
    /**
     * Define se a comida é gelada
     * @param isGelado É gelada?
     */
    public void setGelado(boolean isGelado){
        this.isGelado = isGelado;
    }

    /**
     * Define se a comida é sem Açucar
     * @param isSemAcucar É sem Açucar
     */
    public void setSemAcucar(boolean isSemAcucar){
        this.isSemAcucar = isSemAcucar;
    }
    
    /**
     * Retorna o numero de pessoas que a comida serve
     * @return Numero de pessoas
     */
    public int getServeQuantos(){
        return serveQuantos;
    }

    /**
     * Retorna o tamanho da comida
     * @return Tamanho da comida
     */
    public String getTamanho(){
        return tamanho;
    }

    /**
     * Retorna se o prato é vegetariano
     * @return É vegetariano?
     */
    public boolean getVegetariano(){
        return isVegetariano;
    }

    /**
     * Retorna se o prato é algo gelado
     * @return É algo gelado?
     */
    public boolean getGelado(){
        return isGelado;
    }


    /**
     * Retorna se o prato é sem açucar
     * @return É sem açucar?
     */
    public boolean getSemAcucar(){
        return isSemAcucar;
    }
   

}
