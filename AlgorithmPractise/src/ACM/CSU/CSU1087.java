package ACM.CSU;
//AC
import java.util.Scanner;

public class CSU1087 {

	public static int n;
	public static int ans;
	public static int cnt;
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		while(sin.hasNext()){
			n=sin.nextInt();
			ans=fab(n);
			cnt=calCnt(n);
			System.out.println(ans+" "+cnt);
		}
	}
	
	public static int fab(int m){
		if(m==1 || m==2){
			return 1;
		}else{
			return fab(m-1)+fab(m-2);
		}
	}
	
	public static int calCnt(int m){
		if(m==1 || m==2){
			return 1;
		}else{
			return calCnt(m-1)+calCnt(m-2)+1;
		}
	}
}
