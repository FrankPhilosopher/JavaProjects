package dsa;
/*
 * 串模式匹配：KMP算法
 */
public class PM_KMP {

	public static void main(String[] args) {
		System.out.println(PM("ababacab", "aca"));
	}

	public static int PM(String T, String P) {// KMP算法
		int[] next = BuildNextImproved(P);// 构造next[]表
		int i = 0;// 主串指针
		int j = 0;// 模式串指针
		while (j < P.length() && i < T.length()) {// 自左向右逐个比较字符
			ShowProgress(T, P, i - j, j);
			ShowNextTable(next, i - j, P.length());
			System.out.println();
			if (0 > j || T.charAt(i) == P.charAt(j)) {// 若匹配，或P已移出最左侧（提问：这两个条件能否交换次序？）
				i++;
				j++;// 则转到下一对字符
			} else
				// 否则
				j = next[j];// 模式串右移（注意：主串不用回退）
		}// while
		return (i - j);
	}

	protected static int[] BuildNext(String P) {// 建立模式串P的next[]表
		int[] next = new int[P.length()];// next[]表
		int j = 0;// “主”串指针
		int t = next[0] = -1;// “模式”串指针
		while (j < P.length() - 1)
			if (0 > t || P.charAt(j) == P.charAt(t)) {// 匹配
				j++;
				t++;
				next[j] = t;// 此句可以改进...
			} else
				// 失配
				t = next[t];
		for (j = 0; j < P.length(); j++)
			System.out.print("\t" + P.charAt(j));
		System.out.print("\n");
		ShowNextTable(next, 0, P.length());
		return (next);
	}

	protected static int[] BuildNextImproved(String P) {// 建立模式串P的next[]表（改进版本）
		int[] next = new int[P.length()];
		;// next[]表
		int j = 0;// “主”串指针
		int t = next[0] = -1;// “模式”串指针
		while (j < P.length() - 1)
			if (0 > t || P.charAt(j) == P.charAt(t)) {// 匹配
				j++;
				t++;
				next[j] = (P.charAt(j) != P.charAt(t)) ? t : next[t];// 注意此句与未改进之前的区别
			} else
				// 失配
				t = next[t];
		for (j = 0; j < P.length(); j++)
			System.out.print("\t" + P.charAt(j));
		System.out.print("\n");
		ShowNextTable(next, 0, P.length());
		return (next);
	}

	protected static void ShowNextTable(int[] N, int offset, int length) {// 显示next[]表，供演示分析
		int i;
		for (i = 0; i < offset; i++)
			System.out.print("\t");
		for (i = 0; i < length; i++)
			System.out.print("\t" + N[i]);
		System.out.print("\n\n");
	}

	protected static void ShowProgress(// 动态显示匹配进展
			String T,// 主串
			String P,// 模式串
			int i,// 模式串相对于主串的起始位置
			int j)// 模式串的当前字符
	{
		int t;
		System.out.println("-------------------------------------------");
		for (t = 0; t < T.length(); t++)
			System.out.print("\t" + T.charAt(t));
		System.out.print("\n");
		if (0 <= i + j) {
			for (t = 0; t < i + j; t++)
				System.out.print("\t");
			System.out.print("\t|");
		}
		System.out.println();
		for (t = 0; t < i; t++)
			System.out.print("\t");
		for (t = 0; t < P.length(); t++)
			System.out.print("\t" + P.charAt(t));
		System.out.print("\n");
		System.out.println();
	}
}
