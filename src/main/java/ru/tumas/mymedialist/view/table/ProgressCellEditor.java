/*
 * Copyright (C) 2014 Maxim_Tumas
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
package ru.tumas.mymedialist.view.table;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Maxim_Tumas
 */
public class ProgressCellEditor extends AbstractCellEditor implements TableCellEditor {

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, boolean isSelected, final int row, final int column) {
		WebPanel panel = new WebPanel();
		WebButton button = new WebButton("+");
		button.setPreferredSize(new Dimension(30, 10));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] vals = ((String) value).split("/");
				int progress = Integer.valueOf(vals[0]);
				int episodes = Integer.valueOf(vals[1]);
				if (progress + 1 <= episodes) {
					progress++;
					table.setValueAt(progress, row, column);
					fireEditingStopped();
				}
			}
		});
		WebButton decButton = new WebButton("-");
		decButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] vals = ((String) value).split("/");
				int progress = Integer.valueOf(vals[0]);
				if (progress - 1 >= 0) {
					progress--;
					table.setValueAt(progress, row, column);
					fireEditingStopped();
				}
			}
		});
		decButton.setPreferredSize(new Dimension(30, 10));
		panel.setLayout(new BorderLayout());
		panel.add(button, BorderLayout.WEST);
		panel.add(new WebLabel((String) value), BorderLayout.CENTER);
		panel.add(decButton, BorderLayout.EAST);
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
}
