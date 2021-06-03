package oop.ex6.scopes;

/**
 * This exception is thrown when a function already exists when it shouldn't
 */
public class FunctionAlreadyExistException extends FunctionException {

    /** The first error message */
    private static final String errorMsgAfter=" already exist";

    /** The second error message */
    private static final String errorMsgBefore="the function ";

    /** serialization ID */
    private static final long serialVersionUID = -898655752741376212L;

    /**
     * The default constructor
     * @param functionName The function which shouldn't exist
     */
    public FunctionAlreadyExistException(String functionName)
    {
        super(errorMsgBefore+functionName+errorMsgAfter);
    }
}
