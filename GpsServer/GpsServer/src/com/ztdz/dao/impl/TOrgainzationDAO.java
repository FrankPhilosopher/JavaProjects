package com.ztdz.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TOrgainzation;
import com.ztdz.tools.OrgLevel;

/**
 * A data access object (DAO) providing persistence and search support for
 * TOrgainzation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.ztdz.dao.TOrgainzation
 * @author MyEclipse Persistence Tools
 */

public class TOrgainzationDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TOrgainzationDAO.class);
	// property constants
	public static final String ORG_LEVEL = "orgLevel";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TELEPHONE = "telephone";
	public static final String LINKMAN = "linkman";
	public static final String CELLPHONE = "cellphone";
	public static final String WARNPHONE = "warnphone";
	public static final String FEESTANDARD = "feestandard";
	public static final String BALANCE = "balance";
	public static final String SHORT_NAME = "shortName";
	public static final String R1 = "r1";
	public static final String R2 = "r2";
	public static final String REMARK = "remark";

	protected void initDao() {
		// do nothing
	}

	public void save(TOrgainzation transientInstance) {
		log.debug("saving TOrgainzation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TOrgainzation persistentInstance) {
		log.debug("deleting TOrgainzation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TOrgainzation findById(java.lang.Integer id) {
		log.debug("getting TOrgainzation instance with id: " + id);
		try {
			TOrgainzation instance = (TOrgainzation) getHibernateTemplate()
					.get("com.ztdz.pojo.TOrgainzation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TOrgainzation> findByExample(TOrgainzation instance) {
		log.debug("finding TOrgainzation instance by example");
		try {
			List<TOrgainzation> results = (List<TOrgainzation>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value,
			int firstResult, int maxResults) {
		log.debug("finding TOrgainzation instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TOrgainzation instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOrgLevel(Object orgLevel, int firstResult, int maxResults) {
		return findByProperty(ORG_LEVEL, orgLevel, firstResult, maxResults);
	}

	public int getCountByOrgLevel(Object orgLevel) {
		return getCountByProperty(ORG_LEVEL, orgLevel);
	}

	public List findByName(Object name, int firstResult, int maxResults) {
		return findByProperty(NAME, name, firstResult, maxResults);
	}

	public int getCountByName(Object name) {
		return getCountByProperty(NAME, name);
	}

	public List findByAddress(Object address, int firstResult, int maxResults) {
		return findByProperty(ADDRESS, address, firstResult, maxResults);
	}

	public int getCountByAddress(Object address) {
		return getCountByProperty(ADDRESS, address);
	}

	public List findByTelephone(Object telephone, int firstResult,
			int maxResults) {
		return findByProperty(TELEPHONE, telephone, firstResult, maxResults);
	}

	public int getCountByTelephone(Object telephone) {
		return getCountByProperty(TELEPHONE, telephone);
	}

	public List findByLinkman(Object linkman, int firstResult, int maxResults) {
		return findByProperty(LINKMAN, linkman, firstResult, maxResults);
	}

	public int getCountByLinkman(Object linkman) {
		return getCountByProperty(LINKMAN, linkman);
	}

	public List findByCellphone(Object cellphone, int firstResult,
			int maxResults) {
		return findByProperty(CELLPHONE, cellphone, firstResult, maxResults);
	}

	public int getCountByCellphone(Object cellphone) {
		return getCountByProperty(CELLPHONE, cellphone);
	}

	public List findByWarnphone(Object warnphone, int firstResult,
			int maxResults) {
		return findByProperty(WARNPHONE, warnphone, firstResult, maxResults);
	}

	public int getCountByWarnphone(Object warnphone) {
		return getCountByProperty(WARNPHONE, warnphone);
	}

	public List findByFeestandard(Object feestandard, int firstResult,
			int maxResults) {
		return findByProperty(FEESTANDARD, feestandard, firstResult, maxResults);
	}

	public int getCountByFeestandard(Object feestandard) {
		return getCountByProperty(FEESTANDARD, feestandard);
	}

	public List findByBalance(Object balance, int firstResult, int maxResults) {
		return findByProperty(BALANCE, balance, firstResult, maxResults);
	}

	public int getCountByBalance(Object balance) {
		return getCountByProperty(BALANCE, balance);
	}

	public List findByShortName(Object shortName, int firstResult,
			int maxResults) {
		return findByProperty(SHORT_NAME, shortName, firstResult, maxResults);
	}

	public int getCountByShortName(Object shortName) {
		return getCountByProperty(SHORT_NAME, shortName);
	}

	public List findByR1(Object r1, int firstResult, int maxResults) {
		return findByProperty(R1, r1, firstResult, maxResults);
	}

	public int getCountByR1(Object r1) {
		return getCountByProperty(R1, r1);
	}

	public List findByR2(Object r2, int firstResult, int maxResults) {
		return findByProperty(R2, r2, firstResult, maxResults);
	}

	public int getCountByR2(Object r2) {
		return getCountByProperty(R2, r2);
	}

	public List findByRemark(Object remark, int firstResult, int maxResults) {
		return findByProperty(REMARK, remark, firstResult, maxResults);
	}

	public int getCountByRemark(Object remark) {
		return getCountByProperty(REMARK, remark);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TOrgainzation instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TOrgainzation instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TOrgainzation merge(TOrgainzation detachedInstance) {
		log.debug("merging TOrgainzation instance");
		try {
			TOrgainzation result = (TOrgainzation) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TOrgainzation instance) {
		log.debug("attaching dirty TOrgainzation instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TOrgainzation instance) {
		log.debug("attaching clean TOrgainzation instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TOrgainzationDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TOrgainzationDAO) ctx.getBean("TOrgainzationDAO");
	}

	// //
	// //此处增加了一个方法 查找某个运维机构下的所有组用户
	public List<TOrgainzation> findAllGroupsByAreaId(int areaId,
			int firstResult, int maxResults) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + areaId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(
					Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL)).add(
					Restrictions.eq("TArea.areaId", areaId));

			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			re.printStackTrace();// hjw
			throw re;
		}
	}

	// 根据运维用户的地区id查找某个运维机构下的所有组用户的总数
	public int getCountAllGroupsByAreaId(int areaId) {
		log.debug("get count of TOrgainzation instance with property: "
				+ ", value: " + areaId);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.createAlias("TArea", "tb")
					.add(Restrictions.eq("tb.areaId", areaId));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //
	// //此处又增加了一个方法 查找某个集团机构下的所有组用户
	public List findAllGroupsByUporgId(Integer uporgId, int firstResult,
			int maxResults) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + uporgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(
					Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL)).add(
					Restrictions.eq("TOrgainzation.orgId", uporgId));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //
	// //此处又增加了一个方法 查找某个集团机构下的所有组用户的个数
	public int getCountAllGroupsByUporgId(Integer uporgId) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + uporgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.createAlias("TOrgainzation", "o")
					.add(Restrictions.eq("o.orgId", uporgId));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按名称查找集团
	public TOrgainzation findJituanByName(String name) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + name);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(
					Restrictions.eq("orgLevel", OrgLevel.JITUAN_LEVEL)).add(
					Restrictions.eq("name", name));
			List<TOrgainzation> list = getHibernateTemplate().findByCriteria(
					detachedCriteria);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //
	// 查找处于某个地区的所有集团
	public List findAllBlocByAreaId(int areaId, int firstResult, int maxResults) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + areaId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(
					Restrictions.eq("orgLevel", OrgLevel.JITUAN_LEVEL)).add(
					Restrictions.eq("TArea.areaId", areaId));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //
	// 查找处于某个地区的所有集团的总数
	public int getCountAllBlocByAreaId(int areaId) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + areaId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.JITUAN_LEVEL))
					.createAlias("TArea", "o")
					.add(Restrictions.eq("o.areaId", areaId));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// ------------------------------模糊查询-----------------------------------//

	// 按机构名称进行模糊查询
	public List findMohuByName(String name, int orgLevel, int firstResult,
			int maxResults) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(Restrictions.eq("orgLevel", orgLevel)).add(
					Restrictions.like("name", name, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按机构名称进行模糊查询,查询符合条件的记录的个数
	public int getCountMohuByName(String name, int orgLevel) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria.add(Restrictions.eq("orgLevel", orgLevel)).add(
					Restrictions.like("name", name, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按机构名称和地区id进行模糊查询,即按名称模糊查找某个运维下的符合条件的组用户
	public List findMohuByNameAndAreaId(String name, int areaId,
			int firstResult, int maxResults) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.add(Restrictions.eq("TArea.areaId", areaId))
					.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按机构名称和地区id进行模糊查询,即按名称模糊查找某个运维下的符合条件的组用户的个数
	public int getCountMohuByNameAndAreaId(String name, int areaId) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.add(Restrictions.eq("TArea.areaId", areaId))
					.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按机构名称和地区id进行模糊查询,即按名称模糊查找某个运维下的符合条件的组用户
	public List findMohuByNameAndorgId(String name, int upOrgId,
			int firstResult, int maxResults) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.add(Restrictions.eq("TOrgainzation.orgId", upOrgId))
					.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按机构名称和地区id进行模糊查询,即按名称模糊查找某个运维下的符合条件的组用户的个数
	public int getCountMohuByNameAndorgId(String name, int upOrgId) {
		log.debug("finding TOrgainzation instance with property: " + name
				+ ", value: " + name);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TOrgainzation.class);
			detachedCriteria
					.add(Restrictions.eq("orgLevel", OrgLevel.GROUP_LEVEL))
					.add(Restrictions.eq("TOrgainzation.orgId", upOrgId))
					.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}