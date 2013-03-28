package ACM.HDU;

/**
 * ��ȫ��������
 * ��������
 * ��Ŀ���ӣ�http://acm.hdu.edu.cn/showproblem.php?pid=1248
 * �����
 */
import java.util.Scanner;

public class HDU1248 {

	public static int sum;
	public static int n = 3;
	public static int[] cost;// cost[k]��ʾ����kԪ�õ�������ֵ
	public static int[] value = { 0, 150, 200, 350 };// ��Ʒ�ļ�ֵ����ʵ��Ʒ�ġ�cost��Ҳ��������
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
			cost[0] = 0;// ����0Ԫ��Ȼ�õ�������ֵ��0����ʼ����Ŀ�����������ѭ����һ����ʼ̬
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
