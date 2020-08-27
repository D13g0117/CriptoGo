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
    private ArrayList<Carta> sustitucion; //[Carrta a sustituir, Carta que la sustituye]
    private ArrayList<Carta> mano;
    private ArrayList<Carta> seleccion;

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
    public ArrayList<Carta> setMano(ArrayList<Carta> mano){
        return this.mano = mano;
    }

    public ArrayList<Carta> getSeleccion(){
        return seleccion;
    }
    public ArrayList<Carta> setSeleccion(ArrayList<Carta> seleccion){
        return this.seleccion = seleccion;
    }

    public ArrayList<Carta> getSustitucion(){
        return sustitucion;
    }
    public ArrayList<Carta> setSustitucion(ArrayList<Carta> sustitucion){
        return this.sustitucion = sustitucion;
    }
    
    public Carta getCartaSeleccionada(){
        return cartaSeleccionada;
    }
    public Carta setCartaSeleccionada (Carta cartaSeleccionada){
        return this.cartaSeleccionada = cartaSeleccionada;
    }

}