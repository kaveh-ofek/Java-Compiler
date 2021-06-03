package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * call-specified line analyzer
 */
public class CallLineAnalyze extends AllLineAnalyze {

	/**
	 * regex made to trim the line
	 */
	private static final String TRIM_REGEX = "\\s*(?<name>[a-zA-Z]\\w*)\\s*\\((?<args>.*)\\)\\s*;\\s*";

	/**
	 * regex made to split trim the arguments
	 */
	private static final String ARG_REGEX = "\\s*(?<name>[\\w\\.-]+|\".*\"|'.')\\s*";


	/**
	 * constructor for the 'call' type analyzer
	 * @param givenLine given line
	 * @param givenScope given scope
	 * @param globalInspection flag for the global inspect
	 */
	public CallLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
		this.givenLine = givenLine;
		this.givenScope = givenScope;
		this.globalInspection = globalInspection;
	}

	/**
	 * implement of the analyze virtual method, specified for a "function call" line
	 * @return the scope after update or either new scope
	 * @throws IllegalFormatException exception of invalid format
	 * @throws VariableException exception of variables
	 * @throws FunctionException exception of function/function names
	 */
	@Override
	public Scope analyze() throws IllegalFormatException, VariableException, FunctionException {
		if (this.givenScope.getScopeType() == Scope.ScopeType.GLOBAL)
		{
			throw new IllegalFormatException("Cannot call to function in global scope");
		}
		if (this.globalInspection) {
			return this.givenScope;
		}
		Pattern callPattern = Pattern.compile(TRIM_REGEX);
		Matcher callMatcher = callPattern.matcher(this.givenLine);
		if (!callMatcher.matches()) {
			throw new IllegalFormatException("Call format is invalid");
		}
		String functionName = callMatcher.group("name");
		String[] functionArgs;
		if (callMatcher.group("args").matches("\\s*")) {
			functionArgs = new String[0];
		} else {
			functionArgs = callMatcher.group("args").split(",");
		}
		for (int i = 0; i < functionArgs.length; i++) {
			AnalyzeArgument(functionName, functionArgs[i], i);
		}
		if (this.givenScope.getArgumentCount(functionName) != functionArgs.length) {
			throw new IllegalFormatException("arguments amount is different than the function's " +
											 "declaration");
		}
		this.givenScope.setHadReturn(false);
		return this.givenScope;

	}

	/**
	 * the method will analyze the argument of a function according to its name and the argument index
	 * @param functionName the name of the function
	 * @param argument the argument as a string
	 * @param argIndex and index of the argument
	 * @throws IllegalFormatException exception of invalid format
	 * @throws VariableException exception of variables
	 * @throws FunctionException exception of function/function names
	 */
	private void AnalyzeArgument(String functionName, String argument, int argIndex)
			throws IllegalFormatException, VariableException, FunctionException {
		Pattern argPattern = Pattern.compile(ARG_REGEX);
		Matcher argMatcher = argPattern.matcher(argument);
		if (!argMatcher.matches()) {
			throw new IllegalFormatException("Argument format is invalid");
		}
		String argumentName = argMatcher.group("name");
		String argumentType = getConstantType(argumentName);
		if (argumentType == null) {
			argumentType = this.givenScope.getVariableType(argumentName);
			try {
				this.givenScope.getVariableValue(argumentName);
			} catch (VariableException e) {
				throw e;
			}
		}
		String expectedType = this.givenScope.getArgumentType(functionName, argIndex);
		if (!isTypeCompatible(expectedType, argumentType)) {
			throw new IllegalFormatException(
					"Cannot assign type " + argumentType + " to type " + expectedType);
		}
	}
}
