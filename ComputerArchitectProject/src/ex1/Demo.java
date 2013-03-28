package ex1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ��һ��ָ����л��������룬��������ı�����.<br/>
 * �Լ���ָ����ĳ��Ƚ�������,����չ������͵ȳ�������бȽ�.
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
			scanner = new Scanner(line);//��Ҫ������һ��
			node = new Node(scanner.next(), scanner.nextDouble(), true);//true��ʾ��Ҷ�ӽڵ�
			nodes[n] = node;//����Ҫ��һ���У�ͬʱע���������Ƿ���ı���node
		}
		return nodes;
	}

}
