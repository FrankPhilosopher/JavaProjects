/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-5-24 ÉÏÎç09:05:24
* @Description£º
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1003 {

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t;
		t=sin.nextInt();
		int k,sum,d,level;
		char c;
		String str;
		while(t-->0){
			sum=0;
			k=10;
			d=sin.nextInt();
			str=sin.next();//sin.nextLine()»á´í£¡
			for(int i=0;i<d;i++){
				c=str.charAt(i);
				if(c=='0'){
					k=10;
					continue;
				}else{
					sum+=k;
					k+=10;
					if(k==60){
						k=10;
					}
				}
			}
			level=(sum+50)/100;
			if(level>8)	level=8;
			System.out.println(level);
		}
	}

}
