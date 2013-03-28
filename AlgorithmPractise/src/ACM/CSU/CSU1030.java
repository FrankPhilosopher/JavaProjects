/**
* @Author：胡家威  
* @CreateTime：2011-5-27 下午10:34:09
* @Description：AC
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
			if(p[key]==true){//key是素数
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
	
	//二分查找，返回那个在素数数组primes中接近key值的那个数的下标
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
	
	//得到所有的素数，并保存到数组primes中
	public static void getPrimes(){
		int i,j,k=0;
		for(i=0;i<MAXVALUE;i++){//p[k]=true说明k是素数
			p[i]=true;
		}
		p[0]=false;
		p[1]=false;//1不是素数
		for(i=2;i<MAXVALUE;i++){
			if(p[i]){
				primes[k++]=i;//primes保存所有的素数
				for(j=2*i;j<MAXVALUE;j+=i){//这个循环是要在if成立的情况下执行的！
					p[j]=false;//一个素数的倍数就不是素数了
				}
			}
		}
	}
}
