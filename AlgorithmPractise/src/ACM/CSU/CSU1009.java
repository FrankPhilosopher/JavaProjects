/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-5-24 ÏÂÎç09:02:59
* @Description£ºAC
*/

package ACM.CSU;

import java.util.Arrays;
import java.util.Scanner;

public class CSU1009 {
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int m,n;
		m=sin.nextInt();
		n=sin.nextInt();		
		while(!(m==0 && n==0)){
			double a[][];
			double sum=1.0;
			double res=0.0;
			a=new double[n][m];
			for(int i=0;i<m;i++){
				for(int j=0;j<n;j++){
					a[j][i]=sin.nextDouble();
				}
			}
			for(int k=0;k<n;k++){
				Arrays.sort(a[k]);
			}
			for(int i=0;i<m;i++){
				sum=1.0;
				for(int j=0;j<n;j++){
					sum*=a[j][i];
				}
				res+=sum;
			}
			System.out.printf("%.4f\n",res);
			m=sin.nextInt();
			n=sin.nextInt();	
		}
		if(m==0 && n==0){
			return;
		}		
	}
}
