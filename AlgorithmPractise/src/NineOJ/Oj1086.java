package NineOJ;

import java.util.Scanner;

//Runtime Error
public class Oj1086 {

	static int[] l = new int[3];// 三个距离
	static int[] c = new int[3];// 三个价格
	static int[] dist;// 距离
	static int[][] cost;// 花费
	static int a;// 起点
	static int b;// 终点
	static int n;// 站点总数

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			for (int i = 0; i < 6; i++) {
				if (i >= 3) {
					c[i - 3] = scanner.nextInt();
					continue;
				}
				l[i] = scanner.nextInt();
			}
			a = scanner.nextInt();
			b = scanner.nextInt();
			if (a > b) {
				int temp = a;
				a = b;
				b = temp;
			}
			n = scanner.nextInt();
			dist = new int[n + 1];
			for (int i = 2; i <= n; i++) {
				dist[i] = scanner.nextInt();// dist[i]表示从第一站到第i站的距离
			}
			cost = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++) {// 初始化cost数组
				for (int j = 1; j <= n; j++) {
					cost[i][j] = getCost(Math.abs(dist[j] - dist[i]));// 取绝对值 cost[i][j]表示从第i站到第j站所需要的最小花费
				}
			}
			int min = cost[a][b];
			for (int k = a + 1; k < b; k++) {
				if (min > cost[a][k] + cost[k][b]) {
					min = cost[a][k] + cost[k][b];
				}
			}
			System.out.println(min);
		}
	}

	// 根据距离值得到花费值
	private static int getCost(int len) {
		if (len == 0) {
			return 0;
		} else if (len <= l[0]) {
			return c[0];
		} else if (len <= l[1]) {
			return c[1];
		} else {
			return c[2];
		}
	}

}
