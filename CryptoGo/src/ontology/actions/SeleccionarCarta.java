
package ontology.actions;

import ontology.concepts.*;

public class SeleccionarCarta extends Accion {

  public SeleccionarCarta() {
  }

  private Carta cartaSeleccionada;

  public Carta getCartaSeleccionada() {
    return cartaSeleccionada;
  }
  public void setCartaSeleccionada(Carta cartaSeleccionada) {
    this.cartaSeleccionada = cartaSeleccionada;
  }

}
