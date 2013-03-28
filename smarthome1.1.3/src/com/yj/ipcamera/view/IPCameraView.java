package com.yj.ipcamera.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import util.AppliactionUtil;

import com.yj.ipcamera.view.IPCameraView.TYPE_NUMBER;

import beans.CameraControl;

import communication.CommunicationUtil;

import cameraUtil.CameraButton;

/**
 * H.264IPcamera view
 * @author wuxuehong
 *
 * @date 2012-8-24
 */
public class IPCameraView extends JPanel implements ICamera{

	private final static String TAG = "IPCameraView";
	int width;
	int height;
	byte[] mPixel;
	int[] mPixelRGB888;
	STATUS status = STATUS.INIT;
	private STATUS pre_status;
	public enum STATUS {
		INIT, PLAY, PAUSE, STOP
	};
	private boolean isInited;
	
	/**
	 * 在初始化视频设备时，指定通道和码流 <br/>
	 * 注意：目前设备只有第一个通道
	 */
	public enum TYPE_NUMBER {
		FIRST_MAIN(11), // 第一通道主码流
		FIRST_MINOR(12), // 第一通道次码流
		SECOND_MAIN(21), // 第二通道主码流
		SECOND_MINOR(22);// 第二通道次码流
		final int value;
		private TYPE_NUMBER(int value) {
			this.value = value;
		}
	}

	Thread mSocketThread;
	Socket socket;
	private DataInputStream dinVideoStream;
	private DataOutputStream doutVideoStream;

	private int socketLength = 40960;
	private byte[] streamBuffer = new byte[socketLength];
	private String encoding = "utf-8";

	private byte[] framehead = new byte[20];// 网络数据头
	private byte[] NalBuf = new byte[409600];// 缓存解码帧数据
	private byte[] SockBuf = new byte[409600];// 缓存从网络上接收的数据

	private byte[] SecondBuf = new byte[409600];// 用于组帧
	private int preTs = -1;// 前一帧的时间戳
	private int preNaluType = -1;// 前一nalu的type
	private int preNaluLen = 0;// 前一nalu的长度

	/**
	 * 0 init value<br/>
	 * 97 G726<br/>
	 * 8 G711a
	 */
	private int audioType;
	private boolean authorized;// 用户名、密码是否通过校验

	private String ip;
	private int port;
	private String username;
	private String password;
	private TYPE_NUMBER type_number;
	private int videoflip;
	private BufferedImage videoImage;
	private boolean exception = false;
	private Image error, wait;
	
	//wuxuehong done
	private CameraControl cameraControl;
	
	public IPCameraView(CameraControl cameraControl) {
		this.cameraControl = cameraControl;
		error = java.awt.Toolkit.getDefaultToolkit().getImage(
				CameraButton.class.getClassLoader().getResource("app/error.jpg"));
		wait = java.awt.Toolkit.getDefaultToolkit().getImage(
				CameraButton.class.getClassLoader().getResource("app/wait.jpg"));
	}

	@Override
	public void paint(Graphics g) {
		// System.out.println("e:"+exception);
		if (videoImage == null) {
			super.paint(g);
			if (exception) {
				g.drawImage(error, 0, 0, this.getWidth(), this.getHeight(), null);
			} else {
				g.drawImage(wait, 0, 0, this.getWidth(), this.getHeight(), null);
			}
			return;
		}
		g.drawImage(videoImage, 0, 0, this.getWidth(), this.getHeight(), null);
		g.dispose();
	}

	/**
	 * 初始化视频设备参数，并不会连接，需要调用play()方法后才会开始尝试连接
	 * 
	 * @param ip
	 *            视频设备的IP地址
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param type_number
	 *            通道和码流类型
	 * @param handler
	 *            用于接收和处理错误/异常消息
	 */
	public void init(String ip, int port, String username, String password, TYPE_NUMBER type_number,int videoflip/* ,Handler handler */) {
		//log(TAG, "init ip = " + ip + " , port = " + port + " , type_number = " + type_number);
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.type_number = type_number;
		// mHandler = handler;
		this.videoflip = videoflip;

		isInited = true;
		status = STATUS.INIT;
	}

