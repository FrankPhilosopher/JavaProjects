package NineOJ;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {

	static long L[] = new long[3];
	static long P[] = new long[3];
	static int station[];
	static long dist[];

	public static void main(String[] args) {
		Scanner s = new Scanner(new BufferedInputStream(System.in));
		while (s.hasNextLong()) {
			for (int i = 0; i < 6; i++) {
				if (i > 2)
					P[i - 3] = s.nextLong();
				else
					L[i] = s.nextLong();
			}
			int a = s.nextInt() - 1;
			int b = s.nextInt() - 1;
			int n = s.nextInt();
			station = new int[n];
			dist = new long[b - a + 1];
			for (int i = 1; i < n; i++)
				station[i] = s.nextInt();
			f(a, b);
			if (b - a > 0)
				System.out.println(dist[b - a]);
			if (b == a)
				System.out.println(0);
		}
	}

	static void f(int a, int b) {
		for (int i = a + 1; i <= b; i++) { // 循环计算从起点开始到每个站的最小花费
			int count = 0;
			int x = i;
			while (x - count - 1 >= a && station[x] - station[x - count - 1] <= L[2])
				// 计算本站前面不超过L3的站有几个
				count++;
			long min = Long.MAX_VALUE;
			for (int j = 0; j < count; j++) { // 依次算出 到本站的最小花费。 实用动态规划的思想。
				min = Math.min(min, dist[i - j - 1 - a] + getP(station[i] - station[i - j - 1]));
			}
			dist[i - a] = min;
		}
	}

	static long getP(long len) {
		if (len == 0)
			return 0;
		else if (len <= L[0])
			return P[0];
		else if (len <= L[1])
			return P[1];
		else
			return P[2];
	}

}