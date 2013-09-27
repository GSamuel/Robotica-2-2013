package robotica.cook;

import robotica.interfaces.Cook;

public class WaitingOrderState implements CookState
{
	private Cook cook;
	public WaitingOrderState (Cook cook)
	{
		this.cook = cook;
	}
	
	@Override
	public void giveOrder()
	{
		
	}

}
