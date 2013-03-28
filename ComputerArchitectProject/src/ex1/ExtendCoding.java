package ex1;

import java.util.Arrays;

/**
 * 扩展编码
 * 
 * @author yinger
 * 
 */
public class ExtendCoding implements ICoding {

	public Node[] nodes;//输入的数据节点
	public int[] codeLength;//编码长度
	public double wpl;//wpl

	public ExtendCoding(Node[] nodes, int[] codeLength) {
		this.nodes = nodes;
		this.codeLength = codeLength;
	}

	public void doCoding() {
		initCode();
		System.out.println("采用" + listCodeLength() + "扩展编码方式编码：");
		makeCode();
		displayCode();
		calWPL();
	}

	//初始化code值
	private void initCode() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].code = "";
		}
	}

//生成code
	private void makeCode() {
		Arrays.sort(nodes);//得到的nodes数组按照node的概率降序排列
		int type = codeLength.length;//长度的种类
		int currentLength;//当前编码长度
		int number;//可以使用当前编码长度的最多数目
		int m = 1, n = 1;//number = 2^(m) - n;
		int currentValue = 0;//当前使用到的数字值
		int currentIndex = 0;//nodes的下标，给它的code赋值
		int maxIndex;//该次长度最多可到的数组下标
		for (int i = 0; i < type; i++) {
			n = 1;//重新赋上初值
			currentLength = codeLength[i];
			if (i == 0) {//第一种长度
				m = currentLength;//特例
			}
			if (i == type - 1) {//最后一种长度，数量不用减一
				n = 0;
			}
			number = (int) (Math.pow(2, m) - n);//不会超过number个数使用这个长度进行编码
			maxIndex = Math.min(currentIndex + number, nodes.length);//maxIndex取两者中的较小者
			for (int j = currentIndex; j < maxIndex; j++) {
				String code = Integer.toBinaryString(currentValue);
				nodes[j].code = formateCode(code, currentLength);
				currentValue++;//当前值加一
				currentIndex++;
			}
			//如果这个长度的编码使用完了，那么就要使用下一种长度的编码，在此之前要改变currentValue的值
			if (i < type - 1) {
//				currentValue++;//首先加1，变成该长度的最大值//这里不需要再加一了，上面加了
				m = codeLength[i + 1] - codeLength[i];//两个长度之间的差距
				currentValue = currentValue << m;//向左移动合适的位数
			}
		}
	}

	//根据指定的长度对code进行格式化
	private String formateCode(String code, int currentLength) {
		while (code.length() < currentLength) {
			code = "0" + code;
		}
		return code;
	}

	//计算wpl
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * (nodes[i].code.length());
		}
		System.out.println("WPL=" + wpl);
	}

	//显示编码的长度
	private String listCodeLength() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < codeLength.length; i++) {
			if (i == codeLength.length - 1) {
				sb.append(codeLength[i]);
			} else {
				sb.append(codeLength[i] + "-");
			}
		}
		return sb.toString();
	}

	//显示最终生成的哈夫曼编码
	private void displayCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

}
