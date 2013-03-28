package cn.edu.csu.dbhospital.dialog;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.RcpTask;
import org.eclipse.wb.swt.ResourceManager;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.db.TWorkerManager;
import cn.edu.csu.dbhospital.pojo.TWorker;
import cn.edu.csu.dbhospital.util.AutomLoginUtil;
import cn.edu.csu.dbhospital.util.MD5Util;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * ��¼�Ի���
 * 
 * @author hjw
 * 
 */
public class LoginDialog extends TitleAreaDialog {

	private Composite container;
	private StackLayout stackLayout;
	private Composite loginComposite;
	private Canvas canvas;
	private Text text_name;
	private Text text_pwd;
	private Button okButton;
	private Button cancelButton;
	private Button button_remember;
	private Button button_autologin;

	private boolean waiting = false;// �Ƿ��ڵ�¼�ȴ��׶�
	private AutomLoginUtil alu;// �Զ���¼������
	private TWorker worker;// ��¼������Ա
	private String name;
	private String pwd;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("\u8BF7\u8F93\u5165\u7528\u6237\u540D\u548C\u5BC6\u7801");
		setTitle("\u6B22\u8FCE\u4F7F\u7528");

		Composite area = (Composite) super.createDialogArea(parent);

		container = new Composite(area, SWT.NONE);
		GridData gd_container = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_container.widthHint = 345;
		gd_container.heightHint = 149;
		container.setLayoutData(gd_container);
		stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		loginComposite = new Composite(container, SWT.BORDER);
		stackLayout.topControl = loginComposite;
		loginComposite.setLayout(null);

		Label label = new Label(loginComposite, SWT.NONE);
		label.setBounds(21, 11, 61, 19);
		label.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		label.setText("\u7528\u6237\u540D");

		text_name = new Text(loginComposite, SWT.BORDER);
		text_name.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		text_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {// �û��������벻��Ϊ��
				if (null == text_name.getText().trim() || "".equalsIgnoreCase(text_name.getText().trim())) {
					setErrorMessage("�û�������Ϊ��");
				}
			}
		});
		text_name.setBounds(89, 12, 226, 23);

		Label label_1 = new Label(loginComposite, SWT.NONE);
		label_1.setBounds(21, 54, 61, 19);
		label_1.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		label_1.setText("\u5BC6\u3000\u7801");

		text_pwd = new Text(loginComposite, SWT.BORDER);
		text_pwd.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		text_pwd.setEchoChar('*');
		text_pwd.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (null == text_pwd.getText().trim() || "".equalsIgnoreCase(text_pwd.getText().trim())) {
					setErrorMessage("���벻��Ϊ��");
				}
			}
		});
		text_pwd.setBounds(89, 50, 226, 23);

		button_remember = new Button(loginComposite, SWT.CHECK);
		button_remember.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		button_remember.setBounds(21, 106, 73, 20);
		button_remember.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		button_remember.setText("\u8BB0\u4F4F\u5BC6\u7801");

		button_autologin = new Button(loginComposite, SWT.CHECK);
		button_autologin.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		button_autologin.setBounds(162, 106, 73, 20);
		button_autologin.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		button_autologin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {// �Զ���¼
				if (button_autologin.getSelection()) {
					button_remember.setSelection(true);
				}
			}
		});
		button_autologin.setText("\u81EA\u52A8\u767B\u5F55");

		canvas = new Canvas(container, SWT.BORDER);

		Label lblWaiting = new Label(canvas, SWT.NONE);
		lblWaiting.setBounds(133, 77, 136, 11);
		lblWaiting.setBackgroundImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/waiting.gif"));

		return area;
	}

	// ��ʼ�������е����ݺ�ѡ��
	private void initValues() {
		alu = new AutomLoginUtil();
		try {
			worker = (TWorker) alu.readObject();
		} catch (Exception e) {
			e.printStackTrace();//
		}
		if (worker == null) {
			worker = new TWorker();
		} else {
			text_name.setText(worker.getName());
			if (worker.getLogin() == AutomLoginUtil.RemPwd) {
				button_remember.setSelection(true);
				text_pwd.setText(worker.getPassword());
			} else if (worker.getLogin() == AutomLoginUtil.AutoLogin) {
				button_remember.setSelection(true);
				button_autologin.setSelection(true);
				text_pwd.setText(worker.getPassword());
				buttonPressed(IDialogConstants.OK_ID);// ����Ҫ�����Զ���¼
			}
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (!validateInput()) {
			return;
		}
		if (IDialogConstants.OK_ID == buttonId) {
			name = text_name.getText().trim();
			pwd = text_pwd.getText().trim();
			new RcpTask() {
				@Override
				protected void doTaskInBackground(StringBuffer message) {
					TWorkerManager manager = new TWorkerManager();
					try {
						worker = manager.loginByName(name);
					} catch (Exception e) {
						message.append("��¼�����쳣");
						return;
					}
					if (worker == null) {
						message.append("���������������Ա");
						return;
					}
					if (!worker.getPassword().equalsIgnoreCase(MD5Util.encrypt(pwd))) {
						message.append("�����������");
						return;
					}
					message.append(RcpTask.RESULT_OK);
				}

				@Override
				protected void doBeforeTask() {
					stackLayout.topControl = canvas;// �����¼�ˣ�������ʾ
					container.layout();
					okButton.setEnabled(false);
					setErrorMessage(null);// ֮ǰ�������˴�����ʾ����ʱҪ��������ʾ��գ������ǿ��ַ�������Ȼ�����д���ͼ��
					setMessage("���ں�Ŭ���ĵ�¼�У������ĵȴ�......");
					waiting = true;
				}

				@Override
				protected void doAfterTask(String result) {
					if (result.equalsIgnoreCase(RcpTask.RESULT_OK)) {// ��¼�ɹ�
						SystemUtil.LOGIN_WORKER = worker;
						if (button_remember.getSelection() == true) {
							worker.setLogin(AutomLoginUtil.RemPwd);
						}
						if (button_autologin.getSelection() == true) {
							worker.setLogin(AutomLoginUtil.AutoLogin);
						}
						try {
							worker.setPassword(pwd);
							alu.writeObject(worker);
							worker.setPassword(MD5Util.encrypt(pwd));
						} catch (Exception e) {
							e.printStackTrace();// ����ʱ���ܳ���
						}
						okPressed();
					} else {// ��¼ʧ��
						setErrorMessage(result);
						backToLogin();// ���ص���¼����
					}
				}
			}.execTask();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			if (waiting) {// ������ڵ�¼�ȴ��е����ȡ������ô�ͻص���¼����
				backToLogin();
			} else {
				cancelPressed();// ȡ����
			}
		}
	}

	// ��֤�û������Ƿ�Ϊ��
	private boolean validateInput() {
		String name = text_name.getText().trim();
		String pwd = text_pwd.getText().trim();
		if ("".equalsIgnoreCase(name) || "".equalsIgnoreCase(pwd)) {
			setErrorMessage("�˺ź����붼����Ϊ��");
			return false;
		}
		return true;
	}

	// ���ص�¼����
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
		okButton.setText("\u767B\u5F55");
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancelButton.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		cancelButton.setText("\u53D6\u6D88");
		initValues();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(350, 305);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\u767B\u5F55");
		newShell.setFont(ResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		// ʹ��¼���������ʾ
		Display display = getParentShell().getDisplay();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = newShell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		newShell.setLocation(x, y);
	}

}
