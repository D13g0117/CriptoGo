package mesa.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;
import ontology.actions.*;
import ontology.concepts.*;
import ontology.predicates.*;


public class DarCartasPlan extends Plan
{
    
	public void body()
	{
        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        AgentIdentifier idJugador = (AgentIdentifier) peticion.getParameter("sender").getValue();
        SolicitarCartas solicitud = (SolicitarCartas) peticion.getContent();
        AgentIdentifier target = (AgentIdentifier) solicitud.getSenderID();
        Mazo mazo = (Mazo) getBeliefbase().getBelief("mazo").getFact();
        ArrayList<Carta> cartas = new ArrayList<>();

        // Si turno 1 -> 6 Cartas
        // Si turno 6 -> 4 Cartas
        // Refuse
        
        if ((int)getBeliefbase().getBelief("turno").getFact() == 1){
            for (int i = 0; i < 6; i ++){
                cartas.add(mazo.getCarta());
            }
            mesa.getJugador(idJugador).setMano(cartas);
            getBeliefbase().getBelief("mazo").setFact(mazo);
            getBeliefbase().getBelief("mesa").setFact(mesa);
            IMessageEvent msgsend0 = createMessageEvent("Inform_Dar_Cartas");
            DarCartas accion = new DarCartas();
            accion.setCartas(cartas);
            msgsend0.setContent(accion);
            msgsend0.getParameterSet(SFipa.RECEIVERS).addValue(target);
            System.out.println("[INFO] Se han entregado 6 cartas al jugador con id " + target);
            sendMessage(msgsend0);
        }else if ((int)getBeliefbase().getBelief("turno").getFact() == 6){
            for (int i = 0; i < 4; i ++){
                cartas.add(mazo.getCarta());
            }
            mesa.getJugador(idJugador).setMano(cartas);
            getBeliefbase().getBelief("mazo").setFact(mazo);
            getBeliefbase().getBelief("mesa").setFact(mesa);
            IMessageEvent msgsend1 = createMessageEvent("Inform_Dar_Cartas");
            DarCartas accion = new DarCartas();
            accion.setCartas(cartas);
            msgsend1.setContent(accion);
            msgsend1.getParameterSet(SFipa.RECEIVERS).addValue(target);
            System.out.println("[INFO] Se han entregado 4 cartas al jugador con id " + target);
            sendMessage(msgsend1);
        }else{
            System.out.println("[RECHAZADO] No es el tunro de recibir cartas");
            // Se rechaza la petición de acción del jugador
            DarCartas accion = new DarCartas();
            IMessageEvent respuesta = peticion.createReply("Refuse_Dar_Cartas", accion);
            sendMessage(respuesta);
        }
    }
}