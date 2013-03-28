package imageEffects;

import org.eclipse.swt.graphics.*;
public class Glow {
	/**
	 * @param originalImageData The original image. Transparency information will be ignored.
	 * @param color The color of the glow
	 * @param radius The radius of the glow in pixels
	 * @param highlightRadius The radius of the highlight area
	 * @param opacity The opacity of the glow
	 * @return The glowing image.
	 * This image data will be larger than the original.
	 * The same image data will be returned if the glow radius is 0,
	 * or null if an error occured.
	 */
	public static ImageData glow(ImageData originalImageData, Color color, int radius, int highlightRadius, int opacity) {
		/*
		 * This method will surround an existing image with a glowing border.
		 * This glow is created by adding a solid colored border around an image.
		 * Alpha values are then manipulated in order to blend the border
		 * with its background. This gives a glowing appearance.
		 * 
		 * To obtain the alpha value of a glow pixel, its position in the border radius
		 * as a percent of the radius' total width is first calculated. This percentage
		 * is multipled by the maximum opacity level, giving pixels an outward linear blend
		 * from the image from opaque to transparent.
		 * 
		 * A highlight radius increases the intensity of a given radius of pixels 
		 * surrounding the image to better highlight it. When there is a highlight radius,
		 * the entire glow's overall alpha blending is non-linear.
		 */
		if (originalImageData == null) return null;
		if (color == null) return null;
		if (radius == 0) return originalImageData;
		// the percent increase in color intensity in the highlight radius
		double highlightRadiusIncrease = radius < highlightRadius * 2 ? .15 : radius < highlightRadius * 3 ? .09 : .02;
		opacity = opacity > 255 ? 255 : opacity < 0 ? 0 : opacity;		
		// prepare new image data with 24-bit direct palette to hold glowing copy of image
		ImageData newImageData = new ImageData (originalImageData.width + radius * 2, originalImageData.height + radius * 2, 24, new PaletteData (0xFF, 0xFF00, 0xFF0000));
		int[] pixels = new int[originalImageData.width];
		// copy image data
		for (int row = radius; row < radius + originalImageData.height; row++) {
			originalImageData.getPixels (0, row - radius, originalImageData.width, pixels, 0);
			for (int col = 0; col < pixels.length; col++)
				pixels[col] = newImageData.palette.getPixel (originalImageData.palette.getRGB (pixels[col]));
			newImageData.setPixels (radius, row, originalImageData.width, pixels, 0);
		}
		// initialize glow pixel data
		int colorInt = newImageData.palette.getPixel (color.getRGB ());
		pixels = new int[newImageData.width];
		for (int i = 0; i < newImageData.width; i++) {
			pixels[i] = colorInt;
		}
		// initialize alpha values
		byte[] alphas = new byte[newImageData.width];
		// deal with alpha values on rows above and below the photo
		for (int row = 0; row < newImageData.height; row++) {
			if (row < radius) {
				// only calculate alpha values for top border. they will reflect to the bottom border
				byte intensity = (byte) (opacity * ((((row + 1)) / (double) (radius))));
				for (int col = 0; col < alphas.length / 2 + alphas.length % 2; col++) {
					if (col < radius) {
						// deal with corners:
						// calculate pixel's distance from image corner
						double hypotenuse = Math.sqrt (Math.pow (radius - col - 1, 2.0) + Math.pow (radius - 1 - row, 2.0));
						// calculate alpha based on percent distance from image
						alphas[col] = alphas[alphas.length - col - 1] = (byte)(opacity * Math.max (((radius - hypotenuse) / radius), 0));
						// add highlight radius
						if (hypotenuse < Math.min (highlightRadius, radius * .5)) {
							alphas[col] = alphas[alphas.length - col - 1] =	(byte) Math.min (255, (alphas[col] & 0x0FF)
									* (1 + highlightRadiusIncrease * Math.max (((radius - hypotenuse) / radius), 0)));
						}
					}
					else {
						alphas[col] =	alphas[alphas.length - 1 - col] = (byte) (
								(row > Math.max (radius - highlightRadius - 1, radius * .5)) ? Math.min (255, (intensity & 0x0FF)	
														* (1 + highlightRadiusIncrease * row / radius)) : intensity);
					}
				}
				newImageData.setAlphas (0, row, newImageData.width, alphas, 0);
				newImageData.setAlphas (0, newImageData.height - 1 - row, newImageData.width, alphas, 0);
				newImageData.setPixels (0, row, newImageData.width, pixels, 0);
				newImageData.setPixels (0, newImageData.height - 1 - row, newImageData.width, pixels, 0);
			}
			// deal with rows the image resides on
			else if (row <= newImageData.height / 2) {
				// calculate alpha values
				double intensity = 0;
				for (int col = 0; col < alphas.length; col++) {
					if (col < radius) {
						intensity = (opacity * ((col + 1) / (double) radius));
						if (col > Math.max (radius - highlightRadius - 1, radius * .5)) {
							intensity =	Math.min (255, (intensity)	* (1 + highlightRadiusIncrease * col / radius));
						}
						alphas[col] = alphas[newImageData.width - col - 1] = (byte) (intensity);
					}
					else if (col <= newImageData.width / 2 + newImageData.width % 2) {
						// original image pixels are full opacity
						alphas[col] = alphas[newImageData.width - col - 1] = (byte) (255);
					}
				}
				newImageData.setPixels (0, row, radius, pixels, 0);
				newImageData.setPixels (originalImageData.width + radius, row, radius, pixels, 0);
				newImageData.setAlphas (0, row, newImageData.width, alphas, 0);
				newImageData.setPixels (0, newImageData.height - 1 - row, radius, pixels, 0);
				newImageData.setPixels (originalImageData.width + radius, newImageData.height - 1 - row, radius, pixels, 0);
				newImageData.setAlphas (0, newImageData.height - 1 - row, newImageData.width, alphas, 0);
			}
		}
		return newImageData;
	}
}
