package ACM.Other;

/**
 * 01��������:������
 * ǡ�����ı������⣬ע���ʼ����ʽ
 * ��Ŀ���ӣ�http://acm.zjut.edu.cn/ShowProblem.aspx?ShowID=1355
 */
import java.util.Scanner;

public class ZHUT1355 {

	public static int m;
	public static int n;
	public static int[] num;//num[k]��ʾ�ܼ۸���kʱ��������Ŀ
	public static int[] value;//ÿ����ߵļ۸�������ߵġ�weight������1
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
			num[0]=0;//ǡ�����ı������⣬��ʼ��
			for(int i=1;i<=n;i++){
				for(int j=m;j>=value[i];j--){
					if(num[j]>num[j-value[i]]+1){//ȡ��С��
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
