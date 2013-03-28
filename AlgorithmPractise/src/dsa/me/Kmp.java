package dsa.me;

import java.util.Arrays;

public class Kmp {

	public static void main(String[] args) {
		System.out.println(kmp("ababa", "aba"));
//		System.out.println(kmp("ababa", "bba"));
	}

	// KMP算法
	public static int kmp(String T, String P) {
		int next[] = buildeNext(P);
		System.out.println(Arrays.toString(next));
		int i = 0;
		int j = 0;
		while (i < T.length() && j < P.length()) {// 一定要限制它们在范围内，不然会报错
			if (j < 0 || T.charAt(i) == P.charAt(j)) {// 匹配，i和j都向右移动，j<0(j=-1)
				i++;
				j++;
			} else {// 不匹配，只要移动j，i不要回溯
				j = next[j];
			}
		}
		if (j >= P.length())
			return (i - j);
		else
			return -1;
	}

	// 求next数组
	public static int[] buildeNext(String P) {
		int[] next = new int[P.length()];
		int j = 0;
		int t = -1;// 初始值是-1
		next[0] = -1;
		while (j < P.length() - 1) {
			if (t == -1 || P.charAt(t) == P.charAt(j)) {// 匹配---如果在j这里不存在首尾相同的字符串，那么next[j]就会等于0
				t++;
				j++;
				next[j] = t;// 由这里看出while条件中j不能等于P.length()-1
			} else {// 不匹配
				t = next[t];
			}
		}
		return next;
	}

	// 求next数组的改进版本
	public static int[] buildeNextImproved(String P) {
		int[] next = new int[P.length()];
		int j = 0;
		int t = -1;// 初始值是-1
		next[0] = -1;
		while (j < P.length() - 1) {
			if (t == -1 || P.charAt(t) == P.charAt(j)) {// 匹配
				t++;
				j++;
				next[j] = (P.charAt(j) != P.charAt(t)) ? t : next[t];// 改进的地方
			} else {// 不匹配
				t = next[t];
			}
		}
		return next;
	}

}
