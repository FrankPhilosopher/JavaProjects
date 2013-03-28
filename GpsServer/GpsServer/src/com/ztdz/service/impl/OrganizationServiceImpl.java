package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TAccountDAO;
import com.ztdz.dao.impl.TOrgainzationDAO;
import com.ztdz.pojo.TAccount;
import com.ztdz.pojo.TOrgainzation;
import com.ztdz.tools.OrgLevel;

public class OrganizationServiceImpl {
	private TOrgainzationDAO tOrgainzationDAO;

	public OrganizationServiceImpl() {
		super();
	}

	public void settOrgainzationDAO(TOrgainzationDAO tOrgainzationDAO) {
		this.tOrgainzationDAO = tOrgainzationDAO;
	}

	public TOrgainzationDAO gettOrgainzationDAO() {
		return tOrgainzationDAO;
	}

	// ͨ�����Ʋ���һ������
	public TOrgainzation findByName(String name) {
		List<TOrgainzation> list = tOrgainzationDAO.findByName(name, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// �������Ż�������ά�����������
	public void addOrganization(TOrgainzation tOrgainzation) {
		tOrgainzationDAO.save(tOrgainzation);
	}

	// ������ɾ�����Ż�������ά�����������
	public void delOrganization(int orgid) {
		tOrgainzationDAO.delete(tOrgainzationDAO.findById(orgid));
	}

	// �޸ļ��Ż���ά�����������
	public void updateOrganization(TOrgainzation tOrgainzation) {
		tOrgainzationDAO.attachDirty(tOrgainzation);
	}

	// ���������Ҽ��Ż�������ά�����������
	public TOrgainzation findById(int orgid) {
		return tOrgainzationDAO.findById(orgid);
	}

	// �������м��Ż�������ά�����������(����ҳ)
	public List findAll() {
		return tOrgainzationDAO.findAll(-1, -1);
	}

	// �������м��Ż�������ά�����������(��ҳ)
	public List findAll(int firstResult, int maxResults) {
		return tOrgainzationDAO.findAll(firstResult, maxResults);
	}

	// �������м��Ż�������ά�����������������
	public int getCountAll(int firstResult, int maxResults) {
		return tOrgainzationDAO.getCountAll();
	}

	// ����������ά���� ������ҳ��������ʱ����ά������org_level��Ϊ1�������Ż�����org_level��Ϊ2��
	public List findAllOM() { // OM����ά����д,���˺þ�
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.YUNWEI_LEVLE, -1, -1);
	}

	// ����������ά���� ����ҳ��������ʱ����ά������org_level��Ϊ1�������Ż�����org_level��Ϊ2��
	public List findAllOM(int firstResult, int maxResults) { // OM����ά����д,���˺þ�
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.YUNWEI_LEVLE,
				firstResult, maxResults);
	}

	// ����������ά���������� ������ʱ����ά������org_level��Ϊ1�������Ż�����org_level��Ϊ2��
	public int getCountAllOM() { // OM����ά����д,���˺þ�
		return tOrgainzationDAO.getCountByOrgLevel(OrgLevel.YUNWEI_LEVLE);
	}

