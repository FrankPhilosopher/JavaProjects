package com.yinger.patientims.wizards.wizardPages;

import java.util.Date;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.yinger.patientims.dao.DepartmentDAO;
import com.yinger.patientims.dao.SickBedDAO;
import com.yinger.patientims.dao.SickRoomDAO;
import com.yinger.patientims.model.Department;
import com.yinger.patientims.model.Patient;
import com.yinger.patientims.model.SickBed;
import com.yinger.patientims.model.SickRoom;
import com.yinger.patientims.sigleton.PatientFactory;
import com.yinger.patientims.util.DBUtil;
import com.yinger.patientims.util.PluginUtil;

public class PatientRegisterInfoWizardPage extends WizardPage implements ModifyListener {

	private Combo comboDepartment;
	private Combo comboSickRoom;
	private Combo comboSickBed;

	private String sickBedNoString = "";
	private String sickRoomNoString = "";

	private Patient patient;// 在这个类中保存一个Patient对象

	private String type;

	public PatientRegisterInfoWizardPage() {
		super("PatientRegisterInfoWizardPage");// 这里很重要！
		setTitle("Add Patient Information");
		setMessage("Attention:Please input the following information!", IMessageProvider.INFORMATION);
		// this.setImageDescriptor(Activator.getImageDescriptor("/icons/small/add.gif"));

	}

	// 这个protected的构造方法一定要有！
	protected PatientRegisterInfoWizardPage(String pageName) {
		super(pageName);
		this.type = pageName;
	}

