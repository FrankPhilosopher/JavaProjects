package beans;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 家电设备的命令<br>
 * 有些字段只是对某些类型的指令有用
 * 
 * @author yinger
 * 
 */
public class ApplianceAction {

	public static final int ACTIONTYPE_SINGLE = 0;// 单键响应
	public static final int ACTIONTYPE_INCREASE = 1;// 递增响应
	public static final int ACTIONTYPE_DECREASE = -1;// 递减响应
	public static final int ACTIONTYPE_CYCLE = 2;// 循环响应

	private int aid;// 指令编号
	private int code;// 如果是单键响应那么就有值
	private int style;// 响应类型
	private String name;// 指令的名称
	private List<SubAction> subActions = new ArrayList<SubAction>();// 如果不是单键响应就有元素

	// 这个应该是放在这里的，而不是放在controller中，因为一个家电可能会出现多个相同类型的action
	private int cycleIndex = -1;// 如果是循环响应，那么这个值就是有用的，代表当前的循环响应的index
	private IntegerProperty creaseIndex = new SimpleIntegerProperty(-1);// 目的是为了绑定递增和递减两个响应！
	private StringProperty btntext = new SimpleStringProperty();// 按钮显示的文本

	/**
	 * 重置index
	 */
	public void reset() {
		cycleIndex = -1;
		creaseIndex.set(-1);
	}

	/**
	 * 得到下一个要发送的子命令
	 */
	public SubAction getNextSubAction() {
		SubAction subAction = null;
		if (this.style == ACTIONTYPE_CYCLE) {
			subAction = getNextCycleSubAction();
		} else if (this.style == ACTIONTYPE_INCREASE) {
			subAction = getNextIncreaseSubAction();
		} else if (this.style == ACTIONTYPE_DECREASE) {
			subAction = getNextDecreaseSubAction();
		}
		return subAction;
	}

	/**
	 * 得到下一个循环命令
	 */
	private SubAction getNextCycleSubAction() {
		cycleIndex++;
		SubAction subAction = subActions.get(cycleIndex % subActions.size());
		btntext.set(subAction.getName());
		return subAction;
	}

	/**
	 * 得到下一个递增命令
	 */
	private SubAction getNextIncreaseSubAction() {
		// 增加减少应该放在取出子命令的前面
		creaseIndex.set(creaseIndex.get() + 1);// creaseIndex 是不可以不断的增加减小的
		SubAction subAction = null;
		if (creaseIndex.get() < subActions.size()) {// creaseIndex最多是size-1
			subAction = subActions.get(creaseIndex.get());
			btntext.set(subAction.getName());
		} else {
			creaseIndex.set(creaseIndex.get() - 1);// 取不出来的话就退回去
		}
		return subAction;
	}

	/**
	 * 得到下一个递减命令
	 */
	private SubAction getNextDecreaseSubAction() {
		creaseIndex.set(creaseIndex.get() - 1);// creaseIndex 是不可以不断的增加减小的
		SubAction subAction = null;
		if (creaseIndex.get() >= 0 && creaseIndex.get() < subActions.size()) {
			subAction = subActions.get(creaseIndex.get());
			btntext.set(subAction.getName());
		} else {
			creaseIndex.set(creaseIndex.get() + 1);
		}
		return subAction;
	}

	public String getBtntext() {
		return btntext.get();
	}

	public void setBtntext(String value) {
		btntext.set(value);
	}

	public StringProperty btntextProperty() {
		return btntext;
	}

	public int getCreaseIndex() {
		return creaseIndex.get();
	}

	public void setCreaseIndex(int value) {
		creaseIndex.set(value);
	}

	public IntegerProperty creaseIndexProperty() {
		return creaseIndex;
	}

	public List<SubAction> getSubActions() {
		return subActions;
	}

	public void setSubActions(List<SubAction> subActions) {
		this.subActions = subActions;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
