/*
 * JUtils ToString Generator for Eclipse
 * 
 * Copyright (C) 2007  Adarsh Ramamurthy
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 * 
 * Plugin Home Page: http://eclipse-jutils.sourceforge.net
 */

package org.adarsh.jutils.preferences;

import org.adarsh.jutils.JUtilsPlugin;
import org.adarsh.jutils.internal.Util;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preference page handler for <tt>JUtils -> toString()</tt>.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 * 
 * @version 3.0, 10th December 2006
 * 
 * @version 3.1, 7th July 2007
 */
public class ToStringPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * Represents String Buffer.
	 */
	private static final String STRING_BUFFER = "StringBuffer";

	/**
	 * Represents String Builder.
	 */
	private static final String STRING_BUILDER = "StringBuilder";

	/**
	 * Contains the text for displaying the JavaDoc in a <tt>SourceViewer</tt>.
	 */
	private final Document javaDocDocument = new Document();

	/**
	 * Contains the text for displaying the Method Body in a
	 * <tt>SourceViewer</tt>.
	 */
	private Document bodyDocument = new Document();

	/**
	 * The lone instance of <tt>JUtilsPlugin</tt>.
	 */
	private final JUtilsPlugin jUtilsPlugin = JUtilsPlugin.getDefault();

	/**
	 * The preference store associated with this plugin.
	 */
	private final IPreferenceStore prefStore = this.jUtilsPlugin
			.getPreferenceStore();

	/**
	 * Constructs the preference page.
	 */
	public ToStringPreferencesPage() {
		super(GRID);
		super.setPreferenceStore(this.prefStore);
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		this.prefStore.setDefault(PreferenceConstants.MODE,
				PreferenceConstants.STRING);
		this.prefStore.setDefault(PreferenceConstants.TOSTRING_SORT, false);
		this.prefStore.setDefault(PreferenceConstants.TOSTRING_AUTOSAVE, false);
		this.prefStore.setDefault(PreferenceConstants.TOSTRING_OVERWRITE, true);

		String javaDoc = this.prefStore
				.getString(PreferenceConstants.TOSTRING_JAVADOC_STORE_KEY);

		String body = this.prefStore
				.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);

		// if something is null, fetch the true copy.
		if (Util.isNullString(javaDoc)) {
			javaDoc = Util.getDefaultToStringJavaDoc();
		}

		if (Util.isNullString(body)) {
			body = Util.getDefaultToStringImplementation();
		}

		this.javaDocDocument.set(javaDoc);
		this.bodyDocument.set(body);

		super.setDescription(PreferenceConstants.TOSTRING_PAGE_DESCRIPTION);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {
		this.performApply();

		return super.performOk();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void performApply() {
		this.prefStore.setValue(PreferenceConstants.TOSTRING_JAVADOC_STORE_KEY,
				this.javaDocDocument.get());

		this.prefStore.setValue(PreferenceConstants.TOSTRING_BODY_STORE_KEY,
				this.bodyDocument.get());
	}

	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		super.performDefaults();

		String javadoc = Util.getDefaultToStringJavaDoc();
		String body = Util.getDefaultToStringImplementation();

		this.javaDocDocument.set(javadoc);
		this.bodyDocument.set(body);

		this.prefStore.setValue(PreferenceConstants.TOSTRING_JAVADOC_STORE_KEY,
				javadoc);

		this.prefStore.setValue(PreferenceConstants.TOSTRING_BODY_STORE_KEY,
				body);
	}

	/**
	 * {@inheritDoc}
	 */
	public void createFieldEditors() {
		Composite parent = super.getFieldEditorParent();

		GridData data = new GridData(GridData.FILL_BOTH);

		parent.setLayout(new GridLayout(3, true));
		parent.setLayoutData(data);

		BooleanFieldEditor sortCheckbox = new BooleanFieldEditor(
				PreferenceConstants.TOSTRING_SORT,
				PreferenceConstants.TOSTRING_SORT_LABEL, parent);

		BooleanFieldEditor autoSaveCheckbox = new BooleanFieldEditor(
				PreferenceConstants.TOSTRING_AUTOSAVE,
				PreferenceConstants.TOSTRING_AUTOSAVE_LABEL, parent);

		BooleanFieldEditor overwriteCheckbox = new BooleanFieldEditor(
				PreferenceConstants.TOSTRING_OVERWRITE,
				PreferenceConstants.TOSTRING_OVERWRITE_LABEL, parent);

		super.addField(sortCheckbox);
		super.addField(autoSaveCheckbox);
		super.addField(overwriteCheckbox);

		RadioGroupFieldEditor rgfe = new RadioGroupFieldEditor(
				PreferenceConstants.MODE, PreferenceConstants.MODE_LABEL, 3,
				new String[][] {
						{ PreferenceConstants.STRING_LABEL,
								PreferenceConstants.STRING },
						{ PreferenceConstants.STRING_BUFFER_LABEL,
								PreferenceConstants.STRING_BUFFER },
						{ PreferenceConstants.STRING_BUILDER_LABEL,
								PreferenceConstants.STRING_BUILDER } }, parent,
				true);

		super.addField(rgfe);

		PreferenceUtils.createViewer(parent, PreferenceConstants.JAVADOC_LABEL,
				this.javaDocDocument);

		PreferenceUtils.createViewer(parent,
				PreferenceConstants.METHOD_BODY_LABEL, this.bodyDocument);
	}

	/**
	 * {@inheritDoc}
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);

		// indicates if a different radio button is selected.
		boolean isChanged = !event.getNewValue().equals(event.getOldValue());

		// changed and string selected... populate appropriate values.
		if (isChanged && PreferenceConstants.STRING.equals(event.getNewValue())) {
			String body = this.prefStore
					.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);

			if (Util.isNullString(body) || Util.contains(body, STRING_BUFFER)
					|| Util.contains(body, STRING_BUILDER)) {
				body = Util
						.getDefaultToStringImplementation(PreferenceConstants.STRING);
			}

			this.bodyDocument.set(body);
		}
		// changed and string buffer selected... populate appropriate values.
		else if (isChanged
				&& PreferenceConstants.STRING_BUFFER
						.equals(event.getNewValue())) {
			String body = this.prefStore
					.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);

			if (Util.isNullString(body) || Util.contains(body, STRING_BUILDER)
					|| !Util.contains(body, STRING_BUFFER)) {
				body = Util
						.getDefaultToStringImplementation(PreferenceConstants.STRING_BUFFER);
			}

			this.bodyDocument.set(body);
		}
		// changed and string builder selected... populate appropriate values.
		else if (isChanged
				&& PreferenceConstants.STRING_BUILDER.equals(event
						.getNewValue())) {
			String body = this.prefStore
					.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);

			if (Util.isNullString(body) || Util.contains(body, STRING_BUFFER)
					|| !Util.contains(body, STRING_BUILDER)) {
				body = Util
						.getDefaultToStringImplementation(PreferenceConstants.STRING_BUILDER);
			}

			this.bodyDocument.set(body);
		}
	}
}
