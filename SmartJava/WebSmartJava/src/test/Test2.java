package test;

import manager.*;
import entity.*;

import java.sql.SQLException;
import java.util.*;
public class Test2 {

	/**
	 * 测试订单管理类
	 */
	public static void main(String[] args) {
		OrderManager om=new OrderManager();
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("currentDistribution","27");
		try {
			ArrayList<Order> list=om.searchByMap(hm);
			System.out.println("list:"+list.size());
		} catch (SQLException e) {			
			e.printStackTrace();
		}

	}

}