	@Override
	// 创建相应的控件
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));// 第一个参数是指列的数目，第二个参数是指列是否是等宽的
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		// 添加相应的需要输入的信息的控件，并给控件添加监听器（就是类本身，因为它实现了ModifyListener）
		new Label(composite, SWT.NONE).setText("Department:");
		comboDepartment = new Combo(composite, SWT.READ_ONLY);
		comboDepartment.setLayoutData(gridData);
		comboDepartment.addModifyListener(this);
		// 设置数据
		for (Department department : new DepartmentDAO().getDepartmentList()) {
			comboDepartment.add(department.getName());// 首先要将字符串添加到列表中
			comboDepartment.setData(department.getName(), department);// 其次要设置Data，以String和Object形成映射
		}
		// comboDepartment.setSelection(new Point(0,
		// 0));//TODO:这一句不是很理解，貌似没有效果

		// 刚开始只是设置科室的列表，其他的列表动态的改变着
		new Label(composite, SWT.NONE).setText("SickRoom:");
		comboSickRoom = new Combo(composite, SWT.READ_ONLY);
		comboSickRoom.setLayoutData(gridData);
		comboSickRoom.addModifyListener(this);
		comboSickRoom.setEnabled(false);// 这里有很多的地方都要设置这个，可以重构一下

		new Label(composite, SWT.NONE).setText("SickBed:");
		comboSickBed = new Combo(composite, SWT.READ_ONLY);
		comboSickBed.setLayoutData(gridData);
		comboSickBed.addModifyListener(this);
		comboSickBed.setEnabled(false);

		setControl(composite);
		setPageComplete(false);

		// if(patient!=null){//此时表示本次操作是 修改patient
		patient = PatientFactory.getInstance();
		if (type.equals(PluginUtil.MODIFY)) {
			comboDepartment.setText(patient.getDepartment().getName());
			sickBedNoString = String.valueOf(patient.getSickbed().getSickBedNo());
			sickRoomNoString = String.valueOf(patient.getSickroom().getSickRoomNo());
			comboSickRoom.setText(sickRoomNoString);
			comboSickBed.setText(sickBedNoString);
			setPageComplete(true);
		}

	}

	@Override
	// 实现ModifyListener接口的modifyText方法
	/* 这个方法需要几点，最主要的是e.getSource()，还有一个是setPageComplete，最后一个是setEnabled */
	public void modifyText(ModifyEvent e) {
		// 如果信息无效，给出相应的错误信息，并设置该页还没有完成，next按钮就是灰色的
		if (e.getSource().equals(comboDepartment)) {// 首先得到出发事件的事件源
			if (comboDepartment.getText().trim().length() == 0) {// TODO：这个事件貌似不会发生！
				// 没选之前不会触发它，选了之后就不能再选择到空白
				setMessage("Attention:You should choose a department!", IMessageProvider.WARNING);
				setPageComplete(false);
				comboSickRoom.setEnabled(false);
				comboSickBed.setEnabled(false);
			} else {
				// 这里每次设置值之前要删除掉以前的值，但是这样带来的麻烦就是每次都要查一次数据库来设置值
				refreshComboSickRoom(comboDepartment.getText());
				comboSickRoom.setEnabled(true);
				comboSickBed.setEnabled(false);
			}
			return;
		}
		if (e.getSource().equals(comboSickRoom)) {
			if (comboSickRoom.getText().trim().length() == 0) {
				setMessage("Attention:You should choose a sickroom!", IMessageProvider.WARNING);
				setPageComplete(false);
				comboSickBed.setEnabled(false);
			} else {
				refreshComboSickBed(comboSickRoom.getText());
				comboSickBed.setEnabled(true);
			}
			return;
		}
		if (e.getSource().equals(comboSickBed)) {
			if (comboSickBed.getText().trim().length() == 0) {
				setMessage("Attention:You should choose a sickbed!", IMessageProvider.WARNING);
				setPageComplete(false);
				return;
			}
		}

		// 如果是修改操作，并且还是原来的那个病床（病房也是相同的），给出不同的提示信息
		// 注意这里的String.valueOf很重要的，不能省掉了，不然String和int用equals，永远不会相等
		if (type.equals(PluginUtil.MODIFY) && comboSickRoom.getText().equals(sickRoomNoString) && comboSickBed.getText().equals(sickBedNoString)) {
			setMessage("Warning:The SickBed has been taken by the present patient!", IMessageProvider.WARNING);
			// 此时是可以返回的，有效的
		} else {
			// 判断这个床位是否已经有人了！
			/* TODO：这里最好是改为可以入住的床位 */
			if (DBUtil.validateSickBed(comboDepartment.getText(), Integer.valueOf(comboSickBed.getText()), Integer.valueOf(comboSickRoom.getText()))) {
				setMessage("Error:The SickBed has been taken!", IMessageProvider.WARNING);
				setPageComplete(false);
				return;
			}else{
				setMessage(null);
			}
		}
		// 这里不能调用savePatientInfo方法，不然会改变原有的patient的值！这样导致下次选择到原有的信息时提示还是不对！
		savePatientInfo(); // 到这里表示通过了验证，可以设置相应的内容到一个patient对象上
		setPageComplete(true);
	}

	// 刷新病床列表
	private void refreshComboSickBed(String text) {
		comboSickBed.setItems(new String[0]);// java.lang.IllegalArgumentException:
									// Argument cannot be null
		String where = " and sickroom_id = (select id from t_sickroom where sickroomno=" + text + ") ";
		for (SickBed sickBed : new SickBedDAO().getSickBedList(where)) {
			comboSickBed.add(String.valueOf(sickBed.getSickBedNo()));
			comboSickBed.setData(String.valueOf(sickBed.getSickBedNo()), sickBed);// 因为这里setData了，所以可以很轻松的得到病床id
		}
	}

	// 刷新病房列表
	private void refreshComboSickRoom(String text) {
		comboSickRoom.setItems(new String[0]);
		String where = " and department_id = (select id from t_department where name='" + text + "') ";
		for (SickRoom sickRoom : new SickRoomDAO().getSickRoomList(where)) {
			comboSickRoom.add(String.valueOf(sickRoom.getSickRoomNo()));
			comboSickRoom.setData(String.valueOf(sickRoom.getSickRoomNo()), sickRoom);
		}
	}

	// public Patient getPatient() {
	// return patient;
	// }
	//
	// public void setPatient(Patient patient) {
	// this.patient = patient;
	// }

	// 保存填写的信息
	public void savePatientInfo() {
		// 得到PatientFactory的单独实例
		// Patient patient = PatientFactory.getInstance();
		// patient = PatientFactory.getInstance();//还是得到这个patient

		// 给其赋值
		// 注意登记时间需要考虑
		if (type.equals(PluginUtil.ADD)) {// 只要在添加时需要修改
			patient.setLogtime(new Date());
		}
		patient.getDepartment().setName(comboDepartment.getText());
		patient.getSickroom().setSickRoomNo(Integer.valueOf(comboSickRoom.getText()));
		patient.getSickbed().setSickBedNo(Integer.valueOf(comboSickBed.getText()));

		// 设置病床id
		SickBed sickBed = (SickBed) comboSickBed.getData(comboSickBed.getText());
		patient.getSickbed().setId(sickBed.getId());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
