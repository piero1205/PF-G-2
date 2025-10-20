
public class Carta {

    private String valor;
    private String palo;
    private int poder;
    private String descripcion;
    private boolean bocaAbajo;

    public Carta(String valor, String descripcion, int poder) {
        this.valor = valor;
        this.descripcion = descripcion;
        this.poder = poder;
        this.bocaAbajo = false;
    }

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
        this.descripcion = "Sin descripcion";
        this.poder = 0;
        this.bocaAbajo = false;
    }

    public String getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPoder() {
        return poder;
    }

    public boolean isBocaAbajo() {
        return bocaAbajo;
    }

    public void setBocaAbajo(boolean bocaAbajo) {
        this.bocaAbajo = bocaAbajo;
    }

    public void mostrarCarta() {
        System.out.println("Carta: " + valor);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Poder: " + poder);
        System.out.println("-----------------------");
    }

}
