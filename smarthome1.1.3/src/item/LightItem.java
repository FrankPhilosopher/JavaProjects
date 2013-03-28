package item;

import interfaces.IItem;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import tool.SystemTool;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.LightControl;
import beans.Room;

import communication.CommunicationUtil;

import controller.LightPaneController;
import controller.NameEditController;
import controller.RoomPaneController;
import controller.RoomSelectController;

/**
 * �ƿ�item
 * 
 * @author yinger
 * 
 */
public class LightItem implements IItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	private LightControl control;
	private UiUtil uiUtil;
	private LightTool tool;
	private CommunicationUtil communicationUtil;
	private ProtocolUtil protocolUtil;

	private VBox vbox;
	private ImageView iv;
	private Image onImage;
	private Image offImage;
	private ContextMenu menu;

	public LightItem(LightControl control) {
		this.control = control;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		tool = LightTool.getInstance();
		communicationUtil = CommunicationUtil.getInstance();
		protocolUtil = ProtocolUtil.getInstance();

		menu = new ContextMenu();
		MenuItem nameItem = new MenuItem("�޸�����");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(control);
			}
		});

		if (control.getRoomid().equals(LightControl.EMPTY_ROOM)) {// û�м��뷿��
			MenuItem roomItem = new MenuItem("���뷿��");
			roomItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					uiUtil.openDialog(ResouceManager.ROOM_SELECT_PANE);
					RoomSelectController.getInstance().setLightControl(control);
				}
			});
			menu.getItems().addAll(nameItem, roomItem);
		} else {// �����˷���
			MenuItem roomItem = new MenuItem("�˳�����");
			roomItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					try {
						// ���ȵõ�ԭ�����Ǹ�room
						List<Room> roomList = LightPaneController.getInstance().getRooms();
						Room room = null;
						for (int i = 0; i < roomList.size(); i++) {
							if (control.getRoomid().equals(roomList.get(i).getId())) {
								room = roomList.get(i);
								break;
							}
						}
						if (room == null) {// Ӧ�ò�������������
							return;
						}
						// Ȼ��Ҫɾ�����lightitem
						room.getLightItems().remove(LightItem.this);// Ϊ�˽���ˢ��׼��
						control.setRoomid(LightControl.EMPTY_ROOM);// ����ֻ�ǵ��˳��˷��䣬���Ƿ��仹�Ǳ����������
						tool.quitRoom(control);
						// ����ˢ�£�
						RoomPaneController.getInstance().refreshItems();
					} catch (Exception e) {
						uiUtil.showSystemExceptionMessage("�˳�����ʧ�ܣ�");
					}
				}
			});
			menu.getItems().addAll(nameItem, roomItem);
		}

		if (control.getHasIntensity() == LightControl.INTENSITY_YES) {
			MenuItem upItem = new MenuItem("����");
			upItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (control.getState() == ProtocolUtil.STATE_OFF) {
						return;
					}
					sendData(control.getCommand_buttonup());
				}
			});
			MenuItem downItem = new MenuItem("����");
			downItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (control.getState() == ProtocolUtil.STATE_OFF) {
						return;
					}
					sendData(control.getCommand_buttondown());
				}
			});
			menu.getItems().addAll(upItem, downItem);
		}

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		// �豸����--ͼƬ��ͬ
		if (control.getType() == LightControl.TYPE_LIGHT) {
			onImage = ResouceManager.getImage(ResouceManager.LIGHTON);
			offImage = ResouceManager.getImage(ResouceManager.LIGHTOFF);
		} else {
			onImage = ResouceManager.getImage(ResouceManager.OUTLETON);
			offImage = ResouceManager.getImage(ResouceManager.OUTLETOFF);
		}
		iv = new ImageView();
		iv.setFitWidth(90);
		iv.setFitHeight(90);
		iv.setCursor(Cursor.HAND);
		changeImage();
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {
					if (control.getState() == ProtocolUtil.STATE_ON) {
						sendData(control.getCommand_buttonoff());
					} else {
						sendData(control.getCommand_buttonon());
					}
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

		Text textName = new Text(control.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		vbox.getChildren().addAll(iv, textName);
		// ��ͼƬ�͵Ƶ�״̬�󶨣�
		control.stateProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				Platform.runLater(new Runnable() {
					public void run() {
						changeImage();
					}
				});
			}
		});
		textName.textProperty().bind(control.nameProperty());// ��textname�͵ƿص����ư�
	}

	/**
	 * �޸���ʾͼƬ
	 */
	private void changeImage() {
		if (control.getState() == ProtocolUtil.STATE_OFF) {
			iv.setImage(offImage);
		} else {
			iv.setImage(onImage);
		}
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
	public LightControl getDevice() {
		return control;
	}

	/**
	 * ��Ӧ������Ϣ
	 */
	public void doResponse(final int newstate) {
		control.setState(newstate);
		SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][control.getId()] = newstate;
	}

	/**
	 * �������� ָ���ʧ�ܣ�----commandû�ܷ��ͳ�ȥ �����ʧ�ܣ�----command���ͳ�ȥ�󷵻������ʧ��
	 */
	private void sendData(int command) {
		if (command == ProtocolUtil.COMMAND_EMPTY) {
			uiUtil.showSystemExceptionMessage("���豸��δ������");
			return;
		}
		try {
			communicationUtil.sendData(protocolUtil.packInstructionRequest(command));
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage("ָ���ʧ�ܣ�");
		}
	}

}
