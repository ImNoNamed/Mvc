package model;

import java.util.HashMap;

public class Pokedex {
    private final HashMap<String, Pokemon> pokemonesPorNombre = new HashMap<>();

    public void agregarPokemon(Pokemon p) {
        pokemonesPorNombre.put(p.getName().toLowerCase(), p);
    }

    public Pokemon buscarPorNombre(String nombre) {
        return pokemonesPorNombre.get(nombre.toLowerCase());
    }

    public boolean contiene(String nombre) {
        return pokemonesPorNombre.containsKey(nombre.toLowerCase());
    }
}