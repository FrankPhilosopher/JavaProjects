package ACM.NY;

/**
 * 这个程序没有通过，超时了 
 */
import java.util.Arrays;
import java.util.Scanner;

public class CopyOfNY006 {
	public static void main(String[] args) {
		double[] arr ;
		Scanner scan = new Scanner(System.in);
		int len = scan.nextInt();
		int task = 0;
		double area = 0;
		for(int k=0;k<len;k++){
			int Num = scan.nextInt();
			arr=new double[Num];
			for (int i = 0; i < Num; i++) {
				double n = scan.nextDouble();
				arr[i] = n;
			}
			Arrays.sort(arr);
			double s = 0;
			for (int j = arr.length - 1; j >= 0; j--) {
				double r = arr[j];
				s = 2 * 2 * Math.sqrt(Math.pow(r, 2) - 1);
				area = area + s;
				task = task + 1;
				if (area >= 40) {
					System.out.println(task);
					break;
				}
			}
			task = 0;
			area = 0;
		}
	}
}
