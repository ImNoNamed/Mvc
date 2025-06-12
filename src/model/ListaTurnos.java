package model;

import java.util.LinkedList;

public class ListaTurnos {
    private final LinkedList<Pokemon> listaTurnos = new LinkedList<>();

    public void agregarPorVelocidad(Pokemon pokemon) {
        int i = 0;
        while (i < listaTurnos.size() && listaTurnos.get(i).getSpeed() >= pokemon.getSpeed()) {
            i++;
        }
        listaTurnos.add(i, pokemon);
    }

    public LinkedList<Pokemon> getTurnos() {
        return listaTurnos;
    }
}