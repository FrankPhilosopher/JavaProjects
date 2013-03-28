package ACM.POJ;
import java.util.Scanner;

public class POJ1061 {
	public static void main(String[] args) {
		int x,y,m,n,l;
		Scanner sin=new Scanner(System.in);
		x=sin.nextInt();
		y=sin.nextInt();
		m=sin.nextInt();
		n=sin.nextInt();
		l=sin.nextInt();
		int sx=x;
		int sy=y;
		int dv=Math.abs(m-n);
		int s1=Math.abs(y-x);
		int s2=l+s1;
		if(s1%dv!=0 && s2%dv!=0){
			System.out.println("Impossible");
		}else{
			for(int k=0;;k++){
				
			}
		}
	}
}
