package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * condition-specified line analyzer
 */
public class ConditionLineAnalyze extends AllLineAnalyze
{
    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX="\\s*(?:if|while)\\s*\\(\\s*(.+)\\s*\\)\\s*\\{\\s*";

    /**
     * regex made to split with delimiters
     */
    private static final String DELIMITER="\\|\\||&&";

    /**
     * regex made to recognize an element in the condition
     */
    private static final String ELEMENT_REGEX="\\s*([\\w\\.-]+)\\s*";

    /**
     * regex made to recognize boolean format
     */
    private static final String BOOL_REGEX = "true|false";

    /**
     * regex made to recognize int format
     */
    private static final String INT_REGEX = "-?\\d+";

    /**
     * regex made to recognize double format
     */
    private static final String DOUBLE_REGEX = "-?(?:(?:\\d+\\.?\\d*)|(?:\\d*\\.?\\d+))";

    /**
     * regex made to recognize int label
     */
    private static final String INT_LABEL="int";

    /**
     * regex made to recognize double label
     */
    private static final String DOUBLE_LABEL="double";

    /**
     * regex made to recognize boolean label
     */
    private static final String BOOL_LABEL="boolean";

    /**
     * constructor for the 'condition' type analyzer
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect
     */
    public ConditionLineAnalyze(String givenLine, Scope givenScope,boolean globalInspection)
    {
        this.givenLine = givenLine;
        this.givenScope = givenScope;
        this.globalInspection=globalInspection;
    }

    /**
     * implement of the analyze virtual method, specified for a condition line
     * @return the scope after update or either new scope
     * @throws IllegalFormatException exception of invalid format
     * @throws ScopeException exception of scope
     */
    @Override
    public Scope analyze() throws IllegalFormatException, ScopeException {
        Pattern conditionPattern=Pattern.compile(TRIM_REGEX);
        Matcher conditionMatcher=conditionPattern.matcher(this.givenLine);
        if (!conditionMatcher.matches())
        {
            throw new IllegalFormatException("Condition format is invalid");
        }
        if (this.givenScope.getScopeDepth()==Scope.GLOBAL_DEPTH)
        {
            throw new IllegalFormatException("If/While block cannot be declared in the global scope");
        }
        if (globalInspection)
        {
            this.givenScope.setHadReturn(false);
            return new Scope(this.givenScope, Scope.ScopeType.CONDITION);
        }
        this.givenLine=conditionMatcher.group(1);
        String[] conditions=this.givenLine.split(DELIMITER);
        for (String condition: conditions)
        {
            this.AnalyzeCondition(condition);
        }
        this.givenScope.setHadReturn(false);
        return new Scope(this.givenScope, Scope.ScopeType.CONDITION);
    }

    /**
     * the method will analyze a given condition according to the element regex and the correct format
     * @param condition given condition
     * @throws IllegalFormatException exception of invalid format
     * @throws VariableException exception of variable
     */
    private void AnalyzeCondition(String condition) throws IllegalFormatException, VariableException {
        Pattern conditionPattern=Pattern.compile(ELEMENT_REGEX);
        Matcher conditionMatcher=conditionPattern.matcher(condition);
        if (!conditionMatcher.matches())
        {
            throw new IllegalFormatException("Condition argument format is invalid");
        }
        String element=conditionMatcher.group(1);
        if (element.matches(BOOL_REGEX) || element.matches(INT_REGEX) || element.matches(DOUBLE_REGEX))
        {
            return;
        }
        String elementType=this.givenScope.getVariableType(element);
        try
        {
            this.givenScope.getVariableValue(element);
        }
        catch(VariableNotInitializedException e)
        {
            throw e;
        }
        if (!elementType.equals(INT_LABEL) && !elementType.equals(DOUBLE_LABEL) &&
                !elementType.equals(BOOL_LABEL)) {
            throw new IllegalFormatException("Invalid type of condition argument");
        }


    }
}
