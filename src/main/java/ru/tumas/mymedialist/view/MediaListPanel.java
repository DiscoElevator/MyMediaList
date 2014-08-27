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
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.view.table.ProgressCellEditor;
import ru.tumas.mymedialist.view.table.ProgressCellRenderer;

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
		final WebTable table = new WebTable();
		table.setModel(new MediaTableModel(items));
//		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// making scroll happen when mouse pointer is inside the table
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

		/* ----------------TEST-------------- */
		TableColumn column = table.getColumnModel().getColumn(3);
		column.setCellRenderer(new ProgressCellRenderer());
		column.setCellEditor(new ProgressCellEditor());
		return table;
	}

}
