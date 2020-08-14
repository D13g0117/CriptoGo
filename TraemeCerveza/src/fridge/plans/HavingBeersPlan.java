package fridge.plans;

import jadex.runtime.*;
import ontology.predicates.*;
import jadex.adapter.fipa.AgentIdentifier;
import jadex.adapter.fipa.SFipa;


public class HavingBeersPlan extends Plan
{
	//-------- methods --------
	
	/**
	 *  Plan body.
	 */
	public void body()
	{
        IMessageEvent msgrec = (IMessageEvent) getInitialEvent();
        HayCerveza predicado = (HayCerveza) msgrec.getContent();
		AgentIdentifier sender = (AgentIdentifier) msgrec.getParameter("sender").getValue();
 		int beers= (int) getBeliefbase().getBelief("beers").getFact();
		System.out.println("nevera mirando si queda cerveza...");		
		if (beers>0)
		{
			getBeliefbase().getBelief("beers").setFact(beers-1);			
			System.out.println("nevera confirma que queda cerveza...");					
			// comunicamos que si tenemos cervezas
			IMessageEvent msgsend = createMessageEvent("InformHayCervezasMsg");
			msgsend.setContent(predicado);
			msgsend.getParameterSet(SFipa.RECEIVERS).addValue(sender);
			sendMessage(msgsend); 
		}
		else 
		{
			// comunicamos que no nos quedan cervezas
			System.out.println("nevera confirma que no queda cerveza...");					
			IMessageEvent msgsend = createMessageEvent("InformNoHayCervezasMsg");
			NoHayCerveza predicado2= new NoHayCerveza();
			msgsend.setContent(predicado2);
			msgsend.getParameterSet(SFipa.RECEIVERS).addValue(sender);
			sendMessage(msgsend); 
			System.out.println("nevera pide mas cerveza al super...");
			waitFor(2500);
			getBeliefbase().getBelief("beers").setFact(4);
		// las traen de 4 en 4
			System.out.println("nevera rellenando cerveza...");
		}
	}

}
