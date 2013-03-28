package com.yj.smarthome.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.HttpUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;

public class RefreshDeviceAction extends Action {

	public static final String ID = RefreshDeviceAction.class.getName();
	private IWorkbenchWindow window;

	public RefreshDeviceAction(IWorkbenchWindow window) {
		this.window = window;
		this.setText(SystemUtil.REFRESHDEVICIES);
		this.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.REFRESH));
	}

	@Override
	public void run() {
		//����Ҫ�ر����еı༭��
//		IEditorReference[] preferences = window.getActivePage().getEditorReferences();
//		for (int i = 0; i < preferences.length; i++) {
//			IEditorPart editorPart = preferences[i].getEditor(false);
//			if (editorPart!=null) {
//				editorPart.dispose();
////				editorPart.
//				editorPart = null;
//			}
//		}

		//��������Ҫ����Ƿ��ܹ������ϣ�
		//������Ӳ��ϣ������

		window.getActivePage().closeAllEditors(false);

		Job job = new Job("�����豸�б�") {//job�����ƻ��Ϊdialog�ı���
			//����һЩ�����ķ�����������Խ�������
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				//Ȼ��ʼ�����ļ�
				System.out.println("�����ļ�");
				//�������֮�󣬿�ʼ���ر�Ҫ���ļ� ��ʱ��������version.xml��state.xml�ļ�
				final List<String> files = new ArrayList<String>();
				//������ļ��Ǳ���Ҫ���ص�
				files.add(FileUtil.STATE_XML);
				//�ȶ�ȡ����������version�ļ�
				try {
					HttpUtil.downloadVersionXmlFile();
					XmlUtil.getDownloadFilesFromVersion(files);//��version��xml�ļ��еõ�Ҫ���ص��ļ��б�
				} catch (Exception e1) {
					e1.printStackTrace();
//			return;//���ﱨ����һ��Ҫ�˳�������Ҫ�˳�����
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
						Thread.sleep(200);//��ͣһ�£��鿴
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

}
