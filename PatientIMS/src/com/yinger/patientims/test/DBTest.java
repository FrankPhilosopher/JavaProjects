package com.yinger.patientims.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yinger.patientims.dao.DAOBase;

public class DBTest {

	public static void main(String[] args) {

		// 给病房添加病床的批量处理方法，这样比手工添加要快很多！
//		Connection connection = new DAOBase().getConnection();
//		String sql = "select id from t_sickroom";
//		try {
//			Statement statement = connection.createStatement();
//			Statement statement2 = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(sql);
//			while (resultSet.next()) {
//				String id = resultSet.getString("id");
//				for (int i = 1; i <= 6; i++) {
//					//INSERT INTO t_sickbed(sickbedno,sickroom_id) VALUES (1,1)
//					String sql2 = "INSERT INTO t_sickbed(sickbedno,sickroom_id) VALUES (" + i + "," + id + ");";
//					statement2.addBatch(sql2);
//				}
//			}
//			statement2.executeBatch();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		
		// 批量增加病人信息的方法
		Connection connection = new DAOBase().getConnection();
		String sql = "select id from t_sickbed";
		try {
			Statement statement = connection.createStatement();
			Statement statement2 = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			for(int i=1;i<=5;i++){
				resultSet.next();
				String id = resultSet.getString("id");
				String sql2 = "INSERT INTO t_patient(name,sex,age,phone,logtime,address,sickbed_id) VALUES ('patient"+i+"','female',23,'1235132535','2011-10-9','fasdf',"+String.valueOf(Integer.valueOf(id)+5)+");";
				System.out.println(sql2);
				statement2.addBatch(sql2);
			}
			statement2.executeBatch();
			statement2.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
