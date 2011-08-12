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
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tan.util.Editor;

/**
 * The preference page handler for <tt>JUtils -> Getter Setter Style</tt>.
 * 
 * @author Dolphin
 * 
 */
public class GetterSetterPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * The lone instance of <tt>JUtilsPlugin</tt>.
	 */
	private final JUtilsPlugin jUtilsPlugin = JUtilsPlugin.getDefault();
	
	
	private String editorPath;
	
	private String style = "1"; // 1,2,3
	/**
	 * The preference store associated with this plugin.
	 */
	private final IPreferenceStore prefStore = this.jUtilsPlugin
			.getPreferenceStore();

	public GetterSetterPreferencesPage() {
		super(GRID);
		super.setPreferenceStore(this.prefStore);
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		this.editorPath = Editor.getEditplusPath();
		
		super.setPreferenceStore(this.prefStore);

		super.setDescription(PreferenceConstants.GETTER_SETTER_DESCRIPTION);
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
//		this.prefStore.setValue(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY,
//				this.javaDocDocument.get());
//
//		this.prefStore.setValue(PreferenceConstants.COPYCON_BODY_STORE_KEY,
//				this.bodyDocument.get());
		
		this.prefStore.setValue(PreferenceConstants.EDITOR_PATH,
				editorPath);
		
		this.prefStore.setValue(PreferenceConstants.GETTER_SETTER_STYLE,
				 style );
	}

	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
//		String javadoc = Util.getDefaultCopyConJavaDoc();
//		String body = Util.getDefaultCopyConImplementation();
//
//		this.javaDocDocument.set(javadoc);
//		this.bodyDocument.set(body);
//
//		this.prefStore.setValue(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY,
//				javadoc);
//
//		this.prefStore.setValue(PreferenceConstants.COPYCON_BODY_STORE_KEY,
//				body);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFieldEditors() {
		Composite parent = super.getFieldEditorParent();

		GridData data = new GridData(GridData.FILL_BOTH);

		parent.setLayout(new GridLayout(3, true));
		parent.setLayoutData(data);
		FileFieldEditor editorPathEditor = new FileFieldEditor(PreferenceConstants.EDITOR_PATH, 
				"&编辑器:", getFieldEditorParent());
		
		RadioGroupFieldEditor rgfe = new RadioGroupFieldEditor(
				PreferenceConstants.GETTER_SETTER_STYLE,
				PreferenceConstants.GETTER_SETTER_STYLE_LABEL,
				3,
				new String[][] {
						{ PreferenceConstants.STYLE1,
								PreferenceConstants.STR_STYLE1 },
						{ PreferenceConstants.STYLE2,
								PreferenceConstants.STR_STYLE2 },
						{ PreferenceConstants.STYLE3,
								PreferenceConstants.STR_STYLE3 } }, parent,
				true);

		addField(rgfe);
		
		if ( null != editorPath ) {
			editorPathEditor.setStringValue( editorPath );
		}
		
		addField(editorPathEditor);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);

		// indicates if a different radio button is selected.
		boolean isChanged = !event.getNewValue().equals(event.getOldValue());

		// changed and string selected... populate appropriate values.
		
		if ( isChanged ) {
			if ( PreferenceConstants.STR_STYLE1.equals(event.getNewValue()) ) {
				style = PreferenceConstants.STR_STYLE1;
			} else if ( PreferenceConstants.STR_STYLE2.equals(event.getNewValue()) ) {
				style = PreferenceConstants.STR_STYLE2;
			} else if ( PreferenceConstants.STR_STYLE3.equals(event.getNewValue()) ) {
				style = PreferenceConstants.STR_STYLE3;
			}
		}
		
	}
}
