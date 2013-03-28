package cn.edu.csu.dbhospital.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/**
	 * ����MD5����
	 * 
	 * @param info
	 *            Ҫ���ܵ���Ϣ
	 * @return String ���ܺ���ַ���
	 */
	public static String encrypt(String info) {
		byte[] digesta = null;
		try {
			// �õ�һ��md5����ϢժҪ
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// ���Ҫ���м���ժҪ����Ϣ
			alga.update(info.getBytes());
			// �õ���ժҪ
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// ��ժҪתΪ�ַ���
		String rs = byte2hex(digesta);
		return rs;
	}
	
	public String encryptToMD52(String info) {
		byte[] digesta = null;
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(info.getBytes());
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String rs = digesta.toString();
		return rs;
	}
	/**
	 * ��������ת��Ϊ16�����ַ���
	 * 
	 * @param b
	 *            �������ֽ�����
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	
	public static void main(String[] args) {
		MD5Util jiami = new MD5Util();
		// ִ��MD5����"Hello world!"
		System.out.println("Hello����MD5:" + jiami.encrypt("12341234"));
		System.out.println("Hello����MD51:" + jiami.encryptToMD52("12341234"));
	}
}