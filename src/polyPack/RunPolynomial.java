package polyPack;
/**
 * Author: Andrew Roney
 * Project Name: Polynomial_Project
 * Date: 09/06/2022
 * Class Description: This class will allow the user to select a file. It also sets up the list to be used for the polynomial class. This is also the main class it is where the main(String [] args) is located at.
 */

import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunPolynomial {
	
	public static List<Polynomial> polyList = new ArrayList<>();
	
	public static void main(String[] args) {
		polyListProcess();
	}
	
//Method: Open a file, then token it into an ArrayList
	public static ArrayList<String> readFile(){
		//Create ArrayList and JFileChooser
		ArrayList<String> polyList = new ArrayList<>();
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//Displays directory and files.
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));//Uses current directory
		
		int response = chooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION) {//Read the file with scanner.
			File file = chooser.getSelectedFile();
			try {
				Scanner fileIn = new Scanner(file);
				if(file.isFile()) {
					while(fileIn.hasNextLine()) {
						String expression = fileIn.nextLine();
						polyList.add(expression);
					}
				}
			}
			
			catch(NoSuchElementException nse) {
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "This file is empty!");
			}
			catch(FileNotFoundException fne) {
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The file could not be found!");
			}
		}

		return polyList;//Return the Array List.
	}
	
//Method: Check the order of the polynomial list.
	public static boolean checkOrder(List<Polynomial> polyList) {
		boolean orderCheck = true;
		Polynomial previous = polyList.get(polyList.size() - 1);
		
		for(int i = polyList.size() - 2; i > 0; i--) {
			if(previous.comparePoly(polyList.get(i)) < 0) {
				orderCheck = false;
			}
		}
		
		return orderCheck;
	}
	

	public static void polyListProcess(){
		try {
			ArrayList<String> a = readFile();
			for(String element : a) {
				Polynomial p = new Polynomial(element);
				System.out.println(p);
				polyList.add(p);
			}
		}
		catch(InvalidPolynomialSyntax ex) {
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
		}
		
		System.out.println("The Strong Ordered Polynomial: "+ OrderedList.checkSorted(polyList));
		System.out.println("The Weak Ordered Polynomial: "+ checkOrder(polyList));
	}
}
