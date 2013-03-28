package ex1;

//数据结构：树中的节点元素，同时又是一条指令
public class Node implements Comparable<Node> {

	public String name;//指令名称
	public double possibility;//概率
	public String code = "";//编码方式，初始时是空字符串
	public boolean isLeaf = false;//是否是叶子节点，默认都是false
	public Node leftChild;//左右孩子
	public Node rightChild;

	public Node(String name, double possibility, boolean isLeaf) {
		this.name = name;
		this.isLeaf = isLeaf;
		this.possibility = possibility;
	}

	public Node(double possibility, Node leftChild, Node rightChild) {
		this.possibility = possibility;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	//我希望是倒序
	public int compareTo(Node node) {
		if (possibility > node.possibility) {
			return -1;
		}else if (possibility < node.possibility) {
			return 1;
		}else{
			return 0;
		}
	}

}