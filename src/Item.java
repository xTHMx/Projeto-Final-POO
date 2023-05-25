import java.util.List;

/**
 * Classe abstrata que representa os items
 */
public abstract class Item extends Pesquisavel implements Compravel {
    private String nome;
    private float preco;
    private int codigo;
    private String tipo; //subclasse das deviraçoes -> bebida: vinho, cerveja; comida: japonesa, pizza
    private String imgPath;

    /**
     * Construtor da classe Item
     * @param nome Nome do item
     * @param preco Preco do item
     * @param codigo Codigo do item
     * @param imgPath local da imagem
     */
    public Item(String nome, float preco, int codigo, String tipo, String imgPath){
        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.tipo = tipo;
        this.imgPath = imgPath;
    }

    @Override
    public void addToCarrinho(Cliente cli,int quantidade){
        List<Item> carrinho = cli.getCarrinho();
        carrinho.add(this);
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
     * Retorna se o tipo da bebida
     * @return Tipo da bebida
     */
    public String getTipo(){
        return tipo;
    }

    /**
     * Retorna o local da imagem do Item
     * @return local da imagem
     */
    public String getImgPath(){
        return imgPath;
    }

}
