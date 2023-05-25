public class Sobremesa extends Item {
    private boolean isGelada;
    private boolean isSemAçucar;

    /**
     * Constructor da classe Sobremesa
     * @param nome Nome da Sobremesa
     * @param preco Preço da Sobremesa
     * @param codigo Codigo da Sobremesa
     * @param tipo Tipo da Sobremesa
     * @param imgPath Local da imagem
     * @param isGelada Sobremesa é gelada?
     * @param isSemAçucar Sobremesa é sem açucar?
     */
    public Sobremesa(String nome, float preco, int codigo, String tipo, String imgPath, boolean isGelada, boolean isSemAçucar){
        super(nome, preco, codigo, tipo, imgPath);
        this.isGelada = isGelada;
        this.isSemAçucar = isSemAçucar;
    }

    @Override
    public void addToCarrinho(Cliente cli,int quantidade){
        
    }

    
    /**
     * Retorna se a sobremesa é gelada
     * @return É gelada?
     */
    public boolean getGelada(){
        return isGelada;
    }

    /**
     * Retorna se a sobremesa é sem açucar
     * @return É Sem açucar?
     */
    public boolean getSemAçucar(){
        return isSemAçucar;
    }
}
