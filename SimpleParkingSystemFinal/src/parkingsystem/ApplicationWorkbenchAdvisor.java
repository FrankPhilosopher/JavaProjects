package parkingsystem;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "ParkingSystem.perspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

//    public void initialize(IWorkbenchConfigurer configurer) {
//        super.initialize(configurer);
//        configurer.setSaveAndRestore(true);
//    }

	//If the IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID preference is specified, 
	//it supercedes the perspective specified here. 
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

}
