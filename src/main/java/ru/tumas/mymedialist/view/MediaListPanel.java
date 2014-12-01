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
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.view.table.ProgressCellEditor;
import ru.tumas.mymedialist.view.table.ProgressCellRenderer;

/**
 *
 * @author Maxim Tumas
 */
public class MediaListPanel extends GroupPanel {

	private WebTable mediaTable;

	public MediaListPanel() {
		super();
		this.setLayout(new BorderLayout());
		mediaTable = createTable();
	}

	public MediaListPanel(List<MediaListItem> items) {
		super();
		this.setLayout(new BorderLayout());
		if ((items != null) && !items.isEmpty()) {
			mediaTable = createTable(items);
			this.add(new WebScrollPane(mediaTable), BorderLayout.NORTH);
		}
	}

	public WebTable getMediaTable() {
		return mediaTable;
	}

	private WebTable createTable() {
		WebTable table = new WebTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}

	private WebTable createTable(List<MediaListItem> items) {
		final WebTable table = new WebTable();
		table.setModel(new MediaTableModel(items));
//		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/* making scroll happen when mouse pointer is inside the table */
		final MediaListPanel self = this;
		table.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				WebScrollPane parent = (WebScrollPane) self.getParent().getParent();
				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					int totalScrollAmount = e.getUnitsToScroll() * parent.getVerticalScrollBar().getUnitIncrement();
					parent.getVerticalScrollBar().setValue(parent.getVerticalScrollBar().getValue() + totalScrollAmount);
				}
			}
		});

		table.addMouseListener(new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("doubleclick");
				}
			}
		});

		/* ----------------TEST-------------- */
		TableColumn column = table.getColumnModel().getColumn(3);
		column.setCellRenderer(new ProgressCellRenderer());
		column.setCellEditor(new ProgressCellEditor());
		return table;
	}

	public MediaListItem getSelectedItem() {
		MediaListItem result = null;
		if ((mediaTable != null) && (mediaTable.getSelectedRow() != -1)) {
			MediaTableModel tableModel = (MediaTableModel) mediaTable.getModel();
			result = tableModel.getItem(mediaTable.getSelectedRow());
		}
		return result;
	}
}
