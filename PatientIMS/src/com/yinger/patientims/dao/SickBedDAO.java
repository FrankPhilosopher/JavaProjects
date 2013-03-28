package com.yinger.patientims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yinger.patientims.model.SickBed;

public class SickBedDAO extends DAOBase{
	
	// 得到所有的病床
	public List<SickBed> getSickBedList(String where){
		SickBed sickbed;
		List<SickBed> list = new ArrayList<SickBed>();
		Connection connection = null;
		String sql = "select id,sickbedno,sickroom_id from t_sickbed where id is not null "+where;
//		System.out.println(sql);
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				sickbed = setOneSickBed(resultSet);
				list.add(sickbed);
			}
			pStatement.close();
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

	// 从数据库中取出一条记录并保存到一个病床中
	private SickBed setOneSickBed(ResultSet resultSet) throws Exception {
		SickBed sickbed = new SickBed();
		if(resultSet.getLong("id")!=0){
			sickbed.setId(resultSet.getLong("id"));
		}
		if(resultSet.getLong("sickroom_id")!=0){
			sickbed.getSickRoom().setId(resultSet.getLong("sickroom_id"));
		}
		if(resultSet.getInt("sickbedno")!=0){
			sickbed.setSickBedNo(resultSet.getInt("sickbedno"));
		}
		return sickbed;
	}

}
