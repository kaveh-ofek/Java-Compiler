package oop.ex6.singlelineanalayze;


import oop.ex6.scopes.Scope;

/**
 * int specified line analyzer
 */
public class IntLineAnalyze extends DeclarationLineAnalyze {

    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX = "\\s*int(.+);\\s*";

    /**
     * regex made to split the line
     */
    private static final String SPLIT_REGEX =
            "\\s*(?<name>[a-zA-Z]\\w*|_\\w+)\\s*(?:=\\s*(?<value>-?\\w+)\\s*)?";
    /**
     * regex made to recognize correct int line
     */
    private static final String INT_REGEX = "-?\\d+";

    /**
     * label for int line
     */
    private static final String INT_LABEL = "int";

    /**
     * constructor for the analyzer object
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect of the line
     */
    public IntLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
        super(givenLine, givenScope,globalInspection);
        this.typeName=INT_LABEL;
        this.trimRegex=TRIM_REGEX;
        this.splitRegex=SPLIT_REGEX;
        this.constantVarRegex=INT_REGEX;
    }
}
