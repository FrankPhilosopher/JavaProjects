package com.ztdz.tools;

import java.util.List;

import com.ztdz.pojo.TPosition;
import com.ztdz.pojo.TTempPosition;

/**
 * 定位纠偏工具
 * @author Administrator
 *
 */
public class PositionConvertUtil {
	
	private static String[] dir = {"东北偏北","东北偏东","东南偏东","东南偏南","西南偏南","西南偏西","西北偏西","西北偏北"};
	private static String[] udir = {"正北","东北","正东","东南","正南","西南","正西","西北"};
	/**
	 * 根据度数获取方向信息
	 * @param degree
	 * @return
	 */
	public static String getDirection(int degree){
		if(degree % 45 ==0){
			return udir[degree/45];
		}else{
			return dir[degree/45];
		}
	}

	/**
	 * 实时位置纠偏
	 * @param tpv
	 */
	public static TTempPosition convert(TTempPosition tpv){
		if(tpv==null) return null;
		if("0".equals(tpv.getLocationModel())){  //如果是GPS定位
			tpv.setLatitude(tpv.getLatitude()+tpv.getLatoffset());
			tpv.setLongitude(tpv.getLongitude()+tpv.getLngoffset());
		}else{ //基站定位
			float[] latlng = GoogleLocation.getLocation(Integer.parseInt(tpv.getPlotId()),Integer.parseInt(tpv.getStationId()));
			tpv.setLatitude(latlng[0]);
			tpv.setLongitude(latlng[1]);
		}
		try{
			tpv.setDirection(getDirection(Integer.parseInt(tpv.getDirection())));
		}catch(Exception e){
			tpv.setDirection("");
		}
		return tpv;
	}
	
	/**
	 * 批量纠偏
	 * @param tpvs
	 */
	public static void convertTemp(List<TTempPosition> tpvs){
		if(tpvs==null) return;
		for(int i=0;i<tpvs.size();i++)
			convert(tpvs.get(i));
	}
	
	/**
	 * 历史位置纠偏
	 * @param tpv
	 */
	public static void convert(TPosition tpv){
		if(tpv==null) return;
		tpv.setLatitude(tpv.getLatitude()+tpv.getLatoffset());
		tpv.setLongitude(tpv.getLongitude()+tpv.getLngoffset());
		try{
			tpv.setDirection(getDirection(Integer.parseInt(tpv.getDirection())));
		}catch(Exception e){
			tpv.setDirection("");
		}
	}
	/**
	 * 历史轨迹  批量纠偏
	 * @param tpvs
	 */
	public static void convert(List<TPosition> tpvs){
		if(tpvs == null) return;
		for(int i=0;i<tpvs.size();i++)
			convert(tpvs.get(i));
	}
	
}
