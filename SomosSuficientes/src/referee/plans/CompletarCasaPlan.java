package referee.plans;

import jadex.runtime.*;
import jadex.util.SUtil;
import jadex.adapter.fipa.*;


public class CompletarCasaPlan extends Plan
{
	//-------- methods --------
	
	/**
	 *  Plan body.
	 */
	public void body()
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType("casa");
		sd.setName("jugador");
		AgentDescription adesc = new AgentDescription();
		adesc.addService(sd);
		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(-1);
		IGoal busqueda = createGoal("df_search");
		busqueda.getParameter("description").setValue(adesc);
		busqueda.getParameter("constraints").setValue(sc);
		dispatchSubgoalAndWait(busqueda);
		AgentDescription[] result =(AgentDescription[])busqueda.getParameterSet("result").getValues();
		int numjugscasa= result.length;
		System.out.println(numjugscasa+" jugadores casa encontrados");
		getBeliefbase().getBelief("casa").setFact(numjugscasa);
	}

}
