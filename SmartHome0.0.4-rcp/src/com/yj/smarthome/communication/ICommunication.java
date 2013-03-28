package com.yj.smarthome.communication;

/**
 * 通信的接口
 * 
 * @author yinger
 * 
 */
public interface ICommunication {

	public boolean startConnection() throws Exception;

	public boolean closeConnection() throws Exception;

	public boolean restartConnection() throws Exception;

	public boolean sendData(byte[] data) throws Exception;

	public boolean checkLogin(byte[] data) throws Exception;

}
