/**
* @Author：胡家威  
* @CreateTime：2011-6-6 上午12:03:52
* @Description：TLE
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1077 {

	public final static double p=3.1415926535897932384626433832795;//不对！
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		System.out.printf("%.20f\n",p);
//		System.out.println(p);
		double angle,area;
		int n;
		while(sin.hasNext()){
			n=sin.nextInt();
			if(n<3){
				break;
			}
			angle=p/n;
//			System.out.println(angle);
			area=0.25*n/Math.tan(angle);
//			System.out.println(area);
			System.out.printf("%.2f\n",area);
		}
	}
}
