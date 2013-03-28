package ex1;

import java.util.Arrays;

/**
 * ��չ����
 * 
 * @author yinger
 * 
 */
public class ExtendCoding implements ICoding {

	public Node[] nodes;//��������ݽڵ�
	public int[] codeLength;//���볤��
	public double wpl;//wpl

	public ExtendCoding(Node[] nodes, int[] codeLength) {
		this.nodes = nodes;
		this.codeLength = codeLength;
	}

	public void doCoding() {
		initCode();
		System.out.println("����" + listCodeLength() + "��չ���뷽ʽ���룺");
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

//����code
	private void makeCode() {
		Arrays.sort(nodes);//�õ���nodes���鰴��node�ĸ��ʽ�������
		int type = codeLength.length;//���ȵ�����
		int currentLength;//��ǰ���볤��
		int number;//����ʹ�õ�ǰ���볤�ȵ������Ŀ
		int m = 1, n = 1;//number = 2^(m) - n;
		int currentValue = 0;//��ǰʹ�õ�������ֵ
		int currentIndex = 0;//nodes���±꣬������code��ֵ
		int maxIndex;//�ôγ������ɵ��������±�
		for (int i = 0; i < type; i++) {
			n = 1;//���¸��ϳ�ֵ
			currentLength = codeLength[i];
			if (i == 0) {//��һ�ֳ���
				m = currentLength;//����
			}
			if (i == type - 1) {//���һ�ֳ��ȣ��������ü�һ
				n = 0;
			}
			number = (int) (Math.pow(2, m) - n);//���ᳬ��number����ʹ��������Ƚ��б���
			maxIndex = Math.min(currentIndex + number, nodes.length);//maxIndexȡ�����еĽ�С��
			for (int j = currentIndex; j < maxIndex; j++) {
				String code = Integer.toBinaryString(currentValue);
				nodes[j].code = formateCode(code, currentLength);
				currentValue++;//��ǰֵ��һ
				currentIndex++;
			}
			//���������ȵı���ʹ�����ˣ���ô��Ҫʹ����һ�ֳ��ȵı��룬�ڴ�֮ǰҪ�ı�currentValue��ֵ
			if (i < type - 1) {
//				currentValue++;//���ȼ�1����ɸó��ȵ����ֵ//���ﲻ��Ҫ�ټ�һ�ˣ��������
				m = codeLength[i + 1] - codeLength[i];//��������֮��Ĳ��
				currentValue = currentValue << m;//�����ƶ����ʵ�λ��
			}
		}
	}

	//����ָ���ĳ��ȶ�code���и�ʽ��
	private String formateCode(String code, int currentLength) {
		while (code.length() < currentLength) {
			code = "0" + code;
		}
		return code;
	}

	//����wpl
	private void calWPL() {
		for (int i = 0; i < nodes.length; i++) {
			wpl += nodes[i].possibility * (nodes[i].code.length());
		}
		System.out.println("WPL=" + wpl);
	}

	//��ʾ����ĳ���
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

	//��ʾ�������ɵĹ���������
	private void displayCode() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i].name + "\t" + nodes[i].possibility + "\t" + nodes[i].code);
		}
	}

}
