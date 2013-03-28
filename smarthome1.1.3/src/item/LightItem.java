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
 * 灯控item
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
		MenuItem nameItem = new MenuItem("修改名称");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(control);
			}
		});

		if (control.getRoomid().equals(LightControl.EMPTY_ROOM)) {// 没有加入房间
			MenuItem roomItem = new MenuItem("加入房间");
			roomItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					uiUtil.openDialog(ResouceManager.ROOM_SELECT_PANE);
					RoomSelectController.getInstance().setLightControl(control);
				}
			});
			menu.getItems().addAll(nameItem, roomItem);
		} else {// 加入了房间
			MenuItem roomItem = new MenuItem("退出房间");
			roomItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					try {
						// 首先得到原来的那个room
						List<Room> roomList = LightPaneController.getInstance().getRooms();
						Room room = null;
						for (int i = 0; i < roomList.size(); i++) {
							if (control.getRoomid().equals(roomList.get(i).getId())) {
								room = roomList.get(i);
								break;
							}
						}
						if (room == null) {// 应该不会出现这种情况
							return;
						}
						// 然后要删除这个lightitem
						room.getLightItems().remove(LightItem.this);// 为了界面刷新准备
						control.setRoomid(LightControl.EMPTY_ROOM);// 这里只是灯退出了房间，但是房间还是保存了这个灯
						tool.quitRoom(control);
						// 界面刷新！
						RoomPaneController.getInstance().refreshItems();
					} catch (Exception e) {
						uiUtil.showSystemExceptionMessage("退出房间失败！");
					}
				}
			});
			menu.getItems().addAll(nameItem, roomItem);
		}

		if (control.getHasIntensity() == LightControl.INTENSITY_YES) {
			MenuItem upItem = new MenuItem("调亮");
			upItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (control.getState() == ProtocolUtil.STATE_OFF) {
						return;
					}
					sendData(control.getCommand_buttonup());
				}
			});
			MenuItem downItem = new MenuItem("调暗");
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

		// 设备类型--图片不同
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
		// 将图片和灯的状态绑定！
		control.stateProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				Platform.runLater(new Runnable() {
					public void run() {
						changeImage();
					}
				});
			}
		});
		textName.textProperty().bind(control.nameProperty());// 将textname和灯控的名称绑定
	}

	/**
	 * 修改显示图片
	 */
	private void changeImage() {
		if (control.getState() == ProtocolUtil.STATE_OFF) {
			iv.setImage(offImage);
		} else {
			iv.setImage(onImage);
		}
	}

	/**
	 * 得到这个节点
	 */
	public Node getNode() {
		return vbox;
	}

	/**
	 * 得到设备
	 */
	public LightControl getDevice() {
		return control;
	}

	/**
	 * 响应反馈信息
	 */
	public void doResponse(final int newstate) {
		control.setState(newstate);
		SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][control.getId()] = newstate;
	}

	/**
	 * 发送命令 指令发送失败！----command没能发送出去 命令发送失败！----command发送出去后返回命令发送失败
	 */
	private void sendData(int command) {
		if (command == ProtocolUtil.COMMAND_EMPTY) {
			uiUtil.showSystemExceptionMessage("该设备还未入网！");
			return;
		}
		try {
			communicationUtil.sendData(protocolUtil.packInstructionRequest(command));
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage("指令发送失败！");
		}
	}

}
