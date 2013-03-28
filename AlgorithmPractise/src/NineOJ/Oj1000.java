package NineOJ;

import java.util.Scanner;

//AC
public class Oj1000 {

	public static void main(String[] args) {
//		System.out.println(Integer.MAX_VALUE);//2147483647
		long a, b;
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			a = scanner.nextInt();
			b = scanner.nextInt();
			System.out.println(a + b);
		}
	}

}
