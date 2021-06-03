package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.Scope;

/**
 * string specified string analyzer
 */
public class StringLineAnalyze extends DeclarationLineAnalyze {

    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX = "\\s*String(.+);\\s*";
    /**
     * regex made to split the line
     */
    private static final String SPLIT_REGEX =
            "\\s*(?<name>[a-zA-Z]\\w*|_\\w+)\\s*(?:=\\s*(?<value>\\\".*\\\"|\\w+)\\s*)?";
    /**
     * regex made to recognize correct string line
     */
    private static final String STRING_REGEX = "\\\".*\\\"";

    /**
     * label for string line
     */
    private static final String STRING_LABEL = "String";

    /**
     * constructor for the analyzer object
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect of the line
     */
    public StringLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
        super(givenLine, givenScope,globalInspection);
        this.typeName = STRING_LABEL;
        this.trimRegex = TRIM_REGEX;
        this.splitRegex = SPLIT_REGEX;
        this.constantVarRegex = STRING_REGEX;
    }

}
