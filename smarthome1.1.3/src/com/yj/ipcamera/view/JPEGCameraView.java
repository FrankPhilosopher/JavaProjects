package com.yj.ipcamera.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.AppliactionUtil;

import communication.CommunicationUtil;

import beans.CameraControl;

import cameraUtil.CameraButton;
import cameraUtil.HttpRequest;
import cameraUtil.StreamSplit;
/**
 * JPEG camera view
 * @author wuxuehong
 *
 * @date 2012-8-23
 */
public class JPEGCameraView extends JPanel implements ICamera{

	private BufferedImage image;
	private Image offScreenImage;
	private Image dfImage = java.awt.Toolkit.getDefaultToolkit().getImage(
			JPEGCameraView.class.getClassLoader().getResource("app/bg.gif"));
	//http����
	private HttpRequest httpRequest = new HttpRequest();
	//��Ƶ������Ϣ
	private CameraControl cameraControl;
	//ͼƬ����������
	private final int capacity = 20;
	//��Ƶ����״̬
	private boolean running = false;
	//��ǰ��Ƶ��ʾ��ͼƬ
	private BufferedImage curImage = null;
	//��Ƶ����url
	private String connectString = "";
	//ͼƬ������
	private LinkedList<BufferedImage> imageList1 = new LinkedList<BufferedImage>();
	//�׽�������
	private Socket socket;
	
	// commands
	private static final int DOWN = 0;
	private static final int UP = 2;
	private static final int RIGHT = 4;
	private static final int LEFT = 6;
	private static final int UPDOWN = 26;
	private static final int STOPUPDOWN = 27;
	private static final int LEFTRIGHT = 28;
	private static final int STOPLEFTRIGHT = 29;
		

	public JPEGCameraView(CameraControl comeraControl) {
		this.cameraControl = comeraControl;
	}

