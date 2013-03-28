package SortAlgorithm;

import java.util.Arrays;

/**
 * Ã°ÅÝÅÅÐò
 * 
 * @author yinger
 */

public class BubbleSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// bubble sort --- time: O(n^2)
	public static void sort(int[] s) {
		int n = s.length;
		int i, j, temp, flag;// flag: change happened?
		for (i = n - 1; i >= 1; i--) {// number from end to begin
			flag = 0;
			for (j = 0; j < i; j++) {// range is smaller
				if (s[j] > s[j + 1]) {
					temp = s[j];
					s[j] = s[j + 1];
					s[j + 1] = temp;
					flag = 1;
				}
			}
			if (flag == 0) {// nothing change, sort is done
				break;
			}
		}
	}
}
