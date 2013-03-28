/**
* @Author：胡家威  
* @CreateTime：2011-5-8 下午12:53:04
* @Description：
*/

package ACM.CSU;

import java.util.ArrayList;
import java.util.Scanner;

public class CSU1050 {
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		while(sin.hasNext()){
			int minlen=110,strssize,t,i,j,k,l;
			t=sin.nextInt();
			ArrayList<String> strs = new ArrayList<String>();
			String res,revstr,minstr = null;//minstr可能有多个！
			char[] chars = new char[110];//要初始化
			boolean find = false;
			for(i=0;i<t;i++){
				String in = sin.next();
				strs.add(in);
				if(in.length()<minlen){
					minlen=in.length();
					minstr=in;//用minstr保存最短的字符串
					strs.remove(in);//删除掉那个最短的字符串
				}
			}
//			System.out.println(minstr);
//			System.out.println(minlen+"\t"+t);
			strssize=strs.size();
//			for(i=0;i<strslen;i++){
//				System.out.println(strs.get(i));
//			}
			for(l=minlen;l>0;l--){
				for(j=0;j<=minlen-l;j++){
					chars=new char[110];
					res=minstr.substring(j,j+l);
					for(k=0;k<l;k++){
						chars[k]=res.charAt(l-1-k);
					}					
//					revstr=String.valueOf(chars);//这一种方法不行！
					revstr=String.copyValueOf(chars,0,l);//这个方法很重要啊！
//					System.out.println(res+"\t"+revstr);
					for(i=0;i<strssize;i++){
						if(strs.get(i).indexOf(res)==-1 && strs.get(i).indexOf(revstr)==-1){
							break;
						}
					}
					if(i==strssize){
						find=true;
						System.out.println(l);
						break;
					}
				}//m
				if(find){
					break;
				}
			}//l
		}//sin
	}
}
