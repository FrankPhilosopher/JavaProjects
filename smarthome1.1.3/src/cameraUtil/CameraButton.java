package cameraUtil;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

public class CameraButton extends JButton {

	private Image image;

	public CameraButton(String imageFile) {
		image = java.awt.Toolkit.getDefaultToolkit().getImage(
				CameraButton.class.getClassLoader().getResource(imageFile));
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

}
