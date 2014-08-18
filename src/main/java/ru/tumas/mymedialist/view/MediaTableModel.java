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
	}

	private final List<MediaListItem> items;

	public MediaTableModel(List<MediaListItem> items) {
		this.items = items;
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
			default:
				return "row" + rowIndex + "col" + columnIndex;
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
}
