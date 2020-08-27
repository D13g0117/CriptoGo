package ontology.actions;

import java.util.*;
import ontology.concepts.*;

public class DarCartas extends Accion {

    ArrayList<Carta> cartas;

    public DarCartas() {
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

}