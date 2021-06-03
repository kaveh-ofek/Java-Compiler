package oop.ex6.scopes;

/**
 * This exception is an abstract exception which represents all of the variable exceptions
 */
public abstract class VariableException extends ScopeException {

    /** serialization ID */
    private static final long serialVersionUID = 5280660128795106292L;

    /**
     * The default constructor
     * @param errorMsg the error message
     */
    public VariableException(String errorMsg)
    {
        super(errorMsg);
    }
}
