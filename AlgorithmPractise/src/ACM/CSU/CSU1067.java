/**
 * @Author£ººú¼ÒÍþ  
 * @CreateTime£º2011-6-5 ÏÂÎç09:31:31
 * @Description£ºAC
 */

package ACM.CSU;

import java.util.Scanner;

public class CSU1067 {

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		char a = 'A';
		char b = 'B';
		int max = 0;
		while (sin.hasNext()) {
			String s = sin.next();
			if (a == s.charAt(0)) {
				max = calMax(s, a, b);
			} else {
				max = calMax(s, b, a);
			}
			System.out.println(max);
		}
	}
	
	public static int calMax(String s, char a, char b) {
		int max = 0, temp = 1, index;
		for (int i = 1, len = s.length(); i < len; ) {
			if (s.charAt(i) == a) {
				temp++;
				i++;
				if (temp > max) {
					max = temp;
				}
			} else {
				index = s.indexOf(a, i);
//				System.out.println(index+"\t"+len);
				if (index != -1) {
					temp = index - i ;
				} else {
					temp = len - i ;
				}
				if (temp > max) {
					max = temp;
				}
				i+=temp;
				temp=0;				
			}
		}
		return max;
	}

}
