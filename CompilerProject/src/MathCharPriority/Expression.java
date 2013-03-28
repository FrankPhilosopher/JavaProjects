package MathCharPriority;

/**
 * 文法表达式（由一个非终结符和一个可以表示这个非终结符的字符串表示）
 * 
 * @author yinger
 * 
 */
public class Expression {

	public char eChar;
	public String eString;

	public Expression(char eChar, String eString) {
		this.eChar = eChar;
		this.eString = eString;
	}

}
