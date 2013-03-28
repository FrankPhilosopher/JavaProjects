package ex1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 对一组指令进行霍夫曼编码，并输出最后的编码结果.<br/>
 * 以及对指令码的长度进行评价,与扩展操作码和等长编码进行比较.
 * 
 * @author yinger
 * 
 */
public class Demo {

	public static void main(String[] args) throws Exception {
		Node[] nodes = readData();
		ICoding huffmanCoding = new HuffmanCoding(nodes);
		huffmanCoding.doCoding();
		ICoding equalLengthCoding = new EqualLengthCoding(nodes);
		equalLengthCoding.doCoding();
		int[] codeLength = new int[] { 1, 2, 3, 5 };
		ICoding extendCoding = new ExtendCoding(nodes, codeLength);
		extendCoding.doCoding();
		int[] codeLength2 = new int[] { 2, 4 };
		ICoding extendCoding2 = new ExtendCoding(nodes, codeLength2);
		extendCoding2.doCoding();
	}

	private static Node[] readData() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Demo.class.getResourceAsStream("data.txt")));
		String line = reader.readLine();
		Scanner scanner = new Scanner(line);
		int n = scanner.nextInt();
		Node[] nodes = new Node[n];
		Node node = null;
		while (--n >= 0) {
			line = reader.readLine();
			scanner = new Scanner(line);//不要忘了这一步
			node = new Node(scanner.next(), scanner.nextDouble(), true);//true表示是叶子节点
			nodes[n] = node;//这里要减一才行，同时注意我这里是反向的保存node
		}
		return nodes;
	}

}
