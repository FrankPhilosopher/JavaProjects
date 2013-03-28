/**
 * @Author��������  
 * @CreateTime��2011-5-1 ����05:12:33
 * @Description��
 */

package ACM.NY;

import java.util.Scanner;
import java.util.Stack;

public class NY002 {
	public static void main(String[] arg) {
		Stack<Character> s = new Stack<Character>();
		Scanner sin = new Scanner(System.in);
		int t = sin.nextInt();
		char c;
		String str = null;
		boolean flag;
		for (int j = 0; j < t; j++) {
			flag = true; // ע��ÿ�ζ�Ҫ����һ�£���Ȼ�����ģ��ܶ���Ŀ���������ģ���ס
			str = sin.next();
			for (int i = 0; i < str.length(); i++) {
				c = str.charAt(i);
				if (c == '(' || c == '[') {
					s.push(c);
				} else if (c == ')') {
					if (!s.empty() && s.peek() == '(') { // ע��ÿ�δ�ջ�ﵯ��Ԫ��ʱҪ���ж�ջ�Ƿ�Ϊ�գ���Ȼ�ͻᱨ��
						s.pop();
					} else {
						System.out.println("No");
						flag = false;
						break;
					}
				} else if (c == ']') {
					if (!s.empty() && s.peek() == '[') {
						s.pop();
					} else {
						System.out.println("No");
						flag = false;
						break;
					}
				}
			}
			if (flag)
				System.out.println("Yes");
			s.clear(); // ����ҲҪע�⣬���²���ʱҪ��ջ���
		}
	}
}