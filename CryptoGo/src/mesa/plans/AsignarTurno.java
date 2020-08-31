package mesa.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;
import ontology.actions.UnirsePartida;
import ontology.concepts.Jugador;
import ontology.concepts.Carta;
import ontology.concepts.Mesa;
import ontology.predicates.JugadorUnido;
import ontology.predicates.TurnoAsignado;


public class AsignarTurno extends Plan
{
	public void body()
	{

        // Para mejor sincronizacion
        try {   
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        
        AgentIdentifier jugadorActual = (AgentIdentifier) getBeliefbase().getBelief("jugadorActual").getFact();
        Jugador siguienteJugador = new Jugador();

        getBeliefbase().getBelief("siguienteJugador").setFact(false);



        for (int i = 0; i < mesa.getJugadores().size(); i++){
            if (mesa.getJugadores().get(i).getIdAgente().equals(jugadorActual)){

                // Ultimo jugador
                if (i == mesa.getJugadores().size() - 1){

                    int p = 0;
                    //Cambiar mano a la IZQ
                    if((int)getBeliefbase().getBelief("turno").getFact() < 6){
                        ArrayList<Carta> primera = new ArrayList<>();
                        primera = (ArrayList) mesa.getJugadores().get(0).getMano().clone();
                        for(p = 0; p < mesa.getJugadores().size() - 1; p++){
                            mesa.getJugadores().get(p).setMano(mesa.getJugadores().get(p + 1).getMano());
                        }
                        mesa.getJugadores().get(p).setMano(primera);
                        System.out.println("[INFO] Cambio de manos al jugador de la izquierda");
                    //Cambiar mano a la DCHA
                    }else{
                        ArrayList<Carta> ultima = new ArrayList<>();
                        ultima = (ArrayList) mesa.getJugadores().get(mesa.getJugadores().size() - 1).getMano().clone();
                        for(p = mesa.getJugadores().size() - 1; p > 0; p--){
                            mesa.getJugadores().get(p).setMano(mesa.getJugadores().get(p - 1).getMano());
                        }
                        mesa.getJugadores().get(p).setMano(ultima);
                        System.out.println("[INFO] Cambio de manos al jugador de la derecha");
                    }

                
                    siguienteJugador = mesa.getJugadores().get(0);

                    for (int j = 1; j < mesa.getJugadores().size(); j++){
                        // Si es el primer turno, la mano no estará inicializada
                        if (siguienteJugador.getMano() != null){
                            // Si la mano del siguiente jugador esta vacia, se pasa al siguiente jugador de la lista
                            if(siguienteJugador.getMano().size() == 0){
                                siguienteJugador = mesa.getJugadores().get(j);
                            }
                        }
                    }
                    // Ultimo Turno de la FASE 1
                    if ((int) getBeliefbase().getBelief("turno").getFact() == 5){
                        System.out.println("\n ---------------");
                        System.out.println("| [INFO] FASE 2 |");
                        System.out.println(" ---------------");
                    }

                    //Si la mano del siguiente jugador esta vacia significa que ya no quedan jugadores con cartas
                    if (siguienteJugador.getMano() != null){
                        // Si la mano del siguiente jugador esta vacia, se pasa al siguiente jugador de la lista
                        if(siguienteJugador.getMano().size() == 0){
                            //Parar ejecucion y lanzar el plan final
                            System.out.println("\n ---------------");
                            System.out.println("| [INFO] FIN DE RONDA |");
                            System.out.println(" ---------------");
                            getBeliefbase().getBelief("finRonda").setFact(true);
                            return;
                        }
                    }
                    



                    getBeliefbase().getBelief("turno").setFact((int) getBeliefbase().getBelief("turno").getFact() + 1);
                    getBeliefbase().getBelief("jugadorActual").setFact(siguienteJugador.getIdAgente());
                    System.out.println("\n[INFO] TURNO " + getBeliefbase().getBelief("turno").getFact());
                    
                    IMessageEvent respuesta = createMessageEvent("Inform_Turno_Asignado");
                    TurnoAsignado predicado = new TurnoAsignado();
                    predicado.setJugador(siguienteJugador);
                    predicado.setMesa(mesa);
                    System.out.println("\n[INFO] Decide el jugador con id " + siguienteJugador.getIdAgente() + "\n");
                    respuesta.setContent(predicado);
                    respuesta.getParameterSet(SFipa.RECEIVERS).addValue(siguienteJugador.getIdAgente());
                    sendMessage(respuesta);
                    break;
                }else{

                    siguienteJugador = mesa.getJugadores().get(i + 1);
                    
                    for (int j = i + 1; j < mesa.getJugadores().size(); j++){
                        // Si es el primer turno, la mano no estará inicializada
                        if (siguienteJugador.getMano() != null){
                            // Si la mano del siguiente jugador esta vacia, se pasa al siguiente jugador de la lista
                            if(siguienteJugador.getMano().size() == 0){
                                siguienteJugador = mesa.getJugadores().get(j);
                            }
                        }
                    }


                    //Si la mano del siguiente jugador esta vacia significa que ya no quedan jugadores con cartas
                    if (siguienteJugador.getMano() != null){
                        // Si la mano del siguiente jugador esta vacia, se pasa al siguiente jugador de la lista
                        if(siguienteJugador.getMano().size() == 0){
                            //Parar ejecucion y lanzar el plan final
                            System.out.println("\n ---------------");
                            System.out.println("| [INFO] FIN DE RONDA |");
                            System.out.println(" ---------------");
                            getBeliefbase().getBelief("finRonda").setFact(true);
                            return;
                        }
                    }
                    
                    // Siguiente jugador
                    getBeliefbase().getBelief("jugadorActual").setFact(siguienteJugador.getIdAgente());
                    
                    IMessageEvent respuesta = createMessageEvent("Inform_Turno_Asignado");
                    TurnoAsignado predicado = new TurnoAsignado();
                    predicado.setJugador(siguienteJugador);
                    predicado.setMesa(mesa);
                    System.out.println("\n[INFO] Decide el jugador con id " + siguienteJugador.getIdAgente() + "\n");
                    respuesta.setContent(predicado);
                    respuesta.getParameterSet(SFipa.RECEIVERS).addValue(siguienteJugador.getIdAgente());
                    sendMessage(respuesta);
                    break;
                }
            }
        }
    }
}