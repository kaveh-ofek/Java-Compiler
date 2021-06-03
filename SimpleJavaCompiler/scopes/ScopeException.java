package oop.ex6.scopes;

/**
 * This exception is an abstract exception which represents all of the scope exceptions
 */
public abstract class ScopeException extends Exception {

	/** serialization ID */
	private static final long serialVersionUID = -682280202274220900L;

	/**
	 * The default constructor
	 * @param errorMsg the error message
	 */
	public ScopeException(String errorMsg) {
		super(errorMsg);
	}
}
