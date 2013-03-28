package com.rcp.wxh.actions;

import org.eclipse.jface.action.Action;

import com.rcp.wxh.pojo.TEmp;

/**
 * 抽象验证类
 * 所有action需要继承该抽象类以验证身份
 * 这样就完成了权限管理
 */
public abstract class ValidateStatusAction extends Action {

	public static TEmp user;

	public ValidateStatusAction() {
		if (user != null && user.getPriority() == TEmp.ADMINSTRATOR) // 如果管理员身份则该响应获得权限
			this.setEnabled(true);
		else
			this.setEnabled(false);
	}

}
