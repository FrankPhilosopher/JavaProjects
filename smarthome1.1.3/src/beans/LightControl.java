package beans;

import interfaces.IRenameable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.LightTool;
import util.ProtocolUtil;

/**
 * 灯控和插座
 * 
 * @author yinger
 * 
 */
public class LightControl implements IRenameable {

	public static int INTENSITY_NO = 0;// 是否存在亮度调节
	public static int INTENSITY_YES = 1;
	public static int TYPE_OUTLET = 0;// 插座
	public static int TYPE_LIGHT = 1;// 灯
	public static String EMPTY_ROOM = "";

	private IntegerProperty id = new SimpleIntegerProperty();// id

	private StringProperty name = new SimpleStringProperty();// name

	private IntegerProperty state = new SimpleIntegerProperty(ProtocolUtil.STATE_OFF);// state

	private String roomid = EMPTY_ROOM;

	private int type = TYPE_LIGHT;
	private int hasIntensity = INTENSITY_NO;
	private int command_buttonup;
	private int command_buttondown;
	private int command_buttonon;
	private int command_buttonoff;

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCommand_buttonup() {
		return command_buttonup;
	}

	public void setCommand_buttonup(int command_buttonup) {
		this.command_buttonup = command_buttonup;
	}

	public int getCommand_buttondown() {
		return command_buttondown;
	}

	public void setCommand_buttondown(int command_buttondown) {
		this.command_buttondown = command_buttondown;
	}

	public int getCommand_buttonon() {
		return command_buttonon;
	}

	public void setCommand_buttonon(int command_buttonon) {
		this.command_buttonon = command_buttonon;
	}

	public int getCommand_buttonoff() {
		return command_buttonoff;
	}

	public void setCommand_buttonoff(int command_buttonoff) {
		this.command_buttonoff = command_buttonoff;
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

	public void setHasIntensity(int value) {
		this.hasIntensity = value;
	}

	public int getHasIntensity() {
		return hasIntensity;
	}

	// 重命名---这里是一个不是很好的设计，在bean中嵌入了处理逻辑
	@Override
	public void rename(String name) throws Exception {
		this.name.set(name);
		// 保存到xml文件中
		LightTool.getInstance().rename(this);
	}

	// 得到名称
	@Override
	public String getOldName() {
		return name.get();
	}

}
