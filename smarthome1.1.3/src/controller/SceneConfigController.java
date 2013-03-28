package controller;

import item.SceneConfigItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tool.SceneModeTool;
import util.ProtocolUtil;
import util.UiUtil;
import app.SmartHome;
import beans.SceneMode;
import beans.SceneModeItem;

public class SceneConfigController implements Initializable {

	private static SceneConfigController controller;
	private UiUtil uiUtil;
	private SceneModeTool tool;

	private double initX;
	private double initY;
	private Stage stage;
	private SceneMode sceneMode;
	private int type = 0;// 0表示添加情景模式，1表示修改情景模式
	private List<SceneConfigItem> sceneConfigItems;// 情景模式中的所有sceneconfigitem

	private List<SceneModeItem> lightSceneModeItems;
	private List<SceneModeItem> doorSceneModeItems;
	private List<SceneModeItem> applianceSceneModeItems;
	private List<SceneModeItem> securitySceneModeItems;

	@FXML
	private AnchorPane sceneConfigPane;
	@FXML
	private TextField tfName;
	@FXML
	private Label lblName;
	@FXML
	private Label lblMessage;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;

	@FXML
	private Text configTitle;

	@FXML
	private Accordion accordion;
	@FXML
	private TitledPane lightTitledPane;
	@FXML
	private TitledPane doorTitledPane;
	@FXML
	private TitledPane securityTitledPane;
	@FXML
	private TitledPane applianceTitledPane;

	@FXML
	private TilePane lightTilePane;
	@FXML
	private TilePane doorTilePane;
	@FXML
	private TilePane securityTilePane;
	@FXML
	private TilePane applianceTilePane;

	@FXML
	private Button btnAllOpen_light;
	@FXML
	private Button btnAllClose_light;
	@FXML
	private Button btnAllRemain_light;
	@FXML
	private Button btnAllOpen_door;
	@FXML
	private Button btnAllClose_door;
	@FXML
	private Button btnAllRemain_door;
	@FXML
	private Button btnAllOpen_appliance;
	@FXML
	private Button btnAllClose_appliance;
	@FXML
	private Button btnAllRemain_appliance;
	@FXML
	private Button btnAllOpen_security;
	@FXML
	private Button btnAllClose_security;
	@FXML
	private Button btnAllRemain_security;

	public static SceneConfigController getInstance() {
		
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		controller = this;
		stage = SmartHome.getModelStage();
		tool = SceneModeTool.getInstance();
		uiUtil = UiUtil.getInstance();
		tfName.setText("");
		lblName.setText("");
		tfName.setVisible(false);
		lblName.setVisible(false);
		lblMessage.setText("");
		sceneConfigItems = new ArrayList<SceneConfigItem>();

		lightSceneModeItems = LightPaneController.getInstance().loadSceneModeItems();
		doorSceneModeItems = DoorPaneController.getInstance().loadSceneModeItems();
		securitySceneModeItems = SecurityPaneController.getInstance().loadSceneModeItems();
		applianceSceneModeItems = AppliancePaneController.getInstance().loadSceneModeItems();

		addSceneItems(lightSceneModeItems, lightTilePane);
		addSceneItems(doorSceneModeItems, doorTilePane);
		addSceneItems(securitySceneModeItems, securityTilePane);
		addSceneItems(applianceSceneModeItems, applianceTilePane);
	}

	/**
	 * 添加情景模式配置item到tilepane
	 */
	private void addSceneItems(List<SceneModeItem> items, TilePane tilePane) {
		// List<SceneModeItem> items = tool.loadSceneModeItems();
		SceneConfigItem sceneConfigItem;
		for (int i = 0; i < items.size(); i++) {
			sceneConfigItem = new SceneConfigItem(sceneMode, items.get(i));
			sceneConfigItems.add(sceneConfigItem);// 加入到sceneConfigItems中
			tilePane.getChildren().add(sceneConfigItem.getNode());
		}
	}

