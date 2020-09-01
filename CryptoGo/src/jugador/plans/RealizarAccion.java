package jugador.plans;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.concepts.*;
import ontology.predicates.TurnoAsignado;



public class RealizarAccion extends Plan
{
	public void body()
	{
        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        TurnoAsignado mensajeRecibido = (TurnoAsignado) peticion.getContent();
        Jugador jugador = mensajeRecibido.getJugador();
        Mesa mesa = mensajeRecibido.getMesa();
      

        //Jugador jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        //jugador.setIdAgente((AgentIdentifier) getAgentIdentifier());

        if ((int) getBeliefbase().getBelief("turno").getFact() == 1){
            // Decidir estrategia
            int estrategia = (int) (Math.random()*3);
            getBeliefbase().getBelief("estrategia").setFact(estrategia);
        }


        if ((int) getBeliefbase().getBelief("turno").getFact() == 6){
            //Habilitar sustitucion
            getBeliefbase().getBelief("sustituir").setFact(true);
        }
        getBeliefbase().getBelief("jugador").setFact(jugador);
        getBeliefbase().getBelief("mesa_objeto").setFact(mesa);
        getBeliefbase().getBelief("turnoJugador").setFact(true);

        
    }
}