package com.yinger.patientims.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;

import com.yinger.patientims.Activator;
import com.yinger.patientims.dao.PatientDAO;
import com.yinger.patientims.model.Patient;
import com.yinger.patientims.sigleton.PatientFactory;
import com.yinger.patientims.util.DBUtil;
import com.yinger.patientims.util.PluginUtil;
import com.yinger.patientims.wizards.wizardPages.PatientBasicInfoWizardPage;
import com.yinger.patientims.wizards.wizardPages.PatientRegisterInfoWizardPage;

public class AddPatientInfoWizard extends Wizard {
	/**
	 * 我觉得这里应该使用单态模式，用一个类（或者是Patient类）来保存一个patient对象，
	 * 从而确保新建的时候处理的是同一个patient
	 * 同时显示patient的信息也是一个patient，为了避免和这个patient冲突，可以在那个类中定义两个
	 * 不同的patient，都是采用单例模式进行访问，OK！ 添加病人信息和修改病人信息其实是同一个窗体！
	 * 但是不同的是添加时patient是没有数据的
	 */
//	private Patient patient;
	// 注意！这里对两个page和这个Wizard中都使用了一个Patient对象
	// 目的是为了通过这个patient得到输入的信息
	// 两个自定义的WizardPage
	private PatientBasicInfoWizardPage pageOne;
	private PatientRegisterInfoWizardPage pageTwo;
	
	private String type;

	@Override
	// 添加向导页面（实现了IWizardPage的类）
	/* 最需要注意的是两个page的patient对象是如何关联在一起的 */
	public void addPages() {
		// 设置图片这一句可以，没有问题
		getShell().setImage(new Image(null, Activator.getImageDescriptor("/icons/small/add.gif").getImageData()));
		// TODO：但是设置标题这一句没有生效！
		getShell().setText("Add Patient Information");

		pageOne = new PatientBasicInfoWizardPage();
		pageOne.setType(type);
		// 重点！
//		pageOne.setPatient(patient);
		pageTwo = new PatientRegisterInfoWizardPage();
		pageTwo.setType(type);
		// pageTwo.setPatient(patient);
		// 重点！
//		pageTwo.setPatient(pageOne.getPatient());// 后页的patient是前页的patient！
		addPage(pageOne);
		addPage(pageTwo);
	}

	@Override
	public boolean performFinish() {
		// 这个patient有了很多的信息，但是没有病床id
//		patient = pageTwo.getPatient();
		//经过两个page的设置，PatientFactory的单独实例已经不再是空的了
		Patient patient = PatientFactory.getInstance();
		// 首先根据已有的信息得到病床id
//		Long id = DBUtil.getSickBedId(patient);//实际上是没有必要的，因为可以从下拉列表中得到sickbed对象，id可以很容易的得到
//		Long id = patient.getSickbed().getId();
//		if (id != 0) {
//			patient.getSickbed().setId(id);
//		} else {
//			MessageDialog.openError(null, "Information", "Add patient information failed!");
//			return false;
//		}
//		 然后再插入到数据库中
		if(type.equals(PluginUtil.ADD)){
			if (new PatientDAO().insertPatient(patient)) {
				MessageDialog.openInformation(null, "Information", "Add patient information successfully!");
				return true;
			} else {
				MessageDialog.openError(null, "Information", "Add patient information failed!");
				return false;
			}
		}else{
			if (new PatientDAO().updatePatient(patient)) {
				MessageDialog.openInformation(null, "Information", "Modify patient information successfully!");
				return true;
			} else {
				MessageDialog.openError(null, "Information", "Modify patient information failed!");
				return false;
			}
		}

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public Patient getPatient() {
//		return patient;
//	}
//
//	public void setPatient(Patient patient) {
//		this.patient = patient;
//	}
	
	

}
