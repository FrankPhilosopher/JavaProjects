package com.yinger.patientims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yinger.patientims.model.Department;

public class DepartmentDAO extends DAOBase{

//	private List departmentList = new ArrayList();
//	private Department department;
	
	// 得到所有的科室
	public List<Department> getDepartmentList(){
		Department department;
		List<Department> list = new ArrayList<Department>();
		Connection connection = null;
		String sql = "select id,name from t_department";
		try {
			connection = getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				department = setOneDepartment(resultSet);
				list.add(department);
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

	// 从数据库中取出一条记录并保存到一个Department中
	private Department setOneDepartment(ResultSet resultSet) throws Exception {
		Department department = new Department();
		if(resultSet.getLong("id")!=0){
			department.setId(resultSet.getLong("id"));
		}
		if(resultSet.getString("name")!=null){
			department.setName(resultSet.getString("name"));
		}
		return department;
	}

}
