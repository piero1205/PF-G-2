public class Mazo {

    private static class NodoMazo {
        Carta carta;
        NodoMazo siguiente;

        public NodoMazo(Carta carta) {
            this.carta = carta;
        }
    }

    private NodoMazo cima;
    private int size;

    public Mazo() {
        cima = null;
        size = 0;
    }

    public void push(Carta c) {
        NodoMazo nuevo = new NodoMazo(c);
        nuevo.siguiente = cima;
        cima = nuevo;
        size++;
    }

    public Carta pop() {
        if (cima == null)
            return null;

        Carta c = cima.carta;
        cima = cima.siguiente;
        size--;

        return c;
    }

    public Carta peek() {
        return (cima == null) ? null : cima.carta;
    }

    public boolean estaVacio() {
        return cima == null;
    }

    public int tamaño() {
        return size;
    }

    public void agregarCartasDesdeArreglo(Carta[] arreglo) {
        if (arreglo == null)
            return;

        for (Carta c : arreglo) {
            push(c);
        }
    }

    public Carta[] obtenerCartasComoArreglo() {
        Carta[] arr = new Carta[size];
        NodoMazo actual = cima;
        int i = 0;

        while (actual != null) {
            arr[i++] = actual.carta;
            actual = actual.siguiente;
        }
        return arr;
    }

    public void mostrarMazo() {
        System.out.println("=== Contenido del Mazo ===");

        NodoMazo actual = cima;
        while (actual != null) {
            String palo = (actual.carta.getPalo() == null) ? "-" : actual.carta.getPalo();
            System.out.println(actual.carta.getValor() + " de " + palo);
            actual = actual.siguiente;
        }

        System.out.println("==========================");
    }
}
