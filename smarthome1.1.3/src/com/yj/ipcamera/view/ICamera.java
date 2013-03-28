package com.yj.ipcamera.view;

/**
 * interface for camera
 * @author wuxuehong
 *
 * @date 2012-8-23
 */
public interface ICamera {
	
	/**
	 * 开始捕获视频
	 */
	public void startCapture();
	
	/**
	 * 停止视频捕获
	 */
	public void stopCapture();
	
	/**
	 * 向上巡航
	 */
	public void goUp();
	
	/**
	 * 向下巡航
	 */
	public void goDown();
	
	/**
	 * 向左巡航
	 */
	public void goLeft();
	
	/**
	 * 向右巡航
	 */
	public void goRight();
	
	/**
	 * 上下巡航
	 */
	public void goUpDown();
	
	/**
	 * 左右巡航
	 */
	public void goLeftRight();
	
	/**
	 * 停止巡航
	 */
	public void goPause();

	/**
	 * 视频翻转
	 */
	public void flip();
}
