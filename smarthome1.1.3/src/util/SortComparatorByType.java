package util;

import java.util.Comparator;

import beans.DoorControl;
import beans.LightControl;

public class SortComparatorByType implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		if (arg0 instanceof LightControl || arg1 instanceof LightControl) {
			return compare(((LightControl) arg1).getType(), ((LightControl) arg0).getType());
		} else if (arg0 instanceof DoorControl || arg1 instanceof DoorControl) {
			return compare(((DoorControl) arg1).getType(), ((DoorControl) arg0).getType());
		}
		// else if (arg0 instanceof SecurityControl || arg1 instanceof SecurityControl) {
		// return compare(((SecurityControl) arg0).getName(), ((SecurityControl) arg1).getName());
		// } else if (arg0 instanceof ApplianceControl || arg1 instanceof ApplianceControl) {
		// return compare(((ApplianceControl) arg0).getName(), ((ApplianceControl) arg1).getName());
		// } else if (arg0 instanceof CameraControl || arg1 instanceof CameraControl) {
		// return compare(((CameraControl) arg0).getName(), ((CameraControl) arg1).getName());
		// }
		else {
			return 1;
		}
	}

	public int compare(String o1, String o2) {
		String s1 = (String) o1;
		String s2 = (String) o2;
		int len1 = s1.length();
		int len2 = s2.length();
		int n = Math.min(len1, len2);
		char v1[] = s1.toCharArray();
		char v2[] = s2.toCharArray();
		int pos = 0;
		while (n-- != 0) {
			char c1 = v1[pos];
			char c2 = v2[pos];
			if (c1 != c2) {
				return -(c1 - c2);
			}
			pos++;
		}
		return -(len1 - len2);
	}

	public int compare(Integer o1, Integer o2) {
		int val1 = o1.intValue();
		int val2 = o2.intValue();
		return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
	}

	public int compare(Boolean o1, Boolean o2) {
		return (o1.equals(o2) ? 0 : (o1.booleanValue() == true ? 1 : -1));
	}

}
