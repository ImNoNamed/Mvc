package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import model.*;

public class InterfazGrafica {
    private JFrame ventanaPrincipal;
    private JButton botonIniciar;
    private JFrame ventanaEntrenadores;
    private JTextField campoNombre1, campoNombre2;
    private JFrame ventanaEquipos;
    private JFrame ventanaBatalla;
    private JTextArea areaBatalla;
    private JButton botonTurno;
    private JComboBox<String> comboAtaques1, comboAtaques2;
    private JLabel labelPoke1, labelPoke2;
    private JProgressBar barraHP1, barraHP2;

    public InterfazGrafica() {
        configurarVentanaPrincipal();
    }

    private void configurarVentanaPrincipal() {
        ventanaPrincipal = new JFrame("⚔️ Simulador de Batalla Pokémon");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(700, 350);
        ventanaPrincipal.setLayout(new BorderLayout());

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color colorInicio = new Color(255, 248, 220);
                Color colorFin = new Color(173, 216, 230);
                GradientPaint gp = new GradientPaint(0, 0, colorInicio, 0, height, colorFin);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelFondo.setLayout(new BorderLayout());
        ventanaPrincipal.setContentPane(panelFondo);

        JLabel titulo = new JLabel("¡Bienvenido al Simulador de Batalla Pokémon!");
        titulo.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
        titulo.setForeground(new Color(70, 30, 150));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        panelFondo.add(titulo, BorderLayout.CENTER);

        botonIniciar = new JButton("Iniciar Batalla");
        botonIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        botonIniciar.setBackground(new Color(30, 144, 255));
        botonIniciar.setForeground(Color.WHITE);
        botonIniciar.setFocusPainted(false);
        botonIniciar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(10, 100, 200), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        botonIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelBoton.add(botonIniciar);

        panelFondo.add(panelBoton, BorderLayout.SOUTH);

        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
    }

    public void agregarListenerIniciar(ActionListener listener) {
        botonIniciar.addActionListener(listener);
    }

    public void mostrarVentanaEntrenadores(ActionListener listener) {
        ventanaEntrenadores = new JFrame("⚙️ Configurar Entrenadores");
        ventanaEntrenadores.setSize(400, 250);
        ventanaEntrenadores.setLayout(new GridLayout(4, 1));
        ventanaEntrenadores.getContentPane().setBackground(new Color(245, 245, 245));

        campoNombre1 = new JTextField();
        campoNombre2 = new JTextField();

        ventanaEntrenadores.add(new JLabel("Nombre del Entrenador 1:"));
        ventanaEntrenadores.add(campoNombre1);
        ventanaEntrenadores.add(new JLabel("Nombre del Entrenador 2:"));
        ventanaEntrenadores.add(campoNombre2);

        JButton botonSiguiente = new JButton("Generar Equipos");
        botonSiguiente.setBackground(new Color(144, 238, 144));
        botonSiguiente.setFont(new Font("Arial", Font.BOLD, 14));
        botonSiguiente.addActionListener(listener);

        ventanaEntrenadores.add(botonSiguiente);
        ventanaEntrenadores.setLocationRelativeTo(null);
        ventanaEntrenadores.setVisible(true);
    }

    public String getNombreEntrenador1() {
        return campoNombre1.getText();
    }

    public String getNombreEntrenador2() {
        return campoNombre2.getText();
    }

    public void mostrarEquipos(Entrenador e1, Entrenador e2, ActionListener listener) {
        ventanaEntrenadores.dispose();
        ventanaEquipos = new JFrame("👥 Equipos Generados");
        ventanaEquipos.setSize(500, 500);
        ventanaEquipos.setLayout(new BorderLayout());
        ventanaEquipos.getContentPane().setBackground(new Color(255, 248, 240));

        JLabel titulo = new JLabel("Equipos Pokémon Generados");
        titulo.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        ventanaEquipos.add(titulo, BorderLayout.NORTH);

        JPanel panelEquipos = new JPanel();
        panelEquipos.setLayout(new GridLayout(2, 1, 10, 10));
        panelEquipos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelEquipos.setOpaque(false);

        JTextArea areaEquipo1 = crearAreaEquipo("Equipo de " + e1.getNombre(), e1);
        JTextArea areaEquipo2 = crearAreaEquipo("Equipo de " + e2.getNombre(), e2);

        JScrollPane scroll1 = new JScrollPane(areaEquipo1);
        JScrollPane scroll2 = new JScrollPane(areaEquipo2);

        scroll1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        scroll2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        panelEquipos.add(scroll1);
        panelEquipos.add(scroll2);

        ventanaEquipos.add(panelEquipos, BorderLayout.CENTER);

        JButton botonIniciar = new JButton("¡Comenzar la Batalla!");
        botonIniciar.setBackground(new Color(255, 105, 180));
        botonIniciar.setForeground(Color.WHITE);
        botonIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        botonIniciar.setFocusPainted(false);
        botonIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonIniciar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botonIniciar.addActionListener(listener);

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelBoton.add(botonIniciar);

        ventanaEquipos.add(panelBoton, BorderLayout.SOUTH);

        ventanaEquipos.setLocationRelativeTo(null);
        ventanaEquipos.setVisible(true);
    }

