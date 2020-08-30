package ontology.concepts;
 
import java.util.*;
import jadex.adapter.fipa.AgentIdentifier;
import ontology.*;

public class Seleccion extends Concepto {

    /*** Constructor ***/
    public Seleccion() {
    }
    
    /*** Atributos ***/
    private ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();

    /*** Getters & Setters ***/
    public ArrayList<Carta> getCartasSeleccionadas(){
        return cartasSeleccionadas;
    }
    public void setCartasSeleccionadas(ArrayList<Carta> cartasSeleccionadas){
        this.cartasSeleccionadas = cartasSeleccionadas;
    }
    public int getNumCartasSeleccionadas() {
        return cartasSeleccionadas.size();
    }
    
    public ArrayList<ArrayList<Carta>> getCriptokits() {
        
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

    public void imprimirCartasSeleccionadas(){
        for (int i = 0; i < cartasSeleccionadas.size(); i ++){
            System.out.print(cartasSeleccionadas.get(i).Mostrar());
        }
        System.out.println();
    }
}





        //ArrayList<ArrayList<Carta>> criptoKits = new ArrayList<>();
        //ArrayList<Carta> cartasCriptoKits = new ArrayList<>();
//
        //for (int i = 0; i < cartasSeleccionadas.size(); i++){
        //    if(cartasSeleccionadas.get(i).getTipo() == 1){ //ROSA
        //        for (int j = 0; j < cartasSeleccionadas.size(); j++){
        //            if(cartasSeleccionadas.get(j).getTipo() == 4){ //VERDE
        //                cartasCriptoKits.add(cartasSeleccionadas.get(i));
        //                cartasCriptoKits.add(cartasSeleccionadas.get(j));
        //                criptoKits.add(cartasCriptoKits); //CK1
        //                cartasCriptoKits = new ArrayList<>();
        //            }
        //            if(cartasSeleccionadas.get(j).getTipo() == 5){ //AZUL
        //                for (int k = 0; k < cartasSeleccionadas.size(); k++){
        //                    if(cartasSeleccionadas.get(k).getTipo() == 0){ //ROJA
        //                        cartasCriptoKits.add(cartasSeleccionadas.get(i));
        //                        cartasCriptoKits.add(cartasSeleccionadas.get(j));
        //                        cartasCriptoKits.add(cartasSeleccionadas.get(k));
        //                        criptoKits.add(cartasCriptoKits); //CK2A
        //                        cartasCriptoKits = new ArrayList<>();
        //                    }
        //                    if(cartasSeleccionadas.get(k).getTipo() == 1 && k != i){ //ROSA
        //                        for (int l = 0; l < cartasSeleccionadas.size(); l++){
        //                            if(cartasSeleccionadas.get(l).getTipo() == 3){ //AMARILLA
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(i));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(j));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(k));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(l));
        //                                criptoKits.add(cartasCriptoKits); //CK2C
        //                                cartasCriptoKits = new ArrayList<>();
        //                             }
        //                        }                           
        //                    }
        //                    if(cartasSeleccionadas.get(k).getTipo() == 2){ //NARANJA
        //                        for (int m = 0; m < cartasSeleccionadas.size(); m++){
        //                            if(cartasSeleccionadas.get(m).getTipo() == 3){ //AMARILLA
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(i));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(j));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(k));
        //                                cartasCriptoKits.add(cartasSeleccionadas.get(m));
        //                                criptoKits.add(cartasCriptoKits); //CK2D
        //                                cartasCriptoKits = new ArrayList<>();
        //                             }
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //    }
        //    if (cartasSeleccionadas.get(i).getTipo() == 0){ //ROJO
        //        for (int n = 0; n < cartasSeleccionadas.size(); n++){
        //            if(cartasSeleccionadas.get(n).getTipo() == 5){ //AZUL
        //                for (int o = 0; o < cartasSeleccionadas.size(); o++){
        //                    if(cartasSeleccionadas.get(o).getTipo() == 2){ //NARANJA
        //                        cartasCriptoKits.add(cartasSeleccionadas.get(n));
        //                        cartasCriptoKits.add(cartasSeleccionadas.get(o));
        //                        criptoKits.add(cartasCriptoKits); //CK2B
        //                        cartasCriptoKits = new ArrayList<>();
        //                    }
        //                }
        //             }
        //        }
        //    }
        //}
//
        //System.out.println("CriptoKits :");
        //if(!criptoKits.isEmpty()){
        //    for (int i = 0; i < criptoKits.size(); i++){
        //        for (int j = 0; j < criptoKits.get(i).size(); j++){
        //            System.out.print(criptoKits.get(i).get(j).Mostrar());
        //        }
        //        System.out.println();
        //    }
        //    System.out.println();
        //}
