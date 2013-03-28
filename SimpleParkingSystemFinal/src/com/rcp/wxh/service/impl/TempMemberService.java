package com.rcp.wxh.service.impl;

import java.util.List;

import com.rcp.wxh.dao.impl.TTempMemberDao;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TTempMember;

/**
 * 有关TempMember 对象的业务逻辑处理
 * 
 * @author JH
 *         2011 11 3
 */
public class TempMemberService {
	/**
	 * 新增临时会员
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void addTempMember(TTempMember member) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		try {
			tdao.save(member);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 删除临时会员
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void delTempMember(TTempMember member) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		try {
			tdao.delte(member);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新临时会员
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void updateTempMember(TTempMember member) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		try {
			tdao.update(member);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 保存或者更新临时会员
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void saveOrUpdateTempMember(TTempMember member) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		try {
			tdao.saveOrUpdate(member);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据会员ID查询临时会员
	 * 
	 * @param memberid
	 * @throws Exception
	 */
	public TTempMember getTempMemberById(Integer memberid) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		TTempMember tTempMember = null;
		try {
			tTempMember = (TTempMember) tdao.findById(TTempMember.class, memberid);
		} catch (Exception e) {
			throw e;
		}
		return tTempMember;
	}

	/**
	 * 根据卡号查询临时会员
	 * 
	 * @param tCard
	 * @throws Exception
	 */
	public TTempMember getMemberByCard(TCard tCard) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		TTempMember tTempMember = null;
		try {
			tTempMember = tdao.getTempMemberByCard(tCard);
		} catch (Exception e) {
			throw e;
		}
		return tTempMember;
	}

	/**
	 * 根据车牌号查询临时会员
	 * 
	 * @param carNumber
	 * @throws Exception
	 */
	public List<Object> getMemberByCarNumber(String carNumber) throws Exception {
		TTempMemberDao tdao = new TTempMemberDao();
		List<Object> tTempMember = null;
		try {
			tTempMember = tdao.getMemberByCarNumber(carNumber);
		} catch (Exception e) {
			throw e;
		}
		return tTempMember;
	}

}
