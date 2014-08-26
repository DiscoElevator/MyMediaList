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
package ru.tumas.mymedialist.view.forms;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.SpinnerNumberModel;
import ru.tumas.mymedialist.model.AppSettings;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;
import ru.tumas.mymedialist.listeners.MaxEpisodesChangeListener;
import ru.tumas.mymedialist.listeners.StatusChangeListener;

/**
 *
 * @author Maxim Tumas
 */
public class AddItemForm extends WebDialog {

	private final WebTextField originalNameTextField;
	private final WebTextField localizedNameTextField;
	private final WebComboBox countryComboBox;
	private final WebComboBox typeComboBox;
	private final WebComboBox statusComboBox;
	private final WebSpinner maxEpisodes;
	private final WebSpinner episodesWatched;

	public AddItemForm() {
		super();
		originalNameTextField = new WebTextField();
//		originalNameTextField.setMinimumWidth(100);
		localizedNameTextField = new WebTextField();
//		localizedNameTextField.setMinimumWidth(100);
		countryComboBox = new WebComboBox(getCountries());
		typeComboBox = new WebComboBox(MediaType.values());
		statusComboBox = new WebComboBox(MediaStatus.values());
		maxEpisodes = new WebSpinner(new SpinnerNumberModel(1, 1, 999, 1));
		episodesWatched = new WebSpinner(new SpinnerNumberModel(0, 0, 1, 1));
		statusComboBox.addActionListener(new StatusChangeListener(maxEpisodes, episodesWatched));
		maxEpisodes.addChangeListener(new MaxEpisodesChangeListener(episodesWatched, statusComboBox));
		statusComboBox.setSelectedIndex(0); // fires initial notification
		setModal(true);
		setResizable(false);
		setMinimumSize(new Dimension(500, 400));
		setLayout(new BorderLayout(5, 5));
		WebPanel panel = new WebPanel();
		panel.setLayout(createLayout());
		panel.setMargin(5, 5, 5, 5);
		panel.add(new WebLabel("nameOrig"), "0,0");
		panel.add(originalNameTextField, "1,0,3,0");
		panel.add(new WebLabel("nameLocalized"), "0,1");
		panel.add(localizedNameTextField, "1,1,3,1");
		panel.add(new WebLabel("country"), "0,2");
		panel.add(countryComboBox, "1,2,3,2");
		panel.add(new WebLabel("type"), "0,3");
		panel.add(typeComboBox, "1,3,3,3");
		panel.add(new WebLabel("status"), "0,4");
		panel.add(statusComboBox, "1,4,3,4");
		panel.add(new WebLabel("progress"), "0,5");
		panel.add(episodesWatched, "1,5");
		panel.add(new WebLabel("/"), "2,5");
		panel.add(maxEpisodes, "3,5");
//		panel.setLayout(new FormLayout(false, true));
//		panel.setMargin(5, 5, 5, 5);
//		panel.add(new WebLabel("nameOrig"), FormLayout.LEFT);
//		panel.add(nameOrigTextField, FormLayout.RIGHT);
//		panel.add(new WebLabel("country"), FormLayout.LEFT);
//		panel.add(countryTextField, FormLayout.RIGHT);
//		panel.add(new WebLabel("type"), FormLayout.LEFT);
//		panel.add(typeComboBox, FormLayout.RIGHT);
//		panel.add(new WebLabel("status"), FormLayout.LEFT);
//		panel.add(statusComboBox, FormLayout.RIGHT);
		add(panel, BorderLayout.NORTH);
		add(createAddButton(), BorderLayout.SOUTH);
		setTitle(AppSettings.getLocalizedString("addForm.title"));
	}

	private TableLayout createLayout() {
		double[][] size = new double[][]{
			{TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL},
			{TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED,
				TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED}};
		TableLayout layout = new TableLayout(size);
		layout.setHGap(5);
		layout.setVGap(5);
		return layout;
	}

	private WebButton createAddButton() {
		WebButton addButton = new WebButton("Add");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("entered text: " + originalNameTextField.getText());
				if (!"".equals(originalNameTextField.getText())) {
					MediaListItem item = new MediaListItem();
					item.setOriginalName(originalNameTextField.getText());
					item.setLocalizedName(localizedNameTextField.getText());
					item.setCountry((String) countryComboBox.getSelectedItem());
					item.setType((MediaType) typeComboBox.getSelectedItem());
					item.setStatus((MediaStatus) statusComboBox.getSelectedItem());
					item.setEpisodes((int) maxEpisodes.getValue());
					item.setProgress((int) episodesWatched.getValue());
					ListDAO dao = ListDAOFactory.createListDAO();
					dao.saveItem(item);
				}
			}
		});
		return addButton;
	}

	private static String[] getCountries() {
		Locale currrentLocale = Locale.getDefault();
		List<String> result = new LinkedList<>();
		String[] locales = Locale.getISOCountries();
		for (String countryCode : locales) {
			Locale locale = new Locale("", countryCode);
			result.add(locale.getDisplayCountry(currrentLocale));
		}
		Collections.sort(result);
		return result.toArray(new String[locales.length]);
	}
}
