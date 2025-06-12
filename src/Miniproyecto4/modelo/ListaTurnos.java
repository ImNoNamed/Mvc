package modelo;

import java.util.LinkedList;

public class ListaTurnos {
    private LinkedList<Pokemon> listaTurnos = new LinkedList<>();

    public void agregarPorVelocidad(Pokemon pokemon) {
        int i = 0;
        while (i < listaTurnos.size() && listaTurnos.get(i).getVelocidad() >= pokemon.getVelocidad()) {
            i++;
        }
        listaTurnos.add(i, pokemon);
    }

    public LinkedList<Pokemon> getTurnos() {
        return listaTurnos;
    }
}