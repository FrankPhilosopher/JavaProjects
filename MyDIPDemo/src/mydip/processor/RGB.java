package mydip.processor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class RGB implements IImageProcessor {

	@Override
	public Image process(Image image) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageBox box = new MessageBox(Display.getDefault().getActiveShell(),SWT.ICON_INFORMATION | SWT.OK);
				box.setMessage("ѡ��ͼ�񣬵���Ҽ���ѡ��˵���\nRGB Images�е�ĳһ��ɲ鿴�����");
				box.open();
			}
		});
		return null;
	}

}
