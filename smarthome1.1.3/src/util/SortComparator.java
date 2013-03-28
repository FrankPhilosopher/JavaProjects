package util;

import java.util.Comparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import beans.ApplianceControl;
import beans.CameraControl;
import beans.DoorControl;
import beans.LightControl;
import beans.SecurityControl;

public class SortComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		if (arg0 instanceof LightControl || arg1 instanceof LightControl) {
			return compare(((LightControl) arg0).getName(), ((LightControl) arg1).getName());
		} else if (arg0 instanceof DoorControl || arg1 instanceof DoorControl) {
			return compare(((DoorControl) arg0).getName(), ((DoorControl) arg1).getName());
		} else if (arg0 instanceof SecurityControl || arg1 instanceof SecurityControl) {
			return compare(((SecurityControl) arg0).getName(), ((SecurityControl) arg1).getName());
		} else if (arg0 instanceof ApplianceControl || arg1 instanceof ApplianceControl) {
			return compare(((ApplianceControl) arg0).getName(), ((ApplianceControl) arg1).getName());
		} else if (arg0 instanceof CameraControl || arg1 instanceof CameraControl) {
			return compare(((CameraControl) arg0).getName(), ((CameraControl) arg1).getName());
		} else {
			return 0;
		}
	}

	public int compare(String o1, String o2) {
		for (int i = 0; i < o1.length() && i < o2.length(); i++) {
			int codePoint1 = o1.charAt(i);
			int codePoint2 = o2.charAt(i);
			if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
				i++;
			}
			if (codePoint1 != codePoint2) {
				if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
					return codePoint1 - codePoint2;
				}
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);
				if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				} else {
					return codePoint1 - codePoint2;
				}
			}
		}
		return o1.length() - o2.length();
	}

	/**
	 * 字符的拼音，多音字就得到第一个拼音。不是汉字，就return null。
	 */
	private String pinyin(char c) {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
		if (pinyins == null) {
			return null;
		}
		return pinyins[0];
	}

}
