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

package org.adarsh.jutils.actions;

import org.adarsh.jutils.Messages;
import org.adarsh.jutils.internal.Logger;
import org.adarsh.jutils.internal.SourceManipulator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The Object Action Delegate for copy constructor.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 * 
 * @version 3.0, 10th December 2006
 */
public class CopyConstructorGeneratorOAD implements IObjectActionDelegate {
	/**
	 * The associated <tt>PackageExplorerPart</tt>.
	 */
	private PackageExplorerPart packageExplorerPart;

	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.packageExplorerPart = (PackageExplorerPart) targetPart;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		IWorkbenchPage page = this.packageExplorerPart.getSite().getPage();

		StructuredSelection selection = (StructuredSelection) page
				.getSelection();

		Object[] compUnits = selection.toArray();

		Shell shell = this.packageExplorerPart.getSite().getShell();

		int failed = 0;

		for (int i = 0; i < compUnits.length; i++) {
			ICompilationUnit compUnit = (ICompilationUnit) compUnits[i];

			IType type = compUnit.findPrimaryType();

			try {
				if (type != null && type.isClass()) {
					SourceManipulator.createCopyConstructorWithJavaDoc(type);
				} else {
					failed++;
				}
			} catch (JavaModelException e) {
				MessageDialog.openError(shell,
						Messages.getString("exception.title"),
						Messages.getString("exception.message"));

				Logger.error("Error generating Copy Constructor "
						+ "through OAD", e);

				break;
			}
		}

		if (failed == compUnits.length) {
			MessageDialog.openInformation(shell,
					Messages.getString("copycon.failure.title"),
					Messages.getString("copycon.failure.message"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// selection has changed. But, do nothing.
	}
}
