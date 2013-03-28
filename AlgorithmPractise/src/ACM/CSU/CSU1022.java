/**
* @Author��������  
* @CreateTime��2011-5-30 ����12:11:39
* @Description��
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1022 {
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t,n,i,j,res;
		int[][] data=new int[1000][1000];
		int[][] max=new int[1000][1000];
		t=sin.nextInt();
		while(t-->0){
			for(i=0;i<1000;i++){//��ʼ��
				for(j=0;j<1000;j++){
					data[i][j]=0;
					max[i][j]=0;
				}
			}
			n=sin.nextInt();
			for(i=1;i<=n;i++){//��ֵ
				for(j=1;j<=2*i-1;j++){
					data[i][j]=sin.nextInt();
				}
			}
			max[1][1]=data[1][1];
			for(i=2;i<=n;i++){
				for(j=1;j<=2*i-1;j++){
					max[i][j]=max[i-1][j-1];//�����������м�Ĺ�����
					if(j-2>=1){//��������߹�����
						max[i][j]=max[i][j]>max[i-1][j-2]?max[i][j]:max[i-1][j-2];
					}
					if(j<=2*i-3){//�������ұ߹�����
						max[i][j]=max[i][j]>max[i-1][j]?max[i][j]:max[i-1][j];
					}
					max[i][j]+=data[i][j];//max[i][j]��ʾ������i�е�j��Ԫ�صĺ����ֵ
				}
			}
			res=0;
			for(i=1;i<=2*n-1;i++){//�Ƚ����һ�У�ȷ�����ֵ
				if(max[n][i]>res){
					res=max[n][i];
				}
			}
			System.out.println(res);
		}
	}
}
