package com.yinger.patientims.navigator;

import java.util.ArrayList;
import java.util.List;

import com.yinger.patientims.editors.editorInput.DiagnoseInfoEditorInput;
import com.yinger.patientims.editors.editorInput.ExpenseInfoEditorInput;
import com.yinger.patientims.editors.editorInput.PatientInfoEditorInput;
/**
 * 这个工厂类主要任务就是负责设置在导航视图中要显示的TreeViewer中的节点元素
 * 实体工厂类将实体类中的各个节点数据关联起来，起到统筹的作用
 */
public class NavigatorEntityFactory {

	public static Object setNavigatorEntity() {
		/**
		 * 这里涉及到一个统一化的问题，有些String在其他的地方引用了，不便于修改！
		 * 保存起来？！
		 */
		
		// Level one 一级节点
		NavigatorEntityElement root1 = new NavigatorEntityElement(0, "Hospital Management");
		NavigatorEntityElement root2 = new NavigatorEntityElement(0, "Expense Management");
		NavigatorEntityElement root3 = new NavigatorEntityElement(0, "Information Search");

		// level two 二级节点
		NavigatorEntityElement element1 = new NavigatorEntityElement(1, "Patient Information");
		NavigatorEntityElement element2 = new NavigatorEntityElement(1, "Diagnose Information");
		NavigatorEntityElement element3 = new NavigatorEntityElement(1, "Expense Information");

		// 将下级节点添加到上级
		root1.addChildren(element1);
		root1.addChildren(element2);
		root2.addChildren(element3);
		
		// 设置编辑器
		element1.setEditorInput(new PatientInfoEditorInput());
		element2.setEditorInput(new DiagnoseInfoEditorInput());
		element3.setEditorInput(new ExpenseInfoEditorInput());
		
		// 要返回的list
		List list = new ArrayList();
		list.add(root1);
		list.add(root2);
		list.add(root3);
		return list;
	}
}
