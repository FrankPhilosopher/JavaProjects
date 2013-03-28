package com.rcp.wxh.communicate;

/**
 * 与终端通信异常
 * 
 * @author wuxuehong 2011-11-25
 * 
 */
public class CommunicateException extends Exception {

	public CommunicateException() {
		super();
	}

	public CommunicateException(String msg) {
		super(msg);
	}

	public CommunicateException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommunicateException(Throwable cause) {
		super(cause);
	}

}
