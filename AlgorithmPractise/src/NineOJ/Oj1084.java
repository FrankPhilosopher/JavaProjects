package NineOJ;

import java.util.Scanner;

//Runtime Error
public class Oj1084 {

	private static int[] f = new int[1000001];

	public static void main(String[] args) {
		f[0] = 0;
		f[1] = 1;
		f[2] = 2;
		int n;
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			n = scanner.nextInt();
			System.out.println(fun(n));
		}
	}

	private static int fun(int n) {//求解并返回 f(n)
		if (n == 0) {
			return 0;
		} else if (f[n] != 0) {// 前面已经求解过了
			return f[n];
		}
		if (n % 2 != 0) {
			f[n] = fun(n - 1);
		} else {
			f[n] = fun(n / 2) + fun(n - 1);
		}
		return f[n];
	}

}
