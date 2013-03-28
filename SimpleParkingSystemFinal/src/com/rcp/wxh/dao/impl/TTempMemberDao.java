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
 * ��ʱ��Ա��Ϣ�������ݲ�
 * 
 * @author JH 2011 11 3
 */
public class TTempMemberDao extends BaseHibernateDAO {
	/**
	 * ���ݿ�����ʱ��Ա
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
	 * ���ݳ��ƺŲ�ѯ��ʱ��Ա
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

	// ������ʱmember��˵������������ͼ��һ��insert��������Ϊmember��ǰ�ǲ����ڵ�
	public void updateCarPic(TTempMember tempMember, String filename) throws Exception {
		// ����ÿ�֮ǰ��ʹ�ù��ˣ���ô��temp_member���л���ڼ�¼��Ҫ��ɾ����
		TTempMember tempMember2 = getTempMemberByCard(tempMember.getTCard());
		if (tempMember2!=null) {
			delte(tempMember2);
		}
		String sql = "insert into t_temp_member(CARDID,CARPICTURE) values(?,?)";
		Session session = getSession(); // �����Ự
		Connection con = session.connection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tempMember.getTCard().getCardid());
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		ps.setBinaryStream(2, in, in.available());
		int count = ps.executeUpdate();
		if (count != 1) {
			throw new Exception("ͼƬ����ʧ�ܣ�");
		}
		closeSession(session); // �رջỰ
		ps.close();
		con.close();

	}

}