	/**
	 * 开始连接并播放
	 */
	private void play() {
		//log(TAG, "play() ++ status = " + status);
		if (!isInited) {
			// Log.e(TAG, "Not inited! Can not call paly() method!");
			return;
		}
		switch (status) {
		case INIT:
		case STOP:
			log(TAG, "startplay");
			startPlay();
			// Debug.startMethodTracing();
			break;
		case PLAY:
			// startPlay();
			break;
		case PAUSE:
			status = STATUS.PLAY;
			break;

		}
		log(TAG, "play() -- status = " + status);
	}

	/**
	 * 恢复pause之前的播放状态，与pause()方法在Activity的onResume和onPause中配合使用
	 */
	private void resume() {
		log(TAG, "resume() ++ status = " + status);
		if (pre_status != null)
			status = pre_status;
		log(TAG, "resume() -- status = " + status);
	}

	/**
	 * 将当前状态置为暂停 ，与resume()方法在Activity的onResume和onPause中配合使用
	 */
	private void pause() {
		log(TAG, "pause() ++ status = " + status);
		pre_status = status;
		status = STATUS.PAUSE;
		log(TAG, "pause() -- status = " + status);
	}

	/**
	 * 停止播放
	 */
	private void stop() {
		log(TAG, "stop() ++ status = " + status);
		thistestc = false;
		if (mSocketThread == null) {
			status = STATUS.INIT;
		} else {
			status = STATUS.STOP;
			// Debug.stopMethodTracing();
			mSocketThread.interrupt();
			// mMessageThread.interrupt();
		}
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log(TAG, "stop() -- status = " + status);
	}

	/**
	 * 开始连接并解码播放
	 */
	private void startPlay() {
		initConnection();
	}

	/**
	 * @return 当前状态是否是STATUS.PLAY
	 */
	private boolean isPlaying() {
		return status == STATUS.PLAY;
	}

