package com.yj.smarthome;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.part.EditorPart;

import com.yj.smarthome.skin.LookAndFeel;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.HttpUtil;
import com.yj.smarthome.util.XmlUtil;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

//	@Override
//	public void openIntro() {
//		super.openIntro();// IWorkbenchPreferences.SHOW_INTRO is true
//	}
//
//	@Override
//	public void createWindowContents(Shell shell) {
//		super.createWindowContents(shell);
//	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1000, 700));
		configurer.setShowMenuBar(false);
		configurer.setShowCoolBar(true);//显示工具栏
//		configurer.setShowProgressIndicator(true);
//		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(true);//显示状态栏
		configurer.setTitle("SmartHome 0.0.4");

//		configurer.getActionBarConfigurer().getCoolBarManager().getStyle();
//		configurer.createPageComposite(configurer.getWindow().getShell());

		//定制应用程序的外观
		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		//设置选项卡的样式，不是矩形的边框，而是弧形的
		preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		//设置透视图按钮的位置，默认是左上角，改为放置在右上角
		preferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, IWorkbenchPreferenceConstants.TOP_RIGHT);
		preferenceStore.setValue(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION, SWT.BOTTOM);
		preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);
	}
	
	

//	@Override
//	public void postWindowOpen() {
//		//窗体打开了之后，要下载文件！
//		downloadFiles();
//	}

	private void downloadFiles() {
		System.out.println("下载文件");

		Job job = new Job("下载配置文件") {//job的名称会成为dialog的标题
			//还有一些其他的方法，或许可以进行设置
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				//窗体打开了之后，开始下载必要的文件 此时是两个，version.xml和state.xml文件
				final List<String> files = new ArrayList<String>();
				//下面的两个文件是必须要下载的
				files.add(FileUtil.STATE_XML);
				//先读取下载下来的version文件
				try {
					File versionxml = HttpUtil.downloadVersionXmlFile();
					XmlUtil.getDownloadFilesFromVersion(files);//从version的xml文件中得到要下载的文件列表
				} catch (Exception e1) {
					e1.printStackTrace();
//					return;//这里报错了一定要退出，可能要退出程序！
				}
				monitor.setCanceled(false);//不可以被取消！
				monitor.beginTask("亲，各种文件下载中，请稍等哈......", files.size());
				for (int i = 0, size = files.size(); i < size; i++) {
//					if (monitor.isCanceled()) {//如果取消了就停止，但是实际上是不能够取消的！
					//break;
//					}
					String filename = files.get(i);
					monitor.subTask("O(∩_∩)O~...正在下载文件 " + filename);
					try {
						HttpUtil.downloadFile(FileUtil.DOWN_DIRECTORY + filename, filename);
					} catch (Exception e) {
//						e.printStackTrace();
						//报错提示！
						if (i == files.size() - 1) {//如果是最后一个文件，那么就退出吧
							return Status.OK_STATUS;//这里不一定就要返回了！
						} else {
							monitor.subTask(e.getMessage());//提示文件不存在或者下载失败
							continue;
						}
					}//不显示提示信息
					monitor.worked(1);
					try {
						Thread.sleep(500);//暂停一下，查看
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		//添加监听器
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					//提示下载完成
//					System.out.println("download ok");
//					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "下载提示", "恭喜您，下载完成了，当前您的配置是最新的！");

					//文件下载完成之后，其实还要开始读取设备的状态 即state.xml文件！
					try {
						XmlUtil.initDeviceState();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
		job.setPriority(Job.SHORT);
		job.setUser(true);// 只有设置成了用户级别才可以看到有进度条的dialog
		job.schedule();
	}

	public void setEditorTabFolderColor(Color color) {
		if (getWindowConfigurer().getWindow() == null) {
			return;
		}
		if (getWindowConfigurer().getWindow().getActivePage() == null) {
			return;
		}
		WorkbenchPage page = (WorkbenchPage) getWindowConfigurer().getWindow().getActivePage();
		Composite client = page.getClientComposite();
		Control[] children = client.getChildren();
		Composite child = (Composite) children[0];
		Control[] controls = child.getChildren();
		for (final Control control : controls) {
			// 获取 Editorarea 区域中的 tabfolder 对象
			if (control instanceof CTabFolder) {
				control.setBackground(color);
			}
		}
	}

	public void setEditorAreaBG(Image image) {
		if (getWindowConfigurer().getWindow() == null) {
			return;
		}
		if (getWindowConfigurer().getWindow().getActivePage() == null) {
			return;
		}
		WorkbenchPage page = (WorkbenchPage) getWindowConfigurer().getWindow().getActivePage();
		Composite client = page.getClientComposite();
		Control[] children = client.getChildren();
		Composite child = (Composite) children[0];
		Control[] controls = child.getChildren();
		for (final Control control : controls) {
			if (control instanceof CTabFolder) {
				CTabFolder tabfolder = (CTabFolder) control;
				Listener[] listeners = tabfolder.getListeners(SWT.MenuDetect);
				if (listeners != null) {
					for (int i = 0; i < listeners.length; i++) {
						// 屏蔽系统右键菜单
						tabfolder.removeListener(SWT.MenuDetect, listeners[i]);
					}
				}
				Listener[] listeners2 = tabfolder.getListeners(SWT.DragDetect);
				if (listeners2 != null) {
					for (int i = 0; i < listeners2.length; i++) {
						// 屏蔽编辑器默认可拖动的属性
						tabfolder.removeListener(SWT.DragDetect, listeners2[i]);
					}
				}
				tabfolder.setBackgroundImage(image);
				tabfolder.setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

	public void addPartListener(final Color color) {
		getWindowConfigurer().getWindow().getActivePage().addPartListener(new IPartListener() {
			public void partActivated(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partClosed(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partOpened(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}
		});
	}

	public void setToorbarBG(Image timage) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();
			if (clazz.endsWith("CBanner")) {
				((Composite) childrens[i]).setBackgroundImage(timage);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

//	public void setMenuBG(Image mimage) {
//		org.eclipse.jface.action.MenuManager mm = (MenuManager) ApplicationActionBarAdvisor.getDefault().getMenubar();
//		Menu menu = mm.getMenu();
//		invoke("setBackgroundImage", menu, new Class[] { Image.class }, new Image[] { mimage });
//
//	}
//
//	Object invoke(String methodName, Object object, Class<?>[] argsTypes, Object[] args) {
//		Object result = null;
//
//		try {
//			Method m = object.getClass().getDeclaredMethod(methodName, argsTypes);
//			m.setAccessible(true);
//			result = m.invoke(object, args);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	public void setProgressIndicatorBG(Image image) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();

			if (clazz.endsWith("ProgressRegion$1")) {
				((Composite) childrens[i]).setBackgroundImage(image);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

	public void setStausLineBG(Image image) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();

			if (clazz.endsWith("StatusLine")) {
				((Composite) childrens[i]).setBackgroundImage(image);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

	public void postWindowOpen() {
//		getWindowConfigurer().getWindow().getShell().setMaximized(true);
//		setMenuBG(LookAndFeel.getDefault().getMenuImage());
		setToorbarBG(LookAndFeel.getDefault().getToolBarImage());
		setEditorAreaBG(LookAndFeel.getDefault().getContentBgImage());
		setEditorTabFolderColor(LookAndFeel.getDefault().getTabFolderColor());
		addPartListener(LookAndFeel.getDefault().getTabFolderColor());
		setStausLineBG(LookAndFeel.getDefault().getToolBarImage());
		setProgressIndicatorBG(LookAndFeel.getDefault().getToolBarImage());
		getWindowConfigurer().getWindow().getShell().setBackground(LookAndFeel.getDefault().getShellColor());
		getWindowConfigurer().getWindow().getShell().setBackgroundMode(SWT.INHERIT_FORCE);
		getWindowConfigurer().getWindow().getShell().redraw();

//		downloadFiles();
	}

}
