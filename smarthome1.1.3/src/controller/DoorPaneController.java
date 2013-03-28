package controller;

import interfaces.ISceneMode;
import item.DoorItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import tool.DoorTool;
import util.ProtocolUtil;
import util.SortComparator;
import util.SortComparatorByType;
import util.UiUtil;
import beans.DoorControl;
import beans.SceneModeItem;

/**
 * �Ŵ�������������
 * 
 * @author yinger
 * 
 */
public class DoorPaneController implements Initializable, ISceneMode {

	private static DoorPaneController controller;
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

	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	private List<DoorItem> items;
	private DoorTool tool;

	public static DoorPaneController getInstance() {
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	/**
	 * �¼�����
	 */
	private void initEvents() {
		ivPrev.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivPrev.getOpacity() == 0) {// Ҫ��������жϣ���Ϊ����������Ļ������imageview�ǲ���Ч��
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

	}

	/**
	 * ��ʼ������
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		tool = DoorTool.getInstance();
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		tilePane.setPrefTileWidth(DoorItem.WIDTH);
		tilePane.setPrefTileHeight(DoorItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
		items = new ArrayList<DoorItem>();
		refreshItems();
	}

	/**
	 * ˢ���豸�б�
	 */
	public void refreshItems() {
		pageIndex = 1;// �ص���һҳ
		items.clear();
		List<DoorControl> controls = null;
		try {
			controls = tool.loadControls();
		} catch (Exception e) {// TODO�������豸�б�����쳣��
			uiUtil.showSystemExceptionMessage(e.getMessage());
		}
		if (controls == null) {
			controls = new ArrayList<DoorControl>();// size = 0���б�
		}

		SortComparator comparator = new SortComparator();
		SortComparatorByType cbytype = new SortComparatorByType();
		Collections.sort(controls, comparator);
		Collections.sort(controls, cbytype);

		DoorItem item;
		for (int i = 0; i < controls.size(); i++) {
			item = new DoorItem(controls.get(i));
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
	 * ˢ�½���
	 */
	public void refreshPage() {
		if (totalPage > pageIndex) {// �������ҷ���ť����ʾ״��
			ivNext.setOpacity(1);
		} else {
			ivNext.setOpacity(0);
		}
		if (pageIndex > 1) {
			ivPrev.setOpacity(1);
		} else {
			ivPrev.setOpacity(0);
		}
		tilePane.getChildren().clear();// �������
		int start = (pageIndex - 1) * PAGESIZE;// ��0��ʼ��
		int end = (itemSum - 1) <= (pageIndex * PAGESIZE - 1) ? (itemSum - 1) : (pageIndex * PAGESIZE - 1);
		for (int i = start; i <= end; i++) {// ���뵽tilepane
			tilePane.getChildren().add(items.get(i).getNode());
		}
	}

	/**
	 * ��������Ϣ
	 */
	public void doResponse(int id, int newstate) {
		for (int i = 0; i < itemSum; i++) {
			DoorItem item = items.get(i);
			if (item.getDevice().getId() == id) {
				item.doResponse(newstate);
				break;
			}
		}
	}

	/**
	 * �����龰ģʽ����item
	 */
	@Override
	public List<SceneModeItem> loadSceneModeItems() {
		List<SceneModeItem> sceneModeItems = new ArrayList<SceneModeItem>();
		DoorControl control;
		SceneModeItem item;
		for (int i = 0; i < itemSum; i++) {
			item = new SceneModeItem();
			control = items.get(i).getDevice();
			item.setName(control.getName());
			item.setTypeId(ProtocolUtil.DEVICETYPE_DOOR);
			item.setDeviceId(control.getId());
			item.setDetailType(control.getType());
			item.setState(ProtocolUtil.STATE_OFF);
			item.setCommand_off(control.getCommand_buttonclose());
			item.setCommand_on(control.getCommand_buttonopen());
			sceneModeItems.add(item);
		}
		return sceneModeItems;
	}

}
