package oop.ex6.singlelineanalayze;


import oop.ex6.scopes.*;
import oop.ex6.scopes.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * declaration declaration-specified line analyzer
 */
public abstract class DeclarationLineAnalyze extends AllLineAnalyze {

	/**
	 * regex of delimiter to split with
	 */
	protected static final String DELIMITER = ",";

	/**
	 * regex made to recognize correct int line
	 */
	private static final String INT_REGEX = "\\s*(final\\s+)?int.+";

	/**
	 * regex made to recognize correct double line
	 */
	private static final String DOUBLE_REGEX = "\\s*(final\\s+)?double.+";

	/**
	 * regex made to recognize correct char line
	 */
	private static final String CHAR_REGEX = "\\s*(final\\s+)?char.+";

	/**
	 * regex made to recognize correct bool line
	 */
	private static final String BOOL_REGEX = "\\s*(final\\s+)?boolean.+";

	/**
	 * regex made to recognize correct string line
	 */
	private static final String STRING_REGEX = "\\s*(final\\s+)?String.+";

	/**
	 * regex made to recognize error msg
	 */
	private static final String ERROR_MSG = "Unrecognized line type";

	/**
	 * the name of the type
	 */
	protected String typeName;

	/**
	 * regex used to trim the line
	 */
	protected String trimRegex;

	/**
	 * regex used to split the line
	 */
	protected String splitRegex;

	/**
	 * regex for constance variable
	 */
	protected String constantVarRegex;


	/**
	 * constructor for the end of block type analyzer
	 * @param givenLine given line
	 * @param givenScope given scope
	 * @param globalInspection a flag which states if the analyzer is in global inspection mode
	 */
	public DeclarationLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
		this.givenLine = givenLine;
		this.givenScope = givenScope;
		this.globalInspection = globalInspection;
	}

	/**
	 * the method will check the type of the line according to given regex
	 * @param givenLine given line
	 * @return the type of the line
	 * @throws IllegalFormatException exception of invalid format
	 */
	public static DeclarationTypes checkLine(String givenLine) throws IllegalFormatException {
		if (givenLine.matches(INT_REGEX)) {
			return DeclarationTypes.INT;
		}
		if (givenLine.matches(DOUBLE_REGEX)) {
			return DeclarationTypes.DOUBLE;
		}
		if (givenLine.matches(CHAR_REGEX)) {
			return DeclarationTypes.CHAR;
		}
		if (givenLine.matches(BOOL_REGEX)) {
			return DeclarationTypes.BOOLEAN;
		}
		if (givenLine.matches(STRING_REGEX)) {
			return DeclarationTypes.STRING;
		}
		throw new IllegalFormatException(ERROR_MSG);
	}

	/**
	 * check if the variable is final
	 * @return boolean result
	 */
	protected boolean checkIfFinal() {
		return this.givenLine.matches("\\s*final.+");
	}

	/**
	 * trim the word "final" from the line
	 */
	protected void trimFinal() {
		this.givenLine = this.givenLine.replaceFirst("\\s*final", "");
	}


	/**
	 * implement of the analyze virtual method, specified for a declaration line
	 * @return the scope after update or either new scope
	 * @throws IllegalFormatException exception of invalid format
	 */
	public Scope analyze() throws IllegalFormatException, VariableException {
		if (globalInspection && this.givenScope.getScopeDepth() != Scope.GLOBAL_DEPTH) {
			return this.givenScope;
		}
		if (!globalInspection && this.givenScope.getScopeDepth() == Scope.GLOBAL_DEPTH) {
			return this.givenScope;
		}
		boolean isFinal = false;
		if (this.checkIfFinal()) {
			isFinal = true;
			trimFinal();
		}
		this.trimLine();
		String[] vars = this.givenLine.split(DELIMITER);
		for (String var : vars) {
			this.addVarToScope(var, isFinal);
		}
		this.givenScope.setHadReturn(false);
		return this.givenScope;
	}


	/**
	 * the method will trim the line using the line trim regex
	 * @throws IllegalFormatException exception of invalid format
	 */
	private void trimLine() throws IllegalFormatException {
		Pattern trimPattern = Pattern.compile(this.trimRegex);
		Matcher trimMatcher = trimPattern.matcher(this.givenLine);
		if (!trimMatcher.matches()) {
			throw new IllegalFormatException("Declaration format is invalid");
		}
		this.givenLine = trimMatcher.group(1);
	}

	/**
	 * the method will add the declaration of a variable to the scope information
	 * @param varDeclaration string of the var declare
	 * @param isFinal whether the variable is final
	 * @throws IllegalFormatException exception of invalid format
	 * @throws VariableException exception of variable
	 */
	private void addVarToScope(String varDeclaration, boolean isFinal) throws IllegalFormatException,
																			  VariableException {
		Pattern splitPattern = Pattern.compile(this.splitRegex);
		Matcher splitMatcher = splitPattern.matcher(varDeclaration);
		if (!splitMatcher.matches()) {
			throw new IllegalFormatException("Invalid variable format");
		}
		String varName = splitMatcher.group("name");
		String varValue = null;
		if (splitMatcher.group("value")!=null) {
			varValue = splitMatcher.group("value");
			if (!varValue.matches(this.constantVarRegex)) {
				String valueType = this.givenScope.getVariableType(varValue);
				varValue = this.givenScope.getVariableValue(varValue);
				if (!isTypeCompatible(this.typeName, valueType)) {
					throw new IllegalFormatException(
							"Cannot assign type " + valueType + " to type " + this.typeName);
				}

			}
		} else if (isFinal) {
			throw new IllegalFormatException("Final variable must be initialized");
		}
		this.givenScope.addLocalVariable(varName, this.typeName, varValue, isFinal);

	}


}
