package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VariableException;

/**
 * end of block specified line analyzer
 */
public class EndOfBlockLineAnalyze extends AllLineAnalyze {


    /**
     * regex made to recognize correct end of block line
     */
    private final static String END_OF_BLOCK_REGEX="\\s*\\}\\s*";

    /**
     * constructor for the end of block type analyzer
     * @param givenLine given line
     * @param givenScope given scope
     */
    public EndOfBlockLineAnalyze(String givenLine, Scope givenScope)
    {
        this.givenLine = givenLine;
        this.givenScope = givenScope;
    }

    /**
     * implement of the analyze virtual method, specified for a end of block line
     * @return the scope after update or either new scope
     * @throws IllegalFormatException exception of invalid format
     */
    @Override
    public Scope analyze() throws IllegalFormatException {
        if (!this.givenLine.matches(END_OF_BLOCK_REGEX))
        {
            throw new IllegalFormatException("How did this even happened");
        }
        if (this.givenScope.getScopeType() == Scope.ScopeType.GLOBAL)
        {
            throw new IllegalFormatException("Cannot close the global scope");
        }
        if (this.givenScope.getScopeType() == Scope.ScopeType.METHOD && !this.givenScope.HasReturn())
        {
            throw new IllegalFormatException("Function must end with a return statement");
        }
        return this.givenScope.getOuterScope();
    }

}
