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
    Mesa mesaObjeto = new Mesa();
    ArrayList<Carta> mano = new ArrayList<>();
    ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();

	public void body()
	{

        jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
        jugador.setEstrategia((int) getBeliefbase().getBelief("estrategia").getFact());
        mesaObjeto = (Mesa) getBeliefbase().getBelief("mesa_objeto").getFact();
        AgentIdentifier mesa = (AgentIdentifier) getBeliefbase().getBelief("mesa").getFact();
        ArrayList<Carta> mano = jugador.getMano();
        ArrayList<Carta> cartasSeleccionadas = jugador.getSeleccion().getCartasSeleccionadas();
        int estrategia = (int) getBeliefbase().getBelief("estrategia").getFact();
        SeleccionarCarta seleccion = new SeleccionarCarta();
        getBeliefbase().getBelief("turno").setFact((int) getBeliefbase().getBelief("turno").getFact() + 1);

        // Agresiva
        if ((int) getBeliefbase().getBelief("estrategia").getFact() == 0){
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " opta por una estrategia 'Avanzada'");
            Carta cartaAux = new Carta();
            //Estrategia
            if((int) getBeliefbase().getBelief("turno").getFact() - 1 == 1){
                cartaAux = primeraFase(mano);
                mano.remove(cartaAux);
            }else if((int) getBeliefbase().getBelief("turno").getFact() - 1 == 2){
                cartaAux = segundaFaseAvanzada(mano, cartasSeleccionadas);
                mano.remove(cartaAux);
            }else{
                cartaAux = terceraFaseAvanzada(mano, cartasSeleccionadas);
                mano.remove(cartaAux);
            }

            //Actualizar informacion
            seleccion.setCartaSeleccionada(cartaAux);
            jugador.setMano(mano);
            jugador.getSeleccion().getCartasSeleccionadas().add(seleccion.getCartaSeleccionada());
            jugador.setEstrategia(estrategia);
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
            System.out.println("[PLAN] El jugador con id " + jugador.getIdAgente() + " opta por una estrategia 'Estandar'");
            Carta cartaAux = new Carta();
            
            //Estrategia
            if((int) getBeliefbase().getBelief("turno").getFact() - 1 == 1){
                cartaAux = primeraFase(mano);
                mano.remove(cartaAux);
            }else if((int) getBeliefbase().getBelief("turno").getFact() - 1 == 2){
                cartaAux = segundaFase(mano, cartasSeleccionadas);
                mano.remove(cartaAux);
            }else{
                cartaAux = terceraFase(mano, cartasSeleccionadas);
                mano.remove(cartaAux);
            }

            //Actualizar informacion
            seleccion.setCartaSeleccionada(cartaAux);
            jugador.setMano(mano);
            jugador.setEstrategia(estrategia);
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
            Carta cartaAux = new Carta();
            
            int numeroAleatorio = (int) (Math.random()*mano.size());
            cartaAux = mano.remove(numeroAleatorio);
            

            //Actualizar informacion
            seleccion.setCartaSeleccionada(cartaAux);
            jugador.setMano(mano);
            jugador.setEstrategia(estrategia);
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

    //Devuelve la carta del color dado en la lista de cartas dada
    public Carta getColor(int color, ArrayList<Carta> cartas){
        Carta cartaColor = new Carta();
        ArrayList<Carta> cartasAux = (ArrayList) cartas.clone(); 

        for(int i = 0; i < cartasAux.size(); i++){
            if(cartasAux.get(i).getTipo() == color){
                return cartaColor = cartasAux.get(i);
            }
        }
        return null;
    }

    public Carta primeraFase(ArrayList<Carta> manoParametro){
        //Esta fase solo se ejecuta cuando no hay ninguna carta seleccionada
        //0 - SC | 1 - BC | 2 - H | 3 - OM | 4 - AE | 5 - MAC
        Carta cartaSeleccionada = new Carta();
        ArrayList<Carta> manoAux = (ArrayList) manoParametro.clone();

        //Orden de seleccion de cartas
        cartaSeleccionada = getColor(4, manoAux);  //VERDE
        if (cartaSeleccionada == null) cartaSeleccionada = getColor(1, manoAux); //ROSA
        if (cartaSeleccionada == null) cartaSeleccionada = getColor(5, manoAux); //AZUL
        if (cartaSeleccionada == null) cartaSeleccionada = getColor(0, manoAux); //ROJA
        if (cartaSeleccionada == null) cartaSeleccionada = getColor(2, manoAux); //NARANJA
        if (cartaSeleccionada == null) cartaSeleccionada = getColor(3, manoAux); //AMARILLA
        
        return cartaSeleccionada;
    }

    public Carta segundaFase(ArrayList<Carta> manoParametro, ArrayList<Carta> seleccionParametro){
        //Esta fase solo se ejecuta cuando hay una carta seleccionada
        //0 - SC | 1 - BC | 2 - H | 3 - OM | 4 - AE | 5 - MAC
        Carta cartaSeleccionada = new Carta();
        ArrayList<Carta> manoAux = (ArrayList) manoParametro.clone();
        ArrayList<Carta> seleccionAux = (ArrayList) seleccionParametro.clone();
        int numeroAleatorio = 0;
        boolean verde = false, rosa = false, azul = false;

        for (int i = 0; i < seleccionAux.size(); i++){
            if (seleccionAux.get(i).getTipo() == 4) verde = true;
            if (seleccionAux.get(i).getTipo() == 1) rosa = true;
            if (seleccionAux.get(i).getTipo() == 5) azul = true;
        }

        if (verde){ //VERDE
            cartaSeleccionada = getColor(1, manoAux); //ROSA
            if (cartaSeleccionada == null){
                numeroAleatorio = (int) (Math.random()*manoAux.size());
                cartaSeleccionada = manoAux.get(numeroAleatorio);
            }
        } else if (rosa){ //ROSA
            cartaSeleccionada = getColor(4, manoAux); //VERDE
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(5, manoAux); //AZUL
            }  
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(3, manoAux); //AMARILLO
            }
            if (cartaSeleccionada == null){
                numeroAleatorio = (int) (Math.random()*manoAux.size());
                cartaSeleccionada = manoAux.get(numeroAleatorio);
            }
        }else if (azul){ //AZUL
            cartaSeleccionada = getColor(1, manoAux); //ROSA
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(0, manoAux); //ROJO
            }  
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(2, manoAux); //NARANJA
            }
            if (cartaSeleccionada == null){
                numeroAleatorio = (int) (Math.random()*manoAux.size());
                cartaSeleccionada = manoAux.get(numeroAleatorio);
            }
        }else{
            numeroAleatorio = (int) (Math.random()*manoAux.size());
            cartaSeleccionada = manoAux.get(numeroAleatorio);
        }
        
        return cartaSeleccionada;
    }

    public Carta segundaFaseAvanzada(ArrayList<Carta> manoParametro, ArrayList<Carta> seleccionParametro){
        //Esta fase solo se ejecuta cuando hay una carta seleccionada
        //0 - SC | 1 - BC | 2 - H | 3 - OM | 4 - AE | 5 - MAC
        Carta cartaSeleccionada = new Carta();
        ArrayList<Carta> manoAux = (ArrayList) manoParametro.clone();
        ArrayList<Carta> seleccionAux = (ArrayList) seleccionParametro.clone();
        int numeroAleatorio = 0;
        boolean verde = false, rosa = false, azul = false;

        for (int i = 0; i < seleccionAux.size(); i++){
            if (seleccionAux.get(i).getTipo() == 4) verde = true;
            if (seleccionAux.get(i).getTipo() == 1) rosa = true;
            if (seleccionAux.get(i).getTipo() == 5) azul = true;
        }
        if (verde){ //VERDE
            cartaSeleccionada = getColor(1, manoAux); //ROSA
            if (cartaSeleccionada == null){
                cartaSeleccionada = verCartasRival(manoAux);
            }
        } else if (rosa){ //ROSA
            cartaSeleccionada = getColor(4, manoAux); //VERDE
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(5, manoAux); //AZUL
            }  
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(3, manoAux); //AMARILLO
            }
            if (cartaSeleccionada == null){
                cartaSeleccionada = verCartasRival(manoAux);
            }
        }else if (azul){ //AZUL
            cartaSeleccionada = getColor(1, manoAux); //ROSA
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(0, manoAux); //ROJO
            }  
            if (cartaSeleccionada == null){
                cartaSeleccionada = getColor(2, manoAux); //NARANJA
            }
            if (cartaSeleccionada == null){
                cartaSeleccionada = verCartasRival(manoAux);
            }
        }else{
            cartaSeleccionada = verCartasRival(manoAux);
        }
        
        return cartaSeleccionada;
    }

    public Carta terceraFase(ArrayList<Carta> manoParametro, ArrayList<Carta> seleccionParametro){
    
        ArrayList<Carta> cartasNoCriptoKit = new ArrayList<>();
        ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        ArrayList<ArrayList<Carta>> cartasAux = new ArrayList<>();
        Carta cartaSeleccionada = new Carta();
        ArrayList<Carta> manoAux = (ArrayList) manoParametro.clone();
        ArrayList<Carta> seleccionAux = (ArrayList) seleccionParametro.clone();
        criptoKits = (ArrayList) jugador.getSeleccion().getCriptokits().clone();
        cartasNoCriptoKit = (ArrayList) seleccionAux.clone();
        for (int i = 0; i < criptoKits.size(); i++){
            cartasNoCriptoKit.removeAll(criptoKits.get(i));
        }

        int numeroAleatorio = (int) (Math.random()*manoAux.size());
        cartaSeleccionada = manoAux.get(numeroAleatorio);
        //Se recorren las cartas de la mano en busca de posibles combinaciones de CriptoKits
        for (int i = 0; i < manoAux.size(); i++){
            cartasNoCriptoKit.add(manoAux.get(i));
            cartasAux = (ArrayList) getCriptokits(cartasNoCriptoKit).clone();
            if(cartasAux.size() > 0){
                cartaSeleccionada = manoAux.get(i);
                break;
            }
            cartasNoCriptoKit.remove(manoAux.get(i));
        }

        return cartaSeleccionada;

    }

    public Carta terceraFaseAvanzada(ArrayList<Carta> manoParametro, ArrayList<Carta> seleccionParametro){
    
        ArrayList<Carta> cartasNoCriptoKit = new ArrayList<>();
        ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        ArrayList<ArrayList<Carta>> cartasAux = new ArrayList<>();
        Carta cartaSeleccionada = new Carta();
        ArrayList<Carta> manoAux = (ArrayList) manoParametro.clone();
        ArrayList<Carta> seleccionAux = (ArrayList) seleccionParametro.clone();
        criptoKits = (ArrayList) jugador.getSeleccion().getCriptokits().clone();
        cartasNoCriptoKit = (ArrayList) seleccionAux.clone();
        for (int i = 0; i < criptoKits.size(); i++){
            cartasNoCriptoKit.removeAll(criptoKits.get(i));
        }
        cartaSeleccionada = verCartasRival(manoAux);
        //Se recorren las cartas de la mano en busca de posibles combinaciones de CriptoKits
        for (int i = 0; i < manoAux.size(); i++){
            cartasNoCriptoKit.add(manoAux.get(i));
            cartasAux = (ArrayList) getCriptokits(cartasNoCriptoKit).clone();
            if(cartasAux.size() > 0){
                cartaSeleccionada = manoAux.get(i);
                break;
            }
            cartasNoCriptoKit.remove(manoAux.get(i));
        }

        return cartaSeleccionada;

    }

    //Se selecciona la carta que beneficia al rival para que este no pueda formar criptokits
    public Carta verCartasRival(ArrayList<Carta> manoParam){
        ArrayList<Carta> cartasRival = new ArrayList<>();
        ArrayList<Carta> manoAux = new ArrayList<>();
        ArrayList<Carta> cartasNoCriptoKit = new ArrayList<>();
        ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        ArrayList<ArrayList<Carta>> cartasAux = new ArrayList<>();
        ArrayList<Carta> manoSiguienteJugador = new ArrayList<>();
        Carta cartaSeleccionada = new Carta();
        manoAux = (ArrayList) manoParam.clone();

        //Obtener la seleccion del siguiente jugador
        //Obtener los criptokits del siguiente jugador
        int indiceJugador = mesaObjeto.getJugadores().indexOf(jugador);
        if (indiceJugador == mesaObjeto.getJugadores().size() - 1){
            manoSiguienteJugador = mesaObjeto.getJugadores().get(0).getSeleccion().getCartasSeleccionadas();
            criptoKits = (ArrayList) mesaObjeto.getJugadores().get(0).getSeleccion().getCriptokits().clone();
        }else{
            manoSiguienteJugador = (ArrayList) mesaObjeto.getJugadores().get(indiceJugador + 1).getSeleccion().getCartasSeleccionadas().clone();
            criptoKits = (ArrayList) mesaObjeto.getJugadores().get(indiceJugador + 1).getSeleccion().getCriptokits().clone();
        }   
        //Obtener las cartas del siguiente jugador que no forman criptokits    
        cartasNoCriptoKit = (ArrayList) manoSiguienteJugador.clone();
        for (int i = 0; i < criptoKits.size(); i++){
            cartasNoCriptoKit.removeAll(criptoKits.get(i));
        }

        int numeroAleatorio = (int) (Math.random()*manoAux.size());
        cartaSeleccionada = manoAux.get(numeroAleatorio);
        //Se recorren las cartas de la mano en busca de posibles combinaciones de CriptoKits con la seleccion del siguiente jugador
        for (int i = 0; i < manoAux.size(); i++){
            cartasNoCriptoKit.add(manoAux.get(i));
            cartasAux = (ArrayList) getCriptokits(cartasNoCriptoKit).clone();
            if(cartasAux.size() > 0){
                cartaSeleccionada = manoAux.get(i);
                break;
            }
            cartasNoCriptoKit.remove(manoAux.get(i));
        }
        return cartaSeleccionada;
    }
    

    public ArrayList<ArrayList<Carta>> getCriptokits(ArrayList<Carta> cartasSeleccionadas) {
        
        //0 - SC | 1 - BC | 2 - H | 3 - OM | 4 - AE | 5 - MAC
        //Las cartas de cripto kits son aquellas con las que es posibles formar algun cripto kit

        ArrayList<Carta> cartasSeleccionadasAux = new ArrayList<>();
        cartasSeleccionadasAux.addAll(cartasSeleccionadas);
        ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        ArrayList<ArrayList<Carta>> posiblesCriptoKits = new ArrayList<>();

        posiblesCriptoKits = getPosiblesCriptoKits(cartasSeleccionadasAux);

        while (!posiblesCriptoKits.isEmpty()){
            ArrayList<Carta> mejorCriptokit = new ArrayList<>();
            mejorCriptokit.addAll(getMejorCriptoKit(posiblesCriptoKits));
            ArrayList<Carta> mejorCriptokitAux = new ArrayList<>();
            mejorCriptokitAux = (ArrayList) mejorCriptokit.clone();
            criptoKits.add(mejorCriptokitAux);
            cartasSeleccionadasAux = eliminarGanador(cartasSeleccionadasAux, mejorCriptokit);
            posiblesCriptoKits = new ArrayList<>();
            posiblesCriptoKits.clear();
            posiblesCriptoKits.addAll(getPosiblesCriptoKits(cartasSeleccionadasAux));
            mejorCriptokit.clear();
        }
        return criptoKits;
    }


    public int getPuntos(ArrayList<Carta> criptoKit) {
        int puntos = 16;
        boolean perfecto = true;
        //0 - Bajo | 1 - Medio | 2 - Alto
        for (int i = 0; i < criptoKit.size(); i++){
            if (criptoKit.get(i).getSeguridad() == 0){
                puntos = puntos - 4;
                perfecto = false;
            } 
            else if (criptoKit.get(i).getSeguridad() == 1){
                puntos = puntos - 2;
                perfecto = false;
            } 
        }
        if (perfecto) puntos = puntos + 4;
        return puntos;
    }

    public ArrayList<ArrayList<Carta>> getPosiblesCriptoKits(ArrayList<Carta> cartasSeleccionadasAux1){
        ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        ArrayList<Carta> cartasCriptoKits = new ArrayList<>();
        for (int i = 0; i < cartasSeleccionadasAux1.size(); i++){
            if(cartasSeleccionadasAux1.get(i).getTipo() == 1){ //ROSA
                for (int j = 0; j < cartasSeleccionadasAux1.size(); j++){
                    if(cartasSeleccionadasAux1.get(j).getTipo() == 4){ //VERDE
                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(i));
                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(j));
                        criptoKits.add(cartasCriptoKits); //CK1
                        cartasCriptoKits = new ArrayList<>();
                    }
                    if(cartasSeleccionadasAux1.get(j).getTipo() == 5){ //AZUL
                        for (int k = 0; k < cartasSeleccionadasAux1.size(); k++){
                            if(cartasSeleccionadasAux1.get(k).getTipo() == 0){ //ROJA
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(i));
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(j));
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(k));
                                criptoKits.add(cartasCriptoKits); //CK2A
                                cartasCriptoKits = new ArrayList<>();
                            }
                            if(cartasSeleccionadasAux1.get(k).getTipo() == 1 && k != i){ //ROSA
                                for (int l = 0; l < cartasSeleccionadasAux1.size(); l++){
                                    if(cartasSeleccionadasAux1.get(l).getTipo() == 3){ //AMARILLA
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(i));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(j));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(k));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(l));
                                        criptoKits.add(cartasCriptoKits); //CK2C
                                        cartasCriptoKits = new ArrayList<>();
                                     }
                                }                           
                            }
                            if(cartasSeleccionadasAux1.get(k).getTipo() == 2){ //NARANJA
                                for (int m = 0; m < cartasSeleccionadasAux1.size(); m++){
                                    if(cartasSeleccionadasAux1.get(m).getTipo() == 3){ //AMARILLA
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(i));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(j));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(k));
                                        cartasCriptoKits.add(cartasSeleccionadasAux1.get(m));
                                        criptoKits.add(cartasCriptoKits); //CK2D
                                        cartasCriptoKits = new ArrayList<>();
                                     }
                                }
                            }
                        }
                    }
                }
            }
            if (cartasSeleccionadasAux1.get(i).getTipo() == 0){ //ROJO
                for (int n = 0; n < cartasSeleccionadasAux1.size(); n++){
                    if(cartasSeleccionadasAux1.get(n).getTipo() == 5){ //AZUL
                        for (int o = 0; o < cartasSeleccionadasAux1.size(); o++){
                            if(cartasSeleccionadasAux1.get(o).getTipo() == 2){ //NARANJA
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(i));
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(n));
                                cartasCriptoKits.add(cartasSeleccionadasAux1.get(o));
                                criptoKits.add(cartasCriptoKits); //CK2B
                                cartasCriptoKits = new ArrayList<>();
                            }
                        }
                     }
                }
            }
        }
        return criptoKits;
    }

    public ArrayList<Carta> getMejorCriptoKit(ArrayList<ArrayList<Carta>> posiblesCriptoKits){
        ArrayList<Carta> ganador = new ArrayList<>();
        ganador = posiblesCriptoKits.get(0);
        if (posiblesCriptoKits.size() > 1){
            
            for (int i = 0; i < posiblesCriptoKits.size(); i ++){
                if(getPuntos(ganador) < getPuntos(posiblesCriptoKits.get(i))){
                    ganador = posiblesCriptoKits.get(i);
                }
            }
        }
        return ganador;
    }

    public ArrayList<Carta> eliminarGanador (ArrayList<Carta> cartasSeleccionadasLocal, ArrayList<Carta> ganador){
        ArrayList<Carta> cartasSeleccionadasLocal1 = new ArrayList<>();
        cartasSeleccionadasLocal1.addAll(cartasSeleccionadasLocal);
        for (int i = 0; i < ganador.size(); i ++){
            for (int j = 0; j < cartasSeleccionadasLocal1.size(); j ++){
                if (ganador.get(i) == (cartasSeleccionadasLocal1.get(j))){
                    cartasSeleccionadasLocal1.remove(j);
                    break;
                }
            }
        }
        return cartasSeleccionadasLocal1;
    }

    public void imprimirCriptokits(ArrayList<ArrayList<Carta>> criptoKits){
        ArrayList<ArrayList<Carta>> criptokitsLocal = new ArrayList<>();
        criptokitsLocal.addAll(criptoKits);
        for (int i = 0; i < criptokitsLocal.size(); i ++){
            for (int j = 0; j < criptokitsLocal.get(i).size(); j ++){
                System.out.print(criptokitsLocal.get(i).get(j).Mostrar());
                }
                System.out.println();
            }
    }

    public void imprimirCartas(ArrayList<Carta> cartas){
        ArrayList<Carta> cartasAux = (ArrayList) cartas.clone();
        for (int i = 0; i < cartasAux.size(); i ++){
            System.out.print(cartasAux.get(i).Mostrar());
        }
        System.out.println();
    }


}