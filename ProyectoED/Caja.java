public class Caja {

    private class Nodo {
        Carta carta;
        Nodo anterior;
        Nodo siguiente;

        Nodo(Carta carta) {
            this.carta = carta;
        }
    }

    private Nodo cabeza;
    private Nodo cola;
    private int cantidad;


    public Caja() {
        cabeza = null;
        cola = null;
        cantidad = 0;
    }

    public void agregarAlFinal(Carta carta) {
        Nodo nuevo = new Nodo(carta);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }

        cantidad++;
    }

    public Carta extraerPrimero() {
        if (cabeza == null) {
            return null;
        }

        Carta carta = cabeza.carta;

        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }

        cantidad--;
        return carta;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamano() {
        return cantidad;
    }

    public void imprimir() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.carta);
            actual = actual.siguiente;
        }
    }

    public Carta[] obtenerCartas() {
        Carta[] arreglo = new Carta[cantidad];
        Nodo actual = cabeza;
        int i = 0;

        while (actual != null) {
            arreglo[i++] = actual.carta;
            actual = actual.siguiente;
        }
        return arreglo;
    }
}
