package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import model.*;

public class InterfazGrafica extends JFrame {
    private JTextField tfEntrenador1, tfEntrenador2;
    private JComboBox<String> comboAtaques1, comboAtaques2;
    private JTextArea textoBatalla;
    private final JButton btnIniciar;
    private JButton btnTurno;
    private JProgressBar barraHP1, barraHP2;

    // Ventanas auxiliares
    private JFrame ventanaEntrenadores;
    private JFrame ventanaEquipos;
    private JFrame ventanaBatalla;

    // Variables para los Pokémon activos
    private Pokemon poke1, poke2;
    private Entrenador entrenador1, entrenador2;

    public InterfazGrafica() {
        setTitle("¡Bienvenido al Mundo Pokémon!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
            "<html><center>🎮✨ ¡Prepárate para la Aventura! ✨🎮<br>Bienvenido al Simulador de Batallas Pokémon</center></html>",
            SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 100, 100));
        add(welcomeLabel, BorderLayout.CENTER);

        btnIniciar = new JButton("¡Comenzar!");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setBackground(new Color(100, 200, 255));
        btnIniciar.setForeground(Color.BLACK);
        add(btnIniciar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void agregarListenerIniciar(ActionListener action) {
        btnIniciar.addActionListener(e -> {
            action.actionPerformed(e);
            this.dispose(); // Cierra la ventana de inicio
        });
    }

    public void mostrarVentanaEntrenadores(ActionListener generarEquiposAction) {
        ventanaEntrenadores = new JFrame("Ingreso de Nombres de Entrenadores");
        ventanaEntrenadores.setSize(700, 500);
        ventanaEntrenadores.setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JLabel label1 = new JLabel("Nombre del Entrenador 1:");
        tfEntrenador1 = new JTextField(20);
        JLabel label2 = new JLabel("Nombre del Entrenador 2:");
        tfEntrenador2 = new JTextField(20);

        panelFormulario.add(label1);
        panelFormulario.add(tfEntrenador1);
        panelFormulario.add(Box.createVerticalStrut(20));
        panelFormulario.add(label2);
        panelFormulario.add(tfEntrenador2);

        JButton btnGenerar = new JButton("Formar Equipos");
        btnGenerar.addActionListener(e -> {
            generarEquiposAction.actionPerformed(e);
            ventanaEntrenadores.dispose();
        });

        ventanaEntrenadores.add(panelFormulario, BorderLayout.CENTER);
        ventanaEntrenadores.add(btnGenerar, BorderLayout.SOUTH);
        ventanaEntrenadores.setLocationRelativeTo(null);
        ventanaEntrenadores.setVisible(true);
    }

    public String getNombreEntrenador1() {
        return tfEntrenador1.getText();
    }

    public String getNombreEntrenador2() {
        return tfEntrenador2.getText();
    }

    public void mostrarEquipos(Entrenador entrenador1, Entrenador entrenador2, ActionListener iniciarBatallaAction) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;

        ventanaEquipos = new JFrame("Equipos Generados");
        ventanaEquipos.setSize(700, 500);
        ventanaEquipos.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new GridLayout(1, 2));
        panelContenido.add(crearPanelPokemon(entrenador1));
        panelContenido.add(crearPanelPokemon(entrenador2));

        JButton btnBatalla = new JButton("Iniciar Batalla");
        btnBatalla.addActionListener(e -> {
            iniciarBatallaAction.actionPerformed(e);
            ventanaEquipos.dispose();
        });

