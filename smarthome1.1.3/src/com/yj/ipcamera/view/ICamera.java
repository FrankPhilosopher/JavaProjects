package com.yj.ipcamera.view;

/**
 * interface for camera
 * @author wuxuehong
 *
 * @date 2012-8-23
 */
public interface ICamera {
	
	/**
	 * ��ʼ������Ƶ
	 */
	public void startCapture();
	
	/**
	 * ֹͣ��Ƶ����
	 */
	public void stopCapture();
	
	/**
	 * ����Ѳ��
	 */
	public void goUp();
	
	/**
	 * ����Ѳ��
	 */
	public void goDown();
	
	/**
	 * ����Ѳ��
	 */
	public void goLeft();
	
	/**
	 * ����Ѳ��
	 */
	public void goRight();
	
	/**
	 * ����Ѳ��
	 */
	public void goUpDown();
	
	/**
	 * ����Ѳ��
	 */
	public void goLeftRight();
	
	/**
	 * ֹͣѲ��
	 */
	public void goPause();

	/**
	 * ��Ƶ��ת
	 */
	public void flip();
}
