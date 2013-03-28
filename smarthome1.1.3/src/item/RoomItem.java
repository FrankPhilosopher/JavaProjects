package item;

import interfaces.IItem;
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
import tool.LightTool;
import tool.RoomTool;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.LightControl;
import beans.Room;
import controller.DeleteConfirmController;
import controller.LightPaneController;
import controller.MainPageController;
import controller.NameEditController;
import controller.RoomPaneController;

/**
 * ����item
 * 
 * @author yinger
 * 
 */
public class RoomItem implements IItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	private Room room;
	private UiUtil uiUtil;
	private RoomTool roomTool;
	private LightTool lightTool;

	private VBox vbox;
	private ImageView iv;
	private Image image;
	private ContextMenu menu;

	public RoomItem(Room room) {
		this.room = room;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		roomTool = RoomTool.getInstance();
		lightTool = LightTool.getInstance();

		menu = new ContextMenu();
		MenuItem nameItem = new MenuItem("�޸�����");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(room);
			}
		});
		MenuItem delItem = new MenuItem("ɾ������");
		delItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				deleteRoom();
			}
		});
		menu.getItems().addAll(nameItem, delItem);

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		image = ResouceManager.getImage(ResouceManager.ROOM);
		iv = new ImageView();
		iv.setFitWidth(90);
		iv.setFitHeight(90);
		iv.setCursor(Cursor.HAND);
		iv.setImage(image);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {// ���뷿��
					MainPageController.getInstance().openPane(ProtocolUtil.DEVICETYPE_ROOM);
					RoomPaneController.getInstance().setRoom(room);// ����room
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

		Text textName = new Text(room.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		vbox.getChildren().addAll(iv, textName);
		textName.textProperty().bind(room.nameProperty());// ��textname�͵ƿص����ư�
	}

	private void deleteRoom() {
		uiUtil.openDialog(ResouceManager.DELETE_CONFIRM);// ��ʾ�Ƿ����ɾ��
		new Thread(new Runnable() {// Ϊ�˷�ֹ�������������һ���߳������ɾ��ȷ�ϵĲ���
					public void run() {
						StringBuffer confirmResult = DeleteConfirmController.getInstance().confirmResult;
						synchronized (confirmResult) {// �ȴ��������
							while (confirmResult.toString().length() == 0)
								try {
									confirmResult.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
						}
						if (confirmResult.toString().equalsIgnoreCase("YES")) {
							try {
								roomTool.deleteRoom(room);// ɾ���˷���֮��Ҫ�������е��豸��roomid����ΪĬ��ֵ
								LightControl control = null;
								for (int i = 0; i < room.getLightItems().size(); i++) {
									control = room.getLightItems().get(i).getDevice();
									control.setRoomid(LightControl.EMPTY_ROOM);// �Ļ�ȥ�ˣ����ǲ���Ҫ���棡
									lightTool.quitRoom(control);// ���������˳��ˣ�����control���ǳ����Ǹ�roomid
								}
							} catch (Exception e) {
								uiUtil.showSystemExceptionMessage("ɾ������ʧ�ܣ�");
								return;
							}
							Platform.runLater(new Runnable() {// ȷ����������fx�߳���ִ�е�
								public void run() {
									LightPaneController.getInstance().refreshItems();
								}
							});
						}// �������yes����ô��ɶҲ����--�߳��˳���
					}
				}).start();
	}

	/**
	 * �õ�����ڵ�
	 */
	public Node getNode() {
		return vbox;
	}

	/**
	 * �õ��豸
	 */
	public Room getRoom() {
		return room;
	}

}
