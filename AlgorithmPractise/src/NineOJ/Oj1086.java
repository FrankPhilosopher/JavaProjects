package NineOJ;

import java.util.Scanner;

//Runtime Error
public class Oj1086 {

	static int[] l = new int[3];// ��������
	static int[] c = new int[3];// �����۸�
	static int[] dist;// ����
	static int[][] cost;// ����
	static int a;// ���
	static int b;// �յ�
	static int n;// վ������

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
				dist[i] = scanner.nextInt();// dist[i]��ʾ�ӵ�һվ����iվ�ľ���
			}
			cost = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++) {// ��ʼ��cost����
				for (int j = 1; j <= n; j++) {
					cost[i][j] = getCost(Math.abs(dist[j] - dist[i]));// ȡ����ֵ cost[i][j]��ʾ�ӵ�iվ����jվ����Ҫ����С����
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

	// ���ݾ���ֵ�õ�����ֵ
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
