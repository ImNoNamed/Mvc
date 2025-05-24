package controller;

import model.Entrenador;
import model.Pokemon;
import model.Ataque;

import java.util.Scanner;

public class ControladorConsola {
    private Entrenador entrenador1;
    private Entrenador entrenador2;

    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del Entrenador 1: ");
        String nombre1 = scanner.nextLine();
        System.out.print("Ingrese el nombre del Entrenador 2: ");
        String nombre2 = scanner.nextLine();

        entrenador1 = new Entrenador(nombre1);
        entrenador2 = new Entrenador(nombre2);

        entrenador1.generarEquipoAleatorio();
        entrenador2.generarEquipoAleatorio();

        System.out.println("\nEquipos generados:");
        mostrarEquipo(entrenador1);
        mostrarEquipo(entrenador2);

        System.out.println("\n¡La batalla comienza!");
        iniciarBatalla(scanner);

        scanner.close();
    }

    private void mostrarEquipo(Entrenador entrenador) {
        System.out.println("Equipo de " + entrenador.getNombre() + ":");
        for (Pokemon p : entrenador.getEquipo()) {
            System.out.println("- " + p.getName() + " (HP: " + p.getHealthPoints() + ")");
        }
    }

    private void iniciarBatalla(Scanner scanner) {
        while (entrenador1.tienePokemonVivos() && entrenador2.tienePokemonVivos()) {
            Pokemon poke1 = entrenador1.obtenerPokemonActivo();
            Pokemon poke2 = entrenador2.obtenerPokemonActivo();

            System.out.println("\nTurno de batalla:");
            System.out.println(entrenador1.getNombre() + " usa a " + poke1.getName() + " (HP: " + poke1.getHealthPoints() + ")");
            System.out.println(entrenador2.getNombre() + " usa a " + poke2.getName() + " (HP: " + poke2.getHealthPoints() + ")");

            Ataque ataque1 = seleccionarAtaque(scanner, poke1);
            Ataque ataque2 = seleccionarAtaque(scanner, poke2);

            if (poke1.getSpeed() >= poke2.getSpeed()) {
                ejecutarAtaque(poke1, poke2, ataque1);
                if (poke2.getHealthPoints() > 0) {
                    ejecutarAtaque(poke2, poke1, ataque2);
                }
            } else {
                ejecutarAtaque(poke2, poke1, ataque2);
                if (poke1.getHealthPoints() > 0) {
                    ejecutarAtaque(poke1, poke2, ataque1);
                }
            }

            entrenador1.eliminarPokemonActivo();
            entrenador2.eliminarPokemonActivo();

            if (!entrenador1.tienePokemonVivos() || !entrenador2.tienePokemonVivos()) {
                break;
            }
        }

        if (entrenador1.tienePokemonVivos()) {
            System.out.println("\n¡" + entrenador1.getNombre() + " gana la batalla!");
        } else {
            System.out.println("\n¡" + entrenador2.getNombre() + " gana la batalla!");
        }
    }

    private Ataque seleccionarAtaque(Scanner scanner, Pokemon pokemon) {
        System.out.println("\nAtaques disponibles para " + pokemon.getName() + ":");
        for (int i = 0; i < pokemon.getAttacks().size(); i++) {
            Ataque ataque = pokemon.getAttacks().get(i);
            System.out.println((i + 1) + ". " + ataque.getdamagename() + " (Daño: " + ataque.getdamagepotency() + ")");
        }
        System.out.print("Seleccione el ataque: ");
        int ataqueIndex = scanner.nextInt() - 1;
        return pokemon.getAttacks().get(ataqueIndex);
    }

    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor, Ataque ataque) {
        int hpAntes = defensor.getHealthPoints();
        ataque.applyAttack(defensor);
        int daño = Math.max(hpAntes - defensor.getHealthPoints(), 0);

        System.out.println("\n" + atacante.getName() + " usó " + ataque.getdamagename() + " contra " + defensor.getName());
        System.out.println("Daño causado: " + daño);
        System.out.println(defensor.getName() + " ahora tiene " + defensor.getHealthPoints() + " HP.");
    }
}