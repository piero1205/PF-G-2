import java.util.ArrayList;
import java.util.List;

public class ControlJuego {

    private Caja caja;
    private Mazo mazo;
    private Mano mano;
    private Pozo pozo;
    private boolean partidaGanada;
    private boolean partidaPerdida;

    public ControlJuego(Caja caja, Mazo mazo, Mano mano, Pozo pozo) {
        this.caja = caja;
        this.mazo = mazo;
        this.mano = mano;
        this.pozo = pozo;
        this.partidaGanada = false;
        this.partidaPerdida = false;
    }

    public Caja getCaja() {
        return caja;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public Mano getMano() {
        return mano;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public boolean isPartidaGanada() {
        return partidaGanada;
    }

    public boolean isPartidaPerdida() {
        return partidaPerdida;
    }

    public void setPartidaGanada(boolean partidaGanada) {
        this.partidaGanada = partidaGanada;
    }

    public void setPartidaPerdida(boolean partidaPerdida) {
        this.partidaPerdida = partidaPerdida;
    }

    public boolean esSandwich(Carta a, Carta b, Carta c) {
        int A = a.getValorNumerico();
        int B = b.getValorNumerico();
        int C = c.getValorNumerico();
        int d1 = Carta.diferenciaCircular(A, B);
        int d2 = Carta.diferenciaCircular(B, C);
        return d1 == d2;
    }

    public int cartasARecargar(Carta a, Carta b, Carta c) {
        boolean mismoPalo = a.getPalo().equals(b.getPalo()) && b.getPalo().equals(c.getPalo());
        boolean mismoColor = a.getColor().equals(b.getColor()) && b.getColor().equals(c.getColor());
        if (mismoPalo) return 4;
        if (mismoColor) return 3;
        return 2;
    }

    public List<PermutacionResultado> evaluarTripleta(List<Carta> tripleta) {
        List<PermutacionResultado> lista = new ArrayList<>();
        if (tripleta == null || tripleta.size() != 3) return lista;
        Carta x = tripleta.get(0);
        Carta y = tripleta.get(1);
        Carta z = tripleta.get(2);
        Carta[][] perms = new Carta[][]{
                {x, y, z},
                {x, z, y},
                {y, x, z},
                {y, z, x},
                {z, x, y},
                {z, y, x}
        };
        ArbolPermutaciones arbol = new ArbolPermutaciones();
        for (Carta[] p : perms) {
            int recarga = 0;
            if (esSandwich(p[0], p[1], p[2])) {
                recarga = cartasARecargar(p[0], p[1], p[2]);
            }
            PermutacionResultado pr = new PermutacionResultado(p, recarga);
            arbol.insertar(pr);
        }
        lista.addAll(arbol.inOrden());
        return lista;
    }

    public boolean aplicarTripleta(List<Carta> tripleta) {
        if (tripleta == null || tripleta.size() != 3) return false;
        List<PermutacionResultado> eval = evaluarTripleta(tripleta);
        int max = 0;
        for (PermutacionResultado pr : eval) {
            if (pr.getCartasARecargar() > max) max = pr.getCartasARecargar();
        }
        if (max == 0) return false;
        for (Carta c : tripleta) {
            mano.eliminarCarta(c);
            pozo.encolar(c);
        }
        int espacio = 8 - mano.getSize();
        int tomar = Math.min(max, espacio);
        ArrayList<Carta> nuevas = mazo.tomar(tomar);
        for (Carta c : nuevas) {
            c.setBocaAbajo(false);
            mano.agregarCarta(c);
        }
        if (mazo.victoria()) {
            partidaGanada = true;
        }
        return true;
    }

    public boolean haySandwichEnMano() {
        ArrayList<ArrayList<Carta>> tripletas = mano.obtenerTripletas();
        for (ArrayList<Carta> t : tripletas) {
            List<PermutacionResultado> eval = evaluarTripleta(t);
            for (PermutacionResultado pr : eval) {
                if (pr.getCartasARecargar() > 0) return true;
            }
        }
        return false;
    }
}