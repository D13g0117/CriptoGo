package jugador.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.concepts.*;



public class RecibirCartas extends Plan
{
	public void body()
	{
        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        DarCartas darCartas = new DarCartas();
        ArrayList<Carta> nuevasCartas = new ArrayList<>();
        darCartas = (DarCartas) peticion.getContent();
        nuevasCartas = darCartas.getCartas();
        Jugador jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();

        if (nuevasCartas.size() == 6){
            jugador.setMano(nuevasCartas);
            getBeliefbase().getBelief("jugador").setFact(jugador);
        }else{
            ArrayList<Carta> aux = new ArrayList<>();
            aux.add(new Carta("2", 1, 1));
            aux = jugador.getMano();
            aux.addAll(nuevasCartas);
            jugador.setMano(aux);
            getBeliefbase().getBelief("jugador").setFact(jugador);
        }
        System.out.println("[PLAN] Jugador con id " + getAgentIdentifier() + " ha recibido las cartas");
    }
}