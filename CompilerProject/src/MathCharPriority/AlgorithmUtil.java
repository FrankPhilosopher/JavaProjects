package MathCharPriority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 算法工具类
 * 
 * @author yinger
 * 
 */
public class AlgorithmUtil {

	public static final int TerminalCharCount = 7;//终结符的数目
	public static final int UnterminalCharCount = 5;//非终结符的数目

	public static final List<Character> TerminalCharList = new ArrayList<Character>();//终结符集合
	public static final List<Character> UnterminalCharList = new ArrayList<Character>();//非终结符集合

	public static final Map<Character, Integer> TerminalCharMap = new HashMap<Character, Integer>();//终结符映射表
	public static final Map<Character, Integer> UnterminalCharMap = new HashMap<Character, Integer>();//非终结符映射表

	static {
		Collections.addAll(TerminalCharList, '+', '*', '^', '(', ')', 'i', '#');//如果使用hashset，那么顺序不能保证
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
