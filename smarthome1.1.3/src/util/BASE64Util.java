package util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64加密解密工具类
 * 
 * @author yinger
 */
public class BASE64Util {

	/**
	 * BASE64解密
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static void main(String[] args) throws Exception {
		String data = BASE64Util.encryptBASE64("123456".getBytes());
		if(AppliactionUtil.DEBUG) System.out.println("加密前：" + data);

		byte[] byteArray = BASE64Util.decryptBASE64(data);
		if(AppliactionUtil.DEBUG) System.out.println("解密后：" + new String(byteArray));
	}
}
