package ex1;

/**
 * huffman����ʵ����
 * 
 * ��Ҫע����ǣ���С���еĽڵ��ϵ�͹��������нڵ�Ĺ�ϵû�й�ϵ��
 * 
 * @author yinger
 * 
 */

public class HuffmanCoding implements ICoding {

	public Node[] nodes;//��������ݽڵ�
	public Node[] minheap;//�������������
	public int currentsize = 0;//�ѵ�ǰ�Ĵ�С
	public double wpl;//wpl

	public HuffmanCoding(Node[] nodes) {
		this.nodes = nodes;
		this.minheap = new Node[nodes.length];
	}

	//�㷨ִ�й���
	public void doCoding() {
		initCode();
		buildMinHeap();//build���֮��minheap��nodes�е�node��id��һ���ģ�Ҳ����˵���������õ�����ͬ�Ķ���
		while (currentsize != 1) {//ֻ����������һ���ڵ㣬�������յĸ��ڵ�ֹͣ
			doOneStep();//����һ��ԭ�Ӳ�������������֮���ж�currentsize�Ƿ�Ϊ1
		}
		System.out.println("����Huffman����õ��ı�������");
		makeHuffmanCode(minheap[0]);
		displayHuffmanCode();
		calWPL();
	}

	//��ʼ��codeֵ
	private void initCode() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].code = "";
		}
	}

	//����WPL
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * (nodes[i].code.length());
		}
		System.out.println("WPL=" + wpl);
	}

	//������ʼ��С��
	private void buildMinHeap() {
		for (int i = 0; i < nodes.length; i++) {
			insertNode(nodes[i]);
		}
	}

//����һ������Ҳ���Ǻϲ�һ�Σ�Ҳ����ɾ��������С�Ľڵ㣬����һ���½ڵ㣬���Ҳ�������
	private void doOneStep() {
		Node leftNode = removeNode();
		Node rightNode = removeNode();
		Node newNode = new Node(leftNode.possibility + rightNode.possibility, leftNode, rightNode);//�����ﴴ�����µ�node���뵽��minheap��
		insertNode(newNode);
	}

//���ڵ�ı��뷽ʽ��ֵ
	private void makeHuffmanCode(Node root) {
		Node leftNode = root.leftChild;
		leftNode.code = root.code + "0";
		if (!leftNode.isLeaf) {
			makeHuffmanCode(leftNode);
		}
		Node rightNode = root.rightChild;
		rightNode.code = root.code + "1";
		if (!rightNode.isLeaf) {
			makeHuffmanCode(rightNode);
		}
	}

//����ڵ�
	private void insertNode(Node node) {
		//���Ƚ�node���뵽����ĩβ
		minheap[currentsize] = node;
		//Ȼ��node�ڵ��ƶ������ʵ�λ��
		int currentIndex = currentsize;//��ǰ�ڵ��λ��
		int parentIndex = (currentIndex - 1) / 2;//���ĸ��ڵ��λ��
		while (parentIndex >= 0) {//��ʾ���ڵ���ڵĻ�
			if (minheap[parentIndex].possibility > node.possibility) {//����ӽڵ��С����ô��Ҫ����λ��
				Node tempNode = minheap[parentIndex];
				minheap[parentIndex] = node;
				minheap[currentIndex] = tempNode;
				currentIndex = parentIndex;
				parentIndex = (currentIndex - 1) / 2;
			} else {
				break;//��index=0ʱ��parent=0��Ҫ��֤���������Ҳ���ܹ�����ѭ����
			}
		}
		currentsize++;
	}

//ɾ���ڵ�
	private Node removeNode() {
		//�ж��Ƿ��нڵ��ڶ���
		if (currentsize < 1) {
			return null;
		}
		Node top = minheap[0];
		//�������ڶѶ�
		minheap[0] = minheap[currentsize - 1];//���һ��Ԫ�ط���currentsize - 1
		currentsize--;//��һ��ʱ��Ҫע��
		int index = 0;
		int left, right, smallNode;//smallNode��������ʾ���ӽڵ��н�С���Ǹ��ڵ�
		while (index < currentsize / 2) {//�ڵ�ǰ��С��1/2�ڵĽڵ�Ż��к��ӽڵ�
			left = 2 * index + 1;
			right = left + 1;
			//�õ���С���ӽڵ��index����������ҽڵ㲢�ұ���ڵ�С����ô�����ҽڵ�
			if (right < currentsize && minheap[right].possibility < minheap[left].possibility) {
				smallNode = right;
			} else {
				smallNode = left;
			}
			//����С�ڵ�͸��ڵ�Ƚϣ�������ڵ��Ļ�����ô��Ҫ����һ�ν����������Ҫ�˳���
			if (minheap[index].possibility > minheap[smallNode].possibility) {
				Node tempNode = minheap[index];
				minheap[index] = minheap[smallNode];
				minheap[smallNode] = tempNode;
				index = smallNode;
			} else {
				break;
			}
		}
		return top;
	}

	//��ʾ�������ɵĹ���������
	private void displayHuffmanCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

	//������е�����
//	private void displayHeap() {
//		for (int i = 0; i < currentsize; i++) {
//			System.out.print(minheap[i].possibility + "\t");
//		}
//		System.out.println();
//	}

}
