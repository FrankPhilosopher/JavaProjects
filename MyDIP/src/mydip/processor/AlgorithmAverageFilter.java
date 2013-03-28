package mydip.processor;

import mydip.util.ImageUtil;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class AlgorithmAverageFilter implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		ImageData newData = oldData;
		int[] pixelData = ImageUtil.getPixelData(oldData);// 保存了所有的像素值
		int[] brightData = ImageUtil.getBrightData(oldData, pixelData);// 保存了所有的灰度值
		int[] mask = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };// mask数组
		ImageUtil.calculateNewData(pixelData, brightData, mask, newData);//求出新图像的ImageData
		return new Image(Display.getDefault(), newData);// 注意，生成Image时第一个参数是Display
	}

}
