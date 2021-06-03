package oop.ex6.scopes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a scope of the code - for example, a workspace of a function or of a while block.
 * The global code is also represented by a scope.
 */
public class Scope {

	/**
	 * An enum which represents the type of the scope.
	 */
	public enum ScopeType {
		/** global scope */
		GLOBAL,

		/** method scope */
		METHOD,

		/** condition (if/while) scope */
		CONDITION
	}

	/** The default (and the lowest) depth of a scope */
	public static final int GLOBAL_DEPTH = 1;


	/** The scope which the current scope is nested in (null if it's the global scope) */
	private Scope outerScope;

	/** The table of the scope's local variables */
	private HashMap<String, Variable> mapOfVariables;

	/** The depth of the current scope */
	private final int scopeDepth;

	/** The global table of function declarations of the file */
	private HashMap<String, ArrayList<Variable>> mapOfFunctions;

	/** The type of the scope */
	private final ScopeType scopeType;

	/** A flag which remembers if the last line which was analyzed is a return statement */
	private boolean hadReturn;


	/**
	 * The default constructor of a scope. assumes that this scope is a global one (since it's not nested)
	 */
	public Scope() {
		this.outerScope = null;
		this.mapOfVariables = new HashMap<String, Variable>();
		this.scopeDepth = GLOBAL_DEPTH;
		this.mapOfFunctions = new HashMap<String, ArrayList<Variable>>();
		this.scopeType = ScopeType.GLOBAL;
		this.hadReturn = false;
	}


	/**
	 * The constructor for scopes which are not global
	 * @param givenOuterScope The scope which the current scope is nested in
	 * @param type The type of the current scope
	 * @throws ScopeException If the outer scope in null
	 */
	public Scope(Scope givenOuterScope, ScopeType type) throws ScopeException {
		if (givenOuterScope == null) {
			throw new ScopeIsNullException();
		}
		this.mapOfFunctions = givenOuterScope.mapOfFunctions;
		this.scopeDepth = givenOuterScope.scopeDepth + 1;
		this.mapOfVariables = new HashMap<String, Variable>();
		this.scopeType = type;
		if (givenOuterScope.getScopeType() == ScopeType.GLOBAL) {
			Scope virtualGlobalScope = new Scope();
			virtualGlobalScope.mapOfVariables = this.cloneVariableMap(givenOuterScope.mapOfVariables);
			virtualGlobalScope.mapOfFunctions = this.mapOfFunctions;
			virtualGlobalScope.outerScope = givenOuterScope;
			this.outerScope = virtualGlobalScope;
		} else {
			this.outerScope = givenOuterScope;
		}
	}

	/**
	 * This function creates a deep copy of an existing variable table
	 * @param originalMap The original variable table
	 * @return The instance of the copied table
	 */
	private HashMap<String, Variable> cloneVariableMap(HashMap<String, Variable> originalMap) {
		HashMap<String, Variable> newMap = new HashMap<String, Variable>();
		for (Map.Entry<String, Variable> variableEntry : originalMap.entrySet()) {
			newMap.put(variableEntry.getKey(), new Variable(variableEntry.getValue()));
		}
		return newMap;
	}


	/**
	 * This function adds a new function to the functions table
	 * @param functionName The function which should be added
	 * @throws FunctionAlreadyExistException If the function already exists in teh table
	 */
	public void createFunction(String functionName) throws FunctionAlreadyExistException {
		if (this.mapOfFunctions.containsKey(functionName)) {
			throw new FunctionAlreadyExistException(functionName);
		}
		this.mapOfFunctions.put(functionName, new ArrayList<Variable>());
	}

	/**
	 * This function adds an argument to the argument list of a function
	 * @param functionName The function which the argument should be added to
	 * @param name The name of the argument (saved in order to not lose given data, although it is not used
	 * in this code)
	 * @param type The type of the argument
	 * @param isFinal A flag which represents if the argument is final
	 * @throws FunctionDoesntExistException If the function doesn't exist in the file
	 */
	public void addArgumentToFunction(String functionName, String name, String type, boolean isFinal)
			throws FunctionDoesntExistException {
		Variable newVar = new Variable(name, type, null, isFinal);
		if (!this.mapOfFunctions.containsKey(functionName)) {
			throw new FunctionDoesntExistException(functionName);
		}
		this.mapOfFunctions.get(functionName).add(newVar);
	}

