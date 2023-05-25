
public class Main {
    public static void main(String[] args) throws Exception {
        String usuariosPath = "Delivery/src/itens.dat";
        String itemPath = "Delivery/src/usuarios.dat";
        String pedidosPath = "Delivery/src/pedidos.dat";
        Delivery delivery = new Delivery(itemPath, usuariosPath, pedidosPath);
        
    }
}
