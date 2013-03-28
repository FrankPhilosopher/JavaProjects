package com.yj.smarthome.interfaces;

/**
 * 处理服务器端返回的信息的接口<br/>
 * 所有对于返回信息要做出相应的反馈的组件都要实现这个接口
 * 
 * @author yinger
 * 
 */
public interface IResponse {

	void dealWithResponse(byte[] response);

}
