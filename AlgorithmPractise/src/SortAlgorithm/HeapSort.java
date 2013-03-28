package SortAlgorithm;

import java.util.Arrays;

/**
 * ¶ÑÅÅÐò
 * 
 * @author yinger
 */

public class HeapSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// heap sort --- time: O(n*log(n))
	public static void sort(int[] s) {
		int n = s.length;
		int last = (n - 2) / 2;// the last node that is not leaf node = the parent of last node is (n-1-1)/2
		int i, j, temp;// minIndex = the index of the min node
		for (i = last; i >= 0; i--) {// first:build a min heap
			adjustHeap(s, i, n - 1);// then adjust the heap
		}
		System.out.println("Build Heap: " + Arrays.toString(s));// Build Heap: [9, 10, 43, 11, 25, 87, 56, 34, 23]
		for (j = n - 1; j > 0; j--) {
			temp = s[0];// first:change the start node and the end node
			s[0] = s[j];
			s[j] = temp;
			adjustHeap(s, 0, j - 1);// after change,adjust the heap
			System.out.println("Current Heap: " + " j = " + j + "   " + Arrays.toString(s));
		}
	}

	private static void adjustHeap(int[] s, int start, int end) {// adjust the heap from start node to end node
		int j, l, r, min, minIndex, temp;// minIndex = the index of the min node
		j = start;
		temp = s[start];// save it!
		while (2 * j + 1 <= end) {//2 * j + 1 <= end means in giving range,node j still has child
			l = 2 * j + 1;// left node
			r = 2 * j + 2;// right node --- not always exist
			if (r <= end && s[r] < s[l]) {// find the min node of childe nodes
				min = s[r];// right node is smaller
				minIndex = r;
			} else {
				min = s[l];// left node is smaller
				minIndex = l;
			}
			if (temp > min) {// parent is bigger,so change!
				s[j] = min;
				s[minIndex] = temp;
				j = minIndex;// change the index of the node,it means the current node has moved to index j
			} else {
				break;// if not,break the while loop
			}
		}
	}
}
