/**
* @Author��������  
* @CreateTime��2011-5-4 ����09:38:27
* @Description��
*/

package ACM.NY;

import java.util.Scanner;

/**
 * 
 */
public class NY005 {
	
	public static void main(String arg[]){
		Scanner sin = new Scanner(System.in);
		int t=sin.nextInt();
		int count=0;
		for(int i=0;i<t;i++){
			count=0;
			String a=sin.next();
			String b=sin.next();
			int index=0;
			index=b.indexOf(a, index);
			while(index!=-1){
				count++;
				index++;
				index=b.indexOf(a, index);
			}
			System.out.printf("%d\n",count);
		}		
	}
}