//
        //ArrayList<Carta> ganador = new ArrayList<>();
        //ArrayList<ArrayList<Carta>> aux = criptoKits;
//
        //if (criptoKits.size() > 1){
        //    ganador = criptoKits.get(0);
        //    for (int i = 0; i < criptoKits.size(); i ++){
        //        if(getPuntos(ganador) < getPuntos(criptoKits.get(i))){
        //            ganador = criptoKits.get(i);
        //        }
        //    }
        //    aux.add(ganador);
        //    criptoKits.remove(ganador);
        //}

        //if (aux.size() > 1){
        //    ganador = criptoKits.get(0);
        //    while(criptoKits.size() != 0){
        //        for (int i = 0; i < criptoKits.size(); i ++){
        //            if (getPuntos(ganador) < getPuntos(criptoKits.get(i))){
        //                ganador = criptoKits.get(i);
        //            } 
        //        }
        //        aux.add(ganador);
        //        criptoKits.remove(ganador);
        //    }
        //}

        /*
        ArrayList<Carta> cartasCriptoKits = new ArrayList<>();
        ArrayList<Carta> aux = new ArrayList<>();
        ArrayList<Carta> SC = new ArrayList<>();
        ArrayList<Carta> BC = new ArrayList<>();
        ArrayList<Carta> H = new ArrayList<>();
        ArrayList<Carta> OM = new ArrayList<>();
        ArrayList<Carta> AE = new ArrayList<>();
        ArrayList<Carta> MAC = new ArrayList<>();

        for (int i = 0;  i < cartasSeleccionadas.size(); i++){
            if (cartasSeleccionadas.get(i).getTipo() == 0) SC.add(cartasSeleccionadas.get(i));
            if (cartasSeleccionadas.get(i).getTipo() == 1) BC.add(cartasSeleccionadas.get(i));
            if (cartasSeleccionadas.get(i).getTipo() == 2) H.add(cartasSeleccionadas.get(i));
            if (cartasSeleccionadas.get(i).getTipo() == 3) OM.add(cartasSeleccionadas.get(i));
            if (cartasSeleccionadas.get(i).getTipo() == 4) AE.add(cartasSeleccionadas.get(i));
            if (cartasSeleccionadas.get(i).getTipo() == 5) MAC.add(cartasSeleccionadas.get(i));
        }

        if (!BC.isEmpty() && !AE.isEmpty()){s
            if (!cartasCriptoKits.containsAll(BC)) cartasCriptoKits.addAll(BC);
            if (!cartasCriptoKits.containsAll(AE)) cartasCriptoKits.addAll(AE);
            System.out.println("[INFO] Crypto Kit: CK1");
        }
        if (!SC.isEmpty() && !MAC.isEmpty() && !BC.isEmpty()) {
            if (!cartasCriptoKits.containsAll(SC)) cartasCriptoKits.addAll(SC);
            if (!cartasCriptoKits.containsAll(MAC)) cartasCriptoKits.addAll(MAC);
            if (!cartasCriptoKits.containsAll(BC)) cartasCriptoKits.addAll(BC);
            System.out.println("[INFO] Crypto Kit: CK2.a");
        }
        if (!SC.isEmpty() && !MAC.isEmpty() && !H.isEmpty()) {
            if (!cartasCriptoKits.containsAll(SC)) cartasCriptoKits.addAll(SC);
            if (!cartasCriptoKits.containsAll(MAC)) cartasCriptoKits.addAll(MAC);
            if (!cartasCriptoKits.containsAll(H)) cartasCriptoKits.addAll(H);
            System.out.println("[INFO] Crypto Kit: CK2.b");
        } 
        if (!OM.isEmpty() && !BC.isEmpty() && !MAC.isEmpty() && BC.size() > 1) {
            if (!cartasCriptoKits.containsAll(OM)) cartasCriptoKits.addAll(OM);
            if (!cartasCriptoKits.containsAll(BC)) cartasCriptoKits.addAll(BC);
            if (!cartasCriptoKits.containsAll(MAC)) cartasCriptoKits.addAll(MAC);
            if (!cartasCriptoKits.containsAll(BC)) cartasCriptoKits.addAll(BC);
            System.out.println("[INFO] Crypto Kit: CK2.d");
        }
        if (!OM.isEmpty() && !BC.isEmpty() && !MAC.isEmpty() && !H.isEmpty()) {
            if (!cartasCriptoKits.containsAll(BC)) cartasCriptoKits.addAll(BC);
            if (!cartasCriptoKits.containsAll(MAC)) cartasCriptoKits.addAll(MAC);
            if (!cartasCriptoKits.containsAll(H)) cartasCriptoKits.addAll(H);
            System.out.println("[INFO] Crypto Kit: CK2.c");
        }      
        */