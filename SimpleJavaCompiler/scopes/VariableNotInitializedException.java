package oop.ex6.scopes;

/**
 * This exception is thrown if a variable is not initialized when it should be
 */
public class VariableNotInitializedException extends VariableException {

    /** The error message */
    private static final String errorMsg=" is not initialized";

    /** serialization ID */
    private static final long serialVersionUID = 5601511234254069307L;

    /**
     * The default constructor
     * @param varName The variable which should be initialized
     */
    public VariableNotInitializedException(String varName)
    {
        super(varName+errorMsg);
    }
}
