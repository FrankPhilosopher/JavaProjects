/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-5-26 ÉÏÎç09:07:38
* @Description£º
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1029 {

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t;
		char tmp;
		String s,r;
		t=sin.nextInt();
		while(t-->0){
			s=sin.next();
			char[] chars=s.toCharArray();
			int len=chars.length;
			for(int i=0;i<len/2;i++){
				
				tmp=chars[i];
				chars[i]=chars[len-1-i];
				chars[len-1-i]=tmp;
			}
			r=String.valueOf(chars);
			if(r.compareTo(s)==0){
				System.out.println("YES");
			}else{
				System.out.println("NO");
			}
		}		
	}
}
