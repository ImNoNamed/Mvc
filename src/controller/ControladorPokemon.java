package controller;

import model.*;
import vista.InterfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPokemon {
    private final InterfazGrafica vista;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon poke1;
    private Pokemon poke2;

    public ControladorPokemon(InterfazGrafica vista) {
        this.vista = vista;
    }

    public void iniciarInterfaz() {
        configurarBotonInicio();
    }

    private void configurarBotonInicio() {
        vista.agregarListenerIniciar(e -> {
            vista.mostrarVentanaEntrenadores(event -> {
                String nombre1 = vista.getNombreEntrenador1();
                String nombre2 = vista.getNombreEntrenador2();

                entrenador1 = new Entrenador(nombre1);
                entrenador2 = new Entrenador(nombre2);

                entrenador1.generarEquipoAleatorio();
                entrenador2.generarEquipoAleatorio();

                iniciarBatallaGrafica();
            });
        });
    }

    private void iniciarBatallaGrafica() {
        poke1 = entrenador1.obtenerPokemonActivo();
        poke2 = entrenador2.obtenerPokemonActivo();

        vista.mostrarVentanaBatalla(poke1, poke2, poke1.getAttacks(), poke2.getAttacks(), this::realizarTurno);
    }

    private void realizarTurno(ActionEvent e) {
        String ataque1Seleccionado = vista.getAtaqueSeleccionado1();
        String ataque2Seleccionado = vista.getAtaqueSeleccionado2();

        Ataque ataque1 = poke1.getAttacks().stream()
                .filter(a -> a.getdamagename().equals(ataque1Seleccionado))
                .findFirst().orElse(null);

        Ataque ataque2 = poke2.getAttacks().stream()
                .filter(a -> a.getdamagename().equals(ataque2Seleccionado))
                .findFirst().orElse(null);

        if (ataque1 != null && ataque2 != null) {
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

            vista.actualizarHP(poke1, poke2);

            if (poke1.getHealthPoints() <= 0 || poke2.getHealthPoints() <= 0) {
                manejarPokemonDerrotado();
            }
        }
    }

    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor, Ataque ataque) {
        int hpAntes = defensor.getHealthPoints();
        ataque.applyAttack(defensor);
        int daño = Math.max(hpAntes - defensor.getHealthPoints(), 0);

        vista.mostrarMensajeBatalla(atacante.getName() + " usó " + ataque.getdamagename() + " contra " + defensor.getName() + ". Hizo " + daño + " de daño.");
    }

    private void manejarPokemonDerrotado() {
        if (poke1.getHealthPoints() <= 0) {
            entrenador1.eliminarPokemonActivo();
            if (entrenador1.tienePokemonVivos()) {
                poke1 = entrenador1.obtenerPokemonActivo();
                vista.mostrarMensajeBatalla(entrenador1.getNombre() + " envía a " + poke1.getName() + " al combate.");
            } else {
                vista.desactivarBotonTurno();
                vista.mostrarMensajeBatalla("¡" + entrenador2.getNombre() + " gana la batalla!");
            }
        }

        if (poke2.getHealthPoints() <= 0) {
            entrenador2.eliminarPokemonActivo();
            if (entrenador2.tienePokemonVivos()) {
                poke2 = entrenador2.obtenerPokemonActivo();
                vista.mostrarMensajeBatalla(entrenador2.getNombre() + " envía a " + poke2.getName() + " al combate.");
            } else {
                vista.desactivarBotonTurno();
                vista.mostrarMensajeBatalla("¡" + entrenador1.getNombre() + " gana la batalla!");
            }
        }
    }
}