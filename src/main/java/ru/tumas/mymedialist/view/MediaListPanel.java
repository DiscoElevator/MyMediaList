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
import java.util.List;
import javax.swing.JTable;
import ru.tumas.mymedialist.model.MediaListItem;

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

	public MediaListPanel(List<MediaListItem> items) {
		super();
		this.setLayout(new BorderLayout());
		if ((items != null) && !items.isEmpty()) {
			this.add(new WebScrollPane(createTable(items)), BorderLayout.NORTH);
		}
	}

	private WebTable createTable() {
		WebTable table = new WebTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}

	private WebTable createTable(List<MediaListItem> items) {
		WebTable table = new WebTable();
		table.setModel(new MediaTableModel(items));
//		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}

}
