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
import org.eclipse.jface.text.Document;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preference page handler for <tt>JUtils -> Copy Constructor</tt>.
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
public class CopyConstructorPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * Contains the text for displaying the JavaDoc in a <tt>SourceViewer</tt>.
	 */
	private Document javaDocDocument = new Document();

	/**
	 * Contains the text for displaying the Method(Constructor) Body in a
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

	public CopyConstructorPreferencesPage() {
		super(GRID);
		super.setPreferenceStore(this.prefStore);
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		this.prefStore.setDefault(PreferenceConstants.COPYCON_SORT, false);
		this.prefStore.setDefault(PreferenceConstants.COPYCON_AUTOSAVE, false);
		this.prefStore.setDefault(PreferenceConstants.COPYCON_OVERWRITE, true);

		String javaDoc = this.prefStore
				.getString(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY);

		String body = this.prefStore
				.getString(PreferenceConstants.COPYCON_BODY_STORE_KEY);

		// if something is null, fetch the true copy.
		if (Util.isNullString(javaDoc)) {
			javaDoc = Util.getDefaultCopyConJavaDoc();
		}

		if (Util.isNullString(body)) {
			body = Util.getDefaultCopyConImplementation();
		}

		this.javaDocDocument = new Document(javaDoc);
		this.bodyDocument = new Document(body);

		super.setPreferenceStore(this.prefStore);

		super.setDescription(PreferenceConstants.COPYCON_PAGE_DESCRIPTION);
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
		this.prefStore.setValue(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY,
				this.javaDocDocument.get());

		this.prefStore.setValue(PreferenceConstants.COPYCON_BODY_STORE_KEY,
				this.bodyDocument.get());
	}

	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		String javadoc = Util.getDefaultCopyConJavaDoc();
		String body = Util.getDefaultCopyConImplementation();

		this.javaDocDocument.set(javadoc);
		this.bodyDocument.set(body);

		this.prefStore.setValue(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY,
				javadoc);

		this.prefStore.setValue(PreferenceConstants.COPYCON_BODY_STORE_KEY,
				body);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFieldEditors() {
		Composite parent = super.getFieldEditorParent();

		GridData data = new GridData(GridData.FILL_BOTH);

		parent.setLayout(new GridLayout(3, true));
		parent.setLayoutData(data);

		BooleanFieldEditor sortCheckbox = new BooleanFieldEditor(
				PreferenceConstants.COPYCON_SORT,
				PreferenceConstants.COPYCON_SORT_LABEL, parent);

		BooleanFieldEditor autoSaveCheckbox = new BooleanFieldEditor(
				PreferenceConstants.COPYCON_AUTOSAVE,
				PreferenceConstants.COPYCON_AUTOSAVE_LABEL, parent);

		BooleanFieldEditor overriteCheckbox = new BooleanFieldEditor(
				PreferenceConstants.COPYCON_OVERWRITE,
				PreferenceConstants.COPYCON_OVERWRITE_LABEL, parent);

		super.addField(sortCheckbox);
		super.addField(autoSaveCheckbox);
		super.addField(overriteCheckbox);

		PreferenceUtils.createViewer(parent, PreferenceConstants.JAVADOC_LABEL,
				this.javaDocDocument);

		PreferenceUtils.createViewer(parent,
				PreferenceConstants.CONSTRUCTOR_BODY_LABEL, this.bodyDocument);
	}
}
