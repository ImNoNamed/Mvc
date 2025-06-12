package model;

import java.util.Stack;

public class HistorialMovimientos {
    private Stack<String> historial = new Stack<>();

    public void registrarMovimiento(String movimiento) {
        historial.push(movimiento);
    }

    public String obtenerUltimoMovimiento() {
        return historial.isEmpty() ? "Sin movimientos" : historial.peek();
    }

    public Stack<String> getHistorial() {
        return historial;
    }
}