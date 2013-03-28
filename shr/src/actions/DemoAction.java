package actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

import editor.DemoEditor;
import editor.DemoEditorInput;

public class DemoAction implements IWorkbenchWindowActionDelegate {

	public static final String ID = DemoAction.class.getName();
	private IWorkbenchWindow window;

	private DemoEditor demoeditor;

	@Override
	public void run(IAction action) {
		IEditorInput input = DemoEditorInput.EDITOR_INPUT;
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(input);
		String editorID = DemoEditor.ID;
		if (editor != null) {
			workbenchPage.bringToTop(editor);
		} else {
			try {
				workbenchPage.openEditor(input, editorID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		
//		MessageDialogUtil.showInfoMessage(window.getShell(), "×¢Èë "+demoeditor.getUserDao().NAME);
		
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;

	}

	public DemoEditor getDemoeditor() {
		return demoeditor;
	}

	public void setDemoeditor(DemoEditor demoeditor) {
		this.demoeditor = demoeditor;
	}

}
