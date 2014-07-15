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

import java.util.EnumMap;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import ru.tumas.mymedialist.model.AppSettings;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;

/**
 *
 * @author Maxim Tumas
 */
public class MediaStatusTreeNode extends DefaultMutableTreeNode {

	private static final Map<MediaStatus, String> mediaStatusNames = new EnumMap<>(MediaStatus.class);

	private final MediaStatus mediaStatus;
	private final MediaType mediaType;

	static {
		mediaStatusNames.put(MediaStatus.PLAN_TO_WATCH, "mediaStatus.planToWatch");
		mediaStatusNames.put(MediaStatus.WATCHING, "mediaStatus.watching");
		mediaStatusNames.put(MediaStatus.COMPLETED, "mediaStatus.completed");
		mediaStatusNames.put(MediaStatus.DROPPED, "mediaStatus.dropped");
	}

	public MediaStatusTreeNode(MediaType mediaType, MediaStatus mediaStatus) {
		this.mediaType = mediaType;
		this.mediaStatus = mediaStatus;
	}

	public MediaStatus getMediaStatus() {
		return mediaStatus;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public String toString() {
		return AppSettings.getLocalizedString(mediaStatusNames.get(mediaStatus));
	}
}
