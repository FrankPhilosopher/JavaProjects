package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
//		String s = "hgalshd";
//		System.out.println(s.charAt(-1));
		
		HuffmanTreeNode root = new HuffmanTreeNode("root", 0.5);
		HuffmanTreeNode node1 = new HuffmanTreeNode("node1", 1.0);
		HuffmanTreeNode node2 = new HuffmanTreeNode("china", 2.0);
		HuffmanTreeNode node3 = new HuffmanTreeNode("china", 3.0);
		HuffmanTreeNode node4 = new HuffmanTreeNode("china", 3.5);
		List<HuffmanTreeNode> nodes = new ArrayList<HuffmanTreeNode>();
		Collections.addAll(nodes, root, node1, node2, node3, node4);
		
		System.out.println(nodes.get(0));
		nodes.remove(0);
		System.out.println(nodes.get(0));
		
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

}
