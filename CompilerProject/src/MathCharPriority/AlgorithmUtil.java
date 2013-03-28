package MathCharPriority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �㷨������
 * 
 * @author yinger
 * 
 */
public class AlgorithmUtil {

	public static final int TerminalCharCount = 7;//�ս������Ŀ
	public static final int UnterminalCharCount = 5;//���ս������Ŀ

	public static final List<Character> TerminalCharList = new ArrayList<Character>();//�ս������
	public static final List<Character> UnterminalCharList = new ArrayList<Character>();//���ս������

	public static final Map<Character, Integer> TerminalCharMap = new HashMap<Character, Integer>();//�ս��ӳ���
	public static final Map<Character, Integer> UnterminalCharMap = new HashMap<Character, Integer>();//���ս��ӳ���

	static {
		Collections.addAll(TerminalCharList, '+', '*', '^', '(', ')', 'i', '#');//���ʹ��hashset����ô˳���ܱ�֤
		Collections.addAll(UnterminalCharList, 'S', 'E', 'T', 'F', 'P');
		for (int i = 0; i < TerminalCharCount; i++) {
			TerminalCharMap.put(TerminalCharList.get(i), i);
		}
		for (int i = 0; i < UnterminalCharCount; i++) {
			UnterminalCharMap.put(UnterminalCharList.get(i), i);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < TerminalCharList.size(); i++) {
			System.out.println(TerminalCharList.get(i));
		}
	}

}
