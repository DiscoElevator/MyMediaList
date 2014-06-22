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

import java.util.List;
import javax.swing.table.AbstractTableModel;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaListModel;

/**
 *
 * @author Maxim Tumas
 */
public class MediaTableModel extends AbstractTableModel {

//	private final List<MediaListItem> items;
	private final MediaListModel model;

	public MediaTableModel(MediaListModel model) {
		this.model = model;
	}

	@Override
	public int getRowCount() {
		return model.getItems().size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MediaListItem item = model.getItems().get(rowIndex);
		if (columnIndex == 0) {
			return item.getNameEng();
		} else {
			return item.getCountry();
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Name";
		} else {
			return "Country";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
//	       private String[] columnNames = { "Name", "Sport", "# of Years", "Vegetarian" };
//        private Object[][] data = { { "Kathy", "Snowboarding", 5, false }, { "John", "Rowing", 3, true }, { "Sue", "Knitting", 2, false },
//                { "Jane", "Speed reading", 20, true }, { "Joe", "Pool", 10, false } };
//
//        public final Object[] longValues = { "Jane", "None of the above", 20, Boolean.TRUE };
//
//        @Override
//        public int getColumnCount ()
//        {
//            return columnNames.length;
//        }
//
//        @Override
//        public int getRowCount ()
//        {
//            return data.length;
//        }
//
//        @Override
//        public String getColumnName ( int col )
//        {
//            return columnNames[ col ];
//        }
//
//        @Override
//        public Object getValueAt ( int row, int col )
//        {
//            return data[ row ][ col ];
//        }
//
//        @Override
//        public Class getColumnClass ( int c )
//        {
//            return longValues[ c ].getClass ();
//        }
//
//        @Override
//        public boolean isCellEditable ( int row, int col )
//        {
//            return col >= 1;
//        }
//
//        @Override
//        public void setValueAt ( Object value, int row, int col )
//        {
//            data[ row ][ col ] = value;
//            fireTableCellUpdated ( row, col );
//        }
}