	private Runnable mDecoderRunnable = new Runnable() {
		@Override
		public void run() {
				log(TAG, "Thread Start");
				int bytesRead = -1;
				boolean mGetResponse = false;
				try {
					// 在连接建立后，需首先接收Server传回的HTTP/1.1 200 OK消息以确保连接成功
					bytesRead = dinVideoStream.read(streamBuffer, 0, socketLength);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bytesRead > 20) {
					log(TAG, "bytesRead : " + bytesRead + " , streamBuffer.length : " + streamBuffer.length);
					if (!mGetResponse) {
						try {
							String temp = new String(copyOf(streamBuffer, 0, bytesRead), encoding);
							log(TAG, "streamInputBuffer :\r\n" + temp);
							// 分析当前接收到的消息
							mGetResponse = analyzeResponse(temp);
							log(TAG, "videoWidth = " + width + " , videoHeight = " + height + " , audioType = "
									+ audioType + " , authorized =" + authorized);
							status = mGetResponse ? STATUS.PLAY : STATUS.STOP;
						} catch (Exception e) {
							e.printStackTrace();
							exception = true;
							paint(getGraphics());
						}
					}
				}
				int retHead, len, ts, seqno, retFrame, nalu_type, nalLen;
				long l1;
				while (!Thread.currentThread().isInterrupted() && thistestc) {
					if (status == STATUS.PAUSE) {
						continue;
					} else if (status == STATUS.STOP) {
						return;
					}
					retHead = GetheadH264(framehead);
					len = getFrameDateLenH264(framehead);
					ts = getFrameDataTimeH264(framehead);
					seqno = getFrameDataSeqnoH264(framehead);
					retFrame = GetFrameDataH264(SockBuf, len);
					nalu_type = SockBuf[4] & 0x1f;
					// log(TAG,"retHead = "+retHead+" , len = "+len+" , retFrame = "+retFrame+" , ts = "+ts+" , preTs = "+preTs+" , seqno = "+seqno+" , nalu_type = "+nalu_type);
					if (retHead < 0 || retHead > 20 || retFrame < 0) {
						System.out.println("No Data / Data Error. Re play after 5 seconds");
//						new Timer().schedule(new TimerTask() {
//							@Override
//							public void run() {
//								play();
//								this.cancel();
//							}
//						}, 5000);
						exception = true;
						paint(getGraphics());
						break;
					}
					nalLen = len <= retFrame && len > 0 ? len : retFrame;
					if (preTs == -1 && preNaluType == -1) {
						System.arraycopy(SockBuf, 0, NalBuf, 0, nalLen);
						// Log.i(TAG,"begin buffer");
						preTs = ts;
						preNaluType = nalu_type;
						preNaluLen = nalLen;
						continue;
					}
					if (ts == preTs && nalu_type == preNaluType) {
						System.arraycopy(SockBuf, 0, NalBuf, preNaluLen, nalLen);
						preNaluLen += nalLen;
					} else {
						int ret = DecoderNal(NalBuf, preNaluLen, mPixel);
						if (ret > 0) {
							convert_2_RGB24();// RGB888
							for (int j = 0; j < height; j++) {
								for (int i = 0; i < width; i++) {
									videoImage.setRGB(i, j, mPixelRGB888[j * width + i]);
								}
							}
							updateUI();
						}
						preTs = ts;
						preNaluType = nalu_type;
						preNaluLen = nalLen;
						System.arraycopy(SockBuf, 0, NalBuf, 0, nalLen);
					}
				}
				UninitDecoder();
				status = STATUS.STOP;
				log(TAG, "Thread End.");
			}
	};

	public void convert_2_RGB24() {
		int j = 0;
		int length = width * height;
		int curPixel = 0;
		int alpha = 0xff000000;
		int byteR = 0;
		int byteG = 0;
		int byteB = 0;
		for (int i = 0; i < length; i++) {
			byteR = 0x000000ff & mPixel[j++];
			byteG = 0x000000ff & mPixel[j++];
			byteB = 0x000000ff & mPixel[j++];
			curPixel = 0;
			curPixel = (alpha) | (byteR << 16) | (byteG << 8) | (byteB);
			mPixelRGB888[i] = curPixel;
		}
	}

	/**
	 * 与设备建立连接
	 */
	private void initConnection() {
		new Thread() {
			@Override
			public void run() {
				int timeOut = 5000;
				int cLength = 57;
				StringBuilder sBuilder = new StringBuilder();
				// sBuilder.append("GET /livestream/12?action=play&media=video HTTP/1.0\r\n");
				sBuilder.append("GET /livestream/");
				sBuilder.append(type_number.value);
				sBuilder.append("?action=play&media=video HTTP/1.0\r\n");
				sBuilder.append("User-Agent: HiIpcam/V100R003 VodClient/1.0.0\r\n");
				sBuilder.append("Connection: Keep-Alive\r\n");
				sBuilder.append("Cache-Control: no-cache\r\n");
				sBuilder.append(String.format("Authorization: %s %s\r\n", username, password));
				sBuilder.append(String.format("Content-Length: %d\r\n", cLength));
				sBuilder.append("\r\n");
				sBuilder.append("Cseq: 1");
				sBuilder.append("Transport: RTP/AVP/TCP;unicast;interleaved=0-1");
				sBuilder.append("\r\n");
				byte[] getVideoRequest = sBuilder.toString().getBytes();
				try {
					socket = new Socket(ip, port);
					socket.setSoTimeout(timeOut);
					log(TAG, "this is init videostream");
					dinVideoStream = new DataInputStream(socket.getInputStream());
					doutVideoStream = new DataOutputStream(socket.getOutputStream());
					doutVideoStream.write(getVideoRequest);
				} catch (Exception e) {
					e.printStackTrace();
					exception = true;
					paint(getGraphics());
				}
				if (mSocketThread != null) {
					mSocketThread.interrupt();
				}
				mSocketThread = new Thread(mDecoderRunnable, "SocketThread");
				mSocketThread.start();
			};
		}.start();
	}

