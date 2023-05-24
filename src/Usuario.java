import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os usuarios no geral
 */
public abstract class Usuario {
    private String usuario;
    private String senha;
    private String documento; //cpf/cnpj
    private String profileImg;
    private List<Pedidos> pedidos = new ArrayList<>();

    public Usuario(String usuario, String senha, String documento, String profileImg){
        this.usuario = usuario;
        this.documento = documento;
        this.senha = senha;
        this.profileImg = profileImg;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getSenha(){
        return senha;
    }

    public String getDocumento(){
        return documento;
    }
}
