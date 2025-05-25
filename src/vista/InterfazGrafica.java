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

    public InterfazGrafica() {
        setTitle("¡Simulador de Batallas Pokémon!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Fondo colorido
        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(new Color(135, 206, 250)); // Azul cielo
        panelFondo.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
            "<html><center>🎮 ¡Bienvenido al Simulador de Batallas Pokémon! 🎮<br>"
            + "Prepárate para una emocionante aventura.</center></html>",
            SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 69, 0)); // Rojo anaranjado
        panelFondo.add(welcomeLabel, BorderLayout.CENTER);

        btnIniciar = new JButton("¡Comenzar!");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setBackground(new Color(50, 205, 50)); // Verde lima
        btnIniciar.setForeground(Color.WHITE);
        panelFondo.add(btnIniciar, BorderLayout.SOUTH);

        add(panelFondo, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void agregarListenerIniciar(ActionListener action) {
        btnIniciar.addActionListener(action);
    }

    public void mostrarVentanaEntrenadores(ActionListener generarEquiposAction) {
        JFrame ventanaEntrenadores = new JFrame("Nombres de Entrenadores");
        ventanaEntrenadores.setSize(400, 200);
        ventanaEntrenadores.setLayout(new GridLayout(3, 2, 10, 10));
        ventanaEntrenadores.getContentPane().setBackground(new Color(240, 230, 140)); // Amarillo claro

        JLabel label1 = new JLabel("Entrenador 1:");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        tfEntrenador1 = new JTextField(10);

        JLabel label2 = new JLabel("Entrenador 2:");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setFont(new Font("Arial", Font.BOLD, 14));
        tfEntrenador2 = new JTextField(10);

        JButton btnGenerar = new JButton("Generar Equipos");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerar.setBackground(new Color(30, 144, 255)); // Azul profundo
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.addActionListener(generarEquiposAction);

        ventanaEntrenadores.add(label1);
        ventanaEntrenadores.add(tfEntrenador1);
        ventanaEntrenadores.add(label2);
        ventanaEntrenadores.add(tfEntrenador2);
        ventanaEntrenadores.add(new JLabel()); // Espaciador
        ventanaEntrenadores.add(btnGenerar);

        ventanaEntrenadores.setLocationRelativeTo(null);
        ventanaEntrenadores.setVisible(true);
    }

    public String getNombreEntrenador1() {
        return tfEntrenador1.getText();
    }

    public String getNombreEntrenador2() {
        return tfEntrenador2.getText();
    }

    public void mostrarVentanaBatalla(Pokemon poke1, Pokemon poke2, List<Ataque> ataques1, List<Ataque> ataques2, ActionListener realizarTurnoAction) {
        JFrame ventanaBatalla = new JFrame("Batalla Pokémon");
        ventanaBatalla.setSize(700, 500);
        ventanaBatalla.setLayout(new BorderLayout());

        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        ventanaBatalla.add(new JScrollPane(textoBatalla), BorderLayout.CENTER);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
        barraHP1 = new JProgressBar(0, poke1.getMaxHP());
        barraHP2 = new JProgressBar(0, poke2.getMaxHP());
        barraHP1.setValue(poke1.getHealthPoints());
        barraHP2.setValue(poke2.getHealthPoints());

        panelSuperior.add(new JLabel(poke1.getName()));
        panelSuperior.add(new JLabel(poke2.getName()));
        panelSuperior.add(barraHP1);
        panelSuperior.add(barraHP2);

        JPanel panelAtaques = new JPanel(new GridLayout(1, 2));
        comboAtaques1 = new JComboBox<>();
        comboAtaques2 = new JComboBox<>();
        for (Ataque ataque : ataques1) comboAtaques1.addItem(ataque.getdamagename());
        for (Ataque ataque : ataques2) comboAtaques2.addItem(ataque.getdamagename());
        panelAtaques.add(comboAtaques1);
        panelAtaques.add(comboAtaques2);

        btnTurno = new JButton("Realizar Turno");
        btnTurno.addActionListener(realizarTurnoAction);

        ventanaBatalla.add(panelSuperior, BorderLayout.NORTH);
        ventanaBatalla.add(panelAtaques, BorderLayout.CENTER);
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

    public void actualizarHP(Pokemon poke1, Pokemon poke2) {
        barraHP1.setValue(poke1.getHealthPoints());
        barraHP2.setValue(poke2.getHealthPoints());
    }

    public void mostrarMensajeBatalla(String mensaje) {
        textoBatalla.append(mensaje + "\n");
    }

    public void desactivarBotonTurno() {
        btnTurno.setEnabled(false);
    }
}
