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
package ru.tumas.mymedialist.model;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AppSettings {

	private static final Logger logger = Logger.getLogger(AppSettings.class.getName());
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("META-INF/Bundle", Locale.getDefault()); // TODO config to override default locale (cfg_locale -> default_locale)

	public static String getLocalizedString(String key) {
		String result = "???" + key + "???";
		try {
			result = resourceBundle.getString(key);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return result;
	}
}
