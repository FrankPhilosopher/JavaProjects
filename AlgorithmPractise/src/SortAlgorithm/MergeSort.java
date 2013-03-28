package SortAlgorithm;

import java.util.Arrays;

/**
 * ∫œ≤¢≈≈–Ú
 * 
 * @author yinger
 */

public class MergeSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// merge sort --- time: O(n*log(n))
	public static void sort(int[] s) {
		int n = s.length;
		mergesort(s, 0, n - 1);
	}

	private static void mergesort(int[] s, int start, int end) {//merge from start to end
		if (start < end) {
			int mid = (start + end) / 2;
			mergesort(s, start, mid);// sort left
			mergesort(s, mid + 1, end);// sort right
			int i = start, j = mid + 1, k, len;// left and right are all sorted,then merge them!
			len = end - start + 1;
			int[] temp = new int[len];//use another array to merge
			for (k = 0; k < len; k++) {// i range (start,mid),and j range (mid+1,end)
				if ((i <= mid && j <= end && s[i] < s[j]) || j > end) {// if j>end,then copy must copy i
					temp[k] = s[i];
					i++;
				} else {
					temp[k] = s[j];
					j++;
				}
			}
			for (k = 0; k < len; k++) {// temp is sorted array
				s[k + start] = temp[k];// copy temp to s
			}
		}
	}
}
