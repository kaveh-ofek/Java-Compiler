package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.ScopeException;
import oop.ex6.scopes.VariableException;

/**
 * an abstract class for a given line, the class will specify the line type according to it's regex.
 */
public abstract class AllLineAnalyze {

	/**
	 * regex of boolean expression
	 */
	protected static final String BOOL_REGEX = "true|false";

	/**
	 * regex of char expression
	 */
	protected static final String CHAR_REGEX = "'.'";

	/**
	 * regex of int expression
	 */
	protected static final String INT_REGEX = "-?\\d+";

	/**
	 * regex for double expression
	 */
	protected static final String DOUBLE_REGEX = "-?(?:(?:\\d+\\.?\\d*)|(?:\\d*\\.?\\d+))";

	/**
	 * regex for string expression
	 */
	protected static final String STRING_REGEX = "\\\".*\\\"";

	/**
	 * line to analyze
	 */
	protected String givenLine;
	/**
	 * the scope of the line we analyzing
	 */
	protected Scope givenScope;

	/**
	 * the flag which hints if the analyzer should only inspect the global scope (register the global
	 * variable and the functions only) or parse the whole code
	 */
	protected boolean globalInspection=false;

	/**
	 * abstract method that will be implemented on sub classes, the class may throw many exceptions.
	 * @return the updated scope or new one on few types.
	 * @throws IllegalFormatException exception of invalid
	 * @throws ScopeException exception of scope, which is variable exception and function exception
	 */
	public abstract Scope analyze() throws IllegalFormatException, ScopeException;


	/**
	 * the method will check if the RHS and LHS equals to the same type
	 * @param leftType LHS
	 * @param rightType RHS
	 * @return boolean value, whether they equals to the same type
	 */
	protected static boolean isTypeCompatible(String leftType, String rightType) {
		if (leftType.equals("int")) {
			return rightType.equals("int");
		}
		if (leftType.equals("double")) {
			return rightType.equals("double") || rightType.equals("int");
		}
		if (leftType.equals("String")) {
			return rightType.equals("String");
		}
		if (leftType.equals("char")) {
			return rightType.equals("char");
		}
		if (leftType.equals("boolean")) {
			return rightType.equals("boolean") || rightType.equals("double") || rightType.equals("int");
		}
		return false;
	}

	/**
	 * the method will receive a variable and return its type, recognized by regex.
	 * @param variable given variable
	 * @return the type of the variable
	 */
	protected static String getConstantType(String variable) {
		String type = null;
		if (variable.matches(INT_REGEX)) {
			type = "int";
		} else if (variable.matches(DOUBLE_REGEX)) {
			type = "double";
		} else if (variable.matches(CHAR_REGEX)) {
			type = "char";
		} else if (variable.matches(STRING_REGEX)) {
			type = "String";
		} else if (variable.matches(BOOL_REGEX)) {
			type = "boolean";
		}
		return type;
	}

}


