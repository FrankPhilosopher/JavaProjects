package mydip.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ImageEditorInput implements IEditorInput {

	private String name = "";// nameò�����޷��޸ĵģ�

	public ImageEditorInput(String name) {
		this.name = name;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return name;
	}

	@Override
	// ע����дequals�����������ж�ʲô���������editor��һ���ģ�������name��ͬ������ͬ�ģ�
	public boolean equals(Object obj) {
		if (obj instanceof ImageEditorInput) {
			ImageEditorInput input = (ImageEditorInput) obj;
			if (input.getName().equals(this.getName())) {
				return true;
			}
		}
		return false;
	}

}
