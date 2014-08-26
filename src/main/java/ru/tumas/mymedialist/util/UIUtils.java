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
package ru.tumas.mymedialist.util;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebDialog;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import ru.tumas.mymedialist.model.AppSettings;

/**
 *
 * @author Maxim_Tumas
 */
public final class UIUtils {

	private UIUtils() {
	}

	public static void runBlockingTask(Component owner, final Runnable runnable) {
		final boolean decorateDialogs = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);
		final WebDialog dialog = new WebDialog();
		dialog.setModal(true);
		dialog.setLocationRelativeTo(owner);
		dialog.setMinimumSize(new Dimension(300, 80));
		dialog.setShowCloseButton(false);
		dialog.setResizable(false);
		dialog.setShowTitleComponent(false);
		WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString(AppSettings.getLocalizedString("progressbar.text"));
		dialog.add(progressBar);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						runnable.run();
						dialog.setVisible(false);
					}
				}).start();
			}
		});
		dialog.setVisible(true);
		WebLookAndFeel.setDecorateDialogs(decorateDialogs);
	}
}
