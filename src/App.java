import controller.ControladorPokemon;
import javax.swing.SwingUtilities;
import vista.InterfazGrafica;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazGrafica vista = new InterfazGrafica();
            ControladorPokemon controlador = new ControladorPokemon(vista);
        });
    }
}
