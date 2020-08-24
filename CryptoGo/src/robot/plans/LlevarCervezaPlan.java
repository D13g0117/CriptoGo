package robot.plans;

import jadex.runtime.Plan;
import robot.beliefs.BeersPerHuman;
import jadex.adapter.fipa.SFipa;
import ontology.predicates.CervezaEntregada;
import jadex.runtime.IMessageEvent;
import jadex.adapter.fipa.AgentIdentifier;
import ontology.predicates.HayCerveza;


public class LlevarCervezaPlan extends Plan
{
	public void body()
	{
        IMessageEvent msgrec = (IMessageEvent) getInitialEvent();
		// vemos la respuesta, y actualizamos el believe de acuerdo a esa respuesta
		boolean hay= msgrec.getContent() instanceof HayCerveza; 
		// si hay cervezas, ya no queda pendiente el pedido y como la entregamos no sabemos si quedan mas
		// si no hay cervezas, queda pendiente el pedido, se que no hay mas cervezas.
		getBeliefbase().getBelief("sesihaycervezas").setFact(!hay);	
		getBeliefbase().getBelief("haycervezas").setFact(hay);	
		getBeliefbase().getBelief("cervezaspedidas").setFact(new Boolean(!hay));
		CervezaEntregada ce= new CervezaEntregada();
		AgentIdentifier humano= (AgentIdentifier) getBeliefbase().getBelief("human").getFact();		
		if (hay)
		{
			System.out.println("robot llevando cerveza...");
			// incrementamos el numero de bebidas que ha bebido ese humano
			BeersPerHuman[] bebidastodos= (BeersPerHuman[]) getBeliefbase().getBeliefSet("cervezasbebidas").getFacts();
			int i= bebidastodos.length;
			boolean encontrado=false;
			int bebidas=0;
			while ((i>0) && !encontrado)
			{
				i--;
				BeersPerHuman bphi= bebidastodos[i];
				AgentIdentifier hi= bphi.getHuman();
				if (hi.equals(humano))
				{
					encontrado=true;
					getBeliefbase().getBeliefSet("cervezasbebidas").removeFact(bphi);
					bebidas= bphi.getBeers();
					bebidas++;
					bphi.setBeers(bebidas);
					getBeliefbase().getBeliefSet("cervezasbebidas").addFact(bphi);
				}
			}
		}
		// comunicamos si pudo entregar la cerveza o no
		IMessageEvent	msg	= createMessageEvent("informentregada");
		if (!hay)
		{
			msg = createMessageEvent("failureentregada");
			int current_time= (int) getBeliefbase().getBelief("currenttime").getFact();
			getBeliefbase().getBelief("lapsedtime").setFact(current_time);
			System.out.println("robot no puede llevar cerveza porque no quedan...");			
		}
		msg.setContent(ce);
		msg.getParameterSet(SFipa.RECEIVERS).addValue(humano);
		sendMessage(msg);
	}
}
