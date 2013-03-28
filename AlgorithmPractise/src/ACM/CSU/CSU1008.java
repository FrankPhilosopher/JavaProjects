/**
 * @Author��������  
 * @CreateTime��2011-5-24 ����10:17:41
 * @Description��TLE+WA  ���ڶ����汾
 */

package ACM.CSU;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CSU1008 {

	public static void main(String[] args) {
		Stack<ArrayList<Integer>> stack = new Stack<ArrayList<Integer>>();
		Scanner sin = new Scanner(System.in);
		int t, i, j, k, len, lastcolor, sum, first, firstcolor;
		ArrayList<Integer> last = new ArrayList<Integer>();
		while (sin.hasNext()) {
			sum = 0;
			stack.clear();
			last.clear();
			t = sin.nextInt();
			first = sin.nextInt();
			last.add(first);
			stack.add(last);
			for (j = 1; j < t; j++) {
				first = sin.nextInt();
				last = stack.peek();
				lastcolor = last.get(0);// �������һ������ɫ
				if (lastcolor == first) { // ���Ҫ������Ǹ��������Ǹ���ɫ��ͬ����ֱ�Ӱ����ӵ����һ�������
					last.add(first);
				} else {
					if ((j + 1) % 2 == 0) {// ���������ż��λ,ɾ�����һ������Σ�����ǰһ�������ӳ�
						len = last.size();
						stack.pop();
						if (stack.isEmpty()) {// �������ջ����ջ���ˣ���ô��Ҫ�½�һ������β����뵽ջ��
							ArrayList<Integer> newArray = new ArrayList<Integer>();
							for (k = 0; k < len + 1; k++) {
								newArray.add(first);
							}
							stack.add(newArray);
						} else {
							for (k = 0; k < len + 1; k++) {
								last.add(first);
							}
						}
					} else {// �������������λ,��ջ�����һ���µ������
						ArrayList<Integer> newArray = new ArrayList<Integer>();
						newArray.add(first);
						stack.add(newArray);
					}
				}
			}
			// ���ɫ�������εĸ���
			firstcolor = stack.get(0).get(0);
			for (i = firstcolor; i < stack.size(); i += 2) {
				last = stack.get(i);
				if (last.get(0) == 0) {
					sum += last.size();
				}
			}
			System.out.println(sum);
		}
	}
}
