package com.ztdz.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 基站定位工具
 * @author wuxuehong
 *
 * 2012-7-2
 */
public class GoogleLocation {
	
	public static void main(String args[]){
		float[] latlng = getLocation(39221, 58256);
		System.out.println(latlng[0]);
		System.out.println(latlng[1]);
	}
	
	/**
	 * 
	 * @param cid   基站编号
	 * @param lac   小区编号
	 * @return   [0]-latitude  [1]-longitude
	 */
	public static float[] getLocation(int cid, int lac){
		float latlng[] = new float[2];
		int mcc = 460;
		int mnc = 0;
		JSONObject holder = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		holder.put("version", "1.1.0");
		holder.put("host", "maps.google.com");
		holder.put("address_language", "zh_CN");
		holder.put("request_address", true);
		holder.put("radio_type", "gsm");
		holder.put("carrier", "HTC");
		data.put("cell_id", cid);
		data.put("location_area_code", lac);
		data.put("mobile_countyr_code", mcc);
		data.put("mobile_network_code", mnc);
		array.add(data);
		holder.put("cell_towers", array);
//		System.out.println(holder.toString());
		
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL("http://www.google.com/loc/json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			conn.getOutputStream().write(holder.toString().getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			int code = conn.getResponseCode();
//			System.out.println("code   " + code);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn
					.getInputStream(),"utf-8"));
			String inputLine;
			inputLine = in.readLine();
			in.close();
			// 解析结果
			System.out.println(inputLine);
			JSONObject result = JSONObject.fromObject(inputLine);
			JSONObject location = result.getJSONObject("location");
			JSONObject address = location.getJSONObject("address");

//			System.out.println("city = " + address.getString("city"));
//			System.out.println("region = " + address.getString("region"));
//			System.out.println("latitude = "+location.getString("latitude"));
//			System.out.println("longitude = "+location.getString("longitude"));
			latlng[0] = Float.parseFloat(location.getString("latitude"));
			latlng[1] = Float.parseFloat(location.getString("longitude"));
		} catch (Exception e) {
			latlng[0] = -1;
			latlng[1] = -1;
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return latlng;
	}

}
