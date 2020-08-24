package human.plans;

import jadex.runtime.Plan;


public class DrinkBeerPlan extends Plan
{
	//-------- methods --------
	
	/**
	 *  Plan body.
	 */
	public void body()
	{
		getBeliefbase().getBelief("havebeer").setFact(new Boolean(true));
		// ya nos ha entregado la cerveza el robot
		int current_time= (int) getBeliefbase().getBelief("currenttime").getFact();
		getBeliefbase().getBelief("newbeertime").setFact(current_time);
		// guardamos el momento actual como el momento en el que empezamos a beber una nueva cerveza
		System.out.println("humano bebe cerveza...");		
	}

}
