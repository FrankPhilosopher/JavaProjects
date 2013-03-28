package com.yinger.patientims.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yinger.patientims.model.SickRoom;

public class SickRoomDAO extends DAOBase{
	
	// 得到所有的病房
	public List<SickRoom> getSickRoomList(String where){
		SickRoom sickroom;
		List<SickRoom> list = new ArrayList<SickRoom>();//arraylist的默认大小是10
		Connection connection = null;
		String sql = "select id,sickroomno,department_id from t_sickroom where id is not null" + where;
//		System.out.println(sql);
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				sickroom = setOneSickRoom(resultSet);
				list.add(sickroom);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				connection.close();// Quick Fix : Ctrl + 1
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 从数据库中取出一条记录并保存到一个病房中
	private SickRoom setOneSickRoom(ResultSet resultSet) throws Exception {
		SickRoom sickroom = new SickRoom();
		if(resultSet.getLong("id")!=0){
			sickroom.setId(resultSet.getLong("id"));
		}
		if(resultSet.getLong("department_id")!=0){
			sickroom.getDepartment().setId(resultSet.getLong("department_id"));
		}
		if(resultSet.getInt("sickroomno")!=0){
			sickroom.setSickRoomNo(resultSet.getInt("sickroomno"));
		}
		return sickroom;
	}

}
