package jugador.plans;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.concepts.*;



public class RealizarAccion extends Plan
{
	public void body()
	{
        Jugador jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        jugador.setIdAgente((AgentIdentifier) getAgentIdentifier());
        getBeliefbase().getBelief("jugador").setFact(jugador);
        getBeliefbase().getBelief("turnoJugador").setFact(true);
    }
}