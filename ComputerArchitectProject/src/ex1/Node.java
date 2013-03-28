package ex1;

//���ݽṹ�����еĽڵ�Ԫ�أ�ͬʱ����һ��ָ��
public class Node implements Comparable<Node> {

	public String name;//ָ������
	public double possibility;//����
	public String code = "";//���뷽ʽ����ʼʱ�ǿ��ַ���
	public boolean isLeaf = false;//�Ƿ���Ҷ�ӽڵ㣬Ĭ�϶���false
	public Node leftChild;//���Һ���
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

	//��ϣ���ǵ���
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