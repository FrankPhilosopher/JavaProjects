package cn.edu.csu.dbhospital.test;

import java.util.UUID;

import cn.edu.csu.dbhospital.util.MD5Util;

public class UuidTest {
	
	public static void main(String[] args) {
		
		System.out.println(UUID.randomUUID());//b8a73796-cb73-4b32-96a5-e129558f716b
		//91762e52-8d0f-4b5b-a8f7-7fdc8f2af202
		
		System.out.println(MD5Util.encrypt("admin"));//21232F297A57A5A743894A0E4A801FC3
	}

}
