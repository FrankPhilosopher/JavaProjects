package beans;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * �ҵ��豸������<br>
 * ��Щ�ֶ�ֻ�Ƕ�ĳЩ���͵�ָ������
 * 
 * @author yinger
 * 
 */
public class ApplianceAction {

	public static final int ACTIONTYPE_SINGLE = 0;// ������Ӧ
	public static final int ACTIONTYPE_INCREASE = 1;// ������Ӧ
	public static final int ACTIONTYPE_DECREASE = -1;// �ݼ���Ӧ
	public static final int ACTIONTYPE_CYCLE = 2;// ѭ����Ӧ

	private int aid;// ָ����
	private int code;// ����ǵ�����Ӧ��ô����ֵ
	private int style;// ��Ӧ����
	private String name;// ָ�������
	private List<SubAction> subActions = new ArrayList<SubAction>();// ������ǵ�����Ӧ����Ԫ��

	// ���Ӧ���Ƿ�������ģ������Ƿ���controller�У���Ϊһ���ҵ���ܻ���ֶ����ͬ���͵�action
	private int cycleIndex = -1;// �����ѭ����Ӧ����ô���ֵ�������õģ�����ǰ��ѭ����Ӧ��index
	private IntegerProperty creaseIndex = new SimpleIntegerProperty(-1);// Ŀ����Ϊ�˰󶨵����͵ݼ�������Ӧ��
	private StringProperty btntext = new SimpleStringProperty();// ��ť��ʾ���ı�

	/**
	 * ����index
	 */
	public void reset() {
		cycleIndex = -1;
		creaseIndex.set(-1);
	}

	/**
	 * �õ���һ��Ҫ���͵�������
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
	 * �õ���һ��ѭ������
	 */
	private SubAction getNextCycleSubAction() {
		cycleIndex++;
		SubAction subAction = subActions.get(cycleIndex % subActions.size());
		btntext.set(subAction.getName());
		return subAction;
	}

	/**
	 * �õ���һ����������
	 */
	private SubAction getNextIncreaseSubAction() {
		// ���Ӽ���Ӧ�÷���ȡ���������ǰ��
		creaseIndex.set(creaseIndex.get() + 1);// creaseIndex �ǲ����Բ��ϵ����Ӽ�С��
		SubAction subAction = null;
		if (creaseIndex.get() < subActions.size()) {// creaseIndex�����size-1
			subAction = subActions.get(creaseIndex.get());
			btntext.set(subAction.getName());
		} else {
			creaseIndex.set(creaseIndex.get() - 1);// ȡ�������Ļ����˻�ȥ
		}
		return subAction;
	}

	/**
	 * �õ���һ���ݼ�����
	 */
	private SubAction getNextDecreaseSubAction() {
		creaseIndex.set(creaseIndex.get() - 1);// creaseIndex �ǲ����Բ��ϵ����Ӽ�С��
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
