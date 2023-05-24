import javax.imageio.ImageIO;

/**
 * Classe abstrata que representa os items
 */
public abstract class Item {
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

    /**
     * Altera o preco do item
     * @param preco Novo preço do item
     */
    public void setPreco(float preco){
        this.preco = preco;
    }

    /**
     * Funçao que retorna o nome do item
     * @return Nome do item
     */
    public String getNome(){
        return nome;
    }

    /**
     * Funçao que retorna o preco do item
     * @return Preco do item
     */
    public float getPreco(){
        return preco;
    }

    /**
     * Funçao que retorna o codigo do item
     * @return Codigo do item
     */
    public int getCodigo(){
        return codigo;
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

    /**
     * Retorna a quantidade daquele item no pedido
     * @return Quantidade daquele item no pedido
     */
    public int getQuantidade(){
        return quantidade;
    }

    /**
     * Define a quantidade daquele item para usar no pedido
     * @param quantidade Quantidade do item no pedido
     */
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

}
