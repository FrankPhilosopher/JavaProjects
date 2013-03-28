package NineOJ;

import java.io.BufferedInputStream;
import java.util.Scanner;

//Accepted
public class Oj1086_2 {

	static long L[] = new long[3];
	static long C[] = new long[3];
	static int station[];
	static long cost[];

	public static void main(String[] args) {
		Scanner s = new Scanner(new BufferedInputStream(System.in));
		while (s.hasNextLong()) {
			for (int i = 0; i < 6; i++) {
				if (i > 2)
					C[i - 3] = s.nextLong();
				else
					L[i] = s.nextLong();
			}
			int a = s.nextInt() - 1;
			int b = s.nextInt() - 1;
			cost = new long[b - a + 1];
			int n = s.nextInt();
			station = new int[n];
			for (int i = 1; i < n; i++)
				station[i] = s.nextInt();//station[i]表示第1站到第i+1站的距离
			f(a, b);
			if (b - a > 0)
				System.out.println(cost[b - a]);//cost[i]表示从起点站到距离起点站i站的那个站的最小花费
			if (b == a)
				System.out.println(0);
		}
	}

	static void f(int a, int b) {
		for (int i = a + 1; i <= b; i++) { // 循环计算从起点开始到终点站之间的每个站的最小花费
			int count = 0;
			int x = i;
			while (x - count - 1 >= a && station[x] - station[x - count - 1] <= L[2])
				count++;// 计算当前站前面不超过L3的站有几个
			long min = Long.MAX_VALUE;
			for (int j = 0; j < count; j++) { // 依次算出到当前站的最小花费 --- 肯定是越靠近的站距离越近
				min = Math.min(min, cost[i - j - 1 - a] + getCost(station[i] - station[i - j - 1]));
			}
			cost[i - a] = min;
		}
	}

	static long getCost(long len) {
		if (len == 0)
			return 0;
		else if (len <= L[0])
			return C[0];
		else if (len <= L[1])
			return C[1];
		else
			return C[2];
	}

}