/**
 * @Author��������  
 * @CreateTime��2011-5-247 ����22:00
 * @Description��AC  2880ms
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
		Node lastNode = new Node();//���һ�������
		while (sin.hasNext()) {
			sum = 0;
			nodes.clear();
			t = sin.nextInt();
			for (j = 0; j < t; j++) {
				if (nodes.isEmpty()) {
					Node node = new Node();//һ�������
					node.color = sin.nextInt();
					node.len = 1;
					nodes.push(node);
				} else {
					curcolor = sin.nextInt();
					lastNode = nodes.peek();
					lastcolor = lastNode.color;
					if (lastcolor == curcolor) {// ���Ҫ������Ǹ��������Ǹ���ɫ��ͬ����ֱ�������һ������γ��ȼ�1
						lastNode.len++;
					} else {
						if ((j + 1) % 2 == 0) {// ���������ż��λ,ɾ�����һ������Σ�����ǰһ�������ӳ�
							len = lastNode.len;
							nodes.pop();
							if (nodes.isEmpty()) {//�����ջ��ջΪ�գ���ôҪ���²���һ��node
								Node newNode=new Node();
								newNode.len = len + 1;
								newNode.color = curcolor;
								nodes.push(newNode);
							} else {//��������һ��node�ĳ��ȼ�len+1
								nodes.peek().len+=(len+1);
							}
						} else {//���������λ����ôֻҪ����һ��node����
							Node newNode=new Node();
							newNode.len = 1;
							newNode.color = curcolor;
							nodes.push(newNode);
						}
					}
				}
			}
			// ���ɫ�������εĸ���
			curcolor = nodes.get(0).color;//��һ�����������ǰ�ɫ(0)���ʹ�0��ʼ�����������1��ʼ
			for (i = curcolor; i < nodes.size(); i += 2) {
				lastNode = nodes.get(i);
				sum+=lastNode.len;
			}
			System.out.println(sum);
		}
	}
}
