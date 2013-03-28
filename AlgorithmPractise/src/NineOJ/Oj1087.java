package NineOJ;

import java.util.Scanner;

/**
 * Runtime Error
 * @author yinger
 */

public class Oj1087 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n;
		while (scanner.hasNext()) {
			n = scanner.nextInt();
			if (n == 0) {
				break;
			}
			while (n-- > 0) {
				fun(scanner.nextInt());
			}
		}
	}

	private static void fun(int m) {// 求约数个数的方法
		int sum = 0;
		int root = (int) Math.sqrt(m);// 先求出平方根
		for (int i = 1; i <= root; i++) {
			if (m % i == 0) {
				sum = sum + 2;
			}
		}
		if (root * root == m) {// 如果整数root正好是sum的平方根的话，前面就多算了一次
			sum = sum - 1;
		}
		System.out.println(sum);
	}

}
