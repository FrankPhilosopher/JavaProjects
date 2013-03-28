/**
* @Author：胡家威  
* @CreateTime：2011-6-9 上午10:38:25
* @Description：TLE 超时了！
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1069 {

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int[] arr;
		int t,n,p,q,k,j;
		t=sin.nextInt();
		for(j=1;j<=t;j++){
			arr=new int[10000010];
			for(int i=0;i<10000010;i++){
				arr[i]=0;
			}
			n=sin.nextInt();
			p=sin.nextInt();
			q=sin.nextInt();
			for(int i=0;i<n;i++){
				k=sin.nextInt();
				arr[k]++;
			}
			for(int i=0;i<10000010;i++){
				if(arr[i]==q){
					System.out.println("Case "+j+":");
					System.out.println(i);
					break;
				}
			}
		}
	}

}
