package com.yinger.patientims.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.yinger.patientims.dao.DAOBase;
import com.yinger.patientims.dao.DoctorDAO;
import com.yinger.patientims.model.Doctor;
import com.yinger.patientims.model.Patient;

/**
 * 数据库操作的帮助类 
 *
 */
public class DBUtil {

	private static DAOBase daoBase;
	static {
		daoBase = new DAOBase();
	}
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 根据用户输入的信息判断信息是否正确有效
	public static Doctor getLoginInfo(Doctor doctor) {
		Connection connection = daoBase.getConnection();
		ResultSet resultSet;
		Statement statement;
		// SELECT * FROM t_doctor WHERE username='huatuo' and
		// password='1234'
		String sqlString = "SELECT * FROM t_doctor WHERE username='" + doctor.getUsername() + "' and password='" + doctor.getPassword() + "'";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlString);
			while (resultSet.next()) {
				doctor = DoctorDAO.setOneDoctor(resultSet);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return doctor;
	}

	// 判断某个病床是否已经有病人了
	public static boolean validateSickBed(String dname, int bname, int rname) {
		Connection connection = daoBase.getConnection();
		ResultSet resultSet = null;
		Statement statement = null;
		// SELECT p.id FROM t_patient p,t_sickbed b,t_sickroom
		// r,t_department d
		// WHERE p.sickbed_id=b.id and b.sickroom_id=r.id and
		// r.department_id=d.id
		// and d.name='普外科' and b.sickbedno=2 and r.sickroomno=101
		String sql = "SELECT p.id FROM t_patient p,t_sickbed b,t_sickroom r,t_department d ";
		sql += " WHERE p.sickbed_id=b.id and b.sickroom_id=r.id and r.department_id=d.id ";
		sql += "and d.name='" + dname + "' and b.sickbedno=" + bname + " and r.sickroomno=" + rname + "";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {// 存在病人
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;// 默认是不存在
	}

	//根据已有的病人信息得到病床id---->这个方法已经没有用了！
	public static Long getSickBedId(Patient patient) {
		Connection connection = daoBase.getConnection();
		ResultSet resultSet = null;
		Statement statement = null;
		// SELECT b.id FROM t_sickbed b,t_sickroom r,t_department d
		// WHERE b.sickroom_id=r.id and r.department_id=d.id
		// and d.name='普外科' and b.sickbedno=2 and r.sickroomno=101
		String sql = "SELECT b.id FROM t_sickbed b,t_sickroom r,t_department d ";
		sql += " WHERE b.sickroom_id=r.id and  r.department_id=d.id ";
		sql += "and d.name='" + patient.getDepartment().getName() + "' and b.sickbedno=" + patient.getSickbed().getSickBedNo() + " and r.sickroomno="
				+ patient.getSickroom().getSickRoomNo() + "";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) { //注意，在resultset调用它的方法之前statement是不可以close的！
				return resultSet.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

}
