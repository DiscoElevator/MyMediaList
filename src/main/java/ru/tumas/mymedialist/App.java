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
package ru.tumas.mymedialist;

import com.alee.laf.WebLookAndFeel;
import javax.swing.SwingUtilities;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;
import ru.tumas.mymedialist.view.MainWindow;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("logger.properties");
		logger.debug("Application startup");
		ListDAOFactory.createListDAO(); // preload EntityManagerFactory
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Install WebLaF as application L&F
				WebLookAndFeel.install();

				WebLookAndFeel.setDecorateAllWindows(true);
				MainWindow window = new MainWindow();
				window.setVisible(true);
				logger.debug("Application started");
			}
		});
	}
}
