package test;


public class Test {

	public static void main(String[] args) {

//		int n = 4;
//		System.out.println(n>>1);
		int codelength = 0;
		double n = 5;
		while (n > 1) {
			n = n / 2;// n/2
			codelength++;
		}
		System.out.println(codelength);

//		System.out.println(HuffmanCoding.class.getCanonicalName());
//		System.out.println(HuffmanCoding.class.getName());
//		System.out.println(HuffmanCoding.class.getSimpleName());
		//���ļ��ķ�ʽһ�������ַ�ʽ�õ�InputStream�ٷ�װ����
//		InputStream inputStream = HuffmanCoding.class.getResourceAsStream("data.txt");

//		File file = null;
//		try {
//			file = new File(HuffmanCoding.class.getResource("data.txt").toURI());//File�������Ĳ�����URI
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
	}

//	//������С��
//	private void makeMinHeap() {
//		for (int i = 0; i < nodes.length; i++) {
//			insertNode(new Node(list.get(i)));
//		}
//	}
//
//	//����С���в���ڵ�
//	private void insertNode(Node node) {
//		//���Ƚ�node���뵽����ĩβ
//		nodes[currentsize] = node;
//		//Ȼ��node�ڵ��ƶ������ʵ�λ��
//		int currentIndex = currentsize;//��ǰ�ڵ��λ��
//		int parentIndex = currentIndex / 2;//���ĸ��ڵ��λ��
//		while (parentIndex >= 0) {//��ʾ���ڵ���ڵĻ�
//			if (nodes[parentIndex].key > node.key) {//����ӽڵ��С����ô��Ҫ����λ��
//				Node tempNode = nodes[parentIndex];
//				nodes[parentIndex] = node;
//				nodes[currentIndex] = tempNode;
//				currentIndex = parentIndex;
//				parentIndex = currentIndex / 2;
//			}
//		}
//		currentsize++;
//
//	}

}
