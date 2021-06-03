package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.Scope;

/**
 * char specified line analyzer
 */
public class CharLineAnalyze extends DeclarationLineAnalyze {

    /**
     * regex made to trim the line
     */
    private static final String TRIM_REGEX = "\\s*char(.+);\\s*";

    /**
     * regex made to split the line
     */
    private static final String SPLIT_REGEX =
            "\\s*(?<name>[a-zA-Z]\\w*|_\\w+)\\s*(?:=\\s*(?<value>'.'|\\w+)\\s*)?";

    /**
     * regex made to recognize correct char line
     */
    private static final String CHAR_REGEX = "'.'";

    /**
     * label for char line
     */
    private static final String CHAR_LABEL = "char";

    /**
     * constructor for the analyzer object
     * @param givenLine given line
     * @param givenScope given scope
     * @param globalInspection flag for the global inspect of the line
     */
    public CharLineAnalyze(String givenLine, Scope givenScope, boolean globalInspection) {
        super(givenLine, givenScope,globalInspection);
        this.typeName=CHAR_LABEL;
        this.trimRegex=TRIM_REGEX;
        this.splitRegex=SPLIT_REGEX;
        this.constantVarRegex=CHAR_REGEX;
    }

}
