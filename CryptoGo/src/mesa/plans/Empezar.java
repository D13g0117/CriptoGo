package mesa.plans;

import java.util.*;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontology.actions.*;
import ontology.concepts.*;
import ontology.predicates.*;
import ontology.concepts.Carta;
import ontology.concepts.Mazo;



public class Empezar extends Plan
{
	private Mesa mesa;
	private Mazo mazo;
	
	public void body()
	{
		System.out.println("[PLAN] Se inicializa la mesa");		
		// Tablero
		mesa = (Mesa) getBeliefbase().getBelief("mesa").getFact();
		mazo = (Mazo) getBeliefbase().getBelief("mazo").getFact();
		mazo = InicializarMazo();

		// Actualizamos el belief de la mesa
		getBeliefbase().getBelief("mesa").setFact(mesa);
		getBeliefbase().getBelief("mazo").setFact(mazo);
		getBeliefbase().getBelief("turno").setFact(1);
		getBeliefbase().getBelief("empezar").setFact(false);    
		getBeliefbase().getBelief("jugadorActual").setFact(mesa.getJugadores().get(0).getIdAgente());
		
		// Se informa también al jugador al que le toca ahora jugar
		System.out.println("\n ---------------");
		System.out.println("| [INFO] FASE 1 |");
		System.out.println(" ---------------");
		System.out.println("\n[INFO] TURNO " + getBeliefbase().getBelief("turno").getFact());
		IMessageEvent respuesta = createMessageEvent("Inform_Turno_Asignado");
		TurnoAsignado predicado = new TurnoAsignado();
		predicado.setJugador(mesa.getJugadores().get(0));
		System.out.println("\n[INFO] Decide el jugador con id " + mesa.getJugadores().get(0).getIdAgente() + "\n");
		respuesta.setContent(predicado);
		respuesta.getParameterSet(SFipa.RECEIVERS).addValue(mesa.getJugadores().get(0).getIdAgente());
		sendMessage(respuesta);
	}

