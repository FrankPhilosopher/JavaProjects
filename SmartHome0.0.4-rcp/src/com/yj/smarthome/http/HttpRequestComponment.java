package com.yj.smarthome.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.yj.smarthome.camera.IpCameraMonitorComposite;

public class HttpRequestComponment implements IHttpRequestService {

	String defaultContentEncoding;
	IpCameraMonitorComposite camera;
	private boolean isConnect = false;

	public HttpRequestComponment(IpCameraMonitorComposite camera) {
		this.camera = camera;
		setDefaltContentEncoding("utf-8");
	}

	/**
	 * send http request
	 * 
	 * @param urlString
	 * @param method
	 *            : POST/GET
	 * @param parameters
	 * @param propertys
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(20000);//连接超时时间！
		urlConnection.setReadTimeout(100000);//读取的时间要长些！因为要进行一些必要的判断
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		return urlConnection;
	}

	/**
	 * get response object
	 * 
	 * @param urlString
	 * @param urlConnection
	 * @return
	 * @throws IOException
	 */
	private HttpResponse makeContent(HttpURLConnection urlConnection) throws IOException {
		HttpResponse httpResponser = new HttpResponse();
		InputStream in = null;
		try {
			in = urlConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			int left = 0;
			ByteBuffer bb = null;
			//end  回车换行
			byte[] end = { 13, 10, 13, 10 };
			//54 
			//--ipcamera
			//Content-Type: image/jpeg
			//Content-Length: 
			byte[] seperator = { 45, 45, 105, 112, 99, 97, 109, 101, 114, 97, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112,
					101, 58, 32, 105, 109, 97, 103, 101, 47, 106, 112, 101, 103, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 76, 101,
					110, 103, 116, 104, 58, 32 };//54
			int slen = seperator.length;
			byte[] map = { 45, 45, 105, 112, 99, 97, 109, 101, 114, 97 };//--ipcamera
			int count = 0;
			boolean canreads = false;
			while ((len = in.read(buffer)) != -1) {
				if (!isConnect)
					break;
				try {
					if (left <= 0) {//这个表示上次的图片内容已经读取完了，可以读取下一张图片了
						if (bb != null) {
//					    	   saveImage(bb, "e:/"+count+".jpg");
//					    	   count++;
//					    	   bb = null;
							camera.setImage(bb);
						}
						if (buffer[0] == 45 && buffer[1] == 45) {//--
							canreads = true;
							System.out.println("a new image!" + 0 + "\t\t" + new String(buffer, 0, 10));
							if (buffer[53] == 32) {//:
								char[] c = new char[10];
								int clen = 0;
								for (int i = 54; i < 54 + 10; i++) {//后面跟着一个数字，最后回车换行！
									if (buffer[i] == 13 && buffer[i + 1] == 10)
										break;
									c[i - 54] = (char) buffer[i];
									clen++;
								}
								String maplen = new String(c, 0, clen);//通过字符数组和它的真实长度得到一个字符串
								left = Integer.parseInt(maplen);//得到本次图片内容的字节长度
								bb = ByteBuffer.allocate(left + 2);//开辟空间，ByteBuffer的静态方法allocate
//								System.out.println("***********************eading length!" + maplen);
								int offset = 58 + clen;//54+两次回车换行(4)
								bb.put(buffer, 54 + clen + end.length, len - offset);//ByteBuffer.put(byte[] src, int offset, int length)
								left -= (len - offset);//剩下的没有读的长度
							}
						}
					} else {//上次的图片内容还没有读取完毕，就直接进行读取！
						if (canreads) {
							bb.put(buffer, 0, len);
							left -= len;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					canreads = false;
					left = 0;
					bb = null;
					continue;
				}
			}

//			   System.out.println("capacity:"+bb.capacity());
//			   System.out.println("position:"+bb.position());
//			   System.out.println("limit:"+bb.limit());
//			   System.out.println("remaining:"+bb.remaining());
//			   System.out.println("len:"+len);
//			   System.out.println("left:"+left);

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	//得到视频流
	public void getVideoStream(String urlString) throws IOException {
		HttpURLConnection urlConnection = send(urlString, "GET", null, null);
		makeContent(urlConnection);
	}

	//以post方式发送command
	public void sendCommandGet(String urlString) throws IOException {
		HttpURLConnection urlConnection = send(urlString, "GET", null, null);
		InputStream in = urlConnection.getInputStream();//这一句一定要有！
		in.close();//记得要关闭输入流！不然时间长了就会很卡！
	}

	//抓取图片
	public Image capturePic(String urlString) throws IOException {
		HttpURLConnection urlConnection = send(urlString, "GET", null, null);
		InputStream in = urlConnection.getInputStream();//这一句一定要有！
//		in.close();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>image.....");
		Image image = new Image(Display.getDefault(), in);
		return image;
	}

	public void saveImage(ByteBuffer bb, String filename) throws ImageFormatException, IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bais);
//	    BufferedImage image = decoder.decodeAsBufferedImage();
//	    BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(new File(filename)));
//	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bf);
//	    encoder.encode(image);
//	    bf.flush();
//	    bf.close();
	}

	@Override
	public HttpURLConnection sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}

	@Override
	public HttpURLConnection sendGet(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "GET", params, null);
	}

	@Override
	public HttpURLConnection sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	//这个方法被调用了
	@Override
	public HttpURLConnection sendPost(String urlString) throws IOException {
		return send(urlString, "POST", null, null);
	}

	@Override
	public HttpURLConnection sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	@Override
	public HttpURLConnection sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "POST", params, propertys);
	}

	/**
	 * set content enconding
	 */
	@Override
	public void setDefaltContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public static void main(String args[]) {
		//		String url = "http://192.168.0.178/snapshot.cgi?user=admin&pwd=123456";
//			String url = "http://192.168.0.178/videostream.cgi?user=admin&pwd=123456&resolution=320*240&rate=14";
//			Map<String, String> params = new HashMap<String, String>();
//			HttpRequestComponment hrc = new HttpRequestComponment(null);
//			hrc.setDefaltContentEncoding("utf-8");
//			try {
//				HttpResponse response = hrc.sendPost(url, params);
//	//			System.out.println(response.content);
//				System.out.println(response);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

	}

}
