/**
* @Author：胡家威  
* @CreateTime：2011-5-28 下午03:04:28
* @Description：AC
*/

package ACM.CSU;

import java.util.Scanner;
import java.util.Stack;

public class CSU1019 {
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t=sin.nextInt();
		Stack<Character> stack=new Stack<Character>();
//		Stack<char> stack=new Stack<char>();//这样会报错的！
		String s;
		while(t-->0){
			stack.clear();
			s=sin.next();
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)=='#'){
					if(!stack.isEmpty()){
						stack.pop();
					}
				}else if(s.charAt(i)=='@'){
					if(!stack.isEmpty()){
						stack.clear();
					}
				}else{
					stack.push(s.charAt(i));
				}
			}
			for(int i=0;i<stack.size();i++){
				System.out.printf("%c",stack.get(i));
			}
			System.out.println();
		}
	}
}
