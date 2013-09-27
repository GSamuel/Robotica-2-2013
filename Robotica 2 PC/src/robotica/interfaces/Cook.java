package robotica.interfaces;

import robotica.cook.CookState;
import robotica.cook.PreparingFoodState;

public class Cook
{
	CookState preparingFoodState;

	public Cook()
	{
		preparingFoodState = new PreparingFoodState(this);
	}
}
