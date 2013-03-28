/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-4-29 ÏÂÎç11:37:17
* @Description£º
*/

package ACM.POJ;

import java.util.Scanner;

public class POJ1850 {
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		String s = sin.next();
		if(isOrder(s)){
			long res = order(s);
			System.out.println(res);
		}else{
			System.out.println("0");
		}
	}

	public static boolean isOrder(String s) {
		boolean flag=true;
		for(int i=0;i<s.length()-1;i++){
			if(s.charAt(i)>s.charAt(i+1)){
				flag=false;
				break;
			}
		}
		return flag;
	}

	public static long order(String s) {
		long sum=0;
		int size=s.length();
		for(int i=1;i<size;i++){
			sum+=g(i);
		}
		
		for(int j=1;j<change(s.charAt(0));j++){
			sum+=f(j,size);
		}
		
		int len=0;
		int temp=change(s.charAt(0));
		for(int i=1;i<=size-1;i++){
			len=size-i;	
			for(int j=temp+1;j<change(s.charAt(i));j++){
				sum+=f(j,len);
			}
			temp=change(s.charAt(i));
		}
		return sum+1;
	}

	public static int change(char c){
		return c-'a'+1;      
	}
	
	public static long f(int i,int k){	
		long sum=0;
		if(k==1)	return 1;
		for(int j=i+1;j<=26;j++){
			sum+=f(j,k-1);
		}		
		return sum;
	}
	
	public static long g(int k){
		long sum=0;
		for(int i=1;i<=26;i++){
			sum+=f(i,k);
		}
		return sum;
	}
	

}
