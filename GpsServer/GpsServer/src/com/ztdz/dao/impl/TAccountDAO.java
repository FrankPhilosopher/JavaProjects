package com.ztdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TAccount;

/**
 * A data access object (DAO) providing persistence and search support for
 * TAccount entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ztdz.dao.TAccount
 * @author MyEclipse Persistence Tools
 */

public class TAccountDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TAccountDAO.class);
	// property constants
	public static final String PAIDER = "paider";
	public static final String EXPENSE = "expense";
	public static final String REMARK = "remark";

	protected void initDao() {
		// do nothing
	}

	public void save(TAccount transientInstance) {
		log.debug("saving TAccount instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TAccount persistentInstance) {
		log.debug("deleting TAccount instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TAccount findById(java.lang.Integer id) {
		log.debug("getting TAccount instance with id: " + id);
		try {
			TAccount instance = (TAccount) getHibernateTemplate().get(
					"com.ztdz.pojo.TAccount", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TAccount> findByExample(TAccount instance) {
		log.debug("finding TAccount instance by example");
		try {
			List<TAccount> results = (List<TAccount>) getHibernateTemplate()
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
		log.debug("finding TAccount instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TAccount instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaider(Object paider, int firstResult, int maxResults) {
		return findByProperty(PAIDER, paider, firstResult, maxResults);
	}

	public int getCountByPaider(Object paider) {
		return getCountByProperty(PAIDER, paider);
	}

	public List findByExpense(Object expense, int firstResult, int maxResults) {
		return findByProperty(EXPENSE, expense, firstResult, maxResults);
	}

	public int getCountByExpense(Object expense) {
		return getCountByProperty(EXPENSE, expense);
	}

	public List findByRemark(Object remark, int firstResult, int maxResults) {
		return findByProperty(REMARK, remark, firstResult, maxResults);
	}

	public int getCountByRemark(Object remark) {
		return getCountByProperty(REMARK, remark);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TAccount instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TAccount instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TAccount merge(TAccount detachedInstance) {
		log.debug("merging TAccount instance");
		try {
			TAccount result = (TAccount) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TAccount instance) {
		log.debug("attaching dirty TAccount instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TAccount instance) {
		log.debug("attaching clean TAccount instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TAccountDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TAccountDAO) ctx.getBean("TAccountDAO");
	}

	// 此处增加一个方法 按orgid查询其下的所有账单(分页)
	//
	//
	public List findByOrgId(int orgId, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", orgId: "
				+ orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 按orgId查询其下的所有账单的总数
	//
	//
	public int getCountByOrgId(int orgId) {
		log.debug("finding TRequest instance with property: " + ", orgId: "
				+ orgId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.createAlias("TOrgainzation", "o").add(
					Restrictions.eq("o.orgId", orgId));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时期内所有的账单信息(分页)
	//
	//
	public List findAllByTime(Date timeStart, Date timeEnd, int firstResult,
			int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(
					Restrictions.between("paiddate", timeStart, timeEnd))
					.addOrder(Order.desc("paiddate"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时期内所有的账单信息的总数
	//
	//
	public int getCountAllByTime(Date timeStart, Date timeEnd) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(
					Restrictions.between("paiddate", timeStart, timeEnd))
					.addOrder(Order.desc("paiddate"));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时期内某个orgid下的所有账单信息（分页）
	//
	//
	public List findByOrgIdByTime(int orgId, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId))
					.add(Restrictions.between("paiddate", timeStart, timeEnd))
					.addOrder(Order.desc("paiddate"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时期内某个orgid下的所有账单信息的总数
	//
	//
	public int getCountByOrgIdByTime(int orgId, Date timeStart, Date timeEnd) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.createAlias("TOrgainzation", "o").add(
					Restrictions.eq("o.orgId", orgId)).add(
					Restrictions.between("paiddate", timeStart, timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时间内某一付账人 付过账的所有账单（分页）
	//
	//
	public List findByPaiderByTime(String paider, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq("paider", paider)).add(
					Restrictions.between("paiddate", timeStart, timeEnd))
					.addOrder(Order.desc("paiddate"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时间内某一付账人 付过账的所有账单的总数
	//
	//
	public int getCountByPaiderByTime(String paider, Date timeStart,
			Date timeEnd) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.add(Restrictions.eq("paider", paider)).add(
					Restrictions.between("paiddate", timeStart, timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时间为某一级别的机构充值（如集团）的所有账单（分页）
	//
	//
	public List findByOrgLevel(int orgLevel, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("orgLevel " + orgLevel);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.createAlias("TOrgainzation", "o").add(
					Restrictions.eq("o.orgLevel", orgLevel)).add(
					Restrictions.between("paiddate", timeStart, timeEnd))
					.addOrder(Order.desc("paiddate"));

			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 此处增加一个方法 查询一段时间为某一级别的机构充值（如集团）的所有账单的总数
	//
	//
	public int getCountByOrgLevel(int orgLevel, Date timeStart, Date timeEnd) {
		log.debug("orgLevel " + orgLevel);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TAccount.class);
			detachedCriteria.createAlias("TOrgainzation", "o").add(
					Restrictions.eq("o.orgLevel", orgLevel)).add(
					Restrictions.between("paiddate", timeStart, timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
