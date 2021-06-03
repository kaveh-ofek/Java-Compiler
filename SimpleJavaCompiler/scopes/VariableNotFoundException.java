package oop.ex6.scopes;

/**
 * This exception is thrown if a variable is not found when is should be found
 */
public class VariableNotFoundException extends VariableException {

    /** The error message */
    private static final String errorMsg=" doesn't exist";

    /** serialization ID */
    private static final long serialVersionUID = 3867375517948776661L;

    /**
     * The default constructor
     * @param varName The variable which should be found
     */
    public VariableNotFoundException(String varName)
    {
        super(varName+errorMsg);
    }
}
