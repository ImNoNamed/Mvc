package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.*;
import vista.InterfazGrafica;

public class ControladorPokemon implements ActionListener {
    private final InterfazGrafica vista;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon poke1;
    private Pokemon poke2;
    private int ronda = 1;

    public ControladorPokemon(InterfazGrafica vista) {
        this.vista = vista;
        configurarBotonInicio();
    }

    private void configurarBotonInicio() {
        vista.agregarListenerIniciar(e -> {
            vista.mostrarVentanaEntrenadores(e2 -> {
                String nombre1 = vista.getNombreEntrenador1();
                String nombre2 = vista.getNombreEntrenador2();
                crearEntrenadores(nombre1, nombre2);
                vista.mostrarEquipos(entrenador1, entrenador2, e3 -> {
                    iniciarBatalla();
                });
            });
        });
    }

    private void iniciarBatalla() {
        poke1 = entrenador1.obtenerPokemonActivo();
        poke2 = entrenador2.obtenerPokemonActivo();
        List<Ataque> ataques1 = poke1.getAttacks();
        List<Ataque> ataques2 = poke2.getAttacks();

        vista.mostrarVentanaBatalla(entrenador1.getNombre(), entrenador2.getNombre(), poke1, poke2, ataques1, ataques2, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombreAtaque1 = vista.getAtaqueSeleccionado1();
        String nombreAtaque2 = vista.getAtaqueSeleccionado2();
        realizarTurno(nombreAtaque1, nombreAtaque2);
    }

    public void crearEntrenadores(String nombre1, String nombre2) {
        entrenador1 = new Entrenador(nombre1);
        entrenador2 = new Entrenador(nombre2);
        entrenador1.generarEquipoAleatorio();
        entrenador2.generarEquipoAleatorio();
    }

    public void realizarTurno(String nombreAtaque1, String nombreAtaque2) {
        Ataque ataque1 = buscarAtaque(poke1, nombreAtaque1);
        Ataque ataque2 = buscarAtaque(poke2, nombreAtaque2);

        vista.mostrarMensajeBatalla("\n────────── RONDA " + ronda + " ──────────\n");

        if (poke1.getSpeed() >= poke2.getSpeed()) {
            ejecutarAtaque(poke1, poke2, ataque1, entrenador2);
            if (poke2 != null && poke2.getHealthPoints() > 0) {
                ejecutarAtaque(poke2, poke1, ataque2, entrenador1);
            }
        } else {
            ejecutarAtaque(poke2, poke1, ataque2, entrenador1);
            if (poke1 != null && poke1.getHealthPoints() > 0) {
                ejecutarAtaque(poke1, poke2, ataque1, entrenador2);
            }
        }

        vista.actualizarHP(poke1, poke2);
        ronda++;
    }

    private Ataque buscarAtaque(Pokemon pokemon, String nombreAtaque) {
        for (Ataque ataque : pokemon.getAttacks()) {
            if (ataque.getdamagename().equals(nombreAtaque)) {
                return ataque;
            }
        }
        return null;
    }

    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor, Ataque ataque, Entrenador entrenadorDefensor) {
        int hpAntes = defensor.getHealthPoints();
        ataque.applyAttack(defensor);

        vista.mostrarMensajeBatalla("\n🌟 " + atacante.getName() + " usó " + ataque.getdamagename() + " contra " + defensor.getName() + "\n");
        vista.mostrarMensajeBatalla("⚔️ Daño base: " + ataque.getdamagepotency() + "\n");

        if (ataque.advantage(defensor.getType())) {
            vista.mostrarMensajeBatalla("💥 ¡Ataque con ventaja de tipo!\n");
        }

        int daño = Math.max(hpAntes - defensor.getHealthPoints(), 0);
        vista.mostrarMensajeBatalla("🛡️ " + defensor.getName() + " recibió " + daño + " de daño (HP restante: " + defensor.getHealthPoints() + ")\n");

        if (defensor.getHealthPoints() <= 0) {
            vista.mostrarMensajeBatalla("☠️ " + defensor.getName() + " ha sido derrotado.\n");

            Pokemon nuevoPokemon = entrenadorDefensor.obtenerPokemonActivo();
            if (entrenadorDefensor == entrenador2) {
                poke2 = nuevoPokemon;
                if (poke2 == null) {
                    vista.mostrarMensajeBatalla("\n🏆 ¡" + entrenador1.getNombre() + " gana la batalla!\n");
                    vista.desactivarBotonTurno();
                } else {
                    vista.mostrarMensajeBatalla("🔁 " + entrenadorDefensor.getNombre() + " envía a " + poke2.getName());
                }
            } else {
                poke1 = nuevoPokemon;
                if (poke1 == null) {
                    vista.mostrarMensajeBatalla("\n🏆 ¡" + entrenador2.getNombre() + " gana la batalla!\n");
                    vista.desactivarBotonTurno();
                } else {
                    vista.mostrarMensajeBatalla("🔁 " + entrenadorDefensor.getNombre() + " envía a " + poke1.getName());
                }
            }
        }
    }
}