	/**
	 * This function returns the type of the [argumentIndex]'s argument in the function [functionName]
	 * @param functionName The function which the argument belongs to
	 * @param argumentIndex The index of the argument
	 * @return Returns the type of the [argumentIndex]'s argument in the function [functionName]
	 * @throws FunctionDoesntExistException If the function doesn't exist
	 * @throws FunctionArgumentRangeException If the argument index is out of range
	 */
	public String getArgumentType(String functionName, int argumentIndex)
			throws FunctionException {
		if (!this.mapOfFunctions.containsKey(functionName)) {
			throw new FunctionDoesntExistException(functionName);
		}
		ArrayList<Variable> arguments = this.mapOfFunctions.get(functionName);
		if (arguments.size() - 1 < argumentIndex) {
			throw new FunctionArgumentRangeException(functionName, argumentIndex);
		}
		return arguments.get(argumentIndex).getVariableType();

	}

	/**
	 * This function returns the number of arguments a function has
	 * @param functionName The function which is checked
	 * @return The number of arguments a function has
	 * @throws FunctionDoesntExistException If the function doesn't exist in the code
	 */
	public int getArgumentCount(String functionName) throws FunctionDoesntExistException {
		if (!this.mapOfFunctions.containsKey(functionName)) {
			throw new FunctionDoesntExistException(functionName);
		}
		return this.mapOfFunctions.get(functionName).size();
	}


	/**
	 * This function adds a local variable to the current scope
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param value The value of the variable
	 * @param isFinal A flag which tells if the variable is final or not
	 * @throws VariableAlreadyExist If the variable is already defined in the local scope
	 */
	public void addLocalVariable(String name, String type, String value, boolean isFinal) throws
																						  VariableAlreadyExist {
		if (this.mapOfVariables.containsKey(name)) {
			throw new VariableAlreadyExist(name);
		}
		Variable newVar = new Variable(name, type, value, isFinal);
		this.mapOfVariables.put(name, newVar);
	}

	/**
	 * This function returns the scope which the current scope is nested in.
	 * @return The scope which the current scope is nested in.
	 */
	public Scope getOuterScope() {
		if (this.outerScope == null) {
			return null;
		}
		if (this.outerScope.scopeType == ScopeType.GLOBAL) {
			return this.outerScope.outerScope;
		}
		return this.outerScope;
	}

	/**
	 * This function returns the Variable object which corresponds to the name which was given
	 * @param name The name of teh variable
	 * @return the Variable object which corresponds to the name which was given. Returns null if there is
	 * no such variable
	 */
	private Variable getVariableFromScopes(String name) {
		Scope currentScope = this;
		while (currentScope != null) {
			if (currentScope.mapOfVariables.containsKey(name)) {
				return currentScope.mapOfVariables.get(name);
			}
			currentScope = currentScope.outerScope;
		}
		return null;
	}

	/**
	 * This function returns the type of the variable which its name was given as an input
	 * @param name The name of the variable
	 * @return The type of the variable which its name was given as an input
	 * @throws VariableNotFoundException If there is no such variable
	 */
	public String getVariableType(String name) throws VariableNotFoundException {
		Variable varToGet = this.getVariableFromScopes(name);
		if (varToGet == null) {
			throw new VariableNotFoundException(name);
		}
		return varToGet.getVariableType();
	}

	/**
	 * This function returns the value of the variable which its name was given as an input
	 * @param name The name of the variable
	 * @return The value of the variable which its name was given as an input
	 * @throws VariableNotFoundException If there is no such variable
	 * @throws VariableNotInitializedException If the variable is not initialized
	 */
	public String getVariableValue(String name) throws VariableNotFoundException,
													   VariableNotInitializedException {
		Variable varToGet = this.getVariableFromScopes(name);
		if (varToGet == null) {
			throw new VariableNotFoundException(name);
		}
		if (varToGet.getVariableValue() == null) {
			throw new VariableNotInitializedException(name);
		}
		return varToGet.getVariableValue();
	}

	/**
	 * This variable changes the value of a given variable
	 * @param name The name of the variable
	 * @param value The value we wish to set
	 * @throws VariableNotFoundException If there is no such variable
	 * @throws VariableIsFinalException If the variable is defined as a final variable
	 */
	public void editVariable(String name, String value) throws VariableNotFoundException,
															   VariableIsFinalException {
		Variable varToEdit = this.getVariableFromScopes(name);
		if (varToEdit == null) {
			throw new VariableNotFoundException(name);
		}
		if (varToEdit.isFinal()) {
			throw new VariableIsFinalException(name);
		}
		varToEdit.setVariableValue(value);
	}

	/**
	 * @return The depth of the current scope
	 */
	public int getScopeDepth() {
		return this.scopeDepth;
	}

	/**
	 * @return The type of the current scope
	 */
	public ScopeType getScopeType() {
		return scopeType;
	}

	/**
	 * @return If the previous line which was analyzed is a return commands
	 */
	public boolean HasReturn() {
		return hadReturn;
	}

	/**
	 * Updates the value of the return flag
	 * @param hadReturn the value to update
	 */
	public void setHadReturn(boolean hadReturn) {
		this.hadReturn = hadReturn;
	}
}

