package com.yj.smarthome.interfaces;

/**
 * 设备状态接口
 * 
 * @author yinger
 * 
 */
public interface IStateable {

	public int getId();

	public int getState();

	public void setState(int state);

}
