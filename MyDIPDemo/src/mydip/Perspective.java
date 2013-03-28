package mydip;

import mydip.views.NavigatorView;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		// layout.setEditorAreaVisible(false);//���ÿɱ༭������ʾ����Ҫ�رգ��ر��˵Ļ�һ��ʼ��ʾ��ֻ�����ĵ���������������չ������Ļ�ģ�
//		layout.addView(NavigatorView.ID, IPageLayout.LEFT, 0.3f, editorArea);// ��������ͼ��ӵ�͸��ͼ��
		//��������ͼ�����StandaloneView���ã���̫���������
		layout.addStandaloneView(NavigatorView.ID, true, IPageLayout.LEFT, 0.3f, editorArea);
	}
}
