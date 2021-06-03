package oop.ex6.scopes;

/**
 * This exception is thrown if a variable already exists when it shouldn't
 */
public class VariableAlreadyExist  extends VariableException {

    /** The error message */
    private static final String errorMsg = " already exists";

    /** serialization ID */
    private static final long serialVersionUID = 361449443542202884L;

    /**
     * The default constructor
     * @param name The variable which already exists
     */
    public VariableAlreadyExist(String name) {
        super(name + errorMsg);
    }
}
