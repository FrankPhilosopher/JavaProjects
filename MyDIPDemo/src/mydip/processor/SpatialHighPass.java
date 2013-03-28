package mydip.processor;

import mydip.util.ImageUtil;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class SpatialHighPass implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		ImageData newData = oldData;
		int[] pixelData = ImageUtil.getPixelData(oldData);// ���������е�����ֵ
		int[] brightData = ImageUtil.getBrightData(oldData, pixelData);// ���������еĻҶ�ֵ
//		int[] mask = new int[] { 1, 1, 1, 1, -8, 1, 1, 1, 1 };// mask����
//		int[] mask = new int[] {-1,-1,-1,-1,8,-1,-1,-1,-1};
		int[] mask = new int[] {0,1,0,1,4,1,0,1,0};//��һ�ֵ�Ч�����
		ImageUtil.calculateNewData(pixelData, brightData, mask, newData);//�����ͼ���ImageData
		return new Image(Display.getDefault(), newData);// ע�⣬����Imageʱ��һ��������Display
	}

}
