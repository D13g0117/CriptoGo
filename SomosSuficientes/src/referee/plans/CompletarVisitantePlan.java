package referee.plans;

import jadex.runtime.*;
import jadex.util.SUtil;
import jadex.adapter.fipa.*;


public class CompletarVisitantePlan extends Plan
{
	//-------- methods --------
	
	/**
	 *  Plan body.
	 */
	public void body()
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType("visitante");
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
		int numjugsvis= result.length;
		System.out.println(numjugsvis+" jugadores visitantes encontrados");
		getBeliefbase().getBelief("visitante").setFact(numjugsvis);
	}

}
