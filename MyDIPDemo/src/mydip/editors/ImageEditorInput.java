package mydip.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ImageEditorInput implements IEditorInput {

	private String name = "";// name貌似是无法修改的！

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
	// 注意重写equals方法，用于判断什么情况下两个editor是一样的！这里是name相同就是相同的！
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
