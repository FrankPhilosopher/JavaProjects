package NineOJ;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Oj1201 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
		while (scanner.hasNext()) {
			int n = scanner.nextInt();
			Node root = new Node(scanner.nextInt());// 第一个数字是树根
			n--;
			while (n-- > 0) {
				Node node = new Node(scanner.nextInt());
				insertNode(root, node);
			}
			preOrder(root);
			System.out.println();
			inOrder(root);
			System.out.println();
			postOrder(root);
		}
	}

	private static void postOrder(Node root) {
		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			visite(root);
		}
	}

	private static void inOrder(Node root) {
		if (root != null) {
			inOrder(root.left);
			visite(root);
			inOrder(root.right);
		}
	}

	private static void preOrder(Node root) {
		if (root != null) {
			visite(root);
			preOrder(root.left);
			preOrder(root.right);
		}
	}

	private static void visite(Node root) {
		if (root != null) {
			System.out.print(root.key + " ");
		}
	}

	private static void insertNode(Node root, Node node) {
		if (node.key == root.key) {
			return;// 相同元素直接丢掉不处理
		} else if (node.key > root.key) {
			if (root.right == null) {
				root.right = node;
			} else {
				insertNode(root.right, node);
			}
		} else {
			if (root.left == null) {
				root.left = node;
			} else {
				insertNode(root.left, node);
			}
		}
	}

}

class Node {

	int key;
	Node left;
	Node right;

	public Node(int key) {
		this.key = key;
	}

}