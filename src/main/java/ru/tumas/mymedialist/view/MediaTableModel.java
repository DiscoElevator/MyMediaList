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

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ru.tumas.mymedialist.model.AppSettings;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;

/**
 *
 * @author Maxim Tumas
 */
public class MediaTableModel extends AbstractTableModel {

	private static final List<TableColumnMeta> columnMeta = new ArrayList<>();

	static {
		columnMeta.add(new TableColumnMeta("table.columns.originalName", String.class));
		columnMeta.add(new TableColumnMeta("table.columns.localizedName", String.class));
		columnMeta.add(new TableColumnMeta("table.columns.country", String.class));
		columnMeta.add(new TableColumnMeta("table.columns.progress", Integer.class));
	}

	private final List<MediaListItem> items;

	public MediaTableModel(List<MediaListItem> items) {
		this.items = items;
	}

	public MediaListItem getItem(int rowIndex) {
		return items.get(rowIndex);
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return columnMeta.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MediaListItem item = items.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return item.getOriginalName();
			case 1:
				return item.getLocalizedName();
			case 2:
				return item.getCountry();
			case 3:
				return item.getProgress() + "/" + item.getEpisodes();
			default:
				return "row" + rowIndex + "col" + columnIndex;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		MediaListItem item = items.get(rowIndex);
		if ((columnIndex == 3) && (aValue != null)) {
			item.setProgress((int) aValue);
			ListDAO dao = ListDAOFactory.createListDAO();
			items.remove(rowIndex);
			items.add(rowIndex, dao.saveItem(item));
		}
	}

	@Override
	public String getColumnName(int column) {
		TableColumnMeta meta = columnMeta.get(column);
		return AppSettings.getLocalizedString((meta != null) ? meta.getKey() : "col" + column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		TableColumnMeta meta = columnMeta.get(columnIndex);
		return (meta != null) ? meta.getClass() : String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (MediaStatus.WATCHING.equals(items.get(rowIndex).getStatus()) && (columnIndex == 3));
	}
}
