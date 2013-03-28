/**
* @Author：胡家威  
* @CreateTime：2011-4-21 下午08:52:57
* @Description：AC
*/

package ACM.POJ;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class POJ1002 {
	
	 public static void main (String[] args) {
			Scanner scanner = new Scanner(System.in);
			HashMap hs = new HashMap();
			int line = scanner.nextInt();
			boolean nodup = true;
			for (int i=0; i<line; i++){
				char[] chars = new char[8];
				chars[3] = '-';
				int k = 0; 
				String src = scanner.next();
				for (int j = 0; j < src.length(); j++){
					char c = src.charAt(j);
					if(c == '-'){
						continue;
					}else if(Character.isLetter(c)){
						if(c=='A' || c=='B' || c=='C'){
							c = '2';
						}else if(c=='D' || c=='E' || c=='F'){
							c = '3';
						}else if(c=='G' || c=='H' || c=='I'){
							c = '4';
						}else if(c=='J' || c=='K' || c=='L'){
							c = '5';
						}else if(c=='M' || c=='N' || c=='O'){
							c = '6';
						}else if(c=='P' || c=='R' || c=='S'){
							c = '7';
						}else if(c=='T' || c=='U' || c=='V'){
							c = '8';
						}else if(c=='W' || c=='X' || c=='Y'){
							c = '9';
						}
						if(k == 3){
							chars[++k] = c;
							k++;
						}else{
							chars[k] = c;
							k++;
						}
					}else if(Character.isDigit(c)){
						if(k == 3){
							chars[++k] = c;
							k++;
						}else{
							chars[k] = c;
							k++;
						}	
					}
				}
				String phone = String.valueOf(chars);		
				if(hs.containsKey(phone)){
					nodup = false;
					hs.put(phone,(Integer)hs.get(phone)+1);
				}else{
					hs.put(phone,1);
				}
			}

			if(nodup){
				System.out.println ("No duplicates.");
			}else{
				Set<String> phones = hs.keySet();  //这里 String 很重要！
				String[] str = phones.toArray(new String[phones.size()]);
				Arrays.sort(str);
				for(String s : str){
					int value = (Integer)hs.get(s);
					if(value > 1){
						System.out.println (s+" "+value);
					}
				}
			}
		}
}

