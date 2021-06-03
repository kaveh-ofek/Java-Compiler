package oop.ex6.singlelineanalayze;

/**
 * enum class for the possible types of the lines
 */
public enum LineTypes
{
    /**
     * documentation line type
     */
    DOCUMENTATION,

    /**
     * method line type
     */
    METHOD,

    /**
     * condition line type
     */
    CONDITION,

    /**
     * declaration line type
     */
    DECLARATION,

    /**
     * input line type
     */
    INPUT,

    /**
     * end of block line type
     */
    END_OF_BLOCK,

    /**
     * call line type
     */
    CALL,

    /**
     * empty line type
     */
    EMPTY,

    /**
     * return line type
     */
    RETURN
}
