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

    public void barajar() {
        Carta[] arr = obtenerCartas();

        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            Carta temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        cabeza = null;
        cola = null;
        cantidad = 0;

        for (Carta c : arr) {
            agregarAlFinal(c);
        }
    }

    public Carta extraerAleatoria() {
        if (cabeza == null)
            return null;

        int index = (int) (Math.random() * cantidad);

        Nodo actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }

        Carta carta = actual.carta;

        if (actual == cabeza) {
            extraerPrimero();
        } else if (actual == cola) {
            cola = cola.anterior;
            cola.siguiente = null;
            cantidad--;
        } else {
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
            cantidad--;
        }

        return carta;
    }

    public void pasarAlMazo(Mazo mazo) {
        while (!estaVacia()) {
            Carta c = extraerAleatoria();
            mazo.push(c);
        }
    }
}
