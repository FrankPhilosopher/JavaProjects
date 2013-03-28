package mydip.util;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class ImageUtil {

	// ������ɫֵ��ʵ����������ֵ���õ�����
	public static int getBrightFromPixel(int pixel) {
		int r = (pixel & 0x00ff0000) >> 16;
		int g = (pixel & 0x0000ff00) >> 8;
		int b = (pixel & 0x000000ff);
		int bright = Math.round(0.3f * r + 0.59f * g + 0.11f * b);
		bright = bright < 0 ? 0 : bright;
		bright = bright > 255 ? 255 : bright;
		return bright;
	}

	// ���ݾɵ�����ֵ���µĻҶ�ֵ�õ��µ�����ֵ
	public static int getPixelFromBright(int oldPixel, int bright) {
		float[] yuv = getYUVFromPixel(oldPixel);
		yuv[0] = bright;// ע�����������RGBȥ�󣡶���ֱ�Ӹ�����ֵbright
		return getPixelFromYUV(yuv);
	}

	// ����RGB�����õ�����ֵ //ͨ�� palette �õ�
	public static int getPixelFromRGB(ImageData imageData, RGB rgb) {
//		int pixel = (255 << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		int pixel = imageData.palette.getPixel(rgb);
		return pixel;
	}

	// ����RGB��YUV��ת����������ֵ�еõ�YUV����
	public static float[] getYUVFromPixel(int pixel) {
		float[] yuv = new float[3];
		int r = (pixel & 0x00ff0000) >> 16;
		int g = (pixel & 0x0000ff00) >> 8;
		int b = (pixel & 0x000000ff);
		yuv[0] = (float) (0.3 * r + 0.59 * g + 0.11 * b);// yuv[1]��yuv[2]����yuv[0]û�й�ϵ��
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

	// ������ֵ�еõ�RGB����  //Ҳ����ͨ�� palette �õ�
	public static int[] getRGBFromPixel(int pixel) {
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0x00ff0000) >> 16;
		rgb[1] = (pixel & 0x0000ff00) >> 8;
		rgb[2] = (pixel & 0x000000ff);
		return rgb;
	}

	// ����YUV��RGB��ת����ͨ��YUV����õ�����ֵ
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

	// ���pixelData
	public static int[] getPixelData(ImageData data) {
		int width = data.width;
		int height = data.height;
		int[] pixelData = new int[width * height];// ���������е�����ֵ
		//���б�������ֵ
		for (int i = 0; i < height; i++) {
			data.getPixels(0, i, width, pixelData, i * width);
		}
		return pixelData;
	}

	// ���BrightData
	public static int[] getBrightData(ImageData data, int[] pixelData) {
		int width = data.width;
		int height = data.height;
		int[] brightData = new int[width * height];// ���������е�����ֵ
		// ���brightData
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				brightData[i * width + j] = ImageUtil.getBrightFromPixel(pixelData[i * width + j]);// �õ�����ֵ
			}
		}
		return brightData;
	}

	// ����pixelData��brightData�����µ�ImageData�������newData��
	// ���ڿռ���ĸ�ͨ�͵�ͨ�˲���
	public static void calculateNewData(int[] pixelData, int[] brightData, int[] mask, ImageData newData) {
		int width = newData.width;
		int height = newData.height;
		int newBright;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// ��Ե������
				if (i == 0 || i == 1 || i == (height - 1) || i == (height - 2) || j == 0 || j == 1 || j == (width - 1)
						|| j == (width - 2)) {
					continue;
				} else {
					// ����λ�� 3*3 �ķ��񣬸���mask�õ�
					newBright = (brightData[(i - 1) * width + (j - 1)] * mask[0] + brightData[(i - 1) * width + (j)]
							* mask[1] + brightData[(i - 1) * width + (j + 1)] * mask[2]
							+ brightData[(i) * width + (j - 1)] * mask[3] + brightData[(i) * width + (j)] * mask[4]
							+ brightData[(i) * width + (j + 1)] * mask[6] + brightData[(i + 1) * width + (j - 1)]
							* mask[6] + brightData[(i + 1) * width + (j)] * mask[7] + brightData[(i + 1) * width
							+ (j + 1)]
							* mask[8]) / 9;
				}
				// �ɵ�YUV����
				float[] yuv = ImageUtil.getYUVFromPixel(pixelData[i * width + j]);
				// �޸�brightֵΪ�µĻҶ�ֵ
				yuv[0] = newBright;
				// �õ��µ�����ֵ�����õ�newData��
				newData.setPixel(j, i, ImageUtil.getPixelFromYUV(yuv));
			}
		}
	}

	// ����pixelData�����µ�ImageData�������newData��
	// ���ڲ�ɫͼ�����У�����õ�ͼ���RGB����
	public static void calculateNewDataInRGB(int[] pixelData, ImageData newData) {
		int width = newData.width;
		int height = newData.height;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// �õ��µ�����ֵ�����õ�newData��
				newData.setPixel(j, i, pixelData[j + i * width]);//setPixel ǰ��������������λ��
			}
		}
	}

	/**
	 * ͨ������ֵ�õ�RGB����
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
	 * ͨ��RGB�����õ�����ֵ
	 */
	public static int encodeColor(int rgb[]) {
		int color = (255 << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		return color;
	}

	/**
	 * ������ɫֵ��ʵ����������ֵ���õ�����
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
	 * ��RGBת��YHS ��һ��������color(ʵ������pixel value) ͨ�������Եõ�RGB��ֵ �ڶ�����������������YHS��ֵ
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
	 * ��YUVת��RGB
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
