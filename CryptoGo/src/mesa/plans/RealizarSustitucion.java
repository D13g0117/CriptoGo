package mesa.plans;

import java.util.*;
import java.util.concurrent.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.actions.SeleccionarCarta;
import ontology.concepts.*;
import ontology.predicates.CartaSustituida;

public class RealizarSustitucion extends Plan
{
	public void body()
	{

        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        AgentIdentifier idJugador = (AgentIdentifier) peticion.getParameter("sender").getValue();
        SustituirCarta sustitucion = (SustituirCarta) peticion.getContent();

        Jugador jugador = sustitucion.getJugador();
        ArrayList<Carta> mano = jugador.getMano();
        
        for (int i = 0; i < mesa.getJugadores().size(); i++){            
            if (mesa.getJugadores().get(i).getIdAgente().equals(jugador.getIdAgente())){
                mesa.getJugadores().set(i, jugador);
            }
        }

        getBeliefbase().getBelief("mesa").setFact(mesa);

        IMessageEvent msgsend = createMessageEvent("Inform_Carta_Sustituida");   
        CartaSustituida predicado = new CartaSustituida();
        msgsend.setContent(predicado);
        msgsend.getParameterSet(SFipa.RECEIVERS).addValue(idJugador);
        System.out.println("[INFO] Carta " + sustitucion.getCartaASustituir().Mostrar() + " ha sido sustituida por la carta " + sustitucion.getCartaSustituta().Mostrar() + " satisfactoriamente");
        sendMessage(msgsend);
    }
}
