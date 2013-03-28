package controller;

import item.LightItem;

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
import javafx.scene.text.Text;
import javafx.util.Duration;
import tool.LightTool;
import util.ProtocolUtil;
import util.UiUtil;
import beans.Room;

/**
 * ����ĳ���������������
 * 
 * @author yinger
 * 
 */
public class RoomPaneController implements Initializable {

	private static RoomPaneController controller;
	private UiUtil uiUtil;

	private Room room;

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
	private Text roomName;
	@FXML
	private Button btnExitRoom;

	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	private List<LightItem> items;
	private LightTool lightTool;

	public static RoomPaneController getInstance() {
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

		btnExitRoom.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				// �˳��÷��䣬����ƿؽ��棡
				MainPageController.getInstance().openPane(ProtocolUtil.DEVICETYPE_LIGHT);
				LightPaneController.getInstance().refreshItems();// Ҫˢ��һ�Σ�
			}
		});

	}

	/**
	 * ���÷���
	 */
	public void setRoom(Room room) {
		this.room = room;
		roomName.setText(room.getName());
		refreshItems();
	}

	/**
	 * ��ʼ������
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		lightTool = LightTool.getInstance();
		room = new Room();
		items = new ArrayList<LightItem>();
		roomName.setText("");
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		tilePane.setPrefTileWidth(LightItem.WIDTH);
		tilePane.setPrefTileHeight(LightItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
	}

	/**
	 * ˢ���豸�б�
	 */
	public void refreshItems() {
		pageIndex = 1;// �ص���һҳ
		items = room.getLightItems();
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
			tilePane.getChildren().add(((LightItem) items.get(i)).getNode());// ������light
		}
	}

}