	// �������м��Ż���������ҳ��
	public List findAllBloc() { // Bloc�Ǽ��ţ�group�������飬����������Bloc��
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.JITUAN_LEVEL, -1, -1);
	}

	// �������м��Ż�������ҳ��
	public List findAllBloc(int firstResult, int maxResults) { // Bloc�Ǽ��ţ�group�������飬����������Bloc��
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.JITUAN_LEVEL,
				firstResult, maxResults);
	}

	// �������м��Ż����ĸ���
	public int getCountAllBloc() { // Bloc�Ǽ��ţ�group�������飬����������Bloc��
		return tOrgainzationDAO.getCountByOrgLevel(OrgLevel.JITUAN_LEVEL);
	}

	// ����ĳ����ά�������������з��飨����ҳ��
	public List<TOrgainzation> findAllGroupsByOM(int areaId) {// OM����ά����˼
		return tOrgainzationDAO.findAllGroupsByAreaId(areaId, -1, -1);
	}

	// ����ĳ����ά�������������з��飨��ҳ��
	public List<TOrgainzation> findAllGroupsByOM(int areaId, int firstResult,
			int maxResults) {// OM����ά����˼
		return tOrgainzationDAO.findAllGroupsByAreaId(areaId, firstResult,
				maxResults);
	}

	// ����ĳ����ά�������������з���ĸ���
	public int getCountAllGroupsByOM(int areaId) {// OM����ά����˼
		return tOrgainzationDAO.getCountAllGroupsByAreaId(areaId);
	}

	// ����ĳ�����Ż����µ����з��飨����ҳ��
	public List findAllGroupsByBloc(int blocId) {// bloc�Ǽ��ŵ���˼
		return tOrgainzationDAO.findAllGroupsByUporgId(blocId, -1, -1);
	}

	// ����ĳ�����Ż����µ����з��飨��ҳ��
	public List findAllGroupsByBloc(int blocId, int firstResult, int maxResults) {// bloc�Ǽ��ŵ���˼
		return tOrgainzationDAO.findAllGroupsByUporgId(blocId, firstResult,
				maxResults);
	}

	// ����ĳ�����Ż����µ����з���ĸ���
	public int getCountAllGroupsByBloc(int blocId) {// bloc�Ǽ��ŵ���˼
		return tOrgainzationDAO.getCountAllGroupsByUporgId(blocId);
	}

	// ����ĳ�����������м��ţ�����ҳ��
	public List findAllBlocByAreaId(int areaId) {
		return tOrgainzationDAO.findAllBlocByAreaId(areaId, -1, -1);
	}

	// ����ĳ�����������м��ţ���ҳ��
	public List findAllBlocByAreaId(int areaId, int firstResult, int maxResults) {
		return tOrgainzationDAO.findAllBlocByAreaId(areaId, firstResult,
				maxResults);
	}

	// ����ĳ�����������м��ŵ�����
	public int getCountAllBlocByAreaId(int areaId) {
		return tOrgainzationDAO.getCountAllBlocByAreaId(areaId);
	}

	// ͨ��levelΪ0�ҵ�root����
	public TOrgainzation findRootOrg() {
		List<TOrgainzation> list = tOrgainzationDAO.findByOrgLevel(
				OrgLevel.ADMIN_LEVEL, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	// �����Ʋ��Ҽ���
	public TOrgainzation findJituanByName(String name) {
		return tOrgainzationDAO.findJituanByName(name);
	}

	// ------------------------------ģ����ѯ-----------------------------------//
	/**
	 * ϵͳ����ģ�� wjy
	 */
	// ��ѯ��ά������ά�������ƽ���ģ����ѯ(����ҳ)
	public List findYunweiMohu(String name) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.YUNWEI_LEVLE, -1,
				-1);
	}

	// ��ѯ��ά������ά�������ƽ���ģ����ѯ(��ҳ)
	public List findYunweiMohu(String name, int firstResult, int maxResults) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.YUNWEI_LEVLE,
				firstResult, maxResults);
	}

	// ��ѯ������������ά�ĸ���
	public int getCountYunweiMohu(String name) {
		return tOrgainzationDAO.getCountMohuByName(name, OrgLevel.YUNWEI_LEVLE);
	}

	// ��ѯ���ţ������Ż������ƽ���ģ����ѯ(����ҳ)
	public List findJituanMohu(String name) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.JITUAN_LEVEL, -1,
				-1);
	}

	// ��ѯ���ţ������Ż������ƽ���ģ����ѯ(��ҳ)
	public List findJituanMohu(String name, int firstResult, int maxResults) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.JITUAN_LEVEL,
				firstResult, maxResults);
	}

	// ��ѯ���������ļ��ŵĸ���
	public int getCountJituanMohu(String name) {
		return tOrgainzationDAO.getCountMohuByName(name, OrgLevel.JITUAN_LEVEL);
	}

	/**
	 * ��ά����ģ��
	 */
	// ������ά�����������ķ���(��ҳ)
	public List findMohuByYunwei(String name, int areaId, int firstResult,
			int maxResults) {
		return tOrgainzationDAO.findMohuByNameAndAreaId(name, areaId,
				firstResult, maxResults);
	}

	// ������ά�����������ķ���(����ҳ)
	public List findMohuByYunwei(String name, int areaId) {
		return tOrgainzationDAO.findMohuByNameAndAreaId(name, areaId, -1, -1);
	}

	// ������ά�����������ķ���ĸ���
	public int getCountMohuByYunwei(String name, int areaId) {
		return tOrgainzationDAO.getCountMohuByNameAndAreaId(name, areaId);
	}

	/**
	 * ���Ź���ģ��
	 */
	// ���Ҽ��������������ķ���(��ҳ)
	public List findMohuByJituan(String name, int upOrgId, int firstResult,
			int maxResults) {
		return tOrgainzationDAO.findMohuByNameAndorgId(name, upOrgId,
				firstResult, maxResults);
	}

	// ���Ҽ��������������ķ���(����ҳ)
	public List findMohuByJituan(String name, int upOrgId) {
		return tOrgainzationDAO.findMohuByNameAndorgId(name, upOrgId, -1, -1);
	}

	// ���Ҽ��������������ķ���ĸ���
	public int getCountMohuByJituan(String name, int upOrgId) {
		return tOrgainzationDAO.getCountMohuByNameAndorgId(name, upOrgId);
	}
	/**
	 * �������ģ��
	 */
}
