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
	// �����������Ҫ�ģ�
	// һ��Ҫ�ǵ������������򱨴�Editor initialization failed: mydip.editors.ImageEditor. Site is incorrect.
	// ������Editor������openEditor��ʱ���ã�һ��input����������editor�ı����
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());// ���ñ༭����name
		// ��һ������Ҫ������Ҫʵ�ֵ����಻ͬ��ѡ��򿪵�editor�����־���ѡ�����ƣ�
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

		// ��ӱ༭���Ĺ������������� �򿪣����棬���� ������ť
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		toolBarManager.add(new OpenImageAction());
		toolBarManager.add(new SaveImageAction());
		toolBarManager.add(new ProcessAction());
		toolBarManager.update(true);

		SashForm sashForm = new SashForm(viewForm, SWT.NONE);
		Composite com_left = new Composite(sashForm, SWT.NONE);
		com_left.setLayout(new FillLayout(SWT.VERTICAL));
		// com_left.setLayout(new GridLayout());//ʹ�����ֲ��ַ�ʽ��ʹ��ͼ���С
		canvas1 = new ImageCanvas(com_left, SWT.DOUBLE_BUFFERED);
		// canvas1.setImage(imagesrc);
		canvas1.addListener(SWT.MouseDown, new MouseDownListener());

		Composite com_right = new Composite(sashForm, SWT.NONE);
		com_right.setLayout(new FillLayout(SWT.VERTICAL));
		canvas2 = new ImageCanvas(com_right, SWT.DOUBLE_BUFFERED);
		// canvas2.setImage(imagesrc);
		canvas2.addListener(SWT.MouseDown, new MouseDownListener());

		// ����viewform
		viewForm.setTopLeft(toolBar);
		viewForm.setContent(sashForm);

		popMenu(viewForm);

	}

	@Override
	public void setFocus() {

	}

	// ��������Ĳ˵���
	/**
	 * ����ò˵���֪���������ĸ�ͼ�� 1.������canvas���ò�ͬlistener����������image 2.��listener���жϣ���취�õ�����menu���ò�ͬ��data
	 * 3.ע�⣬�����Ҽ��˵�֮ǰ������һ��MouseDown�¼�
	 */
	public void popMenu(ViewForm viewForm) {
		Menu popMenu = new Menu(viewForm);
		MenuItem item_hist = new MenuItem(popMenu, SWT.PUSH);
		item_hist.setText("Show Histogram");
		item_hist.addSelectionListener(new ShowHistogramListener());

		MenuItem item_info = new MenuItem(popMenu, SWT.PUSH);
		item_info.setText("Show Information");
		item_info.addSelectionListener(new ShowInformationListener());
		// popMenu.setData(imagesrc);//����������data��û���õģ���ʼ��ʱimage����null���������лᱨ��ָ���쳣
		// popMenu2.setData(imagedes);//һ����menu�ṹ����ͬ��data

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

	// ����ԭʼͼ��
	public void setImageSource(Image image) {
		this.imagesrc = image;
		canvas1.setImage(imagesrc);
	}

	// ���ô�����Ŀ��ͼ��
	public void setImageDestination(Image image) {
		this.imagedes = image;
		canvas2.setImage(imagedes);
	}

	// �տ�ʼʹ��menuitem���õ�ѡ�е�image��ʧ�ܣ�ʹ������Ҳ���ǲ���
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
			// ʹ��Java���似�������ݵ���Ĳ�����ͼ�������Ӧ�Ĳ�����ע����ʹ����ȫ����
			Image image = ip.process(imagesrc);
			setImageDestination(image);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// ���öԻ�������ͣ��Լ�ʹ�õ�ϵͳͼ��
			mBox.setText("Infomation");// ���ñ���
			mBox.setMessage("Wrong Action!");// ������ʾ����
			mBox.open();
		}
	}

	// �������Ҫ�ж���ʲô��������Ҫ�жϵ�������ĸ�ͼ��
	// ��ʾֱ��ͼ
	public class ShowHistogramListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Image image = getPopImage(e);
			if (image != null) {
				new HistogramImage().process(image);// ����Ͳ��÷�����
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	// ��ʾͼ����Ϣ
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

	// ������������������������Ҽ����
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

	// ��ͼ��Ĳ���
	public class OpenImageAction extends Action {
		public OpenImageAction() {
			setImageDescriptor(Activator.getImageDescriptor("/icons/icos/Documents.ico"));
			setToolTipText("Open an image");
		}

		public void run() {
			FileDialog fileDialog = new FileDialog(Display.getDefault().getShells()[0], SWT.OPEN);
			// shell������null->Display.getDefault().getShells()[0]
			String filepath = fileDialog.open();
			if (filepath != null) {
				Image image = new Image(null, filepath);
				if (image != null) {
					setImageSource(image);
				}
			}
		}
	}

	// ����ͼ��Ĳ���
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
				imageLoader.save(filepath, imagesrc.type);// ����Ϊ��Դͼ����ͬ�ĸ�ʽ
			}
			MessageBox mBox = new MessageBox(Display.getDefault().getShells()[0], SWT.ICON_INFORMATION);// ���öԻ�������ͣ��Լ�ʹ�õ�ϵͳͼ��
			mBox.setText("Infomation");// ���ñ���
			mBox.setMessage("Save successfully!");// ������ʾ����
			mBox.open();
		}
	}

	// �������
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
