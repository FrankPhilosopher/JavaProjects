package mydip.processor;

import java.util.LinkedList;
import java.util.ListIterator;

import mydip.util.ImageUtil;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class HistogramEqualization implements IImageProcessor {

	@Override
	public Image process(Image image) {
		ImageData oldData = image.getImageData();
		ImageData newData = oldData;
		int width = oldData.width;
		int height = oldData.height;
		int bright;
		int[] pixelData = ImageUtil.getPixelData(oldData);// 保存了所有的像素值
		float yuv[] = new float[3];
		LinkedList histIndexs[] = new LinkedList[256];//用来保存原来的灰度级分布
		for (int i = 0; i < 256; i++) {
			histIndexs[i] = new LinkedList();
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				yuv = ImageUtil.getYUVFromPixel(pixelData[j * width + i]);
				bright = Math.round(yuv[0]);//对bright进一步处理
				bright = bright < 0 ? 0 : bright;
				bright = bright > 255 ? 255 : bright;
				histIndexs[bright].addLast(new HistPlaneInnerStruct(i, j, yuv[1], yuv[2]));//此处是要得到YUV数组
			}
		}
		int avg = width * height / 256;//平均数
		int index = 255;
		ListIterator it = histIndexs[index].listIterator();
		for (int i = 255; i >= 0; i--) {
			for (int j = avg; j > 0; j--) {
				while (!it.hasNext()) {
					index--;
					it = histIndexs[index].listIterator();
				}
				HistPlaneInnerStruct hpis = (HistPlaneInnerStruct) it.next();
				yuv[0] = i;
				yuv[1] = hpis.h;
				yuv[2] = hpis.s;
				newData.setPixel(hpis.x, hpis.y, ImageUtil.getPixelFromYUV(yuv));
			}
		}
		return new Image(Display.getDefault(), newData);
	}

	// 灰度均衡内部结构，保存某个坐标位置的原来的h和s的值
	public class HistPlaneInnerStruct {
		int x;
		int y;
		float h; // 色相
		float s; // 饱和度

		public HistPlaneInnerStruct(int x, int y, float h, float s) {
			this.x = x;
			this.y = y;
			this.h = h;
			this.s = s;
		}
	}

}
