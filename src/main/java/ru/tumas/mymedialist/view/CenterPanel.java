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

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.tree.WebTree;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

/**
 *
 * @author Maxim Tumas
 */
public class CenterPanel extends WebSplitPane {

	public CenterPanel() {
		super(HORIZONTAL_SPLIT);
		WebScrollPane leftScrollPane = new WebScrollPane(createTree());
		WebScrollPane rightScrollPane = new WebScrollPane(new MediaListPanel());
		this.setLeftComponent(leftScrollPane);
		this.setRightComponent(rightScrollPane);
	}

	private WebTree createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("List");
		root.add(createMediaTypeNode(MediaType.ANIME));
		root.add(createMediaTypeNode(MediaType.DRAMA));
		root.add(createMediaTypeNode(MediaType.TV_SHOW));
		root.add(createMediaTypeNode(MediaType.MOVIE));

		WebTree tree = new WebTree(root);
		tree.setMultiplySelectionAllowed(false);
//		tree.setAutoExpandSelectedNode(true);
		tree.addTreeSelectionListener(new MediaStatusSelectionListener(this));
		return tree;
	}

	private MediaTypeTreeNode createMediaTypeNode(MediaType mediaType) {
		MediaTypeTreeNode result = new MediaTypeTreeNode(mediaType);
		result.add(new MediaStatusTreeNode(mediaType, MediaStatus.WATCHING));
		result.add(new MediaStatusTreeNode(mediaType, MediaStatus.COMPLETED));
		result.add(new MediaStatusTreeNode(mediaType, MediaStatus.PLAN_TO_WATCH));
		result.add(new MediaStatusTreeNode(mediaType, MediaStatus.DROPPED));
		return result;
	}

	public void createRightPanel(List<MediaListItem> items) {
		this.setRightComponent(new WebScrollPane(new MediaListPanel(items)));
	}
}
