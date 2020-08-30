package jugador.plans;

import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.actions.SeleccionarCarta;
import ontology.concepts.*;

public class SustituirCartaPlan extends Plan
{
    /*
    Escoger una carta de el conjunto de cartas seleccionadas.
        - Seleccionar una carta que no forme ningun criptoKit
        - En caso de que no se forme ningun criptoKit seleccionar una carta aleatoria
    */

    Jugador jugador = new Jugador();
    SustituirCarta sustitucion = new SustituirCarta();

	public void body()
    {
        ArrayList<Carta> mano = new ArrayList<>();
        ArrayList<Carta> selecion = new ArrayList<>();
    
        jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        mano = jugador.getMano();
        selecion = jugador.getSeleccion().getCartasSeleccionadas();


        if (mano.size() < 4){
            getBeliefbase().getBelief("sustituir").setFact(false);
        }else{

        
        sustitucion = DecidirSustitucion();


        // Si no hay carta a sustituir, no se hace nada
        if (!sustitucion.getCartaASustituir().Mostrar().equals("[ null, Baja, SC ]")){
            //Sustitucion
            for (int i = 0; i < mano.size(); i++) {
                if (mano.get(i).equals(sustitucion.getCartaSustituta())){
                    for (int j = 0; j < selecion.size(); j++){
                        if (selecion.get(j).equals(sustitucion.getCartaASustituir())){
                            mano.remove(i);
                            jugador.getSeleccion().getCartasSeleccionadas().remove(j);
                            jugador.getSeleccion().getCartasSeleccionadas().add(sustitucion.getCartaSustituta());
                            getBeliefbase().getBelief("jugador").setFact(jugador);
                            break;
                        }
                    }
                    break;
                }  
            }
            sustitucion.setJugador(jugador);
            AgentIdentifier mesa = (AgentIdentifier) getBeliefbase().getBelief("mesa").getFact();
            System.out.println("[PLAN] Jugador con id: " + jugador.getIdAgente() + " quiere sustituir la carta " + sustitucion.getCartaASustituir().Mostrar() + " por la carta " + sustitucion.getCartaSustituta().Mostrar());
            IMessageEvent respuesta = createMessageEvent("Request_Solicitar_Cartas");
            respuesta.setContent(sustitucion);
            respuesta.getParameterSet(SFipa.RECEIVERS).addValue(mesa);
            sendMessage(respuesta);

         }else{
            getBeliefbase().getBelief("sustituir").setFact(false);
         }
        }
    }

    public SustituirCarta DecidirSustitucion(){
        
        SustituirCarta sustitucionAux = new SustituirCarta();
        ArrayList<Carta> mano = new ArrayList<>();
        Carta cartaASustituir = new Carta();
        Carta cartaSustituta = new Carta();

        ArrayList<ArrayList<Carta>> criptoKits = jugador.getSeleccion().getCriptokits();

        // SELECCION DE CARTA A SUSTITUIR
        if(!criptoKits.isEmpty()){
            ArrayList<Carta> cartasNoCriptoKit = new ArrayList<>();
            cartasNoCriptoKit = (ArrayList) jugador.getSeleccion().getCartasSeleccionadas().clone();
            for (int i = 0; i < criptoKits.size(); i++){
                cartasNoCriptoKit.removeAll(criptoKits.get(i));
            }
            if(!cartasNoCriptoKit.isEmpty()){
                int aleatorio = (int) (Math.random()*cartasNoCriptoKit.size());
                cartaASustituir = cartasNoCriptoKit.get(aleatorio);
            }    
        }else{
            int aleatorio = (int) (Math.random()*jugador.getSeleccion().getCartasSeleccionadas().size());
            cartaASustituir = jugador.getSeleccion().getCartasSeleccionadas().get(aleatorio);
        }

        //SELECCION DE CARTA SUSTITUTA
        mano = jugador.getMano();
        // Agresiva
        if ((int) getBeliefbase().getBelief("estrategia").getFact() == 0){
            int numeroAleatorio = (int) (Math.random()*mano.size());
            cartaSustituta = mano.get(numeroAleatorio);
        // Defensiva
        } else if ((int) getBeliefbase().getBelief("estrategia").getFact() == 1){
            int numeroAleatorio = (int) (Math.random()*mano.size());
            cartaSustituta = mano.get(numeroAleatorio);
        // Aleatoria
        } else{
            int numeroAleatorio = (int) (Math.random()*mano.size()); 
            cartaSustituta = mano.get(numeroAleatorio);
        }
        
        
        sustitucionAux.getSustitucion().add(cartaASustituir);
        sustitucionAux.getSustitucion().add(cartaSustituta);
        
        return sustitucionAux;
    }
}