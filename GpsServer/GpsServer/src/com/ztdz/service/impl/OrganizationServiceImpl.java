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

	// 通过名称查找一个机构
	public TOrgainzation findByName(String name) {
		List<TOrgainzation> list = tOrgainzationDAO.findByName(name, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// 新增集团机构或运维机构或组机构
	public void addOrganization(TOrgainzation tOrgainzation) {
		tOrgainzationDAO.save(tOrgainzation);
	}

	// 按主键删除集团机构或运维机构或组机构
	public void delOrganization(int orgid) {
		tOrgainzationDAO.delete(tOrgainzationDAO.findById(orgid));
	}

	// 修改集团或运维机构或组机构
	public void updateOrganization(TOrgainzation tOrgainzation) {
		tOrgainzationDAO.attachDirty(tOrgainzation);
	}

	// 按主键查找集团机构或运维机构或组机构
	public TOrgainzation findById(int orgid) {
		return tOrgainzationDAO.findById(orgid);
	}

	// 查找所有集团机构和运维机构和组机构(不分页)
	public List findAll() {
		return tOrgainzationDAO.findAll(-1, -1);
	}

	// 查找所有集团机构和运维机构和组机构(分页)
	public List findAll(int firstResult, int maxResults) {
		return tOrgainzationDAO.findAll(firstResult, maxResults);
	}

	// 查找所有集团机构和运维机构和组机构的总数
	public int getCountAll(int firstResult, int maxResults) {
		return tOrgainzationDAO.getCountAll();
	}

	// 查找所有运维机构 （不分页）（我暂时把运维机构的org_level设为1，将集团机构的org_level设为2）
	public List findAllOM() { // OM是运维的缩写,查了好久
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.YUNWEI_LEVLE, -1, -1);
	}

	// 查找所有运维机构 （分页）（我暂时把运维机构的org_level设为1，将集团机构的org_level设为2）
	public List findAllOM(int firstResult, int maxResults) { // OM是运维的缩写,查了好久
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.YUNWEI_LEVLE,
				firstResult, maxResults);
	}

	// 查找所有运维机构的总数 （我暂时把运维机构的org_level设为1，将集团机构的org_level设为2）
	public int getCountAllOM() { // OM是运维的缩写,查了好久
		return tOrgainzationDAO.getCountByOrgLevel(OrgLevel.YUNWEI_LEVLE);
	}

	// 查找所有集团机构（不分页）
	public List findAllBloc() { // Bloc是集团（group用作分组，所以这里用Bloc）
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.JITUAN_LEVEL, -1, -1);
	}

	// 查找所有集团机构（分页）
	public List findAllBloc(int firstResult, int maxResults) { // Bloc是集团（group用作分组，所以这里用Bloc）
		return tOrgainzationDAO.findByOrgLevel(OrgLevel.JITUAN_LEVEL,
				firstResult, maxResults);
	}

	// 查找所有集团机构的个数
	public int getCountAllBloc() { // Bloc是集团（group用作分组，所以这里用Bloc）
		return tOrgainzationDAO.getCountByOrgLevel(OrgLevel.JITUAN_LEVEL);
	}

	// 查找某个运维机构下属的所有分组（不分页）
	public List<TOrgainzation> findAllGroupsByOM(int areaId) {// OM是运维的意思
		return tOrgainzationDAO.findAllGroupsByAreaId(areaId, -1, -1);
	}

	// 查找某个运维机构下属的所有分组（分页）
	public List<TOrgainzation> findAllGroupsByOM(int areaId, int firstResult,
			int maxResults) {// OM是运维的意思
		return tOrgainzationDAO.findAllGroupsByAreaId(areaId, firstResult,
				maxResults);
	}

	// 查找某个运维机构下属的所有分组的个数
	public int getCountAllGroupsByOM(int areaId) {// OM是运维的意思
		return tOrgainzationDAO.getCountAllGroupsByAreaId(areaId);
	}

	// 查找某个集团机构下的所有分组（不分页）
	public List findAllGroupsByBloc(int blocId) {// bloc是集团的意思
		return tOrgainzationDAO.findAllGroupsByUporgId(blocId, -1, -1);
	}

	// 查找某个集团机构下的所有分组（分页）
	public List findAllGroupsByBloc(int blocId, int firstResult, int maxResults) {// bloc是集团的意思
		return tOrgainzationDAO.findAllGroupsByUporgId(blocId, firstResult,
				maxResults);
	}

	// 查找某个集团机构下的所有分组的个数
	public int getCountAllGroupsByBloc(int blocId) {// bloc是集团的意思
		return tOrgainzationDAO.getCountAllGroupsByUporgId(blocId);
	}

	// 查找某个地区的所有集团（不分页）
	public List findAllBlocByAreaId(int areaId) {
		return tOrgainzationDAO.findAllBlocByAreaId(areaId, -1, -1);
	}

	// 查找某个地区的所有集团（分页）
	public List findAllBlocByAreaId(int areaId, int firstResult, int maxResults) {
		return tOrgainzationDAO.findAllBlocByAreaId(areaId, firstResult,
				maxResults);
	}

	// 查找某个地区的所有集团的总数
	public int getCountAllBlocByAreaId(int areaId) {
		return tOrgainzationDAO.getCountAllBlocByAreaId(areaId);
	}

	// 通过level为0找到root机构
	public TOrgainzation findRootOrg() {
		List<TOrgainzation> list = tOrgainzationDAO.findByOrgLevel(
				OrgLevel.ADMIN_LEVEL, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	// 按名称查找集团
	public TOrgainzation findJituanByName(String name) {
		return tOrgainzationDAO.findJituanByName(name);
	}

	// ------------------------------模糊查询-----------------------------------//
	/**
	 * 系统管理模块 wjy
	 */
	// 查询运维：按运维机构名称进行模糊查询(不分页)
	public List findYunweiMohu(String name) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.YUNWEI_LEVLE, -1,
				-1);
	}

	// 查询运维：按运维机构名称进行模糊查询(分页)
	public List findYunweiMohu(String name, int firstResult, int maxResults) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.YUNWEI_LEVLE,
				firstResult, maxResults);
	}

	// 查询满足条件的运维的个数
	public int getCountYunweiMohu(String name) {
		return tOrgainzationDAO.getCountMohuByName(name, OrgLevel.YUNWEI_LEVLE);
	}

	// 查询集团：按集团机构名称进行模糊查询(不分页)
	public List findJituanMohu(String name) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.JITUAN_LEVEL, -1,
				-1);
	}

	// 查询集团：按集团机构名称进行模糊查询(分页)
	public List findJituanMohu(String name, int firstResult, int maxResults) {
		return tOrgainzationDAO.findMohuByName(name, OrgLevel.JITUAN_LEVEL,
				firstResult, maxResults);
	}

	// 查询满足条件的集团的个数
	public int getCountJituanMohu(String name) {
		return tOrgainzationDAO.getCountMohuByName(name, OrgLevel.JITUAN_LEVEL);
	}

	/**
	 * 运维管理模块
	 */
	// 查找运维下满足条件的分组(分页)
	public List findMohuByYunwei(String name, int areaId, int firstResult,
			int maxResults) {
		return tOrgainzationDAO.findMohuByNameAndAreaId(name, areaId,
				firstResult, maxResults);
	}

	// 查找运维下满足条件的分组(不分页)
	public List findMohuByYunwei(String name, int areaId) {
		return tOrgainzationDAO.findMohuByNameAndAreaId(name, areaId, -1, -1);
	}

	// 查找运维下满足条件的分组的个数
	public int getCountMohuByYunwei(String name, int areaId) {
		return tOrgainzationDAO.getCountMohuByNameAndAreaId(name, areaId);
	}

	/**
	 * 集团管理模块
	 */
	// 查找集团下满足条件的分组(分页)
	public List findMohuByJituan(String name, int upOrgId, int firstResult,
			int maxResults) {
		return tOrgainzationDAO.findMohuByNameAndorgId(name, upOrgId,
				firstResult, maxResults);
	}

	// 查找集团下满足条件的分组(不分页)
	public List findMohuByJituan(String name, int upOrgId) {
		return tOrgainzationDAO.findMohuByNameAndorgId(name, upOrgId, -1, -1);
	}

	// 查找集团下满足条件的分组的个数
	public int getCountMohuByJituan(String name, int upOrgId) {
		return tOrgainzationDAO.getCountMohuByNameAndorgId(name, upOrgId);
	}
	/**
	 * 分组管理模块
	 */
}
