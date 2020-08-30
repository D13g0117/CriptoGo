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

    public String Mostrar(){
      String security, type;
      if (seguridad == 0)security = "Baja";else if (seguridad == 1) security = "Media"; else security = "Alta"; 
      if (tipo == 0)type = "SC";else if (tipo == 1) type = "BC";else if (tipo == 2) type = "H";else if (tipo == 3) type = "OM";else if (tipo == 4) type = "AE"; else type = "MAC";
      return "[ " + nombre + ", " + security + ", " + type + " ]";
    }
}