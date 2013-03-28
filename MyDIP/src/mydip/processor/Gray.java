package mydip.processor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Gray implements IImageProcessor {

	@Override
	public Image process(Image image) {
		return new Image(Display.getDefault(), image, SWT.IMAGE_GRAY);
	}

}
