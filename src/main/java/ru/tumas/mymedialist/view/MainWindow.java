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

import ru.tumas.mymedialist.view.forms.EditItemForm;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.WindowConstants;
import ru.tumas.mymedialist.model.AppSettings;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;

public class MainWindow extends WebFrame {

	private static final int MIN_WIDTH = 800;
	private static final int MIN_HEIGHT = 500;
	
	private CenterPanel centerPanel;

	public MainWindow() {
		super();
		this.setTitle(AppSettings.getLocalizedString("mainWindow.title"));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new GroupPanel(false, createMenu(), createToolbar()), BorderLayout.NORTH);
		centerPanel = new CenterPanel();
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
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
		WebButton button = new WebButton("Add"); // TODO icon
		TooltipManager.setTooltip(button, AppSettings.getLocalizedString("toolbar.addButton.tooltip"), TooltipWay.down);
		button.setDrawFocus(false);
		final MainWindow thisWindow = this;
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditItemForm addItemForm = new EditItemForm();
				addItemForm.pack();
				addItemForm.setLocationRelativeTo(thisWindow);
				addItemForm.setVisible(true);
			}
		});
		result.add(button);
		WebButton removeButton = new WebButton("X"); // TODO icon
		TooltipManager.setTooltip(removeButton, AppSettings.getLocalizedString("toolbar.removeButton.tooltip"), TooltipWay.down);
		removeButton.setDrawFocus(false);
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MediaListPanel mediaListPanel = thisWindow.centerPanel.getMediaListPanel();
				MediaListItem item = mediaListPanel.getSelectedItem();
				if (item != null) {
					// TODO confirmation
					ListDAO dao = ListDAOFactory.createListDAO();
					dao.remove(item.getOriginalName());
				}
			}
		});
		result.add(removeButton);
		return result;
	}
}
