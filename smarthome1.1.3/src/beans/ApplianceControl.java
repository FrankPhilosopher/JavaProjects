package beans;

import interfaces.IRenameable;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.ApplianceTool;
import util.ProtocolUtil;
import util.ResouceManager;

/**
 * 家电设备
 * 
 * @author yinger
 * 
 */
public class ApplianceControl implements IRenameable {

	private IntegerProperty id = new SimpleIntegerProperty();// id

	private StringProperty name = new SimpleStringProperty();// name

	private IntegerProperty state = new SimpleIntegerProperty(ProtocolUtil.STATE_OFF);// state

	private String icon = ResouceManager.APPLIANCE_DEFAULT;// 默认的图片

	private List<ApplianceAction> actions = new ArrayList<ApplianceAction>();

	private int command_open = ProtocolUtil.COMMAND_EMPTY;// 开和关的命令码，默认是空的命令码，发不出去滴
	private int command_close = ProtocolUtil.COMMAND_EMPTY;

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

	public List<ApplianceAction> getActions() {
		return actions;
	}

	public void setActions(List<ApplianceAction> actions) {
		this.actions = actions;
	}

	public void setId(int value) {
		id.set(value);
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

	// 重命名设备
	@Override
	public void rename(String name) throws Exception {
		this.name.set(name);
		ApplianceTool.getInstance().refresh(this);
	}

	@Override
	public String getOldName() {
		return name.get();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
