package com.yj.smarthome.test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class MyTest {
	public static void main(String[] args) {
		//1. 通过NetworkInterface类枚举出当前主机的全部网络设备数据
		try {
			//1.1 返回当前主机每一个网络接口对应的实例
			Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
			if (interfaceList == null) {
				System.out.println("--No interfaces found--");
			} else {
				while (interfaceList.hasMoreElements()) {
					NetworkInterface iface = interfaceList.nextElement();
					//1.2 iface.getName()方法返回的是设备的名称，通常由字母和数字联合构成。
					System.out.println("Interface " + iface.getName() + ":");
					//1.3 枚举出真正持有IP地址信息的对象
					Enumeration<InetAddress> addrList = iface.getInetAddresses();
					if (!addrList.hasMoreElements())
						System.out.println("\t(No Addresses for this interface.");
					while (addrList.hasMoreElements()) {
						InetAddress addr = addrList.nextElement();
						//1.4 这里输出的就是我们通常所说的点分形式的IP地址，如192.163.1.1
						if (addr instanceof Inet4Address)
							System.out.println("\tAddress " + addr.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Error getting network interfaces: " + e.getMessage());
		}
		//2. 根据指定主机名(host)，获取该主机的全部网络设备信息。
		String host = "yinger";
		try {
			System.out.println(host + ":");
			//2.1 通过主机名称直接获取该主机的全部网络地址。
			InetAddress[] addrList = InetAddress.getAllByName(host);
			for (InetAddress addr : addrList) {
				//2.2 getHostName()获取的是主机名，getHostAddress()获取的是点分形式的IP地址。
				if (addr instanceof Inet4Address)
					System.out.println("\t" + addr.getHostName() + "/" + addr.getHostAddress());
			}
		} catch (UnknownHostException e) {
			System.out.println("\tUnable to find address for " + host);
		}
	}
}
/*
 * 输出结果如下： Interface lo: Address 127.0.0.1 Interface net0: (No Addresses for this interface. Interface net1: (No
 * Addresses for this interface. Interface net2: (No Addresses for this interface. Interface net3: (No Addresses for
 * this interface. Interface ppp0: (No Addresses for this interface. Interface eth0: (No Addresses for this interface.
 * Interface eth1: (No Addresses for this interface. Interface eth2: (No Addresses for this interface. Interface ppp1:
 * (No Addresses for this interface. Interface net4: (No Addresses for this interface. Interface eth3: Address
 * 10.24.194.17 Interface net5: (No Addresses for this interface. Interface net6: (No Addresses for this interface.
 * Interface eth4: Interface net7: (No Addresses for this interface. Interface net8: (No Addresses for this interface.
 * Interface eth5: Address 192.168.225.1 Interface eth6: Address 192.168.220.1 Interface eth7: (No Addresses for this
 * interface. Interface eth8: (No Addresses for this interface. Interface eth9: (No Addresses for this interface.
 * Interface eth10: (No Addresses for this interface. Interface eth11: (No Addresses for this interface. Stephen-PC:
 * Stephen-PC/10.24.194.17 Stephen-PC/192.168.225.1 Stephen-PC/192.168.220.1
 */