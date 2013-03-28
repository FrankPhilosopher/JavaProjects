package com.yj.smarthome.stw;

import org.eclipse.nebula.effects.stw.TransitionManager;
import org.eclipse.nebula.effects.stw.Transitionable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.composite.SceneModeTabItemComposite;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;

/**
 * 情景模式对话框中的TabFolder面板
 * 
 * @author yinger
 * 
 */
public class SceneModeSTWFrame extends AbstractSTWDemoFrame {

	private SceneMode sceneMode;//情景模式
	private TabFolder tf;

	public SceneModeSTWFrame(SceneMode sceneMode) {
		this.sceneMode = sceneMode;//传入进来的scenemode肯定不是null，一定是有名称，但是不一定有items
	}

	@Override
	protected void init() {
		_containerComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		_containerComposite.setLayout(new GridLayout());

		tf = new TabFolder(_containerComposite, SWT.NONE);
		tf.setLayoutData(new GridData(GridData.FILL_BOTH));

		//三种设备类型的所有组件面板
		TabItem tbi1 = new TabItem(tf, SWT.NONE);
		tbi1.setText(SystemUtil.LIGHT_CONTROL);
		tbi1.setData("typeId", ProtocolUtil.DEVICETYPE_LIGHT);
//		Composite composite = new Composite(tf, SWT.NONE);
//		composite.setLayout(new FillLayout());
		tbi1.setControl(new SceneModeTabItemComposite(tf, SWT.NONE, sceneMode, ProtocolUtil.DEVICETYPE_LIGHT));

		TabItem tbi2 = new TabItem(tf, SWT.NONE);
		tbi2.setText(SystemUtil.DOOR_CONTROL);
		tbi2.setData("typeId", ProtocolUtil.DEVICETYPE_DOOR);
		tbi2.setControl(new SceneModeTabItemComposite(tf, SWT.NONE, sceneMode, ProtocolUtil.DEVICETYPE_DOOR));

		TabItem tbi3 = new TabItem(tf, SWT.NONE);
		tbi3.setText(SystemUtil.APPLIANCE_CONTROL);
		tbi3.setData("typeId", ProtocolUtil.DEVICETYPE_APPLIANCE);
		tbi3.setControl(new SceneModeTabItemComposite(tf, SWT.NONE, sceneMode, ProtocolUtil.DEVICETYPE_APPLIANCE));

		TabItem tbi4 = new TabItem(tf, SWT.NONE);
		tbi4.setText(SystemUtil.SECURITY_CONTROL);
		tbi4.setData("typeId", ProtocolUtil.DEVICETYPE_SECURITY);
		tbi4.setControl(new SceneModeTabItemComposite(tf, SWT.NONE, sceneMode, ProtocolUtil.DEVICETYPE_SECURITY));

		_tm = new TransitionManager(new Transitionable() {
			@Override
			public void addSelectionListener(SelectionListener listener) {
				tf.addSelectionListener(listener);
			}

			@Override
			public Control getControl(int index) {
				return tf.getItem(index).getControl();
			}

			@Override
			public Composite getComposite() {
				return tf;
			}

			@Override
			public int getSelection() {
				return tf.getSelectionIndex();
			}

			@Override
			public void setSelection(int index) {
				tf.setSelection(index);
			}

			@Override
			public double getDirection(int toIndex, int fromIndex) {
				return getSelectedDirection(toIndex, fromIndex);
			}
		});
	}

}
