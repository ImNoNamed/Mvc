package model;

import java.util.ArrayList;
import java.util.Collections;

public class Entrenador {
    private String nombre;
    private ArrayList<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    public void agregarPokemon(Pokemon pokemon) {
        if (equipo.size() < 3) {
            equipo.add(pokemon);
        } else {
            throw new IllegalStateException("El equipo ya tiene 3 Pokémon.");
        }
    }

    public Pokemon obtenerPokemonActivo() {
        for (Pokemon p : equipo) {
            if (p.getHealthPoints() > 0) {
                return p;
            }
        }
        return null;
    }

    public void eliminarPokemonActivo() {
        equipo.removeIf(p -> p.getHealthPoints() <= 0);
    }

    public boolean tienePokemonVivos() {
        return equipo.stream().anyMatch(p -> p.getHealthPoints() > 0);
    }

    public void generarEquipoAleatorio() {
        ArrayList<Pokemon> pokemonesDisponibles = PokemonesPro.obtenerPokemonesDisponibles();
        Collections.shuffle(pokemonesDisponibles);

        while (equipo.size() < 3) {
            Pokemon pokemonAleatorio = pokemonesDisponibles.remove(0);
            asignarAtaques(pokemonAleatorio);
            equipo.add(pokemonAleatorio);
        }
    }

    private void asignarAtaques(Pokemon pokemon) {
        ArrayList<Ataque> ataquesDisponibles = AtaquesDisponibles.obtenerAtaquesDisponibles();
        Collections.shuffle(ataquesDisponibles);

        for (int i = 0; i < 4 && i < ataquesDisponibles.size(); i++) {
            Ataque original = ataquesDisponibles.get(i);
            Ataque copia = new Ataque(original.getDamageName(), original.getDamageType(), original.getDamagePotency());
            pokemon.addAttack(copia);
        }
    }
}
