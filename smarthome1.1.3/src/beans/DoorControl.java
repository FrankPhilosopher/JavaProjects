package beans;

import interfaces.IRenameable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.DoorTool;
import util.ProtocolUtil;

/**
 * √≈¥∞…Ë±∏
 * 
 * @author yinger
 * 
 */
public class DoorControl implements IRenameable {

	public static final int TYPE_DOOR = 0;
	public static final int TYPE_WINDOW = 1;

	private IntegerProperty id = new SimpleIntegerProperty();// id

	private StringProperty name = new SimpleStringProperty();// name

	private IntegerProperty state = new SimpleIntegerProperty(ProtocolUtil.STATE_OFF);// state

	private int type;
	private int command_buttonopen;
	private int command_buttonclose;
	private int command_buttonstop;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCommand_buttonopen() {
		return command_buttonopen;
	}

	public void setCommand_buttonopen(int command_buttonopen) {
		this.command_buttonopen = command_buttonopen;
	}

	public int getCommand_buttonclose() {
		return command_buttonclose;
	}

	public void setCommand_buttonclose(int command_buttonclose) {
		this.command_buttonclose = command_buttonclose;
	}

	public int getCommand_buttonstop() {
		return command_buttonstop;
	}

	public void setCommand_buttonstop(int command_buttonstop) {
		this.command_buttonstop = command_buttonstop;
	}

	public void setState(int value) {
		state.set(value);
	}

	public int getState() {
		return state.get();
	}

	public IntegerProperty stateProperty() {
		return state;
	}

	public void setId(int value) {
		id.set(value);
	}

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setName(String value) {
		name.set(value);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	@Override
	public void rename(String name) throws Exception {
		this.name.set(name);
		DoorTool.getInstance().rename(this);
	}

	@Override
	public String getOldName() {
		return name.get();
	}

}
