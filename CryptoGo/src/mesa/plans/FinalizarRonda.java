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

public class FinalizarRonda extends Plan
{
	public void body()
	{
        Mesa mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
        int [] puntos = new int [mesa.getJugadores().size()];
        

        //Puntos de cada jugador
        for (int i = 0;  i < mesa.getJugadores().size(); i++){
            puntos[i] = 0;
            ArrayList<ArrayList<Carta>> criptoKits = (ArrayList) mesa.getJugadores().get(i).getSeleccion().getCriptokits().clone();
            System.out.println("El jugador con id " + mesa.getJugadores().get(i).getIdAgente() + " ha formado " + criptoKits.size() + " Cripto Kits:");
            for (int j = 0; j < criptoKits.size(); j++){
                puntos[i] = puntos[i] + getPuntos(criptoKits.get(j));
                for (int k = 0; k < criptoKits.get(j).size(); k++){
                    System.out.print(criptoKits.get(j).get(k).Mostrar());
                }
                System.out.println();
            }
            System.out.println("Puntuacion obtenida: " + puntos[i]);
            System.out.println();
        }

        int indiceDelMayor = 0;
		for (int x = 1; x < puntos.length; x++) {
			if (puntos[x] > puntos[indiceDelMayor]) {
				indiceDelMayor = x;
			}
        }
        
        System.out.println("GANADOR: " + mesa.getJugadores().get(indiceDelMayor).getIdAgente());

    }

    public int getPuntos (ArrayList<Carta> criptoKit){
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
}
