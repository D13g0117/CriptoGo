package ontology.actions;


import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;


public class SolicitarCartas extends Accion {

  public SolicitarCartas() {
  }

  AgentIdentifier sender;
  int numCartasARobar = 0;
  public AgentIdentifier getSenderID() {
    return sender;
  }
  public void setSenderID(AgentIdentifier sender) {
      this.sender = sender;
  }

  public int getNumCartasARobar() {
    return numCartasARobar;
  }
  public void setNumCartasARobar(int numCartasARobar) {
    this.numCartasARobar = numCartasARobar;
  }

}
