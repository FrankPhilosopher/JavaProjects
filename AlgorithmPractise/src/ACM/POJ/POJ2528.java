/**
 * @Author：胡家威  
 * @CreateTime：2011-5-15 上午10:50:53
 * @Description：
 */

package ACM.POJ;

import java.util.*;
import java.io.*;

public class POJ2528 {
	public static int N = 10000;
	public static Node head;

	public static void main(String[] args) {
		Scanner sc = new Scanner(new BufferedInputStream(System.in));
		Set<Integer> endpoints = new TreeSet<Integer>();//TreeSet默认排序了的集合 
		Segment[] segments = new Segment[N];
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			LinkedList<Node> nodes = new LinkedList<Node>();//链表结构，会保存相同的数
			for (int i = 0; i < n; i++) {
				int s = sc.nextInt();
				int e = sc.nextInt();
				segments[i] = new Segment(s, e);
				endpoints.add(s);  //注意这里，set是没有重复的元素的，用于离散化区间
				endpoints.add(e);
			}//foreach循环，向nodes中插入树节点，初始化各参数都是endpoint
			for (Integer endpoint : endpoints) {
				nodes.addLast(new Node(endpoint, endpoint, endpoint, endpoint));//Node(int left, int right, int leftEnd, int rightStart)
			}
			head = buildSegmentTree(nodes); //head实际上就是一系列的离散化后的线段区间的开始，也就是线段树的根区间
			int result = 0;
			for (int i = n - 1; i >= 0; i--) {  //逆序遍历线段，并对线段进行更新，看其是否被覆盖了
				Segment seg = segments[i];
				int left = seg.start;
				int right = seg.end;
				if (updateSegmentTree(left, right, head)) {
					result++;
				}
			}
			System.out.println(result);
		}
	}

	public static boolean updateSegmentTree(int left, int right, Node current) {
		boolean result;
		if (current.isCovered) {
			result = false;
		} else {
			if (left == current.left && right == current.right) {//这个线段的左右端点刚好是一个离散区间的左右端点，那么就是被覆盖了
				current.isCovered = true;
				result = true;
			} else {    //left和right是当前要判断的线段的左右端点
				int leftEnd = current.leftEnd;
				int rightStart = current.rightStart;
				if (right <= leftEnd) {  //该线段分布在该树节点的左子树中
					result = updateSegmentTree(left, right, current.leftChild);
				} else if (left >= rightStart) { //该线段分布在该树节点的右子树中
					result = updateSegmentTree(left, right, current.rightChild);
				} else { //该线段分布在该树节点的左右子树中
					boolean r1 = updateSegmentTree(left, leftEnd,
							current.leftChild);
					boolean r2 = updateSegmentTree(rightStart, right,
							current.rightChild);
					result = (r1 || r2);
				}
				current.isCovered = (current.leftChild.isCovered && current.rightChild.isCovered);
			}
		}
		return result;
	}

	public static Node buildSegmentTree(LinkedList<Node> current) {
		LinkedList<Node> next = new LinkedList<Node>();
		while (current.size() >= 2) {
			Node leftChild = current.removeFirst(); //删除第一个，并将它返回
			Node rightChild = current.removeFirst();
			Node parent = new Node(leftChild.left, rightChild.right,
					leftChild.right, rightChild.left); //重新构造新的树节点，改变数据值然后放入到另一个list中
			parent.leftChild = leftChild;
			parent.rightChild = rightChild;
			next.add(parent);
		}
		if (current.size() > 0) {  //size开始时可能是奇数，此时最后还有一个，那么就把它当做最后一个区间
			next.add(current.removeFirst());
		}
		if (next.size() >= 2) {
			return buildSegmentTree(next);  //继续向上建树
		} else // it's the root left
		{
			return next.removeFirst(); //如果最后只是剩余了一个，那么它就是树的根了，next和nodes都没有必要存那么多的树节点，可以删除
		}
	}
}

class Node {
	int left;
	int right;
	int leftEnd;
	int rightStart;
	int mid;  //这个没有用
	Node parent;
	Node leftChild;
	Node rightChild;
	boolean isCovered;

	Node(int left, int right, int leftEnd, int rightStart) {
		this.left = left;
		this.right = right;
		this.leftEnd = leftEnd;
		this.rightStart = rightStart;
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
		this.isCovered = false;
	}
}

class Segment {
	int start;
	int end;

	Segment(int s, int e) {
		this.start = s;
		this.end = e;
	}
}