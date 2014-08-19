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

	public StatusChangeListener(WebSpinner maxEpisodes, WebSpinner episodesWatched) {
		this.maxEpisodes = maxEpisodes;
		this.episodesWatched = episodesWatched;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MediaStatus status = (MediaStatus) ((WebComboBox) e.getSource()).getSelectedItem();
		switch (status) {
			case PLAN_TO_WATCH:
				episodesWatched.setValue(0);
				episodesWatched.setEnabled(false);
				break;
			case WATCHING:
				episodesWatched.setEnabled(true);
				break;
			case DROPPED:
				episodesWatched.setEnabled(true);
				break;
			case COMPLETED:
				episodesWatched.setValue(maxEpisodes.getValue());
				episodesWatched.setEnabled(false);
				break;
		}
	}

}
