package ontology.predicates;

import java.util.ArrayList;

import ontology.concepts.Jugador;

public class TurnoAsignado extends Predicado {

  private Jugador jugador;
  private ArrayList<Jugador> jugadores;

  public TurnoAsignado() {
  }

  public Jugador getJugador() {
    return jugador;
  }
  public void setJugador(final Jugador jugador) {
    this.jugador = jugador;
  }

  public ArrayList<Jugador> getJugadores() {
    return jugadores;
  }
  public void setJugadores(final ArrayList<Jugador> jugadores) {
    this.jugadores = jugadores;
  }

}