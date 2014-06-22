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

import java.util.List;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaListModel;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;

/**
 *
 * @author Maxim Tumas
 */
public class TreeNodeSelectionListener implements TreeSelectionListener {

	private final CenterPanel centerPanel;
	private final ListDAO listDAO;

	public TreeNodeSelectionListener(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
		this.listDAO = ListDAOFactory.createListDAO();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		if (path.getLastPathComponent() instanceof MediaTypeTreeNode) {
			MediaTypeTreeNode typeNode = (MediaTypeTreeNode) path.getLastPathComponent();
			System.out.println("selected: " + typeNode.getMediaType());
		} else if (path.getLastPathComponent() instanceof MediaStatusTreeNode) {
			MediaStatusTreeNode statusNode = (MediaStatusTreeNode) path.getLastPathComponent();
			System.out.println("selected: " + statusNode.getMediaStatus() + " -> " + statusNode.getMediaType());
			List<MediaListItem> items = listDAO.getByTypeAndStatus(statusNode.getMediaType(), statusNode.getMediaStatus());
			if (items != null) {
				centerPanel.createRightPanel(new MediaListModel(items));
			}
		}
	}
}
