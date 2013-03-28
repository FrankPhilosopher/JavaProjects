package MathCharPriority;

import java.util.ArrayList;
import java.util.Stack;

/**
 * �㷨���ȷ�����
 * 
 * @author yinger
 * 
 */
public class MathCharPriority {

	public ArrayList<Expression> expressions;//���ʹ��hashset����ô˳���ܱ�֤
	public int[][] FirstVt;//FIRSTVT ����
	public int[][] LastVt;//LASTVT ����
	public Stack<CombinedChar> stack;//���ڼ���F��L�����ջ
	public char[][] charTable;//������ȼ���

	public MathCharPriority(ArrayList<Expression> expressions) {
		this.expressions = expressions;
		initValues();
	}

	//��ʼ������
	private void initValues() {
		this.stack = new Stack<CombinedChar>();
		this.FirstVt = new int[AlgorithmUtil.UnterminalCharCount][AlgorithmUtil.TerminalCharCount];
		this.LastVt = new int[AlgorithmUtil.UnterminalCharCount][AlgorithmUtil.TerminalCharCount];
		this.charTable = new char[AlgorithmUtil.TerminalCharCount][AlgorithmUtil.TerminalCharCount];
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			for (int j = 0; j < AlgorithmUtil.TerminalCharCount; j++) {
				charTable[i][j] = ' ';//����char����һ��Ҫ��ʼ���������ӡ�����ܶ��Ƿ���
			}
		}
	}

	//�㷨����
	public void doIt() {
		calFirstVt();
		clearStack();
		calLastVt();
		calCharPriority();
	}

	//���stack
	private void clearStack() {
		while (!stack.isEmpty()) {
			stack.pop();
		}
	}

	//����������ȼ�
	private void calCharPriority() {
		String eString;
		char chi, chi1, chi2;//xi,xi+1,xi+2
		for (int i = 0, size = expressions.size(); i < size; i++) {//��������ʽ
			eString = expressions.get(i).eString;
			for (int j = 0, length = eString.length(); j < length - 1; j++) {//�����ַ���������ע������
				chi = eString.charAt(j);
				chi1 = eString.charAt(j + 1);
//				System.out.println(chi + "\t" + chi1);
				if (j + 2 < length) {//����Ҫ��֤���ܹ�������Χ
					chi2 = eString.charAt(j + 2);
					if (isUnterminalChar(chi1) && isTerminalChar(chi) && isTerminalChar(chi2)) {//aQb
						charEqual(chi, chi2);
					}
				} //�����if���ܺ�����ĺϲ����������ֺܶ�ط�û��ֵ
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

	//ǰ�ߵ����ȼ�С�ں��ߵ�FirstVt aQ
	private void charSmaller(char chi, char chi1) {
		int unterminalChatKey = getUnterminalInt(chi1);
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			if (FirstVt[unterminalChatKey][i] == 1) {
				charTable[getTerminalInt(chi)][i] = 'x';
			}
		}
	}

	//ǰ�ߵ�LastVt���ȼ����ں���  Qa
	private void charGreater(char chi, char chi1) {
		int unterminalChatKey = getUnterminalInt(chi);
		for (int i = 0; i < AlgorithmUtil.TerminalCharCount; i++) {
			if (LastVt[unterminalChatKey][i] == 1) {
				charTable[i][getTerminalInt(chi1)] = 'd';
			}
		}
	}

	//�������ս�����ȼ���ͬ
	private void charEqual(char chi, char chi1) {
		charTable[getTerminalInt(chi)][getTerminalInt(chi1)] = '=';
	}

	//����F����
	public void calFirstVt() {
		//���Ƚ�������������ջ
		pushInitFirstVt();
		//displayStack();
		//Ȼ�����㷨�����ջ��ջ
		fillFirstVt();
		displayVt('F');
	}

	//����ʼʱ����First��������ջ
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
			if (eString.length() >= 2) {//P->Qa  ���ȴ��ڵ���2
				if (isUnterminalChar(eString.charAt(0)) && isTerminalChar(eString.charAt(1))) {
					firstVtIntoStack(new CombinedChar(eChar, eString.charAt(1)));//(P,a)
					continue;
				}
			}
		}
	}

	//���F����
	private void fillFirstVt() {
		CombinedChar combinedChar;
		char unterminalChar;
		char terminalChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();//����(Q,a)
			unterminalChar = combinedChar.unterminalChar;
			terminalChar = combinedChar.terminalChar;
			for (int i = 0, size = expressions.size(); i < size; i++) {//��������ʽ
				if (expressions.get(i).eString.charAt(0) == unterminalChar) {//�ҵ�ʽ������P->Q...
					firstVtIntoStack(new CombinedChar(expressions.get(i).eChar, terminalChar));//push (P,a)
				}
			}
		}
	}

	//����һ��combinedchar��ջ�У���ʱ��F
	private void firstVtIntoStack(CombinedChar combinedChar) {
		//�����Ϊ1��ô����ջ��������ջ�������ͱ�������ͬ�Ķ����ջ
		if (FirstVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] != 1) {
			stack.push(combinedChar);
			FirstVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] = 1;
		}
	}

	public void calLastVt() {
		//���Ƚ�������������ջ
		pushInitLastVt();
		//Ȼ�����㷨�����ջ��ջ
		fillLastVt();
		displayVt('L');
	}

	//����ʼʱ����Last��������ջ
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
			if (eString.length() >= 2) {//P->...aQ  ���ȴ��ڵ���2  -->��ô��Щ�ս������firstvt����lastvt
				if (isUnterminalChar(eString.charAt(eString.length() - 1)) && isTerminalChar(eString.charAt(eString.length() - 2))) {
					lastVtIntoStack(new CombinedChar(eChar, eString.charAt(eString.length() - 2)));//(P,a)
					continue;
				}
			}
		}
	}

	//���L��������
	private void fillLastVt() {
		CombinedChar combinedChar;
		char unterminalChar;
		char terminalChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();//����(Q,a)
			unterminalChar = combinedChar.unterminalChar;
			terminalChar = combinedChar.terminalChar;
			for (int i = 0, size = expressions.size(); i < size; i++) {//��������ʽ
				if (expressions.get(i).eString.charAt(expressions.get(i).eString.length() - 1) == unterminalChar) {//�ҵ�ʽ������P->...Q
					lastVtIntoStack(new CombinedChar(expressions.get(i).eChar, terminalChar));//push (P,a)
				}
			}
		}
	}

	//����һ��combinedchar��ջ�У���ʱ��Last
	private void lastVtIntoStack(CombinedChar combinedChar) {
		//�����Ϊ1��ô����ջ��������ջ�������ͱ�������ͬ�Ķ����ջ
		if (LastVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] != 1) {
			stack.push(combinedChar);
			LastVt[getUnterminalInt(combinedChar.unterminalChar)][getTerminalInt(combinedChar.terminalChar)] = 1;
		}
	}

	//��ʾջ�е�����
	public void displayStack() {
		System.out.println("��ʾջ�е�����");
		CombinedChar combinedChar;
		while (!stack.isEmpty()) {
			combinedChar = stack.pop();
			System.out.println(combinedChar.unterminalChar + "\t" + combinedChar.terminalChar);
		}
	}

	//��ʾF/L����
	public void displayVt(char ch) {
		System.out.println("��ʾ" + ch + "��������");
		for (int i = 0; i <= AlgorithmUtil.UnterminalCharCount; i++) {//ע��ѭ����ֹ����������Ϊ�����ӡʱ��һ���Ǳ��ı���
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

	//��ʾ������ȼ�
	public void displayCharTable() {
		System.out.println("��ʾ������ȼ���");
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

	//�ж��ַ��Ƿ��ǣ��ǣ��ս��
	public static boolean isUnterminalChar(char ch) {
		return AlgorithmUtil.UnterminalCharList.contains(ch);
	}

	public static boolean isTerminalChar(char ch) {
		return AlgorithmUtil.TerminalCharList.contains(ch);
	}

	//���ݱ�ŵõ���ֹ��
	public static char getTerminalChar(int index) {
		return AlgorithmUtil.TerminalCharList.get(index);
	}

	//������ֹ���õ����ı��
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
