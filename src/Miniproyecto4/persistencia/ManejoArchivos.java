package persistencia;

import model.Pokemon;
import model.Entrenador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejoArchivos {

    public static void guardarEntrenador(Entrenador e, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println(e.getNombre());
            for (Pokemon p : e.getEquipo()) {
                writer.println(p.getName() + "," + p.getHealthPoints() + "," + p.getSpeed());
            }
        }
    }

    public static List<String> cargarArchivo(String archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }
}