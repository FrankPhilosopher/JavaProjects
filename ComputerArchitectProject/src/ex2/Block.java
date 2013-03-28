package ex2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Block extends StackPane {

	private IntegerProperty number = new SimpleIntegerProperty();
	private IntegerProperty time = new SimpleIntegerProperty();
	private ObjectProperty<Color> color = new SimpleObjectProperty<Color>();
	private Text text;
	private Rectangle rectangle;
	public static final double BLOCKSIZE = 40;

	public final void setNumber(int value) {
		number.set(value);
	}

	public final int getNumber() {
		return number.get();
	}

	public IntegerProperty numberProperty() {
		return number;
	}

	public final void setTime(int value) {
		time.set(value);
	}

	public final int getTime() {
		return time.get();
	}

	public IntegerProperty timeProperty() {
		return time;
	}

	public final Color getColor() {
		return color.get();
	}

	public final void setColor(Color value) {
		color.set(value);
	}

	public ObjectProperty<Color> colorProperty() {
		return color;
	}

	public Block(int number, Color color) {
		setNumber(number);
		setColor(color);
		createBlock();
	}

	// blank block
	public static Block getBlankBlock() {
		return new Block(-1, Color.WHITE);// blank block:white
	}

	private void createBlock() {
		rectangle = new Rectangle(BLOCKSIZE, BLOCKSIZE);
		rectangle.setFill(color.get());
		rectangle.setOpacity(0.5);
		rectangle.setStroke(Color.BLACK);
		rectangle.fillProperty().bind(color);// bind block color with rectangle color
		// so when the block color changes,the rectangle color will also change!
		// by default,it will not change,remember this!
		// by the way,we can also add a changeListener to the color!then change the rectangle color

		text = new Text();
		text.setFill(Color.RED);
		text.setFont(Font.font("Amble Cn", FontWeight.BOLD, 20));
		if (number.get() >= 0) {// only number > 0 can be displayed
//			text.setText(String.valueOf(number.get()) + "(" + String.valueOf(time.get()) + ")");
			text.setText(String.valueOf(number.get()));
		} else {
			text.setText("");// when number<0,do not show the number
		}
//		text.textProperty().bind(observable)//text can not bind to number,for they are different type
		// so we can add a ChangeListener to number!when number changes,the text changes!
		NumberChangeListener numberLiteral = new NumberChangeListener();
		number.addListener(numberLiteral);
		time.addListener(numberLiteral);
		this.getChildren().addAll(rectangle, text);// block is combined by rectangle and text
	}

	class NumberChangeListener implements ChangeListener {
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			if (number.get() >= 0) {// only number > 0 can be displayed
				text.setText(String.valueOf(number.get()));
//				text.setText(String.valueOf(number.get()) + "(" + String.valueOf(time.get()) + ")");
			} else {
				text.setText("");// when number<0,do not show the number
			}
		}
	}

	@Override
	// if number is same,then the block is same
	public boolean equals(Object obj) {
		if (obj instanceof Block) {
			return ((Block) obj).getNumber() == this.getNumber();
		}
		return false;
	}

	// clone block data from block2 to block1
	public static void cloneBlock(Block block1, Block block2) {
		// this time I just need to change,no need to refresh by myself!
		block1.setTime(block2.getTime());// time also clone
		block1.setNumber(block2.getNumber());
		block1.setColor(block2.getColor());
	}

	//clone block with time plus
	public static void cloneBlockTimePlus(Block block1, Block block2) {
		block1.setTime(block2.getTime() + 1);// time plus!
		block1.setNumber(block2.getNumber());
		block1.setColor(block2.getColor());
	}

	// time ++
	public void timePlus() {
		setTime(getTime() + 1);
	}

	// make a blank not to be seen,do not change time
	public static void makeBlank(Block currentBlock) {
		currentBlock.setNumber(-1);
		currentBlock.setColor(Color.WHITE);
	}

}
