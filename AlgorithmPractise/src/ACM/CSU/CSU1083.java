/**
* @Author：胡家威  
* @CreateTime：2011-7-22 下午11:51:11
* @Description：
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1083 {

	public static int m;
	public static int n;
	public static String[] webs;
	public static String[] sites;
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		while(sin.hasNext()){
			m=sin.nextInt();
			n=sin.nextInt();
			sites=new String[n];
			webs=new String[m];
			for(int i=0;i<m;i++){
				webs[i]=sin.next();
			}
			for(int i=0;i<n;i++){
				sites[i]=sin.next();
			}
			for(int i=0;i<n;i++){
				String site=sites[i]+".";//很重要这里
				int index=0;
				for(int j=0;j<m;j++){
					if((index=webs[j].indexOf(site))!=-1){
						if(index==0 || webs[j].charAt(index-1)=='.' || webs[j].charAt(index-1)=='/' 
							|| webs[j].charAt(index-1)=='\\'){//注意条件，尤其是最后一个条件，防止转义
							StringBuffer sb=new StringBuffer();
							sb.append("http://www.");
							sb.append(site);
							int nextIndex=webs[j].indexOf(".", index);
							sb.append(webs[j].substring(nextIndex+1));
							for(int k=nextIndex;k<sb.length();k++){
								if(sb.charAt(k)=='\\'){
									sb.setCharAt(k, '/');
								}
							}
							System.out.println(sb.toString());
						}
					}
				}
			}
			System.out.println();
		}
	}
}
