package ex1;

/**
 * huffman编码实现类
 * 
 * 需要注意的是：最小堆中的节点关系和哈夫曼树中节点的关系没有关系！
 * 
 * @author yinger
 * 
 */

public class HuffmanCoding implements ICoding {

	public Node[] nodes;//输入的数据节点
	public Node[] minheap;//用数组来保存堆
	public int currentsize = 0;//堆当前的大小
	public double wpl;//wpl

	public HuffmanCoding(Node[] nodes) {
		this.nodes = nodes;
		this.minheap = new Node[nodes.length];
	}

	//算法执行过程
	public void doCoding() {
		initCode();
		buildMinHeap();//build完成之后minheap和nodes中的node的id是一样的，也就是说，它们引用的是相同的对象
		while (currentsize != 1) {//只有在最后插入一个节点，它是最终的根节点停止
			doOneStep();//它是一个原子操作，操作完了之后判断currentsize是否为1
		}
		System.out.println("采用Huffman编码得到的编码结果：");
		makeHuffmanCode(minheap[0]);
		displayHuffmanCode();
		calWPL();
	}

	//初始化code值
	private void initCode() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].code = "";
		}
	}

	//计算WPL
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * (nodes[i].code.length());
		}
		System.out.println("WPL=" + wpl);
	}

	//建立初始最小堆
	private void buildMinHeap() {
		for (int i = 0; i < nodes.length; i++) {
			insertNode(nodes[i]);
		}
	}

//“走一步”，也就是合并一次！也就是删除两个最小的节点，生成一个新节点，并且插入其中
	private void doOneStep() {
		Node leftNode = removeNode();
		Node rightNode = removeNode();
		Node newNode = new Node(leftNode.possibility + rightNode.possibility, leftNode, rightNode);//在这里创建了新的node插入到了minheap中
		insertNode(newNode);
	}

//给节点的编码方式赋值
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

//插入节点
	private void insertNode(Node node) {
		//首先将node插入到数组末尾
		minheap[currentsize] = node;
		//然后将node节点移动到合适的位置
		int currentIndex = currentsize;//当前节点的位置
		int parentIndex = (currentIndex - 1) / 2;//它的父节点的位置
		while (parentIndex >= 0) {//表示父节点存在的话
			if (minheap[parentIndex].possibility > node.possibility) {//如果子节点更小，那么就要交换位置
				Node tempNode = minheap[parentIndex];
				minheap[parentIndex] = node;
				minheap[currentIndex] = tempNode;
				currentIndex = parentIndex;
				parentIndex = (currentIndex - 1) / 2;
			} else {
				break;//当index=0时，parent=0！要保证这种情况下也是能够跳出循环的
			}
		}
		currentsize++;
	}

//删除节点
	private Node removeNode() {
		//判断是否还有节点在堆中
		if (currentsize < 1) {
			return null;
		}
		Node top = minheap[0];
		//将它放在堆顶
		minheap[0] = minheap[currentsize - 1];//最后一个元素放在currentsize - 1
		currentsize--;//减一的时机要注意
		int index = 0;
		int left, right, smallNode;//smallNode是用来表示孩子节点中较小的那个节点
		while (index < currentsize / 2) {//在当前大小的1/2内的节点才会有孩子节点
			left = 2 * index + 1;
			right = left + 1;
			//得到较小孩子节点的index，如果存在右节点并且比左节点小，那么就是右节点
			if (right < currentsize && minheap[right].possibility < minheap[left].possibility) {
				smallNode = right;
			} else {
				smallNode = left;
			}
			//将较小节点和父节点比较，如果父节点大的话，那么就要进行一次交换，否则就要退出！
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

	//显示最终生成的哈夫曼编码
	private void displayHuffmanCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

	//输出堆中的数据
//	private void displayHeap() {
//		for (int i = 0; i < currentsize; i++) {
//			System.out.print(minheap[i].possibility + "\t");
//		}
//		System.out.println();
//	}

}
