package dsa.me;

/**
 * �����㷨
 * �õ���һ��ƥ���λ�ã������ƥ�䷵��-1
 */

public class BruteFrorce {

	public static void main(String[] args) {
		System.out.println(bruteForce("hglahsdhlghs", "shs"));//-1
		System.out.println(bruteForce("hglahsdhlghs", "hs"));//4
	}

	private static int bruteForce(String T, String P) {
		int i, j;
		for (i = 0; i < T.length()-P.length(); i++) {//ע��i�ķ�Χ
			for (j = 0; j < P.length(); j++) {
				if (T.charAt(i + j) != P.charAt(j)) {//��ƥ��
					break;
				}
			}
			if (j >= P.length()) {//ƥ��
				return i;
			}
		}
		return -1;
	}

}
