package beans;

import interfaces.IRenameable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.SecurityTool;
import util.ProtocolUtil;

/**
 * ∞≤∑¿…Ë±∏
 * 
 * @author yinger
 * 
 */
public class SecurityControl implements IRenameable {

	private IntegerProperty id = new SimpleIntegerProperty();// id

	private StringProperty name = new SimpleStringProperty();// name

	private IntegerProperty state = new SimpleIntegerProperty(ProtocolUtil.STATE_OFF);// state

	private int command_open;
	private int command_close;

	public int getCommand_open() {
		return command_open;
	}

	public void setCommand_open(int command_open) {
		this.command_open = command_open;
	}

	public int getCommand_close() {
		return command_close;
	}

	public void setCommand_close(int command_close) {
		this.command_close = command_close;
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
		SecurityTool.getInstance().rename(this);
	}

	@Override
	public String getOldName() {
		return name.get();
	}

}
