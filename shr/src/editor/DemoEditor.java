package editor;

import java.util.List;

import hibernate.TUser;
import hibernate.TUserDAO;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import shr.Activator;

public class DemoEditor extends EditorPart {

	public static final String ID = DemoEditor.class.getName();
	private TUserDAO userDao;
	private Text text;

	public DemoEditor() {
//		userDao = TUserDAO.getFromApplicationContext(Activator.getApplicationContext());		
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
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
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpdemo = new Group(parent, SWT.NONE);
		grpdemo.setText("\u6D4B\u8BD5demo");
		grpdemo.setLayout(new GridLayout(2, false));

		Label label = new Label(grpdemo, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("\u663E\u793A\u6570\u76EE");

		text = new Text(grpdemo, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		List<TUser> users = userDao.findAll();
		text.setText(users.size()+"");
	}

	@Override
	public void setFocus() {
	}

	public TUserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(TUserDAO userDao) {
		this.userDao = userDao;
	}

}
