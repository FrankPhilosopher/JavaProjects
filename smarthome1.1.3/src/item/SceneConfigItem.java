package item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import beans.DoorControl;
import beans.LightControl;
import beans.SceneMode;
import beans.SceneModeItem;

/**
 * �龰ģʽ�����е�item
 * 
 * @author yinger
 * 
 */
public class SceneConfigItem {

	public static final double WIDTH = 100;
	public static final double HEIGHT = 100;

	public static final String REMAIN = "����";
	public static final String OPEN = "��";
	public static final String CLOSE = "��";

	private SceneMode sceneMode;
	private SceneModeItem sceneModeItem;
	private UiUtil uiUtil;

	private VBox vbox;
	private ImageView iv;
	private Image onImage;
	private Image offImage;
	private Text textState;

	public SceneConfigItem(SceneMode scenemode, SceneModeItem sceneModeItem) {
		this.sceneMode = scenemode;
		this.sceneModeItem = sceneModeItem;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(4);

		Text textName = new Text(sceneModeItem.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);
		textName.setTextAlignment(TextAlignment.CENTER);

		textState = new Text(REMAIN);
		textState.getStyleClass().add(ResouceManager.ITEM_TEXT);
		textState.setFill(Color.BLUE);
		textState.setTextAlignment(TextAlignment.CENTER);

		iv = new ImageView();
		iv.setFitWidth(60);
		iv.setFitHeight(60);
		iv.setCursor(Cursor.HAND);
		setOnOffImage();// initUI��ʱ����Ҫ����image������ʼ����offImage
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {// �л�
				if (sceneModeItem.getState() == ProtocolUtil.STATE_ON) {
					sceneModeItem.setState(ProtocolUtil.STATE_OFF);
				} else if (sceneModeItem.getState() == ProtocolUtil.STATE_OFF) {
					sceneModeItem.setState(ProtocolUtil.STATE_EMPTY);
				} else if (sceneModeItem.getState() == ProtocolUtil.STATE_EMPTY) {
					sceneModeItem.setState(ProtocolUtil.STATE_ON);
				}
			}
		});

		iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.playFadeTransition(iv, 2, 0.5f, 1.0f, 1, false);
			}
		});

		vbox.getChildren().addAll(textName, iv, textState);
		sceneModeItem.stateProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				changeState();
			}
		});
		sceneModeItem.setState(ProtocolUtil.STATE_EMPTY);// �����ʼ����ʱ�������changeState����
	}

	/**
	 * �ı�״̬֮�����Ĳ���
	 */
	private void changeState() {
		if (sceneModeItem.getState() == ProtocolUtil.STATE_EMPTY) {
			textState.setText(REMAIN);
			iv.setImage(offImage);
			if (sceneMode != null && sceneMode.getItems().contains(sceneModeItem)) {
				sceneMode.getItems().remove(sceneModeItem);
			}
		} else if (sceneModeItem.getState() == ProtocolUtil.STATE_OFF) {
			textState.setText(CLOSE);
			iv.setImage(offImage);
			if (sceneMode != null && !sceneMode.getItems().contains(sceneModeItem)) {
				sceneMode.getItems().add(sceneModeItem);
			}
		} else {
			textState.setText(OPEN);
			iv.setImage(onImage);
			if (sceneMode != null && !sceneMode.getItems().contains(sceneModeItem)) {
				sceneMode.getItems().add(sceneModeItem);
			}
		}
	}

	/**
	 * ���ÿ��͹ص�ͼƬ
	 */
	private void setOnOffImage() {
		if (sceneModeItem.getTypeId() == ProtocolUtil.DEVICETYPE_LIGHT) {
			if (sceneModeItem.getDetailType() == LightControl.TYPE_LIGHT) {// ��-��
				onImage = ResouceManager.getImage(ResouceManager.LIGHTON);
				offImage = ResouceManager.getImage(ResouceManager.LIGHTOFF);
			} else {// ��-����
				onImage = ResouceManager.getImage(ResouceManager.OUTLETON);
				offImage = ResouceManager.getImage(ResouceManager.OUTLETOFF);
			}
		} else if (sceneModeItem.getTypeId() == ProtocolUtil.DEVICETYPE_DOOR) {
			if (sceneModeItem.getDetailType() == DoorControl.TYPE_WINDOW) {// �Ŵ�-��
				onImage = ResouceManager.getImage(ResouceManager.WINDOWOPEN);
				offImage = ResouceManager.getImage(ResouceManager.WINDOWCLOSE);
			} else {// �Ŵ�-��
				onImage = ResouceManager.getImage(ResouceManager.DOOROPEN);
				offImage = ResouceManager.getImage(ResouceManager.DOORCLOSE);
			}
		} else if (sceneModeItem.getTypeId() == ProtocolUtil.DEVICETYPE_SECURITY) {// ����
			onImage = ResouceManager.getImage(ResouceManager.SECURITYON);
			offImage = ResouceManager.getImage(ResouceManager.SECURITYOFF);
		} else {// �ҵ�
			onImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_DEFAULT);
			offImage = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_DEFAULT);
		}
	}

	/**
	 * �õ�����ڵ�
	 */
	public Node getNode() {
		return vbox;
	}

	/**
	 * �õ��龰ģʽitem
	 */
	public SceneModeItem getSceneModeItem() {
		return sceneModeItem;
	}

	/**
	 * �õ��龰ģʽ
	 */
	public SceneMode getSceneMode() {
		return sceneMode;
	}

	/**
	 * �����龰ģʽ
	 */
	public void setSceneMode(SceneMode sceneMode) {
		this.sceneMode = sceneMode;
		resetSceneConfigItem();
	}

	/**
	 * ����scenemode��������SceneConfigItem
	 */
	private void resetSceneConfigItem() {
		int index = sceneMode.getItems().indexOf(sceneModeItem);
		if (index >= 0) {
			sceneModeItem.setState(sceneMode.getItems().get(index).getState());// ���õ�ǰ��item��stateֵ��Ȼ��ͻᴥ��changeState����������
			sceneMode.getItems().set(index, sceneModeItem);// ��һ������Ҫ�������ͱ�֤��item�ı�Ӧ�õ�scenemode��
		}
	}

}
