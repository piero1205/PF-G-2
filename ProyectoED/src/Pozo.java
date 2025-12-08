public class Pozo {

    private static class Nodo {
        Carta carta;
        Nodo siguiente;

        public Nodo(Carta c) {
            carta = c;
        }
    }

    private Nodo frente;
    private Nodo fin;
    private int size;

    public Pozo() {
        frente = null;
        fin = null;
        size = 0;
    }

    public void encolar(Carta c) {
        if (c == null)
            return;

        c.setBocaAbajo(true);

        Nodo nuevo = new Nodo(c);

        if (fin == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        size++;
    }

    public Carta desencolar() {
        if (frente == null)
            return null;

        Carta c = frente.carta;
        frente = frente.siguiente;

        if (frente == null)
            fin = null;

        size--;
        return c;
    }

    public int tamano() {
        return size;
    }

    public boolean estaVacio() {
        return size == 0;
    }

    public Carta[] obtenerCartas() {
        Carta[] arr = new Carta[size];
        Nodo actual = frente;
        int i = 0;

        while (actual != null) {
            arr[i++] = actual.carta;
            actual = actual.siguiente;
        }
        return arr;
    }

    public void mostrarPozo() {
        System.out.println("=== Cartas en el Pozo ===");
        Nodo actual = frente;

        while (actual != null) {
            String palo = (actual.carta.getPalo() == null) ? "-" : actual.carta.getPalo();
            System.out.println(actual.carta.getValor() + " de " + palo);
            actual = actual.siguiente;
        }

        System.out.println("=========================");
    }
}