package com.ztdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TPosition;

/**
 * A data access object (DAO) providing persistence and search support for
 * TPosition entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ztdz.dao.TPosition
 * @author MyEclipse Persistence Tools
 */

public class TPositionDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TPositionDAO.class);
	// property constants
	public static final String SIM = "sim";
	public static final String LOCATION_MODEL = "locationModel";
	public static final String STATION_ID = "stationId";
	public static final String PLOT_ID = "plotId";
	public static final String LATI_DIRECTION = "latiDirection";
	public static final String LATITUDE = "latitude";
	public static final String LONG_DIRECTION = "longDirection";
	public static final String LONGITUDE = "longitude";
	public static final String DIRECTION = "direction";
	public static final String SPEED = "speed";
	public static final String LATOFFSET = "latoffset";
	public static final String LNGOFFSET = "lngoffset";
	public static final String STATUS = "status";
	public static final String WORKTIME = "worktime";
	public static final String ELEPRESS = "elepress";
	public static final String SIGNAL = "signal";

	protected void initDao() {
		// do nothing
	}

	public void save(TPosition transientInstance) {
		log.debug("saving TPosition instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TPosition persistentInstance) {
		log.debug("deleting TPosition instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TPosition findById(Long id) {
		log.debug("getting TPosition instance with id: " + id);
		try {
			TPosition instance = (TPosition) getHibernateTemplate().get(
					"com.ztdz.pojo.TPosition", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TPosition> findByExample(TPosition instance) {
		log.debug("finding TPosition instance by example");
		try {
			List<TPosition> results = (List<TPosition>) getHibernateTemplate()
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
		log.debug("finding TPosition instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TPosition instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySim(Object sim, int firstResult, int maxResults) {
		return findByProperty(SIM, sim, firstResult, maxResults);
	}

	public List findByLocationModel(Object locationModel, int firstResult,
			int maxResults) {
		return findByProperty(LOCATION_MODEL, locationModel, firstResult,
				maxResults);
	}

	public List findByStationId(Object stationId, int firstResult,
			int maxResults) {
		return findByProperty(STATION_ID, stationId, firstResult, maxResults);
	}

	public List findByPlotId(Object plotId, int firstResult, int maxResults) {
		return findByProperty(PLOT_ID, plotId, firstResult, maxResults);
	}

	public List findByLatiDirection(Object latiDirection, int firstResult,
			int maxResults) {
		return findByProperty(LATI_DIRECTION, latiDirection, firstResult,
				maxResults);
	}

	public List findByLatitude(Object latitude, int firstResult, int maxResults) {
		return findByProperty(LATITUDE, latitude, firstResult, maxResults);
	}

	public List findByLongDirection(Object longDirection, int firstResult,
			int maxResults) {
		return findByProperty(LONG_DIRECTION, longDirection, firstResult,
				maxResults);
	}

	public List findByLongitude(Object longitude, int firstResult,
			int maxResults) {
		return findByProperty(LONGITUDE, longitude, firstResult, maxResults);
	}

	public List findByDirection(Object direction, int firstResult,
			int maxResults) {
		return findByProperty(DIRECTION, direction, firstResult, maxResults);
	}

	public List findBySpeed(Object speed, int firstResult, int maxResults) {
		return findByProperty(SPEED, speed, firstResult, maxResults);
	}

	public List findByLatoffset(Object latoffset, int firstResult,
			int maxResults) {
		return findByProperty(LATOFFSET, latoffset, firstResult, maxResults);
	}

	public List findByLngoffset(Object lngoffset, int firstResult,
			int maxResults) {
		return findByProperty(LNGOFFSET, lngoffset, firstResult, maxResults);
	}

	public List findByStatus(Object status, int firstResult, int maxResults) {
		return findByProperty(STATUS, status, firstResult, maxResults);
	}

	public List findByWorktime(Object worktime, int firstResult, int maxResults) {
		return findByProperty(WORKTIME, worktime, firstResult, maxResults);
	}

	public List findByElepress(Object elepress, int firstResult, int maxResults) {
		return findByProperty(ELEPRESS, elepress, firstResult, maxResults);
	}

	public List findBySignal(Object signal, int firstResult, int maxResults) {
		return findByProperty(SIGNAL, signal, firstResult, maxResults);
	}

	public int getCountBySim(Object sim) {
		return getCountByProperty(SIM, sim);
	}

	public int getCountByLocationModel(Object locationModel) {
		return getCountByProperty(LOCATION_MODEL, locationModel);
	}

	public int getCountByStationId(Object stationId) {
		return getCountByProperty(STATION_ID, stationId);
	}

	public int getCountByPlotId(Object plotId) {
		return getCountByProperty(PLOT_ID, plotId);
	}

	public int getCountByLatiDirection(Object latiDirection) {
		return getCountByProperty(LATI_DIRECTION, latiDirection);
	}

	public int getCountByLatitude(Object latitude) {
		return getCountByProperty(LATITUDE, latitude);
	}

	public int getCountByLongDirection(Object longDirection) {
		return getCountByProperty(LONG_DIRECTION, longDirection);
	}

	public int getCountByLongitude(Object longitude) {
		return getCountByProperty(LONGITUDE, longitude);
	}

	public int getCountByDirection(Object direction) {
		return getCountByProperty(DIRECTION, direction);
	}

	public int getCountBySpeed(Object speed) {
		return getCountByProperty(SPEED, speed);
	}

	public int getCountByLatoffset(Object latoffset) {
		return getCountByProperty(LATOFFSET, latoffset);
	}

	public int getCountByLngoffset(Object lngoffsets) {
		return getCountByProperty(LNGOFFSET, lngoffsets);
	}

	public int getCountByStatus(Object status) {
		return getCountByProperty(STATUS, status);
	}

	public int getCountByWorktime(Object worktime) {
		return getCountByProperty(WORKTIME, worktime);
	}

	public int getCountByElepress(Object elepress) {
		return getCountByProperty(ELEPRESS, elepress);
	}

	public int getCountBySignal(Object signal) {
		return getCountByProperty(SIGNAL, signal);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TPosition instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TPosition instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TPosition merge(TPosition detachedInstance) {
		log.debug("merging TPosition instance");
		try {
			TPosition result = (TPosition) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TPosition instance) {
		log.debug("attaching dirty TPosition instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TPosition instance) {
		log.debug("attaching clean TPosition instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TPositionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TPositionDAO) ctx.getBean("TPositionDAO");
	}

	// 查找某个终端的所有历史定位信息(分页)
	//
	//
	public List findBySim(String sim, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", sim: " + sim);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.createAlias("TTerminal", "t").add(
					Restrictions.eq("t.sim", sim));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 查找某个终端的所有历史定位信息的总数
	//
	//
	public int getCountBySim(String sim, int firstResult, int maxResults) {
		log.debug("finding TRequest instance with property: " + ", sim: " + sim);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.createAlias("TTerminal", "t").add(
					Restrictions.eq("t.sim", sim));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 查找某个终端某个时间段的历史信息(分页)
	//
	//
	public List findBySimBetween(String sim, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.add(Restrictions.between("PTime", timeStart, timeEnd))
			                .add(Restrictions.eq("sim", sim))
			                .add(Restrictions.gt("speed", (double)2.0))
					         .addOrder(Order.asc("id"));
			List list = getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
			for(int i=0;i<list.size();i++)getHibernateTemplate().evict(list.get(i));
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//查找停车记录
	public List findStopBySimBetween(String sim, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.add(Restrictions.between("PTime", timeStart, timeEnd))
			                .add(Restrictions.eq("sim", sim))
			                .add(Restrictions.eq("speed", (double)0))
					         .addOrder(Order.asc("id"));
			List list = getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
			for(int i=0;i<list.size();i++)getHibernateTemplate().evict(list.get(i));
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	// 查找某个终端某个时间段的历史信息的总数
	//
	//
	public int getCountBySimBetween(String sim, Date timeStart, Date timeEnd) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TPosition.class);
			detachedCriteria.add(Restrictions.eq("t.sim", sim))
					.add(Restrictions.between("PTime", timeStart, timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}