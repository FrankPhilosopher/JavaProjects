//AC 
// ≤…“©
package ACM.CSU;
import java.util.Scanner;

public class CSU1088 {

	public static int m,n;
	public static int cost[];
	public static int value[];
	public static int state[];
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		m=sin.nextInt();
		n=sin.nextInt();
		cost=new int[n];
		value=new int[n];
		state=new int[m+1];
		for(int i=0;i<n;i++){
			cost[i]=sin.nextInt();
			value[i]=sin.nextInt();
		}
		for(int j=0;j<=m;j++){
			state[j]=0;
		}
		for(int i=0;i<n;i++){
			for(int j=m;j>=cost[i];j--){
				if(state[j]<state[j-cost[i]]+value[i]){
					state[j]=state[j-cost[i]]+value[i];
				}
			}
		}
		System.out.println(state[m]);
	}
}
