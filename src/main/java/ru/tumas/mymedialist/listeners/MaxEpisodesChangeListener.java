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

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.spinner.WebSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ru.tumas.mymedialist.model.MediaStatus;

/**
 *
 * @author Maxim_Tumas
 */
public class MaxEpisodesChangeListener implements ChangeListener {

	private final WebSpinner episodesWatched;
	private final WebComboBox statusComboBox;

	public MaxEpisodesChangeListener(WebSpinner episodesWatched, WebComboBox statusComboBox) {
		this.episodesWatched = episodesWatched;
		this.statusComboBox = statusComboBox;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		MediaStatus status = (MediaStatus) statusComboBox.getSelectedItem();
		if (MediaStatus.COMPLETED.equals(status)) {
			episodesWatched.setValue(((WebSpinner) e.getSource()).getValue());
		}
	}

}
