package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * documentation-specified line analyzer
 */
public class DocumentationLineAnalyze extends AllLineAnalyze{

    /**
     * constructor for the documentation analyzer
     * @param givenLine given line
     * @param givenScope given scope
     */
    public DocumentationLineAnalyze(String givenLine, Scope givenScope)
    {
        this.givenLine = givenLine;
        this.givenScope = givenScope;
    }

    /**
     * implement of the analyze virtual method, specified for a documentation line
     * @return the scope after update or either new scope
     * @throws IllegalFormatException exception of invalid format
     */
    @Override
    public Scope analyze() throws IllegalFormatException {
        if (!isLegit(this.givenLine))
        {
            throw new IllegalFormatException("How? (documentation bad format)");
        }
        return this.givenScope;
    }

    /**
     * the method will check whether a given line is according to the regex format
     * @param givenLine given line
     * @return boolean value of the result
     */
    private static boolean isLegit(String givenLine)
    {
        Pattern docPattern = Pattern.compile("\\/\\/.*");
        Matcher docMatcher = docPattern.matcher(givenLine);
        return docMatcher.matches();
    }

}
