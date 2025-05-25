import controller.ControladorConsola;
import controller.ControladorPokemon;
import vista.InterfazGrafica;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al juego de Pokémon.");
        System.out.println("Seleccione el modo de juego:");
        System.out.println("1. Consola");
        System.out.println("2. Interfaz gráfica");
        System.out.print("Ingrese su elección (1 o 2): ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (opcion == 1) {
            // Iniciar el modo consola
            new ControladorConsola().iniciarJuego();
        } else if (opcion == 2) {
            // Iniciar el modo interfaz gráfica
            InterfazGrafica vista = new InterfazGrafica();
            new ControladorPokemon(vista).iniciarInterfaz();
        } else {
            System.out.println("Opción no válida. Saliendo del programa.");
        }

        scanner.close();
    }
}