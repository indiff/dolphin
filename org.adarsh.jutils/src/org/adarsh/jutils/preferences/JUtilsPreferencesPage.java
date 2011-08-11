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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preference page handler for <tt>JUtils</tt>.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 */
public class JUtilsPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {
	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		super.noDefaultAndApplyButton();

		GridLayout gridLayout = new GridLayout();

		gridLayout.numColumns = 2;

		parent.setLayout(gridLayout);

		Label label = new Label(parent, SWT.NONE);
		label.setFont(new Font(Display.getCurrent(), "", 10, SWT.BOLD
				| SWT.COLOR_RED));
		label.setText(PreferenceConstants.JUTILS_PAGE_DESCRIPTION);

		Canvas canvas = new Canvas(parent, SWT.NONE);

		canvas.setSize(0, 3);

		label = new Label(parent, SWT.NONE);
		label.setFont(new Font(Display.getCurrent(), "", 10, 1));
		label.setText(PreferenceConstants.ATTRIBUTE);

		label = new Label(parent, SWT.NONE);
		label.setText(PreferenceConstants.ATTRIBUTE_DESCRIPTION);
		label.setFont(new Font(Display.getCurrent(), "", 10, 0));

		label = new Label(parent, SWT.NONE);
		label.setFont(new Font(Display.getCurrent(), "", 10, 1));
		label.setText(PreferenceConstants.CLASS_INSTANCE);

		label = new Label(parent, SWT.NONE);
		label.setText(PreferenceConstants.CLASS_INSTANCE_DESCRIPTION);
		label.setFont(new Font(Display.getCurrent(), "", 10, 0));

		label = new Label(parent, SWT.NONE);
		label.setFont(new Font(Display.getCurrent(), "", 10, 1));
		label.setText(PreferenceConstants.CLASS_NAME);

		label = new Label(parent, SWT.NONE);
		label.setText(PreferenceConstants.CLASS_NAME_DESCRIPTION);
		label.setFont(new Font(Display.getCurrent(), "", 10, 0));
		
		

		label = new Label(parent, SWT.NONE);
		label.setFont(new Font(Display.getCurrent(), "", 10, 1));
		label.setText(PreferenceConstants.MODIFY_AUTHOR);

		label = new Label(parent, SWT.NONE);
		label.setText(PreferenceConstants.MODIFY_AUTHOR_DESCRIPTION);
		label.setFont(new Font(Display.getCurrent(), "", 10, 0));

		return parent;
	}
}