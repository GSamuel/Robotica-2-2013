package robotica.cook;

import robotica.interfaces.Cook;

public class PreparingFoodState implements CookState
{
	private Cook cook;
	public PreparingFoodState (Cook cook)
	{
		this.cook = cook;
	}
	
	@Override
	public void giveOrder()
	{
		// TODO Auto-generated method stub
		
	}

}
