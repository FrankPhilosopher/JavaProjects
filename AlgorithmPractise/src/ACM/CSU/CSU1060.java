/**
 * @Author：胡家威  
 * @CreateTime：2011-6-2 下午11:08:22
 * @Description：
 */

package ACM.CSU;

import java.util.Scanner;

public class CSU1060 {
	public static void main(String[] args) {
		System.out.println(sum(2004));
		Scanner sin=new Scanner(System.in);
		int s=sin.nextInt();
		int start,pal;
		for(start=s;;start++){
			pal=sum(start);
			if(pal!=start && pal>=s && sum(pal)==start){
				System.out.println(start+" "+pal);
				break;
			}
		}
	}
	
	public static int sum(int n){
		int sum=0,min=n;
		for(int i=1;i<n;i++){
			if(i>=min){
				break;
			}else if(n%i==0){
				sum+=i;
				min=n/i;
				sum+=min;
			}
		}	
		return sum-n;
	}
}

//暴力不行 ！ 超时
//public static int sum(int n){
//int sum=0;
//for(int i=1;i<n;i++){
//	if(n%i==0){
//		sum+=i;
//	}
//}	
//return sum;
//}

	//方法很好，但是 WA！
//	public static int sum(int n) {
//		int i, count, sum = 1,num=n;
//		boolean flag;
//		for (i = 2; i <= n; i++) {
////			System.out.println(i);
//			count = 0;
//			flag = false;
//			while(n%i==0){
//				flag = true;
//				n = n / i;
////				System.out.println(n);
//				count++;
//			}
//			if (flag) {
////				System.out.println(i + "\t" + count);
//				sum *= (Math.pow(i, count + 1) - 1) / (i - 1);
//			}
//		}
//		return sum-num;
//	}
//}
	

