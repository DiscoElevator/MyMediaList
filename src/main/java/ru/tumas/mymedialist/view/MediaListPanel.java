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
package ru.tumas.mymedialist.view;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaListModel;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

/**
 *
 * @author Maxim Tumas
 */
public class MediaListPanel extends GroupPanel {

	public MediaListPanel() {
		super();
		this.setLayout(new BorderLayout());
		this.add(new WebScrollPane(createTable()), BorderLayout.NORTH);
	}

	public MediaListPanel(MediaListModel model) {
		super();
		this.setLayout(new BorderLayout());
		if ((model != null) && !model.isEmpty()) {
			this.add(new WebScrollPane(createTable(model)), BorderLayout.NORTH);
		}
	}

	private WebTable createTable() {
		WebTable table = new WebTable();
//		List<MediaListItem> items = new ArrayList<>();
//		MediaListItem item1 = new MediaListItem();
//		item1.setNameEng("Name eng 1");
//		item1.setCountry("country 1");
//		item1.setType(MediaType.ANIME);
//		item1.setStatus(MediaStatus.WATCHING);
//		items.add(item1);
//		MediaListItem item2 = new MediaListItem();
//		item2.setNameEng("Name eng 2");
//		item2.setCountry("country 2");
//		item2.setType(MediaType.ANIME);
//		item2.setStatus(MediaStatus.WATCHING);
//		items.add(item2);
		MediaListModel model = new MediaListModel();
//		model.add(item1);
//		model.add(item2);
		table.setModel(new MediaTableModel(model));
//		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}

	private WebTable createTable(MediaListModel model) {
		WebTable table = new WebTable();
		table.setModel(new MediaTableModel(model));
//		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}

}
