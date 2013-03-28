package item;

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
import tool.ApplianceTool;
import util.KMP;
import util.ResouceManager;
import util.UiUtil;
import beans.ApplianceControl;
import controller.ApplianceControlPaneController;
import controller.NameEditController;
import controller.PicEditController;

/**
 * �ҵ�item
 * 
 * @author yinger
 * 
 */
public class ApplianceItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	private ApplianceControl control;
	private ApplianceTool tool;
	private UiUtil uiUtil;

	private VBox vbox;
	private ImageView iv;
	private Image onImage;
	private ContextMenu menu;

	public ApplianceItem(ApplianceControl control) {
		this.control = control;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		tool = ApplianceTool.getInstance();

		menu = new ContextMenu();
		MenuItem nameItem = new MenuItem("�޸�����");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(control);
			}
		});
		MenuItem iconItem = new MenuItem("�޸�ͼƬ");
		iconItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				PicEditController.type = 1;
				uiUtil.openDialog(ResouceManager.PIC_EDIT_PANE);
				PicEditController.getInstance().setType(1);
				PicEditController.getInstance().setApplianceControl(control);
			}
		});
		menu.getItems().addAll(nameItem, iconItem);

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		Text textName = new Text(control.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		if (!control.getIcon().equalsIgnoreCase(ResouceManager.APPLIANCE_DEFAULT)) {
			onImage = ResouceManager.getApplianceImage(control.getIcon());
		} else {
			setKmpImage();
			// onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_DEFAULT);
		}
		if (onImage == null) {
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_DEFAULT);
		}
		iv = new ImageView();
		iv.setFitWidth(90);
		iv.setFitHeight(90);
		iv.setCursor(Cursor.HAND);
		iv.setImage(onImage);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {// ��ĳ���ҵ�Ŀ��ư�ť���
					uiUtil.openDialog(ResouceManager.APPLIANCE_CONTROL_PANE);
					ApplianceControlPaneController.getInstance().setApplianceControl(control);
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

		textName.textProperty().bind(control.nameProperty());// ��textname�͵ƿص����ư�
		vbox.getChildren().addAll(iv, textName);
	}

	/**
	 * ���������Զ�ƥ��һ��ͼƬ ע�⣺ͼƬ����һ��default_appliance ����һ�� appliance_app ͼƬ��Ȼ��ͬ�����ǻ��ǲ�һ������;
	 */
	private void setKmpImage() {
		if (KMP.indexOf(control.getName(), "����") >= 0) {
			control.setIcon(ResouceManager.APPLIANCE_TV);
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_TV);
		} else if (KMP.indexOf(control.getName(), "�յ�") >= 0) {
			control.setIcon(ResouceManager.APPLIANCE_AIRCONDITION);
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_AIRCONDITION);
		} else if (KMP.indexOf(control.getName(), "����") >= 0) {
			control.setIcon(ResouceManager.APPLIANCE_FRIDGE);
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_FRIDGE);
		} else {
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_DEFAULT);
		}
		// �������Ĭ�ϵģ���ô��Ҫ���浽�ļ��У������´ν����ʱ��Ͳ������ж���
		if (!control.getIcon().equalsIgnoreCase(ResouceManager.APPLIANCE_DEFAULT)) {
			try {
				tool.refresh(control);
			} catch (Exception e) {
				uiUtil.showSystemExceptionMessage("��ȡͼƬʧ�ܣ�");
			}
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
	public ApplianceControl getDevice() {
		return control;
	}

	/**
	 * ��Ӧ������Ϣ
	 */
	public void doResponse(final int newstate) {
		control.setState(newstate);
	}

}
