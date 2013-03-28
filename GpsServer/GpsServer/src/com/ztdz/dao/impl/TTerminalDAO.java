package com.ztdz.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TTerminal;

/**
 * A data access object (DAO) providing persistence and search support for TTerminal entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ztdz.dao.TTerminal
 * @author MyEclipse Persistence Tools
 */

public class TTerminalDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TTerminalDAO.class);
	// property constants
	public static final String SIM = "sim";
	public static final String PHONE = "phone";
	public static final String MODEL = "model";
	public static final String CARNUMBER = "carnumber";
	public static final String GAS = "gas";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String PRIVILEGE1 = "privilege1";
	public static final String PRIVILEGE2 = "privilege2";
	public static final String PRIVILEGE3 = "privilege3";
	public static final String _PPERIOD = "PPeriod";
	public static final String BASEKILO = "basekilo";
	public static final String MAINTENANCE = "maintenance";
	public static final String USERNAME = "username";
	public static final String CELLPHONE = "cellphone";
	public static final String AREA_ID = "areaId";
	public static final String PRINCIPAL = "principal";
	public static final String REMARK = "remark";

	protected void initDao() {
		// do nothing
	}

	public void save(TTerminal transientInstance) {
		log.debug("saving TTerminal instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TTerminal persistentInstance) {
		log.debug("deleting TTerminal instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TTerminal findById(java.lang.Integer id) {
		log.debug("getting TTerminal instance with id: " + id);
		try {
			TTerminal instance = (TTerminal) getHibernateTemplate().get("com.ztdz.pojo.TTerminal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TTerminal> findByExample(TTerminal instance) {
		log.debug("finding TTerminal instance by example");
		try {
			List<TTerminal> results = (List<TTerminal>) getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	// 根据属性键值对搜索，分页或者不分页
	public List findByProperty(String propertyName, Object value, int firstResult, int maxResults) {
		log.debug("finding TTerminal instance with property: " + propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 根据属性键值对搜索，得到总数
	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TTerminal instance with property: " + propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// ////////////////////////
	// ------hjw-----------///////////////////////////////////
	// 根据属性键值对搜索，分页或者不分页
	public List findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults) {
		log.debug("finding TTerminal instance with detachedCriteria: " + detachedCriteria);
		try {
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 根据属性键值对搜索，得到总数
	public int getCountByCriteria(DetachedCriteria detachedCriteria) {
		log.debug("finding TTerminal instance with detachedCriteria: " + detachedCriteria);
		try {
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// ////////////////////////------hjw-----------///////////////////////////////////

	public List findBySim(Object sim, int firstResult, int maxResults) {
		return findByProperty(SIM, sim, firstResult, maxResults);
	}

	public List findByPhone(Object phone, int firstResult, int maxResults) {
		return findByProperty(PHONE, phone, firstResult, maxResults);
	}

	public List findByModel(Object model, int firstResult, int maxResults) {
		return findByProperty(MODEL, model, firstResult, maxResults);
	}

	public List findByCarnumber(Object carnumber, int firstResult, int maxResults) {
		return findByProperty(CARNUMBER, carnumber, firstResult, maxResults);
	}

	public List findByGas(Object gas, int firstResult, int maxResults) {
		return findByProperty(GAS, gas, firstResult, maxResults);
	}

	public List findByStartTime(Object startTime, int firstResult, int maxResults) {
		return findByProperty(START_TIME, startTime, firstResult, maxResults);
	}

	public List findByEndTime(Object endTime, int firstResult, int maxResults) {
		return findByProperty(END_TIME, endTime, firstResult, maxResults);
	}

	public List findByPrivilege1(Object privilege1, int firstResult, int maxResults) {
		return findByProperty(PRIVILEGE1, privilege1, firstResult, maxResults);
	}

	public List findByPrivilege2(Object privilege2, int firstResult, int maxResults) {
		return findByProperty(PRIVILEGE2, privilege2, firstResult, maxResults);
	}

	public List findByPrivilege3(Object privilege3, int firstResult, int maxResults) {
		return findByProperty(PRIVILEGE3, privilege3, firstResult, maxResults);
	}

	public List findByPPeriod(Object PPeriod, int firstResult, int maxResults) {
		return findByProperty(_PPERIOD, PPeriod, firstResult, maxResults);
	}

	public List findByBasekilo(Object basekilo, int firstResult, int maxResults) {
		return findByProperty(BASEKILO, basekilo, firstResult, maxResults);
	}

	public List findByMaintenance(Object maintenance, int firstResult, int maxResults) {
		return findByProperty(MAINTENANCE, maintenance, firstResult, maxResults);
	}

	public List findByUsername(Object username, int firstResult, int maxResults) {
		return findByProperty(USERNAME, username, firstResult, maxResults);
	}

	public List findByCellphone(Object cellphone, int firstResult, int maxResults) {
		return findByProperty(CELLPHONE, cellphone, firstResult, maxResults);
	}

	public List findByAreaId(Object areaId, int firstResult, int maxResults) {
		return findByProperty(AREA_ID, areaId, firstResult, maxResults);
	}

	public List findByPrincipal(Object principal, int firstResult, int maxResults) {
		return findByProperty(PRINCIPAL, principal, firstResult, maxResults);
	}

	public List findByRemark(Object remark, int firstResult, int maxResults) {
		return findByProperty(REMARK, remark, firstResult, maxResults);
	}

	public int getCountBySim(Object sim) {
		return getCountByProperty(SIM, sim);
	}

	public int getCountByPhone(Object phone) {
		return getCountByProperty(PHONE, phone);
	}

	public int getCountByModel(Object model) {
		return getCountByProperty(MODEL, model);
	}

	public int getCountByCarnumber(Object carnumber) {
		return getCountByProperty(CARNUMBER, carnumber);
	}

	public int getCountByGas(Object gas) {
		return getCountByProperty(GAS, gas);
	}

	public int getCountByStartTime(Object startTime) {
		return getCountByProperty(START_TIME, startTime);
	}

	public int getCountByEndTime(Object endTime) {
		return getCountByProperty(END_TIME, endTime);
	}

	public int getCountByPrivilege1(Object privilege1) {
		return getCountByProperty(PRIVILEGE1, privilege1);
	}

	public int getCountByPrivilege2(Object privilege2) {
		return getCountByProperty(PRIVILEGE2, privilege2);
	}

	public int getCountByPrivilege3(Object privilege3) {
		return getCountByProperty(PRIVILEGE3, privilege3);
	}

	public int getCountByPPeriod(Object PPeriod) {
		return getCountByProperty(_PPERIOD, PPeriod);
	}

	public int getCountByBasekilo(Object basekilo) {
		return getCountByProperty(BASEKILO, basekilo);
	}

	public int getCountByMaintenance(Object maintenance) {
		return getCountByProperty(MAINTENANCE, maintenance);
	}

	public int getCountByUsername(Object username) {
		return getCountByProperty(USERNAME, username);
	}

	public int getCountByCellphone(Object cellphone) {
		return getCountByProperty(CELLPHONE, cellphone);
	}

	public int getCountByAreaId(Object areaId) {
		return getCountByProperty(AREA_ID, areaId);
	}

	public int getCountByPrincipal(Object principal) {
		return getCountByProperty(PRINCIPAL, principal);
	}

	public int getCountByRemark(Object remark) {
		return getCountByProperty(REMARK, remark);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TTerminal instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TTerminal instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);

			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TTerminal merge(TTerminal detachedInstance) {
		log.debug("merging TTerminal instance");
		try {
			TTerminal result = (TTerminal) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TTerminal instance) {
		log.debug("attaching dirty TTerminal instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TTerminal instance) {
		log.debug("attaching clean TTerminal instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TTerminalDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TTerminalDAO) ctx.getBean("TTerminalDAO");
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息(分页)
	public List findByOrg(Integer orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数
	public int getCountByOrg(Integer orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.createAlias("TOrgainzation", "o").add(Restrictions.eq("o.orgId", orgId));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //-----hjw
	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的搜索条件(分页)
	public List findMohuByOrgAndSim(String sim, int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("sim", sim, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的搜索条件
	public int getCountMohuByOrgAndSim(String sim, int orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("sim", sim, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的用户名称(分页)
	public List findMohuByOrgAndUserName(String username, int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("username", username, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的用户名称
	public int getCountMohuByOrgAndUserName(String username, int orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("username", username, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的车辆编号(分页)
	public List findMohuByOrgAndCarNumber(String carnumber, int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("carnumber", carnumber, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的车辆编号
	public int getCountMohuByOrgAndCarNumber(String carnumber, int orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("carnumber", carnumber, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的债权人姓名(分页)
	public List findMohuByOrgAndPrincipal(String principal, int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("principal", principal, MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的债权人姓名
	public int getCountMohuByOrgAndPrincipal(String principal, int orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: " + orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId)).add(Restrictions.like("principal", principal, MatchMode.ANYWHERE));
			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// //------hjw

	// 增加一个方法 按类型ID查找同一类型的所有终端信息(分页)
	//
	//
	public List findByCarTypeId(Integer carTypeId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", carTypeId: " + carTypeId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.add(Restrictions.eq("TCarInfo.carTypeId", carTypeId));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 按类型ID查找同一类型的所有终端的总数
	//
	//
	public int getCountByCarTypeId(Integer carTypeId) {
		log.debug("finding TRequest instance with property: " + ", carTypeId: " + carTypeId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.createAlias("TCarInfo", "c").add(Restrictions.eq("c.carTypeId", carTypeId));

			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 按类型名称（如：挖掘机，推土机）查找同一类型的所有终端信息(分页)
	//
	//
	public List findByTypeName(String typeName, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", typeName: " + typeName);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.createAlias("TCarInfo", "c").add(Restrictions.eq("c.typeName", typeName));
			return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个方法 按类型名称（如：挖掘机，推土机）查找同一类型的所有终端的总数
	//
	//
	public int getCountByTypeName(String typeName) {
		log.debug("finding TRequest instance with property: " + ", typeName: " + typeName);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
			detachedCriteria.createAlias("TCarInfo", "c").add(Restrictions.eq("c.typeName", typeName));

			return ((Integer) getHibernateTemplate().findByCriteria(detachedCriteria.setProjection(Projections.rowCount())).get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	// 增加一个方法 按 集团ID查找同一集团的所有终端信息(分页)
	//
	//
	public List findByJituanId(int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", typeName: "
				+ orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TTerminal.class);
			detachedCriteria.createAlias("TOrgainzation", "o1").add(
					Restrictions.eq("o1.TOrgainzation.orgId", orgId));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	// 增加一个方法 按 集团ID查找同一集团的所有终端信息的总数
	//
	//
	public int getCountByJituanId(int orgId) {
		log.debug("finding TRequest instance with property: " + ", typeName: "
				+ orgId);
		
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
			.forClass(TTerminal.class);
			detachedCriteria.createAlias(
					"TOrgainzation", "o1").add(
							Restrictions.eq("o1.TOrgainzation.orgId", orgId));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}