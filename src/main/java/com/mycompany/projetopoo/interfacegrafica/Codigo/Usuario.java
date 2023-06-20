package com.mycompany.projetopoo.interfacegrafica.Codigo;

import com.mycompany.projetopoo.interfacegrafica.JFrameLogin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os usuarios no geral
 */
public abstract class Usuario extends Pesquisavel {
    private String nome;
    private String nomeUsuario;
    private String endereco;
    private String senha;
    private final int codigoUsuario;
    private String profileImg;
    private final List<Pedido> pedidos = new ArrayList<>();

    /**
     * Constructor da classe Usuario
     * @param nomeUsuario Nome de Usuario
     * @param senha Senha do Usuario 
     * @param codigo Codigo do Usuario
     * @param profileImg Local da imagem do usuario
     * @param nome
     */
    public Usuario(String nomeUsuario, String senha, int codigo, String profileImg,String nome){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.codigoUsuario = codigo;
        if(nome == null || nome.isBlank())
            this.nome = " ";
        else this.nome = nome;
        if(profileImg == null || profileImg.isBlank())
            this.profileImg = " ";
        else this.profileImg = profileImg;
    }

    /**
     * Define o local da imagem do usuario
     * @param path Imagem do usuario
     */
    public void setProfileImgPath(String path){
        this.profileImg = path;
    }
    
    /**
     * Define o nome do Usuario
     * @param nomeUsuario Novo nome do Usuario
     */
    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * Define o nome real do Usuario
     * @param nomeReal Novo nome real do usuario
     */
    public void setNomeReal(String nomeReal){
        this.nome = nomeReal;
    }

    /**
     * Define o endereço do Usuario
     * @param endereco Novo endereço
     */
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    /**
     * Define o documento do Usuario
     * @param documento Novo Documento
     */
    public abstract void setDocumento(String documento);
    
    /**
     * Pega o Endereço do usuario
     * @return Endereço do usuario
     */
    public String getEndereco(){
        return endereco;
    }

    @Override
    public String getNome(){
        return nomeUsuario;
    }
   
    /**
     * Pega a senha do Usuario
     * @return Senha do Usuario
     */
    public String getSenha(){
        return senha;
    }
    
    /**
     * Define a senha do Usuario
     * @param senha Nova senha do Usuario
     */
    public void setSenha(String senha){
        this.senha = senha;
    }

    @Override
    public int getCodigo(){
        return codigoUsuario;
    }

    /**
     * Pega o documento do usuario
     * @return Documento do usuario
     */
    public abstract String getDocumento();

   
    /**
     * Pega o local da imagem do usuario
     * @return Imagem do usuario
     */
    public String getProfilePath(){
        return profileImg;
    }
    
    /**
     * Pega a imagem do usuario diretamente
     * @return Imagem
     */
    public String getResolvedProfilePath(){
        if(getProfilePath().isBlank()) return "";
        else
            //Resolver o Path relativo
            return new File(JFrameLogin.jarFilePath).toPath().resolve(new File(getProfilePath()).toPath()).toString();
    }

    /**
     * Define o local da image do usuario
     * @param absolutePath
     */
    public void setRelativeProfilePath(String absolutePath){
        this.profileImg = new File(JFrameLogin.jarFilePath).toPath().relativize(new File(absolutePath).toPath()).toString();
    }

    /**
     * Pega a lista dos pedidos
     * @return Lista dos Pedidos
     */
    public List<Pedido> getPedidos(){
        return pedidos;
    }
    
    @Override
    public String getNomeReal(){
        return nome;
    }
}
