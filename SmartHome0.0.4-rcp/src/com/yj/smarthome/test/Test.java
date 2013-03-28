package com.yj.smarthome.test;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
//		List<String> list = new ArrayList<String>();
//		list.add("hello");
//		list.add("world");
//		System.out.println(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//			if (i == 1) {
//				list.add("hahh");
//			}
//		}

		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int[] b = new int[4];
		System.arraycopy(a, 3, b, 0, b.length);
		System.out.println(Arrays.toString(b));
	}

}
