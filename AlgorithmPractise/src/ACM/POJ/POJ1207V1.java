/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-7-15 ÏÂÎç06:27:33
* @Description£ºWA
*/

package ACM.POJ;

import java.util.Scanner;

public class POJ1207V1 {

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int start,end,len,maxlen,temp;
		while(sin.hasNext()){
			maxlen=0;
			temp=0;
			start=sin.nextInt();
			end=sin.nextInt();
			if(start>end){
				temp=end;
				end=start;
				start=temp;
			}
			for(int i=start;i<=end;i++){
				len=calculateLen(i);
				if(len>maxlen){
					maxlen=len;
				}
			}
			if(temp==0){
				System.out.println(start+" "+end+" "+(maxlen));//()
			}else{
				System.out.println(end+" "+start+" "+(maxlen));//()
			}
		}
	}
	
	public static int calculateLen(int n){
		int len=1;
		while(n!=1){
			if(n%2==0){
				n=n/2;
			}else{
				n=3*n+1;
			}
			len++;
		}
		return len;
	}
}
