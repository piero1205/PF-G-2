import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    private ArrayList<Carta> cartas;
    private JPanel panelCaja, panelMazo, panelMano, panelPozo;

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
        panelCaja.setBorder(BorderFactory.createTitledBorder("Caja (26 Cartas)"));
        panelCaja.setLayout(new GridLayout(4, 7, 10, 10));

        add(panelCaja, BorderLayout.CENTER);

        cartas = new ArrayList<>();
        crearCartas();
        mostrarCartas();

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
    }

    private void mostrarCartas() {
        for (Carta c : cartas) {
            JLabel cartaLabel = new JLabel(c.getValor() + " de " + c.getPalo());
            cartaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cartaLabel.setOpaque(true);
            cartaLabel.setBackground(Color.WHITE);
            cartaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cartaLabel.setPreferredSize(new Dimension(100, 60));
            panelCaja.add(cartaLabel);
        }
    }
}
