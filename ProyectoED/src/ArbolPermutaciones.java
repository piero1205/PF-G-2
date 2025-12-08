import java.util.ArrayList;
import java.util.List;

public class ArbolPermutaciones {

    private NodoPermutacion raiz;

    public void insertar(PermutacionResultado pr) {
        raiz = insertarRec(raiz, pr);
    }

    private NodoPermutacion insertarRec(NodoPermutacion actual, PermutacionResultado pr) {
        if (actual == null) return new NodoPermutacion(pr);
        if (pr.getCartasARecargar() < actual.dato.getCartasARecargar()) {
            actual.izq = insertarRec(actual.izq, pr);
        } else if (pr.getCartasARecargar() > actual.dato.getCartasARecargar()) {
            actual.der = insertarRec(actual.der, pr);
        } else {
            String c1 = pr.clave();
            String c2 = actual.dato.clave();
            if (c1.compareTo(c2) < 0) {
                actual.izq = insertarRec(actual.izq, pr);
            } else if (c1.compareTo(c2) > 0) {
                actual.der = insertarRec(actual.der, pr);
            }
        }
        return actual;
    }

    public List<PermutacionResultado> inOrden() {
        List<PermutacionResultado> lista = new ArrayList<>();
        inOrdenRec(raiz, lista);
        return lista;
    }

    private void inOrdenRec(NodoPermutacion nodo, List<PermutacionResultado> lista) {
        if (nodo == null) return;
        inOrdenRec(nodo.izq, lista);
        lista.add(nodo.dato);
        inOrdenRec(nodo.der, lista);
    }

    public PermutacionResultado mejor() {
        if (raiz == null) return null;
        NodoPermutacion actual = raiz;
        while (actual.der != null) actual = actual.der;
        return actual.dato;
    }
}