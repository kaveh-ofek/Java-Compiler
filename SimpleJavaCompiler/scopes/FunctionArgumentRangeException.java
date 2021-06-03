package oop.ex6.scopes;

/**
 * This exception is thrown when someone tried to reach a function argument which its index doesn't exist
 * in the function's argument list
 */
public class FunctionArgumentRangeException extends FunctionException{

	/** The error message */
	private static final String errorMsg =" doesn't accept this amount of arguments: ";

	/** serialization ID */
	private static final long serialVersionUID = 646386751994943038L;

	/**
	 * The default constructor
	 * @param functionName The function which the argument should be in
	 * @param index The index which was accessed
	 */
	public FunctionArgumentRangeException(String functionName, int index) {
		super(functionName+errorMsg+index);
	}
}
