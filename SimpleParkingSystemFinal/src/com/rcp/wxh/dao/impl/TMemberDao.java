package com.rcp.wxh.dao.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;

/**
 * 会员信息操作数据层
 * 
 * @author JH 2011 11 3
 */
public class TMemberDao extends BaseHibernateDAO {
	public static final int NAME = 1;
	public static final int CARNUMBER = 2;
	public static final int CARTYPE = 3;
	public static final int IDENTIFICATION = 4;
	public static final int PHONENUMBER = 5;
	public static final int WORKPLACE = 6;

	/**
	 * 根据卡查会员
	 * 
	 * @param tCard
	 * @return
	 * @throws Exception
	 */
	public TMember getMemberByCard(TCard tCard) throws Exception {
		TMember tm = null;
		String sql = "from TMember where TCard=?";
		Object[] paras = { tCard };
		try {
			tm = (TMember) findByParas(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return tm;

	}

	/**
	 * 根据参数查会员
	 * 
	 * @param identification
	 * @return
	 * @throws Exception
	 */
	public List<Object> getMemberByPara(String para, int op) throws Exception {
		List<Object> tm = null;
		String sql = null;
		switch (op) {
		case NAME:
			sql = "from TMember where name=?";
			break;
		case CARNUMBER:
			sql = "from TMember where carnumber=?";
			break;
		case CARTYPE:
			sql = "from TMember where cartype=?";
			break;
		case IDENTIFICATION:
			sql = "from TMember where identification=?";
			break;
		case PHONENUMBER:
			sql = "from TMember where phonenumber=?";
			break;
		case WORKPLACE:
			sql = "from TMember where workplace=?";
			break;
		}
		Object[] paras = { para };
		try {
			tm = findByProperties(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return tm;
	}

	// 对于member来说，插入汽车截图是一个更新操作，因为member以前是存在的
	public void updateCarPic(TMember member, String filename) throws Exception {
		String sql = "update t_member set CARPICTURE = ? where MEMBERID=" + member.getMemberid();
		Session session = getSession(); // 开启会话
		Connection con = session.connection();
		PreparedStatement ps = con.prepareStatement(sql);
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		ps.setBinaryStream(1, in, in.available());
		int count = ps.executeUpdate();
		if (count != 1) {
			throw new Exception("图片保存失败！");
		}
		closeSession(session); // 关闭会话
		ps.close();
		con.close();
	}

}
