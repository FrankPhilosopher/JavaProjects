package com.rcp.wxh.sql.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class MySessionFactory {

	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";

	private static final SessionFactory sessionFactory;

	static {
		sessionFactory = new AnnotationConfiguration().configure(CONFIG_FILE_LOCATION).buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
