package com.rcp.wxh.video;

import java.util.List;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

/**
 * ��ʼ����Ƶ��Ϣ USB����ͷ
 * 
 * @author wuxuehong 2011-11-20
 * 
 */

//TODO��yinger   �����û�����ˣ�
public class VideoPlayer {

	private CaptureDeviceInfo captureDeviceInfo = null; // �����������˲���װ�õ�ϸ����Ϣ
	private MediaLocator mediaLocator = null;
	private Player player = null;

	// ��ȡPlayer���󷽷�
	public Player Getplay() throws Exception {
		String str = "vfw:Microsoft WDM Image Capture (Win32):0";
		try {
			captureDeviceInfo = CaptureDeviceManager.getDevice(str); // ��ȡװ����Ϣ
			System.out.println(captureDeviceInfo);
			mediaLocator = captureDeviceInfo.getLocator();
			player = Manager.createRealizedPlayer(mediaLocator);
		} catch (Exception e) {
			throw e;
		}
		return player;
	}

	// ֹͣPlayer����
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
