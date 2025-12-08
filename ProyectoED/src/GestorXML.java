import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GestorXML {

    public static void guardar(ControlJuego juego, File archivo) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.newDocument();

        Element root = doc.createElement("partida");
        root.setAttribute("ganada", String.valueOf(juego.isPartidaGanada()));
        root.setAttribute("perdida", String.valueOf(juego.isPartidaPerdida()));
        doc.appendChild(root);

        Element eCaja = doc.createElement("caja");
        root.appendChild(eCaja);
        for (Carta c : juego.getCaja().obtenerCartas()) {
            eCaja.appendChild(crearElementoCarta(doc, c));
        }

        Element eMazo = doc.createElement("mazo");
        root.appendChild(eMazo);
        for (Carta c : juego.getMazo().obtenerCartasComoArreglo()) {
            eMazo.appendChild(crearElementoCarta(doc, c));
        }

        Element eMano = doc.createElement("mano");
        root.appendChild(eMano);
        for (Carta c : juego.getMano().obtenerCartas()) {
            eMano.appendChild(crearElementoCarta(doc, c));
        }

        Element ePozo = doc.createElement("pozo");
        root.appendChild(ePozo);
        for (Carta c : juego.getPozo().obtenerCartas()) {
            ePozo.appendChild(crearElementoCarta(doc, c));
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        t.transform(source, result);
    }

    private static Element crearElementoCarta(Document doc, Carta c) {
        Element e = doc.createElement("carta");
        e.setAttribute("valor", c.getValor());
        e.setAttribute("palo", c.getPalo());
        e.setAttribute("bocaAbajo", String.valueOf(c.isBocaAbajo()));
        return e;
    }

    public static ControlJuego cargar(File archivo) throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(archivo);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        boolean ganada = Boolean.parseBoolean(root.getAttribute("ganada"));
        boolean perdida = Boolean.parseBoolean(root.getAttribute("perdida"));

        Caja caja = new Caja();
        Mazo mazo = new Mazo();
        Mano mano = new Mano();
        Pozo pozo = new Pozo();

        NodeList listaCaja = doc.getElementsByTagName("caja").item(0).getChildNodes();
        for (int i = 0; i < listaCaja.getLength(); i++) {
            if (listaCaja.item(i) instanceof Element e) {
                Carta c = crearCartaDesdeElemento(e);
                caja.agregarAlFinal(c);
            }
        }

        NodeList listaMazo = doc.getElementsByTagName("mazo").item(0).getChildNodes();
        for (int i = 0; i < listaMazo.getLength(); i++) {
            if (listaMazo.item(i) instanceof Element e) {
                Carta c = crearCartaDesdeElemento(e);
                mazo.push(c);
            }
        }

        NodeList listaMano = doc.getElementsByTagName("mano").item(0).getChildNodes();
        for (int i = 0; i < listaMano.getLength(); i++) {
            if (listaMano.item(i) instanceof Element e) {
                Carta c = crearCartaDesdeElemento(e);
                c.setBocaAbajo(false);
                mano.agregarCarta(c);
            }
        }

        NodeList listaPozo = doc.getElementsByTagName("pozo").item(0).getChildNodes();
        for (int i = 0; i < listaPozo.getLength(); i++) {
            if (listaPozo.item(i) instanceof Element e) {
                Carta c = crearCartaDesdeElemento(e);
                pozo.encolar(c);
            }
        }

        ControlJuego juego = new ControlJuego(caja, mazo, mano, pozo);
        juego.setPartidaGanada(ganada);
        juego.setPartidaPerdida(perdida);
        return juego;
    }

    private static Carta crearCartaDesdeElemento(Element e) {
        String valor = e.getAttribute("valor");
        String palo = e.getAttribute("palo");
        boolean bocaAbajo = Boolean.parseBoolean(e.getAttribute("bocaAbajo"));
        Carta c = new Carta(valor, palo);
        c.setBocaAbajo(bocaAbajo);
        return c;
    }
}