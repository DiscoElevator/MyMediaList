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

import com.alee.extended.date.WebDateField;
import com.alee.extended.layout.TableLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import javax.swing.SpinnerNumberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tumas.mymedialist.listeners.ClearDateActionListener;
import ru.tumas.mymedialist.AppSettings;
import ru.tumas.mymedialist.model.MediaListItem;
import ru.tumas.mymedialist.model.MediaStatus;
import ru.tumas.mymedialist.model.MediaType;
import ru.tumas.mymedialist.model.dao.ListDAO;
import ru.tumas.mymedialist.model.dao.ListDAOFactory;
import ru.tumas.mymedialist.listeners.MaxEpisodesChangeListener;
import ru.tumas.mymedialist.listeners.StatusChangeListener;
import ru.tumas.mymedialist.util.UIBlockingWorker;
import ru.tumas.mymedialist.util.validation.ValidationError;
import ru.tumas.mymedialist.util.validation.ValidationUtils;

/**
 *
 * @author Maxim Tumas
 */
public class EditItemForm extends WebDialog {

	private static final Logger logger = LoggerFactory.getLogger(EditItemForm.class);

	private final WebTextField originalNameTextField;
	private final WebTextField localizedNameTextField;
	private final WebComboBox countryComboBox;
	private final WebComboBox typeComboBox;
	private final WebComboBox statusComboBox;
	private final WebSpinner maxEpisodes;
	private final WebSpinner episodesWatched;
	private final WebDateField startDate;
	private final WebDateField endDate;
	private boolean editMode;

	public EditItemForm() {
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
		startDate = new WebDateField();
		endDate = new WebDateField();
		WebButton startDateClearButton = new WebButton("Clear", new ClearDateActionListener(startDate));
		WebButton endDateClearButton = new WebButton("Clear", new ClearDateActionListener(endDate));
		statusComboBox.addActionListener(new StatusChangeListener(maxEpisodes, episodesWatched,
				startDate, endDate, new WebButton[]{startDateClearButton, endDateClearButton}));
		maxEpisodes.addChangeListener(new MaxEpisodesChangeListener(episodesWatched, statusComboBox));
		statusComboBox.setSelectedIndex(0); // fires initial notification
		setModal(true);
		setResizable(false);
		setMinimumSize(new Dimension(500, 400));
		setLayout(new BorderLayout(5, 5));
		WebPanel panel = new WebPanel();
		panel.setLayout(createLayout());
		panel.setMargin(5, 5, 5, 5);
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.originalName")), "0,0");
		panel.add(originalNameTextField, "1,0,3,0");
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.localizedName")), "0,1");
		panel.add(localizedNameTextField, "1,1,3,1");
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.country")), "0,2");
		panel.add(countryComboBox, "1,2,3,2");
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.type")), "0,3");
		panel.add(typeComboBox, "1,3,3,3");
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.status")), "0,4");
		panel.add(statusComboBox, "1,4,3,4");
		panel.add(new WebLabel(AppSettings.getLocalizedString("editForm.fields.progress")), "0,5");
		panel.add(episodesWatched, "1,5");
		panel.add(new WebLabel("/"), "2,5");
		panel.add(maxEpisodes, "3,5");
		panel.add(new WebLabel("startDate"), "0,6");
		panel.add(startDate, "1,6");
		panel.add(startDateClearButton, "3,6");
		panel.add(new WebLabel("endDate"), "0,7");
		panel.add(endDate, "1,7");
		panel.add(endDateClearButton, "3,7");
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
		add(createSaveButton(), BorderLayout.SOUTH);
		setTitle(AppSettings.getLocalizedString("editForm.title"));
	}
	
	public EditItemForm(MediaListItem item) {
		this();
		originalNameTextField.setText(item.getOriginalName());
		localizedNameTextField.setText(item.getLocalizedName());
		countryComboBox.setSelectedIndex(Arrays.binarySearch(getCountries(), item.getCountry()));
		typeComboBox.setSelectedItem(item.getType());
		statusComboBox.setSelectedItem(item.getStatus());
		maxEpisodes.setValue(item.getEpisodes());
		episodesWatched.setValue(item.getProgress());
		startDate.setDate(item.getStartDate());
		endDate.setDate(item.getEndDate());
		editMode = true;
	}

	private TableLayout createLayout() {
		double[][] size = new double[][]{
			{TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL},
			{TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED,
				TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED,
				TableLayout.PREFERRED}};
		TableLayout layout = new TableLayout(size);
		layout.setHGap(5);
		layout.setVGap(5);
		return layout;
	}

	private WebButton createSaveButton() {
		WebButton addButton = new WebButton("Save");
		final WebDialog dialog = this;
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				logger.debug("Trying to add new item or edit existing");
				final MediaListItem item = new MediaListItem();
				item.setOriginalName(originalNameTextField.getText());
				item.setLocalizedName(localizedNameTextField.getText());
				item.setCountry((String) countryComboBox.getSelectedItem());
				item.setType((MediaType) typeComboBox.getSelectedItem());
				item.setStatus((MediaStatus) statusComboBox.getSelectedItem());
				item.setEpisodes((int) maxEpisodes.getValue());
				item.setProgress((int) episodesWatched.getValue());
				item.setStartDate(startDate.getDate());
				item.setEndDate(endDate.getDate());
				logger.debug("Validating item: " + item.toString());

				UIBlockingWorker<List<ValidationError>, Void> worker = new UIBlockingWorker<List<ValidationError>, Void>(dialog) {

					@Override
					protected List<ValidationError> doInBackground() throws Exception {
						blockUI();
						List<ValidationError> errors = ValidationUtils.validateItem(item, !editMode);
						if (errors.isEmpty()) {
							ListDAO dao = ListDAOFactory.createListDAO();
							dao.saveItem(item);
						}
						return errors;
					}

					@Override
					protected void done() {
						unblockUI();
						try {
							List<ValidationError> errors = get();
							if (!errors.isEmpty()) {
								processValidationErrors(errors);
							} else {
								// TODO clear form and update list
							}
						} catch (InterruptedException ex) {
							// do nothing
						} catch (ExecutionException ex) {
							WebOptionPane.showMessageDialog(dialog, AppSettings.getLocalizedString("error.editForm.cannotSave"),
									AppSettings.getLocalizedString("error.title"), WebOptionPane.ERROR_MESSAGE);
						}
					}
				};
				worker.execute();
			}
		});
		return addButton;
	}

	private void processValidationErrors(List<ValidationError> errors) {
		StringBuilder sb = new StringBuilder();
		sb.append("Validation failed: ");
		for (ValidationError error : errors) {
			sb.append(error.getFieldName());
			sb.append(",");
		}
		logger.debug("Validation failed: " + sb.toString());
		WebOptionPane.showMessageDialog(this, sb.toString(), AppSettings.getLocalizedString("error.title"), WebOptionPane.ERROR_MESSAGE);
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
