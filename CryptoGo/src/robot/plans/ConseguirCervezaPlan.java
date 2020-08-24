package robot.plans;

import jadex.util.SUtil;
import robot.beliefs.BeersPerHuman;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;
import jadex.adapter.fipa.AgentIdentifier;
import jadex.adapter.fipa.SFipa;
import jadex.adapter.fipa.AgentDescription;
import jadex.adapter.fipa.SearchConstraints;
import jadex.adapter.fipa.ServiceDescription;
import ontology.actions.BeberCerveza;

public class ConseguirCervezaPlan extends Plan
{
	private int buscar (BeersPerHuman[] bebidas, AgentIdentifier humano)
	{
		int retorno=-1;
		int i= bebidas.length;
		boolean encontrado=false;
		while ((i>0) && !encontrado)
		{
			i--;
			BeersPerHuman bphi= bebidas[i];
			AgentIdentifier hi= bphi.getHuman();
			if (hi.equals(humano))
			{
				encontrado=true;
				retorno= bphi.getBeers();
			}
		}
		return retorno;
	}
	public void body()
	{
		// miro cuantas cervezas tiene ese humano
		System.out.println ("robot recibe peticion de cerveza...");
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		AgentIdentifier humano= (AgentIdentifier) request.getParameter("sender").getValue();
		BeersPerHuman[] bebidastodos= (BeersPerHuman[]) getBeliefbase().getBeliefSet("cervezasbebidas").getFacts();
		int bebidas= buscar(bebidastodos,humano);
		if (bebidas == -1) 
			// humano que empieza a beber, le ponemos su cuenta a cero
		{
			System.out.println ("robot registra que es la primera cerveza del humano...");
			BeersPerHuman bph= new BeersPerHuman();
			bph.setHuman(humano);
			bph.setBeers(0);
			getBeliefbase().getBeliefSet("cervezasbebidas").addFact(bph);
		}			
		int maximo= (int) getBeliefbase().getBelief("maxcervezas").getFact();
		boolean conductor=false;
		// Miro si el humano es conductor
		ServiceDescription sd = new ServiceDescription();
		sd.setType("conductor");
		AgentDescription dfadesc = new AgentDescription();
		dfadesc.addService(sd);
		IGoal ft = createGoal("df_search");
		ft.getParameter("description").setValue(dfadesc);
		dispatchSubgoalAndWait(ft);
		AgentDescription[]	result	= (AgentDescription[])ft.getParameterSet("result").getValues();
		int i=result.length;
		while ((i>0) && !conductor)
		{
			i--; 
			AgentIdentifier candidato = result[i].getName();
			conductor= candidato.equals(humano);
		}
		// mando mensaje de aceptación si puede beber 
		BeberCerveza bc= new BeberCerveza();
		IMessageEvent	msg	= createMessageEvent("agreebeber");
		if ((bebidas>maximo) && conductor)
		{
			msg = createMessageEvent("refusebeber");
			System.out.println("robot deniega peticion de cerveza: posible conductor borracho");
		}
		else 
		{
			// mando mensaje de rechazo si no puede beber mas por ser conductor
			getBeliefbase().getBelief("cervezaspedidas").setFact(new Boolean(true));
			getBeliefbase().getBelief("human").setFact(humano); 
			System.out.println("robot acepta peticion de cerveza");
		}
			msg.setContent(bc);
			msg.getParameterSet(SFipa.RECEIVERS).addValue(humano);
			sendMessage(msg);
	}
}
