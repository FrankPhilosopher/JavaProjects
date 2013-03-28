/**
* @Author��������  
* @CreateTime��2011-5-27 ����10:34:09
* @Description��AC
*/

package ACM.CSU;

import java.util.Scanner;

public class CSU1030 {

	public static final int MAXVALUE=1299719;
	public static int[] primes=new int[MAXVALUE];
	public static boolean[] p=new boolean[MAXVALUE];
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t,key,mid;
		t=sin.nextInt();
		getPrimes();
		while(t-->0){
			key=sin.nextInt();
			if(p[key]==true){//key������
				System.out.println("0");
			}else{
				mid=binarySearch(key);
				if(key>primes[mid]){
					System.out.println(primes[mid+1]-primes[mid]);
				}else{
					System.out.println(primes[mid]-primes[mid-1]);
				}
			}
		}		
	}
	
	//���ֲ��ң������Ǹ�����������primes�нӽ�keyֵ���Ǹ������±�
	public static int binarySearch(int key){
		int left=0,right=100000,mid=0;
		while(left<=right){
			mid=(left+right)/2;
			if(primes[mid]>key){
				right=mid-1;
			}else{
				left=mid+1;
			}
		}
		return mid;
	}
	
	//�õ����е������������浽����primes��
	public static void getPrimes(){
		int i,j,k=0;
		for(i=0;i<MAXVALUE;i++){//p[k]=true˵��k������
			p[i]=true;
		}
		p[0]=false;
		p[1]=false;//1��������
		for(i=2;i<MAXVALUE;i++){
			if(p[i]){
				primes[k++]=i;//primes�������е�����
				for(j=2*i;j<MAXVALUE;j+=i){//���ѭ����Ҫ��if�����������ִ�еģ�
					p[j]=false;//һ�������ı����Ͳ���������
				}
			}
		}
	}
}
