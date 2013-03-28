/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-6-1 ÏÂÎç10:28:32
* @Description£ºAC
*/

package ACM.CSU;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CSU1031 {

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t;
		t = sin.nextInt();
		while (t-- > 0) {
			String str = sin.next();
			int indexe=-1;
			for(int i=0,len=str.length();i<len;i++){
				if(str.charAt(i)=='e' || str.charAt(i)=='E'){
					indexe = i;
					break;
				}
			}
			boolean r1, r2;
			if (indexe == -1) {
//				System.out.println(str);
				r1 = validcof(str);
				if(r1){
					System.out.println("YES");
				}else{
					System.out.println("NO");
				}
			} else if (indexe == str.length()){
				System.out.println("NO");
			} else {
				String cof = str.substring(0, indexe);
//				System.out.println(cof);
				String exp = str.substring(indexe, str.length());
//				System.out.println(exp);
				r1 = validcof(cof);
				r2 = validexp(exp);
//				System.out.println(r1+"\t"+r2);
				if (r1 && r2) {
					System.out.println("YES");
				} else {
					System.out.println("NO");
				}
			}
		}
	}

	private static boolean validcof(String str) {
		String regEx1 = "^[+-]?[0-9]+(\\.)?([0-9]*)$";
		String regEx2 = "^[+-]?[0-9]*(\\.)?([0-9]+)$";
		boolean r1 = Pattern.compile(regEx1).matcher(str).find();
		boolean r2 = Pattern.compile(regEx2).matcher(str).find();
		boolean result = r1 || r2;
//		System.out.println(r1+"\t"+r2+"\t"+result);
		return result;
	}

	private static boolean validexp(String str) {
		String regEx = "^[Ee]?[+-]?([0-9]+)$";
		boolean result = Pattern.compile(regEx).matcher(str).find();
//		System.out.println(result);
		return result;
	}
}

