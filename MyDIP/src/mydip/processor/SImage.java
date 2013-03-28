package mydip.processor;

import mydip.util.ImageUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class SImage implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		ImageData newData = oldData;
		int[] pixelData = ImageUtil.getPixelData(oldData);// 保存了所有的像素值，大小是 width * height
		int[] rgbData = new int[3];
		float[] ihsData = new float[3];
		int width = oldData.width;
		int height = oldData.height;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
//				rgbData = ImageUtil.getRGBFromPixel(pixelData[i * width + j]);//得到rgb分量
//				RGB rgb = new RGB(rgbData[0], rgbData[1], rgbData[2]);//通过分量得到RGB类
				ihsData = ImageUtil.getYUVFromPixel(pixelData[i * width + j]);
//				hsiData = rgb.getHSB();//通过RGB类得到HSI数据
				//利用hsi中的某一个分量得到新的RGB，然后根据RGB得到像素，并设置到相应的位置即可
				newData.setPixel(j, i, ImageUtil.getPixelFromYUV(new float[]{0,0,ihsData[2]}));
			}
		}
		Image newImage = new Image(Display.getDefault(), newData);
		Image newGreyImage = new Image(Display.getDefault(), newImage, SWT.IMAGE_GRAY);
		return newGreyImage;
	}

}
