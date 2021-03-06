package ru.tumas.mymedialist.model;

import ru.tumas.mymedialist.model.dao.ListDAOImpl;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DiscoElevator
 */
public class ListDAOImplTest {

	private static EntityManagerFactory entityManagerFactory;
	private List<MediaListItem> persistedItems;

	public ListDAOImplTest() {
	}

	@Before
	public void setUp() {
		Map<String, String> props = new HashMap<>();
		props.put("javax.persistence.jdbc.url", "jdbc:h2:/~test;MVCC=true");
		entityManagerFactory = Persistence.createEntityManagerFactory("ru.tumas_MyMediaList_jar_0.0.1PU", props);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createQuery("DELETE FROM MediaListItem");
		q1.executeUpdate();
		em.getTransaction().commit();
		em.close();
		persistedItems = persistItems();
	}

	@After
	public void tearDown() {
		entityManagerFactory.close();
	}

	@Test
	public void testGetAll() {
		System.out.println("getAll");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<MediaListItem> result = instance.getAll();
		assertEquals(persistedItems, result);
	}

	@Test
	public void testGetByStatus() {
		System.out.println("getByStatus");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<MediaListItem> result = instance.get(MediaStatus.COMPLETED);
		assertEquals(1, result.size());
		assertEquals(persistedItems.get(2), result.get(0));
		result = instance.get(MediaStatus.DROPPED);
		assertEquals(Collections.emptyList(), result);
	}

	@Test
	public void testGetByType() {
		System.out.println("getByType");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<MediaListItem> result = instance.get(MediaType.MOVIE);
		assertEquals(Collections.emptyList(), result);
		result = instance.get(MediaType.ANIME);
		assertEquals(2, result.size());
	}

	@Test
	public void testGetByTypeAndStatus() {
		System.out.println("getByTypeAndStatus");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<MediaListItem> result = instance.get(MediaType.ANIME, MediaStatus.COMPLETED);
		assertEquals(1, result.size());
	}

	@Test
	public void testSaveItem() {
		System.out.println("saveItem");
		MediaListItem item = new MediaListItem();
		item.setOriginalName("save eng");
		item.setType(MediaType.DRAMA);
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		MediaListItem savedItem = instance.saveItem(item);
		persistedItems.add(savedItem);
		EntityManager em = entityManagerFactory.createEntityManager();
		MediaListItem result = em.find(MediaListItem.class, savedItem.getId());
		assertEquals(savedItem.getId(), result.getId());
	}

	@Test
	public void testSaveItems() {
		System.out.println("saveItems");
		List<MediaListItem> items = new LinkedList<>();
		MediaListItem item = new MediaListItem();
		item.setOriginalName("saveAll eng");
		item.setType(MediaType.DRAMA);
		items.add(item);
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<MediaListItem> savedItems = instance.saveItems(items);
		persistedItems.addAll(savedItems);
		EntityManager em = entityManagerFactory.createEntityManager();
		MediaListItem result = em.find(MediaListItem.class, savedItems.get(0).getId());
		assertEquals(savedItems.get(0), result);
	}

	private List<MediaListItem> persistItems() {
		MediaListItem item1 = new MediaListItem();
		item1.setOriginalName("item1 eng");
		item1.setEpisodes(10);
		item1.setStartDate(new Date());
		item1.setStatus(MediaStatus.WATCHING);
		item1.setType(MediaType.ANIME);
		MediaListItem item2 = new MediaListItem();
		item2.setOriginalName("item2 eng");
		item2.setEpisodes(12);
		item2.setProgress(1);
		item2.setEndDate(new Date());
		item2.setStatus(MediaStatus.WATCHING);
		item2.setType(MediaType.TV_SHOW);
		MediaListItem item3 = new MediaListItem();
		item3.setOriginalName("item3 eng");
		item3.setEpisodes(10);
		item3.setStartDate(new Date());
		item3.setStatus(MediaStatus.COMPLETED);
		item3.setType(MediaType.ANIME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(item1);
		em.persist(item2);
		em.persist(item3);
		em.getTransaction().commit();
		em.refresh(item1);
		em.refresh(item2);
		em.refresh(item3);
		em.close();
		List<MediaListItem> items = new LinkedList<>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		return items;
	}

}
