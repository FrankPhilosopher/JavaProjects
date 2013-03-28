/**
 * @Author：胡家威  
 * @CreateTime：2011-5-19 上午08:28:37
 * @Description：
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
	 * 这种解法并没有为线段树的每个线段构造一个类，而是将他们各种参数都放在一个相应的很大的数组中
	 * 而区别它们的方法就是靠那个线段的序列号（编号，用于表示它是该线段树的第几个线段）
	 * _left[i]表示第i个线段的左端点值，_sum[i]表示第i个线段的所有元素的总和
	 */
	public static final int N = 100005;  //final变量，常数，不会变的
	public int tot;  //它是用来表示线段树的第几个线段
	public int[] a = new int[N];  //保存所有的数字
	public int[] _left = new int[3 * N];//用来保存右端点值
	public int[] _right = new int[3 * N];//用来保存左端点值
	public long[] _sum = new long[3 * N];//用来保存总和
	public long[] _d = new long[3 * N];//用来保存增值信息
	
	/**
	 * 线段树构造方法，这个只是类的构造方法
	 */
	public SegmentTree() {
		tot = 0;
		int n, m, left, right, value;
		String str;
		Scanner input = new Scanner(System.in);
		n = input.nextInt();
		m = input.nextInt();
		for (int i = 1; i <= n; i++) //从1开始的，便于使用
			a[i] = input.nextInt();  //给a数组赋值，将所有的数字保存下来
		
		creatTree(1, 1, n);//构造方法的第一个参数一定是1，从第一个线段开始构造，
					       //第二个和第三个参数的分别是它的左右端点,也就是线段树的总的跨度
		for (int i = 0; i < m; i++) {
			str = input.next();//scanner分割输入的量用的是空格
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
	 * 线段树构造的方法
	 * @param now  当前线段的序号
	 * @param left	左端点
	 * @param right	右端点
	 * @return	返回这段线段的总和
	 */
	public long creatTree(int now, int left, int right) {
		if (now > tot)
			tot = now;//tot的值就是在这里变化的，tot最终记录了线段树的节点总数
		_left[now] = left;
		_right[now] = right;
		long lSum = 0, rSum = 0;
		if (left < right) {  //left < right表示该线段在树中不是叶子节点，那么就要继续往下构造线段
			lSum = creatTree(2 * now, left, (left + right) / 2); //向下构造时，左边的线段的序号是当前节点序号的2倍，右边的还要加1
			rSum = creatTree(2 * now + 1, (left + right) / 2 + 1, right);//而且左右端点值也要随着发生相应的变化
			_sum[now] = lSum + rSum; //一定记得要给_sum赋值，结合下面的那句理解，其他节点保存的数值是处理后的那些值，便于解决问题
		} else
			_sum[now] = a[left];//递归到了叶子节点时那个线段的总和就是a数组中保存的数值（下标是left）,叶子节点时保存原始数据的！
		return _sum[now];
	}
	
	/**
	 * 插入操作，就是给一个固定的区间的每一个元素增加一个数
	 * @param now	从1开始，每次使用时表示当前的线段编号
	 * @param left	左端点
	 * @param right	右端点
	 * @param value	要增加的值
	 */
	public void insert(int now, int left, int right, int value) {
		if (now > tot)	//now的值不能超过tot
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
