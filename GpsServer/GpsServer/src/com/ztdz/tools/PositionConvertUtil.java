package com.ztdz.tools;

import java.util.List;

import com.ztdz.pojo.TPosition;
import com.ztdz.pojo.TTempPosition;

/**
 * ��λ��ƫ����
 * @author Administrator
 *
 */
public class PositionConvertUtil {
	
	private static String[] dir = {"����ƫ��","����ƫ��","����ƫ��","����ƫ��","����ƫ��","����ƫ��","����ƫ��","����ƫ��"};
	private static String[] udir = {"����","����","����","����","����","����","����","����"};
	/**
	 * ���ݶ�����ȡ������Ϣ
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
	 * ʵʱλ�þ�ƫ
	 * @param tpv
	 */
	public static TTempPosition convert(TTempPosition tpv){
		if(tpv==null) return null;
		if("0".equals(tpv.getLocationModel())){  //�����GPS��λ
			tpv.setLatitude(tpv.getLatitude()+tpv.getLatoffset());
			tpv.setLongitude(tpv.getLongitude()+tpv.getLngoffset());
		}else{ //��վ��λ
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
	 * ������ƫ
	 * @param tpvs
	 */
	public static void convertTemp(List<TTempPosition> tpvs){
		if(tpvs==null) return;
		for(int i=0;i<tpvs.size();i++)
			convert(tpvs.get(i));
	}
	
	/**
	 * ��ʷλ�þ�ƫ
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
	 * ��ʷ�켣  ������ƫ
	 * @param tpvs
	 */
	public static void convert(List<TPosition> tpvs){
		if(tpvs == null) return;
		for(int i=0;i<tpvs.size();i++)
			convert(tpvs.get(i));
	}
	
}
