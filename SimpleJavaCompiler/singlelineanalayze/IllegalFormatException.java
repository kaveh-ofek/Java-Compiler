package oop.ex6.singlelineanalayze;

/**
 * class for invalid format of a line
 */
public class IllegalFormatException extends Exception
{
    /**
     * message template of invalid format
     */
    private static final String errorMsg="Illegal format: ";

    /** serialization ID */
    private static final long serialVersionUID = 4576019423951488297L;

    /**
     * public constructor made of the super class that prints the error message
     * @param error error message to print
     */
    public IllegalFormatException(String error)
    {
        super(errorMsg+error);
    }
}
