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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

public class ListDAOImpl implements ListDAO {

	private static final Logger logger = LoggerFactory.getLogger(ListDAOImpl.class);

	private final EntityManagerFactory entityManagerFactory;

	public ListDAOImpl(EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException();
		}
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public List<MediaListItem> getAll() {
		EntityManager em = entityManagerFactory.createEntityManager();
		List<MediaListItem> items = new ArrayList<>();
		try {
			logger.debug("Search all...");
			items = em.createNamedQuery("MediaListItem.getAll", MediaListItem.class).getResultList();
			logger.debug("Found " + items.size() + " items");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return items;
	}

	@Override
	public List<MediaListItem> get(MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("MediaListItem.getByStatus", MediaListItem.class);
		query.setParameter("status", status);
		List<MediaListItem> items = new ArrayList<>();
		try {
			logger.debug("Searching by status: " + status.toString());
			items = query.getResultList();
			logger.debug("Found " + items.size() + " items");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return items;
	}

	@Override
	public List<MediaListItem> get(MediaType type) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("MediaListItem.getByType", MediaListItem.class);
		query.setParameter("type", type);
		List<MediaListItem> items = new ArrayList<>();
		try {
			logger.debug("Searching by type: " + type.toString());
			items = query.getResultList();
			logger.debug("Found " + items.size() + " items");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return items;
	}

	@Override
	public List<MediaListItem> get(MediaType type, MediaStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("MediaListItem.getByTypeAndStatus", MediaListItem.class);
		query.setParameter("type", type);
		query.setParameter("status", status);
		List<MediaListItem> items = new ArrayList<>();
		try {
			logger.debug("Searching by type and status: " + type.toString() + ", " + status.toString());
			items = query.getResultList();
			logger.debug("Found " + items.size() + " items");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return items;
	}

	@Override
	public MediaListItem saveItem(MediaListItem item) {
		if (item == null) {
			return null;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		MediaListItem result = null;
		try {
			logger.debug("Saving item: " + item.toString());
			em.getTransaction().begin();
			result = em.merge(item);
			em.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public List<MediaListItem> saveItems(List<MediaListItem> items) {
		if ((items == null) || items.isEmpty()) {
			return null;
		}
		List<MediaListItem> result = new ArrayList<>();
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			for (MediaListItem item : items) {
				result.add(em.merge(item));
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public MediaListItem get(String originalName) {
		if ((originalName == null) || (originalName.isEmpty())) {
			return null;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<MediaListItem> query = em.createNamedQuery("MediaListItem.getByOriginalName", MediaListItem.class);
		query.setParameter("name", originalName);
		List<MediaListItem> items = null;
		try {
			logger.debug("Searching by originalName: " + originalName);
			items = query.getResultList();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			em.close();
		}
		MediaListItem result = null;
		if ((items != null) && !items.isEmpty()) {
			result = items.get(0);
		}
		logger.debug("Found " + items.size() + "result: " + result);
		return result;
	}
}