	@Override
	public void paint(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		else {
			g.drawImage(dfImage, 0, 0, this.getWidth(), this.getHeight(), null);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		}
	}

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(this.getWidth(), this.getHeight());
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, this.getWidth(), this.getHeight());
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		this.image = image;
		repaint();
	}
	
	public void setImage(String imagename){
		BufferedImage wait = null;
		try {
			wait = ImageIO.read(JPEGCameraView.class.getClassLoader().getResource(imagename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setImage(wait);
	}
	
	/**
	 * ���Ϳ�������
	 */
	private void sendCommand(final int cmd) {
		new Thread() {
			public void run() {
				try {
					// ��Ϊ���ص�¼��Զ�̵�¼
					String url;
					if(CommunicationUtil.LOGIN_TYPE == CommunicationUtil.SERVER_LOGIN){
						 cameraControl.setIpAddress(CommunicationUtil.getInstance().getRempteip().toString());
					 }
					url = "http://" + cameraControl.getIpAddress() + ":" + cameraControl.getPort()
							+ "/decoder_control.cgi?command=" + cmd + "&onestep=1&user="
							+ cameraControl.getUsername() + "&pwd=" + cameraControl.getPassword();
					httpRequest.doGet(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * ��ת���Ϊ50ʱ
	 * @param bid
	 */
	private void sendVideoFlip(final int bid){
		 new Thread() {
			public void run() {
				try {
					// ��Ϊ���ص�¼��Զ�̵�¼
					String url,command = null;
					if(bid == 51)  //��ת��ͬʱ����
						 command = "camera_control.cgi?param=5&value=3";
					 if(bid == 50)  //����ת
						 command = "camera_control.cgi?param=5&value=0";
					 
					 System.err.println(cameraControl.getIpAddress()+"**********@@@@@@@@@@@@@@");
					 if(CommunicationUtil.LOGIN_TYPE == CommunicationUtil.SERVER_LOGIN){
						 cameraControl.setIpAddress(CommunicationUtil.getInstance().getRempteip().toString());
					 }
					 if(AppliactionUtil.DEBUG) System.out.println(cameraControl.getIpAddress()+"******2");
					url = "http://" + cameraControl.getIpAddress() + ":" + cameraControl.getPort()
							+ "/"+command + "&user="
							+ cameraControl.getUsername() + "&pwd=" + cameraControl.getPassword();
					if(AppliactionUtil.DEBUG) System.out.println(url.toString());
					httpRequest.doGet(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}.start();
	}
	
	/**
	 * ��Ƶ���ӻ�ȡ�߳�
	 * @author wuxuehong
	 *
	 * @date 2012-8-24
	 */
	class ConnectThread extends Thread {
		private String urlString;
		public ConnectThread(String urlString) {
			this.urlString = urlString;
		}
		@Override
		public void run() {
			try {
				// ����socket����
				URL url = new URL(urlString);
				if(AppliactionUtil.DEBUG) System.out.println(urlString);
				if(AppliactionUtil.DEBUG) System.out.println("url.getHost(), url.getPort()" + url.getHost() + ":" + url.getPort());
				socket = new Socket(url.getHost(), url.getPort());
				OutputStream os = socket.getOutputStream();
				InputStream is = socket.getInputStream();
				// ��װ���ͳ�ȥ��socket���ݰ�
				StringBuffer request = new StringBuffer();
				request.append("GET " + url.getFile() + " HTTP/1.0\r\n");
				request.append("Host: " + url.getHost() + "\r\n");
				request.append("\r\n");
				os.write(request.toString().getBytes(), 0, request.length());
				// ʹ���ض�����������������
				StreamSplit localStreamSplit = new StreamSplit(new DataInputStream(new BufferedInputStream(is)));
				Hashtable localHashtable = localStreamSplit.readHeaders();
				String str3 = (String) localHashtable.get("content-type");
				int n = str3.indexOf("boundary=");
				Object localObject2 = "--";
				if (n != -1) {
					localObject2 = str3.substring(n + 9);
					str3 = str3.substring(0, n);
					if (!((String) localObject2).startsWith("--"))
						localObject2 = "--" + (String) localObject2;
				}
				if (str3.startsWith("multipart/x-mixed-replace")) {
					localStreamSplit.skipToBoundary((String) localObject2);
				}
				// do-while ѭ����һֱ��ȡͼƬ
				while (running) {
					if (localObject2 != null) {
						localHashtable = localStreamSplit.readHeaders();
						if (localStreamSplit.isAtStreamEnd())
							break;
						str3 = (String) localHashtable.get("content-type");
						if (str3 == null)
							throw new Exception("No part content type");
					}
					if (str3.startsWith("multipart/x-mixed-replace")) {
						n = str3.indexOf("boundary=");
						localObject2 = str3.substring(n + 9);
						localStreamSplit.skipToBoundary((String) localObject2);
					} else {
						byte[] localObject3 = localStreamSplit.readToBoundary((String) localObject2);
						if (localObject3.length == 0)break;
						// ��ȡ�õ�image��������
						InputStream inputStream = new ByteArrayInputStream(localObject3);
						synchronized (imageList1) {
							if(AppliactionUtil.DEBUG) System.out.println("wait........."+imageList1.size());
							if (imageList1.size() < capacity)
								imageList1.add(ImageIO.read(inputStream));
							else
								ImageIO.read(inputStream);
							imageList1.notifyAll();
						}
					}
				}
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (Exception e) {
				if(AppliactionUtil.DEBUG) System.out.println("��Ƶ���ӶϿ�!");
				setImage("app/error.jpg");
			}finally{
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
					socket = null;
				}
			}
		}
	}
	/**
	 * ��Ƶ��ʾ�߳�
	 * ��ˢͼƬ��ʽ
	 */
	class ImageThread extends Thread {
		public void run() {
			abc:
			while (running) {
				synchronized (imageList1) {
					while (imageList1.isEmpty()) {
						try {
							if(!running) break abc;
							if(AppliactionUtil.DEBUG) System.out.println("wait.........");
							imageList1.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					curImage = imageList1.removeFirst();
					setImage(curImage);
				}
			}
			if(AppliactionUtil.DEBUG) System.out.println("Image Thread ........stop!!!!!!!!!!!");
		}
	}
	
	@Override
	public void startCapture() {
		// TODO Auto-generated method stub
		// ��������¼�����ص�¼
		if(cameraControl.getVideoFlip() == 0){ //50Ϊ����ת
			sendVideoFlip(50);
		}else if(cameraControl.getVideoFlip() == 1){ //51Ϊ��ת
			sendVideoFlip(51);
		}
		if(CommunicationUtil.LOGIN_TYPE == CommunicationUtil.SERVER_LOGIN)
			cameraControl.setIpAddress(CommunicationUtil.getInstance().getRempteip().toString());
			connectString = "http://" + cameraControl.getIpAddress() + ":" + cameraControl.getPort()
					+ "/videostream.cgi?user=" + cameraControl.getUsername() + "&pwd="
					+ cameraControl.getPassword() + "&resolution=" + cameraControl.getResolution() + "&rate=3";
		running = true;
		setImage("app/wait.jpg");
		new Thread(new ConnectThread(connectString)).start();
		new Thread(new ImageThread()).start();
	}

	@Override
	public void stopCapture() {
		running = false; // stop the while recycle
		synchronized (imageList1) {  //������Ƶˢ���߳� �����˳�
			imageList1.notifyAll();
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			socket = null;
		}
	}

	@Override
	public void goUp() {
		// TODO Auto-generated method stub
		//sendCommand(UP);
		if(cameraControl.getVideoFlip() == 0){ //50Ϊ����ת
			sendCommand(UP);
		}else if(cameraControl.getVideoFlip() == 1){ //51Ϊ��ת
			sendCommand(DOWN);
		}
	}

	@Override
	public void goDown() {
		// TODO Auto-generated method stub
		if(cameraControl.getVideoFlip() == 0){ //50Ϊ����ת
			sendCommand(DOWN);
		}else if(cameraControl.getVideoFlip() == 1){ //51Ϊ��ת
			sendCommand(UP);
		}
	}

	@Override
	public void goLeft() {
		// TODO Auto-generated method stub
		//sendCommand(LEFT);
		if(cameraControl.getVideoFlip() == 0){ //50Ϊ����ת
			sendCommand(LEFT);
		}else if(cameraControl.getVideoFlip() == 1){ //51Ϊ��ת
			sendCommand(RIGHT);
		}
	}

	@Override
	public void goRight() {
		// TODO Auto-generated method stub
		//sendCommand(RIGHT);
		if(cameraControl.getVideoFlip() == 0){ //50Ϊ����ת
			sendCommand(RIGHT);
		}else if(cameraControl.getVideoFlip() == 1){ //51Ϊ��ת
			sendCommand(LEFT);
		}
	}

	@Override
	public void goUpDown() {
		// TODO Auto-generated method stub
		sendCommand(UPDOWN);
	}

	@Override
	public void goLeftRight() {
		// TODO Auto-generated method stub
		sendCommand(LEFTRIGHT);
	}

	@Override
	public void goPause() {
		// TODO Auto-generated method stub
		sendCommand(STOPLEFTRIGHT);
		sendCommand(STOPUPDOWN);
	}

	@Override
	public void flip() {
		// TODO Auto-generated method stub
		
	}
	
}
