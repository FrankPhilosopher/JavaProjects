package tree;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ¹þ·òÂüÊ÷
 * 
 * @author yinger
 */
public class HuffmanTree {

	public static void main(String[] args) {
		HuffmanTreeNode root = new HuffmanTreeNode("root", 0.5);
		HuffmanTreeNode node1 = new HuffmanTreeNode("node1", 1.0);
		HuffmanTreeNode node2 = new HuffmanTreeNode("node2", 2.0);
		HuffmanTreeNode node3 = new HuffmanTreeNode("node3", 3.0);
		HuffmanTreeNode node4 = new HuffmanTreeNode("node4", 3.5);
		LinkedList<HuffmanTreeNode> nodes = new LinkedList<HuffmanTreeNode>();
		Collections.addAll(nodes, root, node1, node2, node3, node4);
		HuffmanTree huffmanTree = new HuffmanTree();
		HuffmanTreeNode treeRoot = huffmanTree.createHuffmanTree(nodes);
		treeRoot.wideIterator();
	}

	public HuffmanTreeNode createHuffmanTree(LinkedList<HuffmanTreeNode> nodes) {// create huffmantree
		insertSort(nodes, 0, nodes.size() - 1);// sort nodes using insrt sort
		while (nodes.size() > 1) {
			HuffmanTreeNode left = nodes.pollFirst();// remove the two smallest
			HuffmanTreeNode right = nodes.pollFirst();
			HuffmanTreeNode parent = new HuffmanTreeNode(left.data + "+" + right.data, left.weight + right.weight);
			parent.left = left;
			parent.right = right;
			insertNode(nodes, parent);
		}// end while,nodes.size = 1 --> the root node
		return nodes.peek();
	}

	// insert new node into nodes,and make it sorted!
	private void insertNode(LinkedList<HuffmanTreeNode> nodes, HuffmanTreeNode parent) {
		nodes.addLast(parent);// first add it,make size++,then sort it!
//		System.out.println("Befor: insert new node:");
//		for (HuffmanTreeNode huffmanTreeNode : nodes) {
//			System.out.print(huffmanTreeNode.weight + "  ");
//		}
//		System.out.println();
		int j, low, high, mid, size = nodes.size();
		low = 0;
		high = size - 2;// attention:the last node is not included!
		while (low <= high) {// final position is low
			mid = (low + high) / 2;
			if (nodes.get(mid).weight > parent.weight) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		for (j = size - 1; j > low; j--) {// move back some elements
			nodes.set(j, nodes.get(j - 1));
		}
		nodes.set(low, parent);
//		System.out.println("After: insert new node:" + "  low=" + low);
//		for (HuffmanTreeNode huffmanTreeNode : nodes) {
//			System.out.print(huffmanTreeNode.weight + "  ");
//		}
//		System.out.println();
	}

	private void insertSort(List<HuffmanTreeNode> nodes, int start, int end) {// half find insert sort
		int i, j, size = nodes.size();
		int low, high, mid;
		HuffmanTreeNode tempNode;
		for (i = 1; i < size; i++) {// 0 - (i-1) has been sorted,now insert i
			low = 0;
			high = i - 1;
			tempNode = nodes.get(i);
			while (low <= high) {// final right position is low
				mid = (low + high) / 2;
				if (nodes.get(mid).weight > tempNode.weight) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			}
			for (j = i; j > low; j--) {// move back some elements
				nodes.set(j, nodes.get(j - 1));
			}
			nodes.set(low, tempNode);
		}
//		System.out.println("After insertsort:");
//		for (HuffmanTreeNode huffmanTreeNode : nodes) {
//			System.out.print(huffmanTreeNode.weight + "  ");
//		}
//		System.out.println();
	}

}

class HuffmanTreeNode {

	String data;// define data is string
	double weight;// weight of the node
	HuffmanTreeNode left;
	HuffmanTreeNode right;

	public HuffmanTreeNode(String data, double weight) {
		this.data = data;
		this.weight = weight;
	}

	// wideIterator
	public void wideIterator() {
		// ArrayDeque:faster than stack (when used as a stack) and linkedlist(when used as a queue)
		Queue<HuffmanTreeNode> deque = new ArrayDeque<HuffmanTreeNode>();
		deque.add(this);
		HuffmanTreeNode node;
		while ((node = deque.poll()) != null) {
			visit(node);
			if (node.left != null) {
				deque.add(node.left);
			}
			if (node.right != null) {
				deque.add(node.right);
			}
		}
	}

	// visit function
	private void visit(HuffmanTreeNode node) {
		if (node != null) {
			System.out.println(node.data + "  " + node.weight + "  ");
		}
	}

}