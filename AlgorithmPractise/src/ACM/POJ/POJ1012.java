/**
 * @Author：胡家威  
 * @CreateTime：2011-4-21 下午08:54:04
 * @Description： 第二版，似乎有错
 */
package ACM.POJ;

import java.util.Scanner;

public class POJ1012 {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		while (scn.hasNext()) {
			int k = scn.nextInt();
			if (k == 0) {
				return;
			} else {
				int m; // 要求的m
				int t = 0;
				for (t = 0;;t++) {
					boolean gett = false;
//					for (m = 2;; m++) {
					for (m=(2*t+1)*k;m<(2*t+2)*k; m++) {  // 试探法求m
						if(m == (2*t+2)*k ){
							break;
						}
						int p[] = new int[2 * k]; // 所有的人
						for (int i = 0; i < k; i++) {
							p[i] = 1; // 1表示好人
							p[k + i] = -1; // -1表示坏人 0表示死人
						}
						int count = 0; // 用于计数的变量
						boolean getm = false; // 得到了m的值
						for (int j = 0;; j++) {
							if (j == 2 * k)		//注意这里的条件是j等于2*k
								j = 0; // 使其循环
							if (p[j] == 0)
								continue; // 死人跳过
							else {
								count++; //先让count加1，因为这个人肯定要加上去，然后在判断-- 如果没有数到m，那么继续j循环
								if (count == m) { // 如果数到第m个人
									if (p[j] == 1) { // 如果是好人，先判断坏人是否全死了,如果没有全死，那么这个m不对
										boolean alldie = true; // 默认坏人全死了

										for (int n = k; n < 2 * k; n++) { // k 到 2*k-1都是坏人
											if (p[n] != 0) { // 有一个没有死就设flag为false，并退出for循环
												alldie = false;
												break;
											}
										}

										if (alldie) { // 如果坏人全死，输出m，并跳出j的循环
											getm = true;
										}
										break;
									} else { // 如果第m个人是坏人
										p[j] = 0;
										count = 0; // count重置为0，继续数m个
										continue; // 这里要回到 j的for循环上去
									}
								}
							}
						}// j

						if (getm) {
							gett = true;
							System.out.println(m);
//							break; // 退出 m的循环，进入while的大循环
							break; // 退出 m的循环，进入t的循环
						} 
					}// m
					if (gett) {
						break; // 退出 t的循环，进入while的循环
					} 
				}//t
			}// else
		}// while
	}
}