	public Mazo InicializarMazo (){
		Mazo mazo = new Mazo();
		ArrayList<Carta> cartas = new ArrayList<>();
		Carta carta;
		// SC
		carta = new Carta("A5/1",0, 0);
		cartas.add(carta);
		carta = new Carta("A5/2", 0, 0);
		cartas.add(carta);
		carta = new Carta("ChaCha", 2, 0);
		cartas.add(carta);
		carta = new Carta("E0", 0, 0);
		cartas.add(carta);
		carta = new Carta("Grain 128a", 2, 0);
		cartas.add(carta);
		carta = new Carta("Grain", 1, 0);
		cartas.add(carta);
		carta = new Carta("HC-128", 2, 0);
		cartas.add(carta);
		carta = new Carta("Mickey 2.0", 1, 0);
		cartas.add(carta);
		carta = new Carta("Rabbit", 1, 0);
		cartas.add(carta);
		carta = new Carta("RC4", 0, 0);
		cartas.add(carta);
		carta = new Carta("Salsa 20/20", 2, 0);
		cartas.add(carta);
		carta = new Carta("Snow 2.0", 2, 0);
		cartas.add(carta);
		carta = new Carta("Snow 3G", 2, 0);
		cartas.add(carta);
		carta = new Carta("SOSEMANUK", 2, 0);
		cartas.add(carta);
		carta = new Carta("Trivium", 1, 0);
		cartas.add(carta);
		// BC
		carta = new Carta("AES", 2, 1);
		cartas.add(carta);
		carta = new Carta("AES", 2, 1);
		cartas.add(carta);
		carta = new Carta("AES", 2, 1);
		cartas.add(carta);
		carta = new Carta("AES", 2, 1);
		cartas.add(carta);
		carta = new Carta("Blowfish>=80b keys", 1, 1);
		cartas.add(carta);
		carta = new Carta("Blowfish>=80b keys", 1, 1);
		cartas.add(carta);
		carta = new Carta("Blowfish>=80b keys", 1, 1);
		cartas.add(carta);
		carta = new Carta("Blowfish>=80b keys", 1, 1);
		cartas.add(carta);
		carta = new Carta("Camellia", 2, 1);
		cartas.add(carta);
		carta = new Carta("Camellia", 2, 1);
		cartas.add(carta);
		carta = new Carta("Camellia", 2, 1);
		cartas.add(carta);
		carta = new Carta("Camellia", 2, 1);
		cartas.add(carta);
		carta = new Carta("DES", 0, 1);
		cartas.add(carta);
		carta = new Carta("DES", 0, 1);
		cartas.add(carta);
		carta = new Carta("DES", 0, 1);
		cartas.add(carta);
		carta = new Carta("DES", 0, 1);
		cartas.add(carta);
		carta = new Carta("Kasumi", 1, 1);
		cartas.add(carta);
		carta = new Carta("Kasumi", 1, 1);
		cartas.add(carta);
		carta = new Carta("Kasumi", 1, 1);
		cartas.add(carta);
		carta = new Carta("Kasumi", 1, 1);
		cartas.add(carta);
		carta = new Carta("Serpent", 2, 1);
		cartas.add(carta);
		carta = new Carta("Serpent", 2, 1);
		cartas.add(carta);
		carta = new Carta("Serpent", 2, 1);
		cartas.add(carta);
		carta = new Carta("Serpent", 2, 1);
		cartas.add(carta);
		carta = new Carta("Three-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Three-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Three-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Three-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Two-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Two-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Two-Key 3DES", 1, 1);
		cartas.add(carta);
		carta = new Carta("Two-Key 3DES", 1, 1);
		cartas.add(carta);
		// H
		carta = new Carta("Blake", 2, 2);
		cartas.add(carta);
		carta = new Carta("RIPEMD-128", 0, 2);
		cartas.add(carta);
		carta = new Carta("RIPEMD-160", 1, 2);
		cartas.add(carta);
		carta = new Carta("SHA-2 | 224, 512/224", 1, 2);
		cartas.add(carta);
		carta = new Carta("SHA-2 | 256, 384, 512, 512/256", 2, 2);
		cartas.add(carta);
		carta = new Carta("SHA-3 | 224", 1, 2);
		cartas.add(carta);
		carta = new Carta("SHA-3 | SHAKE128, SHAKE256", 2, 2);
		cartas.add(carta);
		carta = new Carta("SHA-3 | 256, 384, 512", 2, 2);
		cartas.add(carta);
		carta = new Carta("Whirlpool | 512", 2, 2);
		cartas.add(carta);
		carta = new Carta("MD5", 0, 2);
		cartas.add(carta);
		carta = new Carta("MD5", 0, 2);
		cartas.add(carta);
		carta = new Carta("SHA-1", 0, 2);
		cartas.add(carta);
		carta = new Carta("SHA-1", 0, 2);
		cartas.add(carta);
		// OM
		carta = new Carta("CFB", 1, 3);
		cartas.add(carta);
		carta = new Carta("OFB", 1, 3);
		cartas.add(carta);
		carta = new Carta("CBC", 1, 3);
		cartas.add(carta);
		carta = new Carta("CBC", 1, 3);
		cartas.add(carta);
		carta = new Carta("CTR", 1, 3);
		cartas.add(carta);
		carta = new Carta("CTR", 1, 3);
		cartas.add(carta);
		carta = new Carta("ECB", 0, 3);
		cartas.add(carta);
		carta = new Carta("ECB", 0, 3);
		cartas.add(carta);
		carta = new Carta("EME", 2, 3);
		cartas.add(carta);
		carta = new Carta("EME", 2, 3);
		cartas.add(carta);
		carta = new Carta("FFX", 2, 3);
		cartas.add(carta);
		carta = new Carta("FFX", 2, 3);
		cartas.add(carta);
		carta = new Carta("FFX", 2, 3);
		cartas.add(carta);
		// AE
		carta = new Carta("CCM", 1, 4);
		cartas.add(carta);
		carta = new Carta("CWC", 1, 4);
		cartas.add(carta);
		carta = new Carta("GCM", 2, 4);
		cartas.add(carta);
		carta = new Carta("Generic Composition", 1, 4);
		cartas.add(carta);
		carta = new Carta("OCB", 2, 4);
		cartas.add(carta);
		carta = new Carta("EAX", 2, 4);
		cartas.add(carta);
		carta = new Carta("EAX", 2, 4);
		cartas.add(carta);
		// MAC
		carta = new Carta("AMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("AMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("AMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("AMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("CMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("CMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("CMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("CMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("EMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("EMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("EMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("EMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("GMAC", 1, 5);
		cartas.add(carta);
		carta = new Carta("GMAC", 1, 5);
		cartas.add(carta);
		carta = new Carta("GMAC", 1, 5);
		cartas.add(carta);
		carta = new Carta("GMAC", 1, 5);
		cartas.add(carta);
		carta = new Carta("HMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("HMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("HMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("HMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("Poly1305", 1, 5);
		cartas.add(carta);
		carta = new Carta("Poly1305", 1, 5);
		cartas.add(carta);
		carta = new Carta("Poly1305", 1, 5);
		cartas.add(carta);
		carta = new Carta("Poly1305", 1, 5);
		cartas.add(carta);
		carta = new Carta("UMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("UMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("UMAC", 2, 5);
		cartas.add(carta);
		carta = new Carta("UMAC", 2, 5);
		cartas.add(carta);

		mazo.setCartas(cartas);

		System.out.println("[INFO] Cartas del mazo barajadas");
		return mazo;
	}

}
