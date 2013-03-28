package util;

public class KMP {
	public static int indexOf(String target, String pattern, int start) {
		if (target != null && pattern.length() > 0 && target.length() >= pattern.length()) {
			int i = start, j = 0;// i、j分别为目标串、模式串当前比较字符序号
			int[] next = getNext(pattern);
			while (i < target.length()) {
				if (j == -1 || target.charAt(i) == pattern.charAt(j)) {
					i++;// 继续比较后面的字符
					j++;
				} else
					j = next[j];// 获得模式串下一次匹配字符的序号
				if (j == pattern.length())// 一次匹配结束，匹配成功
					return i - j;// 返回匹配的子串序号
			}
		}
		return -1;// 匹配失败时返回-1
	}

	private static int[] getNext(String pattern) {// 返回模式串pattern的next数组
		int j = 0, k = -1;
		int[] next = new int[pattern.length()];
		next[0] = -1;
		while (j < pattern.length() - 1)
			if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
				j++;
				k++;
				next[j] = k;
			} else
				k = next[k];
		return next;
	}

	public static int indexOf(String target, String pattern) {
		return indexOf(target, pattern, 0);
	}

	public static void main(String args[]) {
		String a = "我是空调啊啊";
		String b = "空调";
		int i = indexOf(a, b);
		if (i >= 0)
			if(AppliactionUtil.DEBUG) System.out.println(i);
	}

}
