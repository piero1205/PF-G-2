import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Interfaz extends JFrame {

    private Caja caja;
    private Mazo mazo;
    private Pozo pozo;
    private Mano mano;
    private ControlJuego juego;

    private JPanel panelCaja;
    private JPanel panelMazo;
    private JPanel panelMano;
    private JPanel panelPozo;
    private JPanel panelSeleccionInfo;

    private ArrayList<Carta> seleccionMano;

    private final Color VERDE_MESA = new Color(0, 110, 0);

    public Interfaz() {

        setTitle("The Sandwich Guy");
        setSize(1400, 820);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        seleccionMano = new ArrayList<>();

        caja = new Caja();
        mazo = new Mazo();
        pozo = new Pozo();
        mano = new Mano();
        juego = new ControlJuego(caja, mazo, mano, pozo);

        crearCartas();

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
        panelSuperior.setPreferredSize(new Dimension(1400, 260));
        panelSuperior.setBackground(VERDE_MESA);

        panelMazo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelMazo.setBackground(VERDE_MESA);
        panelMazo.setBorder(BorderFactory.createTitledBorder("Mazo"));
        JScrollPane scrollMazo = new JScrollPane(panelMazo);
        scrollMazo.setPreferredSize(new Dimension(200, 240));
        scrollMazo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollMazo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panelMano = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelMano.setBackground(VERDE_MESA);
        panelMano.setBorder(BorderFactory.createTitledBorder("Mano"));
        JScrollPane scrollMano = new JScrollPane(panelMano);
        scrollMano.setPreferredSize(new Dimension(900, 240));
        scrollMano.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollMano.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollMano.getHorizontalScrollBar().setUnitIncrement(20);

        panelPozo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelPozo.setBackground(VERDE_MESA);
        panelPozo.setBorder(BorderFactory.createTitledBorder("Pozo"));
        JScrollPane scrollPozo = new JScrollPane(panelPozo);
        scrollPozo.setPreferredSize(new Dimension(200, 240));
        scrollPozo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPozo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panelSuperior.add(Box.createHorizontalStrut(20));
        panelSuperior.add(scrollMazo);
        panelSuperior.add(Box.createHorizontalGlue());
        panelSuperior.add(scrollMano);
        panelSuperior.add(Box.createHorizontalGlue());
        panelSuperior.add(scrollPozo);
        panelSuperior.add(Box.createHorizontalStrut(20));

        add(panelSuperior, BorderLayout.NORTH);

       
        panelCaja = new JPanel();
        panelCaja.setLayout(new BoxLayout(panelCaja, BoxLayout.Y_AXIS));
        panelCaja.setBackground(VERDE_MESA);
        panelCaja.setBorder(BorderFactory.createTitledBorder("Caja (52 cartas)"));
        JScrollPane scrollCaja = new JScrollPane(panelCaja);
        scrollCaja.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCaja.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollCaja, BorderLayout.CENTER);

        mostrarCaja();

       
        panelSeleccionInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSeleccionInfo.setPreferredSize(new Dimension(1400, 35));
        panelSeleccionInfo.setBackground(new Color(230, 230, 230));

      
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Controles"));
        panelInferior.setBackground(new Color(230, 230, 230));

        JButton btnNueva = new JButton("Nueva partida");
        JButton btnBarajar = new JButton("Barajar y repartir");
        JButton btnOrdenar = new JButton("Ordenar mano");
        JButton btnValidar = new JButton("Validar mano");
        JButton btnAplicar = new JButton("Aplicar tripleta");
        JButton btnGuardar = new JButton("Guardar partida");
        JButton btnCargar = new JButton("Cargar partida");

        panelInferior.add(btnNueva);
        panelInferior.add(btnBarajar);
        panelInferior.add(btnOrdenar);
        panelInferior.add(btnValidar);
        panelInferior.add(btnAplicar);
        panelInferior.add(btnGuardar);
        panelInferior.add(btnCargar);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(panelSeleccionInfo, BorderLayout.NORTH);
        panelBottom.add(panelInferior, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);

     
        btnNueva.addActionListener(e -> nuevaPartida());
        btnBarajar.addActionListener(e -> barajarYRepartir());
        btnOrdenar.addActionListener(e -> {
            if (partidaTerminada()) return;
            mano.ordenar();
            mostrarMano();
        });
        btnValidar.addActionListener(e -> validarMano());
        btnAplicar.addActionListener(e -> aplicarTripleta());
        btnGuardar.addActionListener(e -> guardarPartida());
        btnCargar.addActionListener(e -> cargarPartida());

        actualizarPanelSeleccion();
        setVisible(true);
    }

  
    private void crearCartas() {
        String[] valores = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String[] palos = {"♠ Picas","♥ Corazones","♦ Diamantes","♣ Tréboles"};

        for (String palo : palos)
            for (String valor : valores)
                caja.agregarAlFinal(new Carta(valor, palo));
    }


    private JPanel crearCartaVisual(Carta c, boolean seleccionable) {

        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 60));
                g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 20, 20);

           
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 20, 20);

                if (!seleccionMano.contains(c)) {
                    g2.setColor(Color.BLACK);
                    g2.drawRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 20, 20);
                } else {
                    g2.setColor(Color.YELLOW);
                    g2.setStroke(new BasicStroke(4));
                    g2.drawRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 20, 20);
                }

                // Boca abajo
                if (c.isBocaAbajo()) {
                    g2.setColor(Color.DARK_GRAY);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 24));
                    g2.drawString("??", 40, 75);
                } else {
                    g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                    if (c.getPalo().contains("♥") || c.getPalo().contains("♦"))
                        g2.setColor(Color.RED);
                    else
                        g2.setColor(Color.BLACK);

                    g2.drawString(c.getValor(), 15, 25);
                    g2.drawString(c.getPalo().split(" ")[0], 15, 55);
                }
            }
        };

        card.setOpaque(false);
        card.setPreferredSize(new Dimension(110, 150));

        if (seleccionable && !c.isBocaAbajo()) {
            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    seleccionarCarta(c);
                }
            });
        }

        return card;
    }

    private void seleccionarCarta(Carta c) {
        if (partidaTerminada()) return;

        if (seleccionMano.contains(c)) {
            seleccionMano.remove(c);
        } else {
            if (seleccionMano.size() == 3) {
                JOptionPane.showMessageDialog(this, "Máximo 3 cartas.");
                return;
            }
            seleccionMano.add(c);
        }
        mostrarMano();
        actualizarPanelSeleccion();
    }

    private void actualizarPanelSeleccion() {
        panelSeleccionInfo.removeAll();
        StringBuilder sb = new StringBuilder("Seleccionadas: ");

        if (seleccionMano.isEmpty()) {
            sb.append("ninguna");
        } else {
            for (Carta c : seleccionMano) {
                sb.append(c.getValor())
                  .append(" ")
                  .append(c.getPalo().split(" ")[0])
                  .append("   ");
            }
        }

        JLabel lbl = new JLabel(sb.toString());
        lbl.setFont(new Font("SansSerif", Font.BOLD, 15));
        panelSeleccionInfo.add(lbl);
        panelSeleccionInfo.revalidate();
        panelSeleccionInfo.repaint();
    }

    private void mostrarCaja() {
        panelCaja.removeAll();

        Carta[] cartas = caja.obtenerCartas();
        String[] nombresPalos = {"Picas", "Corazones", "Diamantes", "Tréboles"};
        String[] simbolos = {"♠", "♥", "♦", "♣"};

        for (int i = 0; i < nombresPalos.length; i++) {
            String paloNombre = nombresPalos[i];
            String simbolo = simbolos[i];

            StringBuilder linea = new StringBuilder();
            linea.append(simbolo).append(" ").append(paloNombre).append(":   ");

            for (Carta c : cartas) {
                if (c.getPalo().contains(paloNombre)) {
                    linea.append(c.getValor()).append("  ");
                }
            }

            JLabel lbl = new JLabel(linea.toString());
            lbl.setForeground(simbolo.equals("♥") || simbolo.equals("♦") ? Color.RED : Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            panelCaja.add(lbl);
        }

        panelCaja.revalidate();
        panelCaja.repaint();
    }

    private void mostrarMazo() {
        panelMazo.removeAll();
        Carta[] arr = mazo.obtenerCartasComoArreglo();
        for (Carta c : arr) panelMazo.add(crearCartaVisual(c, false));
        panelMazo.revalidate();
        panelMazo.repaint();
    }

    private void mostrarPozo() {
        panelPozo.removeAll();
        Carta[] arr = pozo.obtenerCartas();
        for (Carta c : arr) panelPozo.add(crearCartaVisual(c, false));
        panelPozo.revalidate();
        panelPozo.repaint();
    }

    private void mostrarMano() {
        panelMano.removeAll();
        ArrayList<Carta> lista = mano.obtenerCartas();
        for (Carta c : lista) panelMano.add(crearCartaVisual(c, true));
        panelMano.revalidate();
        panelMano.repaint();
    }


    private boolean partidaTerminada() {
        if (juego.isPartidaGanada()) {
            JOptionPane.showMessageDialog(this, "La partida ya fue GANADA. Inicia una nueva partida.");
            return true;
        }
        if (juego.isPartidaPerdida()) {
            JOptionPane.showMessageDialog(this, "La partida ya fue PERDIDA. Inicia una nueva partida.");
            return true;
        }
        return false;
    }

    private void nuevaPartida() {
        caja = new Caja();
        mazo = new Mazo();
        pozo = new Pozo();
        mano = new Mano();
        juego = new ControlJuego(caja, mazo, mano, pozo);

        crearCartas();
        seleccionMano.clear();
        actualizarPanelSeleccion();

        mostrarCaja();
        mostrarMazo();
        mostrarPozo();
        mostrarMano();
    }

    private void barajarYRepartir() {
        if (partidaTerminada()) return;

        ArrayList<Carta> lista = new ArrayList<>();
        while (!caja.estaVacia()) lista.add(caja.extraerPrimero());
        Collections.shuffle(lista);

        for (Carta c : lista) {
            c.setBocaAbajo(true);
            mazo.push(c);
        }

        for (int i = 0; i < 8; i++) {
            Carta c = mazo.pop();
            if (c != null) {
                c.setBocaAbajo(false);
                mano.agregarCarta(c);
            }
        }

        mostrarCaja();
        mostrarMazo();
        mostrarMano();
    }

    private void validarMano() {
        if (partidaTerminada()) return;

        boolean ok = juego.haySandwichEnMano();

        if (ok) {
            JOptionPane.showMessageDialog(this, "Hay al menos un sándwich válido en la mano.");
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún sándwich válido en la mano.\nHas perdido la partida.");
            juego.setPartidaPerdida(true);
        }
    }

    private void aplicarTripleta() {
        if (partidaTerminada()) return;

        if (seleccionMano.size() != 3) {
            JOptionPane.showMessageDialog(this, "Selecciona exactamente 3 cartas.");
            return;
        }

      
        List<PermutacionResultado> perms = juego.evaluarTripleta(seleccionMano);

        if (perms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron evaluar permutaciones.");
            return;
        }

     
        StringBuilder sb = new StringBuilder("Permutaciones posibles:\n\n");
        for (PermutacionResultado pr : perms) {
            sb.append(pr.toTexto()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Permutaciones de la tripleta", JOptionPane.INFORMATION_MESSAGE);

        int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Desea enviar esta tripleta al Pozo y recargar cartas\n" +
                "según la MEJOR combinación encontrada?",
                "Confirmar sándwich",
                JOptionPane.YES_NO_OPTION
        );

        if (resp != JOptionPane.YES_OPTION) {
            return;
        }

       
        boolean ok = juego.aplicarTripleta(seleccionMano);

        if (!ok) {
            JOptionPane.showMessageDialog(this, "La tripleta NO es un sándwich válido.");
            return;
        }

        // Actualizamos UI
        seleccionMano.clear();
        actualizarPanelSeleccion();
        mostrarMazo();
        mostrarMano();
        mostrarPozo();

        if (juego.isPartidaGanada()) {
            JOptionPane.showMessageDialog(this, "🎉 ¡HAS GANADO LA PARTIDA! 🎉");
        }
    }


    private void guardarPartida() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                GestorXML.guardar(juego, f);
                JOptionPane.showMessageDialog(this, "Partida guardada.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }

    private void cargarPartida() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                ControlJuego cargado = GestorXML.cargar(f);
                this.juego = cargado;

                this.caja = juego.getCaja();
                this.mazo = juego.getMazo();
                this.mano = juego.getMano();
                this.pozo = juego.getPozo();

                seleccionMano.clear();
                actualizarPanelSeleccion();

                mostrarCaja();
                mostrarMazo();
                mostrarMano();
                mostrarPozo();

                if (juego.isPartidaGanada()) {
                    JOptionPane.showMessageDialog(this, "Partida cargada (ya GANADA). No se puede seguir jugando esta partida.");
                } else if (juego.isPartidaPerdida()) {
                    JOptionPane.showMessageDialog(this, "Partida cargada (ya PERDIDA). No se puede seguir jugando esta partida.");
                } else {
                    JOptionPane.showMessageDialog(this, "Partida cargada. Puedes continuar jugando.");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
            }
        }
    }
}
