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
package ru.tumas.mymedialist.util.validation;

import java.util.ArrayList;
import java.util.List;
import ru.tumas.mymedialist.model.MediaListItem;

/**
 *
 * @author Maxim_Tumas
 */
public final class ValidationUtils {

	private ValidationUtils() {
	}

	public static List<ValidationError> validateItem(MediaListItem item) {
		List<ValidationError> errors = new ArrayList<>();
		if (item == null) {
			return null;
		}
		if ((item.getOriginalName() == null) || item.getOriginalName().isEmpty()) {
			errors.add(new ValidationError("originalName"));
		}
		if (item.getProgress() > item.getEpisodes()) {
			errors.add(new ValidationError("progress"));
		}
		if ((item.getStartDate() != null) && (item.getEndDate() != null)
				&& (item.getStartDate().compareTo(item.getEndDate()) > 0)) {
			errors.add(new ValidationError("startDate"));
		}
		return errors;
	}
}
