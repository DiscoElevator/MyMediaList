/* 
 * Copyright (C) 2014 Maxim Tumas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.tumas.mymedialist.model.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import ru.tumas.mymedialist.model.ListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

public class ListDAOImpl implements ListDAO {

	private final EntityManagerFactory entityManagerFactory;

	public ListDAOImpl() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ru.tumas_MyMediaList_jar_0.0.1PU");
	}

	public ListDAOImpl(EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException();
		}
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public List<ListItem> getAll() {
		EntityManager em = entityManagerFactory.createEntityManager();
		List<ListItem> items = em.createNamedQuery("ListItem.getAll", ListItem.class).getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<ListItem> getByStatus(MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<ListItem> query = em.createNamedQuery("ListItem.getByStatus", ListItem.class);
		query.setParameter("status", status);
		List<ListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<ListItem> getByType(MediaType type) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<ListItem> query = em.createNamedQuery("ListItem.getByType", ListItem.class);
		query.setParameter("type", type);
		List<ListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<ListItem> getByTypeAndStatus(MediaType type, MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<ListItem> query = em.createNamedQuery("ListItem.getByTypeAndStatus", ListItem.class);
		query.setParameter("type", type);
		query.setParameter("status", status);
		List<ListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public ListItem saveItem(ListItem item) {
		if (item == null) {
			return null;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		ListItem result = em.merge(item);
		em.getTransaction().commit();
		em.close();
		return result;
	}

	@Override
	public List<ListItem> saveItems(List<ListItem> items) {
		if ((items == null) || items.isEmpty()) {
			return null;
		}
		List<ListItem> result = new LinkedList<>();
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		for (ListItem item : items) {
			result.add(em.merge(item));
		}
		em.getTransaction().commit();
		em.close();
		return result;
	}
}
