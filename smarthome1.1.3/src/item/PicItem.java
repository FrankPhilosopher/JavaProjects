package item;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.PicEditController;

/**
 * 修改图片item
 * 
 * @author yinger
 * 
 */
public class PicItem {

	public static final double WIDTH = 60;
	public static final double HEIGHT = 60;

	private StackPane stackPane;
	private Rectangle rectangle;
	private Image image;
	private String imageName;

	public PicItem(String imageName, Image image) {
		this.imageName = imageName;
		this.image = image;
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

		ImageView iv = new ImageView(image);
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//if(PicEditController.type == 1)
					PicEditController.getInstance().setSelectedItem(PicItem.this);
				//else 
			}
		});

		stackPane.getChildren().addAll(rectangle, iv);
	}

	/**
	 * 得到这个节点
	 */
	public Node getNode() {
		return stackPane;
	}

	/**
	 * 得到图片名称
	 */
	public String getImageName() {
		return imageName;
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
