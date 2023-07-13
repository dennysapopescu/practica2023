package com.ssn.practica.personal.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateDemo2 {
	public static void main(String[] args) throws Exception {
		HibernateDemo2 demo = new HibernateDemo2();
		demo.run();
	}

	private SessionFactory sessionFactory;

	private void run() throws Exception {
		setUp();
		saveEntities();
		updateEntity();
		deleteEntity();
		queryEntities();
		queryOneEntity();
	}

	private void deleteEntity() {
		Session session = sessionFactory.openSession();

		session.getTransaction().begin();

		TypedQuery<Course> query = session.createQuery("from Course where name = :nameParameter", Course.class);
		query.setParameter("nameParameter", "Dummy");

		Course course = query.getSingleResult();
		session.delete(course);

		session.getTransaction().commit();
		session.close();
	}

	private void updateEntity() {
		EntityManager entityManager = sessionFactory.createEntityManager();

		entityManager.getTransaction().begin();

		TypedQuery<Course> query = entityManager.createQuery("from Course where name = :nameParameter", Course.class);
		query.setParameter("nameParameter", "Romana");

		Course course = query.getSingleResult();
		course.setName("Limba romana");

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private void queryOneEntity() {
		EntityManager entityManager = sessionFactory.createEntityManager();

		TypedQuery<Course> query = entityManager.createQuery("from Course where name = :nameParameter", Course.class);
		query.setParameter("nameParameter", "Matematica");

		Course result = query.getSingleResult();
		result.getTrainees().toString();
		entityManager.close();

		System.out.println(result.getName());
		System.out.println(result.getTrainees());

	}

	private void queryEntities() {
		EntityManager entityManager = sessionFactory.createEntityManager();
		List<Course> result = entityManager.createQuery("from Course", Course.class).getResultList();

		for (Course c : result) {
			System.out.println(c);
		}

		entityManager.close();
	}

	private void saveEntities() {
		EntityManager entityManager = sessionFactory.createEntityManager();

		entityManager.getTransaction().begin();

		Trainee trainee1 = new Trainee("Ghita", 20);
		entityManager.persist(trainee1);
		Trainee trainee2 = new Trainee("Ion", 21);
		entityManager.persist(trainee2);

		Course course1 = new Course("Romana");
		entityManager.persist(course1);
		Course course2 = new Course("Matematica");
		entityManager.persist(course2);
		Course course3 = new Course("Dummy");
		entityManager.persist(course3);

		trainee1.getCourses().add(course2);
		trainee2.getCourses().add(course1);

		Evaluation ev1 = new Evaluation(10, course1, trainee1);
		Evaluation ev2 = new Evaluation(9, course2, trainee2);

		entityManager.persist(ev1);
		entityManager.persist(ev2);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

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
