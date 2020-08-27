package jugador.plans;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.concepts.*;



public class SolicitarCartasPlan extends Plan
{
	public void body()
	{
        AgentIdentifier mesa = (AgentIdentifier) getBeliefbase().getBelief("mesa").getFact();
        Jugador jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        IMessageEvent msgsend = createMessageEvent("Request_Solicitar_Cartas");
        SolicitarCartas solicitud = new SolicitarCartas();
        solicitud.setSenderID(jugador.getIdAgente());
        msgsend.setContent(solicitud);
        msgsend.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
        System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " solicita cartas");
        sendMessage(msgsend);
    }
}