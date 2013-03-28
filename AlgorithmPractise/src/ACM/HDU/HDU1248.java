package ACM.HDU;

/**
 * 完全背包问题
 * 寒冰王座
 * 题目链接：http://acm.hdu.edu.cn/showproblem.php?pid=1248
 * 结果：
 */
import java.util.Scanner;

public class HDU1248 {

	public static int sum;
	public static int n = 3;
	public static int[] cost;// cost[k]表示花费k元得到的最大价值
	public static int[] value = { 0, 150, 200, 350 };// 物品的价值，其实物品的“cost”也就是它了
	public static int maxValue = 10010;

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t = sin.nextInt();
		while (t-- > 0) {
			sum = sin.nextInt();
			cost = new int[sum + 1];
			for (int i = 0; i <= sum; i++) {
				cost[i] = 0;
			}
			cost[0] = 0;// 花费0元自然得到的最大价值是0，初始化的目的是让下面的循环有一个初始态
			for (int i = 1; i <= n; i++) {
				for (int j = value[i]; j <= sum; j++) {
					if (cost[j] < cost[j - value[i]] + value[i]) {
						cost[j] = cost[j - value[i]] + value[i];
					}
				}
			}
			System.out.println(sum - cost[sum]);
		}
	}

}
