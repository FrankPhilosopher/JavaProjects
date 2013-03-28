/**
 * @Author��������  
 * @CreateTime��2011-5-15 ����10:50:53
 * @Description��
 */

package ACM.POJ;

import java.util.*;
import java.io.*;

public class POJ2528 {
	public static int N = 10000;
	public static Node head;

	public static void main(String[] args) {
		Scanner sc = new Scanner(new BufferedInputStream(System.in));
		Set<Integer> endpoints = new TreeSet<Integer>();//TreeSetĬ�������˵ļ��� 
		Segment[] segments = new Segment[N];
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			LinkedList<Node> nodes = new LinkedList<Node>();//����ṹ���ᱣ����ͬ����
			for (int i = 0; i < n; i++) {
				int s = sc.nextInt();
				int e = sc.nextInt();
				segments[i] = new Segment(s, e);
				endpoints.add(s);  //ע�����set��û���ظ���Ԫ�صģ�������ɢ������
				endpoints.add(e);
			}//foreachѭ������nodes�в������ڵ㣬��ʼ������������endpoint
			for (Integer endpoint : endpoints) {
				nodes.addLast(new Node(endpoint, endpoint, endpoint, endpoint));//Node(int left, int right, int leftEnd, int rightStart)
			}
			head = buildSegmentTree(nodes); //headʵ���Ͼ���һϵ�е���ɢ������߶�����Ŀ�ʼ��Ҳ�����߶����ĸ�����
			int result = 0;
			for (int i = n - 1; i >= 0; i--) {  //��������߶Σ������߶ν��и��£������Ƿ񱻸�����
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
			if (left == current.left && right == current.right) {//����߶ε����Ҷ˵�պ���һ����ɢ��������Ҷ˵㣬��ô���Ǳ�������
				current.isCovered = true;
				result = true;
			} else {    //left��right�ǵ�ǰҪ�жϵ��߶ε����Ҷ˵�
				int leftEnd = current.leftEnd;
				int rightStart = current.rightStart;
				if (right <= leftEnd) {  //���߶ηֲ��ڸ����ڵ����������
					result = updateSegmentTree(left, right, current.leftChild);
				} else if (left >= rightStart) { //���߶ηֲ��ڸ����ڵ����������
					result = updateSegmentTree(left, right, current.rightChild);
				} else { //���߶ηֲ��ڸ����ڵ������������
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
			Node leftChild = current.removeFirst(); //ɾ����һ��������������
			Node rightChild = current.removeFirst();
			Node parent = new Node(leftChild.left, rightChild.right,
					leftChild.right, rightChild.left); //���¹����µ����ڵ㣬�ı�����ֵȻ����뵽��һ��list��
			parent.leftChild = leftChild;
			parent.rightChild = rightChild;
			next.add(parent);
		}
		if (current.size() > 0) {  //size��ʼʱ��������������ʱ�����һ������ô�Ͱ����������һ������
			next.add(current.removeFirst());
		}
		if (next.size() >= 2) {
			return buildSegmentTree(next);  //�������Ͻ���
		} else // it's the root left
		{
			return next.removeFirst(); //������ֻ��ʣ����һ������ô���������ĸ��ˣ�next��nodes��û�б�Ҫ����ô������ڵ㣬����ɾ��
		}
	}
}

class Node {
	int left;
	int right;
	int leftEnd;
	int rightStart;
	int mid;  //���û����
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