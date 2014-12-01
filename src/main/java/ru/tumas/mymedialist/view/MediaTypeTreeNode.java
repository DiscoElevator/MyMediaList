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
import ru.tumas.mymedialist.AppSettings;
import ru.tumas.mymedialist.model.MediaType;

/**
 *
 * @author Maxim Tumas
 */
public class MediaTypeTreeNode extends DefaultMutableTreeNode {

	private static final Map<MediaType, String> mediaTypeNames = new EnumMap<>(MediaType.class);

	private final MediaType mediaType;

	static {
		mediaTypeNames.put(MediaType.ANIME, "mediaType.anime");
		mediaTypeNames.put(MediaType.DRAMA, "mediaType.drama");
		mediaTypeNames.put(MediaType.MOVIE, "mediaType.movie");
		mediaTypeNames.put(MediaType.TV_SHOW, "mediaType.tvShow");
	}

	public MediaTypeTreeNode(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public String toString() {
		return AppSettings.getLocalizedString(mediaTypeNames.get(mediaType));
	}
}
