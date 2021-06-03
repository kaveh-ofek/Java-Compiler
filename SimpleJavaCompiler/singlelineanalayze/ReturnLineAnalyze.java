package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VariableException;

/**
 * return-specified line analyzer
 */
public class ReturnLineAnalyze extends AllLineAnalyze {

	/**
	 * regex made to recognize correct return line
	 */
	private static final String RETURN_REGEX = "\\s*return\\s*;\\s*";

	/**
	 * constructor for the return type analyzer
	 * @param givenLine given line
	 * @param givenScope given scope
	 */
	public ReturnLineAnalyze(String givenLine, Scope givenScope) {
		this.givenLine = givenLine;
		this.givenScope = givenScope;
	}

	/**
	 * implement of the analyze virtual method, specified for a return line
	 * @return the scope after update or either new scope
	 * @throws IllegalFormatException exception of invalid format
	 * @throws VariableException exception of variables
	 * @throws FunctionException exception of function/function names
	 */
	@Override
	public Scope analyze() throws IllegalFormatException, VariableException, FunctionException {
		if (!this.givenLine.matches(RETURN_REGEX)) {
			throw new IllegalFormatException("Return format is invalid");
		}
		if (this.givenScope.getScopeDepth() == Scope.GLOBAL_DEPTH) {
			throw new IllegalFormatException("Cannot return in the global scope");
		}
		this.givenScope.setHadReturn(true);
		return this.givenScope;
	}
}
