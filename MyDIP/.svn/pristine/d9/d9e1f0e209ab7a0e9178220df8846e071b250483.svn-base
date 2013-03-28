package mydip.processor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class HSI implements IImageProcessor {

	@Override
	public Image process(Image image) {
		MessageBox box = new MessageBox(Display.getDefault().getActiveShell(),SWT.ICON_INFORMATION | SWT.OK);
		box.setMessage("选中图像，点击右键，选择菜单项\nHSI Images中的某一项即可查看结果！");
		box.open();	
		return null;
	}

}
