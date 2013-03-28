package mydip.processor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class Reverse implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData srcData = image.getImageData();
		int bytesPerPixel = srcData.bytesPerLine / srcData.width;
		int destBytesPerLine = srcData.width * bytesPerPixel;
		byte[] newData = srcData.data;
		for (int i = 0; i < newData.length; i++)
			newData[i] = (byte) (255 - newData[i]);
		ImageData newImageData = new ImageData(srcData.width, srcData.height, srcData.depth, srcData.palette,
				destBytesPerLine, newData);
		newImageData.transparentPixel = srcData.transparentPixel;
		return new Image(Display.getDefault(), newImageData);
	}

}
