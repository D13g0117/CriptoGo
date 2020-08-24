package robot.plans;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.IGoal;
import jadex.adapter.fipa.AgentIdentifier;
import jadex.adapter.fipa.SFipa;
import jadex.adapter.fipa.AgentDescription;
import jadex.adapter.fipa.SearchConstraints;
import jadex.adapter.fipa.ServiceDescription;
import java.util.Random;
import ontology.predicates.HayCerveza;

public class PreguntarExistenciasPlan extends Plan
{
	public void body()
	{
		// busco a la nevera
		ServiceDescription sd = new ServiceDescription();
		sd.setType("fridge");
		AgentDescription dfadesc = new AgentDescription();
		dfadesc.addService(sd);
		IGoal ft = createGoal("df_search");
		ft.getParameter("description").setValue(dfadesc);
		dispatchSubgoalAndWait(ft);
		AgentDescription[]	result	= (AgentDescription[])ft.getParameterSet("result").getValues();
		AgentIdentifier nevera = result[new Random().nextInt(result.length)].getName();

		// le preguntamos si tiene cervezas
		HayCerveza hc= new HayCerveza();
		System.out.println("robot preguntando si quedan cervezas...");
		IMessageEvent	msg	= createMessageEvent("querycervezas");
		msg.setContent(hc);
		msg.getParameterSet(SFipa.RECEIVERS).addValue(nevera);
		sendMessage(msg);

	}
}
