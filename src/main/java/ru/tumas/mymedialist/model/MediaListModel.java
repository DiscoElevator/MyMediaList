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
package ru.tumas.mymedialist.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;

/**
 *
 * @author Maxim Tumas
 */
public class MediaListModel {

	private List<MediaListItem> items;
	private final ListDAO dao;

	public MediaListModel() {
		this.items = new ArrayList<>();
		dao = ListDAOFactory.createListDAO();
	}

	public MediaListModel(List<MediaListItem> items) {
		this.items = items;
		dao = ListDAOFactory.createListDAO();
	}

	public List<MediaListItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public void setItems(List<MediaListItem> items) {
		this.items = items;
	}

	public void add(MediaListItem item) {
		if (item != null) {
			items.add(dao.saveItem(item));
		}
	}

	public void update(MediaListItem item) {
		if ((item != null) && items.contains(item)) {
			items.remove(item);
			items.add(dao.saveItem(item));
		}
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
}
