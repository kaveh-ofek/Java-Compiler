package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.Scope;

/**
 * double specified line analyzer
 */
public class DoubleLineAnalyze extends DeclarationLineAnalyze {

    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX = "\\s*double(.+);\\s*";

    /**
     * regex made to split the line
     */
    private static final String SPLIT_REGEX =
            "\\s*(?<name>[a-zA-Z]\\w*|_\\w+)\\s*(?:=\\s*(?<value>[\\w\\.-]+)\\s*)?";

    /**
     * regex made to recognize correct double line
     */
    private static final String DOUBLE_REGEX = "-?(?:(?:\\d+\\.?\\d*)|(?:\\d*\\.?\\d+))";

    /**
     * label for double line
     */
    private static final String DOUBLE_LABEL = "double";

    /**
     * constructor for the analyzer object
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect of the line
     */
    public DoubleLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
        super(givenLine, givenScope,globalInspection);
        this.typeName = DOUBLE_LABEL;
        this.trimRegex = TRIM_REGEX;
        this.splitRegex = SPLIT_REGEX;
        this.constantVarRegex = DOUBLE_REGEX;
    }
}