    private JTextArea crearAreaEquipo(String titulo, Entrenador entrenador) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("Consolas", Font.PLAIN, 16));
        area.setText(titulo + ":\n\n");
        for (Pokemon p : entrenador.getEquipo()) {
            area.append("- " + p.getName() + " (" + p.getType() + ", HP: " + p.getHealthPoints() + ")\n");
        }
        return area;
    }

public void mostrarVentanaBatalla(String nombreEntrenador1, String nombreEntrenador2,
                                   Pokemon p1, Pokemon p2,
                                   List<Ataque> ataques1, List<Ataque> ataques2,
                                   ActionListener listener) {
    ventanaEquipos.dispose();
    ventanaBatalla = new JFrame("⚔️ ¡Batalla Pokémon!");
    ventanaBatalla.setSize(700, 500);
    ventanaBatalla.setLayout(new BorderLayout());
    ventanaBatalla.getContentPane().setBackground(new Color(240, 255, 255));

    JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
    panelSuperior.setBackground(new Color(224, 255, 255));

    barraHP1 = new JProgressBar(0, p1.getHealthPoints());
    barraHP1.setValue(p1.getHealthPoints());
    barraHP1.setStringPainted(true);
    barraHP1.setForeground(Color.GREEN);

    barraHP2 = new JProgressBar(0, p2.getHealthPoints());
    barraHP2.setValue(p2.getHealthPoints());
    barraHP2.setStringPainted(true);
    barraHP2.setForeground(Color.GREEN);

    labelPoke1 = new JLabel(nombreEntrenador1 + " - 🐾 " + p1.getName());
    labelPoke2 = new JLabel(nombreEntrenador2 + " - 🐾 " + p2.getName());

    panelSuperior.add(labelPoke1);
    panelSuperior.add(labelPoke2);
    panelSuperior.add(barraHP1);
    panelSuperior.add(barraHP2);

    areaBatalla = new JTextArea();
    areaBatalla.setEditable(false);
    areaBatalla.setBackground(new Color(255, 255, 240));
    areaBatalla.setFont(new Font("Monospaced", Font.PLAIN, 14));

    JPanel panelAtaques = new JPanel(new GridLayout(3, 2));
    comboAtaques1 = new JComboBox<>();
    comboAtaques2 = new JComboBox<>();
    for (Ataque a : ataques1) comboAtaques1.addItem(a.getdamagename());
    for (Ataque a : ataques2) comboAtaques2.addItem(a.getdamagename());

    botonTurno = new JButton("Ejecutar Turno");
    botonTurno.setFont(new Font("Arial", Font.BOLD, 14));
    botonTurno.setBackground(new Color(255, 215, 0));
    botonTurno.addActionListener(listener);

    panelAtaques.add(new JLabel("Ataque de " + nombreEntrenador1 + ":"));
    panelAtaques.add(comboAtaques1);
    panelAtaques.add(new JLabel("Ataque de " + nombreEntrenador2 + ":"));
    panelAtaques.add(comboAtaques2);
    panelAtaques.add(botonTurno);

    ventanaBatalla.add(panelSuperior, BorderLayout.NORTH);
    ventanaBatalla.add(new JScrollPane(areaBatalla), BorderLayout.CENTER);
    ventanaBatalla.add(panelAtaques, BorderLayout.SOUTH);

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
        areaBatalla.append(mensaje);
    }

    public void actualizarHP(Pokemon p1, Pokemon p2) {
        labelPoke1.setText("🐾 " + p1.getName());
        barraHP1.setValue(p1.getHealthPoints());

        labelPoke2.setText("🐾 " + p2.getName());
        barraHP2.setValue(p2.getHealthPoints());
    }

    public void desactivarBotonTurno() {
        botonTurno.setEnabled(false);
    }
}
