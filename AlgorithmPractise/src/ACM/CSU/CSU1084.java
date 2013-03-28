package ACM.CSU;
//AC
import java.math.BigInteger;
import java.util.Scanner;

public class CSU1084 {

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		BigInteger a;
		BigInteger b;
		int t=sin.nextInt();
		while(t-->0){
			a=sin.nextBigInteger();
			b=sin.nextBigInteger();
			System.out.println(a.add(b));
		}		
	}
}
