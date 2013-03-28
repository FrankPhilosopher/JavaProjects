package com.yinger.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 没有通过Hibernate框架，直接测试
 */

public class MyBlob {
	/*
	 * create table photo( id int not null auto_increment primary key , pname varchar(30) not null , myphoto blob );
	 */
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blobtest", "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void add(String fromFileName, String toFileName) {
		Connection conn = this.getConn();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("insert into  photo( pname , myphoto)  values(?,?)");
			ps.setString(1, toFileName);
			File file = new File(fromFileName);
			InputStream in;
			in = new BufferedInputStream(new FileInputStream(file));
			ps.setBinaryStream(2, in, (int) file.length());// setBinaryStream
			int count = 0;
			count = ps.executeUpdate();
			if (count == 1) {
				System.out.println("插入数据成功！");
			} else {
				System.out.println("插入数据失败！");
			}
			in.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getAll() {
		String sql = "select  * from   photo";
		Connection conn = this.getConn();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int j = 0;
			while (rs.next()) {
				InputStream ins = null;
				OutputStream out = null;
				j++;
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
				Blob blob = rs.getBlob("myphoto");
				ins = blob.getBinaryStream();
				File f = new File(rs.getString(2));
				out = new BufferedOutputStream(new FileOutputStream(f));
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = ins.read(buf)) != -1) {
					out.write(buf);
				}
				ins.close();
				out.close();
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void get(int id) {
		String sql = "select  *from   photo  where  id= ?";
		Connection conn = this.getConn();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			File f = null;
			InputStream ins = null;
			OutputStream out = null;
			Blob blob = null;
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
				blob = rs.getBlob("myphoto");
				ins = blob.getBinaryStream();
				f = new File(rs.getString(2));
				out = new BufferedOutputStream(new FileOutputStream(f));
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = ins.read(buf)) != -1) {
					out.write(buf);
				}
				ins.close();
				out.close();
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, File newFile) {
		try {
			String sql = "update  photo  set  myphoto=?  where  id=" + id;
			Connection conn = this.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			InputStream in = new BufferedInputStream(new FileInputStream(newFile));
			ps.setBinaryStream(1, in, (int) newFile.length());
			int count = 0;
			count = ps.executeUpdate();
			System.out.println(count);
			ps.close();
			in.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		MyBlob myBlob = new MyBlob();
		myBlob.add("C:\\29.jpg", "C:\\30.jpg");
		myBlob.getAll();// 结果是：得到了新的图片 30.jpg和29.jpg一样！
	}
}
