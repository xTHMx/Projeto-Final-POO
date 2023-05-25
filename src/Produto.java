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
     */
    public Produto(String nome, float preco, int codigo, String tipo, String imgPath, String marca){
        super(nome, preco, codigo, tipo, imgPath);
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
