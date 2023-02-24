package polyPack;
/**
 * Author: Andrew Roney
 * Project Name: Polynomial_Project
 * Date: 09/06/2022
 * Class Description: This class takes the lists and validates whether it is sorted or not.
 */

import java.util.List;

public class OrderedList {
//Method: Checks to see if the list object is supplied as a parent in ascending order.
	public static <T extends Comparable<? super T>> boolean checkSorted(List<T> list) {
		boolean isSorted = true;
		
		for(int i = list.size() - 1; i > 0; i--) {//Run through each item in the list.
			T current = list.get(i);//Grab the item and set current to that value
			if(!checkSorted(list, current)) {//Check if ordered.
				isSorted = false;
			}
		}
		return isSorted;
	}
	
//Method: This checks the value of the current list item.
	private static <T extends Comparable<? super T>> boolean checkSorted(List<T> list, T current) {
		T currentValue = list.get(list.indexOf(current));//Set value to current item on the list.
		T nextValue = list.get(list.indexOf(current) - 1);//Set value to the next item on the list
		
		if(nextValue != null) {//If the value is not empty, then continue on to the next.
			return currentValue.compareTo(nextValue) >= 0;
		}
		return true;
	}
}
