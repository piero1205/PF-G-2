import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Interfaz extends JFrame {

    private Caja caja;
    private Mazo mazo;
    private Pozo pozo;
    private Mano mano;

    private JPanel panelCaja, panelMazo, panelMano, panelPozo;

    public Interfaz() {
        setTitle("Baraja de cartas");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        caja = new Caja();
        mazo = new Mazo();
        pozo = new Pozo();
        mano = new Mano();

        crearCartas();

        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));

        panelMazo = new JPanel(new GridLayout(4, 4, 5, 5));
        panelMazo.setBorder(BorderFactory.createTitledBorder("Mazo"));
        JScrollPane scrollMazo = new JScrollPane(panelMazo);

        panelPozo = new JPanel(new GridLayout(1, 5, 5, 5));
        panelPozo.setBorder(BorderFactory.createTitledBorder("Pozo"));

        panelMano = new JPanel(new GridLayout(1, 8, 5, 5));
        panelMano.setBorder(BorderFactory.createTitledBorder("Mano"));

        panelSuperior.add(scrollMazo);
        panelSuperior.add(panelPozo);
        panelSuperior.add(panelMano);

        add(panelSuperior, BorderLayout.NORTH);

        panelCaja = new JPanel(new GridLayout(4, 13, 5, 5));
        panelCaja.setBorder(BorderFactory.createTitledBorder("Caja (52 cartas)"));
        add(panelCaja, BorderLayout.CENTER);

        mostrarCaja();

        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Controles"));

        JButton btnBarajar = new JButton("Barajar (Caja → Mazo)");
        JButton btnMoverMazo = new JButton("Tomar carta del Mazo → Pozo");
        JButton btnAgregarMano = new JButton("Tomar carta del Mazo → Mano");

        panelInferior.add(btnBarajar);
        panelInferior.add(btnMoverMazo);
        panelInferior.add(btnAgregarMano);

        add(panelInferior, BorderLayout.SOUTH);

        btnBarajar.addActionListener(e -> {
            ArrayList<Carta> lista = new ArrayList<>();

            while (!caja.estaVacia()) {
                lista.add(caja.extraerPrimero());
            }

            Collections.shuffle(lista);

            for (Carta carta : lista) {
                carta.setBocaAbajo(true);
                mazo.push(carta);
            }

            mostrarCaja();
            mostrarMazo();
        });

        btnMoverMazo.addActionListener(e -> {
            Carta c = mazo.pop();
            if (c != null) {
                c.setBocaAbajo(false);
                pozo.encolar(c);
                mostrarMazo();
                mostrarPozo();
            }
        });

        btnAgregarMano.addActionListener(e -> {
            Carta c = mazo.pop();
            if (c != null) {
                c.setBocaAbajo(false);
                mano.agregarCarta(c);
                mostrarMazo();
                mostrarMano();
            }
        });

        setVisible(true);
    }

    private void crearCartas() {
        String[] valores = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] palos = { "♠ Picas", "♥ Corazones", "♦ Diamantes", "♣ Tréboles" };

        for (String palo : palos)
            for (String valor : valores)
                caja.agregarAlFinal(new Carta(valor, palo));
    }

    private void mostrarCaja() {
        panelCaja.removeAll();
        Carta[] arr = caja.obtenerCartas();
        for (Carta c : arr)
            panelCaja.add(crearLabelCarta(c));
        panelCaja.revalidate();
        panelCaja.repaint();
    }

    private void mostrarMazo() {
        panelMazo.removeAll();
        Carta[] arr = mazo.obtenerCartasComoArreglo();
        for (Carta c : arr)
            panelMazo.add(crearLabelCarta(c));
        panelMazo.revalidate();
        panelMazo.repaint();
    }

    private void mostrarPozo() {
        panelPozo.removeAll();
        Carta[] arr = pozo.obtenerCartas();
        for (Carta c : arr)
            panelPozo.add(crearLabelCarta(c));
        panelPozo.revalidate();
        panelPozo.repaint();
    }

    private void mostrarMano() {
        panelMano.removeAll();
        for (Carta c : mano.obtenerCartas())
            panelMano.add(crearLabelCarta(c));
        panelMano.revalidate();
        panelMano.repaint();
    }

    private JLabel crearLabelCarta(Carta c) {
        JLabel lbl;

        if (c.isBocaAbajo()) {
            lbl = new JLabel("??", SwingConstants.CENTER);
            lbl.setForeground(Color.BLACK);
        } else {
            lbl = new JLabel(c.getValor() + " de " + c.getPalo(), SwingConstants.CENTER);
            lbl.setForeground(c.getPalo().contains("♥") || c.getPalo().contains("♦") ? Color.RED : Color.BLACK);
        }

        lbl.setOpaque(true);
        lbl.setBackground(Color.WHITE);
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lbl.setPreferredSize(new Dimension(90, 90)); // CUADRADO
        return lbl;
    }
}
