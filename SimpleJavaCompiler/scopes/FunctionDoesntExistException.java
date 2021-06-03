package oop.ex6.scopes;

/**
 * This exception is thrown if a function doesn't exist when it should
 */
public class FunctionDoesntExistException extends FunctionException {

    /** The first error message */
    private static final String errorMsgAfter=" doesn't exist";

    /** The second error message */
    private static final String errorMsgBefore="the function ";

    /** serialization ID */
    private static final long serialVersionUID = 7467725526762648015L;

    /**
     * The default constructor
     * @param functionName The function which should exist
     */
    public FunctionDoesntExistException(String functionName)
    {
        super(errorMsgBefore+functionName+errorMsgAfter);
    }
}
