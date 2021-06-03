package oop.ex6.singlelineanalayze;

import oop.ex6.scopes.FunctionException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.ScopeException;
import oop.ex6.scopes.VariableException;

/**
 * The class that generate the correct analyze objects according to the type of the given line
 */
public abstract class AnalyzeLineFactory {
	/**
	 * the only method of the factory class, which is static, the method will generate the analyze object and
	 * activated the analyze method.
	 * @param givenLine given line
	 * @param givenScope given scope
	 * @param globalInspection flag for the global inspect
	 * @return the scope after analyze
	 * @throws IllegalFormatException exception for invalid format
	 * @throws ScopeException exception of scopes
	 */
	public static Scope doLine(String givenLine, Scope givenScope, boolean globalInspection)
			throws IllegalFormatException,
				   ScopeException {
		LineTypes lineType = LineRecognizer.recognize(givenLine);
		Scope newScope = null;
		switch (lineType) {
		case DOCUMENTATION:
			DocumentationLineAnalyze docAnalyzer = new DocumentationLineAnalyze(givenLine, givenScope);
			newScope = docAnalyzer.analyze();
			break;
		case METHOD:
			MethodLineAnalyze methodAnalyzer = new MethodLineAnalyze(givenLine, givenScope,
																	 globalInspection);
			newScope = methodAnalyzer.analyze();
			break;
		case CONDITION:
			ConditionLineAnalyze conditionAnalyzer = new ConditionLineAnalyze(givenLine, givenScope,
																			  globalInspection);
			newScope = conditionAnalyzer.analyze();
			break;
		case DECLARATION:
			DeclarationTypes typeOfDeclare = DeclarationLineAnalyze.checkLine(givenLine);
			DeclarationLineAnalyze lineAnalyze;
			switch (typeOfDeclare) {
			case INT:
				lineAnalyze = new IntLineAnalyze(givenLine, givenScope, globalInspection);
				break;
			case DOUBLE:
				lineAnalyze = new DoubleLineAnalyze(givenLine, givenScope, globalInspection);
				break;
			case CHAR:
				lineAnalyze = new CharLineAnalyze(givenLine, givenScope, globalInspection);
				break;
			case BOOLEAN:
				lineAnalyze = new BoolLineAnalyze(givenLine, givenScope, globalInspection);
				break;
			case STRING:
				lineAnalyze = new StringLineAnalyze(givenLine, givenScope, globalInspection);
				break;
			default:
				throw new IllegalFormatException("Unrecognized line type");
			}
			newScope = lineAnalyze.analyze();
			break;
		case INPUT:
			InputLineAnalyze inputAnalyzer = new InputLineAnalyze(givenLine, givenScope, globalInspection);
			newScope = inputAnalyzer.analyze();
			break;

		case END_OF_BLOCK:
			EndOfBlockLineAnalyze endOfBlockAnalyzer = new EndOfBlockLineAnalyze(givenLine, givenScope);
			newScope = endOfBlockAnalyzer.analyze();
			break;
		case RETURN:
			ReturnLineAnalyze returnLineAnalyze = new ReturnLineAnalyze(givenLine, givenScope);
			newScope = returnLineAnalyze.analyze();
			break;
		case CALL:
			CallLineAnalyze callLineAnalyze = new CallLineAnalyze(givenLine, givenScope,globalInspection);
			newScope = callLineAnalyze.analyze();
			break;
		case EMPTY:
			newScope = givenScope;
			break;
		}
		return newScope;
	}
}
