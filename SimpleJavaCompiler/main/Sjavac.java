package oop.ex6.main;

import java.io.File;

/**
 * The main class of the program
 */
public class Sjavac {

	/**
	 * The main program, which checks if a file is written correctly in the Simple Java language
	 * @param args the arguments of the program
	 */
	public static void main (String [] args){
		File fileToCheck = new File(args[0]);
		if (!fileToCheck.exists())
		{
			System.err.println("IO Error");
			System.out.println(2);
		}
		else
		{
			int returnValue = FileParser.parseFile(fileToCheck);
			System.out.println(returnValue);
		}
	}
}
