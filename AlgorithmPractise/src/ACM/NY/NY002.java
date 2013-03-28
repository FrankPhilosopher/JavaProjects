/**
 * @Author：胡家威  
 * @CreateTime：2011-5-1 下午05:12:33
 * @Description：
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
			flag = true; // 注意每次都要重置一下，不然会出错的！很多题目都是这样的，记住
			str = sin.next();
			for (int i = 0; i < str.length(); i++) {
				c = str.charAt(i);
				if (c == '(' || c == '[') {
					s.push(c);
				} else if (c == ')') {
					if (!s.empty() && s.peek() == '(') { // 注意每次从栈里弹出元素时要先判断栈是否为空，不然就会报错
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
			s.clear(); // 这里也要注意，重新测试时要将栈清空
		}
	}
}