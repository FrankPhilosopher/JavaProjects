package com.yinger.patientims.views.viewerProvider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.yinger.patientims.model.Patient;

public class PatientInfoTableViewerLabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {

		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		
		return null;
	}

	@Override//得到列中要显示的文本
	public String getColumnText(Object element, int columnIndex) {
		Patient patient = (Patient) element;
		switch (columnIndex) {//注意：列的索引是从0开始的
		case 0:
			return patient.getId().toString();
		case 1:
			return patient.getName();
		case 2:
			return patient.getSex();
		case 3:
			return patient.getAge()+"";
		case 4:
			return patient.getPhone();
		case 5:
			return patient.getDepartment().getName();
		case 6:
			return patient.getSickroom().getSickRoomNo()+"";
		case 7:
			return patient.getSickbed().getSickBedNo()+"";
		case 8:
			return patient.getAddress();
		case 9:
			return patient.getLogtime().toString();
//		default:
//			break;
		}
		return "";//默认返回空字符串
	}

}
