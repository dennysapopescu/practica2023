package com.ssn.practica.personal.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.practica.personal.utils.WithSessionAndTransaction;

public class HibernateDemo3 {
	public static void main(String[] args) throws Exception {
		HibernateDemo3 demo = new HibernateDemo3();
		demo.run();
	}

	private void run() throws Exception {
		saveEntities();
		updateEntity();
		deleteEntity();
		queryEntities();
		queryOneEntity();
	}

	private void deleteEntity() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				TypedQuery<Course> query = session.createQuery("from Course where name = :nameParameter", Course.class);
				query.setParameter("nameParameter", "Dummy");

				Course course = query.getSingleResult();
				session.delete(course);
			}
		}.run();
	}

	private void updateEntity() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {

				TypedQuery<Course> query = session.createQuery("from Course where name = :nameParameter", Course.class);
				query.setParameter("nameParameter", "Romana");

				Course course = query.getSingleResult();
				course.setName("Limba romana");

			}
		}.run();
	}

	private void queryOneEntity() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {

				Query<Course> query = session.createQuery("from Course where name = :nameParameter", Course.class);
				query.setParameter("nameParameter", "wqwqw");

				Course result = query.uniqueResult();
				if (result != null) {
					result.getTrainees().toString();
					// session.close();

					System.out.println(result.getName());
					System.out.println(result.getTrainees());
				} else {
					System.out.println("Course not found");
				}

			}
		}.run();
	}

	private void queryEntities() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				List<Course> result = session.createQuery("from Course", Course.class).getResultList();

				for (Course c : result) {
					System.out.println(c);
				}
			}
		}.run();
	}

	private void saveEntities() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {

				Trainee trainee1 = new Trainee("Ghita", 20);
				session.persist(trainee1);
				Trainee trainee2 = new Trainee("Ion", 21);
				session.persist(trainee2);

				Course course1 = new Course("Romana");
				session.persist(course1);
				Course course2 = new Course("Matematica");
				session.persist(course2);
				Course course3 = new Course("Dummy");
				session.persist(course3);

				trainee1.getCourses().add(course2);
				trainee2.getCourses().add(course1);

				Evaluation ev1 = new Evaluation(10, course1, trainee1);
				Evaluation ev2 = new Evaluation(9, course2, trainee2);

				session.persist(ev1);
				session.persist(ev2);

			}
		}.run();
	}

}
