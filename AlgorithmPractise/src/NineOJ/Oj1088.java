package NineOJ;

import java.io.BufferedInputStream;
import java.util.Scanner;

//AC
public class Oj1088 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
		int l, m, s, e, sum;
		int[] flag;
		while (scanner.hasNext()) {
			flag = new int[10001];
			sum = 0;
			l = scanner.nextInt();
			m = scanner.nextInt();
			for (int i = 0; i < m; i++) {
				s = scanner.nextInt();
				e = scanner.nextInt();
				if (s > e) {
					int temp = s;
					s = e;
					e = temp;
				}
				for (int j = s; j <= e; j++) {
					flag[j] = 1;
				}
			}
			for (int i = 0; i <= l; i++) {
				if (flag[i] == 0) {
					sum++;
				}
			}
			System.out.println(sum);
		}
	}
}
