package dsa.me;

/**
 * 蛮力算法
 * 得到第一次匹配的位置，如果不匹配返回-1
 */

public class BruteFrorce {

	public static void main(String[] args) {
		System.out.println(bruteForce("hglahsdhlghs", "shs"));//-1
		System.out.println(bruteForce("hglahsdhlghs", "hs"));//4
	}

	private static int bruteForce(String T, String P) {
		int i, j;
		for (i = 0; i < T.length()-P.length(); i++) {//注意i的范围
			for (j = 0; j < P.length(); j++) {
				if (T.charAt(i + j) != P.charAt(j)) {//不匹配
					break;
				}
			}
			if (j >= P.length()) {//匹配
				return i;
			}
		}
		return -1;
	}

}
