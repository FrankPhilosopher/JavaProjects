package mydip.tools;

import java.awt.Color;
import java.io.FileOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Histogram {

	private String title = ""; // ͼƬ����
	private String XTitle = ""; // ͼƬ���������
	private String YTitle = ""; // ͼƬ��ֱ�������
	private int Xsz = 10; // X���������С
	private int Ysz = 10; // X���������С
	private Color bgcolor = null; // ͼƬ������ɫ
	private int width = 800; // Ҫ���ɵ�ͼƬ�Ŀ��
	private int height = 600; // Ҫ���ɵ�ͼƬ�ĸ߶�
	private double margin = 0.2; // ÿ������ļ�� 0--1֮��
	private boolean isV = true; // ��ͼ��ʾ��ʽ��0:��ֱ 1:ˮƽ��ʾ
	private String fileName = ""; // ͼƬ����(���Լ�·��)

	private DefaultCategoryDataset dataset = null; // ��ʾͼƬ���õ����ݼ�
	private FileOutputStream fosJpg = null; // ����ͼƬʱ�õ��������

	// ���Ҫ���л���״ͼ������
	public void addData(String name, double value, String group) {
		if (dataset != null) {
			dataset.addValue(value, group, name);// double value, Comparable rowKey, Comparable columnKey
		} else {
			dataset = new DefaultCategoryDataset();
			dataset.addValue(value, group, name);
		}
	}

	// ���ļ�·������������״ͼ �����ļ����� �ļ�����Ϊ��ʹ��·��Ϊ��: d:\\web\test.jpg
	public boolean saveHistogram(String fileName, JFreeChart chart) {
		try {
			fosJpg = new FileOutputStream(fileName);
			ChartUtilities.writeChartAsJPEG(fosJpg, 100, chart, this.width, this.height, null);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// ���öԻ�������ͣ��Լ�ʹ�õ�ϵͳͼ��
			mBox.setText("Infomation");// ���ñ���
			mBox.setMessage("Save failed!");// ������ʾ����
			mBox.open();
		} finally {
//			this.dataset.clear();
			try {
				fosJpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	// ��ʼ������
	public void init() {
		setTitle("��״ͼ");
		setXTitle("�����");
		setYTitle("�ݱ���");
		setXFontSize(10);
		setYFontSize(10);
		setWidth(800);
		setHeight(600);
		setMargin(0.1);
		setIsV(true);
		setFileName("temp.jpg");
		setBgcolor(255, 255, 255);
	}

	public void reset() {
		dataset.clear();
		init();
	}

	public void setTitle(String str) {
		this.title = str;
	}

	public void setXTitle(String str) {
		this.XTitle = str;
	}

	public void setYTitle(String str) {
		this.YTitle = str;
	}

	public void setXFontSize(int i) {
		this.Xsz = i;
	}

	public void setYFontSize(int i) {
		this.Ysz = i;
	}

	public void setBgcolor(int red, int green, int blue) {
		this.bgcolor = new Color(red, green, blue);
	}

	public void setBgcolor(Color color) {
		this.bgcolor = color;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public void setIsV(boolean str) {
		this.isV = str;
	}

	public void setFileName(String str) {
		this.fileName = str;
	}

	public int getXsz() {
		return Xsz;
	}

	public void setXsz(int xsz) {
		Xsz = xsz;
	}

	public int getYsz() {
		return Ysz;
	}

	public void setYsz(int ysz) {
		Ysz = ysz;
	}

	public boolean isV() {
		return isV;
	}

	public void setV(boolean isV) {
		this.isV = isV;
	}

	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}

	public FileOutputStream getFosJpg() {
		return fosJpg;
	}

	public void setFosJpg(FileOutputStream fosJpg) {
		this.fosJpg = fosJpg;
	}

	public String getTitle() {
		return title;
	}

	public String getXTitle() {
		return XTitle;
	}

	public String getYTitle() {
		return YTitle;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getMargin() {
		return margin;
	}

	public String getFileName() {
		return fileName;
	}

}