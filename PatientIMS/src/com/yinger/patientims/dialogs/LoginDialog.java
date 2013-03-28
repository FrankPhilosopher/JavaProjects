package com.yinger.patientims.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yinger.patientims.model.Doctor;

/**
 * 登录对话框继承标题区域对话框（TitleAreaDialog）
 * 
 */

// TitleAreaDialog : A dialog that has a title area for displaying a title and
// an image as well as a common area for
// displaying a description, a message, or an error message.
public class LoginDialog extends TitleAreaDialog {

	private Text text_username;
	private Text text_password;
	private Doctor doctor = new Doctor();//首先构造一个Doctor

	public LoginDialog(Shell parentShell) {
		super(parentShell);
		//不能在这里对shell进行操作，因为这里shell还没有创建！		
	}

	// 这个方法是用来设置dialog里面的内容，实际上dialog上半部分的内容
	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent); // 利用父类的方法！
		// 设置dialog上的标题
		setTitle("Add Your Login Information");
		// 设置标题下面的信息提示
		// setMessage("Attention:The textfield must't be blank!");
		setMessage("Attention: You just have three times to try!", IMessageProvider.INFORMATION);// 有了第二个参数就可以显示一个信息的图标
		// Minimal interface to a message provider. Used for dialog pages
		// which can provide a message with an icon.
		return contents;
	}

	// 这个方法也是用来设置dialog里面的内容，但是设置的是dialog下半部分的内容
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));// (int numColumns,
										// boolean
										// makeColumnsEqualWidth)
		// composite.setLayout(new
		// GridLayout());//报错！原因是引错了包！一定要是引入swt中的GridLayout
		// The method setLayout(Layout) in the type Composite is not
		// applicable for the arguments (GridLayout)
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL); // 充满水平方向
		composite.setLayoutData(layoutData);

		Label usernameLabel = new Label(composite, SWT.NONE);
		usernameLabel.setText("UserName：");
		text_username = new Text(composite, SWT.BORDER);
		text_username.setLayoutData(layoutData);

		Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setText("Password：");
		text_password = new Text(composite, SWT.BORDER);
		text_password.setLayoutData(layoutData);
		text_password.setEchoChar('*'); // 密码需要设置为显示 *

		return composite;
	}

	// 从Dialog类中继承过来的方法，用于处理按键事件，并调用其他的方法使窗口open方法有个返回值
	// 注意：这里是不要对登录进行实际的验证的！而只是在非空情况下返回用户按下的键！
	// Notifies that this dialog's button with the given id has been pressed.
	@Override
	protected void buttonPressed(int buttonId) {
		// 单击 OK
		if (IDialogConstants.OK_ID == buttonId) {
			if (!checkLogin()) {//如果填写的内容为空，则修改提示信息为一个错误信息，同时登录窗口不会关闭！
				return;
			}
			setLoginUser();//如果填写的内容不为空，那么将内容设置到doctor上面，并让窗体返回OK键！
			okPressed();// 向父窗体通知登录窗口返回的是OK按钮被按下，也就是窗口的返回值是OK键
			// Dialog :Notifies that the ok button of this dialog has been pressed.
			// 总结：如果调用了okPressed或者cancelPressed方法，那么对话框就要返回了，对话框会被dispose
			// 建议对话框和父窗体之间的通信可以在对话框中保存一个相应的对象，这样就可以在对话框dispose之前将那个对象的内容设置好！
			return;
		}
		// 单击 Cancel
		if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();// Notifies that the cancel button of this dialog has been pressed.
		}
	}

	// 符合要求，设置登录的用户
	private void setLoginUser() {
//		doctor = new Doctor();
		doctor.setUsername(text_username.getText());
		doctor.setPassword(text_password.getText());
	}

	// 检查登录信息，判断是否信息有效
	private boolean checkLogin() {
		String username = text_username.getText();
		String password = text_password.getText();
		// 不符合要求，显示错误信息并返回
		if (username == null || username.equals("") || password == null || password.equals("")) {
			setErrorMessage("Error:UserName and Password must'n be blank!");
			return false;
		}
		return true;
	}

	// 从Window类中继承过来的方法，用于在窗体显示时设置窗体的显示信息
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("User Login");
		newShell.setSize(400, 220);// 注意这里的高度，高度一定要足够的大，否则按钮可能就会看不到
		newShell.setLocation(400, 300);
		// newShell.setImage(new Image(null, "/icons/Home.ico"));
//		newShell.setImage(getDefaultImage());// 设置图片失败，默认图片是系统默认的那个难看的程序图标
//		newShell.setImage(new Image(null, System.getProperty("user.dir")+"\\icons\\User.ico"));
		//路径错了： E:\Java_Study\Eclipse\Eclipse for rcp\eclipse_rcp\icons\User.ico
//		newShell.setImage(new Image(Display.getDefault(), ClassLoader.getSystemResourceAsStream("icons/User.ico")));
		//这样还是不行，display是null
	}

	// 从Window类中继承过来的方法，可以用来改变dialog的窗体的样式
	@Override
	protected int getShellStyle() {
		return super.getShellStyle();
		// return super.getShellStyle() | SWT.MAX | SWT.MIN; // 可以最大化和最小化
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
