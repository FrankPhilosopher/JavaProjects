package controller;

import item.PicItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tool.ApplianceTool;
import tool.SceneModeTool;
import util.AppliactionUtil;
import util.ResouceManager;
import util.UiUtil;
import app.SmartHome;
import beans.ApplianceControl;
import beans.SceneMode;

public class PicEditController implements Initializable {

	private static PicEditController instance;
	
	public static int type ;  // 0 为情景模式，1为家电
	
	private UiUtil uiUtil;
	private ApplianceTool appliancetool;
	private SceneModeTool scenemodetool;
	private List<PicItem> items;
	private ApplianceControl control;
	private SceneMode scenemode;
	private PicItem selectedItem;

	private double initX;
	private double initY;
	private Stage stage;// login stage

	@FXML
	private AnchorPane picPane;

	@FXML
	private TilePane picTilePane;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	public static PicEditController getInstance() {
		return instance;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		picPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		picPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		btnCancle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.close();
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
				if (selectedItem == null) {
					return;
				}
				if(type == 1){
					control.setIcon(selectedItem.getImageName());
					try {
						appliancetool.refresh(control);
					} catch (Exception e) {
						uiUtil.showSystemExceptionMessage("修改图片失败！");
					}
					AppliancePaneController.getInstance().refreshItems();
				}else {
					scenemode.setIcon(selectedItem.getImageName());
					if(AppliactionUtil.DEBUG) System.out.println("scenemode.icon:"+scenemode.getIcon());
					try {
						//tool.refresh(control);
						scenemodetool.refresh(scenemode);
						System.out.println("-----------");
					} catch (Exception e) {
						e.printStackTrace();
						uiUtil.showSystemExceptionMessage("修改图片失败！");
					}
					//AppliancePaneController.getInstance().refreshItems();
					ScenePaneController.getInstance().refreshItems();
				}
				stage.close();
			}
		});
	}

	private void initValues() {
		instance = this;
		stage = SmartHome.getModelStage();
		appliancetool = ApplianceTool.getInstance();
		scenemodetool = SceneModeTool.getInstance();
		uiUtil = UiUtil.getInstance();
		picTilePane.setPrefColumns(4);
		picTilePane.setHgap(10);
		items = new ArrayList<PicItem>();
		initItems();
	}

	/**
	 * 加载图片---家电设备图片
	 */
	private void initItems() {
		Map<String, Image> imageMap;
		if(type == 1) imageMap = ResouceManager.loadAppImages();
		else imageMap = ResouceManager.loadSceneImage();
			
		PicItem picItem;
		for (Map.Entry<String, Image> entry : imageMap.entrySet()) {
			picItem = new PicItem(entry.getKey(), entry.getValue());
			items.add(picItem);
			picTilePane.getChildren().add(picItem.getNode());
		}
		if (items.size() > 0) {
			setSelectedItem(items.get(0));// 第一张图片是默认选中的
		}
	}

	/**
	 * 得到items
	 */
	public List<PicItem> getItems() {
		return items;
	}

	/**
	 * 设置选中的item
	 */
	public void setSelectedItem(PicItem picItem) {
		selectedItem = picItem;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).hideRectangle();
		}
		picItem.showRectangle();
	}

	public void setApplianceControl(ApplianceControl control) {
		this.control = control;
	}
	
	public void setType(int type){
		this.type = type;
	}

	public void setScenemode(SceneMode scenemode) {
		this.scenemode = scenemode;
	}
}
