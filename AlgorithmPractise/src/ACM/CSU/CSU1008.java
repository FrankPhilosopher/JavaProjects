/**
 * @Author：胡家威  
 * @CreateTime：2011-5-24 下午10:17:41
 * @Description：TLE+WA  见第二个版本
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
				lastcolor = last.get(0);// 保存最后一个的颜色
				if (lastcolor == first) { // 如果要插入的那个与最后的那个颜色相同，就直接把它加到最后一个区间段
					last.add(first);
				} else {
					if ((j + 1) % 2 == 0) {// 插入的数是偶数位,删除最后一个区间段，并把前一段区间延长
						len = last.size();
						stack.pop();
						if (stack.isEmpty()) {// 如果弹出栈顶后栈空了，那么就要新建一个区间段并插入到栈中
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
					} else {// 插入的数是奇数位,向栈中添加一个新的区间段
						ArrayList<Integer> newArray = new ArrayList<Integer>();
						newArray.add(first);
						stack.add(newArray);
					}
				}
			}
			// 求白色的三角形的个数
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
