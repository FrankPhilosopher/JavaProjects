package item;

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
import tool.SystemTool;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.SecurityControl;

import communication.CommunicationUtil;

import controller.NameEditController;

/**
 * ����item
 * 
 * @author yinger
 * 
 */
public class SecurityItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	private SecurityControl control;
	private UiUtil uiUtil;
	private CommunicationUtil communicationUtil;
	private ProtocolUtil protocolUtil;

	private VBox vbox;
	private ImageView iv;
	private Image onImage;
	private Image offImage;
	private ContextMenu menu;

	public SecurityItem(SecurityControl control) {
		this.control = control;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
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
		menu.getItems().add(nameItem);

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		Text textName = new Text(control.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		onImage = ResouceManager.getImage(ResouceManager.SECURITYON);
		offImage = ResouceManager.getImage(ResouceManager.SECURITYOFF);
		iv = new ImageView();
		iv.setFitWidth(90);
		iv.setFitHeight(90);
		iv.setCursor(Cursor.HAND);
		changeImage();
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {
					if (control.getState() == ProtocolUtil.STATE_ON) {
						sendData(control.getCommand_close());
					} else {
						sendData(control.getCommand_open());
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
		// ��ͼƬ�͵Ƶ�״̬�󶨣�
		control.stateProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				Platform.runLater(new Runnable() {
					public void run() {
						changeImage();// ��֤������fx�߳���ִ�е�---���Ǽ�ʹ����Fx�߳���ͼƬ���ǲ��䣡
					}
				});
			}
		});
		textName.textProperty().bind(control.nameProperty());// ��textname�͵ƿص����ư�
		vbox.getChildren().addAll(iv, textName);
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
	public SecurityControl getDevice() {
		return control;
	}

	/**
	 * ��Ӧ������Ϣ
	 */
	public void doResponse(final int newstate) {
		control.setState(newstate);
		SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_SECURITY][control.getId()] = newstate;
	}

	/**
	 * ��������
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
