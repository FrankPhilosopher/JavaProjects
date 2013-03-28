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
	 * �ڳ�ʼ����Ƶ�豸ʱ��ָ��ͨ�������� <br/>
	 * ע�⣺Ŀǰ�豸ֻ�е�һ��ͨ��
	 */
	public enum TYPE_NUMBER {
		FIRST_MAIN(11), // ��һͨ��������
		FIRST_MINOR(12), // ��һͨ��������
		SECOND_MAIN(21), // �ڶ�ͨ��������
		SECOND_MINOR(22);// �ڶ�ͨ��������
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

	private byte[] framehead = new byte[20];// ��������ͷ
	private byte[] NalBuf = new byte[409600];// �������֡����
	private byte[] SockBuf = new byte[409600];// ����������Ͻ��յ�����

	private byte[] SecondBuf = new byte[409600];// ������֡
	private int preTs = -1;// ǰһ֡��ʱ���
	private int preNaluType = -1;// ǰһnalu��type
	private int preNaluLen = 0;// ǰһnalu�ĳ���

	/**
	 * 0 init value<br/>
	 * 97 G726<br/>
	 * 8 G711a
	 */
	private int audioType;
	private boolean authorized;// �û����������Ƿ�ͨ��У��

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
	 * ��ʼ����Ƶ�豸���������������ӣ���Ҫ����play()������ŻῪʼ��������
	 * 
	 * @param ip
	 *            ��Ƶ�豸��IP��ַ
	 * @param port
	 *            �˿�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @param type_number
	 *            ͨ������������
	 * @param handler
	 *            ���ڽ��պʹ������/�쳣��Ϣ
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
	 * ��ʼ���Ӳ�����
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
	 * �ָ�pause֮ǰ�Ĳ���״̬����pause()������Activity��onResume��onPause�����ʹ��
	 */
	private void resume() {
		log(TAG, "resume() ++ status = " + status);
		if (pre_status != null)
			status = pre_status;
		log(TAG, "resume() -- status = " + status);
	}

	/**
	 * ����ǰ״̬��Ϊ��ͣ ����resume()������Activity��onResume��onPause�����ʹ��
	 */
	private void pause() {
		log(TAG, "pause() ++ status = " + status);
		pre_status = status;
		status = STATUS.PAUSE;
		log(TAG, "pause() -- status = " + status);
	}

	/**
	 * ֹͣ����
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
	 * ��ʼ���Ӳ����벥��
	 */
	private void startPlay() {
		initConnection();
	}

	/**
	 * @return ��ǰ״̬�Ƿ���STATUS.PLAY
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
					// �����ӽ����������Ƚ���Server���ص�HTTP/1.1 200 OK��Ϣ��ȷ�����ӳɹ�
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
							// ������ǰ���յ�����Ϣ
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
	 * ���豸��������
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
	 * �������ӽ������豸�ش�����һ����Ϣ��ȷ���Ƿ���HTTP/1.1 200 OK���Դ������ӽ����ɹ� <br>
	 * �ڴ���Ϣ�У������width��height��ֵ�����ڴ˴����г�ʼ��
	 * 
	 * @param response
	 *            ���յ��ĵ�һ����Ϣ
	 * @return ��Ϣ�Ƿ���HTTP/1.1 200 OK��ͷ
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
	 * �Ƿ�ת
	 */
	public void controlFlip(){
		if(AppliactionUtil.DEBUG) System.out.println("flip is:"+videoflip);
		if(videoflip ==0)
			sendControlCommand(0,"noflip",0);
		else if(videoflip == 1)
			sendControlCommand(0,"flip",0);
	}
	
	
	
	/**
	 * ������̨��������(GET)
	 * 
	 * @param step
	 *            <li>0������ִ�к����ٴη�ֹͣ�������ֹͣ <li>1������ִ�к��Զ�ֹͣ
	 * @param act
	 *            �������� <br>
	 *            ���������ַ������磺 <li>left������ <li>right������ <li>up������ <li>down������ <li>
	 *            home���ص����ĵ� <li>zoomin������ <li>zoomout����Զ <li>hscan��ˮƽѲ�� <li>
	 *            vscan����ֱѲ�� <li>stop��ֹͣ <li>focusin���۽��� <li>focusout���۽�Զ <li>
	 *            aperturein����ȦС <li>apertureout����Ȧ�� <li>auto���Զ�Ѳ�� <li>brush����ˢ <li>light���ƹ�
	 * @param speed
	 *            ��̨�ٶȣ�ֵ�ķ�ΧΪ1~63��
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
	 * ����RTSP RTP���ݰ�ͷ�е��ֶζ�ȡ����Ƶ���ݵĳ���
	 * 
	 * @param paramArrayOfByte
	 *            ���ݰ�ͷ
	 * @return ����Ƶ֡�ĳ���
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
	 * ����RTSP RTP���ݰ�ͷ�е��ֶζ�ȡ����Ƶ���ݵ�ʱ�������λ�����롣������֡
	 * 
	 * @param paramArrayOfByte
	 *            ���ݰ�ͷ
	 * @return ʱ���ts
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
	 * ����RTSP RTP���ݰ�ͷ�е��ֶζ�ȡSequence No.
	 * @param paramArrayOfByte
	 *            ���ݰ�ͷ
	 * @return ���sequence no.
	 */
	private short getFrameDataSeqnoH264(byte[] paramArrayOfByte) {
		byte byteH = paramArrayOfByte[10];
		byte byteL = paramArrayOfByte[11];
		short i = (short) ((byteH << 8) | byteL);
		return i;
	}

	/**
	 * ��Socket�ж�ȡǰ20�ֽ����ݣ���RTSP���ݰ�ͷ+RTP���ݰ�ͷ
	 * @param paramArrayOfByte
	 *            �洢���ݰ�ͷ������
	 * @return ʵ�ʶ�ȡ�����ֽ���
	 */
	private int GetheadH264(byte[] paramArrayOfByte) {
		int j = recvDataH264(paramArrayOfByte, 20);
		return j;
	}

	/**
	 * ���ݴ����len���ȣ���Socket�ж�ȡ����Ƶ֡����
	 * 
	 * @param paramArrayOfByte1
	 *            �洢����Ƶ֡������
	 * @param len
	 *            ����Ƶ֡�ĳ���
	 * @return ʵ�ʶ�ȡ�����ֽ���
	 */
	private int GetFrameDataH264(byte[] paramArrayOfByte1, int len) {
		int j = recvDataH264(paramArrayOfByte1, len);
		return j;
	}

	/**
	 * ��GetheadH264��GetFrameDataH264ʹ�ã���Socket�ж�ȡָ���ֽ���������
	 * 
	 * @param paramArrayOfByte
	 *            �洢��ȡ�����ݵ�
	 * @param paramInt
	 *            ָ������
	 * @return ʵ�ʶ�ȡ�����ֽ���
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
	 * ��source�����е����ݣ���ָ����start��length����һ�ݳ���
	 * 
	 * @param source
	 *            ��Ҫ���Ƶ�����
	 * @param start
	 *            ��ʼλ��
	 * @param length
	 *            ����
	 * @return �µĸ��ƺ������
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
	 * ��4�ֽ�byte����ת��Ϊint���͵�����
	 * 
	 * @param paramArrayOfByte
	 * @return
	 */
	private static int byte2intH264(byte[] paramArrayOfByte) {
		return 0xFF & paramArrayOfByte[0] | 0xFF00 & paramArrayOfByte[1] << 8 | paramArrayOfByte[2] << 24 >>> 8
				| paramArrayOfByte[3] << 24;
	}

	/**
	 * ��4�ֽ�byte����ת��Ϊint���͵�����
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
		if (cameraControl.getResolution().equals(CameraControl.MIDDLE_RESOLUTION)) {// �зֱ���
			reint = TYPE_NUMBER.FIRST_MINOR;
		} else {
			reint = TYPE_NUMBER.FIRST_MAIN;
		}
		// ��������¼�����ص�¼
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
