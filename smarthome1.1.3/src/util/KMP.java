package util;

public class KMP {
	public static int indexOf(String target, String pattern, int start) {
		if (target != null && pattern.length() > 0 && target.length() >= pattern.length()) {
			int i = start, j = 0;// i��j�ֱ�ΪĿ�괮��ģʽ����ǰ�Ƚ��ַ����
			int[] next = getNext(pattern);
			while (i < target.length()) {
				if (j == -1 || target.charAt(i) == pattern.charAt(j)) {
					i++;// �����ȽϺ�����ַ�
					j++;
				} else
					j = next[j];// ���ģʽ����һ��ƥ���ַ������
				if (j == pattern.length())// һ��ƥ�������ƥ��ɹ�
					return i - j;// ����ƥ����Ӵ����
			}
		}
		return -1;// ƥ��ʧ��ʱ����-1
	}

	private static int[] getNext(String pattern) {// ����ģʽ��pattern��next����
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
		String a = "���ǿյ�����";
		String b = "�յ�";
		int i = indexOf(a, b);
		if (i >= 0)
			if(AppliactionUtil.DEBUG) System.out.println(i);
	}

}
