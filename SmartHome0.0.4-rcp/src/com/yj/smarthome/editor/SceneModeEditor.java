package com.yj.smarthome.editor;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.composite.NewSceneModeEditorComposite;
import com.yj.smarthome.composite.SceneModeEditorComposite;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.xmlImpls.SceneModeXmlTool;

/**
 * �龰ģʽ�༭��
 * 
 * @author yinger
 * 
 */
public class SceneModeEditor extends EditorPart {

	public static final String ID = SceneModeEditor.class.getName();

	private Composite container;

	private ViewForm viewForm;

	public SceneModeEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(SystemUtil.SCENEMODE);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());//VERTICAL ��ֱ����
		// ���ȴ���һ��ViewForm�����������ڿؼ��Ĳ���
		viewForm = new ViewForm(parent, SWT.NONE);
		viewForm.setLayout(new FillLayout());
		createContent();
	}

	//������ʾ����
	private void createContent() {
		//���й����������
		ScrolledComposite scrolledComposite = new ScrolledComposite(viewForm, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		viewForm.setContent(scrolledComposite);
		container = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(container);
//		container.setLayout(new RowLayout(SWT.VERTICAL));//��һ�����ʹ�õ��������кܶ���龰ģʽʱ���Ի�����ʾ
		//�����VERTICAL�������ֱ��������������Ǻ��ŵĹ�����
		container.setLayout(new GridLayout(3, true));
		loadSceneModes();
		container.layout();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//������Ҫ��
	}

	//�����龰ģʽ
	private void loadSceneModes() {
//		SceneModeEditorComposite composite = new SceneModeEditorComposite(container, SWT.NONE, null);
		List<SceneMode> sceneModes = null;
		try {
			sceneModes = SceneModeXmlTool.getInstance().loadAllSceneMode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sceneModes != null && sceneModes.size() > 0) {//�������龰ģʽ
//		SceneModeEditorComposite composite;
			for (int i = 0, size = sceneModes.size(); i < size; i++) {
				new SceneModeEditorComposite(container, SWT.NONE, sceneModes.get(i));
			}
		}
		//���Ҫ���һ������龰ģʽ
		new NewSceneModeEditorComposite(container, SWT.NONE);
	}

	@Override
	public void setFocus() {
	}

}
