package ACM.CSU;
//AC
import java.util.Scanner;

public class CSU1078 {
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int n=sin.nextInt();
		int sum=0;
		if(n==1){
			sum=1;
		}else if(n<1){
			for(int i=n;i<=1;i++){
				sum=sum+i;
			}
		}else if(n>1){
			for(int i=1;i<=n;i++){
				sum=sum+i;
			}
		}
		System.out.println(sum);
	}
}
