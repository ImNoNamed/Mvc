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
        panelFormulario.setOpaque(false);
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JLabel label1 = new JLabel("Nombre del Entrenador 1:");
        tfEntrenador1 = new JTextField(20);
        JLabel label2 = new JLabel("Nombre del Entrenador 2:");
        tfEntrenador2 = new JTextField(20);

        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfEntrenador1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfEntrenador2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelFormulario.add(label1);
        panelFormulario.add(tfEntrenador1);
        panelFormulario.add(Box.createVerticalStrut(20));
        panelFormulario.add(label2);
        panelFormulario.add(tfEntrenador2);

        JButton btnGenerar = new JButton("Formar Equipos");
        btnGenerar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGenerar.addActionListener(e -> {
            generarEquiposAction.actionPerformed(e);
            ventanaEntrenadores.dispose();
        });

        JPanel panelFondo = new JPanel();
        panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
        ventanaEntrenadores.setContentPane(panelFondo);

        panelFondo.add(Box.createVerticalGlue());
        panelFondo.add(panelFormulario);
        panelFondo.add(btnGenerar);
        panelFondo.add(Box.createVerticalGlue());

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
        ventanaEquipos = new JFrame("Equipos Generados");
        ventanaEquipos.setSize(700, 500);
        ventanaEquipos.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new GridLayout(1, 2)); 
        panelContenido.add(crearPanelEquipo(entrenador1));
        panelContenido.add(crearPanelEquipo(entrenador2));

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

    private JPanel crearPanelEquipo(Entrenador entrenador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(entrenador.getNombre()));

        for (Pokemon p : entrenador.getEquipo()) {
            panel.add(new JLabel(p.getName() + " (HP: " + p.getHealthPoints() + ")"));
        }
        return panel;
    }

    public void mostrarVentanaBatalla(Pokemon poke1, Pokemon poke2, List<Ataque> ataques1, 
                                      List<Ataque> ataques2, ActionListener realizarTurnoAction) {

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

        for (Ataque a : ataques1) comboAtaques1.addItem(a.getdamagename());
        for (Ataque a : ataques2) comboAtaques2.addItem(a.getdamagename());

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
}
