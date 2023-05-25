import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os usuarios no geral
 */
public abstract class Usuario extends Pesquisavel {
    private String nomeUsuario;
    private String senha;
    private int codigoUsuario;
    private String profileImg;
    private int[] numeroDosPedidos;
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Constructor da classe Usuario
     * @param nomeUsuario Nome de Usuario
     * @param senha Senha do Usuario 
     * @param codigo Codigo do Usuario
     * @param profileImg Local da imagem do usuario
     */
    public Usuario(String nomeUsuario, String senha, int codigo, String profileImg){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.codigoUsuario = codigo;
        this.profileImg = profileImg;
    }

    @Override
    public String getNome(){
        return nomeUsuario;
    }

    /**
     * Retorna a senha do usuario
     * @return Senha do usuario
     */
    public String getSenha(){
        return senha;
    }

    @Override
    public int getCodigo(){
        return codigoUsuario;
    }

    /**
     * Retorna o documento do usuario
     * @return Documento do usuario
     */
    public abstract String getDocumento();

    /**
     * Retorna o local da imagem do usuario
     * @return Local da imagem
     */
    public String getProfilePath(){
        return profileImg;
    }

    /**
     * Define o local da image do usuario
     * @param profilePath Novo Local da imagem
     */
    public void setProfilePath(String profilePath){
        this.profileImg = profilePath;
    }

    /**
     * Retorna um array com os codigos dos pedidos para reinserçao
     * @return Array de Codigos
     */
    public int[] getNumeroDosPedidos(){
        return numeroDosPedidos;
    }

    /**
     * Retorna a lista de pedidos do Usuario
     * @return Lista de Pedidos
     */
    public List<Pedido> getPedidos(){
        return pedidos;
    }
}
