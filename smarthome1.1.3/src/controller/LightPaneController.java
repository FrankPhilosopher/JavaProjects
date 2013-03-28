package controller;

import interfaces.IItem;
import interfaces.ISceneMode;
import item.LightItem;
import item.RoomItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import tool.LightTool;
import tool.RoomTool;
import util.ProtocolUtil;
import util.ResouceManager;
import util.SortComparator;
import util.SortComparatorByType;
import util.UiUtil;
import beans.LightControl;
import beans.Room;
import beans.SceneModeItem;

/**
 * 灯控面板控制器
 * 
 * @author yinger
 * 
 */
public class LightPaneController implements Initializable, ISceneMode {

	private static LightPaneController controller;
	private UiUtil uiUtil;

	@FXML
	private ImageView ivPrev;
	@FXML
	private ImageView ivNext;

	@FXML
	private AnchorPane topPane;
	@FXML
	private AnchorPane centerPane;
	@FXML
	private TilePane tilePane;

	@FXML
	private Button btnAddRoom;

	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	private List items;
	private List<RoomItem> roomItemList;
	private List<LightItem> lightItemList;

	private List<Room> rooms;

	private LightTool lightTool;
	private RoomTool roomTool;

	public static LightPaneController getInstance() {
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

		btnAddRoom.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.ROOM_ADD_PANE);
			}
		});

	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		roomTool = RoomTool.getInstance();
		lightTool = LightTool.getInstance();
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		tilePane.setPrefTileWidth(LightItem.WIDTH);
		tilePane.setPrefTileHeight(LightItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
		items = new ArrayList<IItem>();
		refreshItems();
	}

	/**
	 * 刷新设备列表
	 */
	public void refreshItems() {
		pageIndex = 1;// 回到第一页
		if (lightItemList != null) {
			lightItemList.clear();
		}
		lightItemList = new ArrayList<LightItem>();
		if (roomItemList != null) {
			roomItemList.clear();
		}
		roomItemList = new ArrayList<RoomItem>();
		if (items != null) {
			items.clear();
		}

		// 首先加载房间
		// List<Room> rooms = null;
		try {
			rooms = roomTool.loadRooms();
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage(e.getMessage());
		}
		if (rooms == null) {
			rooms = new ArrayList<Room>();// size = 0的列表
		}
		RoomItem roomItem;
		for (int i = 0; i < rooms.size(); i++) {
			roomItem = new RoomItem(rooms.get(i));
			roomItemList.add(roomItem);
			items.add(roomItem);
		}

		// 然后加载灯控
		List<LightControl> controls = null;
		try {
			controls = lightTool.loadControls();
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage(e.getMessage());
		}
		if (controls == null) {
			controls = new ArrayList<LightControl>();// size = 0的列表
		}
		// 灯控需要排序
		SortComparator comparator = new SortComparator();
		SortComparatorByType cbytype = new SortComparatorByType();
		Collections.sort(controls, comparator);
		Collections.sort(controls, cbytype);

		LightItem lightItem;
		LightControl lightControl;
		for (int i = 0; i < controls.size(); i++) {
			lightControl = controls.get(i);
			lightItem = new LightItem(lightControl);
			lightItemList.add(lightItem);// 注意这里，所有的灯控item都加入到了lightItemList，处理反馈时遍历的就是lightItemList
			if (lightControl.getRoomid().equalsIgnoreCase(LightControl.EMPTY_ROOM)) {// 如果没有属于某个房间
				items.add(lightItem);
			} else {// 属于某个房间，将这个设备添加到那个房间
				for (int j = 0; j < rooms.size(); j++) {
					if (rooms.get(j).getId().equals(lightControl.getRoomid())) {
						rooms.get(j).getLightItems().add(lightItem);// 注意，这里添加的是lightItem，这样的话就是和lightItemList是引用同一个对象，处理反馈时是一致的
						break;// 退出for
					}
				}
			}
		}
		// 最后综合到一起
		// Collections.addAll(items, roomItemList, lightItemList);// 注意这里！这样写是错误的

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

		IItem item;
		for (int i = start; i <= end; i++) {// 加入到tilepane
			if (i < roomItemList.size()) {
				item = (RoomItem) items.get(i);
			} else {
				item = (LightItem) items.get(i);
			}
			tilePane.getChildren().add(item.getNode());// 后面是light
		}
	}

	/**
	 * 处理反馈信息---只有灯控设备才会处理反馈信息
	 */
	public void doResponse(int id, int newstate) {
		for (int i = 0; i < lightItemList.size(); i++) {
			LightItem item = lightItemList.get(i);
			if (item.getDevice().getId() == id) {
				item.doResponse(newstate);
				break;
			}
		}
	}

	/**
	 * 加载情景模式配置item
	 */
	@Override
	public List<SceneModeItem> loadSceneModeItems() {
		List<SceneModeItem> sceneModeItems = new ArrayList<SceneModeItem>();
		LightControl control;
		SceneModeItem item;
		for (int i = 0; i < lightItemList.size(); i++) {
			item = new SceneModeItem();
			control = lightItemList.get(i).getDevice();
			item.setName(control.getName());
			item.setTypeId(ProtocolUtil.DEVICETYPE_LIGHT);
			item.setDeviceId(control.getId());
			item.setDetailType(control.getType());
			item.setState(ProtocolUtil.STATE_OFF);
			item.setCommand_off(control.getCommand_buttonoff());
			item.setCommand_on(control.getCommand_buttonon());
			sceneModeItems.add(item);
		}
		return sceneModeItems;
	}

	/**
	 * 得到房间列表
	 */
	public List<Room> getRooms() {
		return rooms;
	}

}
