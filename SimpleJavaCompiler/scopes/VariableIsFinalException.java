package oop.ex6.scopes;

/**
 * This exception is thrown if a variable is final when it shouldn't
 */
public class VariableIsFinalException extends VariableException {

    /** The error message */
    private static final String errorMsg = " cannot be edited";

    /** serialization ID */
    private static final long serialVersionUID = -754045493020503279L;

    /**
     * The default constructor
     * @param varName the variable which is final
     */
    public VariableIsFinalException(String varName) {
        super(varName + errorMsg);
    }
}
