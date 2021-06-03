package oop.ex6.scopes;

/**
 * This exception is thrown when a given scope is null when it shouldn't be
 */
public class ScopeIsNullException extends ScopeException {

	/** The error message */
	private static final String errorMsg = "Outer scope is null";

	/** serialization ID */
	private static final long serialVersionUID = 98602490285490986L;

	/**
	 * The default constructor
	 */
	public ScopeIsNullException() {
		super(errorMsg);
	}
}
