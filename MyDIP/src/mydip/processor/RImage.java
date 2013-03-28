package mydip.processor;

import mydip.util.ImageUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class RImage implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		ImageData newData = oldData;
		int[] pixelData = ImageUtil.getPixelData(oldData);// 保存了所有的像素值，大小是 width * height
		// int[] rData = pixelData;
		// int[] gData = pixelData;
		// int[] bData = pixelData;
		// int[][] rgbs = new int[3][pixelData.length];
		int[] rgb = new int[3];
		int width = oldData.width;
		int height = oldData.height;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = ImageUtil.getRGBFromPixel(pixelData[i * width + j]);
				newData.setPixel(j, i, ImageUtil.getPixelFromRGB(oldData, new RGB(rgb[0], 0, 0)));
			}
		}

		// for (int i = 0; i < pixelData.length; i++) {
		// rgb = ImageUtil.getRGBFromPixel(pixelData[i]);
		// newData.setPixel(x, y, pixelValue) = ImageUtil.getPixelFromRGB(oldData,new int[]{rgb[0],0,0});
		// rData[i] = ImageUtil.getPixelFromRGB(oldData,new int[]{rgb[0],0,0});
		// gData[i] = ImageUtil.getPixelFromRGB(oldData,new int[]{0,rgb[1],0});
		// bData[i] = ImageUtil.getPixelFromRGB(oldData,new int[]{0,0,rgb[2]});
		// }
		// ImageUtil.calculateNewDataInRGB(rData, newData);
		Image newImage = new Image(Display.getDefault(), newData);
		Image newGreyImage = new Image(Display.getDefault(), newImage, SWT.IMAGE_GRAY);
		return newGreyImage;
	}

}
