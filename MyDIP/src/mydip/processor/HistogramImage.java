package mydip.processor;

import java.awt.Color;

import mydip.dialog.HistogramDialog;
import mydip.tools.Histogram;
import mydip.util.ImageUtil;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class HistogramImage implements IImageProcessor {

	private Image image;
	private int[] histArray;
	private Histogram histogram;

	@Override
	public Image process(Image image) {
		this.image = image;
		calculateHistogram();
		createHistogram();
		showHistogramDialog();
		return null;
	}

	// 计算灰度直方图信息
	public void calculateHistogram() {
		if (histArray == null) {
			histArray = new int[256];
		}
		for (int i = 0; i < 256; i++) {
			histArray[i] = 0;
		}
		ImageData ideaImageData = image.getImageData();
		int[] lineData = new int[ideaImageData.width];
		for (int y = 0; y < ideaImageData.height; y++) {
			ideaImageData.getPixels(0, y, ideaImageData.width, lineData, 0);// 首先是得到每行的像素值，保存到int数组lineData中
			for (int x = 0; x < lineData.length; x++) {
				histArray[ImageUtil.getBrightFromPixel(lineData[x])]++;
			}
		}
	}

	// 创建灰度直方图
	public void createHistogram() {
		if (histogram == null) {
			histogram = new Histogram();
		}
		// 1.生成绘图类对象
		double count = image.getImageData().width * image.getImageData().height;
		// 2.添加数据
		for (int i = 0; i < histArray.length; i++) {
			histogram.addData(String.valueOf(i), histArray[i] / count, "Histogram");
		}
		// 3.设置图片的初始化属性
		histogram.init();
		// 4.设置这个图片的属性
		histogram.setTitle("灰度直方图");
		histogram.setXTitle("灰度值");
		histogram.setYTitle("百分比");
		histogram.setBgcolor(Color.WHITE);
		histogram.setIsV(true);
		histogram.setWidth(800);
		histogram.setHeight(600);
		histogram.setMargin(0.1);
		histogram.setXFontSize(10);
		histogram.setYFontSize(10);
	}

	// 显示直方图对话框
	public void showHistogramDialog() {
		HistogramDialog dialog = new HistogramDialog(Display.getDefault().getShells()[0], histogram);
		dialog.open();
	}
}
