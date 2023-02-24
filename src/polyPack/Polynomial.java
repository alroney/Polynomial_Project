package polyPack;

/**
 * Author: Andrew Roney
 * Project Name: Polynomial_Project
 * Date: 09/06/2022
 * Class Description: Polynomial class is where the reading and creation is done at. It reads from the file and grabs the characters and formats them into polynomials. 
 * 		This is also where the comparison of the polynomials take place as well.
 */

import java.util.Scanner;
import java.util.Comparator;
import java.util.Iterator;

public class Polynomial implements Iterable<Polynomial.Poly>, Comparable<Polynomial>{
	Comparator<Polynomial> compare;//Compares the polynomials
	public Poly top;//Top of list
	
//Constructor: Sets default values to be from a read file.
	public Polynomial(String fromFile) {
		compare = Polynomial::comparePoly;
		top = null;//Top of the linked list
		
		Scanner scan = new Scanner(fromFile);
		try {
			while(scan.hasNext()) {
				addTerm(scan.nextDouble(), scan.nextInt());
			}
		}
		
		catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			scan.close();
			throw new InvalidPolynomialSyntax("Input Error");
			
		}
		scan.close();
	}
	
//Method: Validates the expression and adds the terms to the String.
	public void addTerm(double coefficient, int exponent) {
		if(exponent < 0) {
			throw new InvalidPolynomialSyntax("Cannot process negative polynomials");
		}
		
		Poly current = top;
		
		if(current == null) {//If the current value is a null, move one the the next value.
			top = new Poly(coefficient, exponent);
			top.next = null;
		}
		
		else {
			while(current.next != null) {//Continue to look for the end of the list with transversal if the next item is not a null value.
				current = current.next;
			}
			current.next = new Poly(coefficient, exponent);
		}
	}
/*==========POLYNOMIAL COMPARISONS==========*/
	
//Method: This will compare the polynomials and set the order.
	@Override
	public int compareTo(Polynomial poly3) {
		Poly thisCurrent = this.top;
		Poly otherCurrent = poly3.top;
		
		while(thisCurrent != null && otherCurrent != null) {
			if(thisCurrent.getExponent() != otherCurrent.getExponent()) {//If the exponent of the current poly is not = to the compared poly then substract by the compared poly.
				return thisCurrent.getExponent() - otherCurrent.getExponent();
			}
			
			else if(thisCurrent.getCoefficient() != otherCurrent.getCoefficient()) {
				
				if(otherCurrent.getCoefficient() > thisCurrent.getCoefficient()) {
					return -1;
				}
				
				else if(otherCurrent.getCoefficient() < thisCurrent.getCoefficient()) {
					return +1;
				}
			}
			//Move on to the next value in the list for comparison.
			thisCurrent = thisCurrent.getNext();
			otherCurrent = otherCurrent.getNext();
		}
		
		
		if(thisCurrent == null && otherCurrent == null) {//If both are null, then they are both equal.
			return 0;
		}
		
		if(thisCurrent == null) {
			return -1;
		}
		
		else {
			return +1;
		}
	}
	
//Method: This compares both of the exponents.
	public int comparePoly(Polynomial poly2) {
		Poly thisPolyTerm = this.top;
		Poly otherPolyTerm = poly2.top;
		while(thisPolyTerm != null && otherPolyTerm != null) {
			if(thisPolyTerm.getExponent() != otherPolyTerm.getExponent()) {
				return thisPolyTerm.getExponent() - otherPolyTerm.getExponent();
			}
			else {
				thisPolyTerm = thisPolyTerm.getNext();
				otherPolyTerm = otherPolyTerm.getNext();
			}
		}
		
		if(thisPolyTerm == null && otherPolyTerm == null) {
			return 0;
		}
		
		if(otherPolyTerm == null) {
			return +1;
		}
		
		else {
			return -1;
		}
	}
	
	
	public Polynomial(Comparator<Polynomial> compare) {
		this.compare = compare;
	}
	
/*==========END POLYNOMIAL COMPARISON==========*/

	
//Method: This cycles through and implements to control the flow of the linked list.
	@Override
	public Iterator<Poly> iterator(){
		return new Iterator<Poly>() {
			public Poly current = top.getTop();
			
			@Override
			public boolean hasNext() {
				return current != null && current.getNext() != null;//Expression has another value when true.
			}
			
			@Override
			public Poly next() {//Move to the next value.
				Poly data = current;
				current = current.next;
				return data;
			}
		};
	}
	
//Method: Sets the signs depending on the value of the coefficient (negative or positive).
	@Override
	public String toString() {
		StringBuilder expressionBuilder = new StringBuilder();
		
		if(top.coefficient > 0) {
			expressionBuilder.append(top.toString());
		}
		else {
			expressionBuilder.append(" - ").append(top.toString());
		}
		
		for(Poly tmp = top.next; tmp != null; tmp = tmp.next) {//Checks the other nodes to see if they are not null.
			if(tmp.coefficient < 0) {
				expressionBuilder.append(" - ").append(tmp.toString());//If the coefficient is a negative value, switch the sign to a minus sign.
			}
			else {
				expressionBuilder.append(" + ").append(tmp.toString());//If the coefficient is a positive value, set the sign to a plus sign.
			}
		}
		
		return expressionBuilder.toString();//Put it all together.
	}
	
	
//Class: Set the values and create the polynomial into a string format.
	public static class Poly {
		public double coefficient;
		public int exponent;
		public Poly next;
		private Poly top;
	
	//Constructor: Set the values to a default.
		public Poly(double c, int e) {
			coefficient = c;
			exponent = e;
			next = null;
		}

	//Method: Get the exponent and then return it.
		public int getExponent() {
			return this.exponent;
		}
		
	//Method: Get the coefficient and then return it.
		public double getCoefficient() {
			return this.coefficient;
		}
		
	//Method: Get the next value and then return it.
		public Poly getNext() {
			return next;
		}
		
	//Method: Print the polynomial expression in legible form.
		@Override
		public String toString() {
			String termString = String.format("", Math.abs(coefficient));
			if(coefficient < 0) {
				coefficient = coefficient * -1;//This solves the double negative issue. The sign change takes care of the coefficient signage.
			}
			
			if(exponent == 0) {
				return coefficient + termString;//A
			}
			else if(exponent == 1) {
				return coefficient + termString + "x";//Ax
			}
			else {
				return coefficient + termString + "x^" + exponent;//Ax^n
			}
		}
		
		public Poly getTop() {
			return top;
		}
	}
}