	/**
	 * 分析连接建立后，设备回传到第一条消息，确认是否是HTTP/1.1 200 OK，以代表连接建立成功 <br>
	 * 在此消息中，会包含width，height等值，将在此处进行初始化
	 * 
	 * @param response
	 *            接收到的第一条消息
	 * @return 消息是否以HTTP/1.1 200 OK开头
	 */
	private boolean analyzeResponse(String response) {
		boolean ret = false;
		width = 0;
		height = 0;
		audioType = 0;
		authorized = false;
		String[] arrays = response.split("\r\n");
		if (arrays[0].startsWith("HTTP/1.1 200 OK")) {
			authorized = true;
			ret = true;
			if (arrays[11].startsWith("m=video")) {
				String[] videoArrays = arrays[11].split(" ");
				String[] videoRangeArrays = videoArrays[2].split("/");
				width = Integer.valueOf(videoRangeArrays[2]);
				height = Integer.valueOf(videoRangeArrays[3]);
				mPixel = new byte[width * height * 3];
				mPixelRGB888 = new int[width * height];
				videoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < mPixel.length; i++) {
					mPixel[i] = (byte) 0x00;
				}
			}
			if (arrays[12].startsWith("m=audio")) {
				String[] audioArrays = arrays[12].split(" ");
				audioType = Integer.valueOf(audioArrays[1]);
			}
		} else {
			exception = true;
			authorized = false;
			ret = false;
			return ret;
		}
		InitDecoder(width, height);
		return ret;
	}


	/**
	 * 是否翻转
	 */
	public void controlFlip(){
		if(AppliactionUtil.DEBUG) System.out.println("flip is:"+videoflip);
		if(videoflip ==0)
			sendControlCommand(0,"noflip",0);
		else if(videoflip == 1)
			sendControlCommand(0,"flip",0);
	}
	
	
	
	/**
	 * 发送云台控制命令(GET)
	 * 
	 * @param step
	 *            <li>0：单步执行后，需再次发停止命令才能停止 <li>1：单步执行后即自动停止
	 * @param act
	 *            命令类型 <br>
	 *            控制命令字符串，如： <li>left：向左 <li>right：向右 <li>up：向上 <li>down：向下 <li>
	 *            home：回到中心点 <li>zoomin：拉近 <li>zoomout：拉远 <li>hscan：水平巡航 <li>
	 *            vscan：垂直巡航 <li>stop：停止 <li>focusin：聚焦近 <li>focusout：聚焦远 <li>
	 *            aperturein：光圈小 <li>apertureout：光圈大 <li>auto：自动巡航 <li>brush：雨刷 <li>light：灯光
	 * @param speed
	 *            云台速度，值的范围为1~63。
	 */
	private void sendControlCommand(final int step, final String act, final int speed) {
		if (!isInited) {
			log(TAG, "Not inited! Can not call paly() method!");
			return;
		}
		new Thread() {
			@Override
			public void run() {
				StringBuilder sBuilder = new StringBuilder();
				if(act.equals("noflip")){
					sBuilder.append("http://");
					sBuilder.append(ip);
					sBuilder.append(":");
					sBuilder.append(port);
					sBuilder.append("/cgi-bin/hi3510/param.cgi?cmd=setimageattr&-mirror=on&-flip=");
					sBuilder.append(URLEncoder.encode(String.valueOf("on")));
				}else if(act.equals("flip")){
					sBuilder.append("http://");
					sBuilder.append(ip);
					sBuilder.append(":");
					sBuilder.append(port);
					sBuilder.append("/cgi-bin/hi3510/param.cgi?cmd=setimageattr&-mirror=off&-flip=");
					sBuilder.append(URLEncoder.encode(String.valueOf("off")));
				}else{
					sBuilder.append("http://");
					sBuilder.append(ip);
					sBuilder.append(":");
					sBuilder.append(port);
					sBuilder.append("/cgi-bin/hi3510/ptzctrl.cgi?-step=");
					sBuilder.append(URLEncoder.encode(String.valueOf(step)));
					sBuilder.append("&-act=");
					sBuilder.append(URLEncoder.encode(act));
					sBuilder.append("&-speed=");
					sBuilder.append(URLEncoder.encode(String.valueOf(speed)));
				}
				try {
					if(AppliactionUtil.DEBUG) System.out.println(sBuilder.toString());
					URL url = new URL(sBuilder.toString());
					Authenticator.setDefault(new Authenticator() {// Basic Authorization
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(username, password.toCharArray());
								}
							});
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// connection.connect();
					InputStream content = (InputStream) connection.getInputStream();
					BufferedReader in = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = in.readLine()) != null) {
						if(AppliactionUtil.DEBUG) System.out.println(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * 根据RTSP RTP数据包头中的字段读取音视频数据的长度
	 * 
	 * @param paramArrayOfByte
	 *            数据包头
	 * @return 音视频帧的长度
	 */
	private int getFrameDateLenH264(byte[] paramArrayOfByte) {
		byte[] arrayOfByte = new byte[4];
		arrayOfByte[3] = paramArrayOfByte[4];
		arrayOfByte[2] = paramArrayOfByte[5];
		arrayOfByte[1] = paramArrayOfByte[6];
		arrayOfByte[0] = paramArrayOfByte[7];
		int j = byte2intH264(arrayOfByte);
		int i = j - 12;
		return i;
	}

	/**
	 * 根据RTSP RTP数据包头中的字段读取音视频数据的时间戳，单位：毫秒。用于组帧
	 * 
	 * @param paramArrayOfByte
	 *            数据包头
	 * @return 时间戳ts
	 */
	private int getFrameDataTimeH264(byte[] paramArrayOfByte) {
		byte[] arrayOfByte = new byte[4];
		arrayOfByte[3] = paramArrayOfByte[12];
		arrayOfByte[2] = paramArrayOfByte[13];
		arrayOfByte[1] = paramArrayOfByte[14];
		arrayOfByte[0] = paramArrayOfByte[15];
		int i = byte2intH264(arrayOfByte);
		return i;
	}

	/**
	 * 根据RTSP RTP数据包头中的字段读取Sequence No.
	 * @param paramArrayOfByte
	 *            数据包头
	 * @return 序号sequence no.
	 */
	private short getFrameDataSeqnoH264(byte[] paramArrayOfByte) {
		byte byteH = paramArrayOfByte[10];
		byte byteL = paramArrayOfByte[11];
		short i = (short) ((byteH << 8) | byteL);
		return i;
	}

	/**
	 * 从Socket中读取前20字节数据，即RTSP数据包头+RTP数据包头
	 * @param paramArrayOfByte
	 *            存储数据包头的引用
	 * @return 实际读取到的字节数
	 */
	private int GetheadH264(byte[] paramArrayOfByte) {
		int j = recvDataH264(paramArrayOfByte, 20);
		return j;
	}

	/**
	 * 根据传入的len长度，从Socket中读取音视频帧数据
	 * 
	 * @param paramArrayOfByte1
	 *            存储音视频帧的引用
	 * @param len
	 *            音视频帧的长度
	 * @return 实际读取到的字节数
	 */
	private int GetFrameDataH264(byte[] paramArrayOfByte1, int len) {
		int j = recvDataH264(paramArrayOfByte1, len);
		return j;
	}

	/**
	 * 供GetheadH264和GetFrameDataH264使用，从Socket中读取指定字节数的数据
	 * 
	 * @param paramArrayOfByte
	 *            存储读取的数据的
	 * @param paramInt
	 *            指定长度
	 * @return 实际读取到的字节数
	 */
	private int recvDataH264(byte[] paramArrayOfByte, int paramInt) {
		int i;
		boolean isLoop = true;
		if (paramInt > 40960 || paramInt < 0) {
			paramInt = 40960;
			isLoop = false;
		}
		int j = 0;
		do {
			try {
				int k = dinVideoStream.read(paramArrayOfByte, j, paramInt - j);
				j += k;
			} catch (Exception e) {
				e.printStackTrace();
				i = -1;
				break;
			}
			i = j;
			if (!isLoop)
				break;
		} while (j < paramInt);
		return i;
	}

	/**
	 * 将source数组中的数据，以指定的start和length复制一份出来
	 * 
	 * @param source
	 *            需要复制的数组
	 * @param start
	 *            起始位置
	 * @param length
	 *            长度
	 * @return 新的复制后的数组
	 */
	private static byte[] copyOf(byte[] source, int start, int length) {
		if (length < 0 || start < 0)
			return null;
		int end = start + length;
		if (end > source.length)
			return null;
		byte[] out = new byte[length];
		for (int i = 0, j = start; j < end; i++, j++) {
			out[i] = source[j];
		}
		return out;
	}

	/**
	 * 将4字节byte数组转换为int类型的数字
	 * 
	 * @param paramArrayOfByte
	 * @return
	 */
	private static int byte2intH264(byte[] paramArrayOfByte) {
		return 0xFF & paramArrayOfByte[0] | 0xFF00 & paramArrayOfByte[1] << 8 | paramArrayOfByte[2] << 24 >>> 8
				| paramArrayOfByte[3] << 24;
	}

	/**
	 * 将4字节byte数组转换为int类型的数字
	 * 
	 * @param b
	 * @param offset
	 * @return
	 */
	private static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}
	private void log(String s) {
		if(AppliactionUtil.DEBUG) System.out.println(s);
	}
	private void log(String tag, String s) {
		log(tag + " : " + s);
	}
	static {
		System.loadLibrary("yjipcamera");
	}
	public native int InitDecoder(int width, int height);
	public native int UninitDecoder();
	public native int DecoderNal(byte[] in, int insize, byte[] out);
	public boolean thistestc = true;
	
	@Override
	public void startCapture() {
		// TODO Auto-generated method stub
		TYPE_NUMBER reint;
		if (cameraControl.getResolution().equals(CameraControl.MIDDLE_RESOLUTION)) {// 中分辨率
			reint = TYPE_NUMBER.FIRST_MINOR;
		} else {
			reint = TYPE_NUMBER.FIRST_MAIN;
		}
		// 服务器登录和网关登录
		if(CommunicationUtil.LOGIN_TYPE == CommunicationUtil.SERVER_LOGIN)
			cameraControl.setIpAddress(CommunicationUtil.getInstance().getRempteip().toString());
		this.init(cameraControl.getIpAddress(), Integer.valueOf(cameraControl.getPort()),
					cameraControl.getUsername(), cameraControl.getPassword(), reint,cameraControl.getVideoFlip());
		this.play();
		this.controlFlip();
	}

	@Override
	public void stopCapture() {
		stop();
	}

	@Override
	public void goUp() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "up", 45);
	}

	@Override
	public void goDown() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "down", 45);
	}

	@Override
	public void goLeft() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "left", 45);
	}

	@Override
	public void goRight() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "right", 45);
	}
	
	@Override
	public void goUpDown() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "vscan", 45);
	}

	@Override
	public void goLeftRight() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "hscan", 45);
	}

	@Override
	public void goPause() {
		// TODO Auto-generated method stub
		sendControlCommand(1, "stop", 45);
	}

	@Override
	public void flip() {
		// TODO Auto-generated method stub
		
	}
}
