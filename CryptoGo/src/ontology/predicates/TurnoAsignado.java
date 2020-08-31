package ontology.predicates;

import java.util.ArrayList;

import ontology.concepts.Jugador;
import ontology.concepts.Mesa;

public class TurnoAsignado extends Predicado {

  private Jugador jugador;
  private Mesa mesa;

  public TurnoAsignado() {
  }

  public Jugador getJugador() {
    return jugador;
  }
  public void setJugador(final Jugador jugador) {
    this.jugador = jugador;
  }

  public Mesa getMesa() {
    return mesa;
  }
  public void setMesa(final Mesa mesa) {
    this.mesa = mesa;
  }

}