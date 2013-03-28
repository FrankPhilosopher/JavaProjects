/**
 * @Author��������  
 * @CreateTime��2011-7-16 ����09:09:57
 * @Description��AC
 */

package ACM.POJ;

import java.util.Scanner;

public class POJ1062 {

	public static int[][] map = new int[101][101];// matrix ��ʾͼ���ڽӾ���[����ͼ]
	public static int[] level = new int[101];// level
	public static int[] dist = new int[101];// distance ��0���õ��Ȩֵ
	public static int[] mark = new int[101];// mark ����Ƿ���Ժ������н���
	public static int m;// level distance
	public static int n;// goods number
	public static int ans;// result
	//public static int INF = Integer.MAX_VALUE;//����ԭ��
	public static int INF = 10000000;

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		m = sin.nextInt();// �ȼ����ƺ���Ʒ��Ŀ
		n = sin.nextInt();
		int i, j, k, t;
		for (i = 0; i <= n; i++) {
			for (j = 0; j <= n; j++) {
				map[i][j] = INF;// ��ʼ���ڽӾ���
			}
		}
		for (i = 1; i <= n; i++) {
			map[0][i] = sin.nextInt();// �۸�͵ȼ�
			level[i] = sin.nextInt();
			t = sin.nextInt();
			for (j = 1; j <= t; j++) {// ���Ʒ
				k = sin.nextInt();
				map[k][i] = sin.nextInt();// ������Ȩ��
			}
		}
		dijkstra();
	}

	public static void dijkstra() {
		int i, j, k, start, min;
		ans=INF;
		for (k = 0; k <= m; k++) {// ö�ٵȼ���
			for (i = 1; i <= n; i++) {
				if (level[i] - level[1] <= k && level[1] - level[i] <= m - k) {
					mark[i] = 1;// ��ʾ���Խ��н��ף������Է��ʵĵ�
					dist[i] = map[0][i];
				} else {
					mark[i] = 0;
					dist[i] = INF;
				}
			}
			for (i = 1; i <= n; i++) {
				start = 1;//����1Ϊ��㣬���û�п��Ե��м�㣬��ô1Ҳ�����յ���
				min = INF;//������ֵÿ��ѭ��ʱ��Ҫ����һ��
				for (j = 1; j <= n; j++) {//�õ�������̵ĵ�
					if (mark[j] == 1 && dist[j] < min) {//���Է��ʵĵ��о��������
						start = j;
						min = dist[j];
					}
				}
				mark[start] = 0;//���ոյõ��ľ�����̵ĵ���Ϊ���ܷ�����
				for (j = 1; j <= n; j++) {//�����µĵ�ļ��룬Ҫ�޸ľ���ֵ[��0��ʼ���õ����̾���ֵ]
					if (mark[j] == 1 && dist[j] > dist[start] + map[start][j]) {//�޸������Ŀ��Է��ʵĵ�ľ���ֵ
						dist[j] = dist[start] + map[start][j];
					}
				}
			}
			if(ans>dist[1])
				ans=dist[1];
		}
		System.out.println(ans);
	}

}
