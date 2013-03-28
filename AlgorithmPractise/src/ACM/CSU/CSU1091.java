package ACM.CSU;
//AC
import java.util.ArrayList;
import java.util.Scanner;

public class CSU1091 {

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t=sin.nextInt();
		int ans;
		while(t-->0){
			String s=sin.next();
			ans=calSub(s);
			System.out.println(ans);
		}
	}

	public static int calSub(String s) {
		ArrayList<String> subs=new ArrayList<String>();
		int i,j,len=s.length();
		int res=0;
		for(i=1;i<=len;i++){
			for(j=0;j<=len-i;j++){
				String sub=s.substring(j, j+i);
				if(!subs.contains(sub)){
					subs.add(sub);
				}
			}
			res+=subs.size();
			subs.clear();
		}
		return res;
	}
}
