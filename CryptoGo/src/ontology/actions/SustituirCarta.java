
package ontology.actions;
import java.util.*;

import ontology.concepts.*;

public class SustituirCarta extends Accion {

    public SustituirCarta() {
    }

    private ArrayList<Carta> sustitucion = new ArrayList<>(); //[Carta a sustituir, Carta que la sustituye]
    private Jugador jugador = new Jugador();

    public ArrayList<Carta> getSustitucion(){
        return sustitucion;
    }
    public void setSustitucion(ArrayList<Carta> sustitucion){
        this.sustitucion = sustitucion;
    }

    public Carta getCartaASustituir(){
        return sustitucion.get(0);
    }

    public Carta getCartaSustituta(){
        return sustitucion.get(1);
    }

    public Jugador getJugador() {
        return jugador;
    }
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

}
