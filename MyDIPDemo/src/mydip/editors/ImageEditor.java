package mydip.editors;

import mydip.Activator;
import mydip.dialog.ImageDialog;
import mydip.processor.HistogramImage;
import mydip.processor.IImageProcessor;
import mydip.widgets.ImageCanvas;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ImageEditor extends EditorPart {

	public static final String ID = "mydip.editors.ImageEditor";
	public Image imagesrc = null;
	public Image imagedes = null;
	private ImageCanvas canvas1;
	private ImageCanvas canvas2;
	private boolean isSource = true;

	public ImageEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	// 这个方法很重要的！
	// 一定要记得重载它，否则报错：Editor initialization failed: mydip.editors.ImageEditor. Site is incorrect.
	// 它会在Editor启动（openEditor）时调用，一般input是用于设置editor的标题的
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());// 设置编辑器的name
		// 这一步很重要，我想要实现点击左侧不同的选项，打开的editor的名字就是选项名称！
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		ViewForm viewForm = new ViewForm(parent, SWT.NONE);
		viewForm.setLayout(new FillLayout());// FillLayout type RowLayout type

		// 添加编辑器的工具栏，包括了 打开，保存，操作 三个按钮
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		toolBarManager.add(new OpenImageAction());
		toolBarManager.add(new SaveImageAction());
		toolBarManager.add(new ProcessAction());
		toolBarManager.update(true);

		SashForm sashForm = new SashForm(viewForm, SWT.NONE);
		Composite com_left = new Composite(sashForm, SWT.NONE);
		com_left.setLayout(new FillLayout(SWT.VERTICAL));
		// com_left.setLayout(new GridLayout());//使用这种布局方式会使得图像很小
		canvas1 = new ImageCanvas(com_left, SWT.DOUBLE_BUFFERED);
		// canvas1.setImage(imagesrc);
		canvas1.addListener(SWT.MouseDown, new MouseDownListener());

		Composite com_right = new Composite(sashForm, SWT.NONE);
		com_right.setLayout(new FillLayout(SWT.VERTICAL));
		canvas2 = new ImageCanvas(com_right, SWT.DOUBLE_BUFFERED);
		// canvas2.setImage(imagesrc);
		canvas2.addListener(SWT.MouseDown, new MouseDownListener());

		// 设置viewform
		viewForm.setTopLeft(toolBar);
		viewForm.setContent(sashForm);

		popMenu(viewForm);

	}

	@Override
	public void setFocus() {

	}

	// 添加上下文菜单！
	/**
	 * 如何让菜单得知操作的是哪个图像？ 1.给两个canvas设置不同listener，不用区分image 2.在listener中判断，想办法得到！给menu设置不同的data
	 * 3.注意，触发右键菜单之前首先有一个MouseDown事件
	 */
	public void popMenu(ViewForm viewForm) {
		Menu popMenu = new Menu(viewForm);
		MenuItem item_hist = new MenuItem(popMenu, SWT.PUSH);
		item_hist.setText("Show Histogram");
		item_hist.addSelectionListener(new ShowHistogramListener());

		MenuItem item_info = new MenuItem(popMenu, SWT.PUSH);
		item_info.setText("Show Information");
		item_info.addSelectionListener(new ShowInformationListener());
		// popMenu.setData(imagesrc);//在这里设置data是没有用的，初始化时image都是null，所以运行会报空指针异常
		// popMenu2.setData(imagedes);//一样的menu结构，不同的data

		MenuItem item_rgb = new MenuItem(popMenu, SWT.CASCADE);
		item_rgb.setText("RGB Images");
		Menu subMenu_rgb = new Menu(popMenu);
		item_rgb.setMenu(subMenu_rgb);
		MenuItem item_r = new MenuItem(subMenu_rgb, SWT.PUSH);
		item_r.setText("RImage");
		item_r.addSelectionListener(new ShowImageListener());
		MenuItem item_g = new MenuItem(subMenu_rgb, SWT.PUSH);
		item_g.setText("GImage");
		item_g.addSelectionListener(new ShowImageListener());
		MenuItem item_b = new MenuItem(subMenu_rgb, SWT.PUSH);
		item_b.setText("BImage");
		item_b.addSelectionListener(new ShowImageListener());

		MenuItem item_hsi = new MenuItem(popMenu, SWT.CASCADE);
		item_hsi.setText("HSI Images");
		Menu subMenu_hsi = new Menu(popMenu);
		item_hsi.setMenu(subMenu_hsi);
		MenuItem item_h = new MenuItem(subMenu_hsi, SWT.PUSH);
		item_h.setText("HImage");
		item_h.addSelectionListener(new ShowImageListener());
		MenuItem item_s = new MenuItem(subMenu_hsi, SWT.PUSH);
		item_s.setText("SImage");
		item_s.addSelectionListener(new ShowImageListener());
		MenuItem item_i = new MenuItem(subMenu_hsi, SWT.PUSH);
		item_i.setText("IImage");
		item_i.addSelectionListener(new ShowImageListener());

		canvas1.setMenu(popMenu);
		canvas2.setMenu(popMenu);
	}

	// 设置原始图像
	public void setImageSource(Image image) {
		this.imagesrc = image;
		canvas1.setImage(imagesrc);
	}

	// 设置处理后的目标图像
	public void setImageDestination(Image image) {
		this.imagedes = image;
		canvas2.setImage(imagedes);
	}

	// 刚开始使用menuitem来得到选中的image，失败！使用坐标也还是不行
	public Image getPopImage(SelectionEvent e) {
		Image image = null;
		if (isSource) {
			image = imagesrc;
			// System.out.println("source");
		} else {
			image = imagedes;
			// System.out.println("destination");
		}
		return image;
	}

	public void processImage(String action) {
		if (imagesrc == null) {
			return;
		}
		if (action == null) {
			action = getPartName();
		}
		IImageProcessor ip = null;
		try {
			ip = (IImageProcessor) Class.forName("mydip.processor." + action).newInstance();
			// 使用Java反射技术，根据点击的操作对图像进行相应的操作！注意类使用了全名！
			Image image = ip.process(imagesrc);
			setImageDestination(image);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// 设置对话框的类型，以及使用的系统图标
			mBox.setText("Infomation");// 设置标题
			mBox.setMessage("Wrong Action!");// 设置显示内容
			mBox.open();
		}
	}

	// 这里除了要判断是什么操作，还要判断点击的是哪个图像
	// 显示直方图
	public class ShowHistogramListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Image image = getPopImage(e);
			if (image != null) {
				new HistogramImage().process(image);// 这里就不用反射了
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	// 显示图像信息
	public class ShowInformationListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Image image = getPopImage(e);
			if (image != null) {
				ImageDialog dialog = new ImageDialog(Display.getDefault().getShells()[0]);
				dialog.setImage(image);
				dialog.open();
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	public class ShowImageListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			String action = ((MenuItem) (e.getSource())).getText();
			processImage(action);
			// ImageDialog dialog = new ImageDialog(Display.getDefault().getShells()[0]);
			// dialog.setImage(imagedes);
			// dialog.open();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	// 鼠标点击，不论是左键点击还是右键点击
	public class MouseDownListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			ImageCanvas canvas = (ImageCanvas) event.widget;
			if (canvas.equals(canvas1)) {
				isSource = true;
			} else {
				isSource = false;
			}
		}
	}

	// 打开图像的操作
	public class OpenImageAction extends Action {
		public OpenImageAction() {
			setImageDescriptor(Activator.getImageDescriptor("/icons/icos/Documents.ico"));
			setToolTipText("Open an image");
		}

		public void run() {
			FileDialog fileDialog = new FileDialog(Display.getDefault().getShells()[0], SWT.OPEN);
			// shell不能是null->Display.getDefault().getShells()[0]
			String filepath = fileDialog.open();
			if (filepath != null) {
				Image image = new Image(null, filepath);
				if (image != null) {
					setImageSource(image);
				}
			}
		}
	}

	// 保存图像的操作
	public class SaveImageAction extends Action {
		public SaveImageAction() {
			setImageDescriptor(Activator.getImageDescriptor("/icons/icos/Bookmark.ico"));
			setToolTipText("Save the destination image");
		}

		public void run() {
			if (imagedes == null) {
				return;
			}
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { imagedes.getImageData() };
			FileDialog fileDialog = new FileDialog(Display.getDefault().getShells()[0], SWT.SAVE);
			String filepath = fileDialog.open();
			if (filepath != null) {
				imageLoader.save(filepath, imagesrc.type);// 保存为和源图像相同的格式
			}
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// 设置对话框的类型，以及使用的系统图标
			mBox.setText("Infomation");// 设置标题
			mBox.setMessage("Save successfully!");// 设置显示内容
			mBox.open();
		}
	}

	// 处理操作
	public class ProcessAction extends Action {
		public ProcessAction() {
			setImageDescriptor(Activator.getImageDescriptor("/icons/icos/Settings.ico"));
			setToolTipText("process the  image");
		}

		public void run() {
			processImage(null);
		}
	}

}
