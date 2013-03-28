package com.yinger.patientims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yinger.patientims.model.Patient;
import com.yinger.patientims.util.DBUtil;

public class PatientDAO extends DAOBase {

	// 得到所有住院的病人的信息
	// SELECT
	// p.id,p.name,p.age,p.sex,p.address,p.logtime,p.phone,d.name,b.sickbedno,r.sickroomno
	// FROM t_patient p,t_department d,t_sickbed b,t_sickroom r
	// WHERE p.sickbed_id=b.id and b.sickroom_id=r.id and r.department_id=d.id
	public List<Patient> getPatientInfoList() {
		Patient patient;
		List<Patient> list = new ArrayList<Patient>();
		// 注意：这里有两个name，要使用到别名
		String sql = "SELECT p.id,p.name as pname,p.age,p.sex,p.address,p.logtime,p.phone,d.name as dname,b.sickbedno,r.sickroomno "
				+ "FROM t_patient p,t_department d,t_sickbed b,t_sickroom r "
				+ "WHERE p.sickbed_id=b.id and b.sickroom_id=r.id and r.department_id=d.id";
		Connection connection = null;
		// 在finally中使用它是它必须要初始化
		// 还有，它一定要放在外面，这样在try外面（finally）才可以被访问到
		// 还有，一定要有finally！关闭数据库连接是很重要的！
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				patient = setOnePatient(resultSet);
				list.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 将数据库中的一条patient记录关联到一个Patient对象中
	// 并且它不处理异常，抛出由上层处理
	private Patient setOnePatient(ResultSet resultSet) throws Exception {
		Patient patient = new Patient();
		if (resultSet.getLong("id") != 0) {
			patient.setId(resultSet.getLong("id"));
		}
		if (resultSet.getString("pname") != null) {
			patient.setName(resultSet.getString("pname"));
		}
		if (resultSet.getString("sex") != null) {
			patient.setSex(resultSet.getString("sex"));
		}
		if (resultSet.getString("phone") != null) {
			patient.setPhone(resultSet.getString("phone"));
		}
		if (resultSet.getString("address") != null) {
			patient.setAddress(resultSet.getString("address"));
		}
		if (resultSet.getInt("age") != 0) {
			patient.setAge(resultSet.getInt("age"));
		}
		if (resultSet.getDate("logtime") != null) {
			patient.setLogtime(resultSet.getDate("logtime"));
		}
		if (resultSet.getString("dname") != null) {
			// 注意这一步！这里容易发生空指针异常！在一个Patient对象中Department等属性并没有被初始化！
			// 所以要在类中new出各个对象，对于SickRoom等等同理
			patient.getDepartment().setName(resultSet.getString("dname"));
		}
		if (resultSet.getInt("sickbedno") != 0) {
			patient.getSickbed().setSickBedNo(resultSet.getInt("sickbedno"));
		}
		if (resultSet.getInt("sickroomno") != 0) {
			patient.getSickroom().setSickRoomNo(resultSet.getInt("sickroomno"));
		}
		return patient;
	}

	// 删除病人信息
	public boolean deletePatient(Patient patient) {
		Long id = patient.getId();
		Connection connection = null;
		String sql = "delete from t_patient where id=" + id + "";
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			int res = pStatement.executeUpdate();
			if (res > 0) {
				return true; // 注意：这里虽然是return了，但是这个方法真正返回之前还是要执行finally
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 添加病人住院信息
	public boolean insertPatient(Patient patient) {
		Connection connection = null;
		// INSERT INTO
		// t_patient(name,sex,age,phone,logtime,address,sickbed_id) VALUES
		// ('patient"+i+"','女',37,'1533535354','2011-10-10','TianJin',"+id+")
		String sql = "INSERT INTO t_patient(name,sex,age,phone,logtime,address,sickbed_id) VALUES ('" + patient.getName() + "','" + patient.getSex() + "',"
				+ patient.getAge() + ",'" + patient.getPhone() + "','" + DBUtil.simpleDateFormat.format(patient.getLogtime()) + "','" + patient.getAddress() + "',"
				+ patient.getSickbed().getId() + ")";
		//patient.getLogtime().toLocaleString()  已经不推荐使用了
		//注意：时间是有点特别地，一定要 patient.getLogtime().toLocaleString()，或者是使用 DateFormater进行format一下！
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			int res = pStatement.executeUpdate();
			if (res > 0) {
				return true; // 注意：这里虽然是return了，但是这个方法真正返回之前还是要执行finally
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 修改病人住院信息
	public boolean updatePatient(Patient patient) {
		Connection connection = null;
		//UPDATE t_patient as p set p.name='yyh' , p.address='ghalsdhgl' , p.age=24 , p.phone='523659365', p.sex='female'
		// where id=6
		StringBuffer sql = new StringBuffer("UPDATE t_patient as p ");
		sql.append(" set p.name='"+patient.getName()+"' ");
		sql.append(", p.address='"+patient.getAddress()+"' ");
		sql.append(" , p.age="+patient.getAge()+" ");
		sql.append(" , p.phone='"+patient.getPhone()+"' ");
		sql.append(", p.sex='"+patient.getSex()+"' ");
		sql.append(" ,p.sickbed_id= "+patient.getSickbed().getId()+" ");
		sql.append(" where id="+patient.getId());
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql.toString());
			int res = pStatement.executeUpdate();
			if (res > 0) {
				return true; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
