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
import ru.tumas.mymedialist.model.MediaListItem;
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
	public List<MediaListItem> getAll() {
		EntityManager em = entityManagerFactory.createEntityManager();
		List<MediaListItem> items = em.createNamedQuery("ListItem.getAll", MediaListItem.class).getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<MediaListItem> getByStatus(MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("ListItem.getByStatus", MediaListItem.class);
		query.setParameter("status", status);
		List<MediaListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<MediaListItem> getByType(MediaType type) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("ListItem.getByType", MediaListItem.class);
		query.setParameter("type", type);
		List<MediaListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<MediaListItem> getByTypeAndStatus(MediaType type, MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("ListItem.getByTypeAndStatus", MediaListItem.class);
		query.setParameter("type", type);
		query.setParameter("status", status);
		List<MediaListItem> items = query.getResultList();
		em.close();
		return Collections.unmodifiableList(items);
	}

	@Override
	public MediaListItem saveItem(MediaListItem item) {
		if (item == null) {
			return null;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		MediaListItem result = em.merge(item);
		em.getTransaction().commit();
		em.close();
		return result;
	}

	@Override
	public List<MediaListItem> saveItems(List<MediaListItem> items) {
		if ((items == null) || items.isEmpty()) {
			return null;
		}
		List<MediaListItem> result = new LinkedList<>();
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		for (MediaListItem item : items) {
			result.add(em.merge(item));
		}
		em.getTransaction().commit();
		em.close();
		return result;
	}
}
