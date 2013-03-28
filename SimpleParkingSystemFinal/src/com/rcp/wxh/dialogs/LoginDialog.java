package com.rcp.wxh.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import parkingsystem.Activator;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.actions.ValidateStatusAction;
import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.resource.Messages;
import com.rcp.wxh.service.impl.EmpService;
import com.rcp.wxh.utils.AutomLoginUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 登录对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class LoginDialog extends Dialog {

	protected Object result = 0;
	protected Shell shell;

	private StackLayout stackLayout; // 卡片布局对象

	private Composite composite; // 登陆框总面板
	private Composite composite_1; // 登录面板
	private Composite composite_2; // 登录等待 面板

	private Text text; // 用户名
	private Text text_1; // 密码
	private Label label_3; // 消息提示标签

	private Button button; // 复选框 记住密码
	private Button button_1; // 复选框 自动登录
	private Button button_4;// 取消登录按钮

	private TEmp emp = null;;

	private boolean isAutomLogin = false;

	private boolean isCancle = false; // 是否取消登录

	private AutomLoginUtil alu;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public LoginDialog(Shell parent, int style) {
		super(parent, style);
		setText(Messages.LOGIN_TITLE);

		// emp 不为空 则表示需要已经记住了用户名密码
		// priority 0-表示 1-表示需要自动登录
		alu = new AutomLoginUtil();
		emp = (TEmp) alu.readObject();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		Display display = getParent().getDisplay();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);
		shell.open();
		shell.layout();
		// 自动登录控制
		if (emp != null && alu.isAutLogin(emp)) {
			loginAction();
//				isAutomLogin = true;
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/alt_window_16.gif"));
		shell.setSize(431, 343);
		shell.setText(Messages.LOGIN_TITLE);
		shell.setLayout(null);

		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		Label lblLogoImage = new Label(shell, SWT.BORDER);
		lblLogoImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/LOGO.JPG"));
//		lblLogoImage.setImage(Activator.getImageDescriptor("icons/LOGO.jpg").createImage());
		lblLogoImage.setBounds(10, 10, 405, 101);
//		lblLogoImage.setText("LOGO image");

		composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 117, 405, 183);
		stackLayout = new StackLayout();
		composite.setLayout(stackLayout);

		composite_1 = new Composite(composite, SWT.BORDER);
		composite_2 = new Composite(composite, SWT.BORDER);

		Label label = new Label(composite_1, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("华文新魏", 12, SWT.NORMAL));
		label.setText(Messages.LOGIN_USERNAME);
		label.setBounds(42, 31, 79, 18);

		text = new Text(composite_1, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(162, 28, 182, 22);
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296) { // 如果是回车
					loginAction();
				}
			}
		});
		if (emp != null && alu.isRePwd(emp))
			text.setText(emp.getOperatorid()); // 设置员工ID

		Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("华文新魏", 12, SWT.NORMAL));
		label_1.setText(Messages.LOGIN_PASSWORD);
		label_1.setBounds(42, 74, 79, 18);

		label_3 = new Label(composite_1, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_3.setFont(SWTResourceManager.getFont("华文新魏", 9, SWT.NORMAL));
		label_3.setBounds(40, 151, 152, 22);

		text_1 = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		if (emp != null && alu.isRePwd(emp))
			text_1.setText(emp.getPassword());
		text_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296) { // 如果是回车
//					MessageDialog.openWarning(shell, "提示", "别懒啦，你就点击按钮吧!");
					loginAction();
				}
			}
		});
		text_1.setBounds(162, 70, 182, 22);

		button = new Button(composite_1, SWT.CHECK);
		button.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		button.setText(Messages.LOGIN_REMPSW);
		button.setBounds(42, 114, 93, 16);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 记住密码 复选框
				if (!button.getSelection()) {
					button_1.setSelection(false);
				}
			}
		});
		if (emp != null && alu.isRePwd(emp)) {
			button.setSelection(true);
		}

		button_1 = new Button(composite_1, SWT.CHECK);
		button_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		button_1.setText(Messages.LOGIN_AUTOLOGIN);
		button_1.setBounds(161, 114, 93, 16);
		if (emp != null && alu.isAutLogin(emp)) {
			button_1.setSelection(true);
		}
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 自动登录按钮 复选框
				if (button_1.getSelection()) {
					button.setSelection(true); // 如果需要自动登录 则需要记住密码
				}
			}
		});

		Button button_2 = new Button(composite_1, SWT.RIGHT);
		button_2.setAlignment(SWT.CENTER);
		button_2.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/login.ico"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 登录按钮
				loginAction();
			}
		});
		button_2.setText(Messages.LOGIN_CONFIRM);
		button_2.setBounds(198, 151, 72, 22);

		Button button_3 = new Button(composite_1, SWT.NONE);
		button_3.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/cancel_login.ico"));
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 取消按钮
				shell.dispose();
			}
		});
		button_3.setText(Messages.LOGIN_CANCEL);
		button_3.setBounds(302, 151, 72, 22);

		button_4 = new Button(composite_2, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 取消登录按钮
				isCancle = true;
				stackLayout.topControl = composite_1;
				composite.layout();
			}
		});
		button_4.setBounds(165, 123, 72, 22);
		button_4.setText(Messages.LOGIN_CANCELLOGIN);

		Label label_2 = new Label(composite_2, SWT.NONE);
		label_2.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/logining.gif"));// swt支持gif，这里设置gif会看到动态的登录效果
		label_2.setBounds(133, 77, 136, 22);

		stackLayout.topControl = composite_1;
		composite.layout();
	}

	/**
	 * 登录响应
	 */
	public void loginAction() {
		isCancle = false;
		final String name = text.getText().trim();// 用户名
		final String password = text_1.getText().trim();// 密码
		if (name.length() == 0 || password.length() == 0) {
			label_3.setText("用户名或密码不能为空!");
			return;
		}
		stackLayout.topControl = composite_2;
		composite.layout();
		
		result = 1; // 验证成功
		shell.dispose();

		
		//yinger  为了测试删除了
		// 点击了一次登录就会启动一个线程去执行  //TODO：yinger  注意：在登录时如果点击了登录之后又点击取消登录会报错！但是没有大碍！
//		new Thread(new Runnable() {// 这里利用线程是为了处理用户多次点击登录的事件
//					public void run() {
//						EmpService emps = new EmpService();
//						TEmp temp = null;
//						try {
//							temp = emps.getEmpByNamePwd(name, password); // 数据库异常
//						} catch (Exception e) {
//							shell.getDisplay().asyncExec(new Runnable() {
//								public void run() {
//									stackLayout.topControl = composite_1;
//									composite.layout();
//									label_3.setText("登录失败!");
//								}
//							});
//							return;
//						}
//						if (isCancle)
//							return; // 如果取消登录了则返回
//						if (temp == null) { // 验证失败
//							// 返回第一个卡片界面
//							shell.getDisplay().asyncExec(new Runnable() {
//								public void run() {
//									stackLayout.topControl = composite_1;
//									composite.layout();
//									label_3.setText("登录失败!");
//								}
//							});
//						} else {
//							ValidateStatusAction.user = temp;// TODO：yinger 登录成功就要设置user
//							result = 1; // 验证成功
//							shell.getDisplay().asyncExec(new Runnable() {
//								public void run() {
//									if (!isAutomLogin) { // 当不是自动登录 则可能需要对密码信息保存处理
//										if (emp == null)
//											emp = new TEmp();
//										emp.setOperatorid(name);
//										emp.setPassword(password);
//										alu.setRePwd(button.getSelection(), emp);
//										alu.setAutLogin(button_1.getSelection(), emp);
//										alu.writeObject(emp);
//									}
//									shell.dispose(); // 关闭窗口
//								}
//							});
//						}
//					}
//				}).start();

	}

}
