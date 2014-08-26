/* 
 * Copyright (C) 2014 Maxim_Tumas
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ru.tumas.mymedialist.listeners;

import com.alee.extended.date.WebDateField;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.spinner.WebSpinner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.tumas.mymedialist.model.MediaStatus;

/**
 *
 * @author Maxim Tumas
 */
public class StatusChangeListener implements ActionListener {

	private final WebSpinner maxEpisodes;
	private final WebSpinner episodesWatched;
	private final WebDateField startDate;
	private final WebDateField endDate;
	private final WebButton[] clearButtons;

	public StatusChangeListener(WebSpinner maxEpisodes, WebSpinner episodesWatched,
			WebDateField startDate, WebDateField endDate, WebButton[] clearButtons) {
		this.maxEpisodes = maxEpisodes;
		this.episodesWatched = episodesWatched;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clearButtons = clearButtons;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MediaStatus status = (MediaStatus) ((WebComboBox) e.getSource()).getSelectedItem();
		switch (status) {
			case PLAN_TO_WATCH:
				episodesWatched.setValue(0);
				episodesWatched.setEnabled(false);
				startDate.setEnabled(false);
				endDate.setEnabled(false);
				disableClearButtons();
				break;
			case WATCHING:
			case DROPPED:
				episodesWatched.setEnabled(true);
				startDate.setEnabled(true);
				endDate.setEnabled(true);
				enableClearButtons();
				break;
			case COMPLETED:
				episodesWatched.setValue(maxEpisodes.getValue());
				episodesWatched.setEnabled(false);
				startDate.setEnabled(true);
				endDate.setEnabled(true);
				enableClearButtons();
				break;
		}
	}

	private void disableClearButtons() {
		for (WebButton button : clearButtons) {
			button.setEnabled(false);
		}
	}

	private void enableClearButtons() {
		for (WebButton button : clearButtons) {
			button.setEnabled(true);
		}
	}
}
