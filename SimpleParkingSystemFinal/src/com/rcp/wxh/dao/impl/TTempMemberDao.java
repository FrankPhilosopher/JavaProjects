package com.rcp.wxh.dao.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.sql.Delete;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TTempMember;

/**
 * 临时会员信息操作数据层
 * 
 * @author JH 2011 11 3
 */
public class TTempMemberDao extends BaseHibernateDAO {
	/**
	 * 根据卡查临时会员
	 * 
	 * @param tCard
	 * @return
	 * @throws Exception
	 */
	public TTempMember getTempMemberByCard(TCard tCard) throws Exception {
		TTempMember tm = null;
		String sql = "from TTempMember where TCard=?";
		Object[] paras = { tCard };
		try {
			tm = (TTempMember) findByParas(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return tm;

	}

	/**
	 * 根据车牌号查询临时会员
	 * 
	 * @param carNumber
	 * @throws Exception
	 */
	public List<Object> getMemberByCarNumber(String carNumber) throws Exception {
		List<Object> tm = null;
		String sql = "from TTempMember where carnumber=?";
		Object[] paras = { carNumber };
		try {
			tm = findByProperties(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return tm;
	}

	// 对于临时member来说，更新汽车截图是一个insert操作，因为member以前是不存在的
	public void updateCarPic(TTempMember tempMember, String filename) throws Exception {
		// 如果该卡之前被使用过了，那么在temp_member表中会存在记录，要先删除！
		TTempMember tempMember2 = getTempMemberByCard(tempMember.getTCard());
		if (tempMember2!=null) {
			delte(tempMember2);
		}
		String sql = "insert into t_temp_member(CARDID,CARPICTURE) values(?,?)";
		Session session = getSession(); // 开启会话
		Connection con = session.connection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tempMember.getTCard().getCardid());
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		ps.setBinaryStream(2, in, in.available());
		int count = ps.executeUpdate();
		if (count != 1) {
			throw new Exception("图片保存失败！");
		}
		closeSession(session); // 关闭会话
		ps.close();
		con.close();

	}

}
