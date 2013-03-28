package cameraUtil;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class CameraPanel extends JPanel {

	private Image dfImage;

	public CameraPanel(String imageFile) {
		dfImage = java.awt.Toolkit.getDefaultToolkit().getImage(
				CameraPanel.class.getClassLoader().getResource("app/cam.jpg"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(dfImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}
