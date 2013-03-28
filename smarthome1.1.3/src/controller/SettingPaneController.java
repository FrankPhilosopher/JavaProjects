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
			public void handle(MouseEvent arg0) {// 下载文件并刷新设备
				handRefresh();
			}
		});

//		textAutoDownload.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent arg0) {
//				user.setAutodownable(!user.isAutodownable());
//				try {
//					autoLoginUtil.writeObject(user);
//				} catch (Exception e) {
//					uiUtil.showErrorMessage(lblMessage, "配置失败！");       
//					return;
//				}
//				setAutoDownloadText();
//				uiUtil.showMessage(lblMessage, "配置成功！");
//			}
//		});

		textAutoLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if (user.getLogin() != AutoLoginUtil.AutoLogin) {// 如果不是自动登陆，那么就设置为自动登陆
					user.setLogin(AutoLoginUtil.AutoLogin);
				} else {// 如果是自动登陆，那么就设置为记住密码
					user.setLogin(AutoLoginUtil.RemPwd);
				}
				try {
					autoLoginUtil.writeObject(user);
				} catch (Exception e) {
					uiUtil.showErrorMessage(lblMessage, "配置失败！");
					return;
				}
				setAutoLoginText(1);
				//uiUtil.showMessage(lblMessage, "配置成功！");
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
					uiUtil.showErrorMessage(lblMessage, "请输入意见内容！");
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
	 * 检查软件版本更新
	 */
	protected void pcUpdate() {
		updateTool = UpdateTool.getInstance();
		changeMessage("正在检查版本更新，请耐心等待......");
		new Thread(new Runnable() {// 这个线程开始下载文件
					public void run() {
						try {
							httpUtil.downloadPcVersionFile();
						} catch (Exception e) {
							uiUtil.showErrorMessage(lblMessage, "更新文件下载失败！");
						}
					}
				}).start();
		new Thread(new Runnable() {// 这个线程就是先等待下载完成，完了之后就更新界面
					public void run() {
						StringBuffer updateResult = httpUtil.getUpdateResult();
						synchronized (updateResult) {
							while (updateResult.toString().length() == 0)
								try {
									updateResult.wait();// 在这里阻塞，直到全部下载完成
								} catch (InterruptedException e) {
									uiUtil.showErrorMessage(lblMessage, "检查版本更新出现异常！");
								}
						}
						if (!updateResult.toString().equalsIgnoreCase("OK")) {
							uiUtil.showErrorMessage(lblMessage, updateResult.toString());
							return;
						}
						try {// 读取版本更新信息
							updateTool.readVersionXml();
						} catch (Exception e) {
							uiUtil.showErrorMessage(lblMessage, e.getMessage());
							return;
						}
						if (checkVersion()) {// 如果需要更新就返回true
							Platform.runLater(new Runnable() {// 下载得到版本文件
								public void run() {
									uiUtil.showMessage(lblMessage, "版本更新文件下载成功！");
									uiUtil.openDialog(ResouceManager.UPDATE_PANE);
								}
							});
						} else {
							uiUtil.showErrorMessage(lblMessage, "当前软件是最新版本，不需要更新!");
						}
					}
				}).start();

	}

	/**
	 * 检查版本
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
	 * 手动更新设备
	 */
	protected void handRefresh() {
		changeMessage("正在下载设备配置文件，请耐心等待......");
		new Thread(new Runnable() {// 这个线程开始下载文件
					public void run() {
						httpUtil.downloadFiles();// 这里或许需要一个线程来完成
					}
				}).start();
		new Thread(new Runnable() {// 这个线程就是先等待下载完成，完了之后就更新界面
					public void run() {
						StringBuffer downloadResult = httpUtil.getDownloadResult();
						synchronized (downloadResult) {
							while (downloadResult.toString().length() == 0)
								try {
									downloadResult.wait();// 在这里阻塞，直到全部下载完成
								} catch (InterruptedException e) {
									uiUtil.showErrorMessage(lblMessage, "设备更新出现异常！");
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
								uiUtil.showMessage(lblMessage, "设备更新成功！");
							}
						});
					}
				}).start();
	}

	/**
	 * 显示信息
	 */
	private void changeMessage(final String string) {
		lblMessage.setTextFill(Color.BLUE);
		lblMessage.setText(string);
	}

	/**
	 * 发送意见
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
					uiUtil.showMessage(lblMessage, "意见发送成功！");
				} catch (Exception e) {
					uiUtil.showSystemExceptionMessage("意见发送失败！请重新发送！");
				}
			}
		}.start();
	}

	/**
	 * 还原初始配置
	 */
	private void resetConfig() {
		uiUtil.openDialog(ResouceManager.RESET_CONFIRM);// 提示是否真的要还原
		new Thread(new Runnable() {// 为了防止界面假死，开启一个线程来完成确认的操作
					public void run() {
						StringBuffer confirmResult = ResetConfirmController.getInstance().confirmResult;
						synchronized (confirmResult) {// 等待结果返回
							while (confirmResult.toString().length() == 0)
								try {
									confirmResult.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();// TODO:怎么处理
								}
						}
						if (confirmResult.toString().equalsIgnoreCase("YES")) {
							fileUtil.resetConfig();// 删除文件或者文件夹
							SystemTool.CURRENT_USER.setFirstRun(true);  //
							SceneModeTool.getInstance().resetSceneMode();// 重新添加两个空的情景模式
							SystemTool.CURRENT_USER.setFirstRun(false);  //AB添加，为了图标正确
							Platform.runLater(new Runnable() {// 确保这里是在fx线程中执行的
								public void run() {
									LightPaneController.getInstance().refreshItems();// 刷新界面元素
									DoorPaneController.getInstance().refreshItems();
									SecurityPaneController.getInstance().refreshItems();
									AppliancePaneController.getInstance().refreshItems();
									ScenePaneController.getInstance().refreshItems();
									CameraPaneController.getInstance().refreshItems();
									uiUtil.showMessage(lblMessage, "配置还原成功！");
								}
							});
						}// 如果不是yes，那么就啥也不干--线程退出！
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
		tfContact.setPromptText("请输入您的电话号码或者邮箱地址");
	}

	/**
	 * 设置自动登陆的text
	 */
	private void setAutoLoginText(int type) {
		if (user.getLogin() != AutoLoginUtil.AutoLogin) {
			textAutoLogin.setText("自动登录配置");
			textAutoLogin.setStyle("-fx-fill: gray;-fx-stroke: gray;");
			if(type == 1)
				uiUtil.showMessage(lblMessage, "配置成功，你取消了自动登录");
		} else {
			textAutoLogin.setText("自动登录配置");
			textAutoLogin.setStyle("-fx-fill:#45C92D;-fx-stroke:#0c9900;");
			if(type == 1)
				uiUtil.showMessage(lblMessage, "配置成功，你设置了自动登录");
		}
	}

	/**
	 * 设置自动下载的text
	 */
//	private void setAutoDownloadText() {
//		if (user.isAutodownable()) {
//			//textAutoDownload.setText("取消自动下载");
//		} else {
//			//textAutoDownload.setText("自动下载配置");
//		}
//	}

}
