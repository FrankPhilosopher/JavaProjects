package com.ztdz.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class addr {

	/****************************** ����ת�� ***********************************/

	public static double lngToPixel(double lng, int zoom) {
		return (lng + 180) * (256L << zoom) / (double)360;
	}

	public static double pixelToLng(double pixelX, int zoom) {
		return pixelX * 360 / (double)((256L << zoom)) - 180;
	}

	/****************************** γ��ת�� ************************************/
	public static double latToPixel(double lat, int zoom) {
		double siny = Math.sin(lat * Math.PI / 180);
		double y = Math.log((1 + siny) / (1 - siny));
		return (128 << zoom) * (1 - y / (2 * Math.PI));
	}

	public static double pixelToLat(double pixelY, int zoom) {
		double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
		double z = Math.pow(Math.E, y);
		double siny = (z - 1) / (z + 1);
		return Math.asin(siny) * 180 / Math.PI;
	}

	public static String getAdress(double latitude,double longitude, int zoom){
//		double lat = pixelToLat(latToPixel(28.174154, 15), 15);
//		double lng = pixelToLng(lngToPixel(112.982285, 15), 15);
		double lat = pixelToLat(latToPixel(latitude, zoom), zoom);
		double lng = pixelToLng(lngToPixel(longitude, zoom), zoom);
		String addr = geocodeAddr(""+lat+"", ""+lng+"");// (38.9146943,121.612382);
		return addr;
	}
	
	public static void main(String args[]){
		System.out.println(getAdress(28.17415,112.98228, 15));
	}

	/**
	 * ���ݾ�γ�ȷ��������ַ����ʱ��Ҫ�ೢ�Լ���
	 * ע��:(ժ�ԣ�http://code.google.com/intl/zh-CN/apis/maps/faq.html
	 * �ύ�ĵ�ַ������������Ƿ������ƣ�) ����� 24 Сʱʱ�����յ�����һ�� IP ��ַ���� 15,000 ����ַ�������� ���һ�� IP
	 * ��ַ�ύ�ĵ�ַ�����������ʹ��죬Google ��ͼ API ���������� 620 ״̬���뿪ʼ��Ӧ�� �����ַ��������ʹ����Ȼ���࣬��Ӹ� IP
	 * ��ַ�� Google ��ͼ API ��ַ�������ķ��ʿ��ܱ�������ֹ��
	 * 
	 * @param latitude
	 *            γ��
	 * @param longitude
	 *            ����
	 * @return
	 */
	public static String geocodeAddr(String latitude, String longitude) {
		String addr = "";

		// Ҳ������http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s������������������Ӣ�ĵ�ַ
		// ��Կ�������дһ��key=abc
		// output=csv,Ҳ������xml��json������ʹ��csv���ص��������෽�����
		String url = String.format(
				"http://maps.google.cn/maps/geo?output=csv&key=bbccddee&q=%s,%s",
				latitude, longitude);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(httpsConn
						.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
//					System.out.println(data);
					String[] retList = data.split(",");
					if (retList.length > 2 && ("200".equals(retList[0]))) {
						addr = retList[2];
						addr = addr.replace("\"", "");
					} else {
						addr = "";
					}
				}
				insr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return addr;
	}
}