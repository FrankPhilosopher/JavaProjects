package item;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import util.ResouceManager;
import beans.Room;
import controller.RoomSelectController;

/**
 * 加入房间中的item
 * 
 * @author yinger
 * 
 */
public class RoomSelectItem {

	public static final double WIDTH = 60;
	public static final double HEIGHT = 70;

	private StackPane stackPane;
	private Rectangle rectangle;

	private Room room;

	public RoomSelectItem(Room room) {
		this.room = room;
		initUI();
	}

	private void initUI() {
		stackPane = new StackPane();
		stackPane.setPrefSize(WIDTH, HEIGHT);
		stackPane.setAlignment(Pos.CENTER);
		stackPane.setPadding(new Insets(5));

		rectangle = new Rectangle(WIDTH, HEIGHT, Color.YELLOWGREEN);
		rectangle.setArcHeight(10);
		rectangle.setArcWidth(10);
		rectangle.setVisible(false);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setPrefSize(50, 50);
		box.setSpacing(5);

		ImageView iv = new ImageView(ResouceManager.getImage(ResouceManager.ROOM));
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				RoomSelectController.getInstance().setSelectedItem(RoomSelectItem.this);
			}
		});

		Text textName = new Text(room.getName());
		textName.setTextAlignment(TextAlignment.CENTER);

		box.getChildren().addAll(iv, textName);

		stackPane.getChildren().addAll(rectangle, box);
	}

	/**
	 * 得到房间
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * 得到这个节点
	 */
	public Node getNode() {
		return stackPane;
	}

	/**
	 * 隐藏背景
	 */
	public void hideRectangle() {
		rectangle.setVisible(false);
	}

	/**
	 * 显示背景
	 */
	public void showRectangle() {
		rectangle.setVisible(true);
	}

}
