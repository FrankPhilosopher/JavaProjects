/**
 * @Author£ººú¼ÒÍþ  
 */
package ACM.HDU;

import java.math.BigInteger;
import java.util.Scanner;
public class HDU1002 {
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t;
		BigInteger a,b;
		t=sin.nextInt();
		for(int i=1;i<=t;i++){
			if(i!=1){
				System.out.println();
			}
			a=sin.nextBigInteger();
			b=sin.nextBigInteger();
			System.out.println("Case "+i+":");
			System.out.println(a+" + "+b+" = "+a.add(b));
		}
	}
}
