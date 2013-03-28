package ex1;

/**
 * 等长编码
 * 
 * @author yinger
 * 
 */
public class EqualLengthCoding implements ICoding {

	public Node[] nodes;//输入的数据节点
	public int codelength = 1;//等长编码的编码长度
	public double wpl;//wpl

	public EqualLengthCoding(Node[] nodes) {
		this.nodes = nodes;
	}

	public void doCoding() {
		initCode();
		System.out.println("采用等长编码方式编码：");
		calCodeLength();
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

	//对node进行编码
	private void makeCode() {
		String code = null;
		for (int i = 0; i < nodes.length; i++) {
			code = Integer.toBinaryString(i);//注意，这个string已经消除了前面的多余的0
			nodes[i].code = formatCode(code);
		}
	}
	
	//格式化一下code
	private String formatCode(String code) {
		while (code.length() < codelength - 1) {
			code = "0" + code;
		}
		return code;
	}
	
	//计算wpl
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * codelength;
		}
		System.out.println("WPL=" + wpl);
	}

	//计算编码长度
	private void calCodeLength() {
		double n = nodes.length;
		while (n > 1) {
			n = n / 2;// n/2
			codelength++;
		}
	}

	//显示最终生成的哈夫曼编码
	private void displayCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

}
