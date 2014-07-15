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
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import ru.tumas.mymedialist.model.AppSettings;

public class MainWindow extends WebFrame {

	private static final int MIN_WIDTH = 800;
	private static final int MIN_HEIGHT = 500;

	public MainWindow() {
		super();
		this.setTitle(AppSettings.getLocalizedString("mainWindow.title"));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new GroupPanel(false, createMenu(), createToolbar()), BorderLayout.NORTH);
		this.getContentPane().add(new CenterPanel(), BorderLayout.CENTER);
		this.getContentPane().add(createStatusBar(), BorderLayout.SOUTH);
	}

	private WebMenuBar createMenu() {
		WebMenuBar result = new WebMenuBar();
		result.add(new WebMenu(AppSettings.getLocalizedString("mainWindow.menu.file")) {
			{
				add(new WebMenuItem(AppSettings.getLocalizedString("mainWindow.menu.file.exit")));
			}
		});
		return result;
	}

	private WebStatusBar createStatusBar() {
		WebStatusBar result = new WebStatusBar();
		result.add(new WebLabel("test"));
		return result;
	}

	private WebToolBar createToolbar() {
		WebToolBar result = new WebToolBar(WebToolBar.HORIZONTAL);
		result.setFloatable(false);
		return result;
	}
}
