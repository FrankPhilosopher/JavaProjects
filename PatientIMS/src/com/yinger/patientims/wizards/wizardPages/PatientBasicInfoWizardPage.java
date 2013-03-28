package com.yinger.patientims.wizards.wizardPages;

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
import org.eclipse.swt.widgets.Text;

import com.yinger.patientims.model.Patient;
import com.yinger.patientims.sigleton.PatientFactory;
import com.yinger.patientims.util.PluginUtil;

public class PatientBasicInfoWizardPage extends WizardPage implements ModifyListener {

	private Text textName;
	private Text textAge;
	private Text textPhone;
	private Text textAddress;
	private Combo comboSex;

	private Patient patient;// 在这个类中保存一个Patient对象
	//采用了单例模式之后，这个是很有必要的！因为这个类中的很多方法使用到了patient
	
	private String type;

	// 不能使用默认的构造器，不论是pubic还是protected
	// 父类WizardPage就没有默认的构造器
	// protected PatientBasicInfoWizardPage(){
	//
	// }
	// 在这个无参的构造方法中调用父类的有参的构造方法，这样就不会报错了
	//这里设置的只是页面内的显示内容
	public PatientBasicInfoWizardPage() {
		// 报错：Implicit super constructor WizardPage() is undefined. Must
		// explicitly invoke another constructor
		super("PatientBasicInfoWizardPage");// 这里很重要！
		setTitle("Add Patient Information");
		setMessage("Attention:Please input the following information!", IMessageProvider.INFORMATION);
//		this.setImageDescriptor(Activator.getImageDescriptor("/icons/small/add.gif"));
		//这个图标不是显示在窗体外面的左上角，而是内部内容的右上角！
		
	}

	// 这个protected的构造方法一定要有！
	// 但是有了上面那个构造方法这个方法就不需要了
	// 但是我覺得可以有，用来设置type
	protected PatientBasicInfoWizardPage(String pageName) {
		super(pageName);
		this.type = pageName;
	}

	@Override
	// 创建相应的控件
	// 一般来说，相应的控件对象都定义为该类的内部属性比较好
	// 还有，一般label控件就不用作为内部属性了，但是国际化的话还是需要！
	// 另外，一般的这类用于填写某些信息的page都是使用GridLayout
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));// 第一个参数是指列的数目，第二个参数是指列是否是等宽的
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		// 等价于
		// GridData gridData = new GridData();
		// gridData.horizontalAlignment = GridData.FILL;

		// 添加相应的需要输入的信息的控件，并给控件添加监听器（就是类本身，因为它实现了ModifyListener）
		new Label(composite, SWT.NONE).setText("Name:");
		textName = new Text(composite, SWT.BORDER);
		textName.setLayoutData(gridData);
		textName.addModifyListener(this);
		
		new Label(composite, SWT.NONE).setText("Age:");
		textAge = new Text(composite, SWT.BORDER);
		textAge.setLayoutData(gridData);
		textAge.addModifyListener(this);

		new Label(composite, SWT.NONE).setText("Sex:");
		comboSex = new Combo(composite, SWT.READ_ONLY);
		comboSex.setItems(new String[]{"Male","Female"});
		comboSex.setText("Male");
		comboSex.setLayoutData(gridData);
		comboSex.addModifyListener(this);

		new Label(composite, SWT.NONE).setText("Phone:");
		textPhone = new Text(composite, SWT.BORDER);
		textPhone.setLayoutData(gridData);
		textPhone.addModifyListener(this);

		new Label(composite, SWT.NONE).setText("Address:");
		textAddress = new Text(composite, SWT.BORDER);
		textAddress.setLayoutData(gridData);
		textAddress.addModifyListener(this);
		setControl(composite);		
		setPageComplete(false);
		
//		if(patient.getId()!=null){//此时表示本次操作是 修改patient
//		if(null!=patient){
		patient = PatientFactory.getInstance();
		if(type.equals(PluginUtil.MODIFY)){
			textName.setText(patient.getName());
			textAge.setText(String.valueOf(patient.getAge()));
			textAddress.setText(patient.getAddress());
			textPhone.setText(patient.getPhone());
			comboSex.setText(patient.getSex());//这里貌似没有生效！
			
			setPageComplete(true);//如果是修改的话，这里page就应该是有效的！
		}
		
	}

	@Override
	// 实现ModifyListener接口的modifyText方法
	/**
	 * TODO：想法：这个方法验证所有的文本框里的数据都不能是空的，但是这么写的话，重复冗余的代码很多
	 * 重构的话，可以将其改成：遍历page的control中的所有文本框，再调用一个公共的方法验证有效性
	 * 但是这样子的对某个需要特殊处理的文本框的处理又不好设置了！
	 */
	public void modifyText(ModifyEvent e) {
		// 如果信息无效，给出相应的错误信息，并设置该页还没有完成，next按钮就是灰色的
		if (textName.getText().trim().length() == 0) {
			setMessage("Attention:Name must'n be blank!", IMessageProvider.WARNING);
			setPageComplete(false);
			return;
		}
		if (textAge.getText().trim().length() == 0) {
			setMessage("Attention:Age must'n be blank!", IMessageProvider.WARNING);
			setPageComplete(false);
			return;
		}
		if (comboSex.getText().trim().length() == 0) {
			setMessage("Attention:Sex must'n be blank!", IMessageProvider.WARNING);
			setPageComplete(false);
			return;
		}
		if (textPhone.getText().trim().length() == 0) {
			setMessage("Attention:Phone must'n be blank!", IMessageProvider.WARNING);
			setPageComplete(false);
			return;
		}
		if (textAddress.getText().trim().length() == 0) {
			setMessage("Attention:Address must'n be blank!", IMessageProvider.WARNING);
			setPageComplete(false);
			return;
		}
		setMessage(null);
		savePatientInfo(); // 到这里表示通过了验证，可以设置相应的内容到一个patient对象上
		setPageComplete(true);//这一句很重要的，不然完全填写正确也是不能next的！
	}

//	public Patient getPatient() {
//		return patient;
//	}
//	
//	public void setPatient(Patient patient){
//		this.patient = patient;
//	}

	// 保存填写的信息
	public void savePatientInfo() {
//		patient = new Patient();//注意：这里不能是new Patient！因为两个页面的patient是相关联的
		//得到PatientFactory的单独实例
//		Patient patient = PatientFactory.getInstance();
		
		/**
		 * 如果是添加，patient是new Patient()，一个新的实例，id为null
		 * 如果是修改，patient是已有的patient对象，id不为null
		 * 
		 * 但是对于如何判断操作的类型
		 * 方法一：判断PatientFactory中的实例是否为空（但是不一定就是判断patient是否是null）
		 * 方法二：使用一个String类型的参数type
		 * 这里选用方法一
		 * 但是，经过一段尝试，并分析之后，我觉得使用一个type来分别操作类型是最好的！
		 */
		//给其赋值
		patient.setName(textName.getText().trim());
		patient.setAddress(textAddress.getText().trim());
		patient.setAge(Integer.valueOf(textAge.getText().trim()));
		patient.setPhone(textPhone.getText().trim());
		patient.setSex(comboSex.getText());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
