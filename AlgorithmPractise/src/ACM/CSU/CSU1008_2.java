/**
 * @Author：胡家威  
 * @CreateTime：2011-5-247 晚上22:00
 * @Description：AC  2880ms
 */

package ACM.CSU;
import java.util.Scanner;
import java.util.Stack;
class Node {
	int len;
	int color;
}
public class CSU1008_2 {
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		Stack<Node> nodes = new Stack<Node>();
		int t, i, j, len, lastcolor, sum, curcolor;
		Node lastNode = new Node();//最后一个区间段
		while (sin.hasNext()) {
			sum = 0;
			nodes.clear();
			t = sin.nextInt();
			for (j = 0; j < t; j++) {
				if (nodes.isEmpty()) {
					Node node = new Node();//一个区间段
					node.color = sin.nextInt();
					node.len = 1;
					nodes.push(node);
				} else {
					curcolor = sin.nextInt();
					lastNode = nodes.peek();
					lastcolor = lastNode.color;
					if (lastcolor == curcolor) {// 如果要插入的那个与最后的那个颜色相同，就直接让最后一个区间段长度加1
						lastNode.len++;
					} else {
						if ((j + 1) % 2 == 0) {// 插入的数是偶数位,删除最后一个区间段，并把前一段区间延长
							len = lastNode.len;
							nodes.pop();
							if (nodes.isEmpty()) {//如果弹栈后栈为空，那么要重新插入一个node
								Node newNode=new Node();
								newNode.len = len + 1;
								newNode.color = curcolor;
								nodes.push(newNode);
							} else {//否则把最后一个node的长度加len+1
								nodes.peek().len+=(len+1);
							}
						} else {//如果是奇数位，那么只要插入一个node就行
							Node newNode=new Node();
							newNode.len = 1;
							newNode.color = curcolor;
							nodes.push(newNode);
						}
					}
				}
			}
			// 求白色的三角形的个数
			curcolor = nodes.get(0).color;//第一个区间段如果是白色(0)，就从0开始计数，否则从1开始
			for (i = curcolor; i < nodes.size(); i += 2) {
				lastNode = nodes.get(i);
				sum+=lastNode.len;
			}
			System.out.println(sum);
		}
	}
}
