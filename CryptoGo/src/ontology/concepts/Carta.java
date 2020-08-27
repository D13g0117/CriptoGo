package ontology.concepts;

import jadex.adapter.fipa.AgentIdentifier;
import ontology.*;

public class Carta extends Concepto {

    /*** Atributos ***/
    private String nombre;
    private int seguridad; //0 - Bajo | 1 - Medio | 2 - Alto
    private int tipo; //0 - SC | 1 - BC | 2 - H | 3 - OM | 4 - AE | 5 - MAC

    /*** Constructor ***/
    public Carta(String nombre, int seguridad, int tipo) {
      this.nombre = nombre;
      this.seguridad = seguridad;
      this.tipo = tipo;
    }

    public Carta() {
    }



    /*** Getters & Setters ***/
    public String getNombre() {
        return nombre;
      }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeguridad() {
        return seguridad;
      }
    public void setSeguridad(int seguridad) {
        this.seguridad = seguridad;
    }

    public int getTipo() {
        return tipo;
      }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}