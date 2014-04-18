package ru.tumas.mymedialist.model;

import ru.tumas.mymedialist.model.MediaType;
import ru.tumas.mymedialist.model.dao.ListDAOImpl;
import ru.tumas.mymedialist.model.ListItem;
import ru.tumas.mymedialist.model.MediaStatus;
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
	private List<ListItem> persistedItems;

	public ListDAOImplTest() {
	}

	@Before
	public void setUp() {
		Map<String, String> props = new HashMap<>();
		props.put("javax.persistence.jdbc.url", "jdbc:h2:/~test");
		entityManagerFactory = Persistence.createEntityManagerFactory("ru.tumas_MyMediaList_jar_0.0.1PU", props);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createQuery("DELETE FROM ListItem");
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
		List<ListItem> result = instance.getAll();
		assertEquals(persistedItems, result);
	}

	@Test
	public void testGetByStatus() {
		System.out.println("getByStatus");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<ListItem> result = instance.getByStatus(MediaStatus.COMPLETED);
		assertEquals(1, result.size());
		assertEquals(persistedItems.get(2), result.get(0));
		result = instance.getByStatus(MediaStatus.DROPPED);
		assertEquals(Collections.emptyList(), result);
	}

	@Test
	public void testGetByType() {
		System.out.println("getByType");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<ListItem> result = instance.getByType(MediaType.MOVIE);
		assertEquals(Collections.emptyList(), result);
		result = instance.getByType(MediaType.ANIME);
		assertEquals(2, result.size());
	}

	@Test
	public void testGetByTypeAndStatus() {
		System.out.println("getByTypeAndStatus");
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<ListItem> result = instance.getByTypeAndStatus(MediaType.ANIME, MediaStatus.COMPLETED);
		assertEquals(1, result.size());
	}

	@Test
	public void testSaveItem() {
		System.out.println("saveItem");
		ListItem item = new ListItem();
		item.setNameEng("save eng");
		item.setType(MediaType.DRAMA);
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		ListItem savedItem = instance.saveItem(item);
		persistedItems.add(savedItem);
		EntityManager em = entityManagerFactory.createEntityManager();
		ListItem result = em.find(ListItem.class, savedItem.getId());
		assertEquals(savedItem.getId(), result.getId());
	}

	@Test
	public void testSaveItems() {
		System.out.println("saveItems");
		List<ListItem> items = new LinkedList<>();
		ListItem item = new ListItem();
		item.setNameEng("saveAll eng");
		item.setType(MediaType.DRAMA);
		items.add(item);
		ListDAOImpl instance = new ListDAOImpl(entityManagerFactory);
		List<ListItem> savedItems = instance.saveItems(items);
		persistedItems.addAll(savedItems);
		EntityManager em = entityManagerFactory.createEntityManager();
		ListItem result = em.find(ListItem.class, savedItems.get(0).getId());
		assertEquals(savedItems.get(0), result);
	}

	private List<ListItem> persistItems() {
		ListItem item1 = new ListItem();
		item1.setNameEng("item1 eng");
		item1.setEpisodes(10);
		item1.setStartDate(new Date());
		item1.setStatus(MediaStatus.WATCHING);
		item1.setType(MediaType.ANIME);
		ListItem item2 = new ListItem();
		item2.setNameEng("item2 eng");
		item2.setEpisodes(12);
		item2.setProgress(1);
		item2.setEndDate(new Date());
		item2.setStatus(MediaStatus.WATCHING);
		item2.setType(MediaType.TV_SHOW);
		ListItem item3 = new ListItem();
		item3.setNameEng("item3 eng");
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
		List<ListItem> items = new LinkedList<>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		return items;
	}

}
