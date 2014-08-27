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
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Maxim_Tumas
 */
public class ProgressCellRenderer extends WebPanel implements TableCellRenderer {

	private final WebLabel label;

	public ProgressCellRenderer() {
		label = new WebLabel();
		WebButton incButton = new WebButton("+");
		incButton.setPreferredSize(new Dimension(30, 10));
		incButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("tralala");
			}
		});
		WebButton decButton = new WebButton("-");
		decButton.setPreferredSize(new Dimension(30, 10));
		setLayout(new BorderLayout());
		add(incButton, BorderLayout.WEST);
		add(label, BorderLayout.CENTER);
		add(decButton, BorderLayout.EAST);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		label.setText((String) value);
		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(table.getBackground());
		}
		return this;
	}
}
