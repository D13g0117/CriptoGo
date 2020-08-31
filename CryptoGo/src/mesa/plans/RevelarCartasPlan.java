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

public class RevelarCartasPlan extends Plan
{
	public void body()
	{

        IMessageEvent peticion = (IMessageEvent) getInitialEvent();
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        AgentIdentifier idJugador = (AgentIdentifier) peticion.getParameter("sender").getValue();
        SeleccionarCarta seleccion = (SeleccionarCarta) peticion.getContent();
        Carta cartaSeleccionada = seleccion.getCartaSeleccionada();
        
        int cartasRevelar = (int) getBeliefbase().getBelief("cartasRevelar").getFact();

        //Hay que actualizar la mano
        

        for (int i = 0; i < mesa.getJugadores().size(); i++){
            if (mesa.getJugadores().get(i).getIdAgente().equals(idJugador)){
                mesa.getJugadores().get(i).getSeleccion().getCartasSeleccionadas().add(cartaSeleccionada);
                //Recorre la mano del jugador y eliminar la carta seleccionada
                for (int j = 0; j < mesa.getJugadores().get(i).getMano().size(); j++){
                    if (mesa.getJugadores().get(i).getMano().get(j).Mostrar().equals(cartaSeleccionada.Mostrar())){
                        mesa.getJugadores().get(i).getMano().remove(j);
                        break;
                    }
                }          
            }
        }
        
        getBeliefbase().getBelief("mesa").setFact(mesa);
        getBeliefbase().getBelief("siguienteJugador").setFact(true);
        
        // DEBE ESPERAR A QUE TODOS LOS JUGADORES HAYAN SELECCIONADO
        //try {
        //    TimeUnit.SECONDS.sleep(2);
        //} catch (Exception e) {
        //    System.out.println(e);
        //}

        IMessageEvent msgsend = createMessageEvent("Inform_Carta_Seleccionada");       
        SeleccionarCarta accion = new SeleccionarCarta();   
        msgsend.setContent(accion);
        msgsend.getParameterSet(SFipa.RECEIVERS).addValue(idJugador);
        System.out.println("[INFO] Carta seleccionada por el jugador con id " + idJugador + ": " + cartaSeleccionada.Mostrar());

        sendMessage(msgsend);
    }
}