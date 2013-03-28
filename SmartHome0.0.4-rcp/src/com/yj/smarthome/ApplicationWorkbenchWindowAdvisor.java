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
		configurer.setShowCoolBar(true);//��ʾ������
//		configurer.setShowProgressIndicator(true);
//		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(true);//��ʾ״̬��
		configurer.setTitle("SmartHome 0.0.4");

//		configurer.getActionBarConfigurer().getCoolBarManager().getStyle();
//		configurer.createPageComposite(configurer.getWindow().getShell());

		//����Ӧ�ó�������
		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		//����ѡ�����ʽ�����Ǿ��εı߿򣬶��ǻ��ε�
		preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		//����͸��ͼ��ť��λ�ã�Ĭ�������Ͻǣ���Ϊ���������Ͻ�
		preferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, IWorkbenchPreferenceConstants.TOP_RIGHT);
		preferenceStore.setValue(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION, SWT.BOTTOM);
		preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);
	}
	
	

//	@Override
//	public void postWindowOpen() {
//		//�������֮��Ҫ�����ļ���
//		downloadFiles();
//	}

	private void downloadFiles() {
		System.out.println("�����ļ�");

		Job job = new Job("���������ļ�") {//job�����ƻ��Ϊdialog�ı���
			//����һЩ�����ķ�����������Խ�������
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				//�������֮�󣬿�ʼ���ر�Ҫ���ļ� ��ʱ��������version.xml��state.xml�ļ�
				final List<String> files = new ArrayList<String>();
				//����������ļ��Ǳ���Ҫ���ص�
				files.add(FileUtil.STATE_XML);
				//�ȶ�ȡ����������version�ļ�
				try {
					File versionxml = HttpUtil.downloadVersionXmlFile();
					XmlUtil.getDownloadFilesFromVersion(files);//��version��xml�ļ��еõ�Ҫ���ص��ļ��б�
				} catch (Exception e1) {
					e1.printStackTrace();
//					return;//���ﱨ����һ��Ҫ�˳�������Ҫ�˳�����
				}
				monitor.setCanceled(false);//�����Ա�ȡ����
				monitor.beginTask("�ף������ļ������У����Եȹ�......", files.size());
				for (int i = 0, size = files.size(); i < size; i++) {
//					if (monitor.isCanceled()) {//���ȡ���˾�ֹͣ������ʵ�����ǲ��ܹ�ȡ���ģ�
					//break;
//					}
					String filename = files.get(i);
					monitor.subTask("O(��_��)O~...���������ļ� " + filename);
					try {
						HttpUtil.downloadFile(FileUtil.DOWN_DIRECTORY + filename, filename);
					} catch (Exception e) {
//						e.printStackTrace();
						//������ʾ��
						if (i == files.size() - 1) {//��������һ���ļ�����ô���˳���
							return Status.OK_STATUS;//���ﲻһ����Ҫ�����ˣ�
						} else {
							monitor.subTask(e.getMessage());//��ʾ�ļ������ڻ�������ʧ��
							continue;
						}
					}//����ʾ��ʾ��Ϣ
					monitor.worked(1);
					try {
						Thread.sleep(500);//��ͣһ�£��鿴
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		//��Ӽ�����
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					//��ʾ�������
//					System.out.println("download ok");
//					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "������ʾ", "��ϲ������������ˣ���ǰ�������������µģ�");

					//�ļ��������֮����ʵ��Ҫ��ʼ��ȡ�豸��״̬ ��state.xml�ļ���
					try {
						XmlUtil.initDeviceState();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
		job.setPriority(Job.SHORT);
		job.setUser(true);// ֻ�����ó����û�����ſ��Կ����н�������dialog
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
			// ��ȡ Editorarea �����е� tabfolder ����
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
						// ����ϵͳ�Ҽ��˵�
						tabfolder.removeListener(SWT.MenuDetect, listeners[i]);
					}
				}
				Listener[] listeners2 = tabfolder.getListeners(SWT.DragDetect);
				if (listeners2 != null) {
					for (int i = 0; i < listeners2.length; i++) {
						// ���α༭��Ĭ�Ͽ��϶�������
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
