package imageEffects;

import org.eclipse.swt.graphics.*;
/**
 * Class for performing image embossing.
 *
 * References:
 * http://today.java.net/pub/a/today/2005/12/08/image-embossing.html
 */
public class Emboss {
	/**
	 * @param originalImageData The image data to be embossed.
	 * Transparency information will be ignored.
	 * @param grayLevel Minimum base level gray to use in embossed outcome.
	 * @return An embossed copy of the image data.
	 */
	public static ImageData emboss(ImageData originalImageData, int grayLevel) {
		// prepare new image data with 24-bit direct palette to hold embossed copy of image
		ImageData newImageData = new ImageData (originalImageData.width, originalImageData.height, 24, new PaletteData (0xFF, 0xFF00, 0xFF0000));
		grayLevel = grayLevel < 0 ? 0 : grayLevel > 255 ? 255 : grayLevel;	
		int rDiff = 0, gDiff = 0, bDiff = 0, gray = 0; // store intensity differences
		int[] rowResult = new int[newImageData.width];
		RGB[] rowRGBData1 = new RGB[newImageData.width], rowRGBData2 = new RGB[newImageData.width];
		getRGBRowData(originalImageData, rowRGBData2, 0); // get first line of pixel data	
		for (int row=0; row <newImageData.height; row++ ) {
			RGB[] tempRow = rowRGBData1;
			// swap references. the second row of pixel data now becomes the first
			if (row < newImageData.height - 1) {
				rowRGBData1 = rowRGBData2;
				rowRGBData2 = tempRow;
				getRGBRowData(originalImageData, rowRGBData2, row + 1);
			}
			for (int col = 0; col < newImageData.width; col++) {
				// for first two columns, compare the target pixel to the pixel above
				if (col  < 2) {
					rDiff = Math.abs(rowRGBData2[col].red - rowRGBData1[col].red);
					gDiff = Math.abs(rowRGBData2[col].green - rowRGBData1[col].green);
					bDiff = Math.abs(rowRGBData2[col].blue - rowRGBData1[col].blue);
				} else {
					// for all columns after the second column, compare the target pixel
					// to the pixel two pixels to the left on the row above
					rDiff = Math.abs(rowRGBData2[col].red - rowRGBData1[col-2].red);
					gDiff = Math.abs(rowRGBData2[col].green - rowRGBData1[col-2].green);
					bDiff = Math.abs(rowRGBData2[col].blue - rowRGBData1[col-2].blue);
				}				
				// calculate gray level
				gray = Math.min(grayLevel + (Math.max(rDiff, Math.max(gDiff, bDiff))), 255);
				rowResult[col] = newImageData.palette.getPixel (new RGB(gray, gray, gray));
			}
			newImageData.setPixels(0, row, newImageData.width, rowResult, 0);			
		}
		return newImageData;
	}

	/**
	 * Gets a row of pixel data as RGB values.
	 * @param imageData The ImageData to retrieve pixel data from
	 * @param resultData RGB array to store retrieved pixel data in
	 * @param row Row of imageData required
	 */
	private static void getRGBRowData(ImageData originalImageData, RGB[] resultData, int row) {
		// assuming resultData.length == originalImageData.width
		int[] pixels = new int[originalImageData.width];
		originalImageData.getPixels (0, row, originalImageData.width, pixels, 0);
		for (int col = 0; col < originalImageData.width; col++) {
			resultData[col] = originalImageData.palette.getRGB (pixels[col]);
		}
	}
}
