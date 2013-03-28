package com.yj.smarthome.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.User;
import com.yj.smarthome.util.AutomLoginUtil;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;

/**
 * 登录对话框
 * 
 * @author yinger
 * 
 */
public class LoginDialog extends TitleAreaDialog {

	private Text text_name;
	private Text text_pwd;
	private StackLayout stackLayout;
	private Composite loginComposite;
	private Combo combo_type;
	private Canvas canvas;
	private Button okButton;
	private Button cancelButton;
	private Composite container;
	private boolean waiting = false;//是否处于登录等待阶段
	private LoginWaitingThread loginWaitingThread;//登录等待线程

	private AutomLoginUtil alu;
	private User user;
	private Button button_remember;
	private Button button_autologin;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
//		setErrorMessage(MessageUtil.LoginDialog_this_errorMessage);
		setHelpAvailable(false);

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("\u8BF7\u8F93\u5165\u7528\u6237\u540D\u548C\u5BC6\u7801\uFF0C\u5E76\u4FDD\u8BC1\u670D\u52A1\u5668\u53C2\u6570\u8BBE\u7F6E\u6B63\u786E\uFF01");
//		setTitleImage(ResourceManager.getPluginImage("com.yj.smarthome0.0.3", "icons/login/login.jpg"));
		setTitle("\u6B22\u8FCE\u4F7F\u7528");

		Composite area = (Composite) super.createDialogArea(parent);
		//下面这种设置背景图片的方式是没有用的！
//		area.setBackgroundImage(ResourceManager.getPluginImage("com.yj.smarthome0.0.3", "icons/login/login.jpg"));

		container = new Composite(area, SWT.NONE);
		GridData gd_container = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_container.widthHint = 450;
		gd_container.heightHint = 163;
		container.setLayoutData(gd_container);
		stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		loginComposite = new Composite(container, SWT.BORDER);
		stackLayout.topControl = loginComposite;
		loginComposite.setLayout(null);

		Label label = new Label(loginComposite, SWT.NONE);
		label.setBounds(21, 11, 61, 19);
		label.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label.setText("\u7528\u6237\u540D");

		text_name = new Text(loginComposite, SWT.BORDER);
//		text_name.setText("a0001");
		text_name.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {//用户名和密码不能为空
				if (null == text_name.getText().trim() || "".equalsIgnoreCase(text_name.getText().trim())) {
					setErrorMessage("用户名不能为空");
				}
			}
		});
		text_name.setBounds(89, 12, 226, 23);

		Label label_1 = new Label(loginComposite, SWT.NONE);
		label_1.setBounds(21, 54, 61, 19);
		label_1.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label_1.setText("\u5BC6\u3000\u7801");

		text_pwd = new Text(loginComposite, SWT.BORDER);
//		text_pwd.setText("123456");
		text_pwd.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_pwd.setEchoChar('*');
		text_pwd.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (null == text_pwd.getText().trim() || "".equalsIgnoreCase(text_pwd.getText().trim())) {
					setErrorMessage("密码不能为空");
				}
			}
		});
		text_pwd.setBounds(89, 50, 226, 23);

		button_remember = new Button(loginComposite, SWT.CHECK);
		button_remember.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		button_remember.setBounds(352, 10, 73, 20);
		button_remember.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		button_remember.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//记住密码
//				alu.setRePwd(button_remember.getSelection(), user);
			}
		});
		button_remember.setText("\u8BB0\u4F4F\u5BC6\u7801");

		button_autologin = new Button(loginComposite, SWT.CHECK);
		button_autologin.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		button_autologin.setBounds(352, 50, 73, 20);
		button_autologin.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		button_autologin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//自动登录
//				alu.setAutLogin(button_autologin.getSelection(), user);
				if (button_autologin.getSelection()) {
					button_remember.setSelection(true);
				}
			}
		});
		button_autologin.setText("\u81EA\u52A8\u767B\u5F55");

		combo_type = new Combo(loginComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_type.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		combo_type.setFont(ResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		combo_type.setItems(new String[] { SystemUtil.GATEWAY_NAME, SystemUtil.SERVER_NAME });//TODO：这里如何进行国际化！
		combo_type.setText(SystemUtil.GATEWAY_NAME);
//		combo_type.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {//选择连接方式
//			}
//		});
		combo_type.setBounds(89, 93, 100, 26);

		Button settingButton = new Button(loginComposite, SWT.NONE);
		settingButton.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
//		settingButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SETTING_ICON));
		settingButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SETTING_ICON));
		settingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//设置服务器参数
				ServerSettingDialog dialog = new ServerSettingDialog(new Shell());
				dialog.open();
			}
		});
		settingButton.setBounds(195, 91, 118, 27);
		settingButton.setText("\u8BBE\u7F6E\u670D\u52A1\u5668\u53C2\u6570");

		Label label_2 = new Label(loginComposite, SWT.NONE);
		label_2.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label_2.setBounds(21, 94, 61, 17);
		label_2.setText("\u8FDE\u63A5\u65B9\u5F0F");

		canvas = new Canvas(container, SWT.BORDER);

		Label lblWaiting = new Label(canvas, SWT.NONE);
		lblWaiting.setBounds(133, 77, 136, 11);
//		lblWaiting.setText(MessageUtil.LoginDialog_lblWaiting_text);
//		lblWaiting.setBackgroundImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LOGIN_WAITING));
		lblWaiting.setBackgroundImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LOGIN_WAITING));

