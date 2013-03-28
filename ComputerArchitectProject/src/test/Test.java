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
		//读文件的方式一：用这种方式得到InputStream再封装即可
//		InputStream inputStream = HuffmanCoding.class.getResourceAsStream("data.txt");

//		File file = null;
//		try {
//			file = new File(HuffmanCoding.class.getResource("data.txt").toURI());//File构造器的参数是URI
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
	}

//	//建立最小堆
//	private void makeMinHeap() {
//		for (int i = 0; i < nodes.length; i++) {
//			insertNode(new Node(list.get(i)));
//		}
//	}
//
//	//向最小堆中插入节点
//	private void insertNode(Node node) {
//		//首先将node插入到数组末尾
//		nodes[currentsize] = node;
//		//然后将node节点移动到合适的位置
//		int currentIndex = currentsize;//当前节点的位置
//		int parentIndex = currentIndex / 2;//它的父节点的位置
//		while (parentIndex >= 0) {//表示父节点存在的话
//			if (nodes[parentIndex].key > node.key) {//如果子节点更小，那么就要交换位置
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
