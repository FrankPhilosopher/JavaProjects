/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-7-15 ÏÂÎç06:27:33
* @Description£ºAC
*/

package ACM.POJ;

import java.util.Scanner;

public class POJ1207V2 {
	
	public static int[] lens=new int[10002];

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		calculateLen();
		int start,end,maxlen,temp;
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
				if(lens[i]>maxlen){
					maxlen=lens[i];
				}
			}
			if(temp==0){
				System.out.println(start+" "+end+" "+(maxlen+1));//()
			}else{
				System.out.println(end+" "+start+" "+(maxlen+1));//()
			}
		}
	}
	
	public static void calculateLen(){
		int i,n;
		for(i=1;i<10002;i++){
			lens[i]=0;
			n=i;
			while(n!=1){
				if(n%2==0){
					n=n/2;
				}else{
					n=3*n+1;
				}
				lens[i]++;
			}
		}
	}
}
