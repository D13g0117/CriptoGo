package human.plans;

import jadex.runtime.Plan;


public class WaitBeerPlan extends Plan
{
	//-------- methods --------
	
	/**
	 *  Plan body.
	 */
	public void body()
	{
		getBeliefbase().getBelief("havebeer").setFact(new Boolean(false));
		// ya nos ha entregado la cerveza el robot
		System.out.println("humano espera su cerveza...");		
		getBeliefbase().getBelief("waitingbeer").setFact(new Boolean(true));
	}

}
