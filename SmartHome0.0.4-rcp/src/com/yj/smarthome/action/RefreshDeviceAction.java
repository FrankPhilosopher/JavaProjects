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
		//首先要关闭所有的编辑器
//		IEditorReference[] preferences = window.getActivePage().getEditorReferences();
//		for (int i = 0; i < preferences.length; i++) {
//			IEditorPart editorPart = preferences[i].getEditor(false);
//			if (editorPart!=null) {
//				editorPart.dispose();
////				editorPart.
//				editorPart = null;
//			}
//		}

		//这里首先要检测是否能够连接上！
		//如果连接不上，会出错！

		window.getActivePage().closeAllEditors(false);

		Job job = new Job("更新设备列表") {//job的名称会成为dialog的标题
			//还有一些其他的方法，或许可以进行设置
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				//然后开始下载文件
				System.out.println("下载文件");
				//窗体打开了之后，开始下载必要的文件 此时是两个，version.xml和state.xml文件
				final List<String> files = new ArrayList<String>();
				//下面的文件是必须要下载的
				files.add(FileUtil.STATE_XML);
				//先读取下载下来的version文件
				try {
					HttpUtil.downloadVersionXmlFile();
					XmlUtil.getDownloadFilesFromVersion(files);//从version的xml文件中得到要下载的文件列表
				} catch (Exception e1) {
					e1.printStackTrace();
//			return;//这里报错了一定要退出，可能要退出程序！
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
						Thread.sleep(200);//暂停一下，查看
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

}
