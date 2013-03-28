package com.yinger.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.cloudgarden.resource.SWTResourceManager;
import com.yinger.download.DownloadInfo;
import com.yinger.download.MultiThreadDownload;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MainFrame extends org.eclipse.swt.widgets.Composite {

	{
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite composite_downloadInfo;
	private ProgressBar progressBar_downloadPro2;
	private Label label_downloadState2;
	private Label label_fileUrl2;
	private Label label_saveFilePath2;
	private Label label_threadNum2;
	private Label label_downloadSpeed2;
	private Label label_filesize2;
	private Label label_filename2;
	private Label label_downloadState;
	private Label label_fileUrl;
	private Label label_saveFilePath;
	private Label label_threadNum;
	private Label label_downloadSpeed;
	private Label label_downloadPro;
	private Label label_filesize;
	private Label label_filename;
	private ToolItem toolItem_about;
	private ToolItem toolItem_close;
	private ToolItem toolItem_new;
	private ToolBar toolBar_tools;
	private List list_downloadList;
	// private String[] downloadList;
	private HashMap<String, DownloadInfo> downloadMap = new HashMap<String, DownloadInfo>();//这个Map保存了所有下载的信息
	private DownloadInfo downloadInfo; //这个很重要，代表着当前的那个正在显示的downloadInfo

	public MainFrame(Composite parent, int style) {
		super(parent, style);
		iniDownloadData();
		initGUI();
		initList();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			this.setSize(new org.eclipse.swt.graphics.Point(400, 300));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent evt) {
					thisWidgetDisposed(evt);
				}
			});
			{
				toolBar_tools = new ToolBar(this, SWT.NONE);
				FormData toolBar_toolsLData = new FormData();
				toolBar_toolsLData.width = 400;
				toolBar_toolsLData.height = 24;
				toolBar_toolsLData.left = new FormAttachment(0, 1000, 0);
				toolBar_toolsLData.top = new FormAttachment(0, 1000, 0);
				toolBar_toolsLData.right = new FormAttachment(1000, 1000, 0);
				toolBar_tools.setLayoutData(toolBar_toolsLData);
				toolBar_tools.setBackground(SWTResourceManager.getColor(192, 192, 192));
				{
					toolItem_new = new ToolItem(toolBar_tools, SWT.NONE);
					toolItem_new.setImage(SWTResourceManager.getImage("images/newtask.gif"));
					toolItem_new.setToolTipText("\u6dfb\u52a0\u65b0\u4efb\u52a1");
					toolItem_new.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							toolItem_newWidgetSelected(evt);
						}
					});
				}
				{
					toolItem_close = new ToolItem(toolBar_tools, SWT.NONE);
					toolItem_close.setImage(SWTResourceManager.getImage("images/close.gif"));
					toolItem_close.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							toolItem_closeWidgetSelected(evt);
						}
					});
				}
				{
					toolItem_about = new ToolItem(toolBar_tools, SWT.NONE);
					toolItem_about.setImage(SWTResourceManager.getImage("images/about.gif"));
					toolItem_about.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							toolItem_aboutWidgetSelected(evt);
						}
					});
				}
			}
			{
				FormData list_downloadListLData = new FormData();
				list_downloadListLData.width = 117;
				list_downloadListLData.height = 275;
				list_downloadListLData.left = new FormAttachment(0, 1000, 0);
				list_downloadListLData.top = new FormAttachment(0, 1000, 24);
				list_downloadListLData.bottom = new FormAttachment(1000, 1000, -1);
				list_downloadList = new List(this, SWT.V_SCROLL);
				list_downloadList.setLayoutData(list_downloadListLData);
				list_downloadList.setBackground(SWTResourceManager.getColor(199, 250, 190));
				list_downloadList.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						list_downloadListWidgetSelected(evt);
					}
				});
			}
			{
				composite_downloadInfo = new Composite(this, SWT.NONE);
				FormLayout composite_downloadInfoLayout = new FormLayout();
				FormData composite_downloadInfoLData = new FormData();
				composite_downloadInfoLData.width = 263;
				composite_downloadInfoLData.height = 275;
				composite_downloadInfoLData.top = new FormAttachment(0, 1000, 24);
				composite_downloadInfoLData.right = new FormAttachment(1000, 1000, 0);
				composite_downloadInfoLData.bottom = new FormAttachment(1000, 1000, -1);
				composite_downloadInfoLData.left = new FormAttachment(343, 1000, 0);
				composite_downloadInfo.setLayoutData(composite_downloadInfoLData);
				composite_downloadInfo.setLayout(composite_downloadInfoLayout);
				composite_downloadInfo.setBackground(SWTResourceManager.getColor(181, 255, 255));
				{
					progressBar_downloadPro2 = new ProgressBar(composite_downloadInfo, SWT.NONE);
					FormData progressBar_downloadPro2LData = new FormData();
					progressBar_downloadPro2LData.width = 170;
					progressBar_downloadPro2LData.height = 17;
					progressBar_downloadPro2LData.left = new FormAttachment(0, 1000, 79);
					progressBar_downloadPro2LData.top = new FormAttachment(0, 1000, 83);
					progressBar_downloadPro2.setLayoutData(progressBar_downloadPro2LData);
					progressBar_downloadPro2.setMinimum(0);
					progressBar_downloadPro2.setMaximum(100);
				}
				{
					label_filename = new Label(composite_downloadInfo, SWT.NONE);
					label_filename.setText("\u6587\u4ef6\u540d\u79f0\uff1a");
					FormData label_filenameLData = new FormData();
					label_filenameLData.width = 61;
					label_filenameLData.height = 21;
					label_filenameLData.left = new FormAttachment(0, 1000, 12);
					label_filenameLData.top = new FormAttachment(0, 1000, 23);
					label_filename.setLayoutData(label_filenameLData);
					label_filename.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_filesize = new Label(composite_downloadInfo, SWT.NONE);
					label_filesize.setText("\u6587\u4ef6\u5927\u5c0f\uff1a");
					FormData label1LData = new FormData();
					label1LData.left = new FormAttachment(0, 1000, 12);
					label1LData.top = new FormAttachment(0, 1000, 53);
					label1LData.width = 61;
					label1LData.height = 21;
					label_filesize.setLayoutData(label1LData);
					label_filesize.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_downloadPro = new Label(composite_downloadInfo, SWT.NONE);
					label_downloadPro.setText("\u4e0b\u8f7d\u8fdb\u5ea6\uff1a");
					FormData label2LData = new FormData();
					label2LData.left = new FormAttachment(0, 1000, 12);
					label2LData.top = new FormAttachment(0, 1000, 83);
					label2LData.width = 61;
					label2LData.height = 21;
					label_downloadPro.setLayoutData(label2LData);
					label_downloadPro.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_downloadSpeed = new Label(composite_downloadInfo, SWT.NONE);
					label_downloadSpeed.setText("\u4e0b\u8f7d\u901f\u5ea6\uff1a");
					FormData label3LData = new FormData();
					label3LData.left = new FormAttachment(0, 1000, 12);
					label3LData.top = new FormAttachment(0, 1000, 113);
					label3LData.width = 61;
					label3LData.height = 21;
					label_downloadSpeed.setLayoutData(label3LData);
					label_downloadSpeed.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_threadNum = new Label(composite_downloadInfo, SWT.NONE);
					label_threadNum.setText("\u7ebf\u7a0b\u6570\u76ee\uff1a");
					FormData label4LData = new FormData();
					label4LData.left = new FormAttachment(0, 1000, 12);
					label4LData.top = new FormAttachment(0, 1000, 143);
					label4LData.width = 61;
					label4LData.height = 21;
					label_threadNum.setLayoutData(label4LData);
					label_threadNum.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_saveFilePath = new Label(composite_downloadInfo, SWT.NONE);
					label_saveFilePath.setText("\u5b58\u653e\u4f4d\u7f6e\uff1a");
					FormData label5LData = new FormData();
					label5LData.left = new FormAttachment(0, 1000, 12);
					label5LData.top = new FormAttachment(0, 1000, 173);
					label5LData.width = 61;
					label5LData.height = 21;
					label_saveFilePath.setLayoutData(label5LData);
					label_saveFilePath.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_fileUrl = new Label(composite_downloadInfo, SWT.NONE);
					label_fileUrl.setText("\u6570\u636e\u6765\u6e90\uff1a");
					FormData label6LData = new FormData();
					label6LData.left = new FormAttachment(0, 1000, 12);
					label6LData.top = new FormAttachment(0, 1000, 203);
					label6LData.width = 61;
					label6LData.height = 21;
					label_fileUrl.setLayoutData(label6LData);
					label_fileUrl.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_downloadState = new Label(composite_downloadInfo, SWT.NONE);
					label_downloadState.setText("\u4e0b\u8f7d\u72b6\u6001\uff1a");
					FormData label7LData = new FormData();
					label7LData.left = new FormAttachment(0, 1000, 12);
					label7LData.top = new FormAttachment(0, 1000, 234);
					label7LData.width = 61;
					label7LData.height = 21;
					label_downloadState.setLayoutData(label7LData);
					label_downloadState.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_filename2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label1LData1 = new FormData();
					label1LData1.left = new FormAttachment(0, 1000, 79);
					label1LData1.top = new FormAttachment(0, 1000, 23);
					label1LData1.width = 173;
					label1LData1.height = 18;
					label_filename2.setLayoutData(label1LData1);
					label_filename2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_filesize2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label2LData1 = new FormData();
					label2LData1.left = new FormAttachment(0, 1000, 79);
					label2LData1.top = new FormAttachment(0, 1000, 53);
					label2LData1.width = 173;
					label2LData1.height = 18;
					label_filesize2.setLayoutData(label2LData1);
					label_filesize2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_downloadSpeed2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label3LData1 = new FormData();
					label3LData1.left = new FormAttachment(0, 1000, 79);
					label3LData1.top = new FormAttachment(0, 1000, 113);
					label3LData1.width = 173;
					label3LData1.height = 18;
					label_downloadSpeed2.setLayoutData(label3LData1);
					label_downloadSpeed2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_threadNum2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label4LData1 = new FormData();
					label4LData1.left = new FormAttachment(0, 1000, 79);
					label4LData1.top = new FormAttachment(0, 1000, 143);
					label4LData1.width = 173;
					label4LData1.height = 18;
					label_threadNum2.setLayoutData(label4LData1);
					label_threadNum2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_saveFilePath2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label5LData1 = new FormData();
					label5LData1.left = new FormAttachment(0, 1000, 79);
					label5LData1.top = new FormAttachment(0, 1000, 173);
					label5LData1.width = 173;
					label5LData1.height = 18;
					label_saveFilePath2.setLayoutData(label5LData1);
					label_saveFilePath2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_fileUrl2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label6LData1 = new FormData();
					label6LData1.left = new FormAttachment(0, 1000, 79);
					label6LData1.top = new FormAttachment(0, 1000, 203);
					label6LData1.width = 173;
					label6LData1.height = 18;
					label_fileUrl2.setLayoutData(label6LData1);
					label_fileUrl2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
				{
					label_downloadState2 = new Label(composite_downloadInfo, SWT.NONE);
					FormData label7LData1 = new FormData();
					label7LData1.left = new FormAttachment(0, 1000, 79);
					label7LData1.top = new FormAttachment(0, 1000, 234);
					label7LData1.width = 173;
					label7LData1.height = 18;
					label_downloadState2.setLayoutData(label7LData1);
					label_downloadState2.setBackground(SWTResourceManager.getColor(181, 255, 255));
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.MIN);
		FillLayout shellLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
		shell.setLayout(shellLayout);
		setShellCenter(shell);
		MainFrame inst = new MainFrame(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setImage(SWTResourceManager.getImage("images/java_coffe.png"));
		shell.setText("\u591a\u7ebf\u7a0b\u4e0b\u8f7d\u5de5\u5177");
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) { // 这里这个其实很重要
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	// 窗口居中显示
	public static void setShellCenter(Shell shell) {
		int width = shell.getMonitor().getClientArea().width;
		int height = shell.getMonitor().getClientArea().height;
		int x = shell.getSize().x;
		int y = shell.getSize().y;
		if (x > width) {
			shell.getSize().x = width;
		}
		if (y > height) {
			shell.getSize().y = height;
		}
		shell.setLocation((width - x) / 2, (height - y) / 2);
	}

	// 新建任务
	private void toolItem_newWidgetSelected(SelectionEvent evt) {
		System.out.println("toolItem_new.widgetSelected, event=" + evt);
		NewTaskDialog taskDialog = new NewTaskDialog(this.getShell(), SWT.NULL);// this.getShell() -- getParent()
		downloadInfo = taskDialog.open();
		if (downloadInfo != null && downloadInfo.getMyResult()==1) {//等于1很重要！
			setFileProperty(downloadInfo);// 设置文件信息到右侧的显示面板上
			startTask(downloadInfo);// 开始任务
		}
	}

	// 开始新的任务
	public void startTask(DownloadInfo downloadInfo) {
		// 首先将下载的保存的文件的名称显示在左侧
		list_downloadList.add(downloadInfo.getSaveFileName());
		list_downloadList.setSelection(new String[] { downloadInfo.getSaveFileName() });
		// 插入到Map中
		downloadMap.put(downloadInfo.getSaveFileName(), downloadInfo);
		// 创建多线程调度类
		MultiThreadDownload multiThreadDownload = new MultiThreadDownload(downloadInfo, MainFrame.this);// this不行
		// 调用开始下载文件的方法
		multiThreadDownload.startDownFile();
	}

	// 初始化左侧的list列表
	private void initList() {
		if (downloadMap.size() != 0) {
			Set<String> listSet = downloadMap.keySet();
			String[] list = listSet.toArray(new String[0]);
			list_downloadList.setItems(list);
		}
	}

	// 程序运行时先加载保存的信息
	@SuppressWarnings("unchecked")
	private void iniDownloadData() {
		File dataFile = new File(".\\downloadinfos.data");
		try {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(dataFile));
			downloadMap = (HashMap<String, DownloadInfo>) oos.readObject();
			oos.close();
		} catch (Exception e) {
			// e.printStackTrace(); //如果文件不存在就会出现异常，但是不要紧，不要打印异常信息
			System.out.println("没有加载的数据！");
		}
	}

	// 保存下载的数据信息
	public void saveDownloadInfos() {
		System.out.println("saveDownloadInfos");
		File dataFile = new File(".\\downloadinfos.data");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
			oos.writeObject(downloadMap);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("保存数据出错了！");
		}
	}

	// 在窗口关闭之前保存数据
	private void thisWidgetDisposed(DisposeEvent evt) {
		System.out.println("this.widgetDisposed, event=" + evt);
		saveDownloadInfos();
	}

	// 根据选中的那个选项设置右侧的面板显示相应的文件属性
	private void list_downloadListWidgetSelected(SelectionEvent evt) {
		System.out.println("list_downloadList.widgetSelected, event=" + evt);
		String[] select = list_downloadList.getSelection();
		if (select != null && select.length != 0) {
			setFileProperty(downloadMap.get(select[0]));
//			setDownloadInfo(downloadMap.get(select[0]));//设置这个很重要的！保证在不同的地方看到的都是这个downloadInfo
			// 这个最好还是放在setFileProperty方法中，因为两个方法是都是要执行的！
		} else {
			list_downloadList.setSelection(new String[] {});
		}// TODO：有一个没有实现，那就是当点击的地方没有一个选项时，那么原来选中的地方就要变成没有被选中
	}

	// 删除一条下载信息
	private void toolItem_closeWidgetSelected(SelectionEvent evt) {
		System.out.println("toolItem_close.widgetSelected, event=" + evt);
		String[] select = list_downloadList.getSelection();
		if (select != null && select.length != 0) {
			list_downloadList.remove(select[0]);
			downloadMap.remove(select[0]);
			if (list_downloadList.getItemCount() > 0) {// 如果删除了一个，那么就要修改被选中的那个，设置成第一个
				list_downloadList.setSelection(0);
				setFileProperty(downloadMap.get(list_downloadList.getItem(0)));
//				setDownloadInfo(downloadMap.get(list_downloadList.getItem(0)));
			} else {
				iniFileProperty();// 如果删得没有了，那么就初始化
			}
		} else {
			new InfoDialog(getShell(), SWT.NULL, "请先选择要删除的下载项！", false).open();
		}
	}

	// 设置文件信息到右侧的显示面板上
	// 注意：如果什么都没有输入，然后又点击了确定，接着又关闭，这时会报错！
	private void setFileProperty(DownloadInfo downloadInfo) {
		if (downloadInfo != null) {
			setDownloadInfo(downloadInfo);
			
			label_downloadSpeed2.setText(downloadInfo.getDownSpeed() + " kb/s");
			label_downloadState2.setText(downloadInfo.isFinished() ? "下载完毕" : "正在下载...");
			label_filename2.setText(downloadInfo.getSaveFileName());
			label_fileUrl2.setText(downloadInfo.getUrl().toString());
			label_saveFilePath2.setText(downloadInfo.getSaveFilePath());
			label_threadNum2.setText(String.valueOf(downloadInfo.getThreadNum()));
			label_filesize2.setText(String.valueOf(Math.round(downloadInfo.getFileLength() / 1024 * 100) / 100.0f) + " KB");// 转换成KB为单位
			progressBar_downloadPro2.setSelection(downloadInfo.isFinished() ? 100 : downloadInfo.getDownProgress());
			
			label_downloadSpeed2.setToolTipText(downloadInfo.getDownSpeed() + " kb/s");
			label_downloadState2.setToolTipText(label_downloadState2.getText());
			label_filename2.setToolTipText(downloadInfo.getSaveFileName());
			label_fileUrl2.setToolTipText(downloadInfo.getUrl().toString());
			label_saveFilePath2.setToolTipText(downloadInfo.getSaveFilePath());
			label_threadNum2.setToolTipText(String.valueOf(downloadInfo.getThreadNum()));
			label_filesize2.setToolTipText(String.valueOf(Math.round(downloadInfo.getFileLength() / 1024 * 100) / 100.0f) + " KB");
			progressBar_downloadPro2.setToolTipText("已经下载了 " + progressBar_downloadPro2.getSelection() + " %");
		} else {
			iniFileProperty();
		}
	}

	// 初始化右侧的文件信息面板，基本上都是为空
	private void iniFileProperty() {
		label_downloadSpeed2.setText("");
		label_downloadState2.setText("");
		label_filename2.setText("");
		label_fileUrl2.setText("");
		label_saveFilePath2.setText("");
		label_threadNum2.setText("");
		label_filesize2.setText("");
		progressBar_downloadPro2.setSelection(0);

		label_downloadSpeed2.setToolTipText("");
		label_downloadState2.setToolTipText("");
		label_filename2.setToolTipText("");
		label_fileUrl2.setToolTipText("");
		label_saveFilePath2.setToolTipText("");
		label_threadNum2.setToolTipText("");
		label_filesize2.setToolTipText("");
		progressBar_downloadPro2.setToolTipText("");
	}

	// 显示一个关于的信息窗体
	private void toolItem_aboutWidgetSelected(SelectionEvent evt) {
		System.out.println("toolItem_about.widgetSelected, event=" + evt);
		String message = "\nAuthor: 胡家威\n";
		message += "Name: 多线程下载工具\n";
		message += "Version: 1.0\n";
		message += "TestURL:\n";
		message += "http://jwc.csu.edu.cn:8088/uploadfiles/\nfiles/cxjy04.doc\n";
		new AboutDialog(this.getShell(), SWT.NULL, message).open();
	}

	public void setList_downloadList(List list_downloadList) {
		this.list_downloadList = list_downloadList;
	}

	public Composite getComposite_downloadInfo() {
		return composite_downloadInfo;
	}

	public void setComposite_downloadInfo(Composite composite_downloadInfo) {
		this.composite_downloadInfo = composite_downloadInfo;
	}

	public ProgressBar getProgressBar_downloadPro2() {
		return progressBar_downloadPro2;
	}

	public void setProgressBar_downloadPro2(ProgressBar progressBar_downloadPro2) {
		this.progressBar_downloadPro2 = progressBar_downloadPro2;
	}

	public Label getLabel_downloadState2() {
		return label_downloadState2;
	}

	public void setLabel_downloadState2(Label label_downloadState2) {
		this.label_downloadState2 = label_downloadState2;
	}

	public Label getLabel_fileUrl2() {
		return label_fileUrl2;
	}

	public void setLabel_fileUrl2(Label label_fileUrl2) {
		this.label_fileUrl2 = label_fileUrl2;
	}

	public Label getLabel_saveFilePath2() {
		return label_saveFilePath2;
	}

	public void setLabel_saveFilePath2(Label label_saveFilePath2) {
		this.label_saveFilePath2 = label_saveFilePath2;
	}

	public Label getLabel_threadNum2() {
		return label_threadNum2;
	}

	public void setLabel_threadNum2(Label label_threadNum2) {
		this.label_threadNum2 = label_threadNum2;
	}

	public Label getLabel_downloadSpeed2() {
		return label_downloadSpeed2;
	}

	public void setLabel_downloadSpeed2(Label label_downloadSpeed2) {
		this.label_downloadSpeed2 = label_downloadSpeed2;
	}

	public HashMap<String, DownloadInfo> getDownloadMap() {
		return downloadMap;
	}

	public void setDownloadMap(HashMap<String, DownloadInfo> downloadMap) {
		this.downloadMap = downloadMap;
	}

	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public Label getLabel_filesize2() {
		return label_filesize2;
	}

	public void setLabel_filesize2(Label label_filesize2) {
		this.label_filesize2 = label_filesize2;
	}

	public Label getLabel_filename2() {
		return label_filename2;
	}

	public void setLabel_filename2(Label label_filename2) {
		this.label_filename2 = label_filename2;
	}

	public Label getLabel_downloadState() {
		return label_downloadState;
	}

	public void setLabel_downloadState(Label label_downloadState) {
		this.label_downloadState = label_downloadState;
	}

	public Label getLabel_fileUrl() {
		return label_fileUrl;
	}

	public void setLabel_fileUrl(Label label_fileUrl) {
		this.label_fileUrl = label_fileUrl;
	}

	public Label getLabel_saveFilePath() {
		return label_saveFilePath;
	}

	public void setLabel_saveFilePath(Label label_saveFilePath) {
		this.label_saveFilePath = label_saveFilePath;
	}

	public Label getLabel_threadNum() {
		return label_threadNum;
	}

	public void setLabel_threadNum(Label label_threadNum) {
		this.label_threadNum = label_threadNum;
	}

	public Label getLabel_downloadSpeed() {
		return label_downloadSpeed;
	}

	public void setLabel_downloadSpeed(Label label_downloadSpeed) {
		this.label_downloadSpeed = label_downloadSpeed;
	}

	public Label getLabel_downloadPro() {
		return label_downloadPro;
	}

	public void setLabel_downloadPro(Label label_downloadPro) {
		this.label_downloadPro = label_downloadPro;
	}

	public Label getLabel_filesize() {
		return label_filesize;
	}

	public void setLabel_filesize(Label label_filesize) {
		this.label_filesize = label_filesize;
	}

	public Label getLabel_filename() {
		return label_filename;
	}

	public void setLabel_filename(Label label_filename) {
		this.label_filename = label_filename;
	}

	public ToolItem getToolItem_about() {
		return toolItem_about;
	}

	public void setToolItem_about(ToolItem toolItem_about) {
		this.toolItem_about = toolItem_about;
		toolItem_about.setToolTipText("\u5173\u4e8e\u8fd9\u4e2a\u5de5\u5177");
		toolItem_about.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				toolItem_aboutWidgetSelected(evt);
			}
		});
	}

	public ToolItem getToolItem_close() {
		return toolItem_close;
	}

	public void setToolItem_close(ToolItem toolItem_close) {
		this.toolItem_close = toolItem_close;
		toolItem_close.setToolTipText("\u5220\u9664\u4e0b\u8f7d\u4efb\u52a1");
	}

	public ToolItem getToolItem_new() {
		return toolItem_new;
	}

	public void setToolItem_new(ToolItem toolItem_new) {
		this.toolItem_new = toolItem_new;
	}

	public ToolBar getToolBar_tools() {
		return toolBar_tools;
	}

	public void setToolBar_tools(ToolBar toolBar_tools) {
		this.toolBar_tools = toolBar_tools;
	}

	public List getList_downloadList() {
		return list_downloadList;
	}
}
