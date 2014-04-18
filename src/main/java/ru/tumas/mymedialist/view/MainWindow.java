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
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import ru.tumas.mymedialist.model.AppSettings;

public class MainWindow extends JFrame {

	private static final int MIN_WIDTH = 300;
	private static final int MIN_HEIGHT = 300;
	private final AppSettings settings;

	public MainWindow() {
		super();
		settings = AppSettings.getInstance();
		this.setTitle(settings.getLocalizedString("mainWindow.title"));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		GroupPanel groupPanel = new GroupPanel(createMenu(), new GroupPanel(), createStatusBar());
		groupPanel.setOrientation(SwingConstants.VERTICAL);
		this.add(groupPanel);
	}

	private WebMenuBar createMenu() {
		WebMenuBar result = new WebMenuBar();
		result.add(new WebMenu(settings.getLocalizedString("mainWindow.menu.file")) {
			{
				add(new WebMenuItem(settings.getLocalizedString("mainWindow.menu.file.exit")));
			}
		});
		return result;
	}

	private WebStatusBar createStatusBar() {
		WebStatusBar result = new WebStatusBar();
		return result;
	}
}
