import java.util.ArrayList;
import java.util.Vector;

import standard.StopProgram;


public class ArrayBoundsTest
{

	public static void main(String[] args)
	{

		new StopProgram().start();
		
		Vector<Integer> vec = new Vector<Integer>();
		vec.addElement(0);
		vec.addElement(5);
		vec.addElement(3);
		
		System.out.println(vec.elementAt(3));
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(5);
		array.add(2);
		array.add(7);
		//System.out.println(array.get(5));
		
		
		int[] ar = {4,5,2,3};
		
		System.out.println(ar[vec.elementAt(3)]);
		
	}

}
