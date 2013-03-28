package SortAlgorithm;

import java.util.Arrays;

/**
 * —°‘Ò≈≈–Ú
 * 
 * @author yinger
 */

public class SelectSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// select sort --- time: O(n^2)
	public static void sort(int[] s) {
		int n = s.length;
		int i, j, min, minIndex, temp;// minIndex: the index of the min number
		for (i = 0; i < n - 1; i++) {// every time select the min number
			minIndex = i;
			min = s[minIndex];
			for (j = i + 1; j <= n - 1; j++) {
				if (s[j] < min) {
					minIndex = j;
					min = s[j];
				}
			}
			if (minIndex != i) {// only one change
				temp = s[i];
				s[i] = s[minIndex];
				s[minIndex] = temp;
			}
		}
	}
}
