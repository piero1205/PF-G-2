public class Carta {

    private String valor;
    private String palo;
    private boolean bocaAbajo;

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
        this.bocaAbajo = false;
    }

    public String getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public boolean isBocaAbajo() {
        return bocaAbajo;
    }

    public void setBocaAbajo(boolean bocaAbajo) {
        this.bocaAbajo = bocaAbajo;
    }

    public String getColor() {
        if (palo.contains("♥") || palo.contains("♦")) return "Rojo";
        return "Negro";
    }

    public int getValorNumerico() {
        return switch (valor) {
            case "A" -> 1;
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            default -> Integer.parseInt(valor);
        };
    }

    public static int diferenciaCircular(int a, int b) {
        int d = Math.abs(a - b);
        return Math.min(d, 13 - d);
    }
}