//		initValues();//这个位置不合适，因为方法中用到了okbutton，但是他还没有创建

		return area;
	}

	//初始化界面中的数据和选项
	private void initValues() {
		alu = new AutomLoginUtil();
		try {
			user = (User) alu.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			user = new User();
		} else {
			text_name.setText(user.getName());
			if (user.getLogin() == AutomLoginUtil.RemPwd) {
				button_remember.setSelection(true);
				text_pwd.setText(user.getPassword());
			} else if (user.getLogin() == AutomLoginUtil.AutoLogin) {
				button_remember.setSelection(true);
				button_autologin.setSelection(true);
				text_pwd.setText(user.getPassword());
				//这里要进入自动登录
				buttonPressed(IDialogConstants.OK_ID);
			}
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			//点击登录了，更改显示
			//TODO:登录这里有个问题，如果能够连接上，这里执行很快，但是如果不能够连接上，这里界面显示不过来
			stackLayout.topControl = canvas;
			container.layout();
			okButton.setEnabled(false);
			setErrorMessage(null);//之前可能有了错误提示，这时要将错误提示清空，不能使空字符串，不然还是有错误图标
			setMessage("正在很努力的登录中，请耐心等待......");
			waiting = true;

			String type = combo_type.getText();
			String name = text_name.getText().trim();
			String pwd = text_pwd.getText().trim();
			//为了解决界面假死的状况，开启一个新的线程执行与服务器的连接
			new LoginThread(type, name, pwd).start();
		}

		if (IDialogConstants.CANCEL_ID == buttonId) {
			if (waiting) {//如果是在登录等待中点击了取消，那么就回到登录界面
				backToLogin();
			} else {
				cancelPressed();//
			}
		}
	}

	private void backToLogin() {
		stackLayout.topControl = loginComposite;
		container.layout();
		okButton.setEnabled(true);
		waiting = false;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		okButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OK_ICON));
		okButton.setText("\u767B\u5F55");
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancelButton.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		cancelButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CANCLE_ICON));
		cancelButton.setText("\u53D6\u6D88");

		//两个image效果不一样的
//		okButton.setBackgroundImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/button/btn_normal.png"));
//		okButton.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/button/btn_normal.png"));

		initValues();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(456, 305);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\u767B\u5F55");
		newShell.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		//使登录界面居中显示
		Display display = getParentShell().getDisplay();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = newShell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		newShell.setLocation(x, y);
	}

	//登录线程
	class LoginThread extends Thread {
		private String type;
		private String name;
		private String pwd;

		public LoginThread(String type, String name, String pwd) {
			this.type = type;
			this.name = name;
			this.pwd = pwd;
			this.setName("登录线程");
		}

		@Override
		public void run() {
			//首先确定用户选择的连接方式
			try {
				//注意连接的释放！如果是第二次点击登录，要保证只是与服务器建立了一个连接
				//这一部分过于繁琐，应该放在另外一个地方，用于建立连接！
				SystemUtil.buildCommunication(type);
			} catch (Exception e) {
				e.printStackTrace();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						setErrorMessage("连接失败，请检查服务器参数设置");
						backToLogin();//回到登陆界面
					}
				});
				return;
			}
			//然后获取用户名和密码，并封装成数据包发送出去
			byte[] data = ProtocolUtil.packCheckLogin(name, pwd);
			try {
//				Thread.sleep(1000);//TODO：为了让工具显示信息
				SystemUtil.communication.sendData(data);
			} catch (Exception e) {
				e.printStackTrace();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						setErrorMessage("信息发送失败，请尝试重新登录");
						backToLogin();//回到登陆界面
					}
				});
				return;
			}
			//这里要处于阻塞中，等待服务器返回的信息
			//可能用户点击了登录，后来又取消了，这个时候不要重新启动新的线程
			//这里进行一次判断，保证登录等待线程只有一个！
			if (loginWaitingThread == null) {
				loginWaitingThread = new LoginWaitingThread("登录等待线程");//这里是重点
				loginWaitingThread.start();
			}

		}
	}

	//登录等待线程
	//问题：几次登录就会有几个这个线程！并且一直存在着
	class LoginWaitingThread extends Thread {

		public LoginWaitingThread(String name) {
			this.setName(name);
		}

		@Override
		public void run() {
//			if (waiting) {
			synchronized (SystemUtil.MESSAGE) {//锁住 message
				while ("".equals(SystemUtil.MESSAGE.toString())) {//没有信息  //while? if?
					try {
						SystemUtil.MESSAGE.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (SystemUtil.MESSAGE.toString().equalsIgnoreCase(SystemUtil.LOGIN_OK)) {//如果成功的话就没有错误内容
				System.out.println("login");
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						SystemUtil.MESSAGE = new StringBuffer("");//将信息设为空
						//设置登录的用户
						user = new User(text_name.getText().trim(), text_pwd.getText().trim());
						if (button_remember.getSelection() == true) {
							user.setLogin(AutomLoginUtil.RemPwd);
						}
						if (button_autologin.getSelection() == true) {
							user.setLogin(AutomLoginUtil.AutoLogin);
						}
						SystemUtil.LOGIN_USER = user;
						try {
							alu.writeObject(user);
						} catch (Exception e) {
							e.printStackTrace();//保存时可能出错
						}
						okPressed();
						return;//登录成功了，等待线程才可以返回结束
					}
				});
			} else {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						setErrorMessage(SystemUtil.MESSAGE.toString());
						SystemUtil.MESSAGE = new StringBuffer("");//将信息设为空
						backToLogin();//返回到登录界面
						return;//这里不能return，不然线程就结束了
					}
				});
			}
		}
//		}
	}

}
