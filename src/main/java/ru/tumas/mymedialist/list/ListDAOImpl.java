package ru.tumas.mymedialist.list;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
	public void saveItem(ListItem item) {
		if (item == null) {
			return;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		ListItem oldItem = em.find(ListItem.class, item.getId());
		if (oldItem != null) {
			em.merge(item);
		} else {
			em.persist(item);
		}
		em.flush();
		em.close();
	}

	@Override
	public void saveItems(List<ListItem> items) {
		if ((items == null) || items.isEmpty()) {
			return;
		}
		EntityManager em = entityManagerFactory.createEntityManager();
		for (ListItem item : items) {
			ListItem oldItem = em.find(ListItem.class, item.getId());
			if (oldItem != null) {
				em.merge(item);
			} else {
				em.persist(item);
			}
			em.flush();
		}
		em.close();
	}
}
