/**
* @Author：胡家威  
* @CreateTime：2011-5-24 上午08:42:20
* @Description：
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1004 {
	
	static int route[];
	static int visit[];
	static int map[][];
	public static void main(String[] args) {
		int t,i,n;
		int start,end;
		Scanner sin=new Scanner(System.in);
		t=sin.nextInt();
		while(t-->0){
			visit=new int[100];
			map=new int[100][100];
			start=sin.nextInt();
			end=sin.nextInt();
			n=sin.nextInt();
			while(n-->0){
				route=new int[sin.nextInt()];
				for(i=0;i<route.length;i++){
					route[i]=sin.nextInt();
					if(i>0){
						map[route[i]][route[i-1]]=1;//保存连通关系
						map[route[i-1]][route[i]]=1;
					}
				}
			}
			if (dfs(start, end) == true) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
			}
		}
	}
	/**
	 *利用这个搜索方法判断是否存在从start到end的路径 
	 */
	public static boolean dfs(int start,int end){
		visit[start]=1;
		if(start==end){
			return true;
		}
		for(int i=0;i<100;i++){
			if(map[start][i]==1 && visit[i]==0){ //如果start和i之间有路径而且i还没有被访问
				if(dfs(i,end))      //那么就可以以i为起点，递归搜索是否存在从i到end的路径
					return true;
			}
		}
		return false;
	}
}
