package jugador.plans;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.concepts.*;



public class UnirsePartidaPlan extends Plan
{
	public void body()
	{
		// Registra al jugador
		AgentIdentifier agentId = new AgentIdentifier();
		Jugador jugador = new Jugador();
		System.out.println("[PLAN] Un nuevo jugador quiere unirse a la partida");
		jugador.setIdAgente(agentId);
		getBeliefbase().getBelief("jugador").setFact(jugador);		

		ServiceDescription sd = new ServiceDescription();
		sd.setName("mesa");
		sd.setType("agente");

		AgentDescription dfaDesc = new AgentDescription();
		dfaDesc.addService(sd);

		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(-1);

		// Busca la mesa
		IGoal ft = createGoal("df_search");
		ft.getParameter("description").setValue(dfaDesc);
		ft.getParameter("constraints").setValue(sc);
		dispatchSubgoalAndWait(ft);

		System.out.println("[INFO] Jugador busca mesa...");
		AgentDescription[] result = (AgentDescription[]) ft.getParameterSet("result").getValues();
		if (result.length > 0) {
			AgentIdentifier mesa = result[0].getName();
			
			getBeliefbase().getBelief("mesa").setFact(mesa);
			
			// Solicitud unirse
			UnirsePartida accion = new UnirsePartida();
			
			IMessageEvent msgsend = createMessageEvent("Request_Unirse_Partida");
			msgsend.setContent(accion);
			msgsend.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
			
			sendMessage(msgsend);

			System.out.println("[INFO] Mesa encontrada! Jugador envia mensaje para unirse a mesa");
		} else {
			System.out.println("[FALLO] Mesa no encontrada");
		}
	}

}
