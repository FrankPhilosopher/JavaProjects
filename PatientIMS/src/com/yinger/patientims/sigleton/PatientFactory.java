package com.yinger.patientims.sigleton;

import com.yinger.patientims.model.Patient;

/**
 * 采用非标准的单例模式
 * 主要用于添加patient和修改patient信息中
 * 
 * 突然有一个想法是建立一个公共的泛型的单例类
 * instance的类型是不确定的，根据传入的参数来确定！
 * 这样在有很多个增删查改的系统中非常适用！
 */
public class PatientFactory {
	
	private static Patient instance = new Patient();
	
	private PatientFactory() {
//		instance = new Patient();
	}
	
	public static Patient getInstance(){
		return instance;
	}
	
	public static void setInstance(Patient patient){
		instance = patient;
	}

}
