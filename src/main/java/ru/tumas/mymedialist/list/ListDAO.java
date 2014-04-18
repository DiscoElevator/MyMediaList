package ru.tumas.mymedialist.list;

import java.util.List;

public interface ListDAO {

	List<ListItem> getAll();

	List<ListItem> getByStatus(MediaStatus status);

	List<ListItem> getByType(MediaType type);

	List<ListItem> getByTypeAndStatus(MediaType type, MediaStatus status);

	ListItem saveItem(ListItem item);

	List<ListItem> saveItems(List<ListItem> items);
}
