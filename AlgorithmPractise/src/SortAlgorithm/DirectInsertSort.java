package SortAlgorithm;

import java.util.Arrays;

/**
 * ÷±Ω”≤Â»Î≈≈–Ú
 * 
 * @author yinger
 */

public class DirectInsertSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// direct insert sort --- time: O(n^2)
	public static void sort(int[] s) {
		int n = s.length;
		int i, j, temp;
		for (i = 1; i < n; i++) {// begin with 1,end with (n-1)
			temp = s[i];
			for (j = i - 1; j >= 0 && s[j] > temp; j--) {// condition: current one (s[i]) is smaller
				s[j + 1] = s[j];// move back one step
			}
			s[j + 1] = temp;// final position:j+1
		}

	}
}
