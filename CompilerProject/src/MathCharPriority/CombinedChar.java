package MathCharPriority;

/**
 * 存入到栈中的字符（包括了一个终结符和一个非终结符）
 * 
 * @author yinger
 * 
 */
public class CombinedChar {

	public char unterminalChar;
	public char terminalChar;

	public CombinedChar(char unterminalChar, char terminalChar) {
		this.unterminalChar = unterminalChar;
		this.terminalChar = terminalChar;
	}

}
