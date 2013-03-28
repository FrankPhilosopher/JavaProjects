package beans;

import interfaces.IRenameable;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.SceneModeTool;
import util.ResouceManager;

/**
 * 情景模式
 * 
 * @author yinger
 * 
 */
public class SceneMode implements IRenameable {

	public static final String HOME = "家庭模式";
	public static final String OUT = "外出模式";
	public static final String GUEST = "会客模式";
	public static final String LEAVE = "远行模式";

	private StringProperty name = new SimpleStringProperty();
	private List<SceneModeItem> items = new ArrayList<SceneModeItem>();// 该情景模式下的设备项，这里保证了items不是null
	private String icon = "scene_default.png";// 默认的图片
	
	public List<SceneModeItem> getItems() {
		return items;
	}

	public void setItems(List<SceneModeItem> items) {
		this.items = items;
	}

	public int getId() {
		return 0;
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
		SceneModeTool.getInstance().rename(this, name);// 这次不一样，因为name是scene的标示符，所以要先在文件中换名字，然后重命名
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
