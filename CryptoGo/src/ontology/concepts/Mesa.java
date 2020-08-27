package ontology.concepts;

import jadex.adapter.fipa.AgentIdentifier;

import java.util.ArrayList;

public class Mesa extends Concepto {

    /*** Constructor ***/
    public Mesa() {
    }

    /*** Atributos ***/
    private Mazo mazo;
    private ArrayList<Jugador> jugadores;

    /*** Getters & Setters ***/
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Mazo getMazo() {
        return mazo;
    }
    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public Jugador getJugador(AgentIdentifier idJugador) {
        for (int i = 0; i < getJugadores().size(); i++) {
          if (getJugadores().get(i).getIdAgente().equals(idJugador)) {
            return getJugadores().get(i);
          }
        }
        return null;
    }
 
}