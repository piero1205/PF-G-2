import java.util.ArrayList;

public class Mano {

    private static class NodoCarta {
        Carta carta;
        NodoCarta siguiente;

        public NodoCarta(Carta carta) {
            this.carta = carta;
        }
    }

    private NodoCarta cabeza;
    private int size;
    private final int MAX = 8;

    public Mano() {
        cabeza = null;
        size = 0;
    }

    public boolean agregarCarta(Carta c) {
        if (size >= MAX) return false;

        NodoCarta nuevo = new NodoCarta(c);

        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza;
        } else {
            NodoCarta actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }

        size++;
        return true;
    }
public void ordenar() {
    ArrayList<Carta> cartas = obtenerCartas();
    int n = cartas.size();

    cartas.sort((c1, c2) -> {
        int v1 = valorNumerico(c1);
        int v2 = valorNumerico(c2);
        return Integer.compare(v1, v2);
    });

    cabeza = null;
    size = 0;

    for (Carta c : cartas) {
        agregarCarta(c);
    }
}
public ArrayList<ArrayList<Carta>> obtenerTripletas() {
    ArrayList<Carta> cartas = obtenerCartas();
    ArrayList<ArrayList<Carta>> tripletas = new ArrayList<>();

    int n = cartas.size();
    for (int i = 0; i < n - 2; i++) {
        for (int j = i + 1; j < n - 1; j++) {
            for (int k = j + 1; k < n; k++) {
                ArrayList<Carta> t = new ArrayList<>();
                t.add(cartas.get(i));
                t.add(cartas.get(j));
                t.add(cartas.get(k));
                tripletas.add(t);
            }
        }
    }
    return tripletas;
}
    public boolean eliminarCarta(Carta c) {
        if (cabeza == null) return false;

        NodoCarta actual = cabeza;
        NodoCarta anterior = null;

        do {
            if (actual.carta.equals(c)) {

                if (anterior == null) {
                    if (cabeza.siguiente == cabeza) {
                        cabeza = null;
                    } else {
                        NodoCarta ultimo = cabeza;
                        while (ultimo.siguiente != cabeza) {
                            ultimo = ultimo.siguiente;
                        }
                        cabeza = cabeza.siguiente;
                        ultimo.siguiente = cabeza;
                    }
                } else {
                    anterior.siguiente = actual.siguiente;
                }

                size--;
                return true;
            }

            anterior = actual;
            actual = actual.siguiente;

        } while (actual != cabeza);

        return false;
    }

    public ArrayList<Carta> obtenerCartas() {
        ArrayList<Carta> lista = new ArrayList<>();

        if (cabeza == null) return lista;

        NodoCarta actual = cabeza;

        do {
            lista.add(actual.carta);
            actual = actual.siguiente;
        } while (actual != cabeza);

        return lista;
    }

    public int getSize() {
        return size;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    private int valorNumerico(Carta c) {
        switch (c.getValor()) {
            case "A": return 1;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            default:  return Integer.parseInt(c.getValor());
        }
    }

    private int diferenciaCircular(int a, int b) {
        int dif = Math.abs(a - b);
        return Math.min(dif, 13 - dif);
    }

    public boolean validarTripleta(ArrayList<Carta> t) {
        if (t.size() != 3) return false;

        int a = valorNumerico(t.get(0));
        int b = valorNumerico(t.get(1));
        int c = valorNumerico(t.get(2));

        int d1 = diferenciaCircular(a, b);
        int d2 = diferenciaCircular(b, c);

        return d1 == d2;
    }

    private boolean esRojo(Carta c) {
        return c.getPalo().contains("♥") || c.getPalo().contains("♦");
    }

    public int calcularRecompensa(ArrayList<Carta> t) {
        boolean mismoPalo =
                t.get(0).getPalo().equals(t.get(1).getPalo()) &&
                t.get(1).getPalo().equals(t.get(2).getPalo());

        if (mismoPalo) return 4;

        boolean mismoColor =
                (esRojo(t.get(0)) == esRojo(t.get(1))) &&
                (esRojo(t.get(1)) == esRojo(t.get(2)));

        if (mismoColor) return 3;

        return 2;
    }

    public boolean validarMano() {
        ArrayList<Carta> cartas = obtenerCartas();
        int n = cartas.size();

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    ArrayList<Carta> trio = new ArrayList<>();
                    trio.add(cartas.get(i));
                    trio.add(cartas.get(j));
                    trio.add(cartas.get(k));
                    if (validarTripleta(trio)) return true;
                }
            }
        }
        return false;
    }
    public boolean aplicarTripleta(ArrayList<Carta> seleccion) {
        if (!validarTripleta(seleccion)) return false;

        for (Carta c : seleccion) eliminarCarta(c);

        return true;
    }
}