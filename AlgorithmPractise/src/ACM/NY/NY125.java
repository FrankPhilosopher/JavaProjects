/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-4-28 ÏÂÎç05:00:00
* @Description£º
*/
package ACM.NY;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
public class NY125 {
	
	public static void main(String[] arg) throws Exception{
		Scanner sin = new Scanner(System.in);
		int t = sin.nextInt();
		int i,j,layer,time,stay,dif,l;
		String str;
		for(i=0;i<t;i++){			
			layer=0;
			time=0;
			dif=1;
			l=sin.nextInt();
			Scanner cin = new Scanner(System.in);
			str=cin.next();
			for(j=0;j<l;j++){
//				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//				Scanner cin = new Scanner(br.readLine());
				
				if(str=="IN"){
					layer++;
					dif*=20;
					str=cin.nextLine();
				}else if(str=="OUT"){
					layer--;
					dif/=20;
					str=cin.nextLine();
				}else{
					stay=cin.nextInt();
					System.out.println(stay);
					time+=stay*60/dif;
				}
			}
			System.out.println(time);
		}
	}
}