        ventanaEquipos.add(new JScrollPane(panelContenido), BorderLayout.CENTER);
        ventanaEquipos.add(btnBatalla, BorderLayout.SOUTH);
        ventanaEquipos.setLocationRelativeTo(null);
        ventanaEquipos.setVisible(true);
    }

    private JPanel crearPanelPokemon(Entrenador entrenador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(entrenador.getNombre()));

        for (Pokemon p : entrenador.getEquipo()) {
            JPanel panelPokemon = new JPanel();
            panelPokemon.setLayout(new BoxLayout(panelPokemon, BoxLayout.Y_AXIS));
            panelPokemon.setBorder(BorderFactory.createTitledBorder(p.getName()));

            JLabel lblHP = new JLabel("HP: " + p.getHealthPoints());
            JLabel lblAtaques = new JLabel("Ataques:");
            JTextArea areaAtaques = new JTextArea();
            areaAtaques.setEditable(false);

            for (Ataque a : p.getAttacks()) {
                areaAtaques.append(a.getdamagename() + " (Daño: " + a.getdamagepotency() + ")\n");
            }

            panelPokemon.add(lblHP);
            panelPokemon.add(lblAtaques);
            panelPokemon.add(new JScrollPane(areaAtaques));
            panel.add(panelPokemon);
        }

        return panel;
    }

    public void mostrarVentanaBatalla(Pokemon poke1, Pokemon poke2, List<Ataque> ataques1,
                                      List<Ataque> ataques2, ActionListener realizarTurnoAction) {
        this.poke1 = poke1;
        this.poke2 = poke2;

        ventanaBatalla = new JFrame("Batalla Pokémon");
        ventanaBatalla.setSize(700, 500);
        ventanaBatalla.setLayout(new BorderLayout());

        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        ventanaBatalla.add(new JScrollPane(textoBatalla), BorderLayout.CENTER);

        JPanel panelSuperior = new JPanel(new GridLayout(5, 2));
        barraHP1 = new JProgressBar(0, poke1.getMaxHP());
        barraHP2 = new JProgressBar(0, poke2.getMaxHP());

        comboAtaques1 = new JComboBox<>();
        comboAtaques2 = new JComboBox<>();

        for (Ataque a : ataques1) comboAtaques1.addItem(a.getdamagename() + " (Daño: " + a.getdamagepotency() + ")");
        for (Ataque a : ataques2) comboAtaques2.addItem(a.getdamagename() + " (Daño: " + a.getdamagepotency() + ")");

        barraHP1.setStringPainted(true);
        barraHP2.setStringPainted(true);
        actualizarHP(poke1, poke2);

        panelSuperior.add(new JLabel(poke1.getName()));
        panelSuperior.add(new JLabel(poke2.getName()));
        panelSuperior.add(barraHP1);
        panelSuperior.add(barraHP2);
        panelSuperior.add(new JLabel("Ataques:"));
        panelSuperior.add(new JLabel("Ataques:"));
        panelSuperior.add(comboAtaques1);
        panelSuperior.add(comboAtaques2);

        btnTurno = new JButton("Realizar Turno");
        btnTurno.addActionListener(realizarTurnoAction);

        ventanaBatalla.add(panelSuperior, BorderLayout.NORTH);
        ventanaBatalla.add(btnTurno, BorderLayout.SOUTH);
        ventanaBatalla.setLocationRelativeTo(null);
        ventanaBatalla.setVisible(true);
    }

    public String getAtaqueSeleccionado1() {
        return (String) comboAtaques1.getSelectedItem();
    }

    public String getAtaqueSeleccionado2() {
        return (String) comboAtaques2.getSelectedItem();
    }

    public void mostrarMensajeBatalla(String mensaje) {
        textoBatalla.append(mensaje + "\n");
        textoBatalla.setCaretPosition(textoBatalla.getDocument().getLength());
    }

    public void actualizarHP(Pokemon poke1, Pokemon poke2) {
        barraHP1.setValue(poke1.getHealthPoints());
        barraHP1.setString(poke1.getName() + " HP: " + poke1.getHealthPoints());
        barraHP2.setValue(poke2.getHealthPoints());
        barraHP2.setString(poke2.getName() + " HP: " + poke2.getHealthPoints());
    }

    public void desactivarBotonTurno() {
        btnTurno.setEnabled(false);
    }

    public void iniciarJuego() {
        agregarListenerIniciar(e -> {
            mostrarVentanaEntrenadores(event -> {
                String nombre1 = getNombreEntrenador1();
                String nombre2 = getNombreEntrenador2();

                entrenador1 = new Entrenador(nombre1);
                entrenador2 = new Entrenador(nombre2);

                entrenador1.generarEquipoAleatorio();
                entrenador2.generarEquipoAleatorio();

                mostrarEquipos(entrenador1, entrenador2, evt -> {
                    poke1 = entrenador1.obtenerPokemonActivo();
                    poke2 = entrenador2.obtenerPokemonActivo();

                    mostrarVentanaBatalla(
                        poke1, poke2, poke1.getAttacks(), poke2.getAttacks(),
                        turnoEvent -> {
                            // Obtener los ataques seleccionados de los ComboBox
                            String ataque1Seleccionado = getAtaqueSeleccionado1().split(" \\(Daño:")[0];
                            String ataque2Seleccionado = getAtaqueSeleccionado2().split(" \\(Daño:")[0];

                            // Buscar los ataques correspondientes en las listas de ataques
                            Ataque ataque1 = poke1.getAttacks().stream()
                                .filter(a -> a.getdamagename().equals(ataque1Seleccionado))
                                .findFirst().orElse(null);

                            Ataque ataque2 = poke2.getAttacks().stream()
                                .filter(a -> a.getdamagename().equals(ataque2Seleccionado))
                                .findFirst().orElse(null);

                            if (ataque1 != null && ataque2 != null) {
                                StringBuilder resultadoTurno = new StringBuilder();

                                // Determinar el orden de los ataques según la velocidad
                                if (poke1.getSpeed() >= poke2.getSpeed()) {
                                    // Pokémon 1 ataca primero
                                    int daño1 = ataque1.calculateDamage(poke2.getType(), poke2.getDefense());
                                    poke2.recibirAtaque(ataque1, poke1);
                                    resultadoTurno.append(poke1.getName())
                                        .append(" usó ")
                                        .append(ataque1.getdamagename())
                                        .append(" contra ")
                                        .append(poke2.getName())
                                        .append(". Hizo ")
                                        .append(daño1)
                                        .append(" de daño.\n");

                                    if (poke2.getHealthPoints() > 0) {
                                        int daño2 = ataque2.calculateDamage(poke1.getType(), poke1.getDefense());
                                        poke1.recibirAtaque(ataque2, poke2);
                                        resultadoTurno.append(poke2.getName())
                                            .append(" usó ")
                                            .append(ataque2.getdamagename())
                                            .append(" contra ")
                                            .append(poke1.getName())
                                            .append(". Hizo ")
                                            .append(daño2)
                                            .append(" de daño.\n");
                                    }
                                } else {
                                    // Pokémon 2 ataca primero
                                    int daño2 = ataque2.calculateDamage(poke1.getType(), poke1.getDefense());
                                    poke1.recibirAtaque(ataque2, poke2);
                                    resultadoTurno.append(poke2.getName())
                                        .append(" usó ")
                                        .append(ataque2.getdamagename())
                                        .append(" contra ")
                                        .append(poke1.getName())
                                        .append(". Hizo ")
                                        .append(daño2)
                                        .append(" de daño.\n");

                                    if (poke1.getHealthPoints() > 0) {
                                        int daño1 = ataque1.calculateDamage(poke2.getType(), poke2.getDefense());
                                        poke2.recibirAtaque(ataque1, poke1);
                                        resultadoTurno.append(poke1.getName())
                                            .append(" usó ")
                                            .append(ataque1.getdamagename())
                                            .append(" contra ")
                                            .append(poke2.getName())
                                            .append(". Hizo ")
                                            .append(daño1)
                                            .append(" de daño.\n");
                                    }
                                }

                                // Actualizar los puntos de vida en la interfaz
                                actualizarHP(poke1, poke2);

                                // Mostrar el resultado del turno en el área de texto
                                mostrarMensajeBatalla(resultadoTurno.toString());

                                // Verificar si algún Pokémon ha sido derrotado
                                if (poke1.getHealthPoints() <= 0 || poke2.getHealthPoints() <= 0) {
                                    manejarPokemonDerrotado();
                                }
                            }
                        }
                    );
                });
            });
        });
    }

    private void manejarPokemonDerrotado() {
        if (poke1.getHealthPoints() <= 0) {
            entrenador1.eliminarPokemonActivo();
            if (entrenador1.tienePokemonVivos()) {
                poke1 = entrenador1.obtenerPokemonActivo();
                mostrarMensajeBatalla(entrenador1.getNombre() + " envía a " + poke1.getName() + " al combate.");
            } else {
                desactivarBotonTurno();
                mostrarMensajeBatalla("¡" + entrenador2.getNombre() + " gana la batalla!");
            }
        }

        if (poke2.getHealthPoints() <= 0) {
            entrenador2.eliminarPokemonActivo();
            if (entrenador2.tienePokemonVivos()) {
                poke2 = entrenador2.obtenerPokemonActivo();
                mostrarMensajeBatalla(entrenador2.getNombre() + " envía a " + poke2.getName() + " al combate.");
            } else {
                desactivarBotonTurno();
                mostrarMensajeBatalla("¡" + entrenador1.getNombre() + " gana la batalla!");
            }
        }
    }
}
