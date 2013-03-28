package com.yj.smarthome.interfaces;

import java.util.Map;

/**
 * 这个接口是表明编辑器上有一组map在里面，实现这个接口就可以通用了！
 * 
 * @author yinger
 * 
 */
public interface IDeviceMap {

	Map getDeviceMap();

	void setDeviceMap(Map map);

}
