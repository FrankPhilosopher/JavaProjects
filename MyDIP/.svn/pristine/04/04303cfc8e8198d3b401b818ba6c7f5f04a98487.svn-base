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

	private String title = ""; // 图片标题
	private String XTitle = ""; // 图片横坐标标题
	private String YTitle = ""; // 图片垂直坐标标题
	private int Xsz = 10; // X轴标尺字体大小
	private int Ysz = 10; // X轴标尺字体大小
	private Color bgcolor = null; // 图片背景颜色
	private int width = 800; // 要生成的图片的宽度
	private int height = 600; // 要生成的图片的高度
	private double margin = 0.2; // 每组柱间的间距 0--1之间
	private boolean isV = true; // 柱图显示方式：0:垂直 1:水平显示
	private String fileName = ""; // 图片名称(可以加路经)

	private DefaultCategoryDataset dataset = null; // 显示图片需用的数据集
	private FileOutputStream fosJpg = null; // 生成图片时用到的输出流

	// 添加要进行画柱状图的数据
	public void addData(String name, double value, String group) {
		if (dataset != null) {
			dataset.addValue(value, group, name);// double value, Comparable rowKey, Comparable columnKey
		} else {
			dataset = new DefaultCategoryDataset();
			dataset.addValue(value, group, name);
		}
	}

	// 按文件路径保存生成柱状图 保存文件名称 文件名称为（使用路径为）: d:\\web\test.jpg
	public boolean saveHistogram(String fileName, JFreeChart chart) {
		try {
			fosJpg = new FileOutputStream(fileName);
			ChartUtilities.writeChartAsJPEG(fosJpg, 100, chart, this.width, this.height, null);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// 设置对话框的类型，以及使用的系统图标
			mBox.setText("Infomation");// 设置标题
			mBox.setMessage("Save failed!");// 设置显示内容
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

	// 初始化参数
	public void init() {
		setTitle("柱状图");
		setXTitle("横标题");
		setYTitle("纵标题");
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