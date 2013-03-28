package mydip.util;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class ImageUtil {

	// 根据颜色值（实际上是像素值）得到亮度
	public static int getBrightFromPixel(int pixel) {
		int r = (pixel & 0x00ff0000) >> 16;
		int g = (pixel & 0x0000ff00) >> 8;
		int b = (pixel & 0x000000ff);
		int bright = Math.round(0.3f * r + 0.59f * g + 0.11f * b);
		bright = bright < 0 ? 0 : bright;
		bright = bright > 255 ? 255 : bright;
		return bright;
	}

	// 根据旧的像素值和新的灰度值得到新的像素值
	public static int getPixelFromBright(int oldPixel, int bright) {
		float[] yuv = getYUVFromPixel(oldPixel);
		yuv[0] = bright;// 注意这里：不是用RGB去求！而是直接给它赋值bright
		return getPixelFromYUV(yuv);
	}

	// 根据RGB分量得到像素值 //通过 palette 得到
	public static int getPixelFromRGB(ImageData imageData, RGB rgb) {
//		int pixel = (255 << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		int pixel = imageData.palette.getPixel(rgb);
		return pixel;
	}

	// 根据RGB和YUV的转换，从像素值中得到YUV数组
	public static float[] getYUVFromPixel(int pixel) {
		float[] yuv = new float[3];
		int r = (pixel & 0x00ff0000) >> 16;
		int g = (pixel & 0x0000ff00) >> 8;
		int b = (pixel & 0x000000ff);
		yuv[0] = (float) (0.3 * r + 0.59 * g + 0.11 * b);// yuv[1]和yuv[2]都和yuv[0]没有关系！
		double c1 = 0.7 * r - 0.59 * g - 0.11 * b;
		double c2 = -0.3 * r - 0.59 * g + 0.89 * b;
		yuv[2] = (float) Math.sqrt(c1 * c1 + c2 * c2);
		if (yuv[2] < 0.005) {
			yuv[1] = 0;
		} else {
			yuv[1] = (float) Math.atan2(c1, c2);
			if (yuv[1] < 0)
				yuv[1] += (float) Math.PI * 2;
		}
		return yuv;
	}

	// 从像素值中得到RGB数组  //也可以通过 palette 得到
	public static int[] getRGBFromPixel(int pixel) {
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0x00ff0000) >> 16;
		rgb[1] = (pixel & 0x0000ff00) >> 8;
		rgb[2] = (pixel & 0x000000ff);
		return rgb;
	}

	// 根据YUV和RGB的转换，通过YUV数组得到像素值
	public static int getPixelFromYUV(float yuv[]) {
		double c1 = yuv[2] * Math.sin(yuv[1]);
		double c2 = yuv[2] * Math.cos(yuv[1]);
		int r = (int) Math.round(yuv[0] + c1);
		r = r < 0 ? 0 : r;
		r = r > 255 ? 255 : r;
		int g = (int) Math.round(yuv[0] - 0.3 * c1 / 0.9 - 0.11 * c2 / 0.59);
		g = g < 0 ? 0 : g;
		g = g > 255 ? 255 : g;
		int b = (int) Math.round(yuv[0] + c2);
		b = b < 0 ? 0 : b;
		b = b > 255 ? 255 : b;
		int pixel = (255 << 24) | (r << 16) | (g << 8) | b;
		return pixel;
	}

	// 填充pixelData
	public static int[] getPixelData(ImageData data) {
		int width = data.width;
		int height = data.height;
		int[] pixelData = new int[width * height];// 保存了所有的像素值
		//逐行保存像素值
		for (int i = 0; i < height; i++) {
			data.getPixels(0, i, width, pixelData, i * width);
		}
		return pixelData;
	}

	// 填充BrightData
	public static int[] getBrightData(ImageData data, int[] pixelData) {
		int width = data.width;
		int height = data.height;
		int[] brightData = new int[width * height];// 保存了所有的像素值
		// 填充brightData
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				brightData[i * width + j] = ImageUtil.getBrightFromPixel(pixelData[i * width + j]);// 得到亮度值
			}
		}
		return brightData;
	}

	// 根据pixelData和brightData计算新的ImageData，填充在newData中
	// 用在空间域的高通和低通滤波中
	public static void calculateNewData(int[] pixelData, int[] brightData, int[] mask, ImageData newData) {
		int width = newData.width;
		int height = newData.height;
		int newBright;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// 边缘不处理
				if (i == 0 || i == 1 || i == (height - 1) || i == (height - 2) || j == 0 || j == 1 || j == (width - 1)
						|| j == (width - 2)) {
					continue;
				} else {
					// 其他位置 3*3 的方格，根据mask得到
					newBright = (brightData[(i - 1) * width + (j - 1)] * mask[0] + brightData[(i - 1) * width + (j)]
							* mask[1] + brightData[(i - 1) * width + (j + 1)] * mask[2]
							+ brightData[(i) * width + (j - 1)] * mask[3] + brightData[(i) * width + (j)] * mask[4]
							+ brightData[(i) * width + (j + 1)] * mask[6] + brightData[(i + 1) * width + (j - 1)]
							* mask[6] + brightData[(i + 1) * width + (j)] * mask[7] + brightData[(i + 1) * width
							+ (j + 1)]
							* mask[8]) / 9;
				}
				// 旧的YUV数组
				float[] yuv = ImageUtil.getYUVFromPixel(pixelData[i * width + j]);
				// 修改bright值为新的灰度值
				yuv[0] = newBright;
				// 得到新的像素值并设置到newData中
				newData.setPixel(j, i, ImageUtil.getPixelFromYUV(yuv));
			}
		}
	}

	// 根据pixelData计算新的ImageData，填充在newData中
	// 用在彩色图像处理中，例如得到图像的RGB分量
	public static void calculateNewDataInRGB(int[] pixelData, ImageData newData) {
		int width = newData.width;
		int height = newData.height;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// 得到新的像素值并设置到newData中
				newData.setPixel(j, i, pixelData[j + i * width]);//setPixel 前两个参数是坐标位置
			}
		}
	}

	/**
	 * 通过像素值得到RGB分量
	 */
	public static int[] decodeColor(int color, int rgb[]) {
		if (rgb == null)
			rgb = new int[3];
		rgb[0] = (color & 0x00ff0000) >> 16;
		rgb[1] = (color & 0x0000ff00) >> 8;
		rgb[2] = (color & 0x000000ff);
		return rgb;
	}

	/**
	 * 通过RGB分量得到像素值
	 */
	public static int encodeColor(int rgb[]) {
		int color = (255 << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		return color;
	}

	/**
	 * 根据颜色值（实际上是像素值）得到亮度
	 */
	public static int getBrightness(int color) {
		int r = (color & 0x00ff0000) >> 16;
		int g = (color & 0x0000ff00) >> 8;
		int b = (color & 0x000000ff);
		int y = Math.round(0.3f * r + 0.59f * g + 0.11f * b);
		y = y < 0 ? 0 : y;
		y = y > 255 ? 255 : y;
		return y;
	}

	/**
	 * 将RGB转成YHS 第一个参数是color(实际上是pixel value) 通过它可以得到RGB的值 第二个参数是用来保存YHS的值
	 */
	public static float[] convertRGBToYUV(int color, float yuv[]) {
		if (yuv == null)
			yuv = new float[3];
		int r = (color & 0x00ff0000) >> 16;
		int g = (color & 0x0000ff00) >> 8;
		int b = (color & 0x000000ff);
	
		yuv[0] = (float) (0.3 * r + 0.59 * g + 0.11 * b);
		double c1 = 0.7 * r - 0.59 * g - 0.11 * b;
		double c2 = -0.3 * r - 0.59 * g + 0.89 * b;
		yuv[2] = (float) Math.sqrt(c1 * c1 + c2 * c2);
		if (yuv[2] < 0.005) {
			yuv[1] = 0;
		} else {
			yuv[1] = (float) Math.atan2(c1, c2);
			if (yuv[1] < 0)
				yuv[1] += (float) Math.PI * 2;
		}
		return yuv;
	}

	/**
	 * 将YUV转成RGB
	 */
	public static int convertYUVToRGB(float yuv[]) {
		double c1 = yuv[2] * Math.sin(yuv[1]);
		double c2 = yuv[2] * Math.cos(yuv[1]);
		int r = (int) Math.round(yuv[0] + c1);
		r = r < 0 ? 0 : r;
		r = r > 255 ? 255 : r;
		int g = (int) Math.round(yuv[0] - 0.3 * c1 / 0.9 - 0.11 * c2 / 0.59);
		g = g < 0 ? 0 : g;
		g = g > 255 ? 255 : g;
		int b = (int) Math.round(yuv[0] + c2);
		b = b < 0 ? 0 : b;
		b = b > 255 ? 255 : b;
	
		int color = (255 << 24) | (r << 16) | (g << 8) | b;
		return color;
	}

}
