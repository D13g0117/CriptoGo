package ontology.concepts;
import java.util.*;
import jadex.adapter.fipa.AgentIdentifier;
import ontology.*;

public class Jugador extends Concepto {

    /*** Constructor ***/
    public Jugador() {
    }

    /*** Atributos ***/
    private AgentIdentifier idAgente;
    private Carta cartaSeleccionada;
    private ArrayList<Carta> mano;
    private Seleccion seleccion = new Seleccion();

    /*** Getters & Setters ***/
    public AgentIdentifier getIdAgente() {
        return idAgente;
    }
    public void setIdAgente(AgentIdentifier idAgente) {
        this.idAgente = idAgente;
      }

    public int getNumCartas() {
        return mano.size();
    }

    public ArrayList<Carta> getMano(){
        return mano;
    }
    public void setMano(ArrayList<Carta> mano){
        this.mano = mano;
    }

    public Seleccion getSeleccion(){
        return seleccion;
    }
    public void setSeleccion(Seleccion seleccion){
        this.seleccion = seleccion;
    }
    
    public Carta getCartaSeleccionada(){
        return cartaSeleccionada;
    }
    public void setCartaSeleccionada (Carta cartaSeleccionada){
        this.cartaSeleccionada = cartaSeleccionada;
    }

}