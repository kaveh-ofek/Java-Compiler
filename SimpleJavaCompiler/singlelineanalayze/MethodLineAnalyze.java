package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.ScopeException;
import oop.ex6.scopes.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * method-specified line analyzer
 */
public class MethodLineAnalyze extends AllLineAnalyze {

	/**
	 * regex made to trim the line
	 */
	private static final String TRIM_REGEX = "\\s*void\\s+([a-zA-Z]\\w*)\\s*\\((.*)\\)\\s*\\{\\s*";

	/**
	 * regex of the possible arguments types
	 */
	private static final String ARG_TYPES = "int|boolean|String|double|char";

	/**
	 * regex made to split trim the arguments
	 */
	private static final String ARG_REGEX =
			"\\s*(?<isFinal>final\\s+)?(?<type>" + ARG_TYPES + ")\\s+(?<name>[a-zA-Z]\\w*|_\\w+)\\s*";


	/**
	 * constructor for the method type analyzer
	 * @param givenLine given line
	 * @param givenScope given scope
	 * @param globalInspection flag for the global inspect
	 */
	public MethodLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
		this.givenLine = givenLine;
		this.givenScope = givenScope;
		this.globalInspection = globalInspection;
	}

	/**
	 * implement of the analyze virtual method, specified for a method line
	 * @return the scope after update or either new scope
	 * @throws IllegalFormatException exception of invalid format
	 * @throws ScopeException exception of scope
	 * @throws FunctionException exception of function/function names
	 */
	@Override
	public Scope analyze() throws IllegalFormatException, ScopeException {
		if (this.givenScope.getScopeDepth()!=Scope.GLOBAL_DEPTH)
		{
			throw new IllegalFormatException("Nested functions are not allowed");
		}
		this.givenScope = new Scope(this.givenScope, Scope.ScopeType.METHOD);
		Pattern methodPattern = Pattern.compile(TRIM_REGEX);
		Matcher methodMatcher = methodPattern.matcher(this.givenLine);
		if (!methodMatcher.matches()) {
			throw new IllegalFormatException("Method does not match the format");
		}
		String functionName = methodMatcher.group(1);
		String[] arguments;
		if (methodMatcher.group(2).matches("\\s*")) {
			arguments = new String[0];
		} else {
			arguments = methodMatcher.group(2).split(",");
		}
		if (globalInspection) {
			this.givenScope.createFunction(functionName);
		}
		for (String argument : arguments) {
			analyzeArgument(functionName, argument);
		}
		return this.givenScope;

	}


	/**
	 * the method will analyze the arguments of the function
	 * @param functionName the name of the function
	 * @param argumentText the string of the argument
	 * @throws FunctionException exception of function
	 * @throws IllegalFormatException exception for invalid format of the line
	 * @throws VariableException exception of the variable
	 */
	private void analyzeArgument(String functionName, String argumentText)
			throws FunctionException, IllegalFormatException, VariableException {
		Pattern argumentPattern = Pattern.compile(ARG_REGEX);
		Matcher argumentMatcher = argumentPattern.matcher(argumentText);
		if (!argumentMatcher.matches()) {
			throw new IllegalFormatException("Argument format is invalid");
		}
		boolean isFinal = false;
		if (argumentMatcher.group("isFinal") != null) {
			isFinal = true;
		}
		String argType = argumentMatcher.group("type");
		String argName = argumentMatcher.group("name");
		if (globalInspection) {
			this.givenScope.addArgumentToFunction(functionName, argName, argType, isFinal);
		}
		this.givenScope.addLocalVariable(argName, argType, "", isFinal);

	}

}
