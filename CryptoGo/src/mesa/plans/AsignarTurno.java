package mesa.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;
import ontology.actions.UnirsePartida;
import ontology.concepts.Jugador;
import ontology.concepts.Mesa;
import ontology.predicates.JugadorUnido;


public class AsignarTurno extends Plan
{
	public void body()
	{
        System.out.println("[PLAN] La mesa decide a quien le toca");
    }

}