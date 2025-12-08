import java.util.Arrays;

public class PermutacionResultado {

    private Carta[] cartas;
    private int cartasARecargar;

    public PermutacionResultado(Carta[] cartas, int cartasARecargar) {
        this.cartas = cartas;
        this.cartasARecargar = cartasARecargar;
    }

    public Carta[] getCartas() {
        return cartas;
    }

    public int getCartasARecargar() {
        return cartasARecargar;
    }

    public String toTexto() {
        StringBuilder sb = new StringBuilder();
        for (Carta c : cartas) {
            sb.append("[").append(c.getValor()).append("|").append(c.getPalo()).append("] ");
        }
        sb.append("→ ").append(cartasARecargar).append(" cartas");
        return sb.toString();
    }

    @Override
    public String toString() {
        return toTexto();
    }

    public String clave() {
        String[] v = new String[cartas.length];
        for (int i = 0; i < cartas.length; i++) {
            v[i] = cartas[i].getValor() + cartas[i].getPalo();
        }
        Arrays.sort(v);
        StringBuilder sb = new StringBuilder();
        for (String s : v) sb.append(s);
        return sb.toString();
    }
}