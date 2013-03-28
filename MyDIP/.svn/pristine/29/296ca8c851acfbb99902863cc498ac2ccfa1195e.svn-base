package mydip.views;

import mydip.editors.ImageEditor;
import mydip.editors.ImageEditorInput;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class NavigatorView extends ViewPart {

	public static final String ID = "mydip.views.NavigatorView";

	public NavigatorView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		ExpandBar expandBar = new ExpandBar(composite, SWT.V_SCROLL);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createExpandItem1(expandBar);// basic tools

		createExpandItem2(expandBar);// spacial

		createExpandItem3(expandBar);// frequency

		createExpandItem4(expandBar);// Color Image Processing

		createExpandItem5(expandBar);// Image Restoration

		expandBar.setSpacing(10);

	}

	private void createExpandItem1(ExpandBar expandBar) {
		Composite composite1 = new Composite(expandBar, SWT.NONE);
		composite1.setLayout(new GridLayout());
		Label label_rotate = new Label(composite1, SWT.NONE);
		label_rotate.setText("Rotate");
		label_rotate.setToolTipText("旋转图像");
		label_rotate.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		// label_rotate.addListener(SWT.MouseEnter, new MouseHoverListener());//使用enter要比使用hover反应的速度快很多！
		label_rotate.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_rotate.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_bigger = new Label(composite1, SWT.NONE);
		label_bigger.setText("Bigger");
		label_bigger.setToolTipText("放大图像");
		label_bigger.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_bigger.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_bigger.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_smaller = new Label(composite1, SWT.NONE);
		label_smaller.setText("Smaller");
		label_smaller.setToolTipText("缩小图像");
		label_smaller.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_smaller.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_smaller.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_grey = new Label(composite1, SWT.NONE);
		label_grey.setText("Gray");
		label_grey.setToolTipText("灰度处理");
		label_grey.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_grey.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_grey.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_reverse = new Label(composite1, SWT.NONE);
		label_reverse.setText("Reverse");
		label_reverse.setToolTipText("图像反色");
		label_reverse.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_reverse.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_reverse.addListener(SWT.MouseExit, new MouseExitListener());

		ExpandItem expandItem1 = new ExpandItem(expandBar, SWT.NONE);
		expandItem1.setText("Basic Tools");
		expandItem1.setControl(composite1);
		expandItem1.setHeight(composite1.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem1.setExpanded(true);
	}

	private void createExpandItem2(ExpandBar expandBar) {
		Composite composite2 = new Composite(expandBar, SWT.NONE);
		composite2.setLayout(new GridLayout());

		Label label_histeq = new Label(composite2, SWT.NONE);
		label_histeq.setText("HistogramEqualization");
		label_histeq.setToolTipText("直方图均衡化");
		label_histeq.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_histeq.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_histeq.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_lp = new Label(composite2, SWT.NONE);
		label_lp.setText("SpatialLowPass");
		label_lp.setToolTipText("空间域低通（钝化）");
		label_lp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_lp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_lp.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_hp = new Label(composite2, SWT.NONE);
		label_hp.setText("SpatialHighPass");
		label_hp.setToolTipText("空间域高通（锐化）");
		label_hp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_hp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_hp.addListener(SWT.MouseExit, new MouseExitListener());

		ExpandItem expandItem2 = new ExpandItem(expandBar, SWT.NONE);
		expandItem2.setText("Spacial Domain");
		expandItem2.setControl(composite2);
		expandItem2.setHeight(composite2.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem2.setExpanded(true);
	}

	private void createExpandItem3(ExpandBar expandBar) {
		Composite composite3 = new Composite(expandBar, SWT.NONE);
		composite3.setLayout(new GridLayout());

		Label label_Idealp = new Label(composite3, SWT.NONE);
		label_Idealp.setText("IdeaLowPassFilter");
		label_Idealp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_Idealp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_Idealp.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_Ideahp = new Label(composite3, SWT.NONE);
		label_Ideahp.setText("IdeaHighPassFilter");
		label_Ideahp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_Ideahp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_Ideahp.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_Gussianlp = new Label(composite3, SWT.NONE);
		label_Gussianlp.setText("GussianLowPassFilter");
		label_Gussianlp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_Gussianlp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_Gussianlp.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_Gussianhp = new Label(composite3, SWT.NONE);
		label_Gussianhp.setText("GussianHighPassFilter");
		label_Gussianhp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_Gussianhp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_Gussianhp.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_ButterWorthlp = new Label(composite3, SWT.NONE);
		label_ButterWorthlp.setText("ButterWorthLowPassFilter");
		label_ButterWorthlp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_ButterWorthlp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_ButterWorthlp.addListener(SWT.MouseExit, new MouseExitListener());
		
		Label label_ButterWorthhp = new Label(composite3, SWT.NONE);
		label_ButterWorthhp.setText("ButterWorthHighPassFilter");
		label_ButterWorthhp.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_ButterWorthhp.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_ButterWorthhp.addListener(SWT.MouseExit, new MouseExitListener());

		ExpandItem expandItem3 = new ExpandItem(expandBar, SWT.NONE);
		expandItem3.setText("Frequency Domain");
		expandItem3.setControl(composite3);
		expandItem3.setHeight(composite3.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem3.setExpanded(false);
	}

	private void createExpandItem4(ExpandBar expandBar) {
		Composite composite4 = new Composite(expandBar, SWT.NONE);
		composite4.setLayout(new GridLayout());

		Label label_RGB = new Label(composite4, SWT.NONE);
		label_RGB.setText("RGB");
		label_RGB.setToolTipText("RGB分量");
		label_RGB.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_RGB.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_RGB.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_HSI = new Label(composite4, SWT.NONE);
		label_HSI.setText("HSI");
		label_HSI.setToolTipText("HSI分量");
		label_HSI.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_HSI.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_HSI.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_EdgeDetection = new Label(composite4, SWT.NONE);
		label_EdgeDetection.setText("EdgeDetection");
		label_EdgeDetection.setToolTipText("边缘检测");
		label_EdgeDetection.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_EdgeDetection.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_EdgeDetection.addListener(SWT.MouseExit, new MouseExitListener());

		ExpandItem expandItem4 = new ExpandItem(expandBar, SWT.NONE);
		expandItem4.setText("Color Image");
		expandItem4.setControl(composite4);
		expandItem4.setHeight(composite4.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem4.setExpanded(true);
	}

	private void createExpandItem5(ExpandBar expandBar) {
		Composite composite4 = new Composite(expandBar, SWT.NONE);
		composite4.setLayout(new GridLayout());

		Label label_algavg = new Label(composite4, SWT.NONE);
		label_algavg.setText("AlgorithmAverageFilter");
		label_algavg.setToolTipText("算术平均滤波");
		label_algavg.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_algavg.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_algavg.addListener(SWT.MouseExit, new MouseExitListener());

		Label label_HSI = new Label(composite4, SWT.NONE);
		label_HSI.setText("GeomAverageFilter");
		label_HSI.setToolTipText("几何平均滤波");
		label_HSI.addListener(SWT.MouseDoubleClick, new DoubleClickListener());
		label_HSI.addListener(SWT.MouseEnter, new MouseEnterListener());
		label_HSI.addListener(SWT.MouseExit, new MouseExitListener());

		ExpandItem expandItem4 = new ExpandItem(expandBar, SWT.NONE);
		expandItem4.setText("Image restoration");
		expandItem4.setControl(composite4);
		expandItem4.setHeight(composite4.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem4.setExpanded(true);
	}

	@Override
	public void setFocus() {

	}

	//双击打开editor
	public class DoubleClickListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			Label label = (Label) event.widget;
			IEditorInput input = new ImageEditorInput(label.getText());
			// 每次都是new的话会导致每次都要打开一个新的窗口
			IWorkbenchPage workbenchPage = getViewSite().getPage();
			IEditorPart editor = workbenchPage.findEditor(input);// 这里是有用的，根据自定义的input相等性原则find！
			String editorID = ImageEditor.ID;
			if (editor != null) {
				workbenchPage.bringToTop(editor);
			} else {
				try {
					workbenchPage.openEditor(input, editorID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	//鼠标进入改变操作的颜色
	public class MouseEnterListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			// System.out.println("MouseEnterListener");
			Label label = (Label) event.widget;
			label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_BLUE));// 设置颜色变化！
			// label.setBackground(label.getDisplay().getSystemColor(SWT.COLOR_BLUE));//应该设置前景色！
			label.setCursor(new Cursor(label.getDisplay(), SWT.CURSOR_HAND));
			// label.setFocus();
		}
	}

	//鼠标移出改变操作的颜色
	public class MouseExitListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			// System.out.println("MouseExitListener");
			Label label = (Label) event.widget;
			label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_BLACK));// 设置颜色变化！
		}
	}

}
