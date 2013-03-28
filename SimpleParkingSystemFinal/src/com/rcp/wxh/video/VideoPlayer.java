package com.rcp.wxh.video;

import java.util.List;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

/**
 * 初始化视频信息 USB摄像头
 * 
 * @author wuxuehong 2011-11-20
 * 
 */

//TODO：yinger   这个类没有用了！
public class VideoPlayer {

	private CaptureDeviceInfo captureDeviceInfo = null; // 该类对象包含了捕获装置的细节信息
	private MediaLocator mediaLocator = null;
	private Player player = null;

	// 获取Player对象方法
	public Player Getplay() throws Exception {
		String str = "vfw:Microsoft WDM Image Capture (Win32):0";
		try {
			captureDeviceInfo = CaptureDeviceManager.getDevice(str); // 获取装备信息
			System.out.println(captureDeviceInfo);
			mediaLocator = captureDeviceInfo.getLocator();
			player = Manager.createRealizedPlayer(mediaLocator);
		} catch (Exception e) {
			throw e;
		}
		return player;
	}

	// 停止Player方法
	public void shearPlay(Player player) {
		this.player = player;
		player.close();
	}

	public static void main(String args[]) throws Exception {

		List list = CaptureDeviceManager.getDeviceList(null);
		System.out.println(list.size());

//   		Vector<CaptureDeviceInfo> list = CaptureDeviceManager.getDeviceList(null);
//   		for(int i=2;i<list.size();i++){
//   			MediaLocator lo = list.get(i).getLocator();
//   			Player p = Manager.createRealizedPlayer(lo);
//   			p.close();
//   			DataSource ds = Manager.createDataSource(lo);
//   			System.out.println(lo+"\t\t\t"+lo.getProtocol()+"\t"+lo.getRemainder()+"\t\t"+ds);
//   			
//   		}
		System.out.println(list.size());

	}

}
