package com.ssn.practica.personal.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class WithSessionAndTransaction {

	private static SessionFactory sessionFactory;

	public void run() {
		try {
			if (sessionFactory == null) {
				setUp();
			}

			Session session = sessionFactory.openSession();
			session.getTransaction().begin();

			doAction(session);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void doAction(Session session);

	protected void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}
}
