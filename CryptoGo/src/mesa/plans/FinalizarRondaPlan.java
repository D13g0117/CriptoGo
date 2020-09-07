package mesa.plans;

import java.io.*;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;

import ontology.actions.*;
import ontology.actions.SeleccionarCarta;
import ontology.concepts.*;

public class FinalizarRondaPlan extends Plan
{
    ArrayList<Jugador> empatados = new ArrayList<>();
    int puntosGanador = 0;
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
        puntosGanador = puntos[indiceDelMayor];
        //Comprobar empates
        for (int x = 1; x < puntos.length; x++) {
			if (puntos[indiceDelMayor] == puntos[x]) {
				empatados.add(mesa.getJugadores().get(x));
            }
        }
        if(!empatados.isEmpty() && empatados.size() > 1){
            System.out.println("EMPATADOS: ");
            for (int x = 0; x < empatados.size(); x++) {
                System.out.println(empatados.get(x).getIdAgente());
            }
            escribirResultados();
            IGoal sd = createGoal("ams_shutdown_platform");
            // sd.getParameter("ams").setValue(ams); // Set ams in case of remote platform
            dispatchSubgoal(sd);
        }else{
            System.out.println("GANADOR: " + mesa.getJugadores().get(indiceDelMayor).getIdAgente());
            escribirResultados();
            IGoal sd = createGoal("ams_shutdown_platform");
            // sd.getParameter("ams").setValue(ams); // Set ams in case of remote platform
            dispatchSubgoal(sd);
        }

        IMessageEvent informe = createMessageEvent("Inform_Finalizar_Ronda");
        FinalizarRonda accion = new FinalizarRonda();
        informe.setContent(accion);
        informe.getParameterSet(SFipa.RECEIVERS).addValue(mesa.getJugadores().get(0).getIdAgente());
        sendMessage(informe);
    }

    public void escribirResultados(){

        String resultados = "./resultados.txt";
        File fichero = new File(resultados);
        try {
            // A partir del objeto File creamos el fichero físicamente
            if(fichero.createNewFile()){
                BufferedWriter bw = new BufferedWriter(new FileWriter(resultados, true));
                bw.write("GANADORES\tPUNTOS\tESTRATEGIA\tCRIPTOKITS\n");
                for (int i = 0; i < empatados.size(); i++){
                    ArrayList<ArrayList<Carta>> criptoKits = (ArrayList) empatados.get(i).getSeleccion().getCriptokits().clone();
                    String tipoCriptokit = getNombreCriptokits(criptoKits);
                    if(empatados.get(i).getEstrategia() == 0){
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\tavanzada\t" + tipoCriptokit + "\n");
                    }else if(empatados.get(i).getEstrategia() == 1){
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\testandar\t" + tipoCriptokit + "\n");
                    }else{
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\taleatoria\t" + tipoCriptokit + "\n");
                    }
                }              
                bw.close();
            }else{
                BufferedWriter bw = new BufferedWriter(new FileWriter(resultados, true));
                for (int i = 0; i < empatados.size(); i++){
                    ArrayList<ArrayList<Carta>> criptoKits = (ArrayList) empatados.get(i).getSeleccion().getCriptokits().clone();
                    String tipoCriptokit = getNombreCriptokits(criptoKits);
                    if(empatados.get(i).getEstrategia() == 0){
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\tavanzada\t" + tipoCriptokit + "\n");
                    }else if(empatados.get(i).getEstrategia() == 1){
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\testandar\t" + tipoCriptokit + "\n");
                    }else{
                        bw.write(empatados.get(i).getIdAgente() + "\t" + puntosGanador + "\taleatoria\t" + tipoCriptokit + "\n");
                    }
                }              
                bw.close();
            }
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
    }

    public String getNombreCriptokits(ArrayList<ArrayList<Carta>> criptoKits){
        String tipoCriptoKit = "";
        for (int j = 0; j < criptoKits.size(); j++){
            if (criptoKits.get(j).size() == 2) tipoCriptoKit = tipoCriptoKit + "CK1, ";
            else if(criptoKits.get(j).size() == 3){
                for(int z = 0; z < criptoKits.get(j).size(); z++){
                    if (criptoKits.get(j).get(z).getTipo() == 1){ //ROSA
                        tipoCriptoKit = tipoCriptoKit + "CK2a, ";
                    }else if(criptoKits.get(j).get(z).getTipo() == 2){ //NARANJA
                        tipoCriptoKit = tipoCriptoKit + "CK2b, ";
                    }
                }
            }else if(criptoKits.get(j).size() == 4){
                if (criptoKits.get(j).get(0).getTipo() == 2){ //NARANJA
                    tipoCriptoKit = tipoCriptoKit + "CK2d, ";
                }else if(criptoKits.get(j).get(1).getTipo() == 2){ //NARANJA
                    tipoCriptoKit = tipoCriptoKit + "CK2d, ";
                }else if(criptoKits.get(j).get(2).getTipo() == 2){ //NARANJA
                    tipoCriptoKit = tipoCriptoKit + "CK2d, ";
                }else if(criptoKits.get(j).get(3).getTipo() == 2){ //NARANJA
                    tipoCriptoKit = tipoCriptoKit + "CK2d, ";
                }else{
                    tipoCriptoKit = tipoCriptoKit + "CK2c, ";
                }
            }
        }
        return tipoCriptoKit;
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
