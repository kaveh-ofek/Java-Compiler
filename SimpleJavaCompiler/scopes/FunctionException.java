package oop.ex6.scopes;

/**
 * This exception is an abstract exception which represents all of the function exceptions
 */
public abstract class FunctionException extends ScopeException {

    /** serialization ID */
    private static final long serialVersionUID = -6228283256436426660L;

    /**
     * The default constructor
     * @param errorMsg the error message
     */
    public FunctionException(String errorMsg)
    {
        super(errorMsg);
    }
}
