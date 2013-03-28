package beans;

import interfaces.IRenameable;
import item.LightItem;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.RoomTool;

/**
 * 房间
 * 
 * @author yinger
 * 
 */
public class Room implements IRenameable {

	private SimpleStringProperty id = new SimpleStringProperty();// id string类型，使用UUID生成，保证唯一

	private StringProperty name = new SimpleStringProperty();// name

	private List<LightItem> lightItems = new ArrayList<LightItem>();

	// public List<LightControl> getLightControls() {
	// return lightControls;
	// }
	//
	// public void setLightControls(List<LightControl> lightControls) {
	// this.lightControls = lightControls;
	// }

	public List<LightItem> getLightItems() {
		return lightItems;
	}

	public void setLightItems(List<LightItem> lightItems) {
		this.lightItems = lightItems;
	}

	public void setId(String value) {
		id.set(value);
	}

	public String getId() {
		return id.get();
	}

	public StringProperty idProperty() {
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

	// 重命名---这里是一个不是很好的设计，在bean中嵌入了处理逻辑
	@Override
	public void rename(String name) throws Exception {
		this.name.set(name);
		RoomTool.getInstance().rename(this);// 保存到xml文件中
	}

	// 得到名称
	@Override
	public String getOldName() {
		return name.get();
	}

}
