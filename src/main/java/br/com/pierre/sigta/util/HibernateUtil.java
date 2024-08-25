package br.com.pierre.sigta.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static EntityManagerFactory factory = null;
	
	static {
		init();
	}
	
	private static void init() {
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory("sigta");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}
	
	public static void shutdown() {
		if (factory != null) {
			factory.close();
		}
	}
}
