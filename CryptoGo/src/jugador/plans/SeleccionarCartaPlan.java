package jugador.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.actions.SeleccionarCarta;
import ontology.concepts.*;

public class SeleccionarCartaPlan extends Plan
{

    Jugador jugador = new Jugador();

	public void body()
	{
        // Para mejor sincronizacion
        try {   
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e);
        }


        jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        AgentIdentifier mesa = (AgentIdentifier) getBeliefbase().getBelief("mesa").getFact();
        ArrayList<Carta> mano = jugador.getMano();
        int estrategia = (int) getBeliefbase().getBelief("estrategia").getFact();
        SeleccionarCarta seleccion = new SeleccionarCarta();
        getBeliefbase().getBelief("turno").setFact((int) getBeliefbase().getBelief("turno").getFact() + 1);

        // Agresiva
        if ((int) getBeliefbase().getBelief("estrategia").getFact() == 0){
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " opta por una estrategia 'Agresiva'");

            //Estrategia
            int numeroAleatorio = (int) (Math.random()*mano.size());
            seleccion.setCartaSeleccionada(mano.remove(numeroAleatorio));

            //Actualizar informacion
            jugador.setMano(mano);
            jugador.getSeleccion().getCartasSeleccionadas().add(seleccion.getCartaSeleccionada());
            getBeliefbase().getBelief("jugador").setFact(jugador);
            getBeliefbase().getBelief("turnoJugador").setFact(false);
            RobarCartas();
            //Enviar mensaje
            IMessageEvent msgsend = createMessageEvent("Request_Seleccionar_Carta");          
            msgsend.setContent(seleccion);
            msgsend.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " selecciona una carta de su mano");

            sendMessage(msgsend);
        // Defensiva
        } else if ((int) getBeliefbase().getBelief("estrategia").getFact() == 1){
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " opta por una estrategia 'Defensiva'");

            //Estrategia
            int numeroAleatorio = (int) (Math.random()*mano.size());
            seleccion.setCartaSeleccionada(mano.remove(numeroAleatorio));

            //Actualizar informacion
            jugador.setMano(mano);
            jugador.getSeleccion().getCartasSeleccionadas().add(seleccion.getCartaSeleccionada());
            getBeliefbase().getBelief("jugador").setFact(jugador);
            getBeliefbase().getBelief("turnoJugador").setFact(false);
            RobarCartas();
            //Enviar mensaje
            IMessageEvent msgsend = createMessageEvent("Request_Seleccionar_Carta");          
            msgsend.setContent(seleccion);
            msgsend.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " selecciona una carta de su mano");

            sendMessage(msgsend);        
        // Aleatoria
        } else{
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " opta por una estrategia 'Aleatoria'");

            //Estrategia
            int numeroAleatorio = (int) (Math.random()*mano.size());
            seleccion.setCartaSeleccionada(mano.remove(numeroAleatorio));

            //Actualizar informacion
            jugador.setMano(mano);
            jugador.getSeleccion().getCartasSeleccionadas().add(seleccion.getCartaSeleccionada());
            getBeliefbase().getBelief("jugador").setFact(jugador);
            getBeliefbase().getBelief("turnoJugador").setFact(false);
            RobarCartas();
            //Enviar mensaje
            IMessageEvent msgsend = createMessageEvent("Request_Seleccionar_Carta");          
            msgsend.setContent(seleccion);
            msgsend.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " selecciona una carta de su mano");

            sendMessage(msgsend);
        }
    }

    public void RobarCartas(){
        if (jugador.getNumCartas() < 2 && (int) getBeliefbase().getBelief("turno").getFact() < 7){
            getBeliefbase().getBelief("pocasCartas").setFact(true);
        }
    }
}