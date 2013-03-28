package mydip.processor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/**
 * Í¼ÏñÐý×ª
 */
public class Rotate implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		int bytesPerPixel = oldData.bytesPerLine / oldData.width;
		int destBytesPerLine = oldData.height * bytesPerPixel;
		byte[] newData = new byte[oldData.data.length];
		int width = 0, height = 0;
		for (int srcY = 0; srcY < oldData.height; srcY++) {
			for (int srcX = 0; srcX < oldData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
				destX = srcY;
				destY = oldData.width - srcX - 1;
				width = oldData.height;
				height = oldData.width;

				destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
				srcIndex = (srcY * oldData.bytesPerLine) + (srcX * bytesPerPixel);
				System.arraycopy(oldData.data, srcIndex, newData, destIndex, bytesPerPixel);
			}
		}
		return new Image(Display.getDefault(), new ImageData(width, height, oldData.depth, oldData.palette, destBytesPerLine, newData));
	}

}
