package com.mycompany.projetopoo.interfacegrafica.Codigo;

public abstract class Pesquisavel {
    /**
     * Retorna o nome da instacia para busca
     * @return Codigo da Instancia
     */
    public int getCodigo(){
        return -1;
    }

    /**
     * Retorna um nome qualquer para busca
     * @return Nome da Instancia
     */
    public String getNome(){
        return null;
    }
    /**
     * Retorna o nome de exibição
     * @return 
     */
    public String getNomeReal(){
        return null;
    }

    /**
     * Retorna se o tipo da bebida
     * @return Tipo da bebida
     */
    public String getTipo(){
        return null;
    }

}
