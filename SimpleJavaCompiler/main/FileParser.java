package oop.ex6.main;

import oop.ex6.scopes.Scope;
import oop.ex6.singlelineanalayze.AnalyzeLineFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * abstract class that parse given file
 */
public abstract class FileParser {

	/**
	 * the method will convert a given file to array list of strings when each string is a line in the file
	 * @param givenFile given file
	 * @return array list of strings
	 * @throws FileNotFoundException exception when file not found
	 */
	private static ArrayList<String> fileToArray(File givenFile) throws FileNotFoundException {
		Scanner s = new Scanner(givenFile);
		ArrayList<String> listOfLines = new ArrayList<String>();
		while (s.hasNextLine()) {
			listOfLines.add(s.nextLine());
		}
		s.close();
		return listOfLines;
	}

	/**
	 * the method will parse a given file along with the factory class
	 * @param givenFile given file
	 * @return result of success or failure
	 */
	public static int parseFile(File givenFile) {
		int returnValue = 0;
		int i = 0;
		try {
			ArrayList<String> listOfAllStrings = fileToArray(givenFile);
			Scope currentScope = new Scope();
			for (i = 0; i < listOfAllStrings.size(); i++) {
				currentScope = AnalyzeLineFactory.doLine(listOfAllStrings.get(i), currentScope, true);
			}
			for (i = 0; i < listOfAllStrings.size(); i++) {
				currentScope = AnalyzeLineFactory.doLine(listOfAllStrings.get(i), currentScope, false);
			}
		} catch (Exception e) {
			returnValue = 1;
			System.err.println("in line " + (i + 1) + ": " + e.getMessage());
		}
		return returnValue;
	}
}
