package item;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tool.SceneModeTool;
import tool.SystemTool;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.SceneMode;
import beans.SceneModeItem;

import communication.CommunicationUtil;

import controller.DeleteConfirmController;
import controller.NameEditController;
import controller.PicEditController;
import controller.SceneConfigController;
import controller.ScenePaneController;

/**
 * 情景模式item
 * 
 * @author yinger
 * 
 */
public class SceneItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	private SceneMode scenemode;
	private UiUtil uiUtil;
	private CommunicationUtil communicationUtil;
	private ProtocolUtil protocolUtil;
	private SceneModeTool tool;

	private VBox vbox;
	public static List<VBox> v = new ArrayList<VBox>();  //修改为全局数组就可。说明这个类每次都被调用了。
	//private VBox last_vbox = null;
	private ImageView iv;
	private Image onImage;
	private ContextMenu menu;

	public SceneItem(SceneMode control) {
		this.scenemode = control;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		tool = SceneModeTool.getInstance();
		communicationUtil = CommunicationUtil.getInstance();
		protocolUtil = ProtocolUtil.getInstance();

		menu = new ContextMenu();
		MenuItem nameItem = new MenuItem("修改名称");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(scenemode);
			}
		});
		
		MenuItem imageItem = new MenuItem("修改图片");
		imageItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				//CopyOfPicEditController.getInstance().initialize(null,null);
				PicEditController.type = 0;
				uiUtil.openDialog("PicEdit.fxml");
				PicEditController.getInstance().setScenemode(scenemode);
			}
		});
		
		MenuItem configItem = new MenuItem("配置");
		configItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.SECENEMODE_CONFIG_PANE);
				SceneConfigController.getInstance().setSceneMode(scenemode);
			}
		});
		MenuItem deleteItem = new MenuItem("删除");
		deleteItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				deleteSceneMode();
			}
		});

		
		
		
		// 可以改名字，不可以删除，默认有四个，名字固定
		menu.getItems().addAll(nameItem,imageItem, configItem, deleteItem);

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		Text textName = new Text(scenemode.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		// onImage = ResouceManager.getImage(ResouceManager.SCENE_DEFAULT);
		setImage(scenemode.getIcon());
		iv = new ImageView();
		iv.setImage(onImage);
		iv.setFitWidth(70);
		iv.setFitHeight(70);
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {// 启动情景模式
			
					startSceneMode();
//					if(last_vbox != null)
//						last_vbox.setStyle("-fx-background-color:#D8E7FA");
					for(int j =0; j<v.size();j++){						
						System.out.println("你好戏,j is:"+j);
						VBox vv = v.get(j);
						vv.setStyle("-fx-background-color:#f1fbff;");
					}
					
					vbox.setStyle("-fx-background-color: #CCFF99;");
					//v.add(vbox);
					//last_vbox = vbox; 
				} else if (e.getButton() == MouseButton.SECONDARY) {
					menu.show(iv, e.getScreenX(), e.getScreenY());
				}
			}
		});

		iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.playFadeTransition(iv, 2, 0.5f, 1.0f, 1, false);
			}
		});
		textName.textProperty().bind(scenemode.nameProperty());
		vbox.getChildren().addAll(iv, textName);
		v.add(vbox);
		//last_vbox.
	}

	private void setImage(String ic) {
		if(ic != null ){
			onImage = ResouceManager.getImage(ic);
		}
//下面的这些都可以去掉，因为第一句已经判断了，再来判断纯属多余
//		else if (scenemode.getName().equalsIgnoreCase(SceneMode.HOME)) {
//			onImage = ResouceManager.getImage(ResouceManager.SCENE_DEFAULT);
//		} else if (scenemode.getName().equalsIgnoreCase(SceneMode.OUT)) {
//			onImage = ResouceManager.getImage(ResouceManager.SCENE_OUT);
//		} else if (scenemode.getName().equalsIgnoreCase(SceneMode.LEAVE)) {
//			onImage = ResouceManager.getImage(ResouceManager.SCENE_LEAVE);
//		} else if (scenemode.getName().equalsIgnoreCase(SceneMode.GUEST)) {
//			onImage = ResouceManager.getImage(ResouceManager.SCENE_GUEST);
//		}
		if (onImage == null) {
			onImage = ResouceManager.getImage(ResouceManager.SCENE_DEFAULT);
		}
	}

	/**
	 * 得到这个节点
	 */
	public Node getNode() {
		return vbox;
	}

	/**
	 * 得到情景模式
	 */
	public SceneMode getSceneMode() {
		return scenemode;
	}

	/**
	 * 启动情景模式
	 */
	private void startSceneMode() {
		
		SceneModeItem sceneModeItem;
		for (int i = 0; i < scenemode.getItems().size(); i++) {
			sceneModeItem = scenemode.getItems().get(i);
			if (sceneModeItem.getState() == ProtocolUtil.STATE_ON) {// 开即发送开的命令
				sendData(sceneModeItem.getCommand_on());
			} else {
				sendData(sceneModeItem.getCommand_off());
			}
		}
	}

	private void deleteSceneMode() {
		uiUtil.openDialog(ResouceManager.DELETE_CONFIRM);// 提示是否真的删除
		new Thread(new Runnable() {// 为了防止界面假死，开启一个线程来完成删除确认的操作
					public void run() {
						StringBuffer confirmResult = DeleteConfirmController.getInstance().confirmResult;
						synchronized (confirmResult) {// 等待结果返回
							while (confirmResult.toString().length() == 0)
								try {
									confirmResult.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();// TODO:怎么处理
								}
						}
						if (confirmResult.toString().equalsIgnoreCase("YES")) {
							tool.deleteSceneMode(scenemode);// 删除--文件
							Platform.runLater(new Runnable() {// 确保这里是在fx线程中执行的
								public void run() {
									ScenePaneController.getInstance().refreshItems();// 刷新列表
								}
							});
						}// 如果不是yes，那么就啥也不干--线程退出！
					}
				}).start();
	}

	/**
	 * 发送命令
	 */
	private void sendData(int command) {
		try {
			communicationUtil.sendData(protocolUtil.packInstructionRequest(command));
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage("指令发送失败！");
		}
	}

}
