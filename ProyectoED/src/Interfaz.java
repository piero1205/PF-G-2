import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    private ArrayList<Carta> cartas;
    private JPanel panelCaja, panelMazo, panelMano, panelPozo;
    private Mano mano;

    public Interfaz() {
        setTitle("Baraja de cartas");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
        panelMazo = new JPanel();
        panelMazo.setBorder(BorderFactory.createTitledBorder("Mazo"));
        panelPozo = new JPanel();
        panelPozo.setBorder(BorderFactory.createTitledBorder("Pozo"));
        panelMano = new JPanel();
        panelMano.setBorder(BorderFactory.createTitledBorder("Mano"));

        panelSuperior.add(panelMazo);
        panelSuperior.add(panelPozo);
        panelSuperior.add(panelMano);
        add(panelSuperior, BorderLayout.NORTH);

        panelCaja = new JPanel();
        panelCaja.setBorder(BorderFactory.createTitledBorder("Caja (52 Cartas)"));
        panelCaja.setLayout(new GridLayout(4, 13, 10, 10));
        add(panelCaja, BorderLayout.CENTER);

        cartas = new ArrayList<>();
        crearCartas();
        mostrarCartas();

        mano = new Mano();

        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Controles"));

        JButton btnBarajar = new JButton("Barajar Cartas");
        JButton btnValidar = new JButton("Validar Sándwich");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");

        panelInferior.add(btnBarajar);
        panelInferior.add(btnValidar);
        panelInferior.add(btnGuardar);
        panelInferior.add(btnCargar);

        add(panelInferior, BorderLayout.SOUTH);

        btnBarajar.addActionListener(e -> {
            mano = new Mano();
            for (int i = 0; i < 8; i++) {
                mano.agregarCarta(cartas.get(i));
            }
            mostrarMano();
        });

        setVisible(true);
    }

    private void crearCartas() {
        String[] valores = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

        for (String valor : valores) {
            cartas.add(new Carta(valor, "♠ Picas"));
        }
        for (String valor : valores) {
            cartas.add(new Carta(valor, "♥ Corazones"));
        }
        for (String valor : valores) {
            cartas.add(new Carta(valor, "♦ Diamantes"));
        }
        for (String valor : valores) {
            cartas.add(new Carta(valor, "♣ Tréboles"));
        }
    }

    private void mostrarCartas() {
        for (Carta c : cartas) {
            JLabel cartaLabel = new JLabel(c.getValor() + " de " + c.getPalo());
            cartaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cartaLabel.setOpaque(true);
            cartaLabel.setBackground(Color.WHITE);
            cartaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cartaLabel.setPreferredSize(new Dimension(100, 60));

            if (c.getPalo().contains("♥") || c.getPalo().contains("♦")) {
                cartaLabel.setForeground(Color.RED);
            } else {
                cartaLabel.setForeground(Color.BLACK);
            }

            panelCaja.add(cartaLabel);
        }
    }

    private void mostrarMano() {
        panelMano.removeAll();

        ArrayList<Carta> lista = mano.obtenerCartas();

        for (Carta c : lista) {
            JLabel lbl = new JLabel(c.getValor() + " de " + c.getPalo());
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBackground(Color.WHITE);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            lbl.setPreferredSize(new Dimension(100, 60));

            if (c.getPalo().contains("♥") || c.getPalo().contains("♦")) {
                lbl.setForeground(Color.RED);
            } else {
                lbl.setForeground(Color.BLACK);
            }

            panelMano.add(lbl);
        }

        panelMano.revalidate();
        panelMano.repaint();
    }
}
