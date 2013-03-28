package tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 二叉树的四种遍历方式<br>
 * 先序，中序，后序，广度优先遍历
 * 
 * @author yinger
 */
public class IteratorAlgorithm {

	public static void main(String[] args) {
		TreeNode root = new TreeNode("0");
		TreeNode node1 = new TreeNode("1");
		TreeNode node2 = new TreeNode("2");
		root.left = node1;
		root.right = node2;
		TreeNode node3 = new TreeNode("3");
		TreeNode node4 = new TreeNode("4");
		node1.left = node3;
		node1.right = node4;
		TreeNode node5 = new TreeNode("5");
		TreeNode node6 = new TreeNode("6");
		node2.left = node5;
		node2.right = node6;
		TreeNode node7 = new TreeNode("7");
		TreeNode node8 = new TreeNode("8");
		node3.left = node7;
		node4.right = node8;

		System.out.print("PreIterator: ");
		root.preIterator();// PreIterator: 0 1 3 7 4 8 2 5 6
		System.out.println();
		System.out.print("InIterator: ");
		root.inIterator();// InIterator: 7 3 1 4 8 0 5 2 6
		System.out.println();
		System.out.print("PostIterator: ");
		root.postIterator();// PostIterator: 7 3 8 4 1 5 6 2 0
		System.out.println();
		System.out.print("WideIterator: ");
		root.wideIterator();// WideIterator: 0 1 2 3 4 5 6 7 8
	}

}

class TreeNode {
	Object data;
	TreeNode left;
	TreeNode right;

	public TreeNode(Object data) {
		this.data = data;
	}

	// preIterator
	public void preIterator() {
		visit(this);
		if (left != null) {
			left.preIterator();
		}
		if (right != null) {
			right.preIterator();
		}
	}

	// inIterator
	public void inIterator() {
		if (left != null) {
			left.inIterator();
		}
		visit(this);
		if (right != null) {
			right.inIterator();
		}
	}

	// postIterator
	public void postIterator() {
		if (left != null) {
			left.postIterator();
		}
		if (right != null) {
			right.postIterator();
		}
		visit(this);
	}

	// wideIterator
	public void wideIterator() {
		// ArrayDeque:faster than stack (when used as a stack) and linkedlist(when used as a queue)
		Queue<TreeNode> deque = new ArrayDeque<TreeNode>();
		deque.add(this);
		TreeNode node;
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
	private void visit(TreeNode treeNode) {
		if (treeNode != null) {
			System.out.print(treeNode.data + "  ");
		}
	}

}