package ACM.CSU;
//AC
import java.util.Scanner;

public class CSU1081 {

	public static int[] prime = new int[1001];

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int n, ans;
		getPrime();
		while (sin.hasNext()) {
			ans = 0;
			n = sin.nextInt();
			for (int i = 1; i <= n; i++) {
				int j = sin.nextInt();
				if (prime[j] == 1) {
					ans++;
				}
			}
			System.out.println(ans);
		}
	}

	public static void getPrime() {
		int i, j;
		boolean flag = true;
		for (i = 1; i < 1001; i ++) {
			if(i%2==0)
				prime[i] = 0;
			else 
				prime[i] = 1;
		}
		prime[0] = 0;
		prime[1] = 0;
		prime[2] = 1;
		for (i = 3; i < 1001; i+=2) {
			if (prime[i] != 0) {
				for (j = 2; j < i; j++) {
					if (i % j == 0) {
						flag = false;
						for (int k = i; k < 1001; k = k + i) {
							prime[k] = 0;
						}
						break;
					}
				}
				if (flag) {
					prime[i] = 1;
				}
			}

		}
//		for (i = 0; i < 1001; i++) {
//			System.out.println(i + " " + prime[i]);
//		}
	}
}
