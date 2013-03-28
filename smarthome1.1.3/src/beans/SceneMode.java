package beans;

import interfaces.IRenameable;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.SceneModeTool;
import util.ResouceManager;

/**
 * �龰ģʽ
 * 
 * @author yinger
 * 
 */
public class SceneMode implements IRenameable {

	public static final String HOME = "��ͥģʽ";
	public static final String OUT = "���ģʽ";
	public static final String GUEST = "���ģʽ";
	public static final String LEAVE = "Զ��ģʽ";

	private StringProperty name = new SimpleStringProperty();
	private List<SceneModeItem> items = new ArrayList<SceneModeItem>();// ���龰ģʽ�µ��豸����ﱣ֤��items����null
	private String icon = "scene_default.png";// Ĭ�ϵ�ͼƬ
	
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
		SceneModeTool.getInstance().rename(this, name);// ��β�һ������Ϊname��scene�ı�ʾ��������Ҫ�����ļ��л����֣�Ȼ��������
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
