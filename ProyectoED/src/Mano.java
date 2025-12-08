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
        if (size >= MAX)
            return false;

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

    public boolean eliminarCarta(Carta c) {
        if (cabeza == null)
            return false;

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

        if (cabeza == null)
            return lista;

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
}
