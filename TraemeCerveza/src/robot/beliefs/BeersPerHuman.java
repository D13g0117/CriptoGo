package robot.beliefs;

import jadex.adapter.fipa.AgentIdentifier;

public class BeersPerHuman 
{
	protected AgentIdentifier human;
	protected int beers;
	
	public BeersPerHuman() {}
	
	public AgentIdentifier getHuman()
	{
		return human;
	}
	public int getBeers()
	{
		return beers;
	}
	public void setBeers(int b)
	{
		this.beers=b;
	}
	public void setHuman(AgentIdentifier h)
	{
		this.human=h;
	}

}