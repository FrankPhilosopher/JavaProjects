package mydip.processor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Smaller implements IImageProcessor {

	@Override
	public Image process(Image image) {
		Image newImage = new Image(Display.getDefault(), image.getImageData().scaledTo(image.getImageData().width / 2, image.getImageData().height / 2));
		return newImage;
	}

}
