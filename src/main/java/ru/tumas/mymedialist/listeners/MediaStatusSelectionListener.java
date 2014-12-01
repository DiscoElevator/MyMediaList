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
package ru.tumas.mymedialist.listeners;

import com.alee.laf.optionpane.WebOptionPane;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import ru.tumas.mymedialist.AppSettings;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;
import ru.tumas.mymedialist.util.UIBlockingWorker;
import ru.tumas.mymedialist.view.CenterPanel;
import ru.tumas.mymedialist.view.MediaStatusTreeNode;
import ru.tumas.mymedialist.view.MediaTypeTreeNode;

/**
 *
 * @author Maxim Tumas
 */
public class MediaStatusSelectionListener implements TreeSelectionListener {

	private final CenterPanel centerPanel;

	public MediaStatusSelectionListener(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		if (path.getLastPathComponent() instanceof MediaTypeTreeNode) {
			MediaTypeTreeNode typeNode = (MediaTypeTreeNode) path.getLastPathComponent();
			System.out.println("selected: " + typeNode.getMediaType());
		} else if (path.getLastPathComponent() instanceof MediaStatusTreeNode) {
			final MediaStatusTreeNode statusNode = (MediaStatusTreeNode) path.getLastPathComponent();
			System.out.println("selected: " + statusNode.getMediaStatus() + " -> " + statusNode.getMediaType());

			UIBlockingWorker<List<MediaListItem>, Void> worker = new UIBlockingWorker<List<MediaListItem>, Void>(centerPanel) {

				@Override
				protected List<MediaListItem> doInBackground() throws Exception {
					blockUI();
					ListDAO dao = ListDAOFactory.createListDAO();
					List<MediaListItem> items = dao.get(statusNode.getMediaType(), statusNode.getMediaStatus());
					return items;
				}

				@Override
				protected void done() {
					unblockUI();
					try {
						List<MediaListItem> items = get();
						centerPanel.createRightPanel(!items.isEmpty() ? items : null);
					} catch (InterruptedException ex) {
						// do nothing
					} catch (ExecutionException ex) {
						WebOptionPane.showMessageDialog(centerPanel, AppSettings.getLocalizedString("error.message.cannotGetItems"),
								AppSettings.getLocalizedString("error.title"), WebOptionPane.ERROR_MESSAGE);
					}
				}
			};
			worker.execute();
		}
	}
}