	/**
	 * 事件处理
	 */
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		sceneConfigPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		sceneConfigPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		ivWinmin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setIconified(true);
			}
		});

		ivWinclose.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.close();
			}
		});

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				handleConfigOk();
			}
		});

		btnCancle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		btnAllOpen_light.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(lightSceneModeItems, "open");
			}
		});
		btnAllOpen_door.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(doorSceneModeItems, "open");
			}
		});
		btnAllOpen_appliance.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(applianceSceneModeItems, "open");
			}
		});
		btnAllOpen_security.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(securitySceneModeItems, "open");
			}
		});

		btnAllClose_light.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(lightSceneModeItems, "close");
			}
		});
		btnAllClose_door.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(doorSceneModeItems, "close");
			}
		});
		btnAllClose_appliance.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(applianceSceneModeItems, "close");
			}
		});
		btnAllClose_security.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(securitySceneModeItems, "close");
			}
		});

		btnAllRemain_light.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(lightSceneModeItems, "remain");
			}
		});
		btnAllRemain_door.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(doorSceneModeItems, "remain");
			}
		});
		btnAllRemain_appliance.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(applianceSceneModeItems, "remain");
			}
		});
		btnAllRemain_security.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				allCommand(securitySceneModeItems, "remain");
			}
		});

	}

	/**
	 * 三个操作按钮，根据type决定作何操作
	 */
	protected void allCommand(List<SceneModeItem> sceneModeItems, String commandType) {
		if (commandType.equalsIgnoreCase("open")) {
			for (int i = 0; i < sceneModeItems.size(); i++) {
				sceneModeItems.get(i).setState(ProtocolUtil.STATE_ON);
			}
		} else if (commandType.equalsIgnoreCase("close")) {
			for (int i = 0; i < sceneModeItems.size(); i++) {
				sceneModeItems.get(i).setState(ProtocolUtil.STATE_OFF);
			}
		} else {
			for (int i = 0; i < sceneModeItems.size(); i++) {
				sceneModeItems.get(i).setState(ProtocolUtil.STATE_EMPTY);
			}
		}
	}

	/**
	 * 设置情景模式，并且设置是新建还是修改模式
	 */
	public void setSceneMode(SceneMode sceneMode) {
		if (sceneMode == null) {// new
			type = 0;
			this.sceneMode = new SceneMode();
			configTitle.setText("新建情景模式");
			lblName.setVisible(false);
			tfName.setVisible(true);
		} else {// edit
			type = 1;
			this.sceneMode = sceneMode;
			tfName.setVisible(false);
			lblName.setVisible(true);
			lblName.setText(sceneMode.getName());// 这一句很奇怪，如果是放在上面的话，那么文本框不能被编辑！也许是因为设置为null的缘故
		}
		// 给sceneconfigitem设置scenemode
		for (int i = 0; i < sceneConfigItems.size(); i++) {
			sceneConfigItems.get(i).setSceneMode(this.sceneMode);
		}
	}

	/**
	 * 点击确定
	 */
	private void handleConfigOk() {
		String name = tfName.getText().trim();
		if (type == 0) {// new
			if (name == null || name.equalsIgnoreCase("")) {
				uiUtil.showErrorMessage(lblMessage, "请输入情景模式名称!");
				return;
			}
			if (!ScenePaneController.getInstance().validate(name)) {
				uiUtil.showErrorMessage(lblMessage, "该情景模式已经存在!");
				return;
			}
			sceneMode.setName(name);
			try {
				tool.newSceneMode(sceneMode);
				ScenePaneController.getInstance().refreshItems();
			} catch (Exception e) {
				uiUtil.showErrorMessage(lblMessage, "新建情景模式失败！");
				return;
			}
		} else {// edit
			try {
				tool.save(sceneMode);
			} catch (Exception e) {
				uiUtil.showErrorMessage(lblMessage, "保存情景模式失败！");
				return;
			}
		}
		
		stage.close();

	}

}
