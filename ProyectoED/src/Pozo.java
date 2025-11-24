import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Pozo {

    private Stack<Carta> pilaPozo;

    public Pozo() {
        pilaPozo = new Stack<>();
    }

    public void tirarCarta(Carta carta) {
        pilaPozo.push(carta);
    }

    public Carta verUltimaCarta() {
        if (pilaPozo.isEmpty())
            return null;

        return pilaPozo.peek();
    }


    public Carta sacarUltimaCarta() {
        if (pilaPozo.isEmpty())
            return null;

        return pilaPozo.pop();
    }


    public int getTamaño() {
        return pilaPozo.size();
    }

    public boolean estaVacio() {
        return pilaPozo.isEmpty();
    }


    public void mostrarPozo() {
        System.out.println(" Cartas en el Pozo ");
        for (Carta c : pilaPozo) {
            System.out.println(c.getValor() + " de " + c.getPalo());
        }
        System.out.println("--------------------");
    }


    public List<Carta> obtenerComoLista() {
        return new ArrayList<>(pilaPozo);
    }
}
