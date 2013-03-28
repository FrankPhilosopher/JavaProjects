package com.yj.ipcamera.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import beans.CameraControl;

import cameraUtil.CameraButton;
import cameraUtil.CameraPanel;

import com.yj.ipcamera.view.IPCameraView;

import communication.CommunicationUtil;

public class Camera extends JFrame {

	private final CameraPanel contentPanel = new CameraPanel("");
	private static ICamera ipcameraview;
	public  static Camera camera;
	
	public  static Camera getInstance(CameraControl cameraControl) {
		if(camera != null){
			if(ipcameraview != null)ipcameraview.stopCapture();
			camera.dispose();
			camera = null;
			ipcameraview = null;
		}
		if(cameraControl.getType() == CameraControl.H264)
			ipcameraview = new IPCameraView(cameraControl);
		else if(cameraControl.getType() == CameraControl.JPEG)
			ipcameraview = new JPEGCameraView(cameraControl);
		camera = new Camera();
		return camera;
	}
	
	public void startCapture(){
		if(ipcameraview != null) ipcameraview.startCapture();
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CameraControl cameraControl = new CameraControl();
			cameraControl.setIpAddress("192.168.1.190");
			cameraControl.setPort("8090");
			cameraControl.setUsername("admin");
			cameraControl.setPassword("123456");
			cameraControl.setName("ipcamera");
			cameraControl.setResolution("8");
			cameraControl.setType(1);
			ipcameraview = new IPCameraView(cameraControl);
			Camera dialog = new Camera();
			dialog.startCapture();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	private Camera() {
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.addWindowListener(new WindowAdapter() {
			//关闭视频窗口
			public void windowClosing(WindowEvent e) {   
				
			}
		});
		//视频窗口拖动
		this.addMouseMotionListener(new MouseMotionListener() {
			int x1, y1;
			public void mouseMoved(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();
			}
			public void mouseDragged(MouseEvent e) {
				int xoff = e.getX() - x1;
				int yoff = e.getY() - y1;
				int fx = Camera.this.getLocation().x;
				int fy = Camera.this.getLocation().y;
				Camera.this.setLocation(fx + xoff, fy + yoff);
			}
		});

		//隐藏窗口边框
		this.setUndecorated(true);
		// 总的大小
		setBounds(100, 100, 550, 555);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		CameraButton button = new CameraButton("app/godown.png");
		button.setText("Down");
		button.setBounds(96, 502, 48, 48);
		button.addActionListener(new ActionListener() {
			//云台往下
			public void actionPerformed(ActionEvent arg0) { // down
				ipcameraview.goDown();
			}
		});
		contentPanel.add(button);
		CameraButton button_1 = new CameraButton("app/goup.png");
		button_1.setBounds(96, 442, 48, 48);
		button_1.addActionListener(new ActionListener() {
			//云台往上
			public void actionPerformed(ActionEvent e) { // up
				ipcameraview.goUp();
			}
		});
		contentPanel.add(button_1);
		CameraButton button_2 = new CameraButton("app/goleft.png");
		button_2.setBounds(34, 478, 48, 48);
		button_2.addActionListener(new ActionListener() {
			//云台往左
			public void actionPerformed(ActionEvent e) { // left
				ipcameraview.goLeft();
			}
		});
		contentPanel.add(button_2);
		CameraButton button_3 = new CameraButton("app/goright.png");
		button_3.setBounds(158, 478, 48, 48);
		button_3.addActionListener(new ActionListener() {
			//云台往右
			public void actionPerformed(ActionEvent e) { // right
				ipcameraview.goRight();
			}
		});
		contentPanel.add(button_3);
		JSeparator separator = new JSeparator();
		separator.setBounds(8, 432, 534, 2);
		contentPanel.add(separator);
		CameraButton btnNewButton = new CameraButton("app/updown.png");
		btnNewButton.addActionListener(new ActionListener() {
			//云台上下巡航
			public void actionPerformed(ActionEvent e) {
				ipcameraview.goUpDown();
			}
		});
		btnNewButton.setBounds(228, 478, 48, 48);
		contentPanel.add(btnNewButton);
		CameraButton btnNewButton_1 = new CameraButton("app/leftright.png");
		btnNewButton_1.addActionListener(new ActionListener() {
			//云台左右巡航
			public void actionPerformed(ActionEvent e) { // left right
				ipcameraview.goLeftRight();
			}
		});
		btnNewButton_1.setBounds(298, 478, 48, 48);
		contentPanel.add(btnNewButton_1);
		CameraButton button_6 = new CameraButton("app/gostop.png");
		button_6.setBounds(364, 478, 48, 48);
		button_6.addActionListener(new ActionListener() {
			//云台停止巡航
			public void actionPerformed(ActionEvent e) { // stop
				ipcameraview.goPause();
			}
		});
		contentPanel.add(button_6);
		CameraButton btnNewButton_2 = new CameraButton("app/close.png");
		btnNewButton_2.setBounds(460, 478, 50, 50);
		contentPanel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			//关闭视频窗口
			public void actionPerformed(ActionEvent e) {
				ipcameraview.stopCapture();
				Camera.this.dispose();
			}
		});
		
		if(ipcameraview != null){
			JPanel cam = (JPanel)ipcameraview;
			cam.setLayout(null);
			cam.setBounds(30, 30, 490, 390);
			contentPanel.add(cam);
			//注意这块！！ 设置布局为null 自由布局
			//为画布添加鼠标事件 可以拖动
			cam.addMouseMotionListener(new MouseMotionListener() {
				int x1, y1;
				public void mouseMoved(MouseEvent e) {
					x1 = e.getX();
					y1 = e.getY();
				}
				public void mouseDragged(MouseEvent e) {
					int xoff = e.getX() - x1;
					int yoff = e.getY() - y1;
					int fx = Camera.this.getLocation().x;
					int fy = Camera.this.getLocation().y;
					Camera.this.setLocation(fx + xoff, fy + yoff);
				}
			});
		}
	}
}
