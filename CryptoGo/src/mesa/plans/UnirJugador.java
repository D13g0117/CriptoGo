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


public class UnirJugador extends Plan
{
	public void body()
	{
        System.out.println("[PLAN] La mesa recibe peticion de unirse a la partida");
        // Mesa
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        // Parámetros de la peticion
        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        AgentIdentifier idJugador = (AgentIdentifier) peticion.getParameter("sender").getValue();
        UnirsePartida accion = (UnirsePartida) peticion.getContent();

        if ((boolean) getBeliefbase().getBelief("empezar").getFact() == false) {
            System.out.println("[FALLO] Ya se ha empezado la partida");
            // Se rechaza la petición de acción del jugador
            IMessageEvent respuesta = peticion.createReply("Failure_Unirse_Partida", accion);
            sendMessage(respuesta);
            return;
        }

        // Se inicializa la lista de jugadores en caso de que sea el primer jugador que se une
        if (mesa.getJugadores() == null) {
            mesa.setJugadores(new ArrayList<Jugador>());
        }   
        // Si el jugador ya está unido
        else if (mesa.getJugador(idJugador) != null) {
          System.out.println("[RECHAZADO] El jugador con id " + idJugador + " ya esta unido a la partida");
          // Se rechaza la petición de acción del jugador
          IMessageEvent respuesta = peticion.createReply("Refuse_Unirse_Partida", accion);
          sendMessage(respuesta);
          return;
        }
        // Si hay 8 o más jugadores
        if ((int) getBeliefbase().getBelief("numJugadores").getFact() >= 8){
          System.out.println("[RECHAZADO] Maximo de 8 jugadores alcanzado");
          // Se rechaza la petición de acción del jugador
          IMessageEvent respuesta = peticion.createReply("Refuse_Unirse_Partida", accion);
          sendMessage(respuesta);
          return;
        }
        // Se añade el jugador a la mesa
        System.out.println("[INFO] Se une a la partida el jugador con id " + idJugador);
        if ((int) getBeliefbase().getBelief("numJugadores").getFact() >= 1 ){
          System.out.println("\n[INFO] La partida comenzara en breves. Esperando otros  jugadores ...\n");
        } else{
          System.out.println("\n[INFO] Esperando otros  jugadores ...\n");
        }
        
        Jugador jugador = new Jugador();
        jugador.setIdAgente(idJugador);
        mesa.getJugadores().add(jugador);

        // Se actualiza en la base de creencias el hecho mesa
        getBeliefbase().getBelief("mesa").setFact(mesa);
        getBeliefbase().getBelief("numJugadores").setFact(((int) getBeliefbase().getBelief("numJugadores").getFact()) + 1);
        // Se informa de que el jugador se ha unido
        IMessageEvent respuesta = peticion.createReply("Inform_Jugador_Unido", new JugadorUnido());
        sendMessage(respuesta);
    }

}