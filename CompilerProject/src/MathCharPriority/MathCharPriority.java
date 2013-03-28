package MathCharPriority;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 算法优先分析法
 * 
 * @author yinger
 * 
 */
public class MathCharPriority {

	public ArrayList<Expression> expressions;//如果使用hashset，那么顺序不能保证
	public int[][] FirstVt;//FIRSTVT 数组
	public int[][] LastVt;//LASTVT 数组
	public Stack<CombinedChar> stack;//用于计算F和L数组的栈
	public char[][] charTable;//算符优先级表

	public MathCharPriority(ArrayList<Expression> expressions) {
		this.expressions = expressions;
		initValues();
	}

	//初始化数据
	private void initValues() {
		this.stack = new Stack<CombinedChar>();
		this.FirstVt = new int[AlgorithmUtil.UnterminalCharCount][AlgorithmUtil.TerminalCharCount];
		this.LastVt = new int[AlgorithmUtil.UnterminalCharCount][AlgorithmUtil.TerminalCharCount];
		this.charTable = new char[AlgorithmUtil.TerminalCharCount][AlgorithmUtil.TerminalCharCount];
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			for (int j = 0; j < AlgorithmUtil.TerminalCharCount; j++) {
				charTable[i][j] = ' ';//对于char数组一定要初始化，否则打印出来很多是方框！
			}
		}
	}

	//算法步骤
	public void doIt() {
		calFirstVt();
		clearStack();
		calLastVt();
		calCharPriority();
	}

	//清空stack
	private void clearStack() {
		while (!stack.isEmpty()) {
			stack.pop();
		}
	}

	//计算算符优先级
	private void calCharPriority() {
		String eString;
		char chi, chi1, chi2;//xi,xi+1,xi+2
		for (int i = 0, size = expressions.size(); i < size; i++) {//遍历产生式
			eString = expressions.get(i).eString;
			for (int j = 0, length = eString.length(); j < length - 1; j++) {//遍历字符串，但是注意条件
				chi = eString.charAt(j);
				chi1 = eString.charAt(j + 1);
//				System.out.println(chi + "\t" + chi1);
				if (j + 2 < length) {//首先要保证不能够超出范围
					chi2 = eString.charAt(j + 2);
					if (isUnterminalChar(chi1) && isTerminalChar(chi) && isTerminalChar(chi2)) {//aQb
						charEqual(chi, chi2);
					}
				} //上面的if不能和下面的合并！否则会出现很多地方没有值
				if (isTerminalChar(chi) && isTerminalChar(chi1)) {//ab
					charEqual(chi, chi1);
				} else if (isTerminalChar(chi) && isUnterminalChar(chi1)) {//aQ
					charSmaller(chi, chi1);
				} else if (isUnterminalChar(chi) && isTerminalChar(chi1)) {//Qa
					charGreater(chi, chi1);
				}
			}
		}
		displayCharTable();
	}

	//前者的优先级小于后者的FirstVt aQ
	private void charSmaller(char chi, char chi1) {
		int unterminalChatKey = getUnterminalInt(chi1);
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			if (FirstVt[unterminalChatKey][i] == 1) {
				charTable[getTerminalInt(chi)][i] = 'x';
			}
		}
	}

	//前者的LastVt优先级大于后者  Qa
	private void charGreater(char chi, char chi1) {
		int unterminalChatKey = getUnterminalInt(chi);
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			if (LastVt[unterminalChatKey][i] == 1) {
				charTable[i][getTerminalInt(chi1)] = 'd';
			}
		}
	}

	//这两个终结符优先级相同
	private void charEqual(char chi, char chi1) {
		charTable[getTerminalInt(chi)][getTerminalInt(chi1)] = '=';
	}

	//计算F数组
	public void calFirstVt() {
		//首先将满足条件的入栈
		pushInitFirstVt();
		//displayStack();
		//然后按照算法步骤出栈入栈
		fillFirstVt();
		displayVt('F');
	}

	//将初始时满足First条件的入栈
	private void pushInitFirstVt() {
		char eChar;
		String eString = null;
		for (int i = 0, size = expressions.size(); i < size; i++) {
			eChar = expressions.get(i).eChar;
			eString = expressions.get(i).eString;
			if (isTerminalChar(eString.charAt(0))) {//P->a
				firstVtIntoStack(new CombinedChar(eChar, eString.charAt(0)));//(P,a)
				continue;
			}
			if (eString.length() >= 2) {//P->Qa  长度大于等于2
				if (isUnterminalChar(eString.charAt(0)) && isTerminalChar(eString.charAt(1))) {
					firstVtIntoStack(new CombinedChar(eChar, eString.charAt(1)));//(P,a)
					continue;
				}
			}
		}
	}

	//填充F数组
	private void fillFirstVt() {
		CombinedChar combinedChar;
		char unterminalChar;
		char terminalChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();//弹出(Q,a)
			unterminalChar = combinedChar.unterminalChar;
			terminalChar = combinedChar.terminalChar;
			for (int i = 0, size = expressions.size(); i < size; i++) {//遍历产生式
				if (expressions.get(i).eString.charAt(0) == unterminalChar) {//找到式子形如P->Q...
					firstVtIntoStack(new CombinedChar(expressions.get(i).eChar, terminalChar));//push (P,a)
				}
			}
		}
	}

	//插入一个combinedchar到栈中，此时是F
	private void firstVtIntoStack(CombinedChar combinedChar) {
		//如果不为1那么就入栈，否则不入栈，这样就避免了相同的多次入栈
		if (FirstVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] != 1) {
			stack.push(combinedChar);
			FirstVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] = 1;
		}
	}

	public void calLastVt() {
		//首先将满足条件的入栈
		pushInitLastVt();
		//然后按照算法步骤出栈入栈
		fillLastVt();
		displayVt('L');
	}

	//将初始时满足Last条件的入栈
	private void pushInitLastVt() {
		char eChar;
		String eString = null;
		for (int i = 0, size = expressions.size(); i < size; i++) {
			eChar = expressions.get(i).eChar;
			eString = expressions.get(i).eString;
			if (isTerminalChar(eString.charAt(eString.length() - 1))) {//P->...a
				lastVtIntoStack(new CombinedChar(eChar, eString.charAt(eString.length() - 1)));//(P,a)
				continue;
			}
			if (eString.length() >= 2) {//P->...aQ  长度大于等于2  -->那么有些终结符既是firstvt又是lastvt
				if (isUnterminalChar(eString.charAt(eString.length() - 1)) && isTerminalChar(eString.charAt(eString.length() - 2))) {
					lastVtIntoStack(new CombinedChar(eChar, eString.charAt(eString.length() - 2)));//(P,a)
					continue;
				}
			}
		}
	}

	//填充L数组内容
	private void fillLastVt() {
		CombinedChar combinedChar;
		char unterminalChar;
		char terminalChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();//弹出(Q,a)
			unterminalChar = combinedChar.unterminalChar;
			terminalChar = combinedChar.terminalChar;
			for (int i = 0, size = expressions.size(); i < size; i++) {//遍历产生式
				if (expressions.get(i).eString.charAt(expressions.get(i).eString.length() - 1) == unterminalChar) {//找到式子形如P->...Q
					lastVtIntoStack(new CombinedChar(expressions.get(i).eChar, terminalChar));//push (P,a)
				}
			}
		}
	}

	//插入一个combinedchar到栈中，此时是Last
	private void lastVtIntoStack(CombinedChar combinedChar) {
		//如果不为1那么就入栈，否则不入栈，这样就避免了相同的多次入栈
		if (LastVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] != 1) {
			stack.push(combinedChar);
			LastVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] = 1;
		}
	}

	//显示栈中的内容
	public void displayStack() {
		System.out.println("显示栈中的内容");
		CombinedChar combinedChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();
			System.out.println(combinedChar.unterminalChar + "\t" + combinedChar.terminalChar);
		}
	}

	//显示F/L数组
	public void displayVt(char ch) {
		System.out.println("显示" + ch + "数组内容");
		for (int i = 0; i <= AlgorithmUtil.UnterminalCharCount; i++) {//注意循环终止的条件，因为这里打印时第一行是表格的标题
			if (i == 0) {
				System.out.print("  \t");
			} else {
				System.out.print(AlgorithmUtil.UnterminalCharList.get(i - 1) + "\t");
			}

			for (int j = 0; j < AlgorithmUtil.TerminalCharCount; j++) {
				if (i == 0) {
					System.out.print(AlgorithmUtil.TerminalCharList.get(j) + "\t");
				} else {
					if (ch == 'F') {
						System.out.print(FirstVt[i - 1][j] + "\t");
					} else {
						System.out.print(LastVt[i - 1][j] + "\t");
					}
				}
			}

			System.out.println();
		}
	}

	//显示算符优先级
	public void displayCharTable() {
		System.out.println("显示算符优先级：");
		for (int i = 0; i <= AlgorithmUtil.TerminalCharCount; i++) {
			if (i == 0) {
				System.out.print("  \t");
			} else {
				System.out.print(AlgorithmUtil.TerminalCharList.get(i - 1) + "\t");
			}

			for (int j = 0; j < AlgorithmUtil.TerminalCharCount; j++) {
				if (i == 0) {
					System.out.print(AlgorithmUtil.TerminalCharList.get(j) + "\t");
				} else {
					System.out.print(charTable[i - 1][j] + "\t");
				}
			}
			System.out.println();
		}
	}

	//判断字符是否是（非）终结符
	public static boolean isUnterminalChar(char ch) {
		return AlgorithmUtil.UnterminalCharList.contains(ch);
	}

	public static boolean isTerminalChar(char ch) {
		return AlgorithmUtil.TerminalCharList.contains(ch);
	}

	//根据编号得到终止符
	public static char getTerminalChar(int index) {
		return AlgorithmUtil.TerminalCharList.get(index);
	}

	//根据终止符得到它的编号
	public static int getTerminalInt(char ch) {
		return AlgorithmUtil.TerminalCharMap.get(ch);
	}

	public static char getUnterminalChar(int index) {
		return AlgorithmUtil.UnterminalCharList.get(index);
	}

	public static int getUnterminalInt(char ch) {
		return AlgorithmUtil.UnterminalCharMap.get(ch);
	}

}
