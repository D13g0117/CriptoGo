package ontology.actions;


import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;


public class SolicitarCartas extends Accion {

  public SolicitarCartas() {
  }

  AgentIdentifier sender;

  public AgentIdentifier getSenderID() {
    return sender;
  }
  public void setSenderID(AgentIdentifier sender) {
      this.sender = sender;
  }


}
