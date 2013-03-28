package ex1;

/**
 * �ȳ�����
 * 
 * @author yinger
 * 
 */
public class EqualLengthCoding implements ICoding {

	public Node[] nodes;//��������ݽڵ�
	public int codelength = 1;//�ȳ�����ı��볤��
	public double wpl;//wpl

	public EqualLengthCoding(Node[] nodes) {
		this.nodes = nodes;
	}

	public void doCoding() {
		initCode();
		System.out.println("���õȳ����뷽ʽ���룺");
		calCodeLength();
		makeCode();
		displayCode();
		calWPL();
	}

	//��ʼ��codeֵ
	private void initCode() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].code = "";
		}
	}

	//��node���б���
	private void makeCode() {
		String code = null;
		for (int i = 0; i < nodes.length; i++) {
			code = Integer.toBinaryString(i);//ע�⣬���string�Ѿ�������ǰ��Ķ����0
			nodes[i].code = formatCode(code);
		}
	}
	
	//��ʽ��һ��code
	private String formatCode(String code) {
		while (code.length() < codelength - 1) {
			code = "0" + code;
		}
		return code;
	}
	
	//����wpl
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * codelength;
		}
		System.out.println("WPL=" + wpl);
	}

	//������볤��
	private void calCodeLength() {
		double n = nodes.length;
		while (n > 1) {
			n = n / 2;// n/2
			codelength++;
		}
	}

	//��ʾ�������ɵĹ���������
	private void displayCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

}
