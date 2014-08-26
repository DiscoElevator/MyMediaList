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
package ru.tumas.mymedialist.listeners;

import com.alee.extended.date.WebDateField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Maxim_Tumas
 */
public class ClearDateActionListener implements ActionListener {

	private final WebDateField dateField;

	public ClearDateActionListener(WebDateField dateField) {
		this.dateField = dateField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dateField.clear();
	}
}
