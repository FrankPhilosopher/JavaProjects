package ACM.CSU;
/**
 * 最短路径算法
 * 类似灌水法
 * RE
 */
import java.util.Scanner;
import java.util.Stack;

public class CSU1080 {

	public static int[][] maze;
	public static int[][] dis;
	public static int[][] mark;
	public static int m,n,sr,sc,er,ec;
	public static int maxDis=100;
	public static Stack<Position> willVisit=new Stack<Position>();
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t=sin.nextInt();
		for(int k=1;k<=t;k++){
			willVisit.clear();
			
			m=sin.nextInt();
			n=sin.nextInt();
			sr=sin.nextInt();
			sc=sin.nextInt();
			er=sin.nextInt();
			ec=sin.nextInt();
			maze=new int[m+2][n+2];
			dis=new int[m+2][n+2];
			mark=new int[m+2][n+2];
			for(int i=0;i<=m+1;i++){
				for(int j=0;j<=n+1;j++){
					maze[i][j]=1;//1表示墙
				}
			}
			for(int i=1;i<=m;i++){
				String s=sin.next();
				for(int j=1;j<=n;j++){
					maze[i][j]=Integer.parseInt(s.substring(j-1, j));
				}
			}
			//print
//			for(int i=0;i<=m+1;i++){
//				for(int j=0;j<=n+1;j++){
//					System.out.print(maze[i][j]+" ");
//				}
//				System.out.println();
//			}
			//solve
			if(maze[sr][sc]==1 || maze[er][ec]==1){
				System.out.println("No Path.");
			}else{
				for(int i=0;i<=m+1;i++){
					for(int j=0;j<=n+1;j++){
						dis[i][j]=maxDis;
						mark[i][j]=0;
					}
				}
				dis[er][ec]=0;
				mark[er][ec]=1;
				Position end=new Position(er,ec,0);
				getDistance(end);//willVisit.add(end);不用写，第一次就是访问终点
				if(dis[sr][sc]==-1 || dis[sr][sc]==maxDis){
					System.out.println("No Path.");
				}else{
					System.out.println(dis[sr][sc]);
				}
			}
		}
	}

	public static void getDistance(Position pos) {
		int x=pos.x;
		int y=pos.y;
		int curDis=pos.dist;
		mark[x][y]=1;
		if(pos.x==sr && pos.y==sc){
			return;
		}
		
		if(x-1>=1 && mark[x-1][y]==0){
			if(maze[x-1][y]==1){// 1=wall
				dis[x-1][y]=-1;
			}else if(dis[x-1][y]>curDis+1){
				dis[x-1][y]=curDis+1;
				willVisit.add(new Position(x-1,y,curDis+1));
			}
		}
		if(y-1>=1 && mark[x][y-1]==0){
			if(maze[x][y-1]==1){
				dis[x][y-1]=-1;
			}else if(dis[x][y-1]>curDis+1){
				dis[x][y-1]=curDis+1;
				willVisit.add(new Position(x,y-1,curDis+1));
			}
		}
		if(y+1<=n && mark[x][y+1]==0){
			if(maze[x][y+1]==1){
				dis[x][y+1]=-1;
			}else if(dis[x][y+1]>curDis+1){
				dis[x][y+1]=curDis+1;
				willVisit.add(new Position(x,y+1,curDis+1));
			}
		}
		if(x+1<=m && mark[x+1][y]==0){
			if(maze[x+1][y]==1){
				dis[x+1][y]=-1;
			}else if(dis[x+1][y]>curDis+1){
				dis[x+1][y]=curDis+1;
				willVisit.add(new Position(x+1,y,curDis+1));
			}
		}
		
		while(!willVisit.isEmpty()){
			Position p=willVisit.pop();
			getDistance(p);
		}
	}
}

class Position{
	int x;
	int y;
	int dist=100;
	
	public Position(){
		
	}
	public Position(int x,int y,int dist){
		this.x=x;
		this.y=y;
		this.dist=dist;
	}
}