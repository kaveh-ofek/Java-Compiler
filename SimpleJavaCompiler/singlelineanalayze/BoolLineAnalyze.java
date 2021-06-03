package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.Scope;

/**
 * boolean specified line analyzer
 */
public class BoolLineAnalyze extends DeclarationLineAnalyze {

    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX = "\\s*boolean(.+);\\s*";

    /**
     * regex made to split the line
     */
    private static final String SPLIT_REGEX =
            "\\s*(?<name>[a-zA-Z]\\w*|_\\w+)\\s*(?:=\\s*(?<value>[\\w\\.-]+)\\s*)?";

    /**
     * regex made to recognize correct boolean line
     */
    private static final String BOOL_REGEX = "true|false|(?:-?(?:(?:\\d+\\.?\\d*)|(?:\\d*\\.?\\d+)))";

    /**
     * label for boolean line
     */
    private static final String BOOL_LABEL = "boolean";


    /**
     * constructor for the analyzer object
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect of the line
     */
    public BoolLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
        super(givenLine, givenScope,globalInspection);
        this.typeName=BOOL_LABEL;
        this.trimRegex=TRIM_REGEX;
        this.splitRegex=SPLIT_REGEX;
        this.constantVarRegex=BOOL_REGEX;
    }
}
