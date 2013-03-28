/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-5-24 ÉÏÎç10:52:53
* @Description£º
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1007 {

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t,px,py,ax,ay,bx,by;
		t=sin.nextInt();
		while(t-->0){
			px=sin.nextInt();
			py=sin.nextInt();
			ax=sin.nextInt();
			ay=sin.nextInt();
			bx=sin.nextInt();
			by=sin.nextInt();
			if(px<ax || px>bx || py<ay || py>by){
				System.out.println("Outside");
			}else if(px>ax && px<bx && py>ay && py<by){
				System.out.println("Inside");
			}else{
				System.out.println("On");
			}
		}
	}
}
