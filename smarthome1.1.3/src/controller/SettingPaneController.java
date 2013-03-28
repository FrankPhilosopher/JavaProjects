package controller;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tool.SceneModeTool;
import tool.SystemTool;
import tool.UpdateTool;
import util.AutoLoginUtil;
import util.FileUtil;
import util.HttpUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.User;
import cameraUtil.HttpRequest;

public class SettingPaneController implements Initializable {

	private static SettingPaneController controller;
	private User user;
	private UiUtil uiUtil;
	private HttpUtil httpUtil;
	private SystemTool systemTool;
	private UpdateTool updateTool;
	private AutoLoginUtil autoLoginUtil;
	private FileUtil fileUtil;

	@FXML
	private Text textHandRefresh;
	//@FXML
	//private Text textAutoDownload;
	@FXML
	private Text textAutoLogin;
	@FXML
	private Text textResetConfig;
	@FXML
	private Text textUpdate;

	@FXML
	private Label lblMessage;

	@FXML
	private TextField tfContact;
	@FXML
	private TextArea taSuggestion;
	@FXML
	private Button btnSend;

	public SettingPaneController getInstance() {
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	private void initEvents() {
		textHandRefresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {// �����ļ���ˢ���豸
				handRefresh();
			}
		});

//		textAutoDownload.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent arg0) {
//				user.setAutodownable(!user.isAutodownable());
//				try {
//					autoLoginUtil.writeObject(user);
//				} catch (Exception e) {
//					uiUtil.showErrorMessage(lblMessage, "����ʧ�ܣ�");       
//					return;
//				}
//				setAutoDownloadText();
//				uiUtil.showMessage(lblMessage, "���óɹ���");
//			}
//		});

		textAutoLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if (user.getLogin() != AutoLoginUtil.AutoLogin) {// ��������Զ���½����ô������Ϊ�Զ���½
					user.setLogin(AutoLoginUtil.AutoLogin);
				} else {// ������Զ���½����ô������Ϊ��ס����
					user.setLogin(AutoLoginUtil.RemPwd);
				}
				try {
					autoLoginUtil.writeObject(user);
				} catch (Exception e) {
					uiUtil.showErrorMessage(lblMessage, "����ʧ�ܣ�");
					return;
				}
				setAutoLoginText(1);
				//uiUtil.showMessage(lblMessage, "���óɹ���");
			}
		});

		textResetConfig.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				resetConfig();
			}
		});

		textUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				pcUpdate();
			}
		});

		btnSend.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				String suggestion = taSuggestion.getText().trim();
				if (suggestion == null || "".equalsIgnoreCase(suggestion)) {
					uiUtil.showErrorMessage(lblMessage, "������������ݣ�");
					return;
				}
				String contact = tfContact.getText();
				if (contact == null || "".equalsIgnoreCase(contact)) {
					contact = "default";
				}
				sendSuggestion(contact, suggestion);
				tfContact.setText(null);
				taSuggestion.setText(null);
			}
		});
	}

	/**
	 * �������汾����
	 */
	protected void pcUpdate() {
		updateTool = UpdateTool.getInstance();
		changeMessage("���ڼ��汾���£������ĵȴ�......");
		new Thread(new Runnable() {// ����߳̿�ʼ�����ļ�
					public void run() {
						try {
							httpUtil.downloadPcVersionFile();
						} catch (Exception e) {
							uiUtil.showErrorMessage(lblMessage, "�����ļ�����ʧ�ܣ�");
						}
					}
				}).start();
		new Thread(new Runnable() {// ����߳̾����ȵȴ�������ɣ�����֮��͸��½���
					public void run() {
						StringBuffer updateResult = httpUtil.getUpdateResult();
						synchronized (updateResult) {
							while (updateResult.toString().length() == 0)
								try {
									updateResult.wait();// ������������ֱ��ȫ���������
								} catch (InterruptedException e) {
									uiUtil.showErrorMessage(lblMessage, "���汾���³����쳣��");
								}
						}
						if (!updateResult.toString().equalsIgnoreCase("OK")) {
							uiUtil.showErrorMessage(lblMessage, updateResult.toString());
							return;
						}
						try {// ��ȡ�汾������Ϣ
							updateTool.readVersionXml();
						} catch (Exception e) {
							uiUtil.showErrorMessage(lblMessage, e.getMessage());
							return;
						}
						if (checkVersion()) {// �����Ҫ���¾ͷ���true
							Platform.runLater(new Runnable() {// ���صõ��汾�ļ�
								public void run() {
									uiUtil.showMessage(lblMessage, "�汾�����ļ����سɹ���");
									uiUtil.openDialog(ResouceManager.UPDATE_PANE);
								}
							});
						} else {
							uiUtil.showErrorMessage(lblMessage, "��ǰ��������°汾������Ҫ����!");
						}
					}
				}).start();

	}

	/**
	 * ���汾
	 */
	private boolean checkVersion() {
		// System.out.println(systemTool.getVersion());
		// System.out.println(updateTool.getVersionCode());
		String[] current = systemTool.getVersion().split("\\.");
		String[] lastest = updateTool.getVersionCode().split("\\.");
		for (int i = 0; i < 3; i++) {
			if (Integer.parseInt(current[i]) < Integer.parseInt(lastest[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ֶ������豸
	 */
	protected void handRefresh() {
		changeMessage("���������豸�����ļ��������ĵȴ�......");
		new Thread(new Runnable() {// ����߳̿�ʼ�����ļ�
					public void run() {
						httpUtil.downloadFiles();// ���������Ҫһ���߳������
					}
				}).start();
		new Thread(new Runnable() {// ����߳̾����ȵȴ�������ɣ�����֮��͸��½���
					public void run() {
						StringBuffer downloadResult = httpUtil.getDownloadResult();
						synchronized (downloadResult) {
							while (downloadResult.toString().length() == 0)
								try {
									downloadResult.wait();// ������������ֱ��ȫ���������
								} catch (InterruptedException e) {
									uiUtil.showErrorMessage(lblMessage, "�豸���³����쳣��");
								}
						}
						if (!downloadResult.toString().equalsIgnoreCase("OK")) {
							uiUtil.showErrorMessage(lblMessage, downloadResult.toString());
							return;
						}
						Platform.runLater(new Runnable() {
							public void run() {
								LightPaneController.getInstance().refreshItems();
								DoorPaneController.getInstance().refreshItems();
								SecurityPaneController.getInstance().refreshItems();
								AppliancePaneController.getInstance().refreshItems();
								uiUtil.showMessage(lblMessage, "�豸���³ɹ���");
							}
						});
					}
				}).start();
	}

	/**
	 * ��ʾ��Ϣ
	 */
	private void changeMessage(final String string) {
		lblMessage.setTextFill(Color.BLUE);
		lblMessage.setText(string);
	}

	/**
	 * �������
	 */
	private void sendSuggestion(final String contact, final String suggestion) {
		new Thread() {
			public void run() {
				try {
					String url = "http://" + "www.iever.cn" + ":" + systemTool.getServer_downport()
							+ "/suggestion/suggestion.aspx?" + "suggestion=" + URLEncoder.encode(suggestion, "utf-8")
							+ "&suggestioncontact=" + contact;
					System.out.println(url);
					HttpRequest httpRequest = new HttpRequest();
					httpRequest.doGet(url);
					uiUtil.showMessage(lblMessage, "������ͳɹ���");
				} catch (Exception e) {
					uiUtil.showSystemExceptionMessage("�������ʧ�ܣ������·��ͣ�");
				}
			}
		}.start();
	}

	/**
	 * ��ԭ��ʼ����
	 */
	private void resetConfig() {
		uiUtil.openDialog(ResouceManager.RESET_CONFIRM);// ��ʾ�Ƿ����Ҫ��ԭ
		new Thread(new Runnable() {// Ϊ�˷�ֹ�������������һ���߳������ȷ�ϵĲ���
					public void run() {
						StringBuffer confirmResult = ResetConfirmController.getInstance().confirmResult;
						synchronized (confirmResult) {// �ȴ��������
							while (confirmResult.toString().length() == 0)
								try {
									confirmResult.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();// TODO:��ô����
								}
						}
						if (confirmResult.toString().equalsIgnoreCase("YES")) {
							fileUtil.resetConfig();// ɾ���ļ������ļ���
							SystemTool.CURRENT_USER.setFirstRun(true);  //
							SceneModeTool.getInstance().resetSceneMode();// ������������յ��龰ģʽ
							SystemTool.CURRENT_USER.setFirstRun(false);  //AB��ӣ�Ϊ��ͼ����ȷ
							Platform.runLater(new Runnable() {// ȷ����������fx�߳���ִ�е�
								public void run() {
									LightPaneController.getInstance().refreshItems();// ˢ�½���Ԫ��
									DoorPaneController.getInstance().refreshItems();
									SecurityPaneController.getInstance().refreshItems();
									AppliancePaneController.getInstance().refreshItems();
									ScenePaneController.getInstance().refreshItems();
									CameraPaneController.getInstance().refreshItems();
									uiUtil.showMessage(lblMessage, "���û�ԭ�ɹ���");
								}
							});
						}// �������yes����ô��ɶҲ����--�߳��˳���
					}
				}).start();
	}

	private void initValues() {
		controller = this;
		user = SystemTool.CURRENT_USER;
		uiUtil = UiUtil.getInstance();
		fileUtil = FileUtil.getInstance();
		httpUtil = HttpUtil.getInstance();
		systemTool = SystemTool.getInstance();
		autoLoginUtil = AutoLoginUtil.getInstance();
		//setAutoDownloadText();
		setAutoLoginText(0);
		tfContact.setPromptText("���������ĵ绰������������ַ");
	}

	/**
	 * �����Զ���½��text
	 */
	private void setAutoLoginText(int type) {
		if (user.getLogin() != AutoLoginUtil.AutoLogin) {
			textAutoLogin.setText("�Զ���¼����");
			textAutoLogin.setStyle("-fx-fill: gray;-fx-stroke: gray;");
			if(type == 1)
				uiUtil.showMessage(lblMessage, "���óɹ�����ȡ�����Զ���¼");
		} else {
			textAutoLogin.setText("�Զ���¼����");
			textAutoLogin.setStyle("-fx-fill:#45C92D;-fx-stroke:#0c9900;");
			if(type == 1)
				uiUtil.showMessage(lblMessage, "���óɹ������������Զ���¼");
		}
	}

	/**
	 * �����Զ����ص�text
	 */
//	private void setAutoDownloadText() {
//		if (user.isAutodownable()) {
//			//textAutoDownload.setText("ȡ���Զ�����");
//		} else {
//			//textAutoDownload.setText("�Զ���������");
//		}
//	}

}
