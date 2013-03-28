/**
* @Author��������  
* @CreateTime��2011-5-24 ����08:42:20
* @Description��
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
						map[route[i]][route[i-1]]=1;//������ͨ��ϵ
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
	 *����������������ж��Ƿ���ڴ�start��end��·�� 
	 */
	public static boolean dfs(int start,int end){
		visit[start]=1;
		if(start==end){
			return true;
		}
		for(int i=0;i<100;i++){
			if(map[start][i]==1 && visit[i]==0){ //���start��i֮����·������i��û�б�����
				if(dfs(i,end))      //��ô�Ϳ�����iΪ��㣬�ݹ������Ƿ���ڴ�i��end��·��
					return true;
			}
		}
		return false;
	}
}
