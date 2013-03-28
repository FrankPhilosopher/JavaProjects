package ACM.CSU;
/**
 * ��������ǵݹ��㷨���������·��
 * �����step������̵Ĳ��������ǿ���ͨ���㷨�õ���·���ı�step��ֵ
 * ���ǿ��ܸı���ֵ��Ȼ������̵Ĳ���
 */
import java.util.Scanner;
import java.util.Stack;

public class CSU1080_2 {

	public static int[][] maze;
	public static int[][] visited;
	public static int m,n,sr,sc,er,ec;
	public static int step;
	public static Stack<Position> path=new Stack<Position>();
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t=sin.nextInt();
		while(t-->0){
			step=0;
			path.clear();
			m=sin.nextInt();
			n=sin.nextInt();
			sr=sin.nextInt();
			sc=sin.nextInt();
			er=sin.nextInt();
			ec=sin.nextInt();
			maze=new int[m+2][n+2];
			visited=new int[m+2][n+2];
			for(int i=0;i<=m+1;i++){
				for(int j=0;j<=n+1;j++){
					maze[i][j]=1;//1��ʾǽ
					visited[i][j]=0;//unvisited
				}
			}
			for(int i=1;i<=m;i++){
				String s=sin.next();
				for(int j=1;j<=n;j++){
					maze[i][j]=Integer.parseInt(s.substring(j-1, j));
				}
			}
			//print
			for(int i=0;i<=m+1;i++){
				for(int j=0;j<=n+1;j++){
					System.out.print(maze[i][j]+" ");
				}
				System.out.println();
			}
			//solve
			visited[er][ec]=1;
			if(maze[sr][sc]==1 || maze[er][ec]==1 || !solve(er,ec)){
				System.out.println("No Path.");
			}else{
				System.out.println(step);
			}
		}
	}

	
	public static boolean solve(int x,int y) {
		System.out.println("solve:"+x+" "+y+" "+"step="+step);
		if(x==sr && y==sc){
			return true;
		}
		visited[x][y]=1;
		step++;
		//ע�⣺����if-else�����жϣ�����solve�����������ж�֮һ
		if(visited[x-1][y]==0 && maze[x-1][y]==0 && x-1>=1 && solve(x-1,y)  ){//up
			return true;
		}
		if(visited[x+1][y]==0 && maze[x+1][y]==0 && x+1<=m && solve(x+1,y)  ){//down
			return true;
		}
		if(visited[x][y-1]==0 && maze[x][y-1]==0 && y-1>=1 && solve(x,y-1)  ){//left
			return true;
		}
		if(visited[x][y+1]==0 && maze[x][y+1]==0 && y+1<=n && solve(x,y+1)  ){//right
			return true;
		}
		return false;
	}
	
}









