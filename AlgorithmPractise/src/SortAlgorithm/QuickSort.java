package SortAlgorithm;

import java.util.Arrays;

/**
 * øÏÀŸ≈≈–Ú
 * 
 * @author yinger
 */

public class QuickSort {

	public static void main(String[] args) {
		int[] s = new int[] { 10, 23, 43, 9, 25, 87, 56, 34, 11 };
		System.out.println("Before: " + Arrays.toString(s));// Before: [10, 23, 43, 9, 25, 87, 56, 34, 11]
		sort(s);// Arrays.sort(s);
		System.out.println("After: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
	}

	// quick sort --- time: O(n*log(n))
	public static void sort(int[] s) {
		int n = s.length;
		subSorrt(s, 0, n - 1);
	}

	private static void subSorrt(int[] s, int start, int end) {
		// System.out.println(" start=" + start + "  end=" + end);
		if (start < end) {// need to sort
			int low, high, standard;
			low = start;
			high = end;
			standard = s[start];// the standard number
			while (low < high) {
				while (low < high && s[high] > standard) {// move high pointer
					high--;
				}// end while --> s[high]<standard
				s[low] = s[high];// s[low] has been saved! --> after this,s[high] has changed to s[low] and saved!
				low++;
				while (low < high && s[low] < standard) {// move low pointer
					low++;
				}// end while --> s[low]>standard
				s[high] = s[low];
				high--;
			}// end while
			if (low > high) {
				low--;//attention: if low = high,then position is low,but if low > high,then position is high = low-1
			}
			s[low] = standard;// final,end the while loop,standard number should be in (low-1) index
			// System.out.println(" standard=" + standard + "  position=" + low);
			// System.out.println("Current Array: " + Arrays.toString(s));// After: [9, 10, 11, 23, 25, 34, 43, 56, 87]
			subSorrt(s, start, low - 1);// sort left
			subSorrt(s, low + 1, end);// sort right
		}// else do nothing
	}
}
