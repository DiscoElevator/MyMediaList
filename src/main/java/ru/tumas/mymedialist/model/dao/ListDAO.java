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

import java.util.List;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

public interface ListDAO {

	List<MediaListItem> getAll();

	List<MediaListItem> get(MediaStatus status);

	List<MediaListItem> get(MediaType type);

	List<MediaListItem> get(MediaType type, MediaStatus status);

	MediaListItem get(String originalName);

	MediaListItem saveItem(MediaListItem item);

	List<MediaListItem> saveItems(List<MediaListItem> items);

	void remove(String originalName);
}
