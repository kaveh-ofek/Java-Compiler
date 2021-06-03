package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * input-specified line analyzer
 */
public class InputLineAnalyze extends AllLineAnalyze{


    /**
     * regex made to recognize correct input line
     */
    private static final String INPUT_REGEX = "\\s*([a-zA-Z]\\w*|_\\w+)\\s*=\\s*(.+)\\s*;\\s*";


    /**
     * constructor for the input type analyzer
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect
     */
    public InputLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection)
    {
        this.givenLine = givenLine;
        this.givenScope = givenScope;
        this.globalInspection=globalInspection;
    }

    /**
     * implement of the analyze virtual method, specified for a input line
     * @return the scope after update or either new scope
     * @throws IllegalFormatException exception of invalid format
     * @throws VariableException exception of variables
     */
    @Override
    public Scope analyze() throws IllegalFormatException, VariableException {
        if (globalInspection && this.givenScope.getScopeDepth() != Scope.GLOBAL_DEPTH) {
            return this.givenScope;
        }
        if (!globalInspection && this.givenScope.getScopeDepth() == Scope.GLOBAL_DEPTH) {
            return this.givenScope;
        }
        Pattern inputPattern=Pattern.compile(INPUT_REGEX);
        Matcher inputMatcher=inputPattern.matcher(this.givenLine);
        if (!inputMatcher.matches())
        {
            throw new IllegalFormatException("Input format is invalid");
        }
        String varName = inputMatcher.group(1);
        String value = inputMatcher.group(2);
        String varType = this.givenScope.getVariableType(varName);
        String constantRegex=getRegexByType(varType);
        if (constantRegex == null)
        {
            throw new IllegalFormatException("How? (assigned strange type to a variable)");
        }
        if (value.matches(constantRegex))
        {
            this.givenScope.editVariable(varName,value);
        }
        else
        {
            String secondVarType=this.givenScope.getVariableType(value);
            if (!isTypeCompatible(varType,secondVarType))
            {
                throw new IllegalFormatException("Cannot assign type "+secondVarType+" to type "+varType);
            }
            this.givenScope.editVariable(varName,this.givenScope.getVariableValue(value));
        }
        this.givenScope.setHadReturn(false);
        return this.givenScope;

    }

    /**
     * the method will return the regex that recognize a given type
     * @param type given type
     * @return regex that recognize the given type
     */
    private String getRegexByType(String type)
    {
        String constantRegex=null;
        if (type.equals("int"))
        {
            constantRegex=INT_REGEX;
        }
        if (type.equals("double"))
        {
            constantRegex=DOUBLE_REGEX;
        }
        if (type.equals("String"))
        {
            constantRegex=STRING_REGEX;
        }
        if (type.equals("char"))
        {
            constantRegex=CHAR_REGEX;
        }
        if (type.equals("boolean"))
        {
            constantRegex=BOOL_REGEX;
        }
        return constantRegex;
    }
}
