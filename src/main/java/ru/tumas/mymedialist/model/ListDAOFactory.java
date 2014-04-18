package ru.tumas.mymedialist.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ListDAOFactory {

	private static EntityManagerFactory entityManagerFactory;

	public static ListDAO createListDAO() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("ru.tumas_MyMediaList_jar_0.0.1PU");
		}
		return new ListDAOImpl(entityManagerFactory);
	}
}
