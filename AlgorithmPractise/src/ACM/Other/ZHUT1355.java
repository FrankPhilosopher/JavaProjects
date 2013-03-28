package ACM.Other;

/**
 * 01背包问题:擎天柱
 * 恰好满的背包问题，注意初始化方式
 * 题目链接：http://acm.zjut.edu.cn/ShowProblem.aspx?ShowID=1355
 */
import java.util.Scanner;

public class ZHUT1355 {

	public static int m;
	public static int n;
	public static int[] num;//num[k]表示总价格是k时的最少数目
	public static int[] value;//每个玩具的价格，所有玩具的“weight”都是1
	public static int maxValue = 10010;

	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		while(sin.hasNext()){
			n=sin.nextInt();
			value=new int[n+1];
			for(int i=1;i<=n;i++){
				value[i]=sin.nextInt();
			}
			m=sin.nextInt();
			num=new int[m+1];
			for(int i=0;i<=m;i++){
				num[i]=maxValue;
			}
			num[0]=0;//恰好满的背包问题，初始化
			for(int i=1;i<=n;i++){
				for(int j=m;j>=value[i];j--){
					if(num[j]>num[j-value[i]]+1){//取更小的
						num[j]=num[j-value[i]]+1;
					}
				}
			}
			if(num[m]<maxValue){
				System.out.println(num[m]);
			}else{
				System.out.println("-1");
			}
		}
	}
}
