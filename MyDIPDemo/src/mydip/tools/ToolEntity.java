package mydip.tools;

import org.eclipse.ui.IEditorInput;

public class ToolEntity {

	private String name;// ���ߵ�����
	private IEditorInput editorInput;// �ڵ��IEditorInput����

	public ToolEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IEditorInput getEditorInput() {
		return editorInput;
	}

	public void setEditorInput(IEditorInput editorInput) {
		this.editorInput = editorInput;
	}

}
