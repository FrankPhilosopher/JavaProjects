package controller;

import item.SceneItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import tool.SceneModeTool;
import util.AppliactionUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.SceneMode;

/**
 * 情景模式面板控制器
 * 
 * @author yinger
 * 
 */
public class ScenePaneController implements Initializable {

	private static ScenePaneController controller;
	private UiUtil uiUtil;

	@FXML
	private ImageView ivPrev;
	@FXML
	private ImageView ivNext;

	@FXML
	private Button btnAdd;

	@FXML
	private AnchorPane topPane;
	@FXML
	private AnchorPane centerPane;
	@FXML
	private TilePane tilePane;

	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	private List<SceneItem> items;
	private SceneModeTool tool;

	public static ScenePaneController getInstance() {
		
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		
		initEvents();
		initValues();
	}

	/**
	 * 事件处理
	 */
	private void initEvents() {
		ivPrev.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivPrev.getOpacity() == 0) {// 要加上这个判断，因为如果看不见的话，这个imageview是不生效的
					arg0.consume();
				} else {
					pageIndex--;
					refreshPage();
//					new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(tilePane.translateXProperty(), 40)),
//							new KeyFrame(Duration.seconds(0.5), new KeyValue(tilePane.translateXProperty(), 20)),
//							new KeyFrame(Duration.seconds(1), new KeyValue(tilePane.translateXProperty(), 0))).play();
				}
			}
		});

		ivNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivNext.getOpacity() == 0) {
					arg0.consume();
				} else {
					pageIndex++;
					refreshPage();
//					new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(tilePane.translateXProperty(), -40)),
//							new KeyFrame(Duration.seconds(0.5), new KeyValue(tilePane.translateXProperty(), -20)),
//							new KeyFrame(Duration.seconds(1), new KeyValue(tilePane.translateXProperty(), 0))).play();
				}
			}
		});

		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.SECENEMODE_CONFIG_PANE);
				SceneConfigController.getInstance().setSceneMode(null);// 新建时传入null
			}
		});

	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		tool = SceneModeTool.getInstance();
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		pageIndex = 1;
		tilePane.setPrefTileWidth(SceneItem.WIDTH);
		tilePane.setPrefTileHeight(SceneItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
		items = new ArrayList<SceneItem>();
		// 如果在这里添加监听器没有用！
		refreshItems();
	}

	/**
	 * 刷新设备列表
	 */
	public void refreshItems() {
		//pageIndex = 1;// 注意这里pageIndex=1，一旦添加了新的或者删除了就会回到第一页，如果没有此句就还是停留在这一页
		items.clear();
		List<SceneMode> controls = null;
		try {
			controls = tool.loadAllSceneMode();
		} catch (Exception e) {// TODO：加载设备列表出现异常！
			uiUtil.showSystemExceptionMessage(e.getMessage());
			return;
		}
		if (controls == null) {
			controls = new ArrayList<SceneMode>();// size = 0的列表
		}
		SceneItem item;
		for (int i = 0; i < controls.size(); i++) {
			item = new SceneItem(controls.get(i));
			items.add(item);
		}
		itemSum = items.size();
		if (itemSum % PAGESIZE == 0) {
			totalPage = itemSum / PAGESIZE;
		} else {
			totalPage = itemSum / PAGESIZE + 1;
		}
		refreshPage();
	}

	/**
	 * 刷新界面
	 */
	public void refreshPage() {
		if (totalPage > pageIndex) {// 调整左右方向按钮的显示状况
			ivNext.setOpacity(1);
		} else {
			ivNext.setOpacity(0);
		}
		if (pageIndex > 1) {
			ivPrev.setOpacity(1);
		} else {
			ivPrev.setOpacity(0);
		}
		tilePane.getChildren().clear();// 首先清空
		int start = (pageIndex - 1) * PAGESIZE;// 从0开始的
		int end = (itemSum - 1) <= (pageIndex * PAGESIZE - 1) ? (itemSum - 1) : (pageIndex * PAGESIZE - 1);
		for (int i = start; i <= end; i++) {// 加入到tilepane
			tilePane.getChildren().add(items.get(i).getNode());
		}
	}

	/**
	 * 验证这个情景模式名称是否重复了
	 */
	public boolean validate(String name) {
		for (int i = 0; i < itemSum; i++) {
			if (items.get(i).getSceneMode().getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

}
