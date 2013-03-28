/**
 * @Author��������  
 * @CreateTime��2011-5-19 ����08:28:37
 * @Description��
 */

package ACM.POJ;

import java.util.Scanner;

/**
 * 
 */
public class POJ3468 {
	public static void main(String[] args) {
		SegmentTree segmentTree = new SegmentTree();
	}
}

class SegmentTree {
	
	/**
	 * ���ֽⷨ��û��Ϊ�߶�����ÿ���߶ι���һ���࣬���ǽ����Ǹ��ֲ���������һ����Ӧ�ĺܴ��������
	 * ���������ǵķ������ǿ��Ǹ��߶ε����кţ���ţ����ڱ�ʾ���Ǹ��߶����ĵڼ����߶Σ�
	 * _left[i]��ʾ��i���߶ε���˵�ֵ��_sum[i]��ʾ��i���߶ε�����Ԫ�ص��ܺ�
	 */
	public static final int N = 100005;  //final������������������
	public int tot;  //����������ʾ�߶����ĵڼ����߶�
	public int[] a = new int[N];  //�������е�����
	public int[] _left = new int[3 * N];//���������Ҷ˵�ֵ
	public int[] _right = new int[3 * N];//����������˵�ֵ
	public long[] _sum = new long[3 * N];//���������ܺ�
	public long[] _d = new long[3 * N];//����������ֵ��Ϣ
	
	/**
	 * �߶������췽�������ֻ����Ĺ��췽��
	 */
	public SegmentTree() {
		tot = 0;
		int n, m, left, right, value;
		String str;
		Scanner input = new Scanner(System.in);
		n = input.nextInt();
		m = input.nextInt();
		for (int i = 1; i <= n; i++) //��1��ʼ�ģ�����ʹ��
			a[i] = input.nextInt();  //��a���鸳ֵ�������е����ֱ�������
		
		creatTree(1, 1, n);//���췽���ĵ�һ������һ����1���ӵ�һ���߶ο�ʼ���죬
					       //�ڶ����͵����������ķֱ����������Ҷ˵�,Ҳ�����߶������ܵĿ��
		for (int i = 0; i < m; i++) {
			str = input.next();//scanner�ָ���������õ��ǿո�
			if (str.charAt(0) == 'Q') {
				left = input.nextInt();
				right = input.nextInt();
				System.out.println(query(1, left, right, 0));
			} else {
				left = input.nextInt();
				right = input.nextInt();
				value = input.nextInt();
				insert(1, left, right, value);
			}
		}
	}
	/**
	 * �߶�������ķ���
	 * @param now  ��ǰ�߶ε����
	 * @param left	��˵�
	 * @param right	�Ҷ˵�
	 * @return	��������߶ε��ܺ�
	 */
	public long creatTree(int now, int left, int right) {
		if (now > tot)
			tot = now;//tot��ֵ����������仯�ģ�tot���ռ�¼���߶����Ľڵ�����
		_left[now] = left;
		_right[now] = right;
		long lSum = 0, rSum = 0;
		if (left < right) {  //left < right��ʾ���߶������в���Ҷ�ӽڵ㣬��ô��Ҫ�������¹����߶�
			lSum = creatTree(2 * now, left, (left + right) / 2); //���¹���ʱ����ߵ��߶ε�����ǵ�ǰ�ڵ���ŵ�2�����ұߵĻ�Ҫ��1
			rSum = creatTree(2 * now + 1, (left + right) / 2 + 1, right);//�������Ҷ˵�ֵҲҪ���ŷ�����Ӧ�ı仯
			_sum[now] = lSum + rSum; //һ���ǵ�Ҫ��_sum��ֵ�����������Ǿ���⣬�����ڵ㱣�����ֵ�Ǵ�������Щֵ�����ڽ������
		} else
			_sum[now] = a[left];//�ݹ鵽��Ҷ�ӽڵ�ʱ�Ǹ��߶ε��ܺ;���a�����б������ֵ���±���left��,Ҷ�ӽڵ�ʱ����ԭʼ���ݵģ�
		return _sum[now];
	}
	
	/**
	 * ������������Ǹ�һ���̶��������ÿһ��Ԫ������һ����
	 * @param now	��1��ʼ��ÿ��ʹ��ʱ��ʾ��ǰ���߶α��
	 * @param left	��˵�
	 * @param right	�Ҷ˵�
	 * @param value	Ҫ���ӵ�ֵ
	 */
	public void insert(int now, int left, int right, int value) {
		if (now > tot)	//now��ֵ���ܳ���tot
			return;
		if (left <= _left[now] && right >= _right[now]) {
			_d[now] += value;
			return;
		}
		long lSum = 0, rSum = 0;
		if (left <= (_left[now] + _right[now]) / 2)
			insert(2 * now, left, right, value);
		if (right > (_left[now] + _right[now]) / 2)
			insert(2 * now + 1, left, right, value);
		if (2 * now <= tot)
			lSum = _sum[2 * now] + _d[2 * now]
					* (_right[2 * now] - _left[2 * now] + 1);
		if (2 * now + 1 <= tot)
			rSum = _sum[2 * now + 1] + _d[2 * now + 1]
					* (_right[2 * now + 1] - _left[2 * now + 1] + 1);
		_sum[now] = lSum + rSum;
	}

	public long query(int now, int left, int right, long d) {
		if (now > tot)
			return 0;
		if (left <= _left[now] && right >= _right[now])
			return _sum[now] + (_d[now] + d) * (_right[now] - _left[now] + 1);
		long lSum = 0, rSum = 0;
		if (left <= (_left[now] + _right[now]) / 2)
			lSum = query(2 * now, left, right, d + _d[now]);
		if (right > (_left[now] + _right[now]) / 2)
			rSum = query(2 * now + 1, left, right, d + _d[now]);
		return lSum + rSum;
	}

}
