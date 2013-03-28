package dsa.me;

import java.util.Arrays;

public class Kmp {

	public static void main(String[] args) {
		System.out.println(kmp("ababa", "aba"));
//		System.out.println(kmp("ababa", "bba"));
	}

	// KMP�㷨
	public static int kmp(String T, String P) {
		int next[] = buildeNext(P);
		System.out.println(Arrays.toString(next));
		int i = 0;
		int j = 0;
		while (i < T.length() && j < P.length()) {// һ��Ҫ���������ڷ�Χ�ڣ���Ȼ�ᱨ��
			if (j < 0 || T.charAt(i) == P.charAt(j)) {// ƥ�䣬i��j�������ƶ���j<0(j=-1)
				i++;
				j++;
			} else {// ��ƥ�䣬ֻҪ�ƶ�j��i��Ҫ����
				j = next[j];
			}
		}
		if (j >= P.length())
			return (i - j);
		else
			return -1;
	}

	// ��next����
	public static int[] buildeNext(String P) {
		int[] next = new int[P.length()];
		int j = 0;
		int t = -1;// ��ʼֵ��-1
		next[0] = -1;
		while (j < P.length() - 1) {
			if (t == -1 || P.charAt(t) == P.charAt(j)) {// ƥ��---�����j���ﲻ������β��ͬ���ַ�������ônext[j]�ͻ����0
				t++;
				j++;
				next[j] = t;// �����￴��while������j���ܵ���P.length()-1
			} else {// ��ƥ��
				t = next[t];
			}
		}
		return next;
	}

	// ��next����ĸĽ��汾
	public static int[] buildeNextImproved(String P) {
		int[] next = new int[P.length()];
		int j = 0;
		int t = -1;// ��ʼֵ��-1
		next[0] = -1;
		while (j < P.length() - 1) {
			if (t == -1 || P.charAt(t) == P.charAt(j)) {// ƥ��
				t++;
				j++;
				next[j] = (P.charAt(j) != P.charAt(t)) ? t : next[t];// �Ľ��ĵط�
			} else {// ��ƥ��
				t = next[t];
			}
		}
		return next;
	}

}
