package com.rcp.wbw.skin;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import parkingsystem.Activator;

import com.swtdesigner.SimpleColorManager;

public class LookAndFeel {
	private static LookAndFeel instance;
	private static String SKIN_TYPE = "SKIN_TYPE";
	private static String RCP_SETTING = "RCP_SETTING";
	private static String CURRENT_TYPE = "blue";
	private static final String TYPE_BLUE = "blue";

	private LookAndFeel() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings rcp_settings = settings.getSection(RCP_SETTING);
		if (rcp_settings != null) {
			if (rcp_settings.get(SKIN_TYPE) != null && !"".equals(rcp_settings.get(SKIN_TYPE)))
				CURRENT_TYPE = rcp_settings.get(SKIN_TYPE);
		}
	}

	synchronized public static LookAndFeel getDefault() {
		if (instance == null) {
			instance = new LookAndFeel();
		}
		return instance;
	}

	public Image getToolBarImage() {
		return Activator.getImageDescriptor("icons/toolbar_blue.jpg").createImage();
	}

	public Image getMenuImage() {
		return Activator.getImageDescriptor("icons/menu_blue.jpg").createImage();

	}

	public Image getContentBgImage() {
		return Activator.getImageDescriptor("icons/content_blue.jpg").createImage();
	}

	public Color getTabFolderColor() {
		RGB rgb = new RGB(210, 234, 255);
		return SimpleColorManager.get(rgb.toString(), rgb);
	}

	public Color getShellColor() {
		RGB rgb = new RGB(151, 205, 255);
		return SimpleColorManager.get(rgb.toString(), rgb);
	}

}
