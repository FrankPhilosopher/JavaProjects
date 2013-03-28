package com.rcp.wxh.communicate;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.progress.UIJob;

/**
 * 用于更新UI界面的job对象
 * 主要是在与终端通信的时候 将获取到的信息同步到界面
 * 
 * @author wuxuehong 2011-11-20
 * 
 */
public class CommunicateUIJob extends UIJob {

	public CommunicateUIJob(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

		return null;
	}

}
