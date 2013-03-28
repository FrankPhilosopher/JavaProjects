package com.yinger.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.yinger.download.DownloadInfo;
import com.yinger.download.DownloadUtil;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class NewTaskDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label_url;
	private Label label_savePath;
	private Label label_threadNumber;
	private Button button_down;
	private Button button_up;
	private Button button;
	private Text text_threadNumber;
	private Text text_savePath;
	private Text text_url;
	private Button button_Cancel;
	private Button button_OK;

	private DownloadInfo downloadInfo = new DownloadInfo();

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			NewTaskDialog inst = new NewTaskDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NewTaskDialog(Shell parent, int style) {
		super(parent, style);
	}

	// ��Dialog����һ�� UrlResult��������
	public DownloadInfo open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			{
				SWTResourceManager.registerResourceUser(dialogShell);
			}

			dialogShell.setLayout(new FormLayout());
			dialogShell.setImage(SWTResourceManager.getImage("images/newtask.gif"));
			dialogShell.setText("\u65b0\u5efa\u4e0b\u8f7d\u4efb\u52a1");
			dialogShell.addShellListener(new ShellAdapter() {
				@Override
				public void shellClosed(ShellEvent evt) {
					dialogShellShellClosed(evt);
				}
			});
			{
				button_down = new Button(dialogShell, SWT.ARROW | SWT.DOWN);
				FormData button_downLData = new FormData();
				button_downLData.width = 25;
				button_downLData.height = 16;
				button_downLData.left = new FormAttachment(0, 1000, 133);
				button_downLData.top = new FormAttachment(0, 1000, 94);
				button_down.setLayoutData(button_downLData);
				button_down.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						button_downWidgetSelected(evt);
					}
				});
			}
			{
				button_up = new Button(dialogShell, SWT.ARROW | SWT.UP);
				FormData button_upLData = new FormData();
				button_upLData.width = 25;
				button_upLData.height = 15;
				button_upLData.left = new FormAttachment(0, 1000, 133);
				button_upLData.top = new FormAttachment(0, 1000, 79);
				button_up.setLayoutData(button_upLData);
				button_up.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						button_upWidgetSelected(evt);
					}
				});
			}
			{
				button = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				button.setText("...");
				FormData buttonLData = new FormData();
				buttonLData.width = 40;
				buttonLData.height = 27;
				buttonLData.left = new FormAttachment(0, 1000, 283);
				buttonLData.top = new FormAttachment(0, 1000, 44);
				button.setLayoutData(buttonLData);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						buttonWidgetSelected(evt);
					}
				});
			}
			{
				button_Cancel = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				button_Cancel.setText("\u53d6\u6d88");
				FormData button_CancelLData = new FormData();
				button_CancelLData.width = 59;
				button_CancelLData.height = 28;
				button_CancelLData.left = new FormAttachment(0, 1000, 269);
				button_CancelLData.top = new FormAttachment(0, 1000, 115);
				button_CancelLData.right = new FormAttachment(1000, 1000, -19);
				button_CancelLData.bottom = new FormAttachment(1000, 1000, -18);
				button_Cancel.setLayoutData(button_CancelLData);
				button_Cancel.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						button_CancelWidgetSelected(evt);
					}
				});
			}
			{
				button_OK = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				button_OK.setText("\u786e\u5b9a");
				FormData button_OKLData = new FormData();
				button_OKLData.width = 59;
				button_OKLData.height = 28;
				button_OKLData.left = new FormAttachment(0, 1000, 208);
				button_OKLData.top = new FormAttachment(0, 1000, 115);
				button_OKLData.right = new FormAttachment(1000, 1000, -80);
				button_OKLData.bottom = new FormAttachment(1000, 1000, -18);
				button_OK.setLayoutData(button_OKLData);
				button_OK.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						button_OKWidgetSelected(evt);
					}
				});
			}
			{
				label_url = new Label(dialogShell, SWT.NONE);
				label_url.setText("URL\uff1a");
				FormData label_urlLData = new FormData();
				label_urlLData.width = 60;
				label_urlLData.height = 20;
				label_urlLData.left = new FormAttachment(0, 1000, 5);
				label_urlLData.top = new FormAttachment(0, 1000, 15);
				label_url.setLayoutData(label_urlLData);
			}
			{
				label_savePath = new Label(dialogShell, SWT.NONE);
				label_savePath.setText("\u4fdd\u5b58\u4f4d\u7f6e\uff1a");
				FormData label_savePathLData = new FormData();
				label_savePathLData.width = 60;
				label_savePathLData.height = 20;
				label_savePathLData.left = new FormAttachment(0, 1000, 5);
				label_savePathLData.top = new FormAttachment(0, 1000, 51);
				label_savePath.setLayoutData(label_savePathLData);
			}
			dialogShell.layout();
			dialogShell.pack();
			{
				label_threadNumber = new Label(dialogShell, SWT.NONE);
				label_threadNumber.setText("\u7ebf\u7a0b\u6570\u76ee\uff1a");
				FormData label_threadNumberLData = new FormData();
				label_threadNumberLData.width = 60;
				label_threadNumberLData.height = 20;
				label_threadNumberLData.left = new FormAttachment(0, 1000, 5);
				label_threadNumberLData.top = new FormAttachment(0, 1000, 88);
				label_threadNumber.setLayoutData(label_threadNumberLData);
			}
			{
				text_url = new Text(dialogShell, SWT.SINGLE | SWT.BORDER);
				FormData text_urlLData = new FormData();
				text_urlLData.width = 208;
				text_urlLData.height = 20;
				text_urlLData.left = new FormAttachment(0, 1000, 71);
				text_urlLData.top = new FormAttachment(0, 1000, 9);
				text_urlLData.right = new FormAttachment(1000, 1000, -20);
				text_url.setLayoutData(text_urlLData);
			}
			{
				text_savePath = new Text(dialogShell, SWT.SINGLE | SWT.BORDER);
				FormData text1LData = new FormData();
				text1LData.left = new FormAttachment(0, 1000, 73);
				text1LData.top = new FormAttachment(0, 1000, 45);
				text1LData.width = 189;
				text1LData.height = 20;
				text1LData.right = new FormAttachment(1000, 1000, -76);
				text_savePath.setLayoutData(text1LData);
			}
			{
				text_threadNumber = new Text(dialogShell, SWT.SINGLE | SWT.BORDER);
				FormData text2LData = new FormData();
				text2LData.left = new FormAttachment(0, 1000, 73);
				text2LData.top = new FormAttachment(0, 1000, 82);
				text2LData.width = 51;
				text2LData.height = 20;
				text2LData.right = new FormAttachment(1000, 1000, -214);
				text_threadNumber.setLayoutData(text2LData);
				text_threadNumber.setText("3");
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) { //
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return downloadInfo;
	}

	// ����
	private void button_upWidgetSelected(SelectionEvent evt) {
		System.out.println("button_up.widgetSelected, event=" + evt);
		int pre = Integer.valueOf(text_threadNumber.getText()) + 1;
		text_threadNumber.setText(String.valueOf(pre));
	}

	// ����
	private void button_downWidgetSelected(SelectionEvent evt) {
		System.out.println("button_up.widgetSelected, event=" + evt);
		int pre = Integer.valueOf(text_threadNumber.getText()) - 1;
		text_threadNumber.setText(String.valueOf(pre));
	}

	// �ļ�ѡ��
	private void buttonWidgetSelected(SelectionEvent evt) {
		System.out.println("button.widgetSelected, event=" + evt);
		FileDialog fileDialog = new FileDialog(getParent(), SWT.SAVE);
		String filePathString = fileDialog.open();
		if (filePathString != null) {
			text_savePath.setText(filePathString);
		}
	}

	// ȷ��
	private void button_OKWidgetSelected(SelectionEvent evt) {
		System.out.println("button_OK.widgetSelected, event=" + evt);
		String url = text_url.getText().trim();
		String saveFilePath = text_savePath.getText().trim();
		String threadNum = text_threadNumber.getText().trim();
		downloadInfo = new DownloadInfo();
		// ע�� new InfoDialog ʱ�ĵ�һ�������� this.dialogShell������ getParent()
		if (url == null || url.equals("")) {
			downloadInfo.setMyResult(-1);
			downloadInfo.setResultMsg("URL��ַΪ�գ�");
			new InfoDialog(this.dialogShell, SWT.NULL, downloadInfo.getResultMsg(), false).open();
			return;
		}
		if (saveFilePath == null || saveFilePath.equals("")) {
			downloadInfo.setMyResult(-2);
			downloadInfo.setResultMsg("�����ַΪ�գ�");
			new InfoDialog(this.dialogShell, SWT.NULL, downloadInfo.getResultMsg(), false).open();
			return;
		}
		if (threadNum == null || threadNum.equals("")) {// ȱ�㣺û���ж�����ַ����Ƿ���һ�����֣�
			downloadInfo.setMyResult(-3);
			downloadInfo.setResultMsg("�߳���ĿΪ�գ�");
			new InfoDialog(this.dialogShell, SWT.NULL, downloadInfo.getResultMsg(), false).open();
			return;
		}
		downloadInfo = DownloadUtil.testTask(url, saveFilePath, Integer.valueOf(threadNum));
		if (downloadInfo.getMyResult() != 1) {// �����˴���
			new InfoDialog(this.dialogShell, SWT.NULL, downloadInfo.getResultMsg(), false).open();
			// dialogShell.close();// ����ǳ����ˣ���ôҲҪ�ر��½�����Ĵ��ڣ���Ȼ�ͻ���ֿ�ָ���쳣{��Ӧ����ô���}
		} else {
			// dialogShell.setVisible(false); //�����������ʹ��open��������
			// dialogShell.close();// ����������ԣ�����ʹ��open��������������ֵ���������еķ���
			new InfoDialog(this.dialogShell, SWT.NULL, downloadInfo.getResultMsg(), true).open();// ������������һЩ���⣡
		}// close + open ����!
	}

	// ȡ��
	private void button_CancelWidgetSelected(SelectionEvent evt) {
		System.out.println("button_Cancel.widgetSelected, event=" + evt);
		// dialogShell.setVisible(false);// �˳��½�����
		// ȡ���Ļ��϶���û��������Ϣ
		// if (downloadInfo==null || downloadInfo.getMyResult() != 1) { //
		// ��������������Ϣ������ȷ�ģ���ô�͸ɴ�����Ϊnull��������Ǹ����⣡[���ʲô��û�����룬Ȼ���ֵ����ȷ���������ֹرգ���ʱ�ᱨ��]
		// }
		dialogShell.close();// �رգ�open��������
	}

	// �ر��½����� [ע�⣺���������һ���ǵ���رհ�ťʱִ�еģ�ֻҪ�Ǵ���ر��ˣ���������ͻ�ִ�У�]
	private void dialogShellShellClosed(ShellEvent evt) {
		System.out.println("Task dialogShell.shellClosed, event=" + evt);
		//ע�⣬�������ķ�������NewTaskDialog�в���downloadInfo�����κ��жϺͲ��������˳�ʼ��������
		//��MainFrame�и���downloadInfo.getMyResult()�Ƿ����1����������з����������ͱ����˺ܶ�Ĳ��ÿ��Ƶ��쳣
//		if (downloadInfo == null || downloadInfo.getMyResult() != 1) { // ��������������Ϣ������ȷ�ģ���ô�͸ɴ�����Ϊnull��������Ǹ����⣡[���ʲô��û�����룬Ȼ���ֵ����ȷ���������ֹرգ���ʱ�ᱨ��]
//			downloadInfo = null;
//		}
	}

}
