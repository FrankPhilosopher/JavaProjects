/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-4-28 ÏÂÎç05:00:00
* @Description£º
*/

package ACM.NY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
/**
 * 
 */
public class CopyOfNY125 {
	
	public static void main(String[] arg) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sin = null;
		sin = new Scanner(br.readLine());
		int t = sin.nextInt();
		int i,j,layer,dif,stay,time,l;
		String str;
		for(i=0;i<t;i++){
			layer=0;
			dif=1;
			time=0;
			l=sin.nextInt();
			for(j=0;j<l;j++){
				str=sin.next();
				System.out.println(str);
				if(str=="IN"){
					layer++;
					dif*=20;
				}else if(str=="OUT"){
					layer--;
					dif/=20;
				}else if(str=="STAY"){
					stay=sin.nextInt();
					time+=stay/dif;
				}
			}
			System.out.printf("%.0f\n",time*60);
		}
	}
}
