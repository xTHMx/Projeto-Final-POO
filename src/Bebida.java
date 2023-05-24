/**
 * Classe que representa as variadas bebidas
 */
public class Bebida extends Item {
    private boolean isAlcoolico;
    private boolean isSemAçucar;
    private int volume;

    /**
     * Constructor da classe bebidas
     * @param nome Nome da Bebida
     * @param preco Preço da Bebida
     * @param codigo Código da Bebida
     * @param tipo Tipo da Bebida
     * @param imgPath local da imagem
     * @param isAlcoolico Bebida é alcoolica
     * @param isSemAçucar Bebida é sem açucar
     * @param volume Volume do recipiente da bebida
     */
    public Bebida(String nome, float preco, int codigo, String tipo, String imgPath, boolean isAlcoolico, boolean isSemAçucar, int volume){
        super(nome, preco, codigo, tipo, imgPath);
        this.isAlcoolico = isAlcoolico;
        this.isSemAçucar = isSemAçucar;
        this.volume = volume;
    }

    /**
     * Retorna se a bebida é alcoolica
     * @return Bebida é alcoolica?
     */
    public boolean getAlcoolico(){
        return isAlcoolico;
    }

    /**
     * Retorna se a bebida é sem açucar
     * @return Bebida sem açucar?
     */
    public boolean getSemAçucar(){
        return isSemAçucar;
    }

    /**
     * Retorna o volume do recipiente da bebida
     * @return Volume do recipiente da bebida
     */
    public int getVolume(){
        return volume;
    }


}
