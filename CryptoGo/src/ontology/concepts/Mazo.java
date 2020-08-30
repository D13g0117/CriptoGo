package ontology.concepts;
 
import java.util.*;
import jadex.adapter.fipa.AgentIdentifier;
import ontology.*;

public class Mazo extends Concepto {

    /*** Constructor ***/
    public Mazo() {
    }
    
    /*** Atributos ***/
    private ArrayList<Carta> cartas;

    /*** Getters & Setters ***/
    public ArrayList<Carta> getCartas(){
        return cartas;
    }
    public void setCartas(ArrayList<Carta> cartas){
        this.cartas = cartas;
    }

    public Carta getCarta(){
        int numeroAleatorio = (int) (Math.random()*cartas.size());
        return cartas.remove(numeroAleatorio);
    }
    public int getNumCartasRestantes() {
        return cartas.size();
    }

}