package com.yinger.patientims.dao;

import java.sql.ResultSet;

import com.yinger.patientims.model.Doctor;

public class DoctorDAO extends DAOBase {

	// 将数据库中取出的一条医生信息设置到一个Doctor对象上
	// 这个方法不处理异常，直接将异常抛出去
	public static Doctor setOneDoctor(ResultSet resultSet) throws Exception {
		Doctor doctor = new Doctor();
		doctor.setId(resultSet.getLong("id"));
		doctor.setAge(resultSet.getInt("age"));
		doctor.setSex(resultSet.getString("sex"));
		doctor.setTitle(resultSet.getString("title"));
		doctor.setName(resultSet.getString("name"));
		doctor.setPhone(resultSet.getString("phone"));
		doctor.setLogtime(resultSet.getDate("logtime"));
		doctor.setPassword(resultSet.getString("password"));
		doctor.setUsername(resultSet.getString("username"));
		return doctor;
	}

}
