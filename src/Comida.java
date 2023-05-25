
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
     */
    public Comida(String nome, float preco, int codigo, String tipo, String imgPath, int serveQ, String tamanho, boolean isVegetariano,boolean isGelado,boolean isSemAcucar){
        super(nome, preco, codigo, tipo, imgPath);
        this.serveQuantos = serveQ;
        this.tamanho = tamanho;
        this.isVegetariano = isVegetariano;
        this.isSemAcucar = isSemAcucar;
        this.isGelado = isGelado;
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
