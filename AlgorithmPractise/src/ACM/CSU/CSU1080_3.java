package ACM.CSU;

/**
 * 最短路径算法
 * 根据标程改写：主要的区别在于走迷宫时的方向上
 * 设置两个数组，它们结合起来就是迷宫的向四个方向的位置
 * AC
 */
import java.util.Scanner;

public class CSU1080_3 {

	public static int[][] maze;
	public static int[][] dis;
	public static int[][] mark;//是否可以访问，1表示可以
	public static int[] nx = { 1, 0, -1, 0 };// 不用给定大小！int[4]会提示错误
	public static int[] ny = { 0, 1, 0, -1 };//从左到右对应的分别是向下，右，上，左
	public static int m, n, sr, sc, er, ec;

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t = sin.nextInt();
		for (int k = 1; k <= t; k++) {
			m = sin.nextInt();
			n = sin.nextInt();
			sr = sin.nextInt();
			sc = sin.nextInt();
			er = sin.nextInt();
			ec = sin.nextInt();
			maze = new int[m + 2][n + 2];// 和标程不同的是：我的数组每次测试都重新生成
			dis = new int[m + 2][n + 2];
			mark = new int[m + 2][n + 2];
			for (int i = 0; i <= m + 1; i++) {// 修改原始的赋值方式，减少循环次数，首先把四周的点都使得他们不用访问
				mark[i][0] = 1;
				mark[i][n + 1] = 1;
				maze[i][0] = 1;//注意要给maze数组的这一部分的点赋值为1，不然后面会出现数组越界的问题，其实没越界但是这里初始时没有值，无法判断！
				maze[i][n + 1] = 1;
			}
			for (int j = 0; j <= n + 1; j++) {
				mark[0][j] = 1;
				mark[m + 1][j] = 1;
				maze[0][j] = 1;
				maze[m + 1][j] = 1;
			}
			for (int i = 1; i <= m; i++) {
				String s = sin.next();
				for (int j = 1; j <= n; j++) {
					maze[i][j] = Integer.parseInt(s.substring(j - 1, j));
				}
			}
			if (maze[sr][sc] == 1 || maze[er][ec] == 1) {// 可以先判断首和尾是否是通路，不是肯定没有路径
				System.out.println("No Path.");
			} else {
				for (int i = 0; i <= m + 1; i++) {
					for (int j = 0; j <= n + 1; j++) {
						dis[i][j] = 0;//初始化时所有点到起点的距离都是0
						mark[i][j]=0;
					}
				}
				dis[sr][sc] = 0;
				mark[sr][sc] = 1;
				setDistance(sr,sc,0);//从起点开始调用setDistance方法
				if (dis[er][ec] != 0 ) {
					System.out.println(dis[er][ec]);
				} else {
					System.out.println("No Path.");
				}
			}
		}
	}

	//设置点到起点的距离
	public static void setDistance(int r,int c,int dist) {
		dis[r][c]=dist;
		for(int i=0;i<4;i++){//四个方向都要设置distance
			//注意条件：当前点的下一个点没有访问过，并且它是通路，并且它的距离还是0或者是比当前点的距离大1(可能之前设置了值)
			//还要注意条件判断的顺序，mark在maze之前，不然会出现数组越界
			if(mark[r+nx[i]][c+ny[i]]==0 && maze[r+nx[i]][c+ny[i]]==0 &&(dis[r+nx[i]][c+ny[i]]==0 || dis[r+nx[i]][c+ny[i]]>dist+1) ){
				setDistance(r+nx[i], c+ny[i], dist+1);
			}
		}
	}
}

