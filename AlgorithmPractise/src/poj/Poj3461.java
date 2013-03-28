package poj;

import java.util.Arrays;
import java.util.Scanner;

public class Poj3461 {

	public static void main(String[] args) {
//		System.out.println(Arrays.toString(buildNext("aba")));
		Scanner scanner = new Scanner(System.in);
		int times = scanner.nextInt();
		while (times > 0) {
			times--;
			String word = scanner.next();
			String text = scanner.next();
			System.out.println(kmp(text, word));
		}
	}

	private static int kmp(String text, String word) {
		int count = 0;
		int[] next = buildNext(word);
		System.out.println(Arrays.toString(next));
		int i = 0;
		int j = 0;
		while (i < text.length() && j < word.length()) {
			if (j < 0 || word.charAt(j) == text.charAt(i)) {
				i++;
				j++;
				if (j == word.length() - 1) {
					count++;
				}
			} else {
				j = next[j];
			}
		}
		return count;
	}

	private static int[] buildNext(String word) {
		int[] next = new int[word.length()];
		int j = 0;
		int t = -1;
		next[0] = -1;
		while (j < (word.length() - 1)) {
			if (t == -1 || word.charAt(j) == word.charAt(t)) {
				t++;
				j++;
				next[j] = (word.charAt(j) == word.charAt(t)) ? next[t] : t;
			} else {
				t = next[t];
			}
		}
		return next;
	}

}
