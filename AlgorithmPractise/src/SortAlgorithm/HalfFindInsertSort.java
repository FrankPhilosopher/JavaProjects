package SortAlgorithm;

import java.util.Arrays;

/**
 * ’€∞Î≤Â»Î≈≈–Ú
 * 
 * @author yinger
 */

public class HalfFindInsertSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// half find insert sort --- time: O(n^2)
	public static void sort(int[] s) {
		int n = s.length;
		int i, j, temp;
		int low, high, mid;
		for (i = 1; i < n; i++) {// begin with 1,end with (n-1)
			temp = s[i];
			low = 0;
			high = i - 1;
			while (low <= high) {// find the insert position --- low
				mid = (low + high) / 2;
				if (s[mid] > temp) {
					high = mid - 1;
				} else {// equal in here! equal can be ignored
					low = mid + 1;
				}
			}
			for (j = i; j > low; j--) {// move back one step
				s[j] = s[j - 1];
			}
			s[low] = temp;
		}

	}
}
