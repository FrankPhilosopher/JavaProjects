package com.yinger.gui;

import com.cloudgarden.resource.SWTResourceManager;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

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
public class InfoDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private String info = "Information:";
	private Label label_img;
	private Label label_info;

	private boolean flag = true; // true 表示正常的信息，false表示出错了
	private Button button_ok;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			InfoDialog inst = new InfoDialog(shell, SWT.NULL, "fsdfas", true);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InfoDialog(Shell parent, int style, String info, boolean flag) {
		super(parent, style);
		this.info += info;
		this.flag = flag;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			{
				SWTResourceManager.registerResourceUser(dialogShell);
			}

			dialogShell.setLayout(new FormLayout());
			dialogShell.setImage(SWTResourceManager.getImage("images/Stats.ico"));
			if (flag) {
				dialogShell.setText("\u4fe1\u606f");
				dialogShell.addShellListener(new ShellAdapter() {
					@Override
					public void shellClosed(ShellEvent evt) {
						dialogShellShellClosed(evt);
					}
				});
			} else {
				dialogShell.setText("\u51fa\u9519\u5566");
			}
			{
				button_ok = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				button_ok.setText("\u786e\u5b9a");
				FormData button_okLData = new FormData();
				button_okLData.width = 40;
				button_okLData.height = 27;
				button_okLData.left = new FormAttachment(0, 1000, 292);
				button_okLData.top = new FormAttachment(0, 1000, 127);
				button_okLData.right = new FormAttachment(1000, 1000, -20);
				button_okLData.bottom = new FormAttachment(1000, 1000, -12);
				button_ok.setLayoutData(button_okLData);
				button_ok.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						button_okWidgetSelected(evt);
					}
				});
			}
			{
				label_info = new Label(dialogShell, SWT.WRAP);
				FormData label_infoLData = new FormData();
				label_infoLData.width = 276;
				label_infoLData.height = 76;
				label_infoLData.left = new FormAttachment(0, 1000, 57);
				label_infoLData.top = new FormAttachment(0, 1000, 22);
				label_infoLData.right = new FormAttachment(1000, 1000, -12);
				label_infoLData.bottom = new FormAttachment(1000, 1000, -42);
				label_info.setLayoutData(label_infoLData);
				label_info.setText(info);// 设置错误信息
			}
			dialogShell.layout();
			dialogShell.pack();
			{
				label_img = new Label(dialogShell, SWT.NONE);
				if (flag) {
					label_img.setImage(SWTResourceManager.getImage("images/Smiley.png"));
				} else {
					label_img.setImage(SWTResourceManager.getImage("images/Smiley Sad.png"));
				}
				FormData label_imgLData = new FormData();
				label_imgLData.width = 39;
				label_imgLData.height = 50;
				label_imgLData.left = new FormAttachment(0, 1000, 12);
				label_imgLData.top = new FormAttachment(0, 1000, 12);
				label_img.setLayoutData(label_imgLData);
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 确定
	private void button_okWidgetSelected(SelectionEvent evt) {
		System.out.println("button_ok.widgetSelected, event=" + evt);
		this.dialogShell.close();// 关闭这个提示框
		if(flag){
			if(!this.getParent().isDisposed()){//注意，这里要判断一下，不然会抛出异常  SWTException: Widget is disposed
				this.getParent().close();//上一个窗体是新建任务窗体，让它关闭并返回downloadInfo！
			}
		}
	}
	
	// 关闭
	private void dialogShellShellClosed(ShellEvent evt) {
		System.out.println("Info dialogShell.shellClosed, event="+evt);
		if(flag){//如果提示信息后用户点击的是关闭的话，那么也是要开始下载的！
			if(!this.getParent().isDisposed()){//注意，这里要判断一下，不然会抛出异常  SWTException: Widget is disposed
				this.getParent().close();//上一个窗体是新建任务窗体，让它关闭并返回downloadInfo！
			}
		}// 注意，如果是下载完成的Dialog出现的话就不是很合适了，提示完了确定主程序也就关闭了，这样不好！
		//TODO：要是能够判断出parent是哪个shell就好了！
	}

}
