package oop.ex6.singlelineanalayze;

/**
 * a class made to recognize the correct enum which signs the type of the line according to the regex of
 * each type
 */
public abstract class LineRecognizer
{
    /**
     * regex for documentation type
     */
    private static final String DOC_REGEX = "//";

    /**
     * regex for method type
     */
    private static final String METHOD_REGEX = "\\s*void.+\\{\\s*";

    /**
     * regex for method call type
     */
    private static final String CALL_REGEX="\\s*([a-zA-Z]\\w*)\\s*\\(.*\\)\\s*;\\s*";

    /**
     * regex for condition type
     */
    private static final String CONDITION_REGEX = "\\s*(if|while).+\\{\\s*";

    /**
     * regex for declaration type
     */
    private static final String DECLARATION_REGEX = "\\s*(final\\s+)?(int|char|boolean|String|double).+;\\s*";

    /**
     * regex for input type
     */
    private static final String INPUT_REGEX = "\\s*([a-zA-Z]\\w*|_\\w+)\\s*=\\s*(.+)\\s*;\\s*";

    /**
     * regex for end of block type
     */
    private static final String END_OF_BLOCK_REGEX="\\s*\\}\\s*";

    /**
     * regex for empty line type
     */
    private static final String EMPTY_LINE_REGEX="\\s*";

    /**
     * regex for return type
     */
    private static final String RETURN_REGEX="\\s*return\\s*;\\s*";

    /**
     * the method recognize the type of the line according to the correct regex
     * @param givenLine given line
     * @return type of the line
     * @throws IllegalFormatException exception of invalid line format
     */
    public static LineTypes recognize(String givenLine) throws IllegalFormatException {
        if (givenLine.startsWith(DOC_REGEX))
        {
            return LineTypes.DOCUMENTATION;
        }

        if (givenLine.matches(METHOD_REGEX))
        {
            return LineTypes.METHOD;
        }

        if (givenLine.matches(CONDITION_REGEX))
        {
            return LineTypes.CONDITION;
        }
        if (givenLine.matches(RETURN_REGEX))
        {
            return LineTypes.RETURN;
        }

        if (givenLine.matches(DECLARATION_REGEX))
        {
            return LineTypes.DECLARATION;
        }
        if (givenLine.matches(END_OF_BLOCK_REGEX))
        {
            return LineTypes.END_OF_BLOCK;
        }

        if (givenLine.matches(INPUT_REGEX))
        {
            return LineTypes.INPUT;
        }

        if (givenLine.matches(CALL_REGEX))
        {
            return LineTypes.CALL;
        }
        if (givenLine.matches(EMPTY_LINE_REGEX))
        {
            return LineTypes.EMPTY;
        }

        throw new IllegalFormatException("Unrecognized line type");
    }
}
