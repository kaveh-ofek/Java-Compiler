package oop.ex6.scopes;

/**
 * This class represents a variable in the variable table
 */
public class Variable {

	/** The name of the variable */
	private final String variableName;

	/** The type of the variable */
	private final String variableType;

	/** The value of the variable */
	private String variableValue;

	/** A flag which represents if the variable is final */
	private final boolean isFinal;

	/**
	 * The default constructor of a variable
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param value The value of the variable
	 * @param isFinal A flag which represents if the variable is final
	 */
	public Variable(String name, String type, String value, boolean isFinal) {
		this.variableName = name;
		this.variableType = type;
		this.variableValue = value;
		this.isFinal = isFinal;
	}

	/**
	 * A copy constructor of a variable
	 * @param orgVariable the variable to copy from
	 */
	public Variable(Variable orgVariable) {
		this.variableName = orgVariable.getVariableName();
		this.variableType = orgVariable.getVariableType();
		this.variableValue = orgVariable.getVariableValue();
		this.isFinal = orgVariable.isFinal();
	}

	/**
	 * @return The name of the variable
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @return The type of the variable
	 */
	public String getVariableType() {
		return variableType;
	}

	/**
	 * @return The value of the variable
	 */
	public String getVariableValue() {
		return variableValue;
	}

	/**
	 * @return If the variable is final
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Sets the value of the variable
	 * @param variableValue The value to set
	 */
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
}
