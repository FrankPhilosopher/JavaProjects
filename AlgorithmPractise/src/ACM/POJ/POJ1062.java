/**
 * @Author：胡家威  
 * @CreateTime：2011-7-16 上午09:09:57
 * @Description：AC
 */

package ACM.POJ;

import java.util.Scanner;

public class POJ1062 {

	public static int[][] map = new int[101][101];// matrix 表示图的邻接矩阵[有向图]
	public static int[] level = new int[101];// level
	public static int[] dist = new int[101];// distance 从0到该点的权值
	public static int[] mark = new int[101];// mark 标记是否可以和他进行交易
	public static int m;// level distance
	public static int n;// goods number
	public static int ans;// result
	//public static int INF = Integer.MAX_VALUE;//错误原因
	public static int INF = 10000000;

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		m = sin.nextInt();// 等级限制和物品数目
		n = sin.nextInt();
		int i, j, k, t;
		for (i = 0; i <= n; i++) {
			for (j = 0; j <= n; j++) {
				map[i][j] = INF;// 初始化邻接矩阵
			}
		}
		for (i = 1; i <= n; i++) {
			map[0][i] = sin.nextInt();// 价格和等级
			level[i] = sin.nextInt();
			t = sin.nextInt();
			for (j = 1; j <= t; j++) {// 替代品
				k = sin.nextInt();
				map[k][i] = sin.nextInt();// 建立有权边
			}
		}
		dijkstra();
	}

	public static void dijkstra() {
		int i, j, k, start, min;
		ans=INF;
		for (k = 0; k <= m; k++) {// 枚举等级差
			for (i = 1; i <= n; i++) {
				if (level[i] - level[1] <= k && level[1] - level[i] <= m - k) {
					mark[i] = 1;// 表示可以进行交易，即可以访问的点
					dist[i] = map[0][i];
				} else {
					mark[i] = 0;
					dist[i] = INF;
				}
			}
			for (i = 1; i <= n; i++) {
				start = 1;//设置1为起点，如果没有可以的中间点，那么1也就是终点了
				min = INF;//这两个值每次循环时都要重置一下
				for (j = 1; j <= n; j++) {//得到距离最短的点
					if (mark[j] == 1 && dist[j] < min) {//可以访问的点中距离最近的
						start = j;
						min = dist[j];
					}
				}
				mark[start] = 0;//将刚刚得到的距离最短的点标记为不能访问了
				for (j = 1; j <= n; j++) {//由于新的点的加入，要修改距离值[从0开始到该点的最短距离值]
					if (mark[j] == 1 && dist[j] > dist[start] + map[start][j]) {//修改其他的可以访问的点的距离值
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
