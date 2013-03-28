package com.yinger.blog;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class Dbhelper {
	
	public static QueryRunner getQueryRunner(){
		QueryRunner qr=null;
		try {
			//ͨ����context.xml���������Դ�������֣����Ի�ȡ���Դ����
			Context context = new InitialContext();
			DataSource ds=(DataSource) context.lookup("java:comp/env/jdbc/mysqlds");//ע�⣺��һ��������java������jdbc��
			//DButils�ĺ����࣬���ʱ�������Դ��������������Լ��Ϳ��������ݿ����Ӷ��󣬲���ִ�����Ժ�Żص���ݳ���
			qr=new QueryRunner(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qr;
	}

}
