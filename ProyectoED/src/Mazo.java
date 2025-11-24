import java.util.Stack;
import java.util.Collections;
import java.util.List;

public class Mazo {

    private Stack<Carta> pila;

    public Mazo() {
        pila = new Stack<>();
    }

    public void agregarCartas(List<Carta> cartas) {
        Collections.shuffle(cartas);
        pila.addAll(cartas);
    }

    public void push(Carta carta) {
        pila.push(carta);
    }

    public Carta pop() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.pop();
    }

    public Carta peek() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.peek();
    }

    public boolean estaVacio() {
        return pila.isEmpty();
    }

    public int tamaño() {
        return pila.size();
    }

    public void mostrarMazo() {
        System.out.println("=== Contenido Mazo ===");
        for (Carta c : pila) {
            System.out.println(c.getValor() + " de " + c.getPalo());
        }
        System.out.println("==========================");
    }


    public Stack<Carta> getPila() {
        return pila;
    }
